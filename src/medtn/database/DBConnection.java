/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medtn.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import medtn.models.Medcine;
import medtn.models.Pharmacie;

/**
 *
 * @author aziz
 */
public class DBConnection {

    private final static DBConnection dbcon = new DBConnection();

    private final String DATA_BASE = "jdbc:mysql://localhost:3306/med.tn?serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Connection con;

    private DBConnection() {

    }

    public static DBConnection getInstance() {
        return dbcon;
    }

    public List<Medcine> getMedicines(String address, String phone, String gouv, String specialitie) {
        List<Medcine> l = new ArrayList<>();
        openConnection();
        if (con != null) {
            try {
                PreparedStatement p = con.prepareStatement(""
                        + "SELECT \n"
                        + "m.id,\n"
                        + "m.adresse_cabinet,\n"
                        + "m.phone_fixe_cabinet,\n"
                        + "m.phone_fixe2_cabinet,\n"
                        + "u.nom,\n"
                        + "u.prenom,"
                        + "g.name as gouvernorats,\n"
                        + "s.name as specialites\n"
                        + "FROM medecines as m\n"
                        + "LEFT JOIN user as u on m.id_user = u.id\n"
                        + "LEFT JOIN gouvernorats as g on m.id_gouvernorat = g.id\n"
                        + "LEFT JOIN specialites as s on m.id_specialite = s.id\n"
                        + "WHERE m.adresse_cabinet LIKE  CONCAT(?, '%')\n"
                        + "AND (m.phone_fixe_cabinet LIKE  CONCAT(?, '%') OR m.phone_fixe2_cabinet LIKE  CONCAT(?, '%'))\n"
                        + "AND g.name LIKE  CONCAT(?, '%')\n"
                        + "AND s.name LIKE  CONCAT(?, '%')\n");
                p.setString(1, address);
                p.setString(2, phone);
                p.setString(3, phone);
                p.setString(4, gouv);
                p.setString(5, specialitie);

                ResultSet res = p.executeQuery();
                while (res.next()) {
                    Medcine m = new Medcine();
                    m.setId(res.getInt(1));
                    m.setAdress(res.getString(2));
                    m.setPhone(res.getString(3));
                    m.setPhone2(res.getString(4));
                    m.setNom(res.getString(5));
                    m.setPrenom(res.getString(6));
                    m.setGouv(res.getString(7));
                    m.setSpecialite(res.getString(8));
                    l.add(m);
                }

            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConnection();
        return l;
    }

    public List<Pharmacie> getPharmacies(String address, String phone, String gouv) {
        List<Pharmacie> l = new ArrayList<>();
        openConnection();
        if (con != null) {
            try {
                PreparedStatement p = con.prepareStatement(""
                        + "SELECT \n"
                        + "p.id,\n"
                        + "p.name_pharmacie,\n"
                        + "p.adresse_pharmacie,\n"
                        + "p.phone_fixe_pharmacie,\n"
                        + "p.phone_fixe2_pharmacie,\n"
                        + "g.name as gouvernorats\n"
                        + "FROM pharmacie as p\n"
                        + "LEFT JOIN gouvernorats as g on p.id_gouvernorat = g.id\n"
                        + "WHERE p.adresse_pharmacie LIKE  CONCAT(?, '%')\n"
                        + "AND (p.phone_fixe_pharmacie LIKE  CONCAT(?, '%') OR p.phone_fixe2_pharmacie LIKE  CONCAT( ?, '%'))\n"
                        + "AND g.name LIKE  CONCAT(?, '%')");
                p.setString(1, address);
                p.setString(2, phone);
                p.setString(3, phone);
                p.setString(4, gouv);

                ResultSet res = p.executeQuery();
                while (res.next()) {
                    Pharmacie phar = new Pharmacie();
                    phar.setId(res.getInt(1));
                    phar.setNom(res.getString(2));
                    phar.setAdress(res.getString(3));
                    phar.setPhone(res.getString(4));
                    phar.setPhone_2(res.getString(5));
                    phar.setGouv(res.getString(6));
                    l.add(phar);
                }

            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConnection();
        return l;
    }

    private void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DATA_BASE, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
    }

}
