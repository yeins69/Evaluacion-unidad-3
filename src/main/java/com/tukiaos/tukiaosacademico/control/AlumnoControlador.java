package com.tukiaos.tukiaosacademico.control;

import com.tukiaos.tukiaosacademico.Modelo.Alumno;
import com.tukiaos.tukiaosacademico.Modelo.Apoderado;
import com.tukiaos.tukiaosacademico.Servicio.AlumnoServicio;
import com.tukiaos.tukiaosacademico.Servicio.ApoderadoServicio;
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
public class AlumnoControlador {

    @Autowired
    private AlumnoServicio alumnoServicio;

    @Autowired
    private ApoderadoServicio apoderadoServicio;

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtDni;
    @FXML
    private DatePicker dpFechaCumpleanos;
    @FXML
    private ComboBox<Apoderado> cbxApoderado;
    @FXML
    private Label lbnMsg;
    @FXML
    private TableView<Alumno> tableView;

    private ObservableList<Alumno> alumnoList;

    private Alumno alumnoSeleccionado = null;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarApoderados();
        cargarDatos();

        tableView.setRowFactory(tv -> {
            TableRow<Alumno> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    alumnoSeleccionado = row.getItem();
                    cargarFormularioDesdeTabla();
                }
            });
            return row;
        });
    }

    private void configurarTabla() {
        TableColumn<Alumno, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idAlumno"));

        TableColumn<Alumno, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));

        TableColumn<Alumno, String> colApellidoPaterno = new TableColumn<>("Apellido Paterno");
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));

        TableColumn<Alumno, String> colApellidoMaterno = new TableColumn<>("Apellido Materno");
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));


        TableColumn<Alumno, String> colDni = new TableColumn<>("Dni");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));

        tableView.getColumns().addAll(colId, colNombres, colApellidoPaterno, colApellidoMaterno,colDni);
    }

    private void cargarApoderados() {
        List<Apoderado> apoderados = apoderadoServicio.listarEntidad();
        cbxApoderado.setItems(FXCollections.observableArrayList(apoderados));
    }

    @FXML
    public void eliminarAlumno(ActionEvent event) {
        if (alumnoSeleccionado == null) {
            lbnMsg.setText("Seleccione un alumno para eliminar.");
            return;
        }

        try {
            alumnoServicio.eliminarRegEntidad(alumnoSeleccionado.getIdAlumno());
            lbnMsg.setText("Alumno eliminado exitosamente.");
            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            lbnMsg.setText("Error al eliminar: " + e.getMessage());
        }
    }


    private void cargarDatos() {
        List<Alumno> lista = alumnoServicio.listarEntidad();
        alumnoList = FXCollections.observableArrayList(lista);
        tableView.setItems(alumnoList);
    }

    @FXML
    public void validarFormulario(ActionEvent event) {
        try {
            if (camposVacios()) {
                lbnMsg.setText("Por favor complete todos los campos.");
                return;
            }

            if (alumnoSeleccionado == null) {
                Alumno alumno = Alumno.builder()
                        .nombres(txtNombres.getText())
                        .apellidoPaterno(txtApellidoPaterno.getText())
                        .apellidoMaterno(txtApellidoMaterno.getText())
                        .dni(txtDni.getText())
                        .fechaCumpleaños(dpFechaCumpleanos.getValue())
                        .apoderado(cbxApoderado.getValue())
                        .build();

                alumnoServicio.guardarEntidad(alumno);
                lbnMsg.setText("Alumno guardado exitosamente.");
            } else {
                alumnoSeleccionado.setNombres(txtNombres.getText());
                alumnoSeleccionado.setApellidoPaterno(txtApellidoPaterno.getText());
                alumnoSeleccionado.setApellidoMaterno(txtApellidoMaterno.getText());
                alumnoSeleccionado.setDni(txtDni.getText());
                alumnoSeleccionado.setFechaCumpleaños(dpFechaCumpleanos.getValue());
                alumnoSeleccionado.setApoderado(cbxApoderado.getValue());

                alumnoServicio.actualizarEntidad(alumnoSeleccionado);
                lbnMsg.setText("Alumno actualizado exitosamente.");
            }

            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            lbnMsg.setText("Error: " + e.getMessage());
        }
    }

    private boolean camposVacios() {
        return txtNombres.getText().isEmpty() || txtApellidoPaterno.getText().isEmpty() ||
               txtApellidoMaterno.getText().isEmpty() || txtDni.getText().isEmpty() ||
               dpFechaCumpleanos.getValue() == null || cbxApoderado.getValue() == null;
    }

    private void cargarFormularioDesdeTabla() {
        txtNombres.setText(alumnoSeleccionado.getNombres());
        txtApellidoPaterno.setText(alumnoSeleccionado.getApellidoPaterno());
        txtApellidoMaterno.setText(alumnoSeleccionado.getApellidoMaterno());
        txtDni.setText(alumnoSeleccionado.getDni());
        dpFechaCumpleanos.setValue(alumnoSeleccionado.getFechaCumpleaños());
        cbxApoderado.setValue(alumnoSeleccionado.getApoderado());

    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        txtDni.clear();
        dpFechaCumpleanos.setValue(null);
        cbxApoderado.setValue(null);
        alumnoSeleccionado = null;
    }
}
