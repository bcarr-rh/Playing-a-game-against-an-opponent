import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;

public class Player {
	long time;
	public Tree gameTree = null;

	public Player(Tree tree) {
		gameTree = tree;
	}

	private ArrayList<pointValueStruct> pvsList;

	public Boolean play(ArrayList<Boolean> moves) throws InterruptedException {

		pointValueStruct max = new pointValueStruct(-99999,-99999,moves);
		time = System.currentTimeMillis();
		(new good(new pointValueStruct(0,0,moves))).start();
		this.wait(0, 26000);
		for (pointValueStruct pvs : pvsList)
			if (pvs.Alpha + pvs.Beta > max.Alpha + max.Beta) 
				max = pvs;
		return max.moves.get(moves.size());

	}

	private class good extends Thread {

		private pointValueStruct pvs;

		public good(pointValueStruct p) {
			pvs = p;
		}

		public void run() {

			if (System.currentTimeMillis() - time >= 25000 || pvs.moves.size() >= gameTree.height) {
				pvsList.add(pvs);
				return;
			} else {
				pvs.moves.add(true);
				float truefloat = gameTree.value(pvs.moves);
				if (pvs.Alpha + truefloat + pvs.Beta >=0) {
					pvs.Alpha += truefloat;
					(new bad(pvs)).start();
					pvs.Alpha -= truefloat;
				}
				pvs.moves.remove(pvs.moves.size() - 1);
				pvs.moves.add(false);
				float falsefloat = gameTree.value(pvs.moves);
				if (pvs.Alpha + falsefloat + pvs.Beta >= 0) {
					pvs.Alpha += falsefloat;
					(new bad(pvs)).start();
				}
			}
			return;
		

		}
	}

	private class bad extends Thread {
		private pointValueStruct pvs;
		
		public bad(pointValueStruct p) {
			pvs = p;
		}

		public void run() {
			if (System.currentTimeMillis() - time >= 25000 || pvs.moves.size() >= gameTree.height) {
				pvsList.add(pvs);
				return;
			} else {
				pvs.moves.add(true);
				float truefloat = gameTree.value(pvs.moves);
				pvs.moves.remove(pvs.moves.size() - 1);
				pvs.moves.add(false);
				float falsefloat = gameTree.value(pvs.moves);
				pvs.moves.remove(pvs.moves.size() - 1);
				if (truefloat < falsefloat) {
					pvs.moves.add(true);
					pvs.Beta += truefloat;
				} else {
					pvs.moves.add(false);
					pvs.Beta += falsefloat;
				}
			}
			return;
		}
	}
	

	private class pointValueStruct {
		public float Alpha;
		public float Beta;
		public ArrayList<Boolean> moves;

		public pointValueStruct(float A, float B, ArrayList<Boolean> m) {
			Alpha = A;
			Beta = B;
			moves = m;
		}
	}

}
