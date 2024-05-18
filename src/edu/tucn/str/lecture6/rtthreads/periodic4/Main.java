package edu.tucn.str.lecture6.rtthreads.periodic4;

import javax.realtime.AsyncEventHandler;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import javax.realtime.ReleaseParameters;
import javax.realtime.SchedulingParameters;

public class Main {	
	public static void main(String[] args) {		
		AsyncEventHandler deadlineMissedHandler = new AsyncEventHandler() {
			public void handleAsyncEvent() {
				System.out.println("deadline ratat!");
			}
		};
		
		SchedulingParameters scheduling = new PriorityParameters(
				PriorityScheduler.MIN_PRIORITY + 10);

		ReleaseParameters releaseParams1 = new PeriodicParameters(
				new RelativeTime(), // Start at start()														
				new RelativeTime(2000, 0), // 2 second period
				null, // cost
				new RelativeTime(1000, 0),// deadline=period/2
				null, // no overrun handler
				deadlineMissedHandler); // deadline miss handler
		
		ReleaseParameters releaseParams2 = new PeriodicParameters(
				new RelativeTime(), // Start at start()														
				new RelativeTime(3000, 0), // 1 second period
				null, // cost
				new RelativeTime(1500, 0),// deadline=period/2
				null, // no overrun handler
				deadlineMissedHandler); // deadline miss handler

		RealtimeThread rtt1 = 
			new MyThread(scheduling, releaseParams1);
		RealtimeThread rtt2 = 
			new MyThread(scheduling, releaseParams2);
		rtt1.start();
		rtt2.start();		
	}
}

