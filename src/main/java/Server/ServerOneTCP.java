package Server;

import architecture.*;

import java.io.*;
import java.net.Socket;

public class ServerOneTCP extends Thread {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    public ServerOneTCP(Socket s) throws IOException{
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
                    try {
                        packet = (byte[]) in.readObject();

                    }catch (IOException e){
                        System.out.println("End of this thread's packet stream..........................");
                        break;
                    }
                    ServerReceiver receiver = new ServerReceiver(packet);
                    receiver.receiveMessage();
                    //chekIfTreadsAlive();

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
            } catch (IOException e) {
                System.err.println("Сокет не закрито ...");
            }
        }
    }

    static void chekIfTreadsAlive() {
        synchronized (ServerTCP.processors) {
            if (ServerTCP.decryptor == null)
                ServerTCP.decryptor = new Decryptor();
            if (!ServerTCP.decryptor.isAlive())
                ServerTCP.decryptor = new Decryptor();
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
        }
    }


}