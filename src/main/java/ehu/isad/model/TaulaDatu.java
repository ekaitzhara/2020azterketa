package ehu.isad.model;

import javafx.scene.image.Image;

import java.sql.Date;


public class TaulaDatu {

    private Integer argazkiId;
    private Image argazkia;
    private String izena;
    private String etiketak;
    private Date data;
    private Integer views;
    private Integer favs;
    private Integer comments;

    public TaulaDatu(Integer pArgazkiId, String argazkiapath, String izena, String etiketak, Date data, Integer views, Integer favs, Integer comments) {
        this.argazkiId = pArgazkiId;
        this.argazkia = new Image(argazkiapath);
        this.izena = izena;
        this.etiketak = etiketak;
        this.data = data;
        this.views = views;
        this.favs = favs;
        this.comments = comments;
    }
}
