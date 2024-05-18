package edu.tucn.str.lecture6.rtthreads.periodic2;

/** Just start a periodic thread with a one-second period.
 */
import javax.realtime.*;

public class Periodic1 {
	public static void main(String[] args) {
		SchedulingParameters scheduling = new PriorityParameters(
				PriorityScheduler.MIN_PRIORITY + 10);
		ReleaseParameters release = new PeriodicParameters(new RelativeTime(), // Start
																				// at
																				// .start()
				new RelativeTime(1000, 0), // 1 second period
				null, // cost
				new RelativeTime(500, 0),// deadline=period/2
				null, // no overrun handler
				null); // no miss handler

		RealtimeThread rt = new MyThread(scheduling, release);
		rt.start();
		try {
			rt.join();
		} catch (Exception e) {
		}
		;
	}
}