package UDP;

import architecture.FakeReceiver;
import packege.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientOneUPD extends Thread{
    final private int MAX_TRIES = 5;
    public ClientOneUPD(){start();}
    @Override
    public void run() {
        for (int i = 0; i < MAX_TRIES; i++) {
            //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            //String sentence = inFromUser.readLine();
            try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");

            byte[] receiveData = new byte[1028];
            byte[] sendData = FakeReceiver.generatePacket();
            System.out.println("Сокет відкрито");
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, ServerUDP.PORT);

            while (true) {

                clientSocket.send(sendPacket);
                System.out.println("Перший пішов");

                DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receiveDatagramPacket);

                Packet recievedPackage = new Packet(receiveDatagramPacket.getData().clone()); //todo прибрати клон?
                System.out.println("FROM SERVER:\n" + recievedPackage.toString());
                if(receiveDatagramPacket.getData()!=null)
                    break;
                else System.out.println("The packet was corrupted, sending it once more...");
                Thread.sleep(5);


            }
            clientSocket.close();
            } catch (IOException e) {
                System.err.println("Сокет не відкрито/закрито");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
