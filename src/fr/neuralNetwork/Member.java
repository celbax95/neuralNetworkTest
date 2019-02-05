package fr.neuralNetwork;

public class Member {

	private NeuralNetwork n;
	private double[][] in;
	private double[][] out;
	private double fit;
	private int score;
	private int memStat;

	public Member() {
		in = new double[20][2];
		out = new double[20][1];
		memStat = 0;
		n = new NeuralNetwork(2,10,1);
	}
	public Member(NeuralNetwork n) {
		in = new double[20][2];
		out = new double[20][1];
		memStat = 0;
		this.n = n;
	}
	public Member(Member m) {
		in = m.in.clone();
		out = m.out.clone();
		memStat = 0;
		n = m.n.clone();
	}
	
	public Member clone() {
		return new Member(this);
	}
	
	public double think(double[][] in) {
		return n.think(in)[0][0];
	}
	
	public boolean memIsFull() {
		return memStat >= 20;
	}
	
	public void mem(double[] d, double r) {
		in[memStat] = d;
		out[memStat++][0] = r;
	}
	
	public void setFit(double f) {
		fit = f;
	}
	public void setScore(int s) {
		score = s;
	}
	
	public double[][] getIn() {
		return in;
	}
	public double[][] getOut() {
		return out;
	}
	
	public double getFit() {
		return fit;
	}
	public int getScore() {
		return score;
	}
	
	public void mutate(double rate) {
		n.mutate(rate);
	}
	
	public NeuralNetwork getNN() {
		return n;
	}
	
	public static Member best(Member...m) {
		return m[bestId(m)];
	}
	public static int bestId(Member...m) {
		int w = 0;
		for (int k = 0; k < m.length; k++) {
			if (m[k].getFit() > m[w].getFit())
				w = k;
		}
		return w;
	}
	public static Member worst(Member...m) {
		return m[worstId(m)];
	}
	public static int worstId(Member...m) {
		int w = 0;
		for (int k = 0; k < m.length; k++) {
			if (m[k].getFit() < m[w].getFit())
				w = k;
		}
		return w;
	}
}
