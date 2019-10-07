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

//ROTAS USUARIOSOL

rota.get('/usuarios', (requisicao, resposta) => {
	execSQL('SELECT * FROM UsuarioSol', resposta);
})

rota.get('/usuarios/:id', (requisicao, resposta) => {
	const id = parseInt(requisicao.params.id);
	execSQL(`SELECT * FROM UsuarioSol WHERE CODIGO = ${id}`, resposta);
})

rota.delete('/usuarios/:id', (requisicao, resposta) => {
	const id = parseInt(requisicao.params.id);
	execSQL(`DELETE UsuarioSol WHERE CODIGO = ${id}`, resposta);
})

rota.patch('/usuarios/:id/:email/:senha', (requisicao, resposta) => {
	const id = parseInt(requisicao.params.id);
	const email = requisicao.params.email;
	const senha = requisicao.params.senha;
	execSQL(`UPDATE UsuarioSol SET email = ${email} WHERE CODIGO = ${id}`, resposta);
	execSQL(`UPDATE UsuarioSol SET nome = ${nome} WHERE CODIGO = ${id}`, resposta);
})

rota.post('/usuarios/:id/:email/:nome/:senha/:codEmpresa', (requisicao, resposta) => {
	const id = parseInt(requisicao.params.id);
	const email = requisicao.params.email;
	const nome = requisicao.params.nome;
	const senha = requisicao.params.senha;
	const codEmpresa = parseInt(requisicao.params.codEmpresa);
	execSQL(`INSERT INTO UsuarioSol VALUES(${id},'${email}', '${nome}', '${senha}', ${codEmpresa})`, resposta);
})
