package com.crud;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    TextField txtId=new TextField();
    TextField txtNombre=new TextField();
    TextField txtDireccion=new TextField();
    TextField txtTelefonos=new TextField();
    ListView<String> listaPersonas=new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        txtId.setDisable(true);

        VBox form=new VBox(5);
        form.setVisible(false);
        form.setManaged(false);

        Button btnNuevo=new Button("Agregar Persona");
        Button btnGuardar=new Button("Guardar");
        Button btnCancelar=new Button("Cancelar");
        Button btnEliminar=new Button("Eliminar");

        btnNuevo.setOnAction(e->{
            limpiar();
            form.setVisible(true);
            form.setManaged(true);
        });

        btnCancelar.setOnAction(e->{
            form.setVisible(false);
            form.setManaged(false);
            limpiar();
        });

        btnGuardar.setOnAction(e->{
            if(txtId.getText().isEmpty()){
                AgendaDB.agregarPersona(txtNombre.getText(),txtDireccion.getText(),txtTelefonos.getText());
            }else{
                int id=Integer.parseInt(txtId.getText());
                AgendaDB.modificarPersona(id,txtNombre.getText(),txtDireccion.getText(),txtTelefonos.getText());
            }
            cargarDatos();
            form.setVisible(false);
            form.setManaged(false);
            limpiar();
        });

        btnEliminar.setOnAction(e->{
            if(!txtId.getText().isEmpty()){
                int id=Integer.parseInt(txtId.getText());
                AgendaDB.borrarPersona(id);
                cargarDatos();
                limpiar();
                form.setVisible(false);
                form.setManaged(false);
            }
        });

        listaPersonas.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
            if(newVal!=null){
                String[] partes=newVal.split(" \\| ");
                txtId.setText(partes[0]);
                txtNombre.setText(partes[1]);
                txtDireccion.setText(partes[2]);
                if(partes.length>3){
                    txtTelefonos.setText(partes[3]);
                }else{
                    txtTelefonos.setText("");
                }
                form.setVisible(true);
                form.setManaged(true);
            }
        });

        form.getChildren().addAll(
                new Label("ID:"),txtId,
                new Label("Nombre:"),txtNombre,
                new Label("Dirección:"),txtDireccion,
                new Label("Teléfonos:"),txtTelefonos,
                new HBox(5,btnGuardar,btnCancelar)
        );

        VBox izquierda=new VBox(10);
        izquierda.getChildren().addAll(
                new Label("Personas Registradas:"),
                listaPersonas,
                new HBox(5,btnNuevo,btnEliminar)
        );

        HBox root=new HBox(15);
        root.getChildren().addAll(izquierda,form);

        cargarDatos();

        Scene scene=new Scene(root,650,380);
        primaryStage.setTitle("CRUD Personas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void cargarDatos(){
        ObservableList<String> items=FXCollections.observableArrayList();
        for(String[] p: AgendaDB.obtenerPersonas()){
            items.add(p[0]+" | "+p[1]+" | "+p[2]+" | "+p[3]);
        }
        listaPersonas.setItems(items);
    }

    void limpiar(){
        txtId.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefonos.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}