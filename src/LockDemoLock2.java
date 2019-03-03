import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/23
 * Time: 22:24
 * Description: j.u.c
 */
public class LockDemoLock2 {
    volatile int i = 0;

    //广泛应用juc并发编程包
    //替换为自己的实现
    Lock lock = new NeteaseLock();
    public void add(){
        //获取锁（一直等，直到获取锁）
        lock.lock();
        try{
            i++;
        }finally {
            //释放锁
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockDemoLock2 Id = new LockDemoLock2();
        //目标20000
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    Id.add();
                }
            }).start();
        }
        try {
            Thread.sleep(2000L);
            System.out.println(Id.i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
