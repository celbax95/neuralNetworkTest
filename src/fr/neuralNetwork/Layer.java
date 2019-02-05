package fr.neuralNetwork;

import java.util.Random;

import fr.matrix.Matrix;

public class Layer {

	private static Random r = new Random(1);
	
	private int nb, nbIn;
	private double[][] synW;
	
	public Layer(int nbNeuron, int nbCoPerNeuron) {
		nb = nbNeuron;
		nbIn = nbCoPerNeuron;
		
		synW = Matrix.subtract(Matrix.multiply(Matrix.random(nbIn, nb, r),2),1);
	}
	public Layer(Layer l) {
		nb = l.nb;
		nbIn = l.nbIn;
		synW = new double[l.synW.length][l.synW[0].length];
		for (int i = 0; i < synW.length; i++) {
			for (int j = 0; j < synW[0].length; j++) {
				synW[i][j] = l.synW[i][j];
			}
		}
	}
	
	public double[][] sigmoid(double[][] a) {
		double[][] r = new double[a.length][a[0].length];
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				r[i][j] = 1/(1+Math.exp(-a[i][j]));
			}
		}
		return r;
	}
	
	public void adjustW(double[][] v) {
		synW = Matrix.add(synW, v);
	}
	
	public double[][] getSynW() {
		return synW;
	}
	
	public void mutate(double rate) {
		synW = Matrix.multiplyEach(synW,Matrix.add( 
				Matrix.subtract(Matrix.multiply(Matrix.random(nbIn, nb), rate*2), rate),1));
	}
	
	public Layer clone() {
		return new Layer(this);
	}
	
	public double[][] think(double[][] in) {
		return sigmoid(Matrix.multiply(in, synW));
	}
}
