/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/23
 * Time: 22:24
 * Description: j.u.c
 */
public class LockDemoLock1 {
    volatile int i = 0;
    public void add(){
        //同步关键字
        synchronized (this){
            i++;
        }
    }

    public static void main(String[] args) {
        LockDemoLock1 Id = new LockDemoLock1();
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
