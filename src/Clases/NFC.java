/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Fernando Rhenals
 */
public class NFC {

    private int idNFC;
    private String codigo;
    private String fecha_activo;
    private String fecha_inactivo;
    private int activo;
    private String idPersona;
    private String nombres;
    
    

    public int getIdNFC() {
        return idNFC;
    }

    public void setIdNFC(int idNFC) {
        this.idNFC = idNFC;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha_activo() {
        return fecha_activo;
    }

    public void setFecha_activo(String fecha_activo) {
        this.fecha_activo = fecha_activo;
    }

    public String getFecha_inactivo() {
        return fecha_inactivo;
    }

    public void setFecha_inactivo(String fecha_inactivo) {
        this.fecha_inactivo = fecha_inactivo;
    }

    public int isActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public void GuardarNFC(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("INSERT INTO nfc (idNFC, codigo, fecha_activo, fecha_fin, estado)"
                + " VALUES (NULL,?,?,?,?)");
        stmt.setString(1, getCodigo());
        stmt.setString(2, getFecha_activo());
        stmt.setString(3, getFecha_inactivo());
        stmt.setInt(4, isActivo());
        stmt.executeUpdate();
    }

    public void EliminarNFC(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("delete from nfc where idNFC = ?");
        stmt.setInt(1, getIdNFC());
        stmt.executeUpdate();
    }

    public void EliminarUltimoRegistro(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareCall("delete from nfc order by nfc.idNFC limit 1");
        stmt.executeUpdate();
    }

    /*  public boolean ValidarEstado(Connection con) throws SQLException {
     boolean b = false;
     PreparedStatement stmt = con.prepareStatement("SELECT * FROM NFC WHERE estado=?");
     stmt.setString(1, getUser());
     stmt.setString(2, getPass());
     ResultSet r = stmt.executeQuery();
     if (r.next()) {
     setIdRol(r.getInt("rol_id"));
     b = true;
     }

     return b;
     }*/
   public LinkedList ListarNFCActivos(Connection con) throws SQLException {
       
       
       PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM personas a INNER JOIN nfc b ON a.nfc_id=b.idNFC WHERE a.rol_id > 1");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            NFC nfc = new NFC();
            nfc.setIdNFC(result.getInt("idNFC"));
            nfc.setCodigo(result.getString("codigo"));
            nfc.setFecha_activo(result.getString("fecha_activo"));
            nfc.setFecha_inactivo(result.getString("fecha_fin"));
            nfc.setActivo(result.getInt("estado"));
            nfc.setIdPersona(result.getString("idPersonas"));
            nfc.setNombres(result.getString("nombre")+ " " + result.getString("apellido"));

            list.add(nfc);
        }
        return list; 
    }
   
    public LinkedList ListarPersonasSinNFC(Connection con) throws SQLException {
       
       
       PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM personas WHERE nfc_id IS NULL && rol_id > 1");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            NFC nfc = new NFC();
            nfc.setIdPersona(result.getString("idPersonas"));
            nfc.setNombres(result.getString("nombre")+ " " + result.getString("apellido"));

            list.add(nfc);
        }
        return list; 
    }
   
    public LinkedList ListarNFCyPersonas(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(
                "SELECT * FROM NFC");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            NFC nfc = new NFC();
            nfc.setIdNFC(result.getInt("idNFC"));
            nfc.setCodigo(result.getString("codigo"));
            nfc.setFecha_activo(result.getString("fecha_activo"));
            nfc.setFecha_inactivo(result.getString("fecha_fin"));
            nfc.setActivo(result.getInt("estado"));

            list.add(nfc);
        }
        return list; 
    }

    public boolean BuscarNFC(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM nfc WHERE codigo = ?");
        stmt.setString(1, getCodigo());
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdNFC(r.getInt("idNFC"));
            setCodigo(r.getString("codigo"));
            setFecha_activo(r.getString("fecha_activo"));
            setFecha_inactivo(r.getString("fecha_fin"));
            setActivo(r.getInt("estado"));

            b = true;
        }

        return b;
    }

    public boolean BuscarPorIDultimoRegistro(Connection con) throws SQLException {
        boolean b = false;
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM nfc ORDER BY idNFC DESC LIMIT 1");
        ResultSet r = stmt.executeQuery();
        if (r.next()) {
            setIdNFC(r.getInt("idNFC"));
            setCodigo(r.getString("codigo"));
            setFecha_activo(r.getString("fecha_activo"));
            setFecha_inactivo(r.getString("fecha_fin"));
            setActivo(r.getInt("estado"));

            b = true;
        }

        return b;
    }

    public LinkedList NFCInactivo(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM NFC WHERE estado = 0");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            NFC nfc = new NFC();
            nfc.setIdNFC(result.getInt("idNFC"));
            list.add(nfc);
        }
        return list;

    }
    
     public void DesvincularNFCPersona(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE personas SET nfc_id = null  WHERE  idPersonas = ? ");
        stmt.setString(1, getIdPersona());
        stmt.executeUpdate();
    }
     
     public void VincularNFCPersona(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("UPDATE personas SET nfc_id = ?  WHERE  idPersonas = ? ");
        stmt.setString(2, getIdPersona());
        stmt.setInt(1, getIdNFC());
        
        stmt.executeUpdate();
    }

    public LinkedList NFCactivo(Connection con) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM NFC WHERE estado = 1");
        LinkedList list = new LinkedList();
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            NFC nfc = new NFC();
            nfc.setIdNFC(result.getInt("idNFC"));
            list.add(nfc);
        }
        return list;

    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

}
