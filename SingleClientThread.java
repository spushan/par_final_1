import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleClientThread extends Thread {
    private int port;
    AtomicInteger score;
    String[] gesture;

    public SingleClientThread(int port, AtomicInteger score, String[] gesture) {
        this.port = port;
        this.score = score;
        this.gesture = gesture;
    }

    public boolean test(int player, int opponent) {
        if(player == 0 && opponent == 2 ||
           player == 1 && opponent == 0 ||
           player == 2 && opponent == 1) {
            System.out.println("Player Wins");
            return true;
        }
        else if(player == 0 && opponent == 1 ||
                player == 1 && opponent == 2 ||
                player == 2 && opponent == 0) {
            System.out.println("Opponent Wins");
            return false;
        }
        else  {
            System.out.println("Players Ties");
            return false;
        }
    }

    public String converter(int x) {
        switch(x) {
            case 0:
                return "rock"; 
            case 1:
                return "paper";
            default:
                return "scissors";

        }
    }
    
    public void run() {
        Socket socket = new Socket();
        boolean notConnected = true;
        while (notConnected) {
            try {
                socket = new Socket("127.0.0.1", port);
                notConnected = false;
            } catch (Exception e) {
                notConnected = true;
            }
        }
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for(int i=0;i<gesture.length;i++) {
                String oppMove = input.readLine();
                int player = Integer.parseInt(gesture[i]);
                int opponent = Integer.parseInt(oppMove);
                System.out.println("Player move is " + converter(player) + " and opponent move is " + converter(opponent));
                if(test(player, opponent)) {
                    score.getAndIncrement();
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
