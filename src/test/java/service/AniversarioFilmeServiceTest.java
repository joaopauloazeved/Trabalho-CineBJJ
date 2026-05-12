package service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Year;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.ClassificacaoEtaria;
import enums.Genero;
import enums.Idioma;
import model.*;

class AniversarioFilmeServiceTest {

    private CatalogoFilmesAPI catalogo;
    private CalculadoraScore calculadora;
    private FiltroFilmes filtro;

    private AniversarioFilmeService service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {

        catalogo = mock(CatalogoFilmesAPI.class);
        calculadora = mock(CalculadoraScore.class);
        filtro = mock(FiltroFilmes.class);

        service = new AniversarioFilmeService(
            catalogo,
            calculadora,
            filtro
        );

        Map<Genero, Double> pesos = new HashMap<>();
        pesos.put(Genero.ACAO, 1.0);

        Set<Idioma> idiomas = new HashSet<>();
        idiomas.add(Idioma.INGLES);

        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos,
            90,
            120,
            ClassificacaoEtaria.DEZESSEIS,
            idiomas
        );

        usuario = new Usuario(
            "Bernardo",
            20,
            perfil,
            true
        );
    }

    @Test
    void deve_RecomendarFilme_Quando_AniversarioEspecialEBemAvaliado() {

        int anoAtual = Year.now().getValue();

        Filme filme = new Filme(
            "F1",
            "Filme Especial",
            100,
            List.of(Genero.ACAO),
            ClassificacaoEtaria.DEZESSEIS,
            Idioma.INGLES,
            90,
            false,
            anoAtual - 10,
            8.5
        );

        when(catalogo.buscarTodos()).thenReturn(List.of(filme));
        when(filtro.filtrar(anyList(), eq(usuario.getPerfil())))
            .thenReturn(List.of(filme));
        when(calculadora.calcular(eq(filme), eq(usuario.getPerfil())))
            .thenReturn(10.0);

        List<Recomendacao> resultado =
            service.recomendarFilmesEmAniversario(usuario, 5, 5);

        assertEquals(1, resultado.size());
        assertEquals("F1", resultado.get(0).getFilme().getId());
    }
    

    @Test
    void deve_NaoRecomendar_Quando_NaoForAniversarioEspecial() {

        int anoAtual = Year.now().getValue();

        Filme filme = new Filme(
            "F1",
            "Filme",
            100,
            List.of(Genero.ACAO),
            ClassificacaoEtaria.DEZESSEIS,
            Idioma.INGLES,
            90,
            false,
            anoAtual - 11,
            8.5
        );

        when(catalogo.buscarTodos())
            .thenReturn(List.of(filme));

        when(filtro.filtrar(anyList(), any()))
            .thenReturn(List.of(filme));

        List<Recomendacao> resultado =
            service.recomendarFilmesEmAniversario(usuario, 5, 5);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deve_Recomendar_Quando_FilmeForPremiado() {

        int anoAtual = Year.now().getValue();

        Filme filme = new Filme(
            "F1",
            "Filme Premiado",
            100,
            List.of(Genero.ACAO),
            ClassificacaoEtaria.DEZESSEIS,
            Idioma.INGLES,
            90,
            true,
            anoAtual - 15,
            6.0
        );

        when(catalogo.buscarTodos())
            .thenReturn(List.of(filme));

        when(filtro.filtrar(anyList(), any()))
            .thenReturn(List.of(filme));

        when(calculadora.calcular(any(), any()))
            .thenReturn(10.0);

        List<Recomendacao> resultado =
            service.recomendarFilmesEmAniversario(usuario, 5, 5);

        assertEquals(1, resultado.size());
    }

    @Test
    void deve_LimitarResultadoAoTopN() {

        int anoAtual = Year.now().getValue();

        Filme f1 = criarFilme("F1", anoAtual - 10);
        Filme f2 = criarFilme("F2", anoAtual - 15);
        Filme f3 = criarFilme("F3", anoAtual - 20);

        when(catalogo.buscarTodos())
            .thenReturn(List.of(f1, f2, f3));

        when(filtro.filtrar(anyList(), any()))
            .thenReturn(List.of(f1, f2, f3));

        when(calculadora.calcular(any(), any()))
            .thenReturn(10.0);

        List<Recomendacao> resultado =
            service.recomendarFilmesEmAniversario(usuario, 5, 2);

        assertEquals(2, resultado.size());
    }

    private Filme criarFilme(String id, int ano) {

        return new Filme(
            id,
            "Filme",
            100,
            List.of(Genero.ACAO),
            ClassificacaoEtaria.DEZESSEIS,
            Idioma.INGLES,
            90,
            true,
            ano,
            8.5
        );
    }
}