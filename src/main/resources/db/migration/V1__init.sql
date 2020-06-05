create table cars
(
    id           bigserial not null,
    created_date timestamp not null,
    updated_date timestamp not null,
    name         varchar(255),
    type         varchar(255),
    primary key (id)
);

create table humans
(
    id           bigserial not null,
    created_date timestamp not null,
    updated_date timestamp not null,
    name         varchar(255),
    second_name  varchar(255),
    primary key (id)
);

create table humans_cars
(
    human_id int8 not null,
    cars_id  int8 not null,
    primary key (human_id, cars_id),
    foreign key (cars_id) references cars on delete cascade,
    foreign key (human_id) references humans
);
