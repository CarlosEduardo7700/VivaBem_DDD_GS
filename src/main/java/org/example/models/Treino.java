package org.example.models;

public class Treino {
    private Long id;
    private TipoTreino tipoTreino;
    private String nome;
    private String descricao;


    public Treino(Long id, TipoTreino tipoTreino, String nome, String descricao) {
        this.id = id;
        this.tipoTreino = tipoTreino;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Treino() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTreino getTipoTreino() {
        return tipoTreino;
    }

    public void setTipoTreino(TipoTreino tipoTreino) {
        this.tipoTreino = tipoTreino;
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
