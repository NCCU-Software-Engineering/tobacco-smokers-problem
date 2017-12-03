package tsp;

import java.util.concurrent.Semaphore;

public class TobaccoSmokersProblem {

	static int count;

	static String[] resources_name = new String[] { ("tobacco"), ("paper"), ("matche") };
	static final int TIBACCO = 0;
	static final int PAPER = 1;
	static final int MATCHE = 2;

	static Semaphore TS = new Semaphore(1);
	static Semaphore AS = new Semaphore(0);

	public static void main(String[] args) {
		Agent agent = new Agent();
		Smoker no_tobacco_smoker = new Smoker(TIBACCO);
		Smoker no_paper_smoker = new Smoker(PAPER);
		Smoker no_matche_smoker = new Smoker(MATCHE);

		agent.start();
		no_tobacco_smoker.start();
		no_paper_smoker.start();
		no_matche_smoker.start();
	}

	private static class Agent extends Thread {
		public Agent() {
			super();
		}

		public void run() {
			while (true) {
				System.out.println("The agent is awake");
				try {
					sleep((int) (Math.random() * 10000));
				} catch (InterruptedException e) {

				}
				try {
					TS.acquire();
				} catch (InterruptedException e) {
				}

				System.out.println("The agent is running");
				int item = (int) (Math.random() * 3);
				Table.put(item);

				TS.release();
			}
		}
	}

	private static class Smoker extends Thread {
		int _need;

		public Smoker(int need) {
			super();
			_need = need;
		}

		public void run() {
			while (true) {
				try {
					sleep((int) (Math.random() * 10000));
				} catch (InterruptedException e) {
				}
				System.out.println("Smoker " + resources_name[_need] + ": queued up for table");
				if (Table.use(_need)) {
					System.out.println("Smoker " + _need + " found " + resources_name[_need]);
					TS.release();
					System.out.println("Smoker " + _need + " is smoking ");
				}
			}
		}
	}

	private static class Table {
		private static boolean[] resources = new boolean[3];

		public static void put(int item) {
			resources[item] = true;
			System.out.println("put " + resources_name[item] + " in the table");
		}

		public static boolean use(int item) {
			if (resources[item]) {
				resources[item] = false;
				return true;
			} else {
				return false;
			}
		}
	}

}
