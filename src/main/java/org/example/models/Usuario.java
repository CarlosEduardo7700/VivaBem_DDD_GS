package org.example.models;

public class Usuario {
    private Long id;
    private Biotipo biotipo;
    private Treino treino;
    private Dieta dieta;
    private String nome;
    private String genero;
    private int idade;
    private String email;
    private String senha;


    public Usuario(Long id, Biotipo biotipo, Treino treino, Dieta dieta, String nome, String genero, int idade, String email, String senha) {
        this.id = id;
        this.biotipo = biotipo;
        this.treino = treino;
        this.dieta = dieta;
        this.nome = nome;
        this.genero = genero;
        this.idade = idade;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Biotipo getBiotipo() {
        return biotipo;
    }

    public void setBiotipo(Biotipo biotipo) {
        this.biotipo = biotipo;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public Dieta getDieta() {
        return dieta;
    }

    public void setDieta(Dieta dieta) {
        this.dieta = dieta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
