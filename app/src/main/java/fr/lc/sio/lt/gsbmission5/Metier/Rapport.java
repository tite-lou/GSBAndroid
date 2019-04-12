package fr.lc.sio.lt.gsbmission5.Metier;

import java.text.DateFormat;
import java.util.Date;

public class Rapport   {
    private Practicien numPra;
    private String id;
    private Visiteur numVis;
    private Date dateVisite, dateRapportEnv;
    private String commentaire;


    public Rapport(Practicien numP, Visiteur numV, String id, String commentaire , Date dateV, Date dateRE){
        this.numPra = numP;
        this.numVis= numV;
        this.id = id;
        this.commentaire= commentaire;
        this.dateVisite=dateV;
        this.dateRapportEnv=dateRE;
    }

    public Practicien getNumPra() {
        return numPra;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Date getDateRapportEnv() {
        return dateRapportEnv;
    }

    public Visiteur getNumVis() {
        return numVis;
    }

    public String getId() {
        return id;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumPra(Practicien numPra) {
        this.numPra = numPra;
    }

    public void setDateRapportEnv(Date dateRapportEnv) {
        this.dateRapportEnv = dateRapportEnv;
    }

    public void setNumVis(Visiteur numVis) {
        this.numVis = numVis;
    }
}
