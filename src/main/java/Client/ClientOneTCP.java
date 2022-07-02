package Client;

import Server.ServerTCP;
import architecture.FakeReceiver;
import packege.Message;
import packege.Packet;

import java.net.*;
import java.util.Date;
import java.io.*;

public class ClientOneTCP extends Thread{
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static int counter = 0;
    private int id = counter++;
    private final int MAX_TRIES = 5;

    public ClientOneTCP(InetAddress addr) throws InterruptedException {
        System.out.println("Запустимо клієнт з номером " + id);
        for (int i = 0; i < MAX_TRIES; i++){
            try {
                socket = new Socket(addr, ServerTCP.PORT);
                break;
            }
            catch (IOException e) {
                System.err.println("Не вдалося з'єднатися з сервером " + i);
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

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                byte[] packet = FakeReceiver.generatePacket();
                out.writeObject(packet);
                out.flush();
                byte[] response =(byte[]) in.readObject();
                Packet receivedPackage = new Packet(response);
                System.out.println("FROM SERVER:\ncType: "+receivedPackage.getbMessage()
                        .toString());
            }
        }
        catch (IOException e) {
            System.err.println("IO Exception");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            }
            catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }


}

