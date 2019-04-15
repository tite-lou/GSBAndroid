package fr.lc.sio.lt.gsbmission5.Metier;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Rapport   {
    private int numPra;
    private String id;
    private String numVis;
    private GregorianCalendar dateVisite, dateRapportEnv;
    private String rapBilan;


    public Rapport(int numP, String numV, String id, String commentaire , GregorianCalendar dateV, GregorianCalendar dateRE){
        this.numPra = numP;
        this.numVis= numV;
        this.id = id;
        this.rapBilan= commentaire;
        this.dateVisite=dateV;
        this.dateRapportEnv=dateRE;
    }
    public Rapport(int numPraticien, String numVisiteur, String commentaire, GregorianCalendar dateVisite){
        this.numPra = numPraticien;
        this.numVis = numVisiteur;
        this.rapBilan = commentaire;
        this.dateVisite = dateVisite;
    }

    public int getNumPra() {
        return numPra;
    }

    public GregorianCalendar getDateVisite() {
        return dateVisite;
    }

    public String getRapBilan() {
        return rapBilan;
    }

    public GregorianCalendar getDateRapportEnv() {
        return dateRapportEnv;
    }

    public String getNumVis() {
        return numVis;
    }

    public String getId() {
        return id;
    }

    public void setRapBilan(String commentaire) {
        this.rapBilan = commentaire;
    }

    public void setDateVisite(GregorianCalendar dateVisite) {
        this.dateVisite = dateVisite;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumPra(int numPra) {
        this.numPra = numPra;
    }

    public void setDateRapportEnv(GregorianCalendar dateRapportEnv) {
        this.dateRapportEnv = dateRapportEnv;
    }

    public void setNumVis(String numVis) {
        this.numVis = numVis;
    }


}
