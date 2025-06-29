package com.tukiaos.tukiaosacademico.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class MenuPrincipalControlador {

    @Autowired
    private ApplicationContext context;

    @FXML private void abrirApoderados(ActionEvent e) { abrirVentana("/view/main-apoderado.fxml"); }
    @FXML private void abrirAlumnos(ActionEvent e) { abrirVentana("/view/main-alumno.fxml"); }
    @FXML private void abrirProfesores(ActionEvent e) { abrirVentana("/view/main-profesor.fxml"); }
    @FXML private void abrirAulas(ActionEvent e) { abrirVentana("/view/main-aula.fxml"); }
    @FXML private void abrirCursos(ActionEvent e) { abrirVentana("/view/main-curso.fxml"); }
    @FXML private void abrirMatriculas(ActionEvent e) { abrirVentana("/view/main-matricula.fxml"); }
    @FXML private void abrirNotasFinales(ActionEvent e) { abrirVentana("/view/main-notas.fxml"); }
    @FXML private void abrirPensiones(ActionEvent e) { abrirVentana("/view/main-pension.fxml"); }
    @FXML private void abrirPagos(ActionEvent e) { abrirVentana("/view/main-pago.fxml"); }
    @FXML private void abrirPeriodos(ActionEvent e) { abrirVentana("/view/main-periodo.fxml"); }
    @FXML private void abrirUsuarios(ActionEvent e) { abrirVentana("/view/main-usuario.fxml"); }

    @FXML
    private void salir(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        window.hide();
    }

    private void abrirVentana(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            loader.setControllerFactory(context::getBean);
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
