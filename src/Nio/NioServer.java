package Nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/27
 * Time: 0:48
 * Description: No Description
 */
public class NioServer {
    private static Charset charset = Charset.forName("utf-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    public static void main(String[] args) throws IOException {
        //创建一个selector
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        int port = 9020;
        ssc.bind(new InetSocketAddress(port));

        //注册到selector
        //设置非阻塞
        ssc.configureBlocking(false);

        //ssc向selector注册,监听连接
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        //连接计数
        int connectionCount = 0;
        //极少线程
        int threads =3;
        //线程池注册
        ExecutorService tpool = Executors.newFixedThreadPool(threads);

        while (true){
            //线程等待就绪的事件
            int readyChannelsCount = selector.select();
            //因为select()阻塞可以被中断
            if(readyChannelsCount ==0){
                continue;
            }

            //得到就绪的channel的key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            //处理模板
            while (keyIterator.hasNext()){

                SelectionKey key =keyIterator.next();

                if(key.isAcceptable()){
                    ServerSocketChannel ssssc = (ServerSocketChannel) key.channel();

                    //接收连接
                    SocketChannel cc = ssssc.accept();

                    //设置非阻塞
                    cc.configureBlocking(false);
                    //向selector注册
                    cc.register(selector,SelectionKey.OP_READ,++connectionCount);
                }else if(key.isConnectable()){

                }else if(key.isReadable()){
                    //交给线程池处理读
                    tpool.execute(new SocketProcess(key));
                    //取消Selector注册，防止线程处理不及时重复选中
                    key.cancel();
                }else if(key.isWritable()){

                }
                //处理过的一定需要从selectedKey中移除
                keyIterator.remove();
            }
        }

    }

    static class SocketProcess implements Runnable{
        SelectionKey key;

        public SocketProcess(SelectionKey key) {
            super();
            this.key = key;
        }

        @Override
        public void run() {
            try {
                System.out.println("连接"+key.attachment() + "发来了"+readFromChannel());
                //如果连接不需要了，关闭
                key.channel().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String readFromChannel() throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();

            int bfsize = 1024;
            ByteBuffer rbf = ByteBuffer.allocateDirect(bfsize);

            //定义更大的Buffer
            ByteBuffer bigBf =null;

            //读的次数计数
            int count = 0;
            while ((sc.read(rbf))!= -1){
                count++;

                ByteBuffer temp = ByteBuffer.allocateDirect(bfsize*(count+1));

                if(bigBf!=null){
                    //将buffer由写转为读
                    bigBf.flip();
                    temp.put(bigBf);
                }

                bigBf =temp;

                //将这次读到的数据放入大的buffer
                rbf.flip();
                bigBf.put(rbf);
                rbf.clear();
            }

            if(bigBf!=null){
                bigBf.flip();
                try {
                    //将字节转为字符，返回接收的字符
                    return decoder.decode(bigBf).toString();
                }catch (CharacterCodingException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
