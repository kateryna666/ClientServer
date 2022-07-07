package architecture;

import Server.ServerTCP;
import packege.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Sender extends Thread {
    public static boolean isServerTCP;
    private static boolean shutdown = false;
    public Sender(){start();}
    public void sendMessageTCP() {
            try {
                int target = 1000;
                int i = 0;
                AbstractMap.SimpleEntry<Integer, byte[]> im;
                int userId;
                byte[] message;
                while (!shutdown) {
                    if(!Encryptor.queueCodedAnswers.isEmpty()) {
                        im = Encryptor.queueCodedAnswers.poll(10L, TimeUnit.SECONDS);
                        if(im!=null) {
                            userId = im.getKey();
                            message = im.getValue();
                            System.out.println("Send " + (++i) + " User " + ServerTCP.clientMap.get(userId) + " received " + Arrays.toString(message));
                            ObjectOutputStream out = ServerTCP.clientMap.get(userId);
                            out.writeObject(message);
                            out.flush();
                            out.close();
                        }

                    }
                    if (Encryptor.queueCodedAnswers.isEmpty()) Thread.sleep(100L);
                    //if (Encryptor.queueCodedAnswers.isEmpty()) break;
                }

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        System.out.println("END OF SENDER");
    }

    public static void shutdown(){
        shutdown = true;
    }

    @Override
    public void run(){
        if(isServerTCP)
            sendMessageTCP();
    }

}
