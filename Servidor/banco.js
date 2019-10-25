const express = require('express'); 
const app = express(); 
const bodyParser = require('body-parser'); 
const porta = 3000; //porta padrão 
const sql = require('mssql'); 
const conexaoStr = "Server=regulus.cotuca.unicamp.br;Database=PR118174;User Id=PR118174;Password=PR118174;";

//conexao com BD 
sql.connect(conexaoStr) 
.then(conexao => global.conexao = conexao) 
.catch(erro => console.log(erro)); 

// configurando o body parser para pegar POSTS mais tarde 
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
app.use(bodyParser.json({limit: '50mb', extended: true}));

app.use(function(req, res, next) { 
	res.header("Access-Control-Allow-Origin", "*"); 
	res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); 
	res.header("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, PATCH, DELETE"); 
	next(); 
});

//definindo as rotas
const rota = express.Router(); 
rota.get('/', (requisicao, resposta) => resposta.json({ mensagem: 'Funcionando!'})); 
app.use('/', rota); 

//inicia servidor 
app.listen(porta); 
console.log('API funcionando na porta ' + porta); 
function execSQL(sql, resposta) { 
	global.conexao.request() 
	.query(sql) 
	.then(resultado => resposta.json(resultado.recordset)) 
	//.then(resultado => console.log(resultado.recordset)) 
	.catch(erro => resposta.json(erro)); 
}

//ROTAS USUARIO

rota.get('/usuario', (requisicao, resposta) => {
	execSQL('SELECT * FROM UsuarioSol', resposta);
})

rota.get('/usuario/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM UsuarioSol WHERE CODIGO = ${codigo}`, resposta);
})


rota.get('/usuario_email/:email', (requisicao, resposta) => {
	const email = requisicao.params.email;
	execSQL(`SELECT * FROM UsuarioSol WHERE EMAIL = '${email}'`, resposta);
})

rota.delete('/delete_usuario/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE UsuarioSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_usuario/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const nome = requisicao.body.nome;
	const senha = requisicao.body.senha;
	const telefone = requisicao.body.telefone;
	const data = requisicao.body.data;
	const cpf = requisicao.body.cpf;
	execSQL(`UPDATE UsuarioSol SET nome = '${nome}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE UsuarioSol SET senha = '${senha}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE UsuarioSol SET telefone = '${telefone}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE UsuarioSol SET data = '${data}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE UsuarioSol SET cpf = '${cpf}' WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_usuario', (requisicao, resposta) => {
	const email = requisicao.body.email;
	const nome = requisicao.body.nome;
	const senha = requisicao.body.senha;
	const codEmpresa = parseInt(requisicao.body.codEmpresa);
	const telefone = requisicao.body.telefone;
	const data = requisicao.body.data;
	const cpf = requisicao.body.cpf;
	execSQL(`INSERT INTO UsuarioSol VALUES('${email}', '${nome}', '${senha}', ${codEmpresa}, '${telefone}', '${data}', '${cpf}')`, resposta);
})

//ROTAS CLIENTE

rota.get('/cliente/:codEmpresa', (requisicao, resposta) => {
	const codEmpresa = parseInt(requisicao.params.codEmpresa);
	execSQL(`SELECT * FROM ClienteSol WHERE CODEMPRESA = ${codEmpresa}`, resposta);
})

rota.get('/cliente_email/:email', (requisicao, resposta) => {
	const email = requisicao.params.email;
	execSQL(`SELECT * FROM ClienteSol WHERE EMAIL = '${email}'`, resposta);
})

rota.delete('/delete_cliente/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE ClienteSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_cliente/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const email = requisicao.body.email;
	const nome = requisicao.body.nome;
	const telefone = requisicao.body.telefone;
	const data = requisicao.body.data;
	const cpf = requisicao.body.cpf;
	execSQL(`UPDATE ClienteSol SET email = '${email}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE ClienteSol SET nome = '${nome}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE ClienteSol SET telefone = '${telefone}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE ClienteSol SET data = '${data}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE ClienteSol SET cpf = '${cpf}' WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_cliente', (requisicao, resposta) => {
	const email = requisicao.body.email;
	const nome = requisicao.body.nome;
	const telefone = requisicao.body.telefone;
	const data = requisicao.body.data;
	const cpf = requisicao.body.cpf;
	const codEmpresa = requisicao.body.codEmpresa;
	const comando = `INSERT INTO ClienteSol VALUES('${email}', '${nome}', '${telefone}', '${data}', '${cpf}', ${codEmpresa})`;
	execSQL(comando, resposta);
})

//ROTAS EMPRESA

rota.get('/empresa', (requisicao, resposta) => {
	execSQL('SELECT * FROM EmpresaSol', resposta);
})

rota.get('/empresa/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM EmpresaSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_empresa', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const cnpj = requisicao.body.cnpj;
	execSQL(`INSERT INTO EmpresaSol VALUES('${nome}', '${cnpj}')`, resposta);
})

//ROTAS MODULO

rota.get('/modulo', (requisicao, resposta) => {
	execSQL(`SELECT * FROM ModuloSol`, resposta);
})

rota.get('/modulo/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM ModuloSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.delete('/delete_modulo/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE ModuloSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_modulo/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const preco = requisicao.body.preco;
	execSQL(`UPDATE ModuloSol SET preco = ${preco} WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_modulo', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const preco = requisicao.body.preco;
	const descricao = requisicao.body.descricao;
	const altura = requisicao.body.altura;
	const largura = requisicao.body.largura;
	const profundidade = requisicao.body.profundidade;
	const peso = requisicao.body.peso;
	const voltagem = requisicao.body.voltagem;
	execSQL(`INSERT INTO ModuloSol VALUES('${nome}', ${preco}, '${descricao}', ${altura}, ${largura}, ${profundidade}, ${peso}, ${voltagem})`, resposta);
})

//ROTAS INVERSOR

rota.get('/inversor', (requisicao, resposta) => {
	execSQL(`SELECT * FROM InversorSol`, resposta);
})

rota.get('/inversor/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM InversorSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.delete('/delete_inversor/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE InversorSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_inversor/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const preco = requisicao.body.preco;
	execSQL(`UPDATE InversorSol SET preco = ${preco} WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_inversor', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const preco = requisicao.body.preco;
	const descricao = requisicao.body.descricao;
	const altura = requisicao.body.altura;
	const largura = requisicao.body.largura;
	const profundidade = requisicao.body.profundidade;
	const peso = requisicao.body.peso;
	const eficienciaMaxima = requisicao.body.eficienciaMaxima;
	execSQL(`INSERT INTO InversorSol VALUES('${nome}', ${preco}, '${descricao}', ${altura}, ${largura}, ${profundidade}, ${peso}, ${eficienciaMaxima})`, resposta);
})

//ROTAS STRINGBOX

rota.get('/stringbox', (requisicao, resposta) => {
	execSQL(`SELECT * FROM InversorSol`, resposta);
})

rota.get('/stringbox/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM StringBoxSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.delete('/delete_stringbox/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE StringBoxSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_stringbox/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const preco = requisicao.body.preco;
	execSQL(`UPDATE StringBoxSol SET preco = ${preco} WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_stringbox', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const preco = requisicao.body.preco;
	const descricao = requisicao.body.descricao;
	const tipo = requisicao.body.tipo;
	const numeroPolos = requisicao.body.numeroPolos;
	const tensaoMaxima = requisicao.body.tensaoMaxima;
	const correnteNominal = requisicao.body.correnteNominal;
	execSQL(`INSERT INTO StringBoxSol VALUES('${nome}', ${preco}, '${descricao}', '${tipo}', ${numeroPolos}, ${tensaoMaxima}, ${correnteNominal})`, resposta);
})

//ROTAS FIXACAO

rota.get('/fixacao', (requisicao, resposta) => {
	execSQL(`SELECT * FROM FixacaoSol`, resposta);
})

rota.get('/fixacao/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM FixacaoSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.delete('/delete_fixacao/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE FixacaoSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_fixacao/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const preco = requisicao.body.preco;
	execSQL(`UPDATE FixacaoSol SET preco = ${preco} WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_fixacao', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const preco = requisicao.body.preco;
	const descricao = requisicao.body.descricao;
	execSQL(`INSERT INTO FixacaoSol VALUES('${nome}', ${preco}, '${descricao}')`, resposta);
})

//ROTAS BOMBASOLAR

rota.get('/bombasolar', (requisicao, resposta) => {
	execSQL(`SELECT * FROM BombaSolarSol`, resposta);
})

rota.get('/bombasolar/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM BombaSolarSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.delete('/delete_bombasolar/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE BombaSolarSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_bombasolar/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const preco = requisicao.body.preco;
	execSQL(`UPDATE BombaSolarSol SET preco = ${preco} WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_bombasolar', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const preco = requisicao.body.preco;
	const descricao = requisicao.body.descricao;
	const altura = requisicao.body.altura;
	const largura = requisicao.body.largura;
	const profundidade = requisicao.body.profundidade;
	const peso = requisicao.body.peso;
	const tensaoAlimentacao = requisicao.body.tensaoAlimentacao;
	const temperaturaMaxima = requisicao.body.temperaturaMaxima;
	const alturaMaxima = requisicao.body.alturaMaxima;
	const bombeamentoMaximoDiario = requisicao.body.bombeamentoMaximoDiario;
	const diametroTubo = requisicao.body.diametroTubo;
	execSQL(`INSERT INTO BombaSolarSol VALUES('${nome}', ${preco}, '${descricao}', ${altura}, ${largura}, ${profundidade}, ${peso}, ${tensaoAlimentacao}, ${temperaturaMaxima}, ${alturaMaxima}, ${bombeamentoMaximoDiario}, '${diametroTubo}')`, resposta);
})

//ROTAS CABOSOL

rota.get('/cabo', (requisicao, resposta) => {
	execSQL(`SELECT * FROM CaboSol`, resposta);
})

rota.get('/cabo/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM CaboSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.delete('/delete_cabo/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE CaboSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_cabo/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const preco = requisicao.body.preco;
	execSQL(`UPDATE CaboSol SET preco = ${preco} WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_cabo', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const preco = requisicao.body.preco;
	const descricao = requisicao.body.descricao;
	const comprimento = requisicao.body.comprimento;
	const diametro = requisicao.body.diametro;
	const conducao = requisicao.body.conducao;
	execSQL(`INSERT INTO CaboSol VALUES('${nome}', ${preco}, '${descricao}', ${comprimento}, ${diametro}, '${conducao}')`, resposta);
})