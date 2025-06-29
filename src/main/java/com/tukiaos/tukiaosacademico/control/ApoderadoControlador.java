package com.tukiaos.tukiaosacademico.control;

import com.tukiaos.tukiaosacademico.Modelo.Apoderado;
import com.tukiaos.tukiaosacademico.Modelo.Profesor;
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
public class ApoderadoControlador {

    @Autowired
    private ApoderadoServicio apoderadoServicio;

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtCelular;
    @FXML
    private TextField txtDireccion;
    @FXML
    private Label lbnMsg;
    @FXML
    private TableView<Apoderado> tableView;

    private ObservableList<Apoderado> apoderadoList;
    private Apoderado apoderadoSeleccionado;

    @FXML
    public void initialize() {
        configurarTabla();
        cargarDatos();

        tableView.setOnMouseClicked(event -> {
            apoderadoSeleccionado = tableView.getSelectionModel().getSelectedItem();
            if (apoderadoSeleccionado != null) {
                cargarFormulario(apoderadoSeleccionado);
            }
        });
    }

    private void configurarTabla() {
        TableColumn<Apoderado, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idApoderado"));

        TableColumn<Apoderado, String> colNombres = new TableColumn<>("Nombres");
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));

        TableColumn<Apoderado, String> colApellidoPaterno = new TableColumn<>("Apellido Paterno");
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));

        TableColumn<Apoderado, String> colApellidoMaterno = new TableColumn<>("Apellido Materno");
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));

        TableColumn<Apoderado, String> colCelular = new TableColumn<>("Celular");
        colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));


        tableView.getColumns().addAll(colId, colNombres, colApellidoPaterno, colApellidoMaterno,colCelular);
    }

    private void cargarDatos() {
        List<Apoderado> lista = apoderadoServicio.listarEntidad();
        apoderadoList = FXCollections.observableArrayList(lista);
        tableView.setItems(apoderadoList);
    }

    @FXML
    public void guardar(ActionEvent event) {
        try {
            if (camposVacios()) {
                lbnMsg.setText("Todos los campos son obligatorios.");
                return;
            }

            if (apoderadoSeleccionado == null) {
                Apoderado apoderado = Apoderado.builder()
                        .nombres(txtNombres.getText())
                        .apellidoPaterno(txtApellidoPaterno.getText())
                        .apellidoMaterno(txtApellidoMaterno.getText())
                        .celular(txtCelular.getText())
                        .direccion(txtDireccion.getText())
                        .build();

                apoderadoServicio.guardarEntidad(apoderado);
                lbnMsg.setText("Apoderado guardado exitosamente.");
            } else {
                apoderadoSeleccionado.setNombres(txtNombres.getText());
                apoderadoSeleccionado.setApellidoPaterno(txtApellidoPaterno.getText());
                apoderadoSeleccionado.setApellidoMaterno(txtApellidoMaterno.getText());
                apoderadoSeleccionado.setCelular(txtCelular.getText());
                apoderadoSeleccionado.setDireccion(txtDireccion.getText());

                apoderadoServicio.guardarEntidad(apoderadoSeleccionado);
                lbnMsg.setText("Apoderado actualizado exitosamente.");
            }

            limpiarFormulario();
            cargarDatos();
            apoderadoSeleccionado = null;
        } catch (Exception e) {
            lbnMsg.setText("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    public void eliminar(ActionEvent event) {
        if (apoderadoSeleccionado != null) {
            apoderadoServicio.eliminarRegEntidad(apoderadoSeleccionado.getIdApoderado());
            lbnMsg.setText("Apoderado eliminado correctamente.");
            limpiarFormulario();
            cargarDatos();
            apoderadoSeleccionado = null;
        } else {
            lbnMsg.setText("Seleccione un apoderado para eliminar.");
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        limpiarFormulario();
        apoderadoSeleccionado = null;
        lbnMsg.setText("Formulario limpio.");
    }

    private boolean camposVacios() {
        return txtNombres.getText().isEmpty() || txtApellidoPaterno.getText().isEmpty() ||
               txtApellidoMaterno.getText().isEmpty() || txtCelular.getText().isEmpty() ||
               txtDireccion.getText().isEmpty();
    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidoPaterno.clear();
        txtApellidoMaterno.clear();
        txtCelular.clear();
        txtDireccion.clear();
    }

    private void cargarFormulario(Apoderado apoderado) {
        txtNombres.setText(apoderado.getNombres());
        txtApellidoPaterno.setText(apoderado.getApellidoPaterno());
        txtApellidoMaterno.setText(apoderado.getApellidoMaterno());
        txtCelular.setText(apoderado.getCelular());
        txtDireccion.setText(apoderado.getDireccion());
    }
}
