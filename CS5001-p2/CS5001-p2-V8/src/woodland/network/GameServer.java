package woodland.network;

import woodland.Game;
import woodland.Json.JsonProcess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

                /**
                //Get input and output streams talk to the client
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                StringBuilder request = new StringBuilder();

                 String line = in.readLine();

                while (line !=null && !line.isEmpty()){
                    request.append(line).append("\r\n");
                    System.out.println(line);
                    line = in.readLine();
                }
            //      System.out.println(request);

                //check if it is a simple GET request
                if (request.toString().startsWith("GET")){
                    //Start sending the reply
                    String body = "{\"status\":\"ok\"}";
                    out.write("HTTP/1.1 200 OK\r\n");

                    System.out.println("200");
                    System.out.println("OK");
                    out.write("Access-Control-Allow-Origin: *\r\n");
                    out.write("Access-Control-Allow-Methods: *\r\n");
                    out.write("Access-Control-Allow-Headers: *\r\n");
                    out.write("Access-Control-Max-Age: 86400\r\n");
                    out.write("Content-Type: application/json\r\n");
                    out.write("Content-Length: " + body.length()+"\r\n");
                    out.write("\r\n");
                    out.write(body + "\r\n" );
                    System.out.println(body);

                }
                out.flush();
               // out.close();
                 */

            }
        } catch (IOException ioe) {
          System.out.println(ioe.getMessage());
        }
    }

}
