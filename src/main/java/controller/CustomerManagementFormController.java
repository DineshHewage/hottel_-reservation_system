package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.sql.*;

public class CustomerManagementFormController {

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
    private TableView<?> tblRoomDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhoneNumber;

    @FXML
    void btnAddOnAction(ActionEvent event) {

        String customerName = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhoneNumber.getText();
        String address = txtAddress.getText();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation", "root", "Dinesh@12345");
            String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);


            ps.setObject(1,customerName);
            ps.setObject(2,email);
            ps.setObject(3,phone);
            ps.setObject(4,address);

            int i = ps.executeUpdate();
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "Customer added successfully!");
            }else {
                JOptionPane.showMessageDialog(null, "Failed to add customer.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }
}
