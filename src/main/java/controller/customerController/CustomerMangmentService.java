package controller.customerController;

import javafx.collections.ObservableList;
import model.CustomerDetails;

public interface CustomerMangmentService {
    ObservableList<CustomerDetails> getAllCustomerInfor();

    void addCustomer(CustomerDetails customerDetails);

    void deleteCustomer(String email);

    void updateCustomer(CustomerDetails customerDetails);
}
