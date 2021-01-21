create table banned_users
(
    email text not null
);

alter table banned_users
    owner to postgres;

create unique index banned_users_email_uindex
    on banned_users (email);

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

create table band
(
    id        integer not null
        constraint band_pk
            primary key
        constraint band_users_id_fk
            references users
            on update cascade on delete cascade,
    band_name text    not null
);

alter table band
    owner to postgres;

create unique index band_id_uindex
    on band (id);

create table band_manager
(
    id         integer not null
        constraint band_manager_pk
            primary key
        constraint band_manager_users_id_fk
            references users
            on update cascade on delete cascade,
    first_name text    not null,
    last_name  text    not null
);

alter table band_manager
    owner to postgres;

create table band_band_managed
(
    id_manager integer not null
        constraint band_band_managed_band_manager_id_fk
            references band_manager
            on update cascade on delete cascade,
    id_band    integer not null
        constraint band_band_managed_band_id_fk
            references band
            on update cascade on delete cascade
);

alter table band_band_managed
    owner to postgres;

create unique index band_band_managed_id_band_uindex
    on band_band_managed (id_band);

create unique index band_band_managed_id_manager_uindex
    on band_band_managed (id_manager);

create unique index band_manager_id_uindex
    on band_manager (id);

create table played_genres
(
    id    integer not null
        constraint played_genres_pk
            primary key
        constraint played_genres_band_id_fk
            references band
            on update cascade on delete cascade,
    genre text    not null
);

alter table played_genres
    owner to postgres;

create unique index played_genres_id_uindex
    on played_genres (id);

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

create table room
(
    id           integer              not null
        constraint room_room_manager_id_fk
            references room_renter
            on update cascade on delete cascade,
    room_code    serial               not null
        constraint room_pk
            primary key,
    room_price   numeric              not null,
    room_is_free boolean default true not null,
    photo        text,
    description  text,
    name         varchar              not null
);

alter table room
    owner to postgres;

create unique index room_room_code_uindex
    on room (room_code);

create unique index room_manager_id_uindex
    on room_renter (id);

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

create table fav_genres
(
    id    integer not null
        constraint fav_genres_pk
            primary key
        constraint fav_genres_solo_id_fk
            references solo
            on update cascade on delete cascade,
    genre text[]  not null
);

alter table fav_genres
    owner to postgres;

create unique index fav_genres_id_uindex
    on fav_genres (id);

create table played_instruments
(
    id          integer not null
        constraint instruments_pk
            primary key
        constraint instruments_solo_id_fk
            references solo
            on update cascade on delete cascade,
    instruments text[]  not null
);

alter table played_instruments
    owner to postgres;

create unique index instruments_id_uindex
    on played_instruments (id);

create unique index solo_id_uindex
    on solo (id);

create unique index users_id_uindex
    on users (id);

