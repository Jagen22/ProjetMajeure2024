import express from 'express';
import bodyParser from 'body-parser'
import {Server} from 'socket.io'
import {createServer} from 'node:http'

var app = express()
app.use(bodyParser.json())
server = createServer(app)
const ioServer = new Server(server)

function notifyClient(promptId,message){

    console.log("Notifying client for promptId : "+promptId+" with message : "+message)
    //ioServer.emit(promptId,message)
}

app.get('/',function(req,res){
    res.send("Up and Running !")
})

app.post('/notifyClient',function(req,res){
    notifyClient(req.body["promptId"],req.body["message"])
    res.send("OK")
})

ioServer.on('connection',(socket)=>{
    console.log('someone connected')
})

var server = app.listen(3000, function () {
    var host = server.address().address;
    var port = server.address().port;
    console.log('Socket API running on :', host, port);
    });