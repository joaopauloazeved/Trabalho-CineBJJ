package service;

import java.util.ArrayList;
import java.util.List;

import model.Filme;
import model.PerfilCinefilo;
import model.enums.Genero;

public class FiltroFilmes {

    public List<Filme> filtrar(List<Filme> filmes, PerfilCinefilo perfil) {

        List<Filme> resultado = new ArrayList<>();

        for (Filme filme : filmes) {

            
            if (perfil.jaAssistido(filme.getId())) {
                continue;
            }

            
            if (filme.getClassificacao().getIdade() >
                perfil.getClassificacaoMaxima().getIdade()) {
                continue;
            }

            
            if (!perfil.getIdiomasAceitos().contains(filme.getIdioma())) {
                continue;
            }

            
            boolean temGeneroValido = false;

            for (Genero genero : filme.getGeneros()) {
                if (perfil.getPesoGenero(genero) > 0.0) {
                    temGeneroValido = true;
                    break;
                }
            }

            if (!temGeneroValido) {
                continue;
            }

            resultado.add(filme);
        }

        return resultado;
    }
}