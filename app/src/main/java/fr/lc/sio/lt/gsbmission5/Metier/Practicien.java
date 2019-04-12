package fr.lc.sio.lt.gsbmission5.Metier;

public class Practicien {

    private int praNum;
    private String praNom;
    private String praPrenom;
    private String praAdresse;
    private String praCp;
    private String praVille;
    private float praCoefNotoriete;
    private String praTypeCode;
    private String praVisiteur ;

    public float getPraCoefNotoriete() {
        return praCoefNotoriete;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getPraNum() {
        return praNum;
    }

    public String getPraAdresse() {
        return praAdresse;
    }

    public String getPraCp() {
        return praCp;
    }

    public String getPraNom() {
        return praNom;
    }

    public String getPraPrenom() {
        return praPrenom;
    }

    public String getPraTypeCode() {
        return praTypeCode;
    }

    public String getPraVille() {
        return praVille;
    }

    public String getPraVisiteur() {
        return praVisiteur;
    }

    public void setPraAdresse(String praAdresse) {
        this.praAdresse = praAdresse;
    }

    public void setPraCoefNotoriete(float praCoefNotoriete) {
        this.praCoefNotoriete = praCoefNotoriete;
    }

    public void setPraCp(String praCp) {
        this.praCp = praCp;
    }

    public void setPraNom(String praNom) {
        this.praNom = praNom;
    }

    public void setPraNum(int praNum) {
        this.praNum = praNum;
    }

    public void setPraPrenom(String praPrenom) {
        this.praPrenom = praPrenom;
    }

    public void setPraTypeCode(String praTypeCode) {
        this.praTypeCode = praTypeCode;
    }

    public void setPraVille(String praVille) {
        this.praVille = praVille;
    }

    public void setPraVisiteur(String praVisiteur) {
        this.praVisiteur = praVisiteur;
    }
    public Practicien(int praNum, String praNom, String praPrenom, String praAdresse, String praCp,
                     String praVille, float praCoefNotoriete, String praTypeCode, String praVisiteur) {
        this.praNum = praNum;
        this.praNom = praNom;
        this.praPrenom = praPrenom;
        this.praAdresse = praAdresse;
        this.praCp = praCp;
        this.praVille = praVille;
        this.praCoefNotoriete = praCoefNotoriete;
        this.praTypeCode = praTypeCode;
        this.praVisiteur = praVisiteur;
    }

  //  public Practicien() {
   //     super();
  //  }
}
