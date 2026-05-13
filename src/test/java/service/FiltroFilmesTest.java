package service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.ClassificacaoEtaria;
import enums.Genero;
import enums.Idioma;

import java.util.*;

import model.*;

class FiltroFilmesTest {
	@Test
	void deve_RemoverFilme_Quando_JaFoiAssistido() {

	    FiltroFilmes filtro = new FiltroFilmes();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 1.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Teste",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        80, false, 0, 0
	    );

	    perfil.marcarAssistido("F1");

	    List<Filme> resultado = filtro.filtrar(List.of(filme), perfil);

	    assertTrue(resultado.isEmpty());
	}
	@Test
	void deve_RemoverFilme_Quando_ClassificacaoMaiorQuePermitida() {

	    FiltroFilmes filtro = new FiltroFilmes();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 1.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Teste",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZOITO,
	        Idioma.INGLES,
	        80, false, 0, 0
	    );

	    List<Filme> resultado = filtro.filtrar(List.of(filme), perfil);

	    assertTrue(resultado.isEmpty());
	}
	@Test
	void deve_RemoverFilme_Quando_IdiomaNaoAceito() {

	    FiltroFilmes filtro = new FiltroFilmes();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 1.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.PORTUGUES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Teste",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        80, false, 0, 0
	    );

	    List<Filme> resultado = filtro.filtrar(List.of(filme), perfil);

	    assertTrue(resultado.isEmpty());
	}
	@Test
	void deve_RemoverFilme_Quando_GeneroTemPesoZero() {

	    FiltroFilmes filtro = new FiltroFilmes();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.TERROR, 0.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Terror",
	        100,
	        List.of(Genero.TERROR),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        80, false, 0, 0
	    );

	    List<Filme> resultado = filtro.filtrar(List.of(filme), perfil);

	    assertTrue(resultado.isEmpty());
	}
}