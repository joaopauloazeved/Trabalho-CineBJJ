package service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.Filme;
import model.PerfilCinefilo;
import model.Recomendacao;
import model.Usuario;

public class AniversarioFilmeService {

    private final CatalogoFilmesAPI catalogo;
    private final CalculadoraScore calculadora;
    private final FiltroFilmes filtro;

    public AniversarioFilmeService(
            CatalogoFilmesAPI catalogo,
            CalculadoraScore calculadora,
            FiltroFilmes filtro) {

        this.catalogo = catalogo;
        this.calculadora = calculadora;
        this.filtro = filtro;
    }

    public List<Recomendacao> recomendarFilmesEmAniversario(
            Usuario usuario,
            int minimoAnos,
            int topN) {

        int anoAtual = Year.now().getValue();

        List<Filme> catalogoCompleto =
                catalogo.buscarTodos();

        PerfilCinefilo perfil =
                usuario.getPerfil();

        List<Filme> filmesFiltrados =
                filtro.filtrar(catalogoCompleto, perfil);

        List<Recomendacao> recomendacoes =
                new ArrayList<>();

        for (Filme filme : filmesFiltrados) {

            int idadeFilme =
                    anoAtual - filme.getAnoLancamento();

            boolean aniversarioValido =
                    idadeFilme >= minimoAnos;

            boolean aniversarioEspecial =
                    idadeFilme % 5 == 0;

            boolean filmeBemAvaliado =
                    filme.getAvaliacao() >= 8.0;

            boolean filmePremiado =
                    filme.isPremiado();

            if (aniversarioValido
                    && aniversarioEspecial
                    && (filmeBemAvaliado || filmePremiado)) {

                double score =
                        calculadora.calcular(filme, perfil);

                score += filme.getAvaliacao();

                if (filmePremiado) {
                    score += 2.0;
                }

                recomendacoes.add(
                        new Recomendacao(
                                filme,
                                score,
                                "Aniversário de "
                                        + idadeFilme
                                        + " anos"
                        )
                );
            }
        }

        recomendacoes.sort(
                Comparator
                        .comparing(Recomendacao::getScore)
                        .reversed()
                        .thenComparing(
                                r -> r.getFilme().getAvaliacao(),
                                Comparator.reverseOrder()
                        )
                        .thenComparing(
                                r -> r.getFilme().getPopularidade(),
                                Comparator.reverseOrder()
                        )
        );

        if (recomendacoes.size() > topN) {

            return new ArrayList<>(
                    recomendacoes.subList(0, topN)
            );
        }

        return recomendacoes;
    }
}