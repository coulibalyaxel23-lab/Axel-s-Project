package com.example.prime.autorisation.BnB.dto;

public class AutorisationRequest {

    private String nomPrenoms;
    private String codeAgent;
    private String numeroCel;
    private String adresseMail;
    private String numeroPolice;
    private String lieuFait;
    private String jourFait;
    private String moisFait;
    private String anneeFait;
    private String jeConsens;
    private String signature;

    public String getNomPrenoms() {
        return nomPrenoms;
    }

    public void setNomPrenoms(String nomPrenoms) {
        this.nomPrenoms = nomPrenoms;
    }

    public String getCodeAgent() {
        return codeAgent;
    }

    public void setCodeAgent(String codeAgent) {
        this.codeAgent = codeAgent;
    }

    public String getNumeroCel() {
        return numeroCel;
    }

    public void setNumeroCel(String numeroCel) {
        this.numeroCel = numeroCel;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getNumeroPolice() {
        return numeroPolice;
    }

    public void setNumeroPolice(String numeroPolice) {
        this.numeroPolice = numeroPolice;
    }

    public String getLieuFait() {
        return lieuFait;
    }

    public void setLieuFait(String lieuFait) {
        this.lieuFait = lieuFait;
    }

    public String getJourFait() {
        return jourFait;
    }

    public void setJourFait(String jourFait) {
        this.jourFait = jourFait;
    }

    public String getMoisFait() {
        return moisFait;
    }

    public void setMoisFait(String moisFait) {
        this.moisFait = moisFait;
    }

    public String getAnneeFait() {
        return anneeFait;
    }

    public void setAnneeFait(String anneeFait) {
        this.anneeFait = anneeFait;
    }

    public String getJeConsens() {
        return jeConsens;
    }

    public void setJeConsens(String jeConsens) {
        this.jeConsens = jeConsens;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

