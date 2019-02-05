package fr.neuralNetwork;

import java.util.Random;

public class Game {

	private static Random r = new Random();
	private final static double MAXSPEED = 20;
	private final static double WIN = 100000;
	
	private int SIZE;
	private double cursor;
	private double mid;
	private double spd;
	private double accel;
	private int score;
	
	public Game(int size, double accel) {
		SIZE = size;
		mid = (double)size/2;
		this.accel = accel;
		spd = 0;
		score = 0;
		cursor = mid;
	}

	public void reset() {
		cursor = mid;
		spd = 0;
		score = 0;
	}
	
	public void manage(double move) {
		if (move >= -1 && move <= 1 && move != 0)
			move = move*2-1;
		cursor+=spd;
		spd += move*accel;
		double ta = accel*(r.nextDouble()-0.5);
		spd += ta;
		//System.out.println(ta + " - " + spd);
		if (Math.abs(spd) > MAXSPEED)
			spd = MAXSPEED*Math.signum(spd);
		score++;
	}

	public static Random getR() {
		return r;
	}

	public int getSIZE() {
		return SIZE;
	}

	public double getCursor() {
		return cursor;
	}
	public double getNormCursor() {
		return cursor/500;
	}

	public double getMid() {
		return mid;
	}

	public double getSpd() {
		return spd;
	}
	public double getNormSpd() {
		return spd/MAXSPEED;
	}

	public double getAccel() {
		return accel;
	}

	public int getScore() {
		return score;
	}
	
	public double getFit() {
		return (double)(score)/WIN;
	}
	
	public boolean isRunning() {
		return cursor>=0 && cursor <= SIZE;
	}
	
	public String toString() {
		String s = "|";
		for (int i = 0; i < SIZE*100/SIZE; i++) {
			if ((int)Math.round(cursor*100/SIZE) == i)
				s+="o";
			else
				s+=" ";
		}
		s+="| "+score;
		return s;
	}
	
	public static double average(double[] d) {
		double td = 0;
		for (int i = 0; i < d.length; i++) {
			td += d[i];
		}
		return Math.floor((td/d.length)*WIN)/WIN;
	}
	public static int best(int[] d) {
		int b = 0;
		for (int i = 1; i < d.length; i++) {
			if (d[i] > d[b])
				b = i;
		}
		return d[b];
	}
}
