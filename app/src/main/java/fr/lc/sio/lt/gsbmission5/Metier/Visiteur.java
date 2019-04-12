package fr.lc.sio.lt.gsbmission5.Metier;

public class Visiteur {

    private String visMatricule;
    private String visNom;
    private String visPrenom;



    public String getVisMatricule() {
        return visMatricule;
    }

    public String getVisNom() {
        return visNom;
    }

    public String getVisPrenom() {
        return visPrenom;
    }

    public void setVisMatricule(String visMatricule) {
        this.visMatricule = visMatricule;
    }

    public void setVisNom(String visNom) {
        this.visNom = visNom;
    }

    public void setVisPrenom(String visPrenom) {
        this.visPrenom = visPrenom;
    }

    @Override
    public String toString() {
        return getClass()+" : [Matricule : "+getVisMatricule()+" , Nom : "+getVisNom()+", Prénom : "+getVisPrenom()+"]";
    }

}
