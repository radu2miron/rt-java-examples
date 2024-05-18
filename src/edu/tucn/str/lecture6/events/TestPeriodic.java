package edu.tucn.str.lecture6.events;

import javax.realtime.*;

public class TestPeriodic {

	class MyHandler implements Runnable {
		public void run() {
			System.err.println("handler is executed");
		}
	}

	public void test() {
		AsyncEventHandler handler = new AsyncEventHandler(new MyHandler());
		PeriodicTimer timer = new PeriodicTimer(new AbsoluteTime(
				Clock.getRealtimeClock()), new RelativeTime(1000, 0), handler);
		System.err.println("is timer enabled? " + timer.isRunning());
		timer.start();
		System.err.println("is timer enabled? " + timer.isRunning());
	}

	public static void main(String[] args) {
		new TestPeriodic().test();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
		}
	}
}
