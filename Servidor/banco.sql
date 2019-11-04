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

create table KitSol(
	codigo int identity(1,1) primary key,
	codEmpresa int not null,
	nome varchar(50) not null

	constraint fkKitEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
)

create table KitModuloSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitModulo foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkModulo foreign key(codProduto) references ModuloSol(codigo)
)

create table KitInversorSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitInversor foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkInversor foreign key(codProduto) references InversorSol(codigo)
)

create table KitStringBoxSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitStringBox foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkStringBox foreign key(codProduto) references StringBoxSol(codigo)
)

create table KitFixacaoSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitFixacao foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkFixacao foreign key(codProduto) references FixacaoSol(codigo)
)

create table KitBombaSolarSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitBombaSolar foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkBombaSolar foreign key(codProduto) references BombaSolarSol(codigo)
)

create table KitCaboSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitCabo foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkCabo foreign key(codProduto) references CaboSol(codigo)
)

create table PropostaSol(
	codigo int identity(1,1) primary key,
	codUsuario int not null,
	codEmpresa int not null,
	codCliente int not null,
	codKit int not null,

	constraint fkUsuarioProposta foreign key(codUsuario) references UsuarioSol(codigo),
	constraint fkEmpresaProposta foreign key(codEmpresa) references EmpresaSol(codigo),
	constraint fkClienteProposta foreign key(codCliente) references ClienteSol(codigo),
	constraint fkKitProposta foreign key(codKit) references KitSol(codigo)
)