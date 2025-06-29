package com.tukiaos.tukiaosacademico.control;

import com.tukiaos.tukiaosacademico.Modelo.Aula;
import com.tukiaos.tukiaosacademico.Servicio.AulaServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AulaControlador {

    @Autowired
    private AulaServicio aulaServicio;

    @FXML
    private TextField txtNombreAula;
    @FXML
    private Label lbnMsg;
    @FXML
    private TableView<Aula> tableView;

    private ObservableList<Aula> aulaList;
    private Aula aulaSeleccionada;  // para editar o eliminar

    @FXML
    public void initialize() {
        configurarTabla();
        cargarDatos();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                aulaSeleccionada = newSelection;
                cargarFormularioDesdeSeleccionado();
            }
        });
    }

    private void configurarTabla() {
        TableColumn<Aula, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idAula"));

        TableColumn<Aula, String> colNombre = new TableColumn<>("Nombre Aula");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAula"));

        tableView.getColumns().addAll(colId, colNombre);
    }

    private void cargarDatos() {
        List<Aula> lista = aulaServicio.listarEntidad();
        aulaList = FXCollections.observableArrayList(lista);
        tableView.setItems(aulaList);
    }

    @FXML
    public void validarFormulario(ActionEvent event) {
        try {
            if (aulaSeleccionada == null) {
                Aula aula = Aula.builder()
                        .nombreAula(txtNombreAula.getText())
                        .build();

                aulaServicio.guardarEntidad(aula);
                lbnMsg.setText("Aula guardada exitosamente.");
            } else {
                aulaSeleccionada.setNombreAula(txtNombreAula.getText());
                aulaServicio.guardarEntidad(aulaSeleccionada);
                lbnMsg.setText("Aula actualizada correctamente.");
            }

            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            lbnMsg.setText("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarAula(ActionEvent event) {
        try {
            if (aulaSeleccionada != null) {
                aulaServicio.eliminarRegEntidad(aulaSeleccionada.getIdAula());
                lbnMsg.setText("Aula eliminada correctamente.");
                limpiarFormulario();
                cargarDatos();
            } else {
                lbnMsg.setText("Seleccione un aula para eliminar.");
            }
        } catch (Exception e) {
            lbnMsg.setText("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        txtNombreAula.clear();
        aulaSeleccionada = null;
        tableView.getSelectionModel().clearSelection();
    }

    private void cargarFormularioDesdeSeleccionado() {
        if (aulaSeleccionada != null) {
            txtNombreAula.setText(aulaSeleccionada.getNombreAula());
        }
    }
}
