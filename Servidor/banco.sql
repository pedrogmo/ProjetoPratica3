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
	constraint fkUsuarioEmpresa foreign key(codEmpresa) references EmpresaSol(codigo),

	telefone varchar(20) not null,

	data char(10) not null
	constraint chkDataUsuario check(data like 
		'[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]'),

	cpf char(14) not null
	constraint chkCpfUsuario check(cpf like 
		'[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]-[0-9][0-9]'),
)

create table ClienteSol(
	codigo int identity(1,1) primary key,
	email varchar(50) not null,
	nome varchar(100) not null,
	telefone varchar(20) not null,

	data char(10) not null
	constraint chkData check(data like 
		'[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]'),

	cpf char(14) not null
	constraint chkCpf check(cpf like 
		'[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]-[0-9][0-9]'),

	codEmpresa int not null
	constraint fkClienteEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
)

insert into EmpresaSol values('PuroAr', '07.893.913/0001-23')

create table ModuloSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,
	altura float not null,
	largura float not null,
	profundidade float not null,
	peso float not null,
	voltagem float not null
)

insert into ModuloSol values('modulo1', 10.5, 'modulo 1 teste', 10, 10, 10, 20, 50)

create table InversorSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,
	altura float not null,
	largura float not null,
	profundidade float not null,
	peso float not null,
	eficienciaMaxima float not null
)

insert into InversorSol values('inversor1', 11, 'inversor 1 teste', 12, 12, 12, 25, 120)

create table StringBoxSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,

	tipo varchar(30) not null
)

create table FixacaoSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null
)

create table BombaSolarSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,
	altura float not null,
	largura float not null,
	profundidade float not null,
	peso float not null,

	tensaoAlimentacao float not null,
	temperaturaMaxima float not null,
	alturaMaxima float not null,
	bombeamentoMaximoDiario float not null,
	diametroTubo varchar(20) not null,
)

create table CaboSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,

	comprimento float not null,
	diametro float not null,
	conducao varchar(20) not null
)