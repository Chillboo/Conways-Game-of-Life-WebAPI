package za.co.wethinkcode.game_of_life;

import io.javalin.Javalin;

public class GameServer {
    private final Javalin server;
    private final int DEFAULT_PORT = 8080;

    public GameServer(String dbUrl) {
        server = Javalin.create();
        //Cute, left here because.
        server.get("/_ping", context -> context.result("pong"));

        WorldApiHandler worldApiHandler = new WorldApiHandler(dbUrl);
        server.get("/worlds", context -> worldApiHandler.getAllWorlds(context));
        server.post("/world", context -> worldApiHandler.createNew(context));
        server.post("world/{id}/next", context -> worldApiHandler.advanceGame(context));
    }

    public void start(int port) {
        int listen_port = port > 0 ? port : DEFAULT_PORT; // use port if > 0 otherwise use DEFAULT_PORT value
        this.server.start(listen_port);
    }

    public void stop() {
        this.server.stop();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer(args[0]);
        server.start(8080);
    }
}
