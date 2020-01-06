package ehu.isad.db;

import ehu.isad.model.TaulaDatu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatuakDBKud {

    // singleton patroia
    private static DatuakDBKud instantzia = new DatuakDBKud();
    private static String idErab;

    private DatuakDBKud() {

    }

    public static DatuakDBKud getInstantzia() {
        return instantzia;
    }

    public ArrayList<TaulaDatu> emanDatuak() {
        ArrayList<TaulaDatu> emaitza = new ArrayList<>();

        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        ResultSet rs=null;
        String query = "SELECT id, filename, value, date FROM captchas";
        rs = dbKud.execSQL(query);


        try {
            while (rs.next()) {

                Integer id = rs.getInt("id");
                String filename = rs.getString("filename");
                String value = rs.getString("value");
                String date = rs.getString("date");

                emaitza.add(new TaulaDatu(id, filename, value, date));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emaitza;
    }

    public void ezabatuGuztia() {
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        ResultSet rs=null;
        String query = "DELETE FROM datuak";
        dbKud.execSQL(query);
    }

    public void sartuBerria(String filename, long date) {
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        ResultSet rs=null;
        String query = "INSERT INTO captchas(filename, date) " +
                "VALUES('" + filename +"', '"+date+"')";
        dbKud.execSQL(query);
    }


    public Integer emanId(String filename, long date) {
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        ResultSet rs=null;
        String query = "SELECT id FROM captchas WHERE filename='"+filename+"' AND date='"+date+"'";
        rs = dbKud.execSQL(query);
        try {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void eguneratuContentGuztiak(ArrayList<TaulaDatu> datuak) {
        DBKudeatzaile dbKud = DBKudeatzaile.getInstantzia();
        ResultSet rs=null;
        for (TaulaDatu t : datuak) {
            String query = null;
            if (t.getValue() != null) {
                query = "UPDATE captchas SET value='" + t.getValue() + "' WHERE id='" + t.getId() + "'";
                dbKud.execSQL(query);
            }
        }
    }

    public String probaMysql() {
        DBKudeatzaile_mysql dbKud_mysql = DBKudeatzaile_mysql.getInstantzia();
        ResultSet rs = null;
        String query = "SELECT data FROM txanponak WHERE id=76";
        rs = dbKud_mysql.execSQL(query);
        try {
            if (rs.next()) {
                return rs.getString("data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
