create table EmpresaSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	cnpj varchar(18) not null

	constraint chkCnpj check(cnpj like 
		'[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]/[0-9][0-9][0-9][0-9]-[0-9][0-9]')
)

create table UsuarioSol(
	codigo int identity(1,1) primary key,
	email varchar(50) not null,
	nome varchar(100) not null,
	senha varchar(50) not null,
	codEmpresa int not null

	constraint fkUsuarioEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
)

insert into EmpresaSol values('PuroAr', '07.893.913/0001-23')
insert into UsuarioSol values('gugahmeira@gmail.com', 'Gustavo Henrique de Meira', 'supermacho', 2)