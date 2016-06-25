
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter server ip:");
        String serverIp = sc.nextLine();
        System.out.println("Enter server port:");
        int port = sc.nextInt();
        sc.close();
        startGame(serverIp, port);
    }

    public static void startGame(String serverIp, int serverPort) {
        try {
            Socket socket = NetworkUtils.connectToServer(serverIp, serverPort);
            DataInputStream ds = new DataInputStream(socket.getInputStream());
            Game game = NetworkUtils.readInitialMessage(ds);

            BlockingQueue<Integer> moveQueue = new ArrayBlockingQueue<>(10); // don't expect more than 10 keys to be queued

            new Thread(() -> {
                try {
                    Gui gui = new Gui();
                    gui.start(moveQueue);
                    DatagramSocket datagramSocket = new DatagramSocket();
                    DatagramPacket dp = new DatagramPacket("1".getBytes(), "1".getBytes().length);
                    dp.setSocketAddress(new InetSocketAddress(serverIp, serverPort));
                    datagramSocket.send(dp);
                    DataInputStream udpDs = new DataInputStream(new UdpInputStream(datagramSocket));
                    startReceivingPeriodic(game, gui, udpDs);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            new Thread(() -> {
                try {
                    startSendingKeys(socket, moveQueue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startReceivingPeriodic(Game game, Gui gui, DataInputStream ds) throws IOException {
        while (true) {
            State state = NetworkUtils.readPeriodicMessage(ds);
            gui.draw(game, state);
        }
    }

    public static void startSendingKeys(Socket socket, BlockingQueue<Integer> moveQueue) throws IOException {
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        Map<Integer, Integer> keyMap = new KeyMapper().keyMap;
        while (true) {
            try {
                int move = moveQueue.take();
                pw.print(keyMap.get(move));
                pw.flush();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
