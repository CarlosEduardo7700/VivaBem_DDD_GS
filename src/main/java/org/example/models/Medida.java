package org.example.models;

public class Medida {
    private Long id;
    private double cintura;
    private double torax;
    private double bracoDireito;
    private double bracoEsquerdo;
    private double coxaDireita;
    private double coxaEsquerda;
    private double panturrilhaDireita;
    private double panturrilhaEsquerda;
    private double altura;
    private double peso;


    public Medida(Long id, double cintura, double torax, double bracoDireito, double bracoEsquerdo, double coxaDireita, double coxaEsquerda, double panturrilhaDireita, double panturrilhaEsquerda, double altura, double peso) {
        this.id = id;
        this.cintura = cintura;
        this.torax = torax;
        this.bracoDireito = bracoDireito;
        this.bracoEsquerdo = bracoEsquerdo;
        this.coxaDireita = coxaDireita;
        this.coxaEsquerda = coxaEsquerda;
        this.panturrilhaDireita = panturrilhaDireita;
        this.panturrilhaEsquerda = panturrilhaEsquerda;
        this.altura = altura;
        this.peso = peso;
    }

    public Medida() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCintura() {
        return cintura;
    }

    public void setCintura(double cintura) {
        this.cintura = cintura;
    }

    public double getTorax() {
        return torax;
    }

    public void setTorax(double torax) {
        this.torax = torax;
    }

    public double getBracoDireito() {
        return bracoDireito;
    }

    public void setBracoDireito(double bracoDireito) {
        this.bracoDireito = bracoDireito;
    }

    public double getBracoEsquerdo() {
        return bracoEsquerdo;
    }

    public void setBracoEsquerdo(double bracoEsquerdo) {
        this.bracoEsquerdo = bracoEsquerdo;
    }

    public double getCoxaDireita() {
        return coxaDireita;
    }

    public void setCoxaDireita(double coxaDireita) {
        this.coxaDireita = coxaDireita;
    }

    public double getCoxaEsquerda() {
        return coxaEsquerda;
    }

    public void setCoxaEsquerda(double coxaEsquerda) {
        this.coxaEsquerda = coxaEsquerda;
    }

    public double getPanturrilhaDireita() {
        return panturrilhaDireita;
    }

    public void setPanturrilhaDireita(double panturrilhaDireita) {
        this.panturrilhaDireita = panturrilhaDireita;
    }

    public double getPanturrilhaEsquerda() {
        return panturrilhaEsquerda;
    }

    public void setPanturrilhaEsquerda(double panturrilhaEsquerda) {
        this.panturrilhaEsquerda = panturrilhaEsquerda;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}
