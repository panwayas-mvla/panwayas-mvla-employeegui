/**
 * 
 */


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class EmpDataGUI.
 */
public class EmpDataGUI extends Application {

	/**  Scene 1 Overarching Pane *. */
	private BorderPane main = new BorderPane();

	/**  Scene 1 Pane with Buttons and Labels. */
	private GridPane center = new GridPane();	

	/**  Scene 2 Overarching Pane. */
	private BorderPane view = new BorderPane();

	/** The scroll. */
	private ScrollPane scroll = new ScrollPane();

	/** The controller. */
	private ListController controller = new ListController();

	/** The years. */
	private TextField lName = new TextField(), fName = new TextField(), 
			SSN = new TextField(), age = new TextField(), pronouns = new TextField(),
			salary = new TextField(), years = new TextField();

	/** The emp data entry. */
	private Label lNLabel = new Label("Last Name: "), fNLabel = new Label("First Name:"), ssnLabel = new Label("SSN:"),
			ageLabel = new Label("Age:"), pronounsLabel = new Label("Pronouns:"), salaryLabel = new Label("Salary:"),
			yearsLabel = new Label("Years:"), dept = new Label("Dept:"), 
			empDataViewScene2 = new Label("Employee Data View"), empDataEntry = new Label("EMPLOYEE DATA ENTRY");

	/** The management. */
	private RadioButton engineering = new RadioButton("Engineering"), marketing = new RadioButton("Marketing/Sales"),
			manufacturing = new RadioButton("Manufacturing"), finance = new RadioButton("Finance"),
			HR = new RadioButton("Human Resources"), customerSupport = new RadioButton("Customer Support"),
			management = new RadioButton("Management");

	/** The save DB. */
	private Button back = new Button("Back"), sortByName = new Button("Sort By Name"), 
			sortByID = new Button("Sort By ID"), sortBySalary = new Button("Sort By Salary"), 
			addEmployee = new Button("Add Employee"), viewEmployees = new Button("View Employee"),
			saveDB = new Button("Save Employee DB");

	/** The radio buttons box. */
	private VBox labelsBox = new VBox(12), textFieldsBox = new VBox(1), radioButtonsBox = new VBox(10);

	/** The bottom scene 2 box. */
	private HBox bottomScene1Box = new HBox(10), bottomScene2Box = new HBox(10);

	/** The tg. */
	private ToggleGroup tg = new ToggleGroup();

	/**
	 * Create the panes in the GUI.
	 *
	 * @param primaryStage the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(main,400,500);
		Scene scene2 = new Scene(view, 400, 500);

		//set default
		engineering.setToggleGroup(tg);
		marketing.setToggleGroup(tg);
		manufacturing.setToggleGroup(tg);
		finance.setToggleGroup(tg);
		HR.setToggleGroup(tg);
		customerSupport.setToggleGroup(tg);
		management.setToggleGroup(tg);
		tg.selectToggle(engineering);

		view.setTop(empDataViewScene2);
		view.setBottom(bottomScene2Box);
		bottomScene1Box.getChildren().addAll(addEmployee, viewEmployees, saveDB);
		bottomScene2Box.getChildren().addAll(back, sortByName, sortByID, sortBySalary);
		main.setBottom(bottomScene1Box);
		main.setTop(empDataEntry);

		labelsBox.getChildren().addAll(lNLabel, fNLabel, ssnLabel, ageLabel, pronounsLabel, salaryLabel, yearsLabel);
		textFieldsBox.getChildren().addAll(lName, fName, SSN, age, pronouns, salary, years);
		radioButtonsBox.getChildren().addAll(engineering, marketing, manufacturing, 
				finance, HR, customerSupport, management);

		addEmployee.setOnAction(e -> {callAddEmployee(); 
		controller.clearTextFields(fName, lName, SSN, 
				age, pronouns, salary, years); tg.selectToggle(engineering);
		});

		sortByName.setOnAction(e -> {controller.sortByName(); viewEmployeeDB();});
		sortByID.setOnAction(e -> {controller.sortByID(); viewEmployeeDB();});
		sortBySalary.setOnAction(e -> {controller.sortBySalary(); viewEmployeeDB();});

		main.setCenter(center);
		center.add(dept, 0, 1);
		center.add(labelsBox, 0, 0);
		center.add(textFieldsBox, 1, 0);
		center.add(radioButtonsBox, 1, 1);

		viewEmployees.setOnAction(e -> {viewEmployeeDB(); primaryStage.setScene(scene2);});

		back.setOnAction(e -> primaryStage.setScene(scene));
		view.setCenter(scroll);

		saveDB.setOnAction(e -> controller.saveEmployeeDB());


		primaryStage.setTitle("Employees");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Call add employee.
	 */
	private void callAddEmployee( ) {
		String error = controller.addEmployee(fName.getText(), lName.getText(), 
				SSN.getText(), age.getText(),
				pronouns.getText(), salary.getText(), years.getText(), ((RadioButton)tg.getSelectedToggle()).getText());
		System.out.println(error);
		if(!error.equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Add Employee Failed");
			alert.setContentText(error);
			alert.showAndWait();
		}

	}

	/**
	 * Switches the screen to the employee database
	 * upon the view employee button being pressed.
	 */
	private void viewEmployeeDB() {
		String[] empDataStr = controller.getEmployeeDataStr();
		ListView<String> lv = new ListView<>(FXCollections.observableArrayList(empDataStr));
		lv.setPrefWidth(400);
		scroll.setContent(lv);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

} ;
