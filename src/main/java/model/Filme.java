package model;
import java.util.List;

import enums.ClassificacaoEtaria;
import enums.Genero;
import enums.Idioma;

public class Filme {

   private final String id;
   private final String titulo;
   private final int duracao;
   private final List<Genero> generos;
   private final ClassificacaoEtaria classificacao;
   private final Idioma idioma;
   private final int popularidade;
   private final int anoLancamento;
   private final double avaliacao;
   private final boolean premiado;

   public Filme(String id,
           String titulo,
           int duracao,
           List<Genero> generos,
           ClassificacaoEtaria classificacao,
           Idioma idioma,
           int popularidade,
           boolean premiado,
           int anoLancamento,
           double avaliacao) {

  this.id = id;
  this.titulo = titulo;
  this.duracao = duracao;
  this.generos = generos;
  this.classificacao = classificacao;
  this.idioma = idioma;
  this.popularidade = popularidade;
  this.anoLancamento = anoLancamento;
  this.avaliacao = avaliacao;
  this.premiado = premiado;
}
   public boolean isPremiado() {return premiado;}
   public String getId() { return id; }

   public String getTitulo() { return titulo; }

   public int getDuracao() { return duracao; }

   public List<Genero> getGeneros() { return generos; }

   public ClassificacaoEtaria getClassificacao() { return classificacao; }

   public Idioma getIdioma() { return idioma; }

   public int getPopularidade() { return popularidade; }
   
   public int getAnoLancamento() {return anoLancamento;}

	public double getAvaliacao() {return avaliacao;}

   @Override
   public boolean equals(Object o) {
       if (this == o) return true;
       if (!(o instanceof Filme)) return false;
       Filme filme = (Filme) o;
       return id.equals(filme.id);
   }

   @Override
   public int hashCode() {
       return id.hashCode();
   }
}