package tsp;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class TobaccoSmokersProblemDeadlockTest {

	static int count;

	static String[] resources_name = new String[] { ("tobacco"), ("paper"), ("matches") };
	static final int TOBACCO = 0;
	static final int PAPER = 1;
	static final int MATCHES = 2;

	static Semaphore agentSemaphore = new Semaphore(1);
	static Semaphore[] smokerSemaphore = new Semaphore[3];

	public static MyGUI mygui = new MyGUI("Tobacco Smokers Problem Deadlock");
	public static Random random = new Random();

	public static void main(String[] args) {

		// smokerSemaphore set 1 to trigger the Deadlock.
		for (int i = 0; i < smokerSemaphore.length; i++) {
			smokerSemaphore[i] = new Semaphore(1);
		}

		Agent paper_matches_agent = new Agent(PAPER, MATCHES, 0);
		Agent matches_tobacco_agent = new Agent(MATCHES, TOBACCO, 1);
		Agent tobacco_paper_agent = new Agent(TOBACCO, PAPER, 2);

		Smoker tobacco_smoker = new Smoker(PAPER, MATCHES, 0);
		Smoker paper_smoker = new Smoker(MATCHES, TOBACCO, 1);
		Smoker matches_smoker = new Smoker(TOBACCO, PAPER, 2);

		tobacco_paper_agent.start();
		paper_matches_agent.start();
		matches_tobacco_agent.start();
		tobacco_smoker.start();
		paper_smoker.start();
		matches_smoker.start();
	}

	private static class Agent extends Thread {
		private int _offer1, _offer2, called_smoker;

		public Agent(int offer1, int offer2, int smoker) {
			super();
			this._offer1 = offer1;
			this._offer2 = offer2;
			this.called_smoker = smoker;
		}

		public void run() {
			while (true) {
				// Set the random delay time for the agent thread.
				try {
					sleep((int) (myRandom() * 4000)+ 1000);
					agentSemaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Put two resources to the table and release the called smoker.
				mygui.put(resources_name[_offer1], resources_name[_offer2]);
				System.out.println(
						"Agent puts " + resources_name[_offer1] + " and " + resources_name[_offer2] + " to the table.");
				Table.putItem(_offer1, _offer2);
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				smokerSemaphore[called_smoker].release();
			}
		}
	}

	private static class Smoker extends Thread {
		private int _need1, _need2;
		private boolean completeSmoke;
		private int ID;

		public Smoker(int need1, int need2, int ID) {
			super();
			this._need1 = need1;
			this._need2 = need2;
			this.ID = ID;
			this.completeSmoke = false;
		}

		public void run() {
			while (true) {
				// Set the random delay time for the smoker thread.
				try {
					sleep((int) (myRandom() * 8000)+ 2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Random the two needed resources.
				if ((int) (myRandom() * 2) == 0) {
					int temp = this._need1;
					this._need1 = this._need2;
					this._need2 = temp;
				}

				// Take the first needed resource.
				if (Table.isItemExisted(_need1)) {
					try {
						smokerSemaphore[ID].acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Table.getItem(_need1);
					mygui.get(ID, resources_name[_need1]);
					System.out.println("Smoker[" + ID + "] takes " + resources_name[_need1] + " from the table");

					// Set delay for taking the second needed resource.
					try {
						sleep((int) (myRandom() * 2000) + 300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// Take the second needed resource.
					if (Table.getItem(_need2)) {
						mygui.get(ID, resources_name[_need2]);
						System.out.println("Smoker[" + ID + "] takes " + resources_name[_need2] + " from the table");
						this.completeSmoke = true;
					} else {
						System.out.println("Smoker[" + ID + "] found no " + resources_name[_need2] + " on the table");
						System.out.println("!!!The Deadlock Happens!!!");
						mygui.anger(ID);
					}
				}

				// Complete to smoke and release the agent.
				if (this.completeSmoke) {
					this.completeSmoke = false;
					try {
						sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mygui.smoke(ID);
					System.out.println("***Smoker[" + ID + "] complete the smoking.");
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mygui.restart();
					agentSemaphore.release();
				}
			}
		}
	}

	private static class Table {
		private static boolean[] resources = new boolean[] { false, false, false };

		// Put two items to the table.
		public static void putItem(int item1, int item2) {
			resources[item1] = resources[item2] = true;
			System.out.println(resources_name[item1] + " and " + resources_name[item2] + " are put on the table.");
		}

		// Get the item from the table.
		public static boolean getItem(int item) {
			if (resources[item]) {
				resources[item] = false;
				return true;
			} else {
				return false;
			}
		}

		// Check if the item is existed on the table.
		public static boolean isItemExisted(int item) {
			return resources[item];
		}
	}

	private static double myRandom() {

		double u = random.nextDouble();

		int x = 0;
		double cdf = 0;
		while (u >= cdf) {
			x++;
			cdf = 1 - Math.exp(-1.0 * 0.1 * x);
		}
		return (double) (x) / 100;
	}
}