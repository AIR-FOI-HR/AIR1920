create table kategorija_znamenitost
(
    id_kategorija_znamenitosti int identity
        constraint kategorija_znamenitost_pk
            primary key nonclustered,
    naziv                      varchar(255) not null
)
go

create unique index kategorija_znamenitost_id_kategorija_znamenitosti_uindex
    on kategorija_znamenitost (id_kategorija_znamenitosti)
go

create unique index kategorija_znamenitost_naziv_uindex
    on kategorija_znamenitost (naziv)
go

create table slika
(
    id_slika    int identity
        constraint slika_pk
            primary key nonclustered,
    img_url     varchar(255) not null,
    tekst_slike varchar(255) not null
)
go

create table korisnik
(
    korisnicko_ime varchar(255) not null
        constraint korisnik_pk
            primary key nonclustered,
    ime            varchar(255) not null,
    prezime        varchar(255) not null,
    email          varchar(255) not null,
    lozinka        varchar(255) not null,
    id_slika       int default 1
        constraint korisnik_ima_sliku_fk
            references slika
            on update cascade on delete set default
)
go

create unique index korisnik_korisnicko_ime_uindex
    on korisnik (korisnicko_ime)
go

create table lokacija
(
    id_lokacija int identity
        constraint lokacija_pk
            primary key nonclustered,
    naziv       varchar(255) not null,
    opis        varchar(1023),
    id_slika    int default 2
        constraint lokacija_ima_sliku
            references slika
            on update cascade on delete set default
)
go

create table korisnik_lokacij
(
    korisnicko_ime varchar(255) not null
        constraint kor_u_lok_fk
            references korisnik,
    id_lokacija    int          not null
        constraint lok_ima_kor_fk
            references lokacija,
    datum          date         not null,
    constraint korisnik_lokacij_pk
        primary key nonclustered (korisnicko_ime, id_lokacija)
)
go

create unique index lokacija_id_lokacija_uindex
    on lokacija (id_lokacija)
go

create unique index slika_id_slika_uindex
    on slika (id_slika)
go

create table znamenitost
(
    id_znamenitost             int identity
        constraint znamenitost_pk
            primary key nonclustered,
    naziv                      varchar(255) not null,
    adresa                     varchar(255) not null,
    opis                       varchar(1023),
    longitude                  bigint,
    latitude                   bigint,
    id_kategorija_znamenitosti int          not null
        constraint znam_ima_kat_fk
            references kategorija_znamenitost
            on update cascade,
    id_lokacija                int          not null
        constraint znam_ima_lok_fk
            references lokacija
            on update cascade,
    id_slika                   int          not null
)
go

create table favourites
(
    korisnicko_ime varchar(255) not null
        constraint kor_fav_znam_fk
            references korisnik,
    id_znamenitost int          not null
        constraint znam_fav_kor_fk
            references znamenitost,
    constraint favourites_pk
        primary key nonclustered (korisnicko_ime, id_znamenitost)
)
go

create table komentar
(
    id_komentar    int identity
        constraint komentar_pk
            primary key nonclustered,
    opis           varchar(1023),
    ocjena         int          not null,
    korisnicko_ime varchar(255) not null
        constraint kom_pripada_kor_fk
            references korisnik,
    id_znamenitost int          not null
        constraint kom_na_znam_fk
            references znamenitost
)
go

create unique index komentar_id_komentar_uindex
    on komentar (id_komentar)
go

create table ocjena_komentara
(
    korisnicko_ime varchar(255) not null
        constraint kor_ocj_kom_fk
            references korisnik,
    id_komentar    int          not null
        constraint kom_ocj_kor_fk
            references komentar,
    ocjena         bit          not null,
    constraint ocjena_komentara_pk
        primary key nonclustered (korisnicko_ime, id_komentar)
)
go

create table slika_galerije
(
    id_slika       int not null
        constraint slika_pripada_znam_fk
            references slika,
    id_znamenitost int not null
        constraint znam_ima_slika_fk
            references znamenitost,
    constraint slika_galerije_pk
        primary key nonclustered (id_slika, id_znamenitost)
)
go

create unique index znamenitost_id_znamenitost_uindex
    on znamenitost (id_znamenitost)
go


