import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/23
 * Time: 22:24
 * Description: j.u.c
 */
public class LockDemo1 {
    //volatile int i = 0;
    AtomicInteger i = new AtomicInteger();
    public void add(){
        //i++;
        //对i变量进行加一操作,原子操作
        i.incrementAndGet();
    }

    public static void main(String[] args) {
        LockDemo1 Id = new LockDemo1();
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
