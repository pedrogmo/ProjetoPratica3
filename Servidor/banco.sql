create table EmpresaSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	cnpj varchar(18) not null

	constraint chkCnpj check(cnpj like 
		'[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]/[0-9][0-9][0-9][0-9]-[0-9][0-9]'),

	senha varchar(50) not null
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
	potencia float not null,
	codEmpresa int not null
	constraint fkModuloEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
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
	eficienciaMaxima float not null,
	codEmpresa int not null
	constraint fkInversorEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
)

create table StringBoxSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,

	tipo varchar(30) not null,
	numeroPolos int not null,
	tensaoMaxima float not null,
	correnteNominal float not null,
	codEmpresa int not null
	constraint fkStringBoxEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
)

create table FixacaoSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,
	codEmpresa int not null
	constraint fkFixacaoEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
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
	codEmpresa int not null
	constraint fkBombaSolarEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
)

create table CaboSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	preco money not null,
	descricao varchar(100) not null,

	comprimento float not null,
	diametro float not null,
	conducao varchar(20) not null,
	codEmpresa int not null
	constraint fkCaboEmpresa foreign key(codEmpresa) references EmpresaSol(codigo)
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
	constraint fkModulo foreign key(codProduto) references ModuloSol(codigo),

	quantidade int not null
)

create table KitInversorSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitInversor foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkInversor foreign key(codProduto) references InversorSol(codigo),

	quantidade int not null
)

create table KitStringBoxSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitStringBox foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkStringBox foreign key(codProduto) references StringBoxSol(codigo),

	quantidade int not null
)

create table KitFixacaoSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitFixacao foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkFixacao foreign key(codProduto) references FixacaoSol(codigo),

	quantidade int not null
)

create table KitBombaSolarSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitBombaSolar foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkBombaSolar foreign key(codProduto) references BombaSolarSol(codigo),

	quantidade int not null
)

create table KitCaboSol(
	codigo int identity(1,1) primary key,
	
	codKit int not null
	constraint fkKitCabo foreign key(codKit) references KitSol(codigo),

	codProduto int not null
	constraint fkCabo foreign key(codProduto) references CaboSol(codigo),

	quantidade int not null
)

create table PropostaSol(
	codigo int identity(1,1) primary key,
	nome varchar(50) not null,
	codUsuario int not null,
	codCliente int not null,
	codKit int not null,

	constraint fkUsuarioProposta foreign key(codUsuario) references UsuarioSol(codigo),
	constraint fkClienteProposta foreign key(codCliente) references ClienteSol(codigo),
	constraint fkKitProposta foreign key(codKit) references KitSol(codigo)
)

--TRIGGERS

alter trigger delete_empresa_tg on EmpresaSol instead of delete
as
begin

	declare @codEmpresa int
	select @codEmpresa = codigo from Deleted

	delete from UsuarioSol where codEmpresa = @codEmpresa
	delete from ClienteSol where codEmpresa = @codEmpresa
	delete from KitSol where codEmpresa = @codEmpresa
	delete from ModuloSol where codEmpresa = @codEmpresa
	delete from InversorSol where codEmpresa = @codEmpresa
	delete from StringBoxSol where codEmpresa = @codEmpresa
	delete from FixacaoSol where codEmpresa = @codEmpresa
	delete from BombaSolarSol where codEmpresa = @codEmpresa
	delete from CaboSol where codEmpresa = @codEmpresa
	delete from EmpresaSol where codigo = @codEmpresa

end

alter trigger delete_usuario_tg on UsuarioSol instead of delete
as
begin

	declare @codUsuario int
	select @codUsuario = codigo from Deleted

	delete from PropostaSol where codUsuario = @codUsuario
	delete from UsuarioSol where codigo = @codUsuario
end

alter trigger delete_cliente_tg on ClienteSol instead of delete
as
begin

	declare @codCliente int
	select @codCliente = codigo from Deleted

	delete from PropostaSol where codCliente = @codCliente
	delete from ClienteSol where codigo = @codCliente
end

alter trigger delete_kit_tg on KitSol instead of delete
as
begin

	declare @codKit int
	select @codKit = codigo from Deleted

	delete from PropostaSol where codKit = @codKit
	delete from KitModuloSol where codKit = @codKit
	delete from KitInversorSol where codKit = @codKit
	delete from KitStringBoxSol where codKit = @codKit
	delete from KitFixacaoSol where codKit = @codKit
	delete from KitBombaSolarSol where codKit = @codKit
	delete from KitCaboSol where codKit = @codKit
	delete from KitSol where codigo = @codKit
end

alter trigger delete_modulo_tg on ModuloSol instead of delete
as
begin

	declare @codModulo int
	select @codModulo = codigo from Deleted

	delete from KitModuloSol where codProduto = @codModulo
	delete from ModuloSol where codigo = @codModulo
end

alter trigger delete_inversor_tg on InversorSol instead of delete
as
begin

	declare @codInversor int
	select @codInversor = codigo from Deleted

	delete from KitInversorSol where codProduto = @codInversor
	delete from InversorSol where codigo = @codInversor
end

alter trigger delete_stringbox_tg on StringBoxSol instead of delete
as
begin

	declare @codStringBox int
	select @codStringBox = codigo from Deleted

	delete from KitStringBoxSol where codProduto = @codStringBox
	delete from StringBoxSol where codigo = @codStringBox
end

alter trigger delete_fixacao_tg on FixacaoSol instead of delete
as
begin

	declare @codFixacao int
	select @codFixacao = codigo from Deleted

	delete from KitFixacaoSol where codProduto = @codFixacao
	delete from FixacaoSol where codigo = @codFixacao
end

alter trigger delete_bombasolar_tg on BombaSolarSol instead of delete
as
begin

	declare @codBombaSolar int
	select @codBombaSolar = codigo from Deleted

	delete from KitBombaSolarSol where codProduto = @codBombaSolar
	delete from BombaSolarSol where codigo = @codBombaSolar
end

alter trigger delete_cabo_tg on CaboSol instead of delete
as
begin

	declare @codCabo int
	select @codCabo = codigo from Deleted

	delete from KitCaboSol where codProduto = @codCabo
	delete from CaboSol where codigo = @codCabo
end
