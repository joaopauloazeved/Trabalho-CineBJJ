package service;

import enums.Genero;
import model.Filme;
import model.PerfilCinefilo;

public class CalculadoraScore {

    private static final double PESO_GENERO = 0.50;
    private static final double PESO_DURACAO = 0.20;
    private static final double PESO_POPULARIDADE = 0.15;
    private static final double PESO_AFINIDADE = 0.15;

    public double calcular(Filme filme, PerfilCinefilo perfil) {

        double scoreGenero = calcularGenero(filme, perfil);
        double scoreDuracao = calcularDuracao(filme, perfil);
        double scorePopularidade = filme.getPopularidade();
        double scoreAfinidade = calcularAfinidade(filme, perfil);

        double scoreFinal =
                (scoreGenero * PESO_GENERO) +
                (scoreDuracao * PESO_DURACAO) +
                (scorePopularidade * PESO_POPULARIDADE) +
                (scoreAfinidade * PESO_AFINIDADE);

        return Math.min(100, Math.max(0, scoreFinal));
    }

    private double calcularGenero(Filme filme, PerfilCinefilo perfil) {

        double soma = 0;

        for (Genero g : filme.getGeneros()) {
            soma += perfil.getPesoGenero(g);
        }

        return (soma / filme.getGeneros().size()) * 100;
    }

    private double calcularDuracao(Filme filme, PerfilCinefilo perfil) {

        int duracao = filme.getDuracao();

        if (duracao >= perfil.getDuracaoMin() && duracao <= perfil.getDuracaoMax()) {
            return 100;
        }

        int distancia;

        if (duracao < perfil.getDuracaoMin()) {
            distancia = perfil.getDuracaoMin() - duracao;
        } else {
            distancia = duracao - perfil.getDuracaoMax();
        }

        double penalidade = distancia * 2;

        return Math.max(0, 100 - penalidade);
    }

    private double calcularAfinidade(Filme filme, PerfilCinefilo perfil) {

        if (perfil.getNotas().isEmpty()) return 0;

        double soma = 0;
        int count = 0;

        for (Integer nota : perfil.getNotas().values()) {
            soma += nota;
            count++;
        }

        return (soma / count) * 20; 
    }
}