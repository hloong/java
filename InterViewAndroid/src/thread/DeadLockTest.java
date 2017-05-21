package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程死锁
 */
public class DeadLockTest {
	public static void main(String[] args) {
		MyTask mytask = new MyTask();
		
		Thread thread1 = new Thread(mytask);
		mytask.setFlag(1);
		thread1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread thread2 = new Thread(mytask);
		mytask.setFlag(2);
		thread2.start();
		
		//解决死锁问题，可以判断是否锁定
//		Lock lock = new ReentrantLock();
//		try {
//			if(!lock.tryLock(3, TimeUnit.SECONDS)){
//				thread2.start();
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	static class MyTask implements Runnable{
		Object obj1 = "obj1";
		Object obj2 = "obj2";
		int flag;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(flag == 1){
				synchronized (obj1) {
					System.out.println("locking "+obj1);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (obj2) {
						System.out.println("使用顺序object1->obj2");
					}
				}
			}else if(flag == 2){
				synchronized (obj2) {
					System.out.println("locking "+obj2);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized (obj1) {
						System.out.println("使用顺序object2->obj1");
					}
				}
			}
			
			
		}
		
		public void setFlag(int flag){
			this.flag = flag;
		}
		
	}
}
