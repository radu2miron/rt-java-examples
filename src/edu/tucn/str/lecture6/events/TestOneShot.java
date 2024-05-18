package edu.tucn.str.lecture6.events;

import javax.realtime.*;

public class TestOneShot {
	class MyHandler implements Runnable {
		public void run() {
			System.err.println("handler is executed");
		}
	}

	public void test() {
		AsyncEventHandler handler = new AsyncEventHandler(new MyHandler());
		OneShotTimer timer = new OneShotTimer(new RelativeTime(2000, 0, Clock.getRealtimeClock()), handler);
		timer.start();
		System.out.println("Timer started. Will trigger the event handler in 2 sec.");
	}

	public static void main(String[] args) {
		new TestOneShot().test();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
	}
}
