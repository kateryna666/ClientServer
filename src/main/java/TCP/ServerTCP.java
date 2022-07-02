package TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    static final int PORT = 8081;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server has been launched.");

        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    new ServerOneTCP(socket);
                } catch (IOException e) {
                    socket.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }
}
