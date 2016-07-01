package trabalho2.DTOs;

public class DadosDaBusca {
    private String comando;
    private long patrimonio;
    private String titulo;
    private String autoria;
    private String veiculo;

    public long getPatrimonio() {
        return patrimonio;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public void setPatrimonio(long patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
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


}
