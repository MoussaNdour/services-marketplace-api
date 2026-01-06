alter table serviceProposal add column price DOUBLE PRECISION default 10 not null;

create index idx_price on serviceProposal(price);