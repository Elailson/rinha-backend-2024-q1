create table cliente(
	id serial primary key,
	limite integer not null,
	saldo integer not null
);

create table transacao(
	id serial primary key,
	valor integer not null,
	tipo integer not null,
	descricao varchar(10) not null,
	realizada_em timestamp
);