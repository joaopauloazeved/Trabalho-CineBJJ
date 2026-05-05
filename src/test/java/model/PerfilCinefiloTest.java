package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import model.enums.*;
import exception.PesoInvalidoException;
import exception.DuracaoInvalidaException;

class PerfilCinefiloTest {

    @Test
    void deve_CriarPerfil_Quando_DadosValidos() {
        Map<Genero, Double> pesos = new HashMap<>();
        pesos.put(Genero.ACAO, 0.8);

        Set<Idioma> idiomas = new HashSet<>();
        idiomas.add(Idioma.INGLES);

        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos,
            90,
            120,
            ClassificacaoEtaria.DEZESSEIS,
            idiomas
        );

        assertNotNull(perfil);
    }
    @Test
    void deve_MarcarFilmeComoAssistido() {

        Map<Genero, Double> pesos = new HashMap<>();
        pesos.put(Genero.ACAO, 0.8);

        Set<Idioma> idiomas = new HashSet<>();
        idiomas.add(Idioma.INGLES);

        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
        );

        perfil.marcarAssistido("F1");

        assertTrue(perfil.jaAssistido("F1"));
    }
    @Test
    void deve_AdicionarNotaValida() {

        Map<Genero, Double> pesos = new HashMap<>();
        pesos.put(Genero.ACAO, 0.8);

        Set<Idioma> idiomas = new HashSet<>();
        idiomas.add(Idioma.INGLES);

        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
        );

        perfil.adicionarNota("F1", 5);

        assertEquals(5, perfil.getNotas().get("F1"));
    }
    @Test
    void deve_LancarExcecao_Quando_NotaInvalida() {

        Map<Genero, Double> pesos = new HashMap<>();
        pesos.put(Genero.ACAO, 0.8);

        Set<Idioma> idiomas = new HashSet<>();
        idiomas.add(Idioma.INGLES);

        PerfilCinefilo perfil = new PerfilCinefilo(
            pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
        );

        assertThrows(RuntimeException.class, () -> {
            perfil.adicionarNota("F1", 10);
        });
    }
}