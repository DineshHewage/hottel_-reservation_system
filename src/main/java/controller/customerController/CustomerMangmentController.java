package controller.customerController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustomerDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static controller.customerController.CustomerManagementFormController.*;

public class CustomerMangmentController implements CustomerMangmentService {

    @Override
    public ObservableList<CustomerDetails> getAllCustomerInfor() {
        ObservableList<CustomerDetails> customerDetailsListObjects = FXCollections.observableArrayList();
        Connection connection = null;
        try {
            String sql = "select * from customers";
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CustomerDetails customerDetailsObject = new CustomerDetails(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                );
                customerDetailsListObjects.add(customerDetailsObject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerDetailsListObjects;
    }

    @Override
    public void addCustomer(CustomerDetails customerDetails) {

        String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?,?,?,?)";
        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setObject(1, customerDetails.getName());
            ps.setObject(2, customerDetails.getEmail());
            ps.setObject(3, customerDetails.getPhone());
            ps.setObject(4, customerDetails.getAddress());

            int i = ps.executeUpdate();
            addCustomerMessage(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCustomer(String email) {
        String sql = "DELETE FROM customers WHERE email=?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            int j = ps.executeUpdate();
            deleteCustomerMessage(j);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCustomer(CustomerDetails customerDetails) {
        String sql = "UPDATE customers SET name=?, phone=?, address=? WHERE email=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, customerDetails.getName());
            ps.setObject(2, customerDetails.getPhone());
            ps.setObject(3, customerDetails.getAddress());
            ps.setObject(4, customerDetails.getEmail());
            int k = ps.executeUpdate();
            updateCustomerMessage(k);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
