//package com.crud;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestDB {
//    @Test
//    public void testInsertar(){
//        int antes=AgendaDB.obtenerPersonas().size();
//        AgendaDB.agregarPersona("Prueba JUnit","Direccion Test","1111,2222");
//        int despues=AgendaDB.obtenerPersonas().size();
//        assertEquals(antes+1,despues);
//    }
//
//    @Test
//    public void testEliminar(){
//        AgendaDB.agregarPersona("Persona Para Borrar","Calle 0","0000");
//        var lista=AgendaDB.obtenerPersonas();
//        String[] ultimo=lista.get(lista.size()-1);
//        int id=Integer.parseInt(ultimo[0]);
//
//        AgendaDB.borrarPersona(id);
//        var listaNueva=AgendaDB.obtenerPersonas();
//        assertTrue(listaNueva.stream().noneMatch(p->p[0].equals(String.valueOf(id))));
//    }
//}