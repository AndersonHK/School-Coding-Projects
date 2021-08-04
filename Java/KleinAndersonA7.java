//Importing libraries needed
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//The program
public class KleinAndersonA7 extends Application {

    //Creating elements
    Button sumBtn = new Button("+");
    Button subBtn = new Button("-");
    Button mltBtn = new Button("x");
    Button divBtn = new Button("\u00F7");
    TextField istTxf = new TextField();
    TextField sndTxf = new TextField();
    Label result = new Label("?");
    Button clrBtn = new Button("Clear");
    FlowPane flowpane = new FlowPane();

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Setting name
        primaryStage.setTitle("KleinAndersonA7 (calculator)");

        //Attaching items and resizing appropriately
        flowpane.getChildren().addAll(sumBtn,subBtn,mltBtn,divBtn,istTxf,sndTxf,result,clrBtn);
        result.setAlignment(Pos.CENTER);
        result.setStyle("-fx-border-color: black;");
        ResizeThings(1,1);

        //Adding listeners to all button children
        for(Node child : flowpane.getChildren()){
            if(child.getClass()==clrBtn.getClass()) {
                Button toTrigger = (Button) child;
                toTrigger.setOnAction(e -> operation(toTrigger.getText()));
                System.out.println("Giving action "+toTrigger.getText()+" to button "+toTrigger.getText());
            }
        }

        //Creating scene and setting stage
        Scene scene = new Scene(flowpane, 300, 185);
        primaryStage.setScene(scene);

        //Creating listener for window size change
        final InvalidationListener resizeListener = new InvalidationListener() {
            @Override
            public void invalidated(final Observable observable) {
                ResizeThings(scene.getWidth()/300,scene.getHeight()/185);
            }
        };

        //Adding listener to scene
        scene.widthProperty().addListener(resizeListener);
        scene.heightProperty().addListener(resizeListener);

        //Displaying the calculator
        primaryStage.show();
    }

    //Letting user know that program properly closed
    @Override
    public void stop() {
        System.out.println();
        System.out.println("Program Completed.");
    }

    //Function called to resize items when window size changes
    private void ResizeThings (double scaleX, double scaleY){
        double halfsize = 135*scaleX;
        double fullsize = 280*scaleX;
        double height = 25*scaleY;
        flowpane.setHgap(10*scaleX);
        flowpane.setVgap(10*scaleY);
        flowpane.setLayoutX(10*scaleX);
        flowpane.setLayoutY(10*scaleY);
        sumBtn.setPrefSize(halfsize,height); sumBtn.setFont(Font.font(12*scaleY));
        subBtn.setPrefSize(halfsize,height); subBtn.setFont(Font.font(12*scaleY));
        mltBtn.setPrefSize(halfsize,height); mltBtn.setFont(Font.font(12*scaleY));
        divBtn.setPrefSize(halfsize,height); divBtn.setFont(Font.font(12*scaleY));
        istTxf.setPrefSize(halfsize,height); istTxf.setFont(Font.font(12*scaleY));
        sndTxf.setPrefSize(halfsize,height); sndTxf.setFont(Font.font(12*scaleY));
        result.setPrefSize(fullsize,height); result.setFont(Font.font(12*scaleY));
        clrBtn.setPrefSize(fullsize,height); clrBtn.setFont(Font.font(12*scaleY));
        result.setFont(Font.font(12*scaleY));
        result.setPadding(new Insets(5*scaleX, 0, 0, 5*scaleX));
    }

    //Function to detect operation and give appropriate response
    private void operation (String operation){
        System.out.println("Got action for "+operation);
        try {
            switch(operation) {
                case "+": {
                    result.setText(parseWithoutZeroes(istTxf) + " + " + parseWithoutZeroes(sndTxf) + " = " + parseResultsWithoutZeroes((Double.parseDouble(istTxf.getText()) + Double.parseDouble(sndTxf.getText()))));
                    result.setAlignment(Pos.CENTER_LEFT); break;
                }
                case "-": {
                    result.setText(parseWithoutZeroes(istTxf) + " - " + parseWithoutZeroes(sndTxf) + " = " + parseResultsWithoutZeroes((Double.parseDouble(istTxf.getText()) - Double.parseDouble(sndTxf.getText()))));
                    result.setAlignment(Pos.CENTER_LEFT); break;
                }
                case "x": {
                    result.setText(parseWithoutZeroes(istTxf) + " x " + parseWithoutZeroes(sndTxf) + " = " + parseResultsWithoutZeroes(Double.parseDouble(istTxf.getText()) * Double.parseDouble(sndTxf.getText())));
                    result.setAlignment(Pos.CENTER_LEFT); break;
                }
                case "\u00F7": {
                    if(Double.parseDouble(sndTxf.getText())==0) { result.setText("Error: You cannot divide by 0."); result.setAlignment(Pos.CENTER); break;}
                    else{result.setText(parseWithoutZeroes(istTxf) + " \u00F7 " + parseWithoutZeroes(sndTxf) + " = " + parseResultsWithoutZeroes(Double.parseDouble(istTxf.getText()) / Double.parseDouble(sndTxf.getText())));}
                    result.setAlignment(Pos.CENTER_LEFT); break;
                }
                case "Clear": {
                    result.setText("?"); istTxf.setText(""); sndTxf.setText("");
                    result.setAlignment(Pos.CENTER); break;
                }
            }
        }catch (Exception e) {
            if(istTxf.getText().length()==0 || sndTxf.getText().length()==0 ){
                result.setAlignment(Pos.CENTER); result.setText("Error: Enter numbers in both text fields.");
            }
            else {
                result.setAlignment(Pos.CENTER); result.setText("Error: Enter only numbers in the text fields.");
            }
        }
    }

    //Functions for removing .0 when not necessary
    private String parseWithoutZeroes (TextField NumberToGet) {
        String result = Double.parseDouble(NumberToGet.getText())+"";
        if(result.substring(result.length() - 2).equals(".0")) {
            result = result.substring(0,result.indexOf(".0"));
        }
        return result;
    }

    //Functions for removing .0 when not necessary but on results
    private String parseResultsWithoutZeroes (double Dresult) {
        String result = Dresult+"";
        if(result.substring(result.length() - 2).equals(".0")){
            result = result.substring(0,result.indexOf(".0"));
        }
        return result;
    }

    //Just here to launch the program on startup
    public static void main(String[] args) {
        launch(args);
    }
}

/*Sources
How to make flow-panes: http://tutorials.jenkov.com/javafx/flowpane.html
How to add the division symbol: https://stackoverflow.com/questions/24949930/division-sign%C3%B7-and-multiply-signx-returns-a-weird-character%C3%83
Resizing things on JavaFX: https://stackoverflow.com/questions/19940504/how-to-call-functions-on-maximizenot-resize-of-a-scene-in-javafx
Borders in Java: https://stackoverflow.com/questions/10548634/javafx-2-0-adding-border-to-label
Alignment in Java: https://stackoverflow.com/questions/40447500/setalignment-method-in-javafx
Case in Java: https://www.w3schools.com/java/java_switch.asp
*/