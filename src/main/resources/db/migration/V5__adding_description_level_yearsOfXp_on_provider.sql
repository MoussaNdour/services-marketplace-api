alter table provider add column description text not null default 'Service description';
alter table provider add column yearsOfExperience int not null default 1;
alter table provider add column level varchar(255) default 'JUNIOR';


