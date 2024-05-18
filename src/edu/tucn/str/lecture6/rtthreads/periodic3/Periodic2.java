package edu.tucn.str.lecture6.rtthreads.periodic3;

import javax.realtime.*;

public class Periodic2 {
	public static void main(String[] args) {
		SchedulingParameters scheduling = new PriorityParameters(
				PriorityScheduler.instance().getMinPriority() + 10);

		RealtimeThread rt = new MyThread2(scheduling);
		rt.start();
		try {
			rt.join();
		} catch (Exception e) {
		}
		;
	}
}