package controller.roomController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RoomDetails;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RoomManagementFormController implements Initializable {

    @FXML
    private JFXRadioButton availableRadio;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPricePerNight;

    @FXML
    private TableColumn<?, ?> colRoomNumber;

    @FXML
    private TableColumn<?, ?> colRoomStatus;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private JFXComboBox<String> roomTypeCombo;

    @FXML
    private TableView<RoomDetails> tblRoomDetails;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtPricePerNight;

    @FXML
    private JFXTextField txtRoomNumber;

    @FXML
    public JFXRadioButton maintenanceRadio;

    @FXML
    private JFXRadioButton unavailableRadio;

    RoomManagmentService roomManagmentService = new RoomManagmentController();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        RoomDetails roomDetailsObject = new RoomDetails(
                Integer.parseInt(txtRoomNumber.getText()),
                roomTypeCombo.getValue(),
                Double.parseDouble(txtPricePerNight.getText()),
                txtDescription.getText(),
                checkRoomStatus()
        );

        roomManagmentService.addRoomDetails(roomDetailsObject);
        loadRoomDetails();
        cleanForm();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cleanForm();
    }

    private void cleanForm() {
        txtRoomNumber.setText(null);
        roomTypeCombo.getSelectionModel().clearSelection();
        txtPricePerNight.setText(null);
        txtDescription.setText(null);
        availableRadio.setSelected(false);
        unavailableRadio.setSelected(false);
        maintenanceRadio.setSelected(false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int roomNumber = Integer.parseInt(txtRoomNumber.getText());
        roomManagmentService.deleteRoomDetails(roomNumber);
        loadRoomDetails();
        cleanForm();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {

        RoomDetails updatedRoomDetails = new RoomDetails(
                Integer.parseInt(txtRoomNumber.getText()),
                roomTypeCombo.getValue(),
                Double.parseDouble(txtPricePerNight.getText()),
                txtDescription.getText(),
                checkRoomStatus()
        );

        roomManagmentService.updateRoomDetails(updatedRoomDetails);
        loadRoomDetails();
        cleanForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        ------initialize roomTypeCombo-------
        ObservableList<String> roomTypes = FXCollections.observableArrayList(
                "Single",
                "Double",
                "Suite"
        );
        roomTypeCombo.setItems(roomTypes);
//        ----------------------------------------

//        ------set toggleGroup for radiobuttons------
        ToggleGroup roomStstusToggleGroup = new ToggleGroup();

        availableRadio.setToggleGroup(roomStstusToggleGroup);
        unavailableRadio.setToggleGroup(roomStstusToggleGroup);
        maintenanceRadio.setToggleGroup(roomStstusToggleGroup);
//        --------------------------------------------

//        ------set table details----------
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colPricePerNight.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));

        loadRoomDetails();

//        -------Show Room Infor as per user clicks.
        tblRoomDetails.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setSelectedValue(newValue);
            }
        });
    }

    private void loadRoomDetails() {
        //roomDetails.clear();
        //ObservableList<RoomDetails> allRoomDetail = rmc.getAllRoomDetail();
        ObservableList<RoomDetails> allRoomDetails = roomManagmentService.getAllRoomDetails();

        tblRoomDetails.setItems(allRoomDetails);
    }

    private String checkRoomStatus() {
        if (availableRadio.isSelected()) {
            return "Available";
        } else if (maintenanceRadio.isSelected()) {
            return "Maintenance";
        }
        return "UnAvailable";
    }

    private void setSelectedValue(RoomDetails selectedValue) {
        txtRoomNumber.setText(String.valueOf(selectedValue.getRoomNumber()));
        roomTypeCombo.getSelectionModel().select(selectedValue.getRoomType());
        txtPricePerNight.setText(String.valueOf(selectedValue.getPricePerNight()));
        txtDescription.setText(selectedValue.getDescription());

        if (selectedValue.getRoomStatus().equals("Available")) {
            availableRadio.setSelected(true);
        } else if (selectedValue.getRoomStatus().equals("Maintenance")) {
            maintenanceRadio.setSelected(true);
        } else {
            unavailableRadio.setSelected(true);
        }
    }

    public static void addButtonMessage(int i) {
        Alert alert;
        if (i > 0) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Room detail added successfully!");
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add room details.");
        }
        alert.showAndWait();
    }

    public static void updateButtonMessage(int k){
        Alert alert;
        if (k > 0) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Room detail updated successfully!");
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update room details.");
        }
        alert.showAndWait();
    }

    public static void deleteButtonMessage(int j) {
        Alert alert;
        if (j > 0) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Room detail deleted successfully!");
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete Room detail.");
        }
        alert.showAndWait();
    }
}
