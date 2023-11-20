package org.example.models;

public class Objetivo {
    private Long id;
    private String nome;
    private String tempo;
    private double peso;

    public Objetivo(Long id, String nome, String tempo, double peso) {
        this.id = id;
        this.nome = nome;
        this.tempo = tempo;
        this.peso = peso;
    }

    public Objetivo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
