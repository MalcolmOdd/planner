create table person (
    id uuid not null,
    name varchar(255),
    primary key (id)
);
create table tasktype (
    id uuid not null,
    name varchar(255),
    primary key (id)
);
create table task (
    end_ts timestamp(6) with time zone,
    start_ts timestamp(6) with time zone,
    id uuid not null,
    person_id uuid,
    tasktype_id uuid not null,
    primary key (id),
    foreign key (person_id) references person,
    foreign key (tasktype_id) references tasktype
);
