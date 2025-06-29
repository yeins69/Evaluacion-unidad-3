package com.tukiaos.tukiaosacademico.control;

import com.tukiaos.tukiaosacademico.Modelo.Aula;
import com.tukiaos.tukiaosacademico.Modelo.Curso;
import com.tukiaos.tukiaosacademico.Modelo.Profesor;
import com.tukiaos.tukiaosacademico.Servicio.AulaServicio;
import com.tukiaos.tukiaosacademico.Servicio.CursoServicio;
import com.tukiaos.tukiaosacademico.Servicio.ProfesorServicio;
import com.tukiaos.tukiaosacademico.dto.ComboBoxOption;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CursoControlador {

    @Autowired
    private CursoServicio cursoServicio;

    @Autowired
    private ProfesorServicio profesorServicio;

    @Autowired
    private AulaServicio aulaServicio;

    @FXML
    private TextField nombreCursoField, horasSemanaField;
    @FXML
    private ComboBox<String> profesorComboBox, aulaComboBox;
    @FXML
    private Label lbnMsg;
    @FXML
    private TableView<Curso> tableView;

    private ObservableList<Curso> cursoList;

    private Map<String, Long> profesorMap = new HashMap<>();
    private Map<String, Long> aulaMap = new HashMap<>();

    private Curso cursoSeleccionado; // NUEVO: para editar y eliminar

    @FXML
    public void initialize() {
        configurarTabla();
        cargarProfesores();
        cargarAulas();
        cargarDatos();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cursoSeleccionado = newSelection;
                cargarFormularioDesdeSeleccionado();
            }
        });
    }

    private void configurarTabla() {
        TableColumn<Curso, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idCurso"));

        TableColumn<Curso, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));

        TableColumn<Curso, String> colHoras = new TableColumn<>("Horas Semana");
        colHoras.setCellValueFactory(new PropertyValueFactory<>("horasSemana"));

        TableColumn<Curso, String> colProfesor = new TableColumn<>("Profesor");
        colProfesor.setCellValueFactory(cellData -> {
            Profesor prof = cellData.getValue().getProfesor();
            return new ReadOnlyStringWrapper(prof != null ? prof.getNombres() : "");
        });

        TableColumn<Curso, String> colAula = new TableColumn<>("Aula");
        colAula.setCellValueFactory(cellData -> {
            Aula aula = cellData.getValue().getAula();
            return new ReadOnlyStringWrapper(aula != null ? aula.getNombreAula() : "");
        });

        tableView.getColumns().addAll(colId, colNombre, colHoras, colProfesor, colAula);
    }

    private void cargarDatos() {
        List<Curso> lista = cursoServicio.listarEntidad();
        cursoList = FXCollections.observableArrayList(lista);
        tableView.setItems(cursoList);
    }

    private void cargarProfesores() {
        profesorComboBox.getItems().clear();
        profesorMap.clear();
        List<ComboBoxOption> lista = profesorServicio.listarCombobox();
        for (ComboBoxOption op : lista) {
            profesorComboBox.getItems().add(op.getValue());
            profesorMap.put(op.getValue(), Long.valueOf(op.getKey()));
        }
    }

    private void cargarAulas() {
        aulaComboBox.getItems().clear();
        aulaMap.clear();
        List<ComboBoxOption> lista = aulaServicio.listarCombobox();
        for (ComboBoxOption op : lista) {
            aulaComboBox.getItems().add(op.getValue());
            aulaMap.put(op.getValue(), Long.valueOf(op.getKey()));
        }
    }

    @FXML
    public void validarFormulario(ActionEvent event) {
        try {
            String profesorSeleccionadoStr = profesorComboBox.getSelectionModel().getSelectedItem();
            String aulaSeleccionadaStr = aulaComboBox.getSelectionModel().getSelectedItem();

            if (profesorSeleccionadoStr == null || aulaSeleccionadaStr == null) {
                lbnMsg.setText("Seleccione Profesor y Aula.");
                return;
            }

            Long idProfesor = profesorMap.get(profesorSeleccionadoStr);
            Profesor profesor = profesorServicio.buscarEntidad(idProfesor);

            Long idAula = aulaMap.get(aulaSeleccionadaStr);
            Aula aula = aulaServicio.buscarEntidad(idAula);

            if (cursoSeleccionado == null) {
                Curso nuevoCurso = Curso.builder()
                        .nombreCurso(nombreCursoField.getText())
                        .horasSemana(horasSemanaField.getText())
                        .profesor(profesor)
                        .aula(aula)
                        .build();

                cursoServicio.guardarEntidad(nuevoCurso);
                lbnMsg.setText("Curso guardado correctamente.");
            } else {
                cursoSeleccionado.setNombreCurso(nombreCursoField.getText());
                cursoSeleccionado.setHorasSemana(horasSemanaField.getText());
                cursoSeleccionado.setProfesor(profesor);
                cursoSeleccionado.setAula(aula);
                cursoServicio.guardarEntidad(cursoSeleccionado);
                lbnMsg.setText("Curso actualizado correctamente.");
            }

            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            lbnMsg.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminarCurso(ActionEvent event) {
        try {
            if (cursoSeleccionado != null) {
                cursoServicio.eliminarRegEntidad(cursoSeleccionado.getIdCurso());
                lbnMsg.setText("Curso eliminado correctamente.");
                limpiarFormulario();
                cargarDatos();
            } else {
                lbnMsg.setText("Seleccione un curso para eliminar.");
            }
        } catch (Exception e) {
            lbnMsg.setText("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        nombreCursoField.clear();
        horasSemanaField.clear();
        profesorComboBox.getSelectionModel().clearSelection();
        aulaComboBox.getSelectionModel().clearSelection();
        cursoSeleccionado = null;
        tableView.getSelectionModel().clearSelection();
    }

    private void cargarFormularioDesdeSeleccionado() {
        if (cursoSeleccionado != null) {
            nombreCursoField.setText(cursoSeleccionado.getNombreCurso());
            horasSemanaField.setText(cursoSeleccionado.getHorasSemana());
            profesorComboBox.getSelectionModel().select(cursoSeleccionado.getProfesor().getNombres());
            aulaComboBox.getSelectionModel().select(cursoSeleccionado.getAula().getNombreAula());
        }
    }
}
