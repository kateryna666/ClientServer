package TCP;

import architecture.ReceiverTCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOneTCP extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public ServerOneTCP(Socket s) throws IOException, ClassNotFoundException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        this.start();
    }

    public void run() {
        try {
            byte[] packet;
            while (true) {
                try {
                    packet = (byte[]) in.readObject();
                }catch (IOException e){
                    System.out.println("End of this thread's packet stream..........................");
                    break;
                }

                ReceiverTCP receiverTCP = new ReceiverTCP(packet,out);
                receiverTCP.receiveMassage();
            }
            System.out.println("Закриваємо сокет на сервері");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Сокет не закрито ...");
            }
        }
    }
}
