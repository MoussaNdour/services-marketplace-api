alter table service add column mark decimal;
alter table service add column description text;

create index idx_service_mark on service(mark);