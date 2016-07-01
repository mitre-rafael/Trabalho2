window.onload = iniciar;

var controle;

var dialogo;

function iniciar() {
 
    document.getElementById('idLimparBusca').addEventListener("click",
        function() {
            limparBusca();
        }
    );
        
    document.getElementById('idLimparCat').addEventListener("click",
        function() {
            limparCatalogacao();  
        }
    );
    
    document.getElementById('idExcluir').addEventListener("click",
        function(){
            var sendData = {
            "patrimonio": document.getElementById('idpatrimonio3').value};
        
            fazerPedidoAJAXDeletar(sendData,resultadoServidor);  
            limparCatalogacao();
        }
    );
    
    document.getElementById('idAdicionarNovo').addEventListener("click",
        function(){
            if (document.getElementById('idtitulo3').value===""){
                alert("O titulo é obrigatório!!")
            }
            else if ((document.getElementById('idautoria3').value==="") && (document.getElementById('idveiculo3').value==="")){
                alert("1");
                var sendData = {
                "patrimonio": "0",
                "titulo" : document.getElementById('idtitulo3').value,
                "autoria": " -",
                "veiculo" : " -",
                };
            fazerPedidoAJAXInserir(sendData,resultadoServidor);
            }
            else if (document.getElementById('idautoria3').value===""){
                alert("2");
                var sendData = {
                "patrimonio": "0",
                "titulo" : document.getElementById('idtitulo3').value,
                "autoria": " -",
                "veiculo" : document.getElementById('idveiculo3').value
                };
            fazerPedidoAJAXInserir(sendData,resultadoServidor);
            }
            else if (document.getElementById('idveiculo3').value===""){
                alert("3");
                
                var sendData = {
                "patrimonio": "0",
                "titulo" : document.getElementById('idtitulo3').value,
                "autoria": " -",
                "veiculo" : document.getElementById('idveiculo3').value
                };
            fazerPedidoAJAXInserir(sendData,resultadoServidor);
            }
            else{
                var sendData = {
                "patrimonio": "0",
                "titulo" : document.getElementById('idtitulo3').value,
                "autoria" : document.getElementById('idautoria3').value,
                "veiculo" : document.getElementById('idveiculo3').value
            };
            fazerPedidoAJAXInserir(sendData,resultadoServidor);  
            }   
        }
    );
    
    document.getElementById('idEditar').addEventListener("click",
        function () {
            if (estadoEditando) {
                estadoEditando = false;
            } else {
                estadoEditando = true;
            }
            
           mudarAtributosEditando(estadoEditando); 
           limparCatalogacao();
        }
    );
    
    document.getElementById('idApresentarTudo').addEventListener("click",
        function(){
            var sendData = {
            "operacao" : "apresentarTudo"
            };
            fazerPedidoAJAXApresentarTudo(sendData, apresentarResposta);
            limparBusca();
        }
    );
    
    document.getElementById('idBuscar').addEventListener("click",
        function(){

            if(document.getElementById('idpatrimonio2').value==""){
            var sendData = {
                "patrimonio" : "0",    
                "titulo" : document.getElementById('idtitulo2').value,
                "autoria" : document.getElementById('idautoria2').value,
                "veiculo" : document.getElementById('idveiculo2').value }
                }
            else{
            var sendData = {
                "patrimonio" : document.getElementById('idpatrimonio2').value,    
                "titulo" : document.getElementById('idtitulo2').value,
                "autoria" : document.getElementById('idautoria2').value,
                "veiculo" : document.getElementById('idveiculo2').value
                };
            }
            fazerPedidoAJAXBuscar(sendData, apresentarResposta);
        }
    );
    
    document.getElementById("idBuscarEdicao").addEventListener("click",
        function(){

            var sendData = {
                "patrimonio": document.getElementById('idpatrimonio3').value
            };
           
            fazerPedidoAJAXBuscaEdicao(sendData,popularCamposComRespostaJSON);
        }
    );
    
    document.getElementById("idItemAnterior").addEventListener("click",
        function(){
            var sendData ={ 
                "patrimonio": (document.getElementById('idpatrimonio3').value-1).toString()
            };
            document.getElementById("idpatrimonio3").value = document.getElementById("idpatrimonio3").value -1;
            fazerPedidoAJAXBuscaEdicao(sendData,popularCamposComRespostaJSON);
        }
    );
    
     document.getElementById("idItemProximo").addEventListener("click",
        function(){
            var sendData ={ 
                "patrimonio": (parseInt(document.getElementById('idpatrimonio3').value)+1).toString()
            };
            document.getElementById("idpatrimonio3").value = (parseInt(document.getElementById('idpatrimonio3').value)+1);
            fazerPedidoAJAXBuscaEdicao(sendData,popularCamposComRespostaJSON);
        }
    );
    
    document.getElementById("idSalvarAtual").addEventListener("click",
        function(){
            if ((document.getElementById('idtitulo3').value==="")||
                    (document.getElementById('idautoria3').value==="")||
                    (document.getElementById('idveiculo3').value==="")){
                alert("O formulário está vazio!")}
            else{
            var sendData ={ 
            "patrimonio" : document.getElementById('idpatrimonio3').value,    
            "titulo" : document.getElementById('idtitulo3').value,
            "autoria" : document.getElementById('idautoria3').value,
            "veiculo" : document.getElementById('idveiculo3').value
            };
            fazerPedidoAJAXSalvarModificacao(sendData,resultadoServidor);
            }
        }
    );
    
    
    function limparBusca(){
            document.getElementById('idpatrimonio2').value = "";
            document.getElementById('idtitulo2').value = "";
            document.getElementById('idautoria2').value = "";
            document.getElementById('idveiculo2').value = "";
            document.getElementById('iddatapublicacao21').value = "";
            document.getElementById('iddatapublicacao22').value = "";
            document.getElementById('idpalchave2').value = "";    
    }
    
    function limparCatalogacao(){
            document.getElementById('idpatrimonio3').value = "";
            document.getElementById('idtitulo3').value = "";
            document.getElementById('idautoria3').value = "";
            document.getElementById('idveiculo3').value = "";
            document.getElementById('iddatapublicacao3').value = "";
            document.getElementById('idpalchave3').value = "";        
    }
    
    function fazerPedidoAJAXDeletar(objetoJSON,resposta){
        var stringJSON = JSON.stringify(objetoJSON);
        var objPedidoAJAX = new XMLHttpRequest();
        objPedidoAJAX.open("POST", "ServletExclusao");
        objPedidoAJAX.setRequestHeader("Content-Type","application/json;charset=UTF-8");
        // Prepara recebimento da resposta: tipo da resposta JSON!
        objPedidoAJAX.responseType = 'json';
        objPedidoAJAX.onreadystatechange =
            function() {
                if(objPedidoAJAX.readyState===4 && objPedidoAJAX.status===200){
                    // A resposta 'response' já é avaliada para json!
                    // Ao contrario da resposta responseText.
                    resposta(objPedidoAJAX.response);
                  //  alert(objJSONresp.resultado);
                };
            };
        // Envio do pedido
        objPedidoAJAX.send(stringJSON);
    };
    
    function resultadoServidor(objJSONresp){
        alert(objJSONresp.resultado);
    };

    function fazerPedidoAJAXInserir(objetoJSON,resposta){
        var stringJSON = JSON.stringify(objetoJSON);
        var objPedidoAJAX = new XMLHttpRequest();
        objPedidoAJAX.open("POST", "servletCatalogacao");
        objPedidoAJAX.setRequestHeader("Content-Type","application/json;charset=UTF-8");
        // Prepara recebimento da resposta: tipo da resposta JSON!
        objPedidoAJAX.responseType = 'json';
        objPedidoAJAX.onreadystatechange =
            function() {
                if(objPedidoAJAX.readyState===4 && objPedidoAJAX.status===200){
                    // A resposta 'response' já é avaliada para json!
                    // Ao contrario da resposta responseText.
                    resposta(objPedidoAJAX.response);
                  //  alert(objJSONresp.resultado);
                };
            };
        // Envio do pedido
        objPedidoAJAX.send(stringJSON);
    };
    
    var estadoEditando = false;
    function mudarAtributosEditando(estado) {
        
        if (estado) {
            document.getElementById('idpatrimonio3').removeAttribute("readonly");
            document.getElementById('idMsgDialogo3').innerHTML= "<h5>Para editar, busque um patrimônio!</h5>";
            document.getElementById('idBuscarEdicao').setAttribute("type","button");
            document.getElementById('idItemAnterior').setAttribute("type","button");
            document.getElementById('idItemProximo').setAttribute("type","button");
            document.getElementById('idSalvarAtual').setAttribute("type","button");
            document.getElementById('idExcluir').setAttribute("type","button");
            document.getElementById('idAdicionarNovo').setAttribute("type","hidden");
            document.getElementById('idEditar').setAttribute("value","Ir para Inserção");
        } else {
            document.getElementById('idMsgDialogo3').innerHTML= "<h5></h5>";
            document.getElementById('idpatrimonio3').setAttribute("readonly",true);
            document.getElementById('idBuscarEdicao').setAttribute("type","hidden");
            document.getElementById('idItemAnterior').setAttribute("type","hidden");
            document.getElementById('idItemProximo').setAttribute("type","hidden");
            document.getElementById('idSalvarAtual').setAttribute("type","hidden");
            document.getElementById('idExcluir').setAttribute("type","hidden");
            document.getElementById('idAdicionarNovo').setAttribute("type","button");
            document.getElementById('idEditar').setAttribute("value","Ir para Edição");
            

            
          
        }
    }
    
    function apresentarResposta(objJsonresp){
        
        return "<tr>\n\
                Patrimono:"+ objJsonresp.patrimonio +"<br>\n\
                Titulo: "+ objJsonresp.titulo+" <br>\n\
                Autoria:"+objJsonresp.autoria+"<br>\n\
                Veiculo: "+objJsonresp.veiculo+"<br><hr>\n\
                </tr>";
    }
        
    function fazerPedidoAJAXApresentarTudo(objetoJSON,resposta){
        var stringJSON = JSON.stringify(objetoJSON);
        var objPedidoAJAX = new XMLHttpRequest();
        var resultado = "<table border=\"1\" style=\"border:2px solid black;border-collapse:collapse;\">";
        
        objPedidoAJAX.open("POST", "ServletApresentarTudo");
        objPedidoAJAX.setRequestHeader("Content-Type","application/json;charset=UTF-8");
        // Prepara recebimento da resposta: tipo da resposta JSON!
        objPedidoAJAX.responseType = 'json';
        objPedidoAJAX.onreadystatechange =
            function() {
                if(objPedidoAJAX.readyState===4 && objPedidoAJAX.status===200){
                    // A resposta 'response' já é avaliada para json!
                    // Ao contrario da resposta responseText.
                    for (var i = 0; i < objPedidoAJAX.response.length; i++){
                        
                        resultado += resposta(objPedidoAJAX.response[i],i);
                        
                    }
                };
            document.getElementById("idNroRows").innerHTML = "<h5> " + objPedidoAJAX.response.length +" ";
            resultado+="</table>";
            document.getElementById("idTabelaResultados").innerHTML = resultado;   

            };
        // Envio do pedido
        objPedidoAJAX.send(stringJSON);
    };
       
    function fazerPedidoAJAXBuscar(objetoJSON,resposta){
        var stringJSON = JSON.stringify(objetoJSON);
        var objPedidoAJAX = new XMLHttpRequest();
        var resultado = "<table border=\"1\" style=\"border:2px solid black;border-collapse:collapse;\">";

        objPedidoAJAX.open("POST", "ServletBuscar");
        objPedidoAJAX.setRequestHeader("Content-Type","application/json;charset=UTF-8");
        // Prepara recebimento da resposta: tipo da resposta JSON!
        objPedidoAJAX.responseType = 'json';

        objPedidoAJAX.onreadystatechange =
            function() {
                if(objPedidoAJAX.readyState===4 && objPedidoAJAX.status===200){
                    // A resposta 'response' já é avaliada para json!
                    // Ao contrario da resposta responseText.
                    if (objPedidoAJAX.response[0].patrimonio=="0"){ document.getElementById("idNroRows").innerHTML = "<h5> 0, não encontrou nada :-( ";}
                    else{
                    for (var i = 0; i < objPedidoAJAX.response.length; i++){
                        resultado += resposta(objPedidoAJAX.response[i],i);
                    }
                    document.getElementById("idNroRows").innerHTML = "<h5> " + objPedidoAJAX.response.length + " ";
                    }
                }
            resultado+="</table>";
            document.getElementById("idTabelaResultados").innerHTML = resultado;   
            };
        // Envio do pedido
        objPedidoAJAX.send(stringJSON);
    };

    function fazerPedidoAJAXBuscaEdicao (objetoJSON,funcPopularPagina){
        var stringJSON = JSON.stringify(objetoJSON);
        var objPedidoAJAX = new XMLHttpRequest();
        objPedidoAJAX.open("POST", "ServletBuscar");
        objPedidoAJAX.setRequestHeader("Content-Type","application/json;charset=UTF-8");
        // Prepara recebimento da resposta: tipo da resposta JSON!
        objPedidoAJAX.responseType = 'json';
        objPedidoAJAX.onreadystatechange =
            function() {
                if(objPedidoAJAX.readyState===4 && objPedidoAJAX.status===200){
                    // A resposta 'response' já é avaliada para json!
                    // Ao contrario da resposta responseText.
                    funcPopularPagina(objPedidoAJAX.response[0]);
                };
            };
        // Envio do pedido
        objPedidoAJAX.send(stringJSON);
    }
    
    function popularCamposComRespostaJSON(objJSONresp){
        if(objJSONresp.patrimonio!=="0"){
            document.getElementById('idpatrimonio3').value = objJSONresp.patrimonio;
            document.getElementById('idtitulo3').value = objJSONresp.titulo;
            document.getElementById('idautoria3').value = objJSONresp.autoria;
            document.getElementById('idveiculo3').value = objJSONresp.veiculo;
        }
        else{
            document.getElementById('idtitulo3').value = ""
            document.getElementById('idautoria3').value = ""
            document.getElementById('idveiculo3').value = ""
        }
    };
    
    function fazerPedidoAJAXSalvarModificacao (objetoJSON,resposta){
        var stringJSON = JSON.stringify(objetoJSON);
        var objPedidoAJAX = new XMLHttpRequest();
        objPedidoAJAX.open("POST", "ServletEdicao");
        objPedidoAJAX.setRequestHeader("Content-Type","application/json;charset=UTF-8");
        // Prepara recebimento da resposta: tipo da resposta JSON!
        objPedidoAJAX.responseType = 'json';
        objPedidoAJAX.onreadystatechange =
            function() {
                if(objPedidoAJAX.readyState===4 && objPedidoAJAX.status===200){
                    // A resposta 'response' já é avaliada para json!
                    // Ao contrario da resposta responseText.
                    resposta(objPedidoAJAX.response);
                };
            };
        // Envio do pedido
        objPedidoAJAX.send(stringJSON);
    };
}