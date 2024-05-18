package edu.tucn.str.lecture6.rtthreads.periodic2;

import javax.realtime.*;

/**
 * This is a periodic thread that can be used for demonstrations. This one never
 * stops, so someone will have to kill it from outside. The way this is used,
 * that will be the user with kill -9. :-( One characteristic of this thread is
 * that it increases the cost of each cycle until it starts to miss its
 * deadline, then backs off a bit, then starts increasing again.
 */

public class MyThread extends RealtimeThread {
	volatile double f;

	public MyThread(SchedulingParameters sched, ReleaseParameters release) {
		super(sched, release);
	}

	public void run() {
		RealtimeThread me = currentRealtimeThread();
		int bound;

		bound = 0;
		while (true) {
			do {
				for (f = 0.0; f < bound; f += 1.0)
					; // Use some time
				bound += 10000; // Use more next time
				System.out.println("Ding! " + bound);
			} while (me.waitForNextPeriod());
			// Recover from miss or overrun
			System.out.println("Scheduling error");
			bound -= 15000;

			while (!me.waitForNextPeriod()) {
				// Eat errors
				System.out.print(".");
			}

			System.out.println();
		}
	}
}