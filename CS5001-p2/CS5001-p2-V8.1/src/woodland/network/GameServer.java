package woodland.network;

import woodland.Game;
import woodland.Json.JsonProcess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create socket connection for http server
 */
public class GameServer {

    public GameServer(int port, Game game){
        try {
            ServerSocket ss = new ServerSocket(port);

          //   InetAddress localhost = InetAddress.getLocalHost();
           //  System.out.println(localhost.getHostAddress());
            while (true){
                Socket conn = ss.accept();
                System.out.println("Server got new connection request from " + conn.getInetAddress());
                //Create new connection handler
                ConnectionHandler ch = new ConnectionHandler(conn,game);
                ch.start();  //start handler thread

            }
        } catch (IOException ioe) {
          System.out.println(ioe.getMessage());
        }
    }

}
