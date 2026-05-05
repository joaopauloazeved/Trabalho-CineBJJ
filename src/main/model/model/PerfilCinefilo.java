package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import exception.PesoInvalidoException;
import exception.DuracaoInvalidaException;

import model.enums.ClassificacaoEtaria;
import model.enums.Genero;
import model.enums.Idioma;

	public class PerfilCinefilo {

    	private final Map<Genero, Double> pesosGenero;
    	private final int duracaoMin;
    	private final int duracaoMax;
    	private final ClassificacaoEtaria classificacaoMaxima;
    	private final Set<Idioma> idiomasAceitos;
    	private final Set<String> filmesAssistidos;
    	private final Map<String, Integer> notas;

    	public PerfilCinefilo(Map<Genero, Double> pesosGenero,
                          int duracaoMin,
                          int duracaoMax,
                          ClassificacaoEtaria classificacaoMaxima,
                          Set<Idioma> idiomasAceitos) {

        if (duracaoMin > duracaoMax) {
        	 throw new DuracaoInvalidaException("Duração mínima maior que máxima");
        }

        this.pesosGenero = new HashMap<>();
        for (Map.Entry<Genero, Double> e : pesosGenero.entrySet()) {
            double p = e.getValue();
            if (p < 0.0 || p > 1.0) {
            throw new PesoInvalidoException("Peso inválido");
            }
            this.pesosGenero.put(e.getKey(), p);
        }

        this.duracaoMin = duracaoMin;
        this.duracaoMax = duracaoMax;
        this.classificacaoMaxima = classificacaoMaxima;
        this.idiomasAceitos = new HashSet<>(idiomasAceitos);
        this.filmesAssistidos = new HashSet<>();
        this.notas = new HashMap<>();
    }

    public void marcarAssistido(String id) {
        filmesAssistidos.add(id);
    }

    public boolean jaAssistido(String id) {
        return filmesAssistidos.contains(id);
    }

    public double getPesoGenero(Genero g) {
        return pesosGenero.getOrDefault(g, 0.0);
    }

    public Set<Idioma> getIdiomasAceitos() {
        return idiomasAceitos;
    }

    public ClassificacaoEtaria getClassificacaoMaxima() {
        return classificacaoMaxima;
    }

    public int getDuracaoMin() { return duracaoMin; }
    public int getDuracaoMax() { return duracaoMax; }

    public void adicionarNota(String id, int nota) {
        if (nota < 1 || nota > 5) 
        throw new PesoInvalidoException("Nota inválida");
        notas.put(id, nota);
    }

    public Map<String, Integer> getNotas() {
        return notas;
    }
}