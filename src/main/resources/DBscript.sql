create table public.registered_users
(
    id        serial not null
        constraint registered_users_pk
            primary key,
    email     text   not null,
    password  text   not null,
    user_type text   not null,
    city      varchar
);

alter table public.registered_users
    owner to postgres;

create unique index registered_users_email_uindex
    on public.registered_users (email);

create unique index registered_users_id_uindex
    on public.registered_users (id);

create table public.users
(
    id                  integer not null
        constraint users_pk
            primary key
        constraint users_registered_users_id_fk
            references public.registered_users
            on update cascade on delete cascade,
    encoded_profile_img text
);

alter table public.users
    owner to postgres;

create unique index users_id_uindex
    on public.users (id);

create table public.solo
(
    id         integer not null
        constraint solo_pk
            primary key
        constraint solo_users_id_fk
            references public.users
            on update cascade on delete cascade,
    age        integer,
    first_name text,
    last_name  text
);

alter table public.solo
    owner to postgres;

create unique index solo_id_uindex
    on public.solo (id);

create table public.band
(
    id        integer not null
        constraint band_pk
            primary key
        constraint band_users_id_fk
            references public.users
            on update cascade on delete cascade,
    band_name text    not null,
    spotify   varchar,
    youtube   varchar,
    facebook  varchar,
    members   integer[]
);

alter table public.band
    owner to postgres;

create unique index band_id_uindex
    on public.band (id);

create table public.room_renter
(
    id      integer not null
        constraint room_manager_pk
            primary key
        constraint room_manager_users_id_fk
            references public.users
            on update cascade on delete cascade,
    name    varchar not null,
    address varchar not null
);

alter table public.room_renter
    owner to postgres;

create unique index room_manager_id_uindex
    on public.room_renter (id);

create table public.room
(
    id          integer not null
        constraint room_room_manager_id_fk
            references public.room_renter
            on update cascade on delete cascade,
    room_code   serial  not null
        constraint room_pk
            primary key,
    room_price  numeric not null,
    photo       text,
    description text,
    name        text    not null
);

alter table public.room
    owner to postgres;

create unique index room_room_code_uindex
    on public.room (room_code);

create table public.banned_users
(
    email text not null
);

alter table public.banned_users
    owner to postgres;

create unique index banned_users_email_uindex
    on public.banned_users (email);

create table public.band_solo_members
(
    id_band integer not null
        constraint band_solo_members_band_id_fk
            references public.band
            on update cascade on delete cascade,
    id_solo integer not null
        constraint band_solo_members_solo_id_fk
            references public.solo
            on update cascade on delete cascade
);

alter table public.band_solo_members
    owner to postgres;

create unique index band_solo_members_id_band_uindex
    on public.band_solo_members (id_band);

create unique index band_solo_members_id_solo_uindex
    on public.band_solo_members (id_solo);

create table public.played_instruments
(
    id          integer not null
        constraint instruments_pk
            primary key
        constraint instruments_solo_id_fk
            references public.solo
            on update cascade on delete cascade,
    instruments text[]
);

alter table public.played_instruments
    owner to postgres;

create unique index instruments_id_uindex
    on public.played_instruments (id);

create table public.fav_genres
(
    id    integer not null
        constraint fav_genres_pk
            primary key
        constraint fav_genres_solo_id_fk
            references public.solo
            on update cascade on delete cascade,
    genre text[]
);

alter table public.fav_genres
    owner to postgres;

create unique index fav_genres_id_uindex
    on public.fav_genres (id);

create table public.played_genres
(
    id    integer not null
        constraint played_genres_pk
            primary key
        constraint played_genres_band_id_fk
            references public.band
            on update cascade on delete cascade,
    genre text[]
);

alter table public.played_genres
    owner to postgres;

create unique index played_genres_id_uindex
    on public.played_genres (id);

create table public.notifications
(
    message_id   serial  not null
        constraint messages_pk
            primary key,
    sender       integer,
    receiver     integer,
    type         varchar not null,
    seen         boolean,
    booking_id   integer,
    join_request integer
);

alter table public.notifications
    owner to postgres;

create table public.messages
(
    code             serial                                not null
        constraint table_name_pk
            primary key,
    id_sender        integer                               not null
        constraint table_name_users_id_fk_2
            references public.registered_users
            on update cascade on delete cascade,
    id_receiver      integer                               not null
        constraint table_name_users_id_fk
            references public.registered_users
            on update cascade on delete cascade,
    subject          text    default 'empty subject'::text not null,
    body             text    default 'empty body'::text    not null,
    is_read          boolean default false                 not null,
    sender_user_type text                                  not null
);

alter table public.messages
    owner to postgres;

create unique index table_name_code_uindex
    on public.messages (code);

create table public.applications
(
    code        serial  not null
        constraint applications_pk
            primary key,
    id_band     integer not null
        constraint applications_band_id_fk
            references public.band
            on update cascade on delete cascade,
    message     text,
    instruments text[]
);

alter table public.applications
    owner to postgres;

create unique index applications_code_uindex
    on public.applications (code);

create table public.join_request
(
    code             serial                       not null
        constraint join_request_pk
            primary key,
    code_application integer
        constraint join_request_applications_code_fk
            references public.applications
            on update cascade on delete cascade,
    id_band          integer                      not null
        constraint join_request_band_id_fk
            references public.band
            on update cascade on delete cascade,
    id_solo          integer                      not null
        constraint join_request_solo_id_fk
            references public.solo
            on update cascade on delete cascade,
    message          text,
    request_state    text default 'CREATED'::text not null
);

alter table public.join_request
    owner to postgres;

create unique index join_request_code_uindex
    on public.join_request (code);

create table public.booking
(
    code        serial                not null
        constraint booking_pk
            primary key,
    room_code   integer               not null
        constraint booking_room_room_code_fk
            references public.room
            on update cascade on delete cascade
        constraint booking_room_room_code_fk_2
            references public.room
            on update cascade on delete cascade,
    date        date                  not null,
    start_time  time                  not null,
    end_time    time,
    booker_id   integer               not null
        constraint booking_users_id_fk
            references public.users
            on update cascade on delete cascade,
    is_accepted boolean default false not null,
    id_renter   integer               not null
        constraint booking_room_renter_id_fk
            references public.room_renter
            on update cascade on delete cascade
);

alter table public.booking
    owner to postgres;

create unique index booking_booking_code_uindex
    on public.booking (code);

