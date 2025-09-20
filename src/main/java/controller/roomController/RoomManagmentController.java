package controller.roomController;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RoomDetails;
import java.sql.*;
import static controller.roomController.RoomManagementFormController.*;
import static db.DBConnection.*;

public class RoomManagmentController implements RoomManagmentService{

    @Override
    public void addRoomDetails(RoomDetails roomDetailsObject) {

        String SQL = "INSERT INTO rooms(room_number, room_type, price_per_night, description, room_status) VALUES(?,?,?,?,?);";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1, roomDetailsObject.getRoomNumber());
            preparedStatement.setObject(2, roomDetailsObject.getRoomType());
            preparedStatement.setObject(3, roomDetailsObject.getPricePerNight());
            preparedStatement.setObject(4, roomDetailsObject.getDescription());
            preparedStatement.setObject(5, roomDetailsObject.getRoomStatus());

            int i = preparedStatement.executeUpdate();
            addButtonMessage(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<RoomDetails> getAllRoomDetails() {
        ObservableList<RoomDetails> roomDetails = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * FROM rooms;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                RoomDetails rd = new RoomDetails(
                        resultSet.getInt("room_number"),
                        resultSet.getString("room_type"),
                        resultSet.getDouble("price_per_night"),
                        resultSet.getString("description"),
                        resultSet.getString("room_status")
                );
                roomDetails.add(rd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomDetails;
    }

    @Override
    public void updateRoomDetails(RoomDetails updatedRoomDetails) throws SQLException {
        String sql = "UPDATE rooms SET room_type = ?, price_per_night = ?, description = ?, room_status = ? WHERE room_number = ?";
        Connection connection = getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1, updatedRoomDetails.getRoomType());
        preparedStatement.setObject(2, updatedRoomDetails.getPricePerNight());
        preparedStatement.setObject(3, updatedRoomDetails.getDescription());
        preparedStatement.setObject(4, updatedRoomDetails.getRoomStatus());
        preparedStatement.setObject(5, updatedRoomDetails.getRoomNumber());
        int k = preparedStatement.executeUpdate();
        updateButtonMessage(k);
    }

    @Override
    public void deleteRoomDetails(int roomNumber) {
        Connection connection;

        try {
            connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE FROM rooms WHERE room_number = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setObject(1, roomNumber);
            int j = ps.executeUpdate();

            deleteButtonMessage(j);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
