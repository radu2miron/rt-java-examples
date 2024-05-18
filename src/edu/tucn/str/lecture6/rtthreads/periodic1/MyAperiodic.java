package edu.tucn.str.lecture6.rtthreads.periodic1;

import javax.realtime.AperiodicParameters;
import javax.realtime.AsyncEventHandler;
import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.PriorityScheduler;
import javax.realtime.RelativeTime;
import javax.realtime.ReleaseParameters;
import javax.realtime.SchedulingParameters;

public class MyAperiodic extends AperiodicThread {

	public MyAperiodic(PriorityParameters scheduling,
			AperiodicParameters release, int queueSize) {
		super(scheduling, release, queueSize);
	}

	public void run() {

		boolean noProblems = true;

		while (noProblems) {

			System.out
					.println("Do something interesting in the aperiodic thread.");

			int count = 10000;
			for (int i = 0; i < count; i++) {
				double y = 0.56;
				double x = 0.2;
				x = 0.3 * y * y - 5 + x / y;
			}
			count += 10000;

			noProblems = waitForNextRelease();

		}

		// Deal with deadline overrun.
		System.out.println("Something si wrong.");
		this.getScheduler().fireSchedulable(
				getReleaseParameters().getDeadlineMissHandler());

	}

	public static void main(String[] args) {

		AsyncEventHandler hmiss = new AsyncEventHandler(new MissedHandler2());

		AperiodicParameters release = new AperiodicParameters(new RelativeTime(
				10, 0), // cost
				new RelativeTime(10, 0),// deadline=period/2
				null, // no overrun handler
				hmiss); // no miss handler

		PriorityParameters scheduling = new PriorityParameters(
				PriorityScheduler.MIN_PRIORITY + 10);

		MyAperiodic myT = new MyAperiodic(scheduling, release, 5);

		myT.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		myT.release();

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		myT.release();
	}
}

class MissedHandler2 implements Runnable {
	public void run() {
		System.err.println(" missed handler");
	}
}
