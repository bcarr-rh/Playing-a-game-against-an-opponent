import java.util.ArrayList;
import java.util.Timer;
public class Player {
	long time;
	public Tree gameTree = null;
	public Player(Tree tree) {gameTree=tree;}
	public Boolean play(ArrayList<Boolean> moves) {
		
		time = System.currentTimeMillis();
		return null;
		
		
	}
	private  pointValueStruct good(pointValueStruct p) {
		float truefloat = gameTree.value(p.moves.add(true));
		float falsefloat = gameTree.value(p.moves.add(false));
		return null;
				
	}
	private class pointValueStruct {
		public float Alpha;
		public float Beta;
		public ArrayList<Boolean> moves;
		public pointValueStruct(float A, float B,ArrayList<Boolean> m) {
			Alpha = A;
			Beta = B;
			moves = m;
		}
	}

}
