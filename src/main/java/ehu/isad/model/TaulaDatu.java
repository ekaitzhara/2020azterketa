package ehu.isad.model;

import javafx.scene.image.Image;

import java.sql.Date;


public class TaulaDatu {

    private Integer argazkiId;
    private Image argazkia;
    private String izena;
    private String etiketak;
    private Date data;
    private String deskribapena;
    private Integer favs;
    private Integer comments;

    public TaulaDatu(Integer pArgazkiId, String argazkiapath, String izena, String etiketak, Date data, String desk, Integer favs, Integer comments) {
        this.argazkiId = pArgazkiId;
        //this.argazkia = new Image(argazkiapath);
        //this.argazkia = new Image(this.getClass().getResource("/data/dasiteam/flickr/argazkiak").toString() + izena + ".jpg", true);
        this.argazkia = new Image(argazkiapath, true);
        this.izena = izena;
        this.etiketak = etiketak;
        this.data = data;
        this.deskribapena = desk;
        this.favs = favs;
        this.comments = comments;
    }

    public Integer getArgazkiId() {
        return argazkiId;
    }

    public Image getArgazkia() {
        return argazkia;
    }

    public String getIzena() {
        return izena;
    }

    public String getEtiketak() {
        return etiketak;
    }

    public Date getData() {
        return data;
    }

    public String getDeskribapena() {
        return deskribapena;
    }

    public Integer getFavs() {
        return favs;
    }

    public Integer getComments() {
        return comments;
    }
}
