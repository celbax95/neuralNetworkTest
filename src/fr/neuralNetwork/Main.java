package fr.neuralNetwork;

import java.util.Random;

import Image.Image;
import fr.matrix.Matrix;

public class Main {

	public static final double mutationRate = 0.1;
	
	
	public static final int untilScore = 15000;
	public static final int trials = 1000;
	public static final int popAmount = 60;
	public static final double popMutated = 0.8;
	public static final int keepers = 5;
	public static final int fitAccu = 5;
	
	public static final boolean display = true;
	
	public static final boolean without = true;
	
	public static final int[] seeBestAt = {1,5,10,15,20,30,40,50};
	
	public static boolean seeBest = true;
	
	public static final boolean onlyBests = true;
	
	public static final boolean displayFitFScoreT = true;
	
	public static final int scorePerLine = 20;
	
	public static Random r = new Random();
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("---        3        ---");
		Thread.sleep(500);
		System.out.println("---        2        ---");
		Thread.sleep(500);
		System.out.println("---        1        ---");
		Thread.sleep(500);
		System.out.println("--- C'est partit !! ---");
		Thread.sleep(200);
		
		Member[] pop = new Member[popAmount];
		for (int i = 0; i < pop.length; i++) {
			pop[i] = new Member();
		}
		
		Member[] best = new Member[keepers];

		Game g = new Game(500,10);
		
		
		int scorePrec = 0;
		
		if (without) {
			while (g.isRunning()) {
				Thread.sleep(80);
				System.out.println(g);
				g.manage(0);
			}
		}
		
		for (int j = 0; scorePrec<untilScore && j<trials ; j++) {
			for (int i = 0; i < pop.length; i++) {				
				double[] memFit = new double[fitAccu];
				int[] memScore = new int[fitAccu];
				for (int k = 0; k < fitAccu; k++) {
					// play
					g.reset();
					while (g.isRunning()) {
						double[][] d = new double[1][2];
						d[0][0] = g.getNormCursor();
						d[0][1] = g.getNormSpd();
						double r = pop[i].think(d);
						g.manage(r);
						
						if (!pop[i].memIsFull()) {
							pop[i].mem(d[0],r);
						}
					}
					// set score
					memFit[k] = g.getFit();
					memScore[k] = g.getScore();
					if (k+1 == fitAccu) {
						pop[i].setFit(Game.average(memFit));
						pop[i].setScore(Game.best(memScore));						
					}
					scorePrec = g.getScore();
				}
				// set bests
				if (j==0 && i<best.length) {
					best[i] = pop[i];
				}
				else {
					int id = Member.worstId(best);
					if (pop[i].getFit() > best[id].getFit())
						best[id] = pop[i];
				}
			}
			
			// See bests
			if (display) {
				System.out.println("\n\n----------------------");
				System.out.println(" Generation "+j+"\n");
				Member[] tm = onlyBests?best:pop;
				for (int i = 0; i < tm.length; i++) {
					System.out.print(" | ");
					if (displayFitFScoreT)
						System.out.print(tm[i].getScore());
					else
						System.out.print(tm[i].getFit());
					if (i%scorePerLine-1==0 && i+1 >= scorePerLine)
						System.out.println(" | ");
				}
				System.out.println(" |\n----------------------\n\n");
			}
				
			// Regen pop
			for (int i = 0; i < pop.length; i++) {
				pop[i] = new Member(best[i%best.length].getNN().clone());
				if(r.nextDouble() < popMutated) {
					pop[i].mutate(mutationRate);
				}
			}
			Member b = Member.best(best);
			scorePrec = b.getScore();
			
			// play best
			if (display) {
				for (int i = 0; i < seeBestAt.length; i++) {
					if (j == seeBestAt[i] || (seeBest && !(scorePrec<untilScore && j<trials))) {
						g.reset();
						while (g.isRunning()) {
							Thread.sleep(8);
							System.out.println(g);
							double[][] d = new double[1][2];
							d[0][0] = g.getNormCursor();
							d[0][1] = g.getNormSpd();
							double r = b.think(d);
							g.manage(r);
						}
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
						if (displayFitFScoreT)
							System.out.println("Score : "+b.getScore());
						else
							System.out.println("Fit : "+b.getFit());
						Thread.sleep(1000);
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
					}
				}
			}
		}
	}
}
