package trabalho2.DTOs;

import java.io.Serializable;
import java.sql.Date;

public class ReferenciaBibliografica implements Serializable{
    private long patrimonio;
    private String titulo;
    private String autoria;
   // private String palChave;
    private String veiculo;
   // private String dataPublicacao;

    public long getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(long patrimonio) {
        this.patrimonio = patrimonio;
    }

  /*  public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }*/

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutoria() {
        return autoria;
    }

    public void setAutoria(String autoria) {
        this.autoria = autoria;
    }

  /*  public String getPalChave() {
        return palChave;
    }

    public void setPalChave(String palchave) {
        this.palChave = palchave;
    }*/


}
