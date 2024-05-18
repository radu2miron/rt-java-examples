package edu.tucn.str.lecture6.rtthreads.periodic1;

import javax.realtime.*;

public class AperiodicThread extends RealtimeThread {

	public AperiodicThread(PriorityParameters scheduling,
			AperiodicParameters release, int queueSize) {
		super(scheduling, release);
		releaseTimes = new AbsoluteTime[queueSize];
		first = queueSize - 1;
		last = 0;
		size = queueSize;
		myClock = Clock.getRealtimeClock();
		myDeadline = release.getDeadline();
		MonitorControl.setMonitorControl(this, PriorityInheritance.instance());
	}

	public void start() {
		// Save the release time.
		synchronized (this) {
			releaseTimes[last] = myClock.getTime();
			last = (last + 1) % size;
		}
		super.start();
	}

	public boolean waitForNextRelease() {
		synchronized (this) {
			first = (first + 1) % size;
			// Check for miss deadline.
			AbsoluteTime lastDeadline = releaseTimes[first].add(myDeadline);
			if (myClock.getTime().compareTo(lastDeadline) == 1)
				return false; // deadline miss
			try {
				while (((first + 1) % size) == last)
					wait();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	public void release() {

		synchronized (this) {

			// Throws ResourceLimitError.
			if (first == last)
				throw new ResourceLimitError();

			releaseTimes[last] = myClock.getTime();
			last = (last + 1) % size;
			notify();
		}

	}

	private AbsoluteTime releaseTimes[];

	private int first, last, size;

	private Clock myClock;

	private RelativeTime myDeadline;

}
