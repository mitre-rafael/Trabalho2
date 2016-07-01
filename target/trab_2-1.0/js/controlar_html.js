$(document).ready(function(){
    $("#mudarPraCatalogacao").click(function(){
        $("#idCatalogacao").fadeIn();
        $("#idBusca").fadeOut("fast");
        document.getElementById('idpatrimonio3').value = "";
        document.getElementById('idtitulo3').value = "";
        document.getElementById('idautoria3').value = "";
        document.getElementById('idveiculo3').value = "";
        document.getElementById('iddatapublicacao3').value = "";
        document.getElementById('idpalchave3').value = "";    
    });
    
    $("#mudarPraBusca").click(function(){
        $("#idCatalogacao").fadeOut("fast");
        $("#idBusca").fadeIn();
        document.getElementById('idpatrimonio2').value = "";
        document.getElementById('idtitulo2').value = "";
        document.getElementById('idautoria2').value = "";
        document.getElementById('idveiculo2').value = "";
        document.getElementById('iddatapublicacao21').value = "";
        document.getElementById('iddatapublicacao22').value = "";
        document.getElementById('idpalchave2').value = "";  
    });
   
});
