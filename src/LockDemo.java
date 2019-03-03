/**
 * Created with IntelliJ IDEA.
 * User: CloudCx
 * Date: 2019/2/23
 * Time: 22:24
 * Description: No Description
 */
public class LockDemo {
    volatile int i = 0;
    public void add(){
        i++;
    }

    public static void main(String[] args) {
        LockDemo Id = new LockDemo();
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
