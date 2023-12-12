package woodland.network;


import woodland.Game;
import woodland.Json.JsonParse;
import woodland.Json.JsonProcess;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Utilize threads for socket connection
 */
public class ConnectionHandler extends Thread {
    private Socket conn;
    private BufferedReader in;
    private PrintWriter out;
    boolean endFlag;
     public Game game;


    public ConnectionHandler(Socket conn,Game game){
        this.conn = conn;
        this.game = game;
        endFlag = false;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = new PrintWriter(conn.getOutputStream());
        } catch (IOException ioe) {
            System.out.println("ConnectionHandler: " + ioe.getMessage());
        }
    }

    /**
     *   run method is invoked when the Thread's start method
     */

    public void run(){
        System.out.println("new ConnectionHandler thread started .... ");
        try{
          printData();
        }catch (Exception e){
            System.out.println("ConnectionHandler:run " + e.getMessage());
            cleanup();
           // cleanup(); // cleanup and exit
        }
    }
    private void printData() throws DisconnectedException, IOException {
        while(true){
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = in.readLine();
            // if readLine fails, we can shut down the connection on this side cleanly by throwing a DisconnectedException
            if (line == null || line.equals("null")){
                throw new DisconnectedException(" ... client has closed the connection ... ");
            }
            //build the request string
            StringBuilder request = new StringBuilder();
            while (line!= null && !line.isEmpty()){
                request.append(line).append("\r\n");
                line = in.readLine();
            }
           //  System.out.println(request.toString());
           //  System.out.println("request ends here");
            String requestBody = request.toString();
            // check different types of the http requests
            if (request.toString().startsWith("GET") && !request.toString().startsWith("GET /game")) {
                //Start sending the reply
                String body = "{\"status\":\"ok\"}";
                writeResponse(body);
            }

            if (request.toString().startsWith("GET /game") ) {
                //Start sending the reply
                JsonProcess jsonProcess = new JsonProcess();
                String body =  jsonProcess.print(game);
                writeResponse(body);
            }

            if (request.toString().startsWith("POST /game") && !endFlag) {
                //check if the game has started
                if (!game.flagStart){
                    game.flagStart = true;
                    game.creatureSetup();
                    game.spellSetup();
                }
                System.out.println("Start Post");
                String[] requestLines = request.toString().split("\r\n");
                int contentLength=0;
                for (String header : requestLines) {
                    if (header.startsWith("Content-Length:")){
                        String temp = header.substring("Content-Length: ".length());
                        contentLength =Integer.parseInt(temp);
                    }
                }
                System.out.println(contentLength);
                char[] buff = new char[contentLength];
                int readResult =  in.read(buff, 0, contentLength);
                String jsonPayload = new String(buff);
                System.out.println("Json part:" + jsonPayload);
                try (JsonReader jsonReader = Json.createReader(new StringReader(jsonPayload))){
                    JsonObject jsonReadObject = jsonReader.readObject();
                    JsonParse jsonParse = new JsonParse(jsonReadObject);
                    //Start sending the reply
                    System.out.println(jsonParse.action);
                    System.out.println(jsonParse.animalName);
                    System.out.println(jsonParse.row);
                    System.out.println(jsonParse.col);

                    /** do the possible attack and check for shield, confuse, charm status when
                      it takes turns between animals
                     */
                    if (jsonParse.action.equals("move")){
                        game.checkMove(jsonParse);
                    }
                    if (jsonParse.action.equals("spell")){
                        game.checkSpell(jsonParse);
                    }
                    //check if the post is a valid move/spell
                boolean postValid = game.statusMessage.equals("The last move was successful.") || game.statusMessage.equals("The last move was interrupted by a creature.") || game.statusMessage.equals("The last spell was successful.");
                if (postValid && !jsonParse.animalName.equals(game.lastPostAnimal)) {
                    game.turnCheck(jsonParse.animalName);
                }
                    game.lastPostAnimal = jsonParse.animalName;

                }catch (Exception e){
                    System.out.println("error json");
                }
                JsonProcess jsonProcess = new JsonProcess();
                String body =  jsonProcess.print(game);
                writeResponse(body);
            }

            if (request.toString().startsWith("POST /reset") ) {
                System.out.println("Start Reset");
                game.reset();
                JsonProcess jsonProcess = new JsonProcess();
                String body =  jsonProcess.print(game);
                writeResponse(body);
            }

            if (request.toString().startsWith("OPTIONS") ) {
                //Start sending the reply
                out.write("Allow: OPTIONS, GET, POST");
                String body = "";
                writeResponse(body);
            }
            out.flush();
            in.close();
        }
    }

    private void writeResponse(String body){
        // if a game is won or lost, it will not response to a post/game request
        if (game.gameOver() || game.gameWin()){
            endFlag = true;
        }
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Access-Control-Allow-Origin: *\r\n");
        out.write("Access-Control-Allow-Methods: *\r\n");
        out.write("Access-Control-Allow-Headers: *\r\n");
        out.write("Access-Control-Max-Age: 86400\r\n");
        out.write("Content-Type: application/json\r\n");
        out.write("Content-Length: " + body.length() + "\r\n");
        out.write("\r\n");
        out.write(body + "\r\n");
        System.out.println(body);
  }
  private void cleanup(){
      try {
          in.close();;
          out.close();;
          conn.close();
      } catch (IOException ioe) {
          System.out.println("ConnectionHandler:cleanup " + ioe.getMessage());
      }
      ;
  }
}
