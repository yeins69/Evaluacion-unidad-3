package com.tukiaos.tukiaosacademico.control;

import com.tukiaos.tukiaosacademico.Modelo.Profesor;
import com.tukiaos.tukiaosacademico.Servicio.ProfesorServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProfesorControlado {

    @Autowired
    private ProfesorServicio profesorServicio;

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtDni;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtDireccion;
    @FXML
    private DatePicker dpFechaContratacion;
    @FXML
    private Label lbnMsg;
    @FXML
    private TableView<Profesor> tableView;

    private ObservableList<Profesor> profesorList;
    private Profesor profesorSeleccionado;  // NUEVO: para manejar la edición

    @FXML
    public void initialize() {
        cargarColumnas();
        cargarDatos();

        // Agregamos el listener de selección en la tabla
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                profesorSeleccionado = newSelection;
                cargarFormularioDesdeSeleccionado();
            }
        });
    }

    private void cargarColumnas() {
        TableColumn<Profesor, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idProfesor"));

        TableColumn<Profesor, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));

        TableColumn<Profesor, String> colApellidoPaterno = new TableColumn<>("Apellido Paterno");
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));

        TableColumn<Profesor, String> colApellidoMaterno = new TableColumn<>("Apellido Materno");
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));

        TableColumn<Profesor, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        TableColumn<Profesor, String> colCelular = new TableColumn<>("Celular");
        colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));

        tableView.getColumns().addAll(colId, colNombres, colApellidoPaterno, colApellidoMaterno,colDni,colCelular);
    }

    private void cargarDatos() {
        List<Profesor> lista = profesorServicio.listarEntidad();
        profesorList = FXCollections.observableArrayList(lista);
        tableView.setItems(profesorList);
    }

    @FXML
    public void validarFormulario(ActionEvent event) {
        try {
            if (profesorSeleccionado == null) {  // Si no hay selección, es nuevo
                Profesor profesor = Profesor.builder()
                        .nombres(txtNombres.getText())
                        .apellidoPaterno(txtApellidoPaterno.getText())
                        .apellidoMaterno(txtApellidoMaterno.getText())
                        .dni(txtDni.getText())
                        .celular(txtCelular.getText())
                        .direccion(txtDireccion.getText())
                        .fechaContratacion(dpFechaContratacion.getValue())
                        .build();
                profesorServicio.guardarEntidad(profesor);
                lbnMsg.setText("Profesor guardado exitosamente.");
            } else { // Actualizar el existente
                profesorSeleccionado.setNombres(txtNombres.getText());
                profesorSeleccionado.setApellidoPaterno(txtApellidoPaterno.getText());
                profesorSeleccionado.setApellidoMaterno(txtApellidoMaterno.getText());
                profesorSeleccionado.setDni(txtDni.getText());
                profesorSeleccionado.setCelular(txtCelular.getText());
                profesorSeleccionado.setDireccion(txtDireccion.getText());
                profesorSeleccionado.setFechaContratacion(dpFechaContratacion.getValue());

                profesorServicio.guardarEntidad(profesorSeleccionado);
                lbnMsg.setText("Profesor actualizado correctamente.");
            }

            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            lbnMsg.setText("Error al guardar:");
            // aquí opcionalmente puedes loguear e.getMessage() en la consola para ti:
            e.printStackTrace();

            //lbnMsg.setText("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    public void eliminarProfesor(ActionEvent event) {
        try {
            if (profesorSeleccionado != null) {
                profesorServicio.eliminarRegEntidad(profesorSeleccionado.getIdProfesor());
                lbnMsg.setText("Profesor eliminado correctamente.");
                limpiarFormulario();
                cargarDatos();
            } else {
                lbnMsg.setText("Seleccione un profesor para eliminar.");
            }
        } catch (Exception e) {
            lbnMsg.setText("Error al eliminar: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        txtDni.clear();
        txtCelular.clear();
        txtDireccion.clear();
        dpFechaContratacion.setValue(null);
        profesorSeleccionado = null;
        tableView.getSelectionModel().clearSelection();
    }

    private void cargarFormularioDesdeSeleccionado() {
        if (profesorSeleccionado != null) {
            txtNombres.setText(profesorSeleccionado.getNombres());
            txtApellidoPaterno.setText(profesorSeleccionado.getApellidoPaterno());
            txtApellidoMaterno.setText(profesorSeleccionado.getApellidoMaterno());
            txtDni.setText(profesorSeleccionado.getDni());
            txtCelular.setText(profesorSeleccionado.getCelular());
            txtDireccion.setText(profesorSeleccionado.getDireccion());
            dpFechaContratacion.setValue(profesorSeleccionado.getFechaContratacion());
        }
    }
}
