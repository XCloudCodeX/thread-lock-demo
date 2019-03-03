package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/25
 * Time: 23:24
 * Description: No Description
 */
public class BioServerV1 {
    private static Charset charset = Charset.forName("utf-8");

    public static void main(String[] args) {
        int port = 9090;
        try(ServerSocket ss = new ServerSocket(port)){
            while (true){
                Socket s = ss.accept();

                BufferedReader reader = new BufferedReader(
                   new InputStreamReader(s.getInputStream(),charset));

                String mess = null;

                //接收数据
                while ((mess = reader.readLine())!=null){
                    System.out.println(mess);
                }
                s.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
