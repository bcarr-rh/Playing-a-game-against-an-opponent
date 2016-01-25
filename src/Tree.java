
import java.util.ArrayList;
import java.util.Random;

public class Tree {
	public final int TOTALNODES=2097152;
	public final float EPSILON=0.00001f;
	public final float distribution=10.0f;
	
	public long randomSeed=0L;
	public float[] topTree=new float[TOTALNODES];
	private Random r=new Random();
	public long height=40;
	
	public Tree(long rs) {
		randomSeed=rs;
		r.setSeed(randomSeed);
		topTree[0]=topTree[1]=0.0f;
		int current=2; int levelNodes=2; float factor=1.0f;
		while (current<TOTALNODES) {
			for (int i=0; i<levelNodes; i++) {
				int parent=current/2;
				float sign=0.0f;
				if (r.nextBoolean()&&r.nextBoolean())
					if (topTree[parent]>EPSILON) sign=1.0f; else sign=-1.0f;
				else if (topTree[parent]>EPSILON) sign=-1.0f; else sign=1.0f;
				float offset=((Math.abs(topTree[parent])<EPSILON)?(r.nextFloat()*2.0f*distribution-distribution):(r.nextFloat()*distribution*factor*sign));
				topTree[current]=topTree[parent]+offset;
				current++; }
			levelNodes*=2; factor*=0.9f; }
	}
	
	public float value(ArrayList<Boolean> moves) {
		// low and high will both be values between 0 and 2^21-1=TOTALNODES.
		// The depth will be the number of bits examined, starting with the low order bit of the low int.
		// A depth of 0 will indicate the root.
		int position=1;
		for (int i=0; i<Math.min(20, moves.size()); i++) {
			if (moves.get(i).booleanValue()) position=position*2+1; else position*=2; }
		if (moves.size()<=20) return topTree[position];
		r.setSeed(randomSeed+position);
		
		float[] bottomTree=new float[TOTALNODES];
		bottomTree[0]=bottomTree[1]=topTree[position];
		int current=2; int levelNodes=2; float factor=0.12157665459056928801f;
		while (current<TOTALNODES) {
			for (int i=0; i<levelNodes; i++) {
				int parent=current/2;
				float sign=0.0f;
				if (r.nextBoolean()&&r.nextBoolean())
					if (bottomTree[parent]>EPSILON) sign=1.0f; else sign=-1.0f;
				else if (bottomTree[parent]>EPSILON) sign=-1.0f; else sign=1.0f;
				float offset=((Math.abs(bottomTree[parent])<EPSILON)?(r.nextFloat()*2.0f*distribution-distribution):(r.nextFloat()*distribution*factor*sign));
				bottomTree[current]=bottomTree[parent]+offset;
				current++; }
			levelNodes*=2; factor*=0.9f; }
		
		position=1;
		for (int i=20; i<moves.size(); i++) {
			if (moves.get(i).booleanValue()) position=position*2+1; else position*=2; }
		return bottomTree[position];
	}
}

