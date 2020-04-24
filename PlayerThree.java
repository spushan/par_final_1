import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
public class PlayerThree {

    private final static int PORT0 = 8082;
    private final static int PORT1 = 8080;
    private final static int PORT2 = 8081;
    private final static String name = "three";
    public static AtomicInteger score = new AtomicInteger();
    
    public static void main(String[] args) throws Exception {
        int numGame = Integer.valueOf(args[0]);
        Random random = new Random();
        String[] gesture = new String[numGame];
        for(int i = 0; i<numGame; i++) {
            gesture[i] = Integer.toString(random.nextInt(3));
        }

        Thread server = new ServerThread(PORT0, gesture);
        Thread opponent1 = new SingleClientThread(PORT1, score, gesture);
        Thread opponent2 = new SingleClientThread(PORT2, score, gesture);
        server.start();
        opponent1.start();
        opponent2.start();
        server.join();
        opponent1.join();
        opponent2.join();
        System.out.println("player " + name + " score: " + score);
    }
}