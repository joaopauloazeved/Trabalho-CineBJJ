package service;

import java.util.ArrayList;
import java.util.List;

import model.Filme;

public class FiltroCapsulaTempo {

    public List<Filme> filtrarPorAno(List<Filme> filmes, int ano) {

        List<Filme> resultado = new ArrayList<>();

        for (Filme filme : filmes) {

            if (filme.getAnoLancamento() == ano) {
                resultado.add(filme);
            }
        }

        return resultado;
    }
}