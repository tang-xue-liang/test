import java.util.concurrent.atomic.AtomicReference;

public class Test01 {
    private AtomicReference<Thread> cas = new AtomicReference<Thread>();

    public void lock() {
        Thread current = Thread.currentThread();
        // 利用CAS

        while (!cas.compareAndSet(null, current)) {
            // DO nothing
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        cas.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        Test01 test01 = new Test01();
//        Test01 test02 = new Test01();
             new Thread(){
                @Override
                public void run() {
                    test01.lock();
                    test01.unlock();

                }
            }.start();

        new Thread() {
            @Override
            public void run() {
                test01.lock();
//                test01.unlock();
            }
        }.start();
    }
}
