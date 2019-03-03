import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

//lock实现接口
public class NeteaseLock implements Lock {
    //标志，谁拿到了锁
    AtomicReference<Thread> owner = new AtomicReference<>();
    //集合 -- 存储正在等待的线程
    public LinkedBlockingDeque<Thread> waiters = new LinkedBlockingDeque<>();

    @Override
    public void lock() {
        //cas
        while (!owner.compareAndSet(null, Thread.currentThread())) {
            //没拿到锁需要等待，其它线程释放锁
            //加入到等待集合
            waiters.add(Thread.currentThread());
            //？等待，线程不继续执行 park unpark
            //让currentThread线程等待
            LockSupport.park();
            waiters.remove(Thread.currentThread());
        }
        //拿到锁继续执行
    }

    @Override
    public void unlock() {
        if(owner.compareAndSet(Thread.currentThread(),null)){
            //释放锁后，告知其它线程，其它线程可以继续执行
            Thread next =null;
            Object[] objects = waiters.toArray();
            //遍历拿出所有等待线程
            for (Object object:objects) {
                next = (Thread) object;
                LockSupport.unpark(next);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}
