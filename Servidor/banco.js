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

/*app.use(function(req, res, next) { 
	res.header("Access-Control-Allow-Origin", "*"); 
	res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); 
	res.header("Access-Control-Allow-Methods", "GET, POST, HEAD, OPTIONS, PATCH, DELETE"); 
	next(); 
});*/

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

rota.get('/usuarios', (requisicao, resposta) => {
	execSQL('SELECT * FROM UsuarioSol', resposta);
})

rota.get('/usuarios/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`SELECT * FROM UsuarioSol WHERE CODIGO = ${codigo}`, resposta);
})


rota.get('/usuarios_email/:email', (requisicao, resposta) => {
	const email = requisicao.params.email;
	execSQL(`SELECT * FROM UsuarioSol WHERE EMAIL = '${email}'`, resposta);
})

rota.delete('/delete_usuario/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	execSQL(`DELETE UsuarioSol WHERE CODIGO = ${codigo}`, resposta);
})

rota.patch('/update_usuario/:codigo', (requisicao, resposta) => {
	const codigo = parseInt(requisicao.params.codigo);
	const email = requisicao.body.email;
	const senha = requisicao.body.senha;
	execSQL(`UPDATE UsuarioSol SET email = '${email}' WHERE CODIGO = ${codigo}`, resposta);
	execSQL(`UPDATE UsuarioSol SET nome = '${nome}' WHERE CODIGO = ${codigo}`, resposta);
})

rota.post('/insert_usuario', (requisicao, resposta) => {
	const email = requisicao.body.email;
	const nome = requisicao.body.nome;
	const senha = requisicao.body.senha;
	const codEmpresa = parseInt(requisicao.body.codEmpresa);
	execSQL(`INSERT INTO UsuarioSol VALUES('${email}', '${nome}', '${senha}', ${codEmpresa})`, resposta);
})

//ROTAS CLIENTE

rota.get('/clientes/:codEmpresa', (requisicao, resposta) => {
	const codEmpresa = parseInt(requisicao.params.codEmpresa);
	execSQL(`SELECT * FROM ClienteSol WHERE CODEMPRESA = ${codEmpresa}`, resposta);
})

rota.get('/clientes_email/:email', (requisicao, resposta) => {
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

rota.get('/empresas', (requisicao, resposta) => {
	execSQL('SELECT * FROM EmpresaSol', resposta);
})

rota.post('/insert_empresa', (requisicao, resposta) => {
	const nome = requisicao.body.nome;
	const cnpj = requisicao.body.cnpj;
	execSQL(`INSERT INTO EmpresaSol VALUES('${nome}', '${cnpj}')`, resposta);
})