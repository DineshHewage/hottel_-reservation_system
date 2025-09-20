package controller.customerController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerDetails;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableView<CustomerDetails> tblRoomDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhoneNumber;

    CustomerMangmentService customerMangmentService = new CustomerMangmentController();

    @FXML
    void btnAddOnAction(ActionEvent event) {
        CustomerDetails customerDetails = new CustomerDetails(
                txtName.getText(),
                txtEmail.getText(),
                txtPhoneNumber.getText(),
                txtAddress.getText());

        customerMangmentService.addCustomer(customerDetails);
        loadCustomerDetails();
        clearForm();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearForm();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String email = txtEmail.getText();
        customerMangmentService.deleteCustomer(email);
        loadCustomerDetails();
        clearForm();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        CustomerDetails customerDetails = new CustomerDetails(
                txtName.getText(),
                txtEmail.getText(),
                txtPhoneNumber.getText(),
                txtAddress.getText());

        customerMangmentService.updateCustomer(customerDetails);
        loadCustomerDetails();
        clearForm();
    }

    private void clearForm() {
        txtName.clear();
        txtEmail.clear();
        txtPhoneNumber.clear();
        txtAddress.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // ----------set table details----------
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadCustomerDetails();

        //        -------Show Room Infor as per user clicks.
        tblRoomDetails.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                setSelectedValue(newValue);
            }
        });
    }

    private void setSelectedValue(CustomerDetails newValue) {
        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        txtPhoneNumber.setText(newValue.getPhone());
        txtAddress.setText(newValue.getAddress());
    }

    private void loadCustomerDetails() {
        ObservableList<CustomerDetails> customerDetailsList = customerMangmentService.getAllCustomerInfor();
        tblRoomDetails.setItems(customerDetailsList);
    }

    public static void addCustomerMessage(int i){
        Alert alert;
        if (i > 0) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer detail added successfully!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add Customer details.");
        }
        alert.showAndWait();
    }

    public static void deleteCustomerMessage(int j){
        Alert alert;
        if (j> 0) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer detail deleted successfully!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to detele Customer details.");
        }
        alert.showAndWait();
    }

    public static void updateCustomerMessage(int k){
        Alert alert;
        if (k> 0) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer detail updated successfully!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update Customer details.");
        }
        alert.showAndWait();
    }
}
