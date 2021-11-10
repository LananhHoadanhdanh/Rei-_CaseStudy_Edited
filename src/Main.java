import service.file_IO.RoomFileIO;
import service.manage.RoomManage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        RoomManage roomInstance = RoomManage.getRoomInstance();
        RoomFileIO.readRoomFromFile();
        RoomFileIO.writeRoomToFile();
        for (int i = 0; i < roomInstance.getRoomList().size(); i++) {
            System.out.println(roomInstance.getRoomList().get(i).getLastCheckIn());
        }

    }
}
