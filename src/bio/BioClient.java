package bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/25
 * Time: 23:46
 * Description: No Description
 */
public class BioClient implements Runnable{
    private String host;
    private int port;
    private Charset charset =Charset.forName("utf-8");

    public BioClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }


    @Override
    public void run() {
        try(Socket s = new Socket(host,port); OutputStream out =s.getOutputStream()){
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入：");
            String mess = scanner.nextLine();
            out.write(mess.getBytes(charset));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BioClient client = new BioClient("localhost",9020);
        client.run();
    }
}
