package edu.tucn.str.lecture6.rtthreads.periodic3;

/** This starts a periodic thread that pushes its CPU utilization up
 *  until it starts missing deadlines, then backs it down a bit and 
 *  starts increasing again.
 *  It wanders around this utilization limit for a while, then 
 *  ends.
 */

import javax.realtime.*;

public class MyThread2 extends RealtimeThread {
	volatile double f;

	public MyThread2(SchedulingParameters sched) {
		super(sched);
	}

	/**
	 * Generate release parameters for a periodic thread with a 1 second period.
	 */
	private ReleaseParameters mkRelease() {
		return new PeriodicParameters(new RelativeTime(), // Start at .start()
				new RelativeTime(1000, 0), // 1 second period
				null, // cost
				new RelativeTime(100, 0),// deadline=period/10
				null, // no overrun handler
				null); // no miss handler
	}

	/**
	 * run method for a periodic real-time thread. This one fools with its
	 * utilization to keep it right near the maximum it can sustain.
	 */
	public void run() {
		ReleaseParameters release = mkRelease();
		RealtimeThread me = currentRealtimeThread();
		Scheduler scheduler = getScheduler();
		SchedulingParameters sched = getSchedulingParameters();
		System.out.println(sched);

		if (me.setReleaseParametersIfFeasible(release)) {
			int bound = 0;
			int limit = 30;

			while (true) {
				do {
					for (f = 0.0; f < bound; f += 1.0)
						; // Use some time
					bound += 100; // Use more next time
					System.out.println("Ding! " + bound);
					if (--limit <= 0)
						return;
				} while (waitForNextPeriod());
				// Recover from miss or overrun
				System.out.println("Scheduling error");
				bound -= 15000;
				while (!waitForNextPeriod())
					// Eat errors
					System.out.print(".");
				System.out.println();
			}
		} else
			System.out.println("Load is not feasible");
	}
}