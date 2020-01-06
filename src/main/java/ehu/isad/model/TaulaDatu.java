package ehu.isad.model;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaulaDatu {

    private Integer id;
    private String filename;
    private String value;
    private String date;
    private Image irudia;

    public TaulaDatu(Integer id, String filename, String value, String date) {
        this.id = id;
        this.filename = filename;
        this.value = value;

        long i = Long.parseLong(date);
        Date d = new Date(i);
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd, yy");
        String newFormat = sdf.format(d);
        Integer hilabetea = Integer.parseInt(newFormat.split(" ")[0]);
        String hilIzen = new DateFormatSymbols().getMonths()[hilabetea-1].substring(0,3);
        char aux = hilIzen.charAt(0);
        String aux2 = Character.toString(aux).toUpperCase();
        newFormat = newFormat.substring(2);
        hilIzen = aux2 + hilIzen.substring(1);
        this.date = hilIzen + newFormat;

//        String irudiKarpeta = "/home/ekaitzhara/Documentos/UNI/irudiak_azt19/";
//        irudiKarpeta = irudiKarpeta.replace('/', File.separatorChar);
//        String irudiPath = irudiKarpeta + filename;
        String azterketarakoKarpeta = "/home/ekaitzhara/Documentos/UNI/InformazioSistemenAnalisia/3_partziala/irudiak_azterketa2020/";
//        this.irudia = new Image("irudiak/" + filename);
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(new File(azterketarakoKarpeta + filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.irudia = new Image(targetStream);
    }

    public Integer getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    public Image getIrudia() {
        return irudia;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
