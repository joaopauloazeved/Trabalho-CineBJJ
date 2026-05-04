
public class Filme {
	private String titulo;
    private int ano;
    private int duracao;
    private List<Genero> generos;
    private int classificacao;
    private String idioma;
    private float popularidade;

    
    public Filme(String titulo, int ano, int duracao, List<Genero> generos, int classificacao, String idioma, float popularidade) {
        this.titulo = titulo;
        this.ano = ano;
        this.duracao = duracao;
        this.generos = generos;
        this.classificacao = classificacao;
        this.idioma = idioma;
        this.popularidade = popularidade;
    }

    
    public String getTitulo() {
    	return titulo; 
    }
    public int getAno() {
    	return ano; 
    }
    public int getDuracao() {
    	return duracao; 
    }
    public List<Genero> getGeneros() {
    	return generos; 
    }
    
    public int getClassificacao() {
    	return classificacao; 
    }
    public String getIdioma() {
    	return idioma; 
    }
    public float getPopularidade() {
    	return popularidade; 
    }

   
    public void setTitulo(String titulo) {
    	this.titulo = titulo;
    }
    public void setAno(int ano) 
    	this.ano = ano; 
    }
    public void setDuracao(int duracao) {
    	this.duracao = duracao; 
    }
    public void setGeneros(List<Genero> generos) {
    	this.generos = generos;
    }
    public void setClassificacao(int classificacao) { 
    	this.classificacao = classificacao;
    }
    public void setIdioma(String idioma) {
    	this.idioma = idioma; 
    }
    public void setPopularidade(float popularidade) {
    	this.popularidade = popularidade; 
    }

}
