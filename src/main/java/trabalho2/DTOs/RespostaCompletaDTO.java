/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho2.DTOs;

import java.util.ArrayList;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

/**
 *
 * @author rafael
 */
public class RespostaCompletaDTO {
    private boolean sucesso;
    private int numeroRegistros;

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }
    public ArrayList <ReferenciaBibliografica> listaResposta = new ArrayList<>();
    
    public Iterator getIterator(){
        return listaResposta.iterator();
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
    
    public void addResposta (ReferenciaBibliografica resposta){
        listaResposta.add(resposta);
    }
    
    public JsonObject toJSON(){
        JsonArrayBuilder jsonListaResposta = Json.createArrayBuilder();
        Iterator iterador = listaResposta.iterator();
        
        while(iterador.hasNext()){
            ReferenciaBibliografica resposta = (ReferenciaBibliografica) iterador.next();
            JsonObject jsonRespostaDTO = Json.createObjectBuilder()
                    .add("patrimonio",resposta.getPatrimonio())
                    .add("titulo", resposta.getTitulo())
                    .add("autoria",resposta.getAutoria())
                    .add("veiculo",resposta.getVeiculo())
                   // .add("palchave",resposta.getPalChave())
                  //  .add("datapublicacao", resposta.getDataPublicacao())
                    .build();
            jsonListaResposta.add(jsonRespostaDTO);
        }
        
        JsonObject jsonRespostaCompletaDTO = Json.createObjectBuilder()
                .add("sucesso", sucesso)
                .add("numeroRegistros", numeroRegistros)
                .add("arrayDeResposta",jsonListaResposta.build())
                .build();
        return jsonRespostaCompletaDTO;
    }
    
    @Override
    public String toString(){
        return toJSON().toString();
    }     
}
