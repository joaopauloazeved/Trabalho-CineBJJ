package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;
import model.enums.*;
import util.GeradorAleatorio;

class RecomendadorServiceTest {

    private CatalogoFilmesAPI catalogo;
    private HistoricoUsuarioRepository historico;
    private NotificadorPush notificador;
    private GeradorAleatorio gerador;

    private RecomendadorService service;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        catalogo = mock(CatalogoFilmesAPI.class);
        historico = mock(HistoricoUsuarioRepository.class);
        notificador = mock(NotificadorPush.class);
        gerador = mock(GeradorAleatorio.class);

        service = new RecomendadorService(
            catalogo,
            historico,
            notificador,
            gerador,
            new CalculadoraScore(),
            new FiltroFilmes()
        );

        Map<Genero, Double> pesos = new HashMap<>();
        pesos.put(Genero.ACAO, 1.0);
        pesos.put(Genero.DRAMA, 0.8);
        pesos.put(Genero.TERROR, 0.0);

        Set<Idioma> idiomas = new HashSet<>();
        idiomas.add(Idioma.INGLES);

        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos,
            90,
            150,
            ClassificacaoEtaria.DEZESSEIS,
            idiomas
        );

        usuario = new Usuario("Maria", 28, perfil, true);
    }

    @Test
    void deve_RetornarListaVazia_Quando_CatalogoEstaVazio() {
        when(catalogo.buscarTodos()).thenReturn(List.of());

        List<Recomendacao> resultado = service.recomendar(usuario, 5);

        assertTrue(resultado.isEmpty());
        verify(catalogo).buscarTodos();
        verify(historico).registrarRecomendacao(eq(usuario), anyList());
        verify(notificador).enviar(eq(usuario), anyString());
    }

    @Test
    void deve_RetornarTopN_Quando_CatalogoTemMaisFilmes() {
        when(catalogo.buscarTodos()).thenReturn(List.of(
            new Filme("F1", "Filme 1", 100, List.of(Genero.ACAO),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 70),
            new Filme("F2", "Filme 2", 100, List.of(Genero.DRAMA),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 80),
            new Filme("F3", "Filme 3", 100, List.of(Genero.ACAO),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 90)
        ));

        List<Recomendacao> resultado = service.recomendar(usuario, 2);

        assertEquals(2, resultado.size());
        verify(historico).registrarRecomendacao(eq(usuario), eq(resultado));
    }

    @Test
    void deve_OrdenarPorScoreDesc_Quando_Recomendar() {
        when(catalogo.buscarTodos()).thenReturn(List.of(
            new Filme("F1", "Baixo", 100, List.of(Genero.DRAMA),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 50),
            new Filme("F2", "Alto", 100, List.of(Genero.ACAO),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 90)
        ));

        List<Recomendacao> resultado = service.recomendar(usuario, 2);

        assertTrue(resultado.get(0).getScore() >= resultado.get(1).getScore());
    }

    @Test
    void deve_RetornarListaVazia_Quando_CatalogoLancaExcecao() {
        when(catalogo.buscarTodos()).thenThrow(new RuntimeException("API offline"));

        List<Recomendacao> resultado = service.recomendar(usuario, 5);

        assertTrue(resultado.isEmpty());
        verify(historico, never()).registrarRecomendacao(any(), anyList());
        verify(notificador, never()).enviar(any(), anyString());
    }

    @Test
    void deve_RetornarFilmeAleatorio_Quando_SurpreendaMe() {
        when(catalogo.buscarTodos()).thenReturn(List.of(
            new Filme("F1", "Filme 1", 100, List.of(Genero.ACAO),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 70),
            new Filme("F2", "Filme 2", 100, List.of(Genero.ACAO),
                ClassificacaoEtaria.DEZESSEIS, Idioma.INGLES, 80)
        ));

        when(gerador.sortearInteiro(0, 1)).thenReturn(1);

        Recomendacao resultado = service.recomendarAleatorio(usuario);

        assertNotNull(resultado);
        assertEquals("F2", resultado.getFilme().getId());
    }
}