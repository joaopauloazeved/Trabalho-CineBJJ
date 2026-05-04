package PerfilCinefilo;
import java.util.List;
import java.util.ArrayList;

public class PerfilCinefilo {
	private int pesoDeGenero;
    private int duracaoMin;
    private int duracaoMax;
    private int classificacao;
    private String idiomas;
    private List<String> historicoAssistidos;
    private int notas;

    
    public PerfilCinefilo() {
        this.historicoAssistidos = new ArrayList<>();
    }

    
    public int getPesoDeGenero() {
        return pesoDeGenero;
    }

    public void setPesoDeGenero(int pesoDeGenero) {
        this.pesoDeGenero = pesoDeGenero;
    }

    public int getDuracaoMin() {
        return duracaoMin;
    }

    public void setDuracaoMin(int duracaoMin) {
        this.duracaoMin = duracaoMin;
    }

    public int getDuracaoMax() {
        return duracaoMax;
    }

    public void setDuracaoMax(int duracaoMax) {
        this.duracaoMax = duracaoMax;
    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public List<String> getHistoricoAssistidos() {
        return historicoAssistidos;
    }

    public void setHistoricoAssistidos(List<String> historicoAssistidos) {
        this.historicoAssistidos = historicoAssistidos;
    }

    public int getNotas() {
        return notas;
    }

    public void setNotas(int notas) {
        this.notas = notas;
    }

}
