package Server;

import architecture.Decryptor;
import architecture.Encryptor;
import architecture.Processor;
import architecture.Sender;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerTCP extends Thread{
    //public static Map<Long, AbstractMap.SimpleEntry<InetAddress, Integer>> clientMap = new ConcurrentHashMap<>();
    private boolean shutdown = false;
    private ServerSocket serverSocket;

    public static int PORT = 8082;
    public static final int CAPACITY = 100;

    public static Map<Integer, ObjectOutputStream> clientMap = new ConcurrentHashMap<>();
    static Thread decryptor;
    static Thread[] processors = new Thread[CAPACITY/20+1];
    static Thread encryptor;
    static Thread sender;

    public ServerTCP() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        decryptor = new Decryptor();
        for (int i = 0; i < processors.length; i++)
            ServerTCP.processors[i] = new Processor();
        encryptor = new Encryptor();
        sender = new Sender();
        Sender.isServerTCP = true;
    }
    public void run() {
        System.out.println("Server start".toUpperCase());
        shutdown = false;

        while (!shutdown) {
            try {
                System.out.println("Serverok start".toUpperCase());
                Socket socket = this.serverSocket.accept();
                try {
                    new ServerOneTCP(socket);
                } catch (IOException e) {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.err.println("While ended".toUpperCase());
        try {
            serverSocket.close();
            Decryptor.shutdown();
            Processor.shutdown();
            Encryptor.shutdown();
            Sender.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stops() {
        shutdown = true;

    }
}