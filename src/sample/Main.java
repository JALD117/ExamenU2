package sample;
/*
* Alumno: José Alejandro López Doblado
* ISC IU4
* Examen Unidad 2
* Ejercicio 1
*/

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setResizable(false);

        final int[] cont = {0};
        final boolean[] acierto = {false};

        final int[] valor1 = new int[1];
        valor1[0] = ((int) (Math.random()*30));
        final int[] valor2 = new int[1];
        valor2[0] = ((int) (Math.random()*30));

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Examen Unidad 2");
        Pane canvas = new Pane();

        primaryStage.setScene(new Scene(canvas, 280, 200));
        Label lblTitle = new Label("Multiplicación de números aleatorios");
        lblTitle.setLayoutX(50);

        Label lbltexto = new Label("");
        lbltexto.setLayoutY(25);
        lbltexto.setLayoutX(20);

        Label lbltexto2 = new Label("");
        lbltexto2.setLayoutY(120);
        lbltexto2.setLayoutX(0);

        Label lblTiempo = new Label("");
        lblTiempo.setLayoutY(150);
        lblTiempo.setLayoutX(110);

        Button btnIniciar = new Button("Iniciar");
        btnIniciar.setLayoutY(80);
        btnIniciar.setLayoutX(120);

        TextField txtfRespuesta = new TextField();
        txtfRespuesta.setLayoutY(50);
        txtfRespuesta.setLayoutX(65);
        txtfRespuesta.setVisible(false);

        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setLayoutY(80);
        btnAceptar.setLayoutX(110);
        btnAceptar.setVisible(false);

        canvas.getChildren().addAll(lblTitle,lbltexto,lbltexto2,btnIniciar,txtfRespuesta,btnAceptar,lblTiempo);


        //BOTON INICIAR
        btnIniciar.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                acierto[0] = false;

                valor1[0] = ((int) (Math.random()*30));
                valor2[0] = ((int) (Math.random()*30));

                lblTiempo.setVisible(true);
                lbltexto.setVisible(true);
                lbltexto2.setVisible(false);

                txtfRespuesta.clear();

                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {

                        while(acierto[0]==false){

                            //Modificando la GUI
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lblTiempo.setText("Tiempo: "+String.valueOf(cont[0]));

                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            cont[0]++;
                        }

                        return null;
                    }
                };

                Thread hilo = new Thread(task);

                hilo.setDaemon(true);
                hilo.start();

                btnIniciar.setVisible(false);
                txtfRespuesta.setVisible(true);
                btnAceptar.setVisible(true);

                lbltexto.setText("¿Cuál es el resultado de multiplicar "+ valor1[0] + "*"+ valor2[0] +"?");
            }
        });

        //BOTON ACEPTAR
        btnAceptar.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lbltexto2.setVisible(true);

                if (Integer.parseInt(txtfRespuesta.getText()) == (valor1[0] * valor2[0])){
                    acierto[0] = true;
                    lbltexto2.setText("¡¡Respuesta correta!!  Tiempo en acertar: "+cont[0]+" segundos");
                    cont[0] = 0;

                    lblTiempo.setVisible(false);
                    lbltexto.setVisible(false);
                    btnIniciar.setVisible(true);
                    txtfRespuesta.setVisible(false);
                    btnAceptar.setVisible(false);


                } else {
                    lbltexto2.setText("          Respuesta incorrecta, intente de nuevo");
                }
            }
        });
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
