package Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/27
 * Time: 1:42
 * Description: No Description
 */
public class NioClient {
    static Charset charset = Charset.forName("utf-8");

    public static void main(String[] args){
        try (SocketChannel sc = SocketChannel.open()){
            //连接 会阻塞
            boolean conneted =
                    sc.connect(new InetSocketAddress("localhost",9020));
            System.out.println("connected ="+conneted);

            //写
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入:");
            String mess =scanner.nextLine();
            ByteBuffer bf = ByteBuffer.wrap(mess.getBytes(charset));

            while (bf.hasRemaining()){
                int writeCount = sc.write(bf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
