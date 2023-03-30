create table IF NOT EXISTS public.zakazi
(
    id               bigint  not null
        primary key,
    version          integer not null,
    date             timestamp(6),
    dead_man_surname varchar(255),
    name             varchar(255),
    phone            varchar(255),
    raboti           varchar(255),
    stadia           varchar(255),
    uchastok         varchar(255)
);

alter table public.zakazi
    owner to vaadinstart;
create sequence IF NOT EXISTS public.idgenerator
    increment by 50;

alter sequence public.idgenerator owner to vaadinstart;