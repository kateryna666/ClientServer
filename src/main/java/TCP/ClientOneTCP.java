package TCP;

import architecture.FakeReceiver;
import packege.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientOneTCP extends Thread{

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static int counter = 0;
    private int id = counter++;
    private static int threadcount = 0;
    private final int MAX_TRIES = 5;

    public ClientOneTCP(InetAddress addr) throws InterruptedException {
        threadcount++;
        System.out.println("Starting client number " + id);
        for (int i = 0; i < MAX_TRIES; i++){
            try {
                socket = new Socket(addr, ServerTCP.PORT);
                break;
            }
            catch (IOException e) {
                System.err.println("Couldn't connect to the server: " + i);
                Thread.sleep(1000);
                if (i == MAX_TRIES - 1){
                    return;
                }
            }
        }

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            this.start();
        }
        catch (IOException e) {
            try {
                socket.close();
            }
            catch (IOException e2) {
                System.err.println("Сокет не закрито");
            }
        }
    }
    public static int threadCount() {
        return threadcount;
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                byte[] packet = FakeReceiver.generatePacket();
                out.writeObject(packet);
                out.flush();

                byte[] response = (byte[]) in.readObject();
                Packet receivedPackage = new Packet(response);
                System.out.println("FROM SERVER:\n"+ receivedPackage);
            }
        }
        catch (IOException e) {
            System.err.println("IO Exception");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Завжди закриває:
            try {
                socket.close();
                in.close();
                out.close();
            }
            catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
        //threadcount--; // Завершуємо цей потік
    }
}
