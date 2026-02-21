create table users(
    id serial primary key,
    email varchar(254) not null unique,
    password varchar(254) not null,
    role varchar(254) default 'CLIENT',
    active boolean default true
);

create table client(
    id serial primary key,
    userId int,
    firstname varchar(254) not null,
    lastname varchar(254) not null,
    profession varchar(254),
    foreign key (userId) references users(id)
);

create table provider(
    id serial primary key,
    userId int,
    firstname varchar(254) not null,
    lastname varchar(254) not null,
    profession varchar(254) not null,
    foreign key (userId) references users(id)
);

create table admin(
    id serial primary key,
    userId int,
    foreign key (userId) references users(id)
);

create table category(
    id serial primary key,
    name varchar(254) not null unique
);

create table service(
    id serial primary key,
    name varchar(254) not null unique,
    category int,
    createdAt timestamp default current_timestamp,
    foreign key (category) references category(id)
);

create table serviceProposal(
    id serial primary key,
    service int,
    provider int,
    foreign key (service) references service(id),
    foreign key (provider) references provider(id)
);

create table askingService(
    id serial primary key,
    proposal int not null,
    client int not null,
    foreign key (proposal) references serviceProposal(id),
    foreign key (client) references client(id)
);

create table comment(
    id serial primary key,
    content text not null,
    createdAt timestamp default current_timestamp
);

create table review(
    id serial primary key,
    mark int not null,
    comment int not null,
    foreign key (comment) references comment (id)
);

alter table serviceProposal add constraint unique_service_provider unique(service,provider);

--creation des index
create index idx_user_role on users(role);
create index idx_user_client on client(userId);
create index idx_user_provider on provider(userId);
create index idx_user_admin on admin(userId);
create index idx_profession_provider on provider(profession);
create index idx_mark_review on review(mark);
create index idx_category_name on category(name);
create index idx_service_name on service(name);
create index idx_askingservice_proposal on askingService(proposal);
create index idx_askingservice_client on askingService(client);
create index idx_review_comment on review(comment);
create index idx_serviceproposal_service on serviceProposal(service);
create index idx_serviceproposal_provider on serviceProposal(provider);
