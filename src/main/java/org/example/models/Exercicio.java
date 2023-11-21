package org.example.models;

public class Exercicio {
    private Long id;
    private TipoTreino tipoTreino;
    private String nome;
    private int series;
    private int repeticoes;
    private int tempoDescanso;

    public Exercicio(Long id, TipoTreino tipoTreino, String nome, int series, int repeticoes, int tempoDescanso) {
        this.id = id;
        this.tipoTreino = tipoTreino;
        this.nome = nome;
        this.series = series;
        this.repeticoes = repeticoes;
        this.tempoDescanso = tempoDescanso;
    }

    public Exercicio() {}

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

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(int tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }
}
