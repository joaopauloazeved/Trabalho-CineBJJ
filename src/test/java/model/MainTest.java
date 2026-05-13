package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import app.Main;

class MainTest {

    private final PrintStream saidaOriginal = System.out;
    private final java.io.InputStream entradaOriginal = System.in;

    @AfterEach
    void restaurarSistema() {
        System.setOut(saidaOriginal);
        System.setIn(entradaOriginal);
    }

    @Test
    void deve_MostrarRecomendacoes_Quando_UsuarioEscolheOpcao1() {
        String entrada = String.join("\n",
            "Maria",
            "28",
            "2",
            "90",
            "200",
            "6",
            "2",
            "0",
            "1",
            "1",
            "0"
        );

        ByteArrayOutputStream saida = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
        System.setOut(new PrintStream(saida));

        assertDoesNotThrow(() -> Main.main(new String[]{}));

    }
    @Test
    void deve_MostrarFilmePorAno_Quando_UsuarioEscolheCapsulaDoTempo() {
        String entrada = String.join("\n",
            "Maria",
            "28",
            "2",
            "90",
            "200",
            "6",
            "2",
            "0",
            "1",
            "3",
            "2016",
            "0"
        );

        ByteArrayOutputStream saida = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(entrada.getBytes()));
        System.setOut(new PrintStream(saida));

        assertDoesNotThrow(() -> Main.main(new String[]{}));

        String texto = saida.toString();

        assertTrue(texto.contains("A Chegada"));
    }
}