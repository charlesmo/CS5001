import woodland.Game;
import woodland.Json.JsonProcess;
import woodland.network.*;
public class GameServerMain {


    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        long seed = Long.parseLong(args[1]);

        Game mygame = new Game(seed);
        GameServer myserver = new GameServer(port, mygame);

    }
}
