package edu.tucn.str.lecture6.rtthreads.periodic4;

import javax.realtime.RealtimeThread;
import javax.realtime.ReleaseParameters;
import javax.realtime.SchedulingParameters;

public class MyThread extends RealtimeThread {

	public MyThread(SchedulingParameters sched, ReleaseParameters release) {
		super(sched, release);
	}

	public void run() {
		RealtimeThread me = currentRealtimeThread();
		int T = 0;
		do {
			System.out.println(this.getName() + "este in T " + T++);

			for (int i = 0; i < 99000; i++) {
				i++;
				i--;
			}
		} while (me.waitForNextPeriod());
	}
}