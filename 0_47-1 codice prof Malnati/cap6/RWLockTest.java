public class RWLockTest {

	int indentCount=0;

	class ReaderThread extends Thread {
		private RWLock lock;
		
		ReaderThread(RWLock lock,String name) {
			this.lock=lock;			
			setName(name);
		}
		
		public void run() {
			for (int i=0; i<10; i++) {
				 lock.richiediLettura();
				 System.out.println(indentMore()+getName()+": inizio lettura #"+i);
				 try {
				 	Thread.sleep((int) (1000*Math.random()));
				 } catch (Exception e) {}
				 System.out.println(indentLess()+getName()+": fine lettura #"+i);
				 lock.rilascia();
			}
		}
		
	}
	
	class WriterThread extends Thread {
		private RWLock lock;
		
		WriterThread(RWLock lock,String name) {
			this.lock=lock;			
			setName(name);
		}
		
		public void run() {
			for (int i=0; i<10; i++) {
				 lock.richiediScrittura();
				 System.out.println(indentMore()+getName()+": inizio scrittura #"+i);
				 try {
				 	Thread.sleep((int) (1000*Math.random()));
				 } catch (Exception e) {}
				 System.out.println(indentLess()+getName()+": fine scrittura #"+i);
				 lock.rilascia();
			}
		}
		
	}

	public RWLockTest() {
		RWLock lock = new RWLock();
		for (int i=0; i<3; i++) {
			Thread t= new ReaderThread(lock,"Reader"+i);
			t.start();
		}
		for (int i=0; i<3; i++) {
			Thread t= new WriterThread(lock,"Writer"+i);
			t.start();
		}
	}
	
	public synchronized String indentMore() {
		char[] cs=new char[indentCount];
		for (int i=0;i<indentCount; i++) 
			cs[i]=' ';
		indentCount+=2;
		return new String(cs);
	}

	public synchronized String indentLess() {
		indentCount-=2;
		char[] cs=new char[indentCount];
		for (int i=0;i<indentCount; i++) 
			cs[i]=' ';
		return new String(cs);
	}
	
	public static void main(String[] args) {
		RWLockTest t=new RWLockTest();
	}
}