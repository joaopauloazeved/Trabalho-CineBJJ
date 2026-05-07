package service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import enums.ClassificacaoEtaria;
import enums.Genero;
import enums.Idioma;

import java.util.*;

import model.*;

class CalculadoraScoreTest {
	
	@Test
	void deve_TerScoreAlto_Quando_GeneroEhPreferido() {

	    CalculadoraScore calc = new CalculadoraScore();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 1.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Ação",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        80
	    );

	    double score = calc.calcular(filme, perfil);

	    assertTrue(score > 70);
	}
	@Test
	void deve_TerScoreBaixo_Quando_GeneroNaoPreferido() {

	    CalculadoraScore calc = new CalculadoraScore();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 0.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Ação",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        80
	    );

	    double score = calc.calcular(filme, perfil);

	    assertTrue(score < 50);
	}
	@Test
	void deve_TerScoreMaior_Quando_DuracaoEstaDentroDoIntervalo() {

	    CalculadoraScore calc = new CalculadoraScore();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 1.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        50
	    );

	    double score = calc.calcular(filme, perfil);

	    assertTrue(score > 60);
	}
	@Test
	void deve_NuncaPassarDe100() {

	    CalculadoraScore calc = new CalculadoraScore();

	    Map<Genero, Double> pesos = new HashMap<>();
	    pesos.put(Genero.ACAO, 1.0);

	    Set<Idioma> idiomas = new HashSet<>();
	    idiomas.add(Idioma.INGLES);

	    PerfilCinefilo perfil = new PerfilCinefilo(
	        pesos, 90, 120, ClassificacaoEtaria.DEZESSEIS, idiomas
	    );

	    Filme filme = new Filme(
	        "F1",
	        "Filme Top",
	        100,
	        List.of(Genero.ACAO),
	        ClassificacaoEtaria.DEZESSEIS,
	        Idioma.INGLES,
	        100
	    );

	    double score = calc.calcular(filme, perfil);

	    assertTrue(score <= 100);
	}
}