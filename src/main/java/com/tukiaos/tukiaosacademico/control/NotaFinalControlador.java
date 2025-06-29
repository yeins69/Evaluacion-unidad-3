package com.tukiaos.tukiaosacademico.control;
import com.tukiaos.tukiaosacademico.Modelo.Curso;
import com.tukiaos.tukiaosacademico.Servicio.MatriculaServicio;
import com.tukiaos.tukiaosacademico.Modelo.Alumno;
import com.tukiaos.tukiaosacademico.Modelo.Aula;
import com.tukiaos.tukiaosacademico.Modelo.NotaFinal;
import com.tukiaos.tukiaosacademico.Servicio.AlumnoServicio;
import com.tukiaos.tukiaosacademico.Servicio.AulaServicio;
import com.tukiaos.tukiaosacademico.Servicio.CursoServicio;
import com.tukiaos.tukiaosacademico.Servicio.NotaFinalServicio;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Controller
public class NotaFinalControlador {

    @FXML private ComboBox<Aula> cbxAula;
    @FXML private ComboBox<Alumno> cbxAlumno;
    @FXML private TextField txtNota1;
    @FXML private TextField txtNota2;
    @FXML private TextField txtNota3;
    @FXML private TableView<NotaFinal> tableView;
    @FXML private Label lbnMsg;

    @Autowired private NotaFinalServicio notaFinalServicio;
    @Autowired private AulaServicio aulaServicio;
    @Autowired private AlumnoServicio alumnoServicio;
    @Autowired private MatriculaServicio matriculaServicio;
    @Autowired private CursoServicio cursoServicio;

    @FXML
    public void initialize() {
        cargarCombos();
        configurarTabla();
        cargarDatos();
    }

    private void cargarCombos() {
        cbxAula.setItems(FXCollections.observableArrayList(aulaServicio.listarEntidad()));
        cbxAlumno.setItems(FXCollections.observableArrayList(alumnoServicio.listarEntidad()));
    }

    private void configurarTabla() {
        TableColumn<NotaFinal, String> colAula = new TableColumn<>("Aula");
        colAula.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getAula() != null ? cellData.getValue().getAula().getNombreAula() : ""
        ));

        TableColumn<NotaFinal, String> colAlumno = new TableColumn<>("Alumno");
        colAlumno.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getAlumno() != null ? cellData.getValue().getAlumno().getNombres() : ""
        ));
        TableColumn<NotaFinal, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getCurso() != null ? cellData.getValue().getCurso().getNombreCurso() : ""
        ));
        TableColumn<NotaFinal, String> colCalificacion = new TableColumn<>("Promedio");
        colCalificacion.setCellValueFactory(cellData -> {
            Double calificacion = cellData.getValue().getCalificacion();
            String texto = calificacion != null ? String.format("%.1f", calificacion) : "";
            return new ReadOnlyStringWrapper(texto);
        });


        TableColumn<NotaFinal, Double> colFinal = new TableColumn<>("Nota Final");
        colFinal.setCellValueFactory(cellData -> {
            NotaFinal nota = cellData.getValue();
            double promedio = (nota.getNota1() + nota.getNota2() + nota.getNota3()) / 3.0;
            return new javafx.beans.property.SimpleDoubleProperty(promedio).asObject();
        });

        TableColumn<NotaFinal, Double> colNota1 = new TableColumn<>("Nota1");
        colNota1.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nota1"));

        TableColumn<NotaFinal, Double> colNota2 = new TableColumn<>("Nota2");
        colNota2.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nota2"));

        TableColumn<NotaFinal, Double> colNota3 = new TableColumn<>("Nota3");
        colNota3.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nota3"));

        tableView.getColumns().setAll(colAula,colCurso, colAlumno, colNota1, colNota2, colNota3, colCalificacion);
    }

    private void cargarDatos() {
        List<NotaFinal> lista = notaFinalServicio.listarEntidad();
        ObservableList<NotaFinal> datos = FXCollections.observableArrayList(lista);
        tableView.setItems(datos);
    }
    private double getDoubleFromCell(Cell cell) {
        if (cell == null) return 0.0;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }
    @FXML
    private void importarDesdeExcel() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos Excel", "*.xlsx", "*.xls"));
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    Workbook workbook = new XSSFWorkbook(fis);
                    Sheet sheet = workbook.getSheetAt(0);

                    for (Row row : sheet) {
                        if (row.getRowNum() == 0) continue; // Saltar encabezado

                        String aulaNombre = row.getCell(0).getStringCellValue();
                        String alumnoNombre = row.getCell(1).getStringCellValue();
                        String cursoNombre = row.getCell(2).getStringCellValue();
                        double nota1 = getDoubleFromCell(row.getCell(3));
                        double nota2 = getDoubleFromCell(row.getCell(4));
                        double nota3 = getDoubleFromCell(row.getCell(5));

                        Aula aula = aulaServicio.buscarPorNombre(aulaNombre);
                        Alumno alumno = alumnoServicio.buscarPorNombre(alumnoNombre);
                        Curso curso = cursoServicio.buscarPorNombre(cursoNombre);

                        if (aula == null || alumno == null || curso == null) {
                            System.out.println("No se encontró aula, alumno o curso para la fila " + (row.getRowNum() + 1));
                            continue;
                        }

                        // ✅ Validar que el alumno esté matriculado en el curso
                        boolean estaMatriculado = matriculaServicio.alumnoEstaMatriculadoEnCurso(alumno.getIdAlumno(), curso.getIdCurso());
                        if (!estaMatriculado) {
                            System.out.println("El alumno " + alumnoNombre + " no está matriculado en el curso " + cursoNombre);
                            continue;
                        }

                        NotaFinal nota = new NotaFinal();
                        nota.setAula(aula);
                        nota.setAlumno(alumno);
                        nota.setCurso(curso);
                        nota.setNota1(nota1);
                        nota.setNota2(nota2);
                        nota.setNota3(nota3);
                        nota.setCalificacion((nota1 + nota2 + nota3) / 3.0);
                        double promedio = (nota1 + nota2 + nota3) / 3.0;
                        nota.setCalificacion(promedio);
                        notaFinalServicio.guardarEntidad(nota);
                    }
                }

                cargarDatos(); // Actualiza la tabla en pantalla
                lbnMsg.setText("Notas importadas correctamente.");
            }

        } catch (Exception e) {
            lbnMsg.setText("Error al importar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void guardarNota() {
        try {
            Aula aula = cbxAula.getValue();
            Alumno alumno = cbxAlumno.getValue();

            if (aula == null || alumno == null) {
                lbnMsg.setText("Debe seleccionar un aula y un alumno.");
                return;
            }

            // Validar notas
            double nota1 = validarNota(txtNota1.getText(), "Nota 1");
            double nota2 = validarNota(txtNota2.getText(), "Nota 2");
            double nota3 = validarNota(txtNota3.getText(), "Nota 3");

            // Crear objeto y calcular promedio
            NotaFinal nota = new NotaFinal();
            nota.setAula(aula);
            nota.setAlumno(alumno);
            nota.setNota1(nota1);
            nota.setNota2(nota2);
            nota.setNota3(nota3);
            nota.setCalificacion((nota1 + nota2 + nota3) / 3.0);

            notaFinalServicio.guardarEntidad(nota);
            lbnMsg.setText("Nota guardada correctamente.");
            cargarDatos();
        } catch (IllegalArgumentException e) {
            lbnMsg.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            lbnMsg.setText("Error al guardar: " + e.getMessage());
        }
    }
    private double validarNota(String texto, String nombreCampo) {
        if (texto == null || texto.trim().isEmpty())
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacía.");

        double valor;
        try {
            valor = Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(nombreCampo + " debe ser un número.");
        }

        if (valor < 0 || valor > 20)
            throw new IllegalArgumentException(nombreCampo + " debe estar entre 0 y 20.");

        return valor;
    }


}