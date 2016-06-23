import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class NetworkUtils {

    private final static String SPLIT_CHAR = ":";

    public static Socket connectToServer(String ip, int port) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port));
        return socket;
    }

    public static Game readInitialMessage(DataInputStream ds) throws IOException {
        String msg = readMessage(ds);
        String[] parts = msg.split(SPLIT_CHAR);
        int[][] map = new Gson().fromJson(parts[1], int[][].class);
        return new Game(map, Integer.parseInt(parts[0]));
    }


    public static State readPeriodicMessage(DataInputStream ds) throws IOException {
        String msg = readMessage(ds);
        String[] parts = msg.split(SPLIT_CHAR);
        int[][] playerMap = new Gson().fromJson(parts[0], int[][].class);
        Player[] players = new Player[playerMap.length];
        for (int i = 0; i < playerMap.length; i++) {
            players[i] = new Player(playerMap[i][0], playerMap[i][1], playerMap[i][2], playerMap[i][3]);
        }
        int[][] bulletMap = new Gson().fromJson(parts[1], int[][].class);
        Bullet[] bullets = new Bullet[bulletMap.length];
        for (int i = 0; i < bulletMap.length; i++) {
            bullets[i] = new Bullet(bulletMap[i][0], bulletMap[i][1]);
        }
        return new State(players, bullets);
    }

    public static String readMessage(DataInputStream ds) throws IOException {
        byte[] lenArr = new byte[2];
        ds.readFully(lenArr);
        short length = ByteBuffer.wrap(lenArr).order(ByteOrder.LITTLE_ENDIAN).getShort();
        byte[] message = new byte[length];
        ds.readFully(message);
        return new String(message, StandardCharsets.UTF_8);
    }
}
