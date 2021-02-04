create table registered_users
(
    id        serial not null
        constraint registered_users_pk
            primary key,
    email     text   not null,
    password  text   not null,
    user_type text   not null,
    city      varchar
);

alter table registered_users
    owner to postgres;

create unique index registered_users_email_uindex
    on registered_users (email);

create unique index registered_users_id_uindex
    on registered_users (id);

create table users
(
    id                  integer not null
        constraint users_pk
            primary key
        constraint users_registered_users_id_fk
            references registered_users
            on update cascade on delete cascade,
    encoded_profile_img text
);

alter table users
    owner to postgres;

create unique index users_id_uindex
    on users (id);

create table solo
(
    id         integer not null
        constraint solo_pk
            primary key
        constraint solo_users_id_fk
            references users
            on update cascade on delete cascade,
    age        integer,
    first_name text,
    last_name  text
);

alter table solo
    owner to postgres;

create unique index solo_id_uindex
    on solo (id);

create table band
(
    id        integer not null
        constraint band_pk
            primary key
        constraint band_users_id_fk
            references users
            on update cascade on delete cascade,
    band_name text    not null,
    spotify   varchar,
    youtube   varchar,
    facebook  varchar,
    members   integer[]
);

alter table band
    owner to postgres;

create unique index band_id_uindex
    on band (id);

create table room_renter
(
    id      integer not null
        constraint room_manager_pk
            primary key
        constraint room_manager_users_id_fk
            references users
            on update cascade on delete cascade,
    name    varchar not null,
    address varchar not null
);

alter table room_renter
    owner to postgres;

create unique index room_manager_id_uindex
    on room_renter (id);

create table room
(
    id          integer not null
        constraint room_room_manager_id_fk
            references room_renter
            on update cascade on delete cascade,
    room_code   serial  not null
        constraint room_pk
            primary key,
    room_price  numeric not null,
    photo       text,
    description text,
    name        text    not null
);

alter table room
    owner to postgres;

create unique index room_room_code_uindex
    on room (room_code);

create table banned_users
(
    email text not null
);

alter table banned_users
    owner to postgres;

create unique index banned_users_email_uindex
    on banned_users (email);

create table band_solo_members
(
    id_band integer not null
        constraint band_solo_members_band_id_fk
            references band
            on update cascade on delete cascade,
    id_solo integer not null
        constraint band_solo_members_solo_id_fk
            references solo
            on update cascade on delete cascade
);

alter table band_solo_members
    owner to postgres;

create unique index band_solo_members_id_band_uindex
    on band_solo_members (id_band);

create unique index band_solo_members_id_solo_uindex
    on band_solo_members (id_solo);

create table played_instruments
(
    id          integer not null
        constraint instruments_pk
            primary key
        constraint instruments_solo_id_fk
            references solo
            on update cascade on delete cascade,
    instruments text[]
);

alter table played_instruments
    owner to postgres;

create unique index instruments_id_uindex
    on played_instruments (id);

create table fav_genres
(
    id    integer not null
        constraint fav_genres_pk
            primary key
        constraint fav_genres_solo_id_fk
            references solo
            on update cascade on delete cascade,
    genre text[]
);

alter table fav_genres
    owner to postgres;

create unique index fav_genres_id_uindex
    on fav_genres (id);

create table played_genres
(
    id    integer not null
        constraint played_genres_pk
            primary key
        constraint played_genres_band_id_fk
            references band
            on update cascade on delete cascade,
    genre text[]
);

alter table played_genres
    owner to postgres;

create unique index played_genres_id_uindex
    on played_genres (id);

create table notifications
(
    message_id serial  not null
        constraint messages_pk
            primary key,
    sender     integer,
    receiver   integer,
    type       varchar not null,
    seen       boolean,
    booking_id integer
);

alter table notifications
    owner to postgres;

create table messages
(
    code             serial                                not null
        constraint table_name_pk
            primary key,
    id_sender        integer                               not null
        constraint table_name_users_id_fk_2
            references registered_users
            on update cascade on delete cascade,
    id_receiver      integer                               not null
        constraint table_name_users_id_fk
            references registered_users
            on update cascade on delete cascade,
    subject          text    default 'empty subject'::text not null,
    body             text    default 'empty body'::text    not null,
    is_read          boolean default false                 not null,
    sender_user_type text                                  not null
);

alter table messages
    owner to postgres;

create unique index table_name_code_uindex
    on messages (code);

create table applications
(
    code        serial  not null
        constraint applications_pk
            primary key,
    id_band     integer not null
        constraint applications_band_id_fk
            references band
            on update cascade on delete cascade,
    message     text,
    instruments text[]
);

alter table applications
    owner to postgres;

create unique index applications_code_uindex
    on applications (code);

create table join_request
(
    code             serial                not null
        constraint join_request_pk
            primary key,
    code_application integer
        constraint join_request_applications_code_fk
            references applications
            on update cascade on delete cascade,
    id_band          integer               not null
        constraint join_request_band_id_fk
            references band
            on update cascade on delete cascade,
    id_solo          integer               not null
        constraint join_request_solo_id_fk
            references solo
            on update cascade on delete cascade,
    message          text,
    is_accepted      boolean default false not null
);

alter table join_request
    owner to postgres;

create unique index join_request_code_uindex
    on join_request (code);

create table booking
(
    code        serial                not null
        constraint booking_pk
            primary key,
    room_code   integer               not null
        constraint booking_room_room_code_fk
            references room
            on update cascade on delete cascade
        constraint booking_room_room_code_fk_2
            references room
            on update cascade on delete cascade,
    date        date                  not null,
    start_time  time                  not null,
    end_time    time,
    booker_id   integer               not null
        constraint booking_users_id_fk
            references users
            on update cascade on delete cascade,
    is_accepted boolean default false not null
);

alter table booking
    owner to postgres;

create unique index booking_booking_code_uindex
    on booking (code);

