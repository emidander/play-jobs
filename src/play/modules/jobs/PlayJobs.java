/** 
 * Copyright 2011 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Felipe Oliveira (http://mashup.fm)
 * 
 */
package play.modules.jobs;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import play.jobs.OnApplicationStart;
import play.modules.jobs.PlayJobs.JobEntry;

/**
 * The Class ScheduledJobs.
 */
public class PlayJobs implements Iterable<JobEntry> {

	/** The pool size. */
	private int poolSize;

	/** The active count. */
	private int activeCount;

	/** The task count. */
	private long taskCount;

	/** The queue size. */
	private int queueSize;

	/** The scheduled jobs. */
	private List<JobEntry> scheduledJobs;

	/**
	 * Instantiates a new scheduled jobs.
	 * 
	 * @param poolSize
	 *            the pool size
	 * @param activeCount
	 *            the active count
	 * @param taskCount
	 *            the task count
	 * @param queueSize
	 *            the queue size
	 * @param scheduledJobs
	 *            the scheduled jobs
	 * @param queue
	 *            the queue
	 */
	public PlayJobs(int poolSize, int activeCount, long taskCount, int queueSize, List<JobEntry> scheduleJobs) {
		super();
		this.poolSize = poolSize;
		this.activeCount = activeCount;
		this.taskCount = taskCount;
		this.queueSize = queueSize;
		this.scheduledJobs = scheduleJobs;
	}

	/**
	 * Instantiates a new scheduled jobs.
	 */
	public PlayJobs() {

	}

	/**
	 * Scheduled Jobs Iterator
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<JobEntry> iterator() {
		return this.scheduledJobs.iterator();
	}

	/**
	 * Gets the pool size.
	 * 
	 * @return the pool size
	 */
	public int getPoolSize() {
		return this.poolSize;
	}

	/**
	 * Gets the active count.
	 * 
	 * @return the active count
	 */
	public int getActiveCount() {
		return this.activeCount;
	}

	/**
	 * Gets the task count.
	 * 
	 * @return the task count
	 */
	public long getTaskCount() {
		return this.taskCount;
	}

	/**
	 * Gets the queue size.
	 * 
	 * @return the queue size
	 */
	public int getQueueSize() {
		return this.queueSize;
	}

	/**
	 * Gets the scheduled jobs.
	 * 
	 * @return the scheduled jobs
	 */
	public List<JobEntry> getScheduledJobs() {
		return this.scheduledJobs;
	}

	/**
	 * The Class JobEntry.
	 */
	public static class JobEntry implements Comparator<JobEntry> {

		/** The job class. */
		private Class jobClass;

		/** The last run. */
		private String lastRun = "n/a";

		/** The run on application start. */
		private String runOnApplicationStart = "n/a";

		/** The run on cron. */
		private String runOnCron = "n/a";

		/** The run every so often. */
		private String runEverySoOften = "n/a";

		/** The last run was error. */
		private String lastRunWasError = "n/a";

		/**
		 * Instantiates a new job entry.
		 */
		public JobEntry() {

		}

		/**
		 * Instantiates a new job entry.
		 * 
		 * @param jobClass
		 *            the job class
		 */
		public JobEntry(Class jobClass) {
			this.jobClass = jobClass;
			this.runOnApplicationStart = this.annotationCheck(OnApplicationStart.class, jobClass);
			this.runOnCron = this.annotationCheck(On.class, jobClass);
			this.runEverySoOften = this.annotationCheck(Every.class, jobClass);
		}

		/**
		 * Instantiates a new schedule job.
		 * 
		 * @param job
		 *            the job
		 */
		public JobEntry(Job job) {
			// PrettyTime prettyTime = new PrettyTime();
			// prettyTime.format(new Date(job.lastRun));
			this.jobClass = job.getClass();
			this.runOnApplicationStart = this.annotationCheck(OnApplicationStart.class, job);
			this.runOnCron = this.annotationCheck(On.class, job);
			this.runEverySoOften = this.annotationCheck(Every.class, job);
		}

		/**
		 * Annotation check.
		 * 
		 * @param clazz
		 *            the clazz
		 * @param job
		 *            the job
		 * @return the string
		 */
		private String annotationCheck(Class<? extends Annotation> clazz, Job job) {
			return this.annotationCheck(clazz, job.getClass());
		}

		/**
		 * Annotation check.
		 * 
		 * @param clazz
		 *            the clazz
		 * @param jobClass
		 *            the jobClass
		 * @return the string
		 */
		private String annotationCheck(Class<? extends Annotation> clazz, Class jobClass) {
			if (jobClass == null) {
				return "n/a";
			}
			if (jobClass.isAnnotationPresent(clazz)) {
				return "Yes";
			} else {
				return "-";
			}
		}

		/**
		 * Gets the job class.
		 * 
		 * @return the job class
		 */
		public Class getJobClass() {
			return this.jobClass;
		}

		/**
		 * Gets the last run.
		 * 
		 * @return the last run
		 */
		public String getLastRun() {
			return this.lastRun;
		}

		/**
		 * Gets the run on application start.
		 * 
		 * @return the run on application start
		 */
		public String getRunOnApplicationStart() {
			return this.runOnApplicationStart;
		}

		/**
		 * Gets the run on cron.
		 * 
		 * @return the run on cron
		 */
		public String getRunOnCron() {
			return this.runOnCron;
		}

		/**
		 * Gets the run every so often.
		 * 
		 * @return the run every so often
		 */
		public String getRunEverySoOften() {
			return this.runEverySoOften;
		}

		/**
		 * Gets the last run was error.
		 * 
		 * @return the last run was error
		 */
		public String getLastRunWasError() {
			return this.lastRunWasError;
		}

		/**
		 * Compare.
		 * 
		 * @param arg0
		 *            the arg0
		 * @param arg1
		 *            the arg1
		 * @return the int
		 */
		@Override
		public int compare(JobEntry arg0, JobEntry arg1) {
			if ((arg0 != null) && (arg1 != null) && (arg0.getClass() != null) && (arg1.getClass() != null)) {
				return arg0.getClass().getName().compareTo(arg1.getClass().getName());
			}
			return 0;
		}

	}

}
