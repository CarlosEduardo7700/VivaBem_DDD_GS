package org.example.models;

public class TipoTreino {
    private Long id;
    private Treino treino;
    private String nome;
    private String descricao;


    public TipoTreino(Long id, Treino treino, String nome, String descricao) {
        this.id = id;
        this.treino = treino;
        this.nome = nome;
        this.descricao = descricao;
    }

    public TipoTreino() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
