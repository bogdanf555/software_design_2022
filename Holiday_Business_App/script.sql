create table destination
(
    id                 int          not null
        primary key,
    celsiusTemperature double       null,
    country            varchar(255) null,
    description        varchar(255) null,
    name               varchar(255) not null,
    constraint UK_kw349sqcyo1k39xa0wn3k3q2r
        unique (name)
);

create table hibernate_sequence
(
    next_val bigint null
);

create table package
(
    id              int          not null
        primary key,
    bookings        int          not null,
    end             date         not null,
    extraDetails    varchar(255) null,
    maximumBookings int          not null,
    name            varchar(255) not null,
    price           double       not null,
    start           date         not null,
    destination_id  int          null,
    constraint UK_5w2s5w41h46jo7cq46hrb8cx2
        unique (name),
    constraint FKtq1gn2h0fe7r8ddc34f6tlit1
        foreign key (destination_id) references destination (id)
);

create table user
(
    id        int          not null
        primary key,
    firstName varchar(255) null,
    lastName  varchar(255) null,
    password  varchar(255) not null,
    username  varchar(255) not null,
    constraint UK_sb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);

create table user_package
(
    user_id    int not null,
    package_id int not null,
    constraint FK8n8qhfs5eceli4n13yf6u1agp
        foreign key (user_id) references user (id),
    constraint FKjcwapl2htr7k7l485y29cinog
        foreign key (package_id) references package (id)
);


