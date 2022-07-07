package architecture;

import Client.ClientOneTCP;
import Server.ServerTCP;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class TCPtest {
    static ServerTCP server;
    @Test
    void serverUsing() throws IOException, InterruptedException {
        server = new ServerTCP();
        server.start();


        final int MAX_CLIENT_NUMBER = 200;
        long time = System.currentTimeMillis();

        ClientOneTCP[] clientOneTCPS= new ClientOneTCP[MAX_CLIENT_NUMBER];
        for (int i = 0; i < MAX_CLIENT_NUMBER; i++) {
            clientOneTCPS[i] = new ClientOneTCP(InetAddress.getByName("localhost"));
            //clientOneTCPS[i].join();
        }

        clientOneTCPS[MAX_CLIENT_NUMBER -1].join();


        System.err.println("Time = " + (System.currentTimeMillis() - time));

    }
    @AfterAll
    static void stopServer(){
        server.stops();
    }

}
