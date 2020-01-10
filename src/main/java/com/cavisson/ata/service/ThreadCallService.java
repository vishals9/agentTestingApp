package com.cavisson.ata.service;
/**
 * @author Vishal Singh
 *
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadCallService {

	public static String callForkJoinThreadService() {
		int nThreads = Runtime.getRuntime().availableProcessors();
		int[] numbers = new int[1000];

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = i;
		}

		ForkJoinPool forkJoinPool = new ForkJoinPool(nThreads);
		Long result = forkJoinPool.invoke(new Sum(numbers, 0, numbers.length));
		System.out.println("Fork Join : thread count = " + nThreads + ", join pool time = " + result);
		return "Fork Join : thread count = " + nThreads + ", join pool time = " + result;
	}

	public static String callSimpleThreadPoolExecutorService() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

		for (int i = 1; i <= 5; i++) {
			Task task = new Task("Task");
			System.out.println("Created : " + task.getName());

			executor.execute(task);
		}
		executor.shutdown();

		return "tasks created. see console";
	}

	// scheduled thread pool executor using ruunable interface
	public static String callScheduledThreadPoolExecutorRunnableService() {
		try {

			Runnable runnabledelayedTask = new Runnable() {
				@Override
				public void run() {
					printStackTrace();
					System.out.println(Thread.currentThread().getName() + " is Running Delayed Task");
				}
			};

			ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);

			// scheduledPool.scheduleWithFixedDelay(runnabledelayedTask, 1, 1,
			// TimeUnit.SECONDS);

			ScheduledFuture sf = scheduledPool.schedule(runnabledelayedTask, 4, TimeUnit.SECONDS);

			String value = (String) sf.get();

			System.out.println("Callable returned" + value);

			scheduledPool.shutdown();

			System.out.println("Is ScheduledThreadPool shutting down? " + scheduledPool.isShutdown());

		} catch (Exception e) {
			System.err.println(e);
		}

		return "DONE";
	}

	// scheduled thread pool executor using callable interface
	public static String callScheduledThreadPoolExecutorCallableService() {
		try {

			Callable callabledelayedTask = new Callable() {

				@Override
				public String call() throws Exception {
					printStackTrace();
					return "GoodBye! See you at another invocation...";
				}
			};

			ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);

			// scheduledPool.scheduleWithFixedDelay(runnabledelayedTask, 1, 1,
			// TimeUnit.SECONDS);

			ScheduledFuture sf = scheduledPool.schedule(callabledelayedTask, 4, TimeUnit.SECONDS);

			String value = (String) sf.get();

			System.out.println("Callable returned" + value);

			scheduledPool.shutdown();

			System.out.println("Is ScheduledThreadPool shutting down? " + scheduledPool.isShutdown());

		} catch (Exception e) {
			System.err.println(e);
		}
		return "DONE";

	}

	public static void printStackTrace() {
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stk = Thread.currentThread().getStackTrace();
		for (StackTraceElement stacktrace : stk) {
			sb.append(stacktrace.toString());
			sb.append("\n");
		}

		System.out.println("Stacktrace :" + sb.toString());

	}

	static class Task implements Runnable {
		private String name;

		public Task(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void run() {
			try {
				Long duration = (long) (Math.random() * 10);
				System.out.println("Executing : " + name);
				TimeUnit.SECONDS.sleep(duration);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	static class Sum extends RecursiveTask<Long> {
		private static final long serialVersionUID = 1L;
		int low;
		int high;
		int[] array;

		Sum(int[] array, int low, int high) {
			this.array = array;
			this.low = low;
			this.high = high;
		}

		protected Long compute() {

			if (high - low <= 10) {
				long sum = 0;

				for (int i = low; i < high; ++i)
					sum += array[i];
				return sum;
			} else {
				int mid = low + (high - low) / 2;
				Sum left = new Sum(array, low, mid);
				Sum right = new Sum(array, mid, high);
				left.fork();
				long rightResult = right.compute();
				long leftResult = left.join();
				return leftResult + rightResult;
			}
		}
	}
}
