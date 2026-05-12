	package service;

	import static org.junit.jupiter.api.Assertions.*;

	import java.util.List;

	import org.junit.jupiter.api.Test;

	import enums.ClassificacaoEtaria;
	import enums.Genero;
	import enums.Idioma;
	import model.Filme;

	class FiltroCapsulaTest {

	    @Test
	    void deve_RetornarApenasFilmesDoAnoInformado() {
	        FiltroCapsulaTempo filtroCapsula = new FiltroCapsulaTempo();

	        Filme filme2010 = criarFilme("F1", "Filme 2010", 2010);
	        Filme filme2011 = criarFilme("F2", "Filme 2011", 2011);

	        List<Filme> resultado = filtroCapsula.filtrarPorAno(
	            List.of(filme2010, filme2011), 
	            2010
	        );

	        assertEquals(1, resultado.size());
	        assertEquals("F1", resultado.get(0).getId());
	    }

	    @Test
	    void deve_RetornarApenasFilmesDaDecadaInformada() {
	        FiltroCapsulaTempo filtroCapsula = new FiltroCapsulaTempo();
	        
	        Filme filme1995 = criarFilme("F1", "Filme 1995", 1995);
	        Filme filme2001 = criarFilme("F2", "Filme 2001", 2001);

	        List<Filme> resultado = filtroCapsula.filtrarPorDecada(
	            List.of(filme1995, filme2001), 
	            1990
	        );

	        assertEquals(1, resultado.size());
	        assertEquals("F1", resultado.get(0).getId());
	    }

	    private Filme criarFilme(String id, String titulo, int ano) {
	        return new Filme(
	            id,
	            titulo,
	            100,
	            List.of(Genero.ACAO),
	            ClassificacaoEtaria.DEZESSEIS,
	            Idioma.INGLES,
	            80,
	            false,
	            ano,
	            8.0
	        );
	    }
	}

