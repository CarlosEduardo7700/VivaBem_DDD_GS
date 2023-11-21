package org.example.models;

public class Cliente {
    private Long id;
    private Medida medida;
    private Objetivo objetivo;
    private Biotipo biotipo;
    private Dieta dieta;
    private Treino treino;
    private String email;
    private String senha;
    private String nome;
    private String genero;
    private int idade;
    private double metabolismo;


    public Cliente(Long id, Medida medida, Objetivo objetivo, Biotipo biotipo, Dieta dieta, Treino treino, String email, String senha, String nome, String genero, int idade, double metabolismo) {
        this.id = id;
        this.medida = medida;
        this.objetivo = objetivo;
        this.biotipo = biotipo;
        this.dieta = dieta;
        this.treino = treino;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.genero = genero;
        this.idade = idade;
        this.metabolismo = metabolismo;
    }

    public Cliente() {}


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

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public double getMetabolismo() {
        return metabolismo;
    }

    public void setMetabolismo(double metabolismo) {
        this.metabolismo = metabolismo;
    }
}
