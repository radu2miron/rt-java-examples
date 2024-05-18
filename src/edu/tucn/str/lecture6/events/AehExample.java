package edu.tucn.str.lecture6.events;

import javax.realtime.*;

public class AehExample {

	class MyHandler implements Runnable{
		public void run(){
			System.out.println("handler is executed");
		}
	}
	
	public void test(){
		AsyncEventHandler h = new AsyncEventHandler(new MyHandler());
		AsyncEvent event = new AsyncEvent();
		
		event.addHandler(h);
		
		event.fire();
		event.fire();
		
		System.out.println("is handled? "+event.handledBy(h));
	}
	
	public static void main(String[] args) {
		new AehExample().test();
	}

}
