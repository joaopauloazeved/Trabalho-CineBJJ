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

    public List<Filme> filtrarPorDecada(List<Filme> filmes, int decada) {

        List<Filme> resultado = new ArrayList<>();

        int inicio = decada;
        int fim = decada + 9;

        for (Filme filme : filmes) {

            int ano = filme.getAnoLancamento();

            if (ano >= inicio && ano <= fim) {
                resultado.add(filme);
            }
        }

        return resultado;
    }
}