package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import enums.*;
import model.*;
import util.GeradorAleatorio;

@ExtendWith(MockitoExtension.class)
class RecomendadorServiceTest {

    @Mock
    private CatalogoFilmesAPI catalogo;

    @Mock
    private HistoricoUsuarioRepository historico;

    @Mock
    private NotificadorPush notificador;

    @Mock
    private GeradorAleatorio gerador;

    @Mock
    private FiltroCapsulaTempo filtroCapsula;

    @Spy
    private CalculadoraScore calculadora = new CalculadoraScore();

    private RecomendadorService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        service = new RecomendadorService(
            catalogo,
            historico,
            notificador,
            gerador,
            calculadora,
            new FiltroFilmes(), 
            filtroCapsula
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
            criarFilme("F1", "Filme 1", Genero.ACAO, 70, 2010, 8.0),
            criarFilme("F2", "Filme 2", Genero.DRAMA, 80, 2011, 8.0),
            criarFilme("F3", "Filme 3", Genero.ACAO, 90, 2012, 8.0)
        ));

        List<Recomendacao> resultado = service.recomendar(usuario, 2);

        assertEquals(2, resultado.size());
        verify(historico).registrarRecomendacao(eq(usuario), eq(resultado));
    }

    @Test
    void deve_OrdenarPorScoreDesc_Quando_Recomendar() {
        when(catalogo.buscarTodos()).thenReturn(List.of(
            criarFilme("F1", "Baixo", Genero.DRAMA, 50, 2010, 7.0),
            criarFilme("F2", "Alto", Genero.ACAO, 90, 2010, 9.0)
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
            criarFilme("F1", "Filme 1", Genero.ACAO, 70, 2010, 8.0),
            criarFilme("F2", "Filme 2", Genero.ACAO, 80, 2011, 8.0)
        ));

        when(gerador.sortearInteiro(0, 1)).thenReturn(1);

        Recomendacao resultado = service.recomendarAleatorio(usuario);

        assertNotNull(resultado);
        assertEquals("F2", resultado.getFilme().getId());
    }

    @Test
    void deve_CapturarRecomendacoesRegistradas_Quando_Recomendar() {
        when(catalogo.buscarTodos()).thenReturn(List.of(
            criarFilme("F1", "Filme 1", Genero.ACAO, 70, 2010, 8.0),
            criarFilme("F2", "Filme 2", Genero.DRAMA, 80, 2011, 8.0),
            criarFilme("F3", "Filme 3", Genero.ACAO, 90, 2012, 8.0)
        ));

        ArgumentCaptor<List<Recomendacao>> captor =
            ArgumentCaptor.forClass(List.class);

        service.recomendar(usuario, 2);

        verify(historico).registrarRecomendacao(eq(usuario), captor.capture());

        List<Recomendacao> registradas = captor.getValue();

        assertAll(
            () -> assertEquals(2, registradas.size()),
            () -> assertNotNull(registradas.get(0).getFilme()),
            () -> assertTrue(registradas.get(0).getScore() >= registradas.get(1).getScore())
        );
    }

    @Test
    void deve_ChamarCalculadora_ParaCadaFilmeFiltrado() {
        when(catalogo.buscarTodos()).thenReturn(List.of(
            criarFilme("F1", "Filme 1", Genero.ACAO, 70, 2010, 8.0),
            criarFilme("F2", "Filme 2", Genero.DRAMA, 80, 2011, 8.0)
        ));

        service.recomendar(usuario, 2);

        verify(calculadora, times(2))
            .calcular(any(Filme.class), eq(usuario.getPerfil()));
    }

    @Test
    void deve_ChamarFiltroCapsulaPorAno_Quando_RecomendarPorAno() {
        Filme filme = criarFilme("F1", "Filme 2010", Genero.ACAO, 80, 2010, 8.5);

        when(catalogo.buscarTodos()).thenReturn(List.of(filme));

        when(filtroCapsula.filtrarPorAno(anyList(), eq(2010)))
            .thenReturn(List.of(filme));

        List<Recomendacao> resultado =
            service.recomendarPorAno(usuario, 2010, 5);

        assertEquals(1, resultado.size());
        assertNotNull(resultado.get(0));;

        verify(filtroCapsula).filtrarPorAno(anyList(), eq(2010));
    }

    private Filme criarFilme(
        String id,
        String titulo,
        Genero genero,
        int popularidade,
        int anoLancamento,
        double avaliacao
    ) {
        return new Filme(
            id,
            titulo,
            100,
            List.of(genero),
            ClassificacaoEtaria.DEZESSEIS,
            Idioma.INGLES,
            popularidade,
            false,
            anoLancamento,
            avaliacao
        );
    }
}