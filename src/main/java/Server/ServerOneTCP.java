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
        System.out.println("Запустимо server з номером ");
        try {
            byte[] packet;
            while (true) {
                try {
                    try {
                        System.out.println("Запустимо server з номером 1");
                        packet = (byte[]) in.readObject();

                    }catch (IOException e){
                        System.out.println("End of this thread's packet stream..........................");
                        break;
                    }
                    System.out.println("Запустимо server з номером 2");
                    ServerReceiver receiver = new ServerReceiver(this.userId, packet);
                    receiver.receiveMessage();
                    System.out.println("Запустимо server з номером 3");


                    //todo sender with IPAddresses
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

    static void chekIfTreadsAlive() {
        synchronized (ServerTCP.decryptor) {
            if (ServerTCP.decryptor == null)
                ServerTCP.decryptor = new Decryptor();
            if (!ServerTCP.decryptor.isAlive())
                ServerTCP.decryptor = new Decryptor();
            else return;

            if (ServerTCP.processors[0] == null) {
                for (int i = 0; i < ServerTCP.processors.length; i++)
                    if (ServerTCP.processors[i] == null) {
                        ServerTCP.processors[i] = new Processor();
                    }
            }
            if (!ServerTCP.processors[0].isAlive()) {
                for (int i = 0; i < ServerTCP.processors.length; i++)
                    if (!ServerTCP.processors[i].isAlive()) {
                        ServerTCP.processors[i] = new Processor();
                    }
            }
            if (ServerTCP.encryptor == null)
                ServerTCP.encryptor = new Encryptor();
            if (!ServerTCP.encryptor.isAlive())
                ServerTCP.encryptor = new Encryptor();
            if (ServerTCP.sender == null)
                ServerTCP.sender = new Sender();
            if (!ServerTCP.sender.isAlive())
                ServerTCP.sender = new Sender();
            try {
                ServerTCP.sender.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}