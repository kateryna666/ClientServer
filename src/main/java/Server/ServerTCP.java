package Server;

import architecture.Decryptor;
import architecture.Encryptor;
import architecture.Processor;
import architecture.Sender;
import javafx.util.Pair;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTCP {
    public static Map<Long, Pair<InetAddress, Integer>> clientMap = new ConcurrentHashMap<>();
    public static final int PORT = 8081;
    public static final int CAPACITY = 100;
    public boolean timeToStop = false;
    static Thread decryptor;
    static Thread[] processors = new Thread[CAPACITY/20+1];
    static Thread encryptor;
    static Thread sender;

    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server has been launched.");
        decryptor = new Decryptor();
        for (int i = 0; i < processors.length; i++)
            ServerTCP.processors[i] = new Processor();
        encryptor = new Encryptor();
        sender = new Sender();

        try {
            while (true) {
                Socket socket = s.accept();
                try {
                    new ServerOneTCP(socket);
                } catch (IOException e) {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    socket.close();
                }
            }
        } finally {
            s.close();
            decryptor.interrupt();
            for (int i = 0; i < processors.length; i++)
                ServerTCP.processors[i].interrupt();
            encryptor.interrupt();
            sender.interrupt();
        }
    }
}