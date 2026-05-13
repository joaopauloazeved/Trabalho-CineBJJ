package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import enums.ClassificacaoEtaria;
import enums.Genero;
import enums.Idioma;

import java.util.List;

class FilmeTest {

    @Test
    void deve_CriarFilme_Quando_DadosValidos() {
        Filme filme = new Filme(
            "F01",
            "Duna",
            166,
            List.of(Genero.FICCAO_CIENTIFICA, Genero.DRAMA),
            ClassificacaoEtaria.QUATORZE,
            Idioma.INGLES,
            92, false, 0, 0
        );

        assertAll(
            () -> assertEquals("F01", filme.getId()),
            () -> assertEquals("Duna", filme.getTitulo()),
            () -> assertEquals(166, filme.getDuracao()),
            () -> assertEquals(ClassificacaoEtaria.QUATORZE, filme.getClassificacao()),
            () -> assertEquals(Idioma.INGLES, filme.getIdioma()),
            () -> assertEquals(92, filme.getPopularidade())
        );
    }

    @Test
    void deve_ConsiderarFilmesIguais_Quando_TemMesmoId() {
        Filme filme1 = new Filme(
            "F01",
            "Duna",
            166,
            List.of(Genero.FICCAO_CIENTIFICA),
            ClassificacaoEtaria.QUATORZE,
            Idioma.INGLES,
            92, false, 0, 0
        );

        Filme filme2 = new Filme(
            "F01",
            "Outro título",
            100,
            List.of(Genero.COMEDIA),
            ClassificacaoEtaria.LIVRE,
            Idioma.PORTUGUES,
            50, false, 0, 0
        );

        assertEquals(filme1, filme2);
        assertEquals(filme1.hashCode(), filme2.hashCode());
    }
}