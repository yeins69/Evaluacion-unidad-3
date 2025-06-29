package com.tukiaos.tukiaosacademico.componente;

import javafx.stage.Stage;


public class StageManager {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}

