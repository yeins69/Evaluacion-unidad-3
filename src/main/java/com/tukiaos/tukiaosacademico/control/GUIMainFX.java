package com.tukiaos.tukiaosacademico.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class GUIMainFX {

    @Autowired
    private ApplicationContext context;

    @FXML
    private TabPane tabPaneFx;
    @FXML
    private BorderPane bp;
    @FXML
    private MenuBar menuBarFx;

    @FXML
    public void initialize() {
        // Menú "Gestión"
        Menu menuGestion = new Menu("Gestión");

        // Item "Matrícula"
        MenuItem itemMatricula = new MenuItem("Gestión de Matrícula");
        itemMatricula.setOnAction(e -> abrirFormularioMatricula());

        menuGestion.getItems().add(itemMatricula);
        menuBarFx.getMenus().add(menuGestion);

        bp.setTop(menuBarFx);
        bp.setCenter(tabPaneFx);
    }

    private void abrirFormularioMatricula() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-apoderado.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent formulario = loader.load();

            ScrollPane scrollPane = new ScrollPane(formulario);
            Tab tabMatricula = new Tab("Apoderado", scrollPane);
            tabPaneFx.getTabs().add(tabMatricula);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
