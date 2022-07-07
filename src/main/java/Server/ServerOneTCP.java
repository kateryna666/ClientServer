package Server;

import architecture.*;

import java.io.*;
import java.net.Socket;

public class ServerOneTCP extends Thread {
    static int staticUserId = 0;
    private int userId;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public ServerOneTCP(Socket s) throws IOException{
        socket = s;
        this.userId = staticUserId++;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        ServerTCP.clientMap.put(userId, out);
        this.start();
    }

    public void run() {
        try {
            byte[] packet;
            while (true) {
                try {
                    try {
                        packet = (byte[]) in.readObject();
                    }catch (IOException e){
                        System.out.println("End of this thread's packet stream..........................");
                        break;
                    }
                    ServerReceiver receiver = new ServerReceiver(this.userId, packet);
                    receiver.receiveMessage();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Закриваємо сокет на сервері");
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
                ServerTCP.clientMap.remove(userId);
            } catch (IOException e) {
                System.err.println("Сокет не закрито ...");
            }
        }
    }

    public ObjectOutputStream getOut() {
        return out;
    }



}