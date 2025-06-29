package com.tukiaos.tukiaosacademico.control;

import com.tukiaos.tukiaosacademico.Modelo.Alumno;
import com.tukiaos.tukiaosacademico.Modelo.Curso;
import com.tukiaos.tukiaosacademico.Modelo.Matricula;
import com.tukiaos.tukiaosacademico.Servicio.AlumnoServicio;
import com.tukiaos.tukiaosacademico.Servicio.CursoServicio;
import com.tukiaos.tukiaosacademico.Servicio.MatriculaServicio;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class MatriculaControlador {

    @FXML private DatePicker dpFechaMatricula;
    @FXML private TextField txtCosto;
    @FXML private ComboBox<Curso> cbxCurso;
    @FXML private ComboBox<Alumno> cbxAlumno;
    @FXML private ComboBox<String> cbxEstado;
    @FXML private Label lbnMsg;
    @FXML private TableView<Matricula> tableView;
    @FXML private TextField txtFiltroDato;

    @Autowired private MatriculaServicio matriculaServicio;
    @Autowired private CursoServicio cursoServicio;
    @Autowired private AlumnoServicio alumnoServicio;

    private ObservableList<Matricula> datos;

    // Variable para saber si estamos editando
    private Matricula matriculaSeleccionada;

    @FXML
    public void initialize() {
        cargarCombos();
        cargarTabla();
        limpiarFormulario();
        configurarEventosTabla();
    }

    private void cargarCombos() {
        cbxCurso.setItems(FXCollections.observableArrayList(cursoServicio.listarEntidad()));
        cbxAlumno.setItems(FXCollections.observableArrayList(alumnoServicio.listarEntidad()));
        cbxEstado.setItems(FXCollections.observableArrayList(Arrays.asList("Activo", "Inactivo")));
    }

    private void cargarTabla() {
        TableColumn<Matricula, String> colFecha = new TableColumn<>("Fecha Matrícula");
        colFecha.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFechaMatricula().toString()));

        TableColumn<Matricula, String> colCosto = new TableColumn<>("Costo");
        colCosto.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCosto()));

        TableColumn<Matricula, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(cellData -> {
            Curso curso = cellData.getValue().getCurso();
            return new ReadOnlyStringWrapper(curso != null ? curso.getNombreCurso() : "");
        });

        TableColumn<Matricula, String> colAlumno = new TableColumn<>("Alumno");
        colAlumno.setCellValueFactory(cellData -> {
            Alumno alumno = cellData.getValue().getAlumno();
            return new ReadOnlyStringWrapper(alumno != null ? alumno.getNombres() : "");
        });

        TableColumn<Matricula, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEstado()));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(colFecha, colCosto, colCurso, colAlumno, colEstado);

        listarDatos();
    }

    private void listarDatos() {
        List<Matricula> lista = matriculaServicio.listarEntidad();
        datos = FXCollections.observableArrayList(lista);
        tableView.setItems(datos);
    }

    private void configurarEventosTabla() {
        tableView.setRowFactory(tv -> {
            TableRow<Matricula> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    matriculaSeleccionada = row.getItem();
                    cargarFormulario(matriculaSeleccionada);
                }
            });
            return row;
        });
    }

    private void cargarFormulario(Matricula matricula) {
        dpFechaMatricula.setValue(matricula.getFechaMatricula());
        txtCosto.setText(matricula.getCosto());
        cbxCurso.setValue(matricula.getCurso());
        cbxAlumno.setValue(matricula.getAlumno());
        cbxEstado.setValue(matricula.getEstado());
    }

    @FXML
    public void validarFormulario() {
        try {
            LocalDate fecha = dpFechaMatricula.getValue();
            String costo = txtCosto.getText();
            Curso curso = cbxCurso.getValue();
            Alumno alumno = cbxAlumno.getValue();
            String estado = cbxEstado.getValue();

            if (fecha == null || costo.isEmpty() || curso == null || alumno == null || estado == null) {
                lbnMsg.setText("Complete todos los campos.");
                return;
            }

            if (matriculaSeleccionada == null) {
                // Nueva matricula
                Matricula nuevaMatricula = Matricula.builder()
                        .fechaMatricula(fecha)
                        .costo(costo)
                        .curso(curso)
                        .alumno(alumno)
                        .estado(estado)
                        .build();

                matriculaServicio.guardarEntidad(nuevaMatricula);
                lbnMsg.setText("Matrícula guardada correctamente.");
            } else {
                // Editar matricula existente
                matriculaSeleccionada.setFechaMatricula(fecha);
                matriculaSeleccionada.setCosto(costo);
                matriculaSeleccionada.setCurso(curso);
                matriculaSeleccionada.setAlumno(alumno);
                matriculaSeleccionada.setEstado(estado);

                matriculaServicio.guardarEntidad(matriculaSeleccionada);
                lbnMsg.setText("Matrícula actualizada correctamente.");
            }

            listarDatos();
            limpiarFormulario();
        } catch (Exception e) {
            lbnMsg.setText("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarMatricula() {
        try {
            Matricula seleccionada = tableView.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                lbnMsg.setText("Seleccione una matrícula para eliminar.");
                return;
            }

            matriculaServicio.eliminarRegEntidad(seleccionada.getIdMatricula());
            lbnMsg.setText("Matrícula eliminada correctamente.");
            listarDatos();
            limpiarFormulario();
        } catch (Exception e) {
            lbnMsg.setText("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        dpFechaMatricula.setValue(null);
        txtCosto.clear();
        cbxCurso.getSelectionModel().clearSelection();
        cbxAlumno.getSelectionModel().clearSelection();
        cbxEstado.getSelectionModel().clearSelection();
        matriculaSeleccionada = null;
    }
}
