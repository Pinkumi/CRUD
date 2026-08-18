package com.crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDB {
    private static final String URL="jdbc:mariadb://localhost:3306/agenda";
    private static final String USER="usuario1";
    private static final String PASSWORD="superpassword";

    public static Connection getConexion()throws Exception{
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }

    public static List<String[]> obtenerPersonas(){
        List<String[]> lista=new ArrayList<>();
        try{
            Connection c=getConexion();
            Statement s=c.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM Personas");
            while(rs.next()){
                int id=rs.getInt("id");
                String nom=rs.getString("nombre");
                String dir=rs.getString("direccion");

                Statement s2=c.createStatement();
                ResultSet rs2=s2.executeQuery("SELECT telefono FROM Telefonos WHERE personaId="+id);
                String tels="";
                while(rs2.next()){
                    tels+=rs2.getString("telefono")+",";
                }
                rs2.close();
                s2.close();
                lista.add(new String[]{String.valueOf(id),nom,dir,tels});
            }
            rs.close();
            s.close();
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return lista;
    }

    public static void agregarPersona(String nombre,String direccion,String telefonos){
        try{
            Connection c=getConexion();
            PreparedStatement ps=c.prepareStatement("INSERT INTO Personas(nombre,direccion) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,nombre);
            ps.setString(2,direccion);
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            int id=0;
            if(rs.next()){
                id=rs.getInt(1);
            }
            rs.close();
            ps.close();

            if(!telefonos.trim().isEmpty()){
                String[] tArr=telefonos.split(",");
                for(String t:tArr){
                    if(!t.trim().isEmpty()){
                        PreparedStatement ps2=c.prepareStatement("INSERT INTO Telefonos(personaId,telefono) VALUES(?,?)");
                        ps2.setInt(1,id);
                        ps2.setString(2,t.trim());
                        ps2.executeUpdate();
                        ps2.close();
                    }
                }
            }
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void modificarPersona(int id,String nombre,String direccion,String telefonos){
        try{
            Connection c=getConexion();
            PreparedStatement ps=c.prepareStatement("UPDATE Personas SET nombre=?,direccion=? WHERE id=?");
            ps.setString(1,nombre);
            ps.setString(2,direccion);
            ps.setInt(3,id);
            ps.executeUpdate();
            ps.close();

            PreparedStatement ps2=c.prepareStatement("DELETE FROM Telefonos WHERE personaId=?");
            ps2.setInt(1,id);
            ps2.executeUpdate();
            ps2.close();

            if(!telefonos.trim().isEmpty()){
                String[] tArr=telefonos.split(",");
                for(String t:tArr){
                    if(!t.trim().isEmpty()){
                        PreparedStatement ps3=c.prepareStatement("INSERT INTO Telefonos(personaId,telefono) VALUES(?,?)");
                        ps3.setInt(1,id);
                        ps3.setString(2,t.trim());
                        ps3.executeUpdate();
                        ps3.close();
                    }
                }
            }
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void borrarPersona(int id){
        try{
            Connection c=getConexion();
            PreparedStatement ps=c.prepareStatement("DELETE FROM Personas WHERE id=?");
            ps.setInt(1,id);
            ps.executeUpdate();
            ps.close();
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}