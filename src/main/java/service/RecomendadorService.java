package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.Filme;
import model.PerfilCinefilo;
import model.Recomendacao;
import model.Usuario;
import util.GeradorAleatorio;

public class RecomendadorService {

    private final CatalogoFilmesAPI catalogo;
    private final HistoricoUsuarioRepository historico;
    private final NotificadorPush notificador;
    private final GeradorAleatorio gerador;
    private final CalculadoraScore calculadora;
    private final FiltroFilmes filtro;
    private final FiltroCapsulaTempo filtroCapsula;

    public RecomendadorService(CatalogoFilmesAPI catalogo,
                               HistoricoUsuarioRepository historico,
                               NotificadorPush notificador,
                               GeradorAleatorio gerador,
                               CalculadoraScore calculadora,
                               FiltroFilmes filtro,
                               FiltroCapsulaTempo filtroCapsula) {
        this.catalogo = catalogo;
        this.historico = historico;
        this.notificador = notificador;
        this.gerador = gerador;
        this.calculadora = calculadora;
        this.filtro = filtro;
        this.filtroCapsula = filtroCapsula;
    }

    public List<Recomendacao> recomendar(Usuario usuario, int topN) {
        try {
            List<Filme> filmesDoCatalogo = catalogo.buscarTodos();
            PerfilCinefilo perfil = usuario.getPerfil();

            List<Filme> filmesFiltrados = filtro.filtrar(filmesDoCatalogo, perfil);
            List<Recomendacao> recomendacoes = new ArrayList<>();

            for (Filme filme : filmesFiltrados) {
                double score = calculadora.calcular(filme, perfil);
                recomendacoes.add(
                    new Recomendacao(filme, score, "Baseado nas suas preferências")
                );
            }

            recomendacoes.sort(
                Comparator.comparing(Recomendacao::getScore).reversed()
                    .thenComparing(r -> r.getFilme().getPopularidade(), Comparator.reverseOrder())
                    .thenComparing(r -> gerador.sortearInteiro(0, 100))
            );

            List<Recomendacao> resultado;

            if (recomendacoes.size() > topN) {
                resultado = new ArrayList<>(recomendacoes.subList(0, topN));
            } else {
                resultado = recomendacoes;
            }

            historico.registrarRecomendacao(usuario, resultado);

            if (usuario.isNotificacoesAtivas()) {
                notificador.enviar(usuario, "Sua recomendação do dia está pronta!");
            }

            return resultado;

        } catch (Exception e) {
            return List.of();
        }
    }

    public Recomendacao recomendarAleatorio(Usuario usuario) {
        List<Filme> filmesDoCatalogo = catalogo.buscarTodos();
        PerfilCinefilo perfil = usuario.getPerfil();

        List<Filme> filmesFiltrados = filtro.filtrar(filmesDoCatalogo, perfil);

        if (filmesFiltrados.isEmpty()) {
            return null;
        }

        int indice = gerador.sortearInteiro(0, filmesFiltrados.size() - 1);
        Filme escolhido = filmesFiltrados.get(indice);

        double score = calculadora.calcular(escolhido, perfil);

        return new Recomendacao(escolhido, score, "Modo surpresa");
    }
    
    public List<Recomendacao> recomendarPorAno(Usuario usuario,
            int ano,
            int topN) {

    	List<Filme> filmesDoCatalogo = catalogo.buscarTodos();

    		PerfilCinefilo perfil = usuario.getPerfil();

		List<Filme> filmesFiltrados =
			filtro.filtrar(filmesDoCatalogo, perfil);
			
		List<Filme> filmesDoAno =
			filtroCapsula.filtrarPorAno(filmesFiltrados, ano);
			
		List<Recomendacao> recomendacoes = new ArrayList<>();
			
		for (Filme filme : filmesDoAno) {
			double score = calculadora.calcular(filme, perfil);
			
			recomendacoes.add(
			new Recomendacao(filme,score,"Cápsula do Tempo: " + ano));
			}
			
			recomendacoes.sort(Comparator.comparing(Recomendacao::getScore).reversed().thenComparing(r -> r.getFilme().getAvaliacao(),Comparator.reverseOrder()).thenComparing(r -> r.getFilme().getPopularidade(),Comparator.reverseOrder()));
			
			if (recomendacoes.size() > topN) {
				return new ArrayList<>(recomendacoes.subList(0, topN));
			}
			
				return recomendacoes;
    }
}