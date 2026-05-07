package model;

public class Usuario {

    private String nome;
    private int idade;
    private PerfilCinefilo perfil;
    private boolean notificacoesAtivas;

    public Usuario(String nome, int idade, PerfilCinefilo perfil, boolean notificacoesAtivas) {
        this.nome = nome;
        this.idade = idade;
        this.perfil = perfil;
        this.notificacoesAtivas = notificacoesAtivas;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public PerfilCinefilo getPerfil() {
        return perfil;
    }

    public boolean isNotificacoesAtivas() {
        return notificacoesAtivas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setPerfil(PerfilCinefilo perfil) {
        this.perfil = perfil;
    }

    public void setNotificacoesAtivas(boolean notificacoesAtivas) {
        this.notificacoesAtivas = notificacoesAtivas;
    }
}