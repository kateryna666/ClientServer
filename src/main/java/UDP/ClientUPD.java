package UDP;

public class ClientUPD {
    final static int MAX_Clients = 10;
    public static void main(String[] args){

                for (int i = 0; i < MAX_Clients; i++){
                    new ClientOneUPD();
                }
    }
}
