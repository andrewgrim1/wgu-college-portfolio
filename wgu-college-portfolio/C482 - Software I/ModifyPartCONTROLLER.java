package com.example.expediant_proj;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * The Modify Part Controller class allows for User Input to Modify the selected Part Object Data in the Text Fields and Radio Buttons
 * This class ensures that user input is valid and handles all possible exceptions
 *
 * @author Andrew Grim : C482 Software I : 8/24/22
 * */
public class ModifyPartCONTROLLER implements Initializable {
    /**
     * Variables of the Modify Part Controller Class
     */
    @FXML
    public TextField part_ID_txt;
    @FXML
    public ToggleGroup partType;
    @FXML
    public TextField part_Name_txt;
    @FXML
    public TextField part_Inv_txt;
    @FXML
    public TextField part_Price_txt;
    @FXML
    public TextField part_Max_txt;
    @FXML
    public TextField part_Min_txt;
    @FXML
    public TextField part_SwitchType_txt;
    @FXML
    public Label switchType_lbl;
    @FXML
    public RadioButton inHouseTgl, outsourcedTgl;
    @FXML
    public Button save_input_Btn;
    @FXML
    public Button cancel_Btn;
    private Part moddedPart;
    public void getSwitchType(ActionEvent event) throws IOException {}
    /**
     * @param min - The user inputs the minimum
     * @param max - The user inputs the maximum
     * @param stock - The user inputs the stock
     * @return logical - The boolean logical is returned
     * This method checks to make sure the Stock value is between the Minimum and Maximum
     */
    private boolean stockIsBetweenMinAndMax(int min, int max, int stock){
        boolean logical = true;
        if (min > stock || max < stock) {
            logical = false;
            alertCases(3);
        }
        return logical;
    }
    /**
     * @param min - The user inputs the minimum
     * @param max - The user inputs the maximum
     * @return logical - The boolean logical is returned
     * This method checks to make sure the Minimum is less than the Maximum
     */
    private boolean minIsLessThanMax(int min, int max){
        boolean logical = true;
        if (0 > min || max <= min) {
            logical = false;
            alertCases(4);
        }
        return logical;
    }
    /**
     * @param event - On the Button Save Press Event
     * @throws IOException - IOException is thrown
     * The save_input method saves the Add Part Form Data and handles all Exceptions
     * The program returns to the Main Menu Window upon successful completion of this method
     */
    @FXML
    void save_input(ActionEvent event) throws IOException {
        try {
            int partId = moddedPart.getId();
            String partName = part_Name_txt.getText();
            Double partPrice = Double.parseDouble(part_Price_txt.getText());
            int partInv = Integer.parseInt(part_Inv_txt.getText());
            int partMin = Integer.parseInt(part_Min_txt.getText());
            int partMax = Integer.parseInt(part_Max_txt.getText());
            int machineID;
            String companyName;
            boolean partAdded = false;
            if (partName.isEmpty()) {
                alertCases(1);
            } else {
                if (minIsLessThanMax(partMin, partMax) && stockIsBetweenMinAndMax(partMin, partMax, partInv)) {
                    if (inHouseTgl.isSelected()) {
                        try {
                            machineID = Integer.parseInt(part_SwitchType_txt.getText());
                            InHouse newInHouse = new InHouse(partId, partName, partPrice, partInv, partMin, partMax, machineID);
                            Inventory.addPart(newInHouse);
                            partAdded = true;
                        } catch (Exception e) {
                            alertCases(2);
                        }
                    }
                    if (outsourcedTgl.isSelected()) {
                        companyName = part_SwitchType_txt.getText();
                        Outsourced newOutsourced = new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, companyName);
                        Inventory.addPart(newOutsourced);
                        partAdded = true;
                    }
                    if (partAdded) {
                        Inventory.deletePart(moddedPart);
                        open_MainScreen(event);
                    }
                }
            }
        } catch (Exception e){
            alertCases(5);
        }
    }
    /**
     * @param event - When this method is called the Main Screen opens back up
     * @throws IOException - IOException is thrown
     * This method opens the Main Screen
     */
    private void open_MainScreen(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainGUI.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    /**
     * @param event - When the cancel button is pressed the user returns to the Main Screen
     * @throws IOException - IOException is thrown
     * This method cancels the Add Part Form and returns to the Main Screen
     */
    public void cancel(ActionEvent event) throws IOException {
        Alert alert_Exit = new Alert(Alert.AlertType.CONFIRMATION);
        alert_Exit.setTitle("Cancel?");
        alert_Exit.setContentText("Continue with Cancellation? Data won't be saved...");
        Optional<ButtonType> response = alert_Exit.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            open_MainScreen(event);
        }
    }
    /**
     * @param event - On a Toggle Event, the switchType Label changes to Machine ID or Company Name
     */
    public void onInHouseTgl(ActionEvent event) throws IOException {
        switchType_lbl.setText("Machine ID");
    }
    public void onOutsourcedTgl(ActionEvent event) throws IOException {
        switchType_lbl.setText("Company Name");
    }
    /**
     * @param url - The Selected Part Values are Initialized
     * @param resourceBundle - The method determines if the Part is an instance of InHouse or Outsourced
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       moddedPart = MainCONTROLLER.getTargetPart();
        if (moddedPart instanceof InHouse) {
            inHouseTgl.setSelected(true);
            switchType_lbl.setText("Machine ID");
            part_SwitchType_txt.setText(String.valueOf(((InHouse) moddedPart).getMachineId()));
        }
        if (moddedPart instanceof Outsourced) {
            outsourcedTgl.setSelected(true);
            switchType_lbl.setText("Company Name");
            part_SwitchType_txt.setText(String.valueOf(((Outsourced) moddedPart).getCompanyName()));
        }
        part_ID_txt.setText(String.valueOf((moddedPart).getId()));
        part_Name_txt.setText(String.valueOf((moddedPart).getName()));
        part_Price_txt.setText(String.valueOf((moddedPart).getPrice()));
        part_Inv_txt.setText(String.valueOf((moddedPart).getStock()));
        part_Max_txt.setText(String.valueOf((moddedPart).getMax()));
        part_Min_txt.setText(String.valueOf((moddedPart).getMin()));
    }
    /**
     * @param alertNumber - The alertNumber integer informs the program which Error is being encountered.
     *                    The alertCases switch case handles all possible errors.
     */
    private void alertCases(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertNumber) {
            case 1:
                alert.setTitle("Error!");
                alert.setHeaderText("Type Error:");
                alert.setContentText("Please enter a correct name type.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error!");
                alert.setHeaderText("Type Error:");
                alert.setContentText("Please enter a correct machine ID type.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error!");
                alert.setHeaderText("Logic Error:");
                alert.setContentText("Inventory must be between minimum and maximum values.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error!");
                alert.setHeaderText("Logic Error:");
                alert.setContentText("Minimum value must be less than maximum value.");
                alert.showAndWait();
                break;
            case 5:
                alert.setTitle("Error!");
                alert.setHeaderText("Form Error:");
                alert.setContentText("Please ensure all fields contain values.");
                alert.showAndWait();
                break;
            case 6:
                alert.setTitle("Error!");
                alert.setHeaderText("Input Error:");
                alert.setContentText("In-House or Outsourced must be selected.");
                alert.showAndWait();
                break;
        }
    }
}