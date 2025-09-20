package controller.roomController;

import javafx.collections.ObservableList;
import model.RoomDetails;
import java.sql.SQLException;

public interface RoomManagmentService {

    void deleteRoomDetails(int roomNumber);

    void addRoomDetails(RoomDetails roomD);

    ObservableList<RoomDetails> getAllRoomDetails();

    void updateRoomDetails(RoomDetails updatedRoomDetails) throws SQLException;
}
