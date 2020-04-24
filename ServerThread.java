import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    
    private int serverPort;
    private String[] gesture;
    
    public ServerThread(int serverPort, String[] gesture) {
        this.serverPort = serverPort;
        this.gesture = gesture;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket[] socketArr = new Socket[2];
            int ptr = 0;
                while(ptr < 2) {
                    socketArr[ptr] = serverSocket.accept();
                    ptr++;
                }

            serverSocket.close(); 
            PrintWriter out1 = new PrintWriter(socketArr[0].getOutputStream(),true);
            PrintWriter out2 = new PrintWriter(socketArr[1].getOutputStream(),true);
            
            for(int i=0;i<gesture.length;i++) {
                out1.println(gesture[i]);
                out2.println(gesture[i]);
            }
            out1.close();
            out2.close();
            socketArr[0].close();
            socketArr[1].close();
            serverSocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}