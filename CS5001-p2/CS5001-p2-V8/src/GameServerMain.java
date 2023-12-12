import woodland.Game;
import woodland.Json.JsonProcess;
import woodland.network.*;
public class GameServerMain {


    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        long seed = Long.parseLong(args[1]);

        // System.out.println(port);
        // System.out.println(seed);
        Game mygame = new Game(seed);

       // JsonProcess jsonProcess = new JsonProcess();
       // String body =  jsonProcess.print(mygame);

        GameServer myserver = new GameServer(port, mygame);

    }
}
