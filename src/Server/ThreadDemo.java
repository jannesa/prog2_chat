package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadDemo {
	
	static class Zaehlen implements Runnable {

		@Override
		public void run() {
			for(int i = 0; i<20;i++) {
				//Zahl der forschleife und Namen des aktuellen Thread-Namen ausgeben.
				System.out.println(Thread.currentThread().getName() + ", Zahl " + i);
				
				//Thread pausieren.
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		
		//Neuen Thread erstellen und ein neues Objekt von zählen. Außerdem Thread starten. 
//		Thread t3 = new Thread(new Zaehlen());
//		Thread t4 = new Thread(new Zaehlen());
		
	
		System.out.println("START");
		
		//Ein Pool bzw. Gruppe von Threads.
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		
		
		Thread t1 = new Thread(new Zaehlen());
		Thread t2 = new Thread(new Zaehlen());

		
		//Thread t1 einen Namen geben.
		t1.setName("thread1");
		t1.setName("thread2");
		
		
		//Thread starten
//		t1.start();
		
		//Threads im Pool starten.
		executor.execute(t1);
		executor.execute(t2);
		
		executor.shutdown();
		
		//Jeden Sekunde prüfen ob Threads im Pool noch laufen. 
		while(!executor.awaitTermination(1, TimeUnit.SECONDS)) {
			System.out.println("Threads laufen noch!");
		}
		
		
		
		
		
		//Auf Thread warten (bis beendet). Nur ohne executor notwendig!
//		t1.join();
		
		//new Thread(new Zaehlen()).start();
		
		System.out.println("STOP");
	}

}
