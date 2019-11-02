create table EmpresaSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	cnpj varchar(18) not null

	constraint chkCnpj check(cnpj like 
		'[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]/[0-9][0-9][0-9][0-9]-[0-9][0-9]')
)

create trigger delete_empresa_tg on EmpresaSol instead of delete
as
begin

	declare @codEmpresa int
	select @codEmpresa = codigo from Deleted

	delete from UsuarioSol where codEmpresa = @codEmpresa
	delete from ClienteSol where codEmpresa = @codEmpresa
	delete from EmpresaSol where codigo = @codEmpresa

end

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

	permissaoEmpresa bit not null
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

create table StringBoxSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,

	tipo varchar(30) not null,
	numeroPolos int not null,
	tensaoMaxima float not null,
	correnteNominal float not null
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