package sample;//Importing libraries needed
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

//The program
public class Main extends Application {

    //Creating elements
    private Label LFName = new Label("First Name");
    private TextField TFName = new TextField();
    private Label LLName = new Label("Last Name");
    private TextField TLName = new TextField();
    private Label LTitles = new Label("Titles");
    private ScrollPane STitles = new ScrollPane();
    private ListView<String> VTitles = new ListView<>();
    private Label LGenders = new Label("Last Name");
    private ToggleGroup GGenders = new ToggleGroup();
    private Label LUniversity = new Label("University");
    private ComboBox<String> BUniversity = new ComboBox<>();
    private Label LSkills = new Label("Skills");
    private ScrollPane SSkills = new ScrollPane();
    private ListView<String> VSkills = new ListView<>();
    private Button BReview = new Button("Review");
    private Label LReview = new Label();
    private HBox ReviewButtonLabel = new HBox(BReview,LReview);
    private FlowPane SelectionScreen = new FlowPane();
    private Label LResult = new Label();
    private Button BSubmit = new Button("Submit");
    private Button BBack = new Button("Back");
    private HBox ConfirmationButtons = new HBox(BSubmit,BBack);
    private FlowPane ConfirmationScreen = new FlowPane(LResult,ConfirmationButtons);
    private Label LResponse = new Label("Congratulations! Application Sent.");
    private FlowPane ResponseScreen = new FlowPane(LResponse);
    private Scene SelectionScene = new Scene(SelectionScreen, 360, 495);
    private Scene ConfirmationScene = new Scene(ConfirmationScreen, 360, 200);

    @Override
    public void start(Stage primaryStage) {

        //Setting name
        primaryStage.setTitle("New Grad Application");

        //Generating Gender Toggles
        String[] Genders = {"Male","Female","Other"};
        for (String Gender : Genders ){
            RadioButton temp = new RadioButton(Gender);
            GGenders.getToggles().add(temp);
        }

        //Creating Title View
        ObservableList<String> Titles = FXCollections.observableArrayList("Mr.","Mrs.","Ms.","Miss","Dr.");
        VTitles.setItems(Titles);
        VTitles.setPrefHeight(120);
        STitles.setContent(VTitles);
        STitles.setPrefViewportHeight(60);
        STitles.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        //Creating University Dropdown
        ObservableList<String> Universities = FXCollections.observableArrayList("Santa Clara University","University of San Francisco","Stanford University","UC Berkley","UC San Jose");
        BUniversity.setItems(Universities);
        BUniversity.setEditable(true);

        //Creating Skills View
        ObservableList<String> Skills = FXCollections.observableArrayList("C++","C#","Java","JavaScript","Python","HTML","CSS","PHP","SQL");
        VSkills.setItems(Skills);
        VSkills.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        VSkills.setPrefHeight(216);
        SSkills.setContent(VSkills);
        SSkills.setPrefViewportHeight(60);
        SSkills.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        //Configuring Selection Screen FlowPane
        SelectionScreen.getChildren().addAll(LFName,TFName,LLName,TLName,LTitles,STitles,LGenders);
        for (Toggle toggle : GGenders.getToggles()) {
            SelectionScreen.getChildren().add((Node) toggle);
        }
        SelectionScreen.getChildren().addAll(LUniversity,BUniversity,LSkills,SSkills,ReviewButtonLabel);
        LReview.setTextFill(Color.RED);
        ReviewButtonLabel.setSpacing(10);
        ReviewButtonLabel.setAlignment(Pos.CENTER_LEFT);
        SelectionScreen.setHgap(10);
        SelectionScreen.setVgap(5);
        SelectionScreen.setOrientation(Orientation.VERTICAL);
        SelectionScreen.setAlignment(Pos.CENTER);

        //Configuring Confirmation Screen FlowPane
        ConfirmationButtons.setSpacing(10);
        ConfirmationButtons.setAlignment(Pos.BOTTOM_LEFT);
        ConfirmationScreen.setOrientation(Orientation.VERTICAL);
        ConfirmationScreen.setHgap(10);
        ConfirmationScreen.setVgap(5);
        ConfirmationScreen.setAlignment(Pos.CENTER);
        LResult.setWrapText(true);

        //Configuring Result Screen FlowPane
        ResponseScreen.setOrientation(Orientation.VERTICAL);
        ResponseScreen.setAlignment(Pos.CENTER);

        //Adding Listeners to Buttons
        BReview.setOnAction(e -> GoToReview(primaryStage));
        BBack.setOnAction(e -> GoBack(primaryStage));
        BSubmit.setOnAction(e -> ShowResponse(primaryStage));

        //Creating scene and setting stage
        primaryStage.setScene(SelectionScene);

        //Displaying the calculator
        primaryStage.show();
    }

    //Letting user know that program properly closed
    @Override
    public void stop() {
        System.out.println();
        System.out.println("Program Completed.");
    }

    //Function to change to Review Stage
    private void GoToReview (Stage primaryStage){
        try {

            //Testing if form has been properly filled
            String Title;

            if (TFName.getLength()==0){
                LReview.setText("Please Fill Your Fist Name.");
                return;
            }
            if (TLName.getLength()==0){
                LReview.setText("Please Fill Your Last Name.");
                return;
            }
            try{Title = VTitles.getItems().get(VTitles.getSelectionModel().getSelectedIndices().get(0));}catch(Exception e){
                LReview.setText("Please Fill Your Titles.");
                return;
            }
            if (GGenders.getSelectedToggle()==null){
                LReview.setText("Please Fill Your Gender.");
                return;
            }
            try{if(BUniversity.getValue().length()==0){
                LReview.setText("Please Fill Your University.");
                return;
            }}catch(Exception e){
                LReview.setText("Please Fill Your University.");
                return;
            }

            LReview.setText("");

            //Setting Up Strings
            RadioButton gender = (RadioButton)GGenders.getSelectedToggle();
            StringBuilder skillList = new StringBuilder();
            try{
                for(int skill : VSkills.getSelectionModel().getSelectedIndices()){
                    skillList.append(VSkills.getItems().get(skill)).append(", ");
                }
                skillList = new StringBuilder(skillList.substring(0, skillList.length() - 2) + ".");
            }catch(Exception e){
                LReview.setText("Please Fill Your Skills");
                return;
            }

            //Delivering the Strings and creating stage
            String resultIntro = "Thank you for your application, please review the details below: \n \n";
            LResult.setText(resultIntro +Title+" "+TFName.getText()+" "+TLName.getText()+"\nGender: "+gender.getText()+"\nUniversity: "+BUniversity.getValue()+"\nSkills Selected: "+skillList);
            LResult.setMaxWidth(340);
            LResult.setWrapText(true);
            primaryStage.setScene(ConfirmationScene);
        }catch (Exception ignored) {}
    }

    //Function to change to Response Stage
    private void ShowResponse (Stage primaryStage) {
        primaryStage.setScene(new Scene(ResponseScreen, 360, 50));
    }

    //Function to change to go back to the first screen
    private void GoBack (Stage primaryStage) {
        primaryStage.setScene(SelectionScene);
    }

    //Just here to launch the program on startup
    public static void main(String[] args) {
        launch(args);
    }
}

/*Sources
Main Source: http://tutorials.jenkov.com/javafx/index.html
ListViewPort Fix: https://stackoverflow.com/questions/17412933/always-show-vertical-scrollbar-for-javafx-listview
*/