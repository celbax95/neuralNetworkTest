package fr.neuralNetwork;

import java.util.Random;

import fr.matrix.Matrix;

public class NeuralNetwork {

	Layer[] l;
	
	public NeuralNetwork(Layer...l) {
		this.l = l;
	}
	public NeuralNetwork(int...in) {
		this.l = new Layer[in.length-1];
		for (int i = 0; i < in.length-1; i++) {
			l[i] = new Layer(in[i+1],in[i]);
		}
	}
	public NeuralNetwork(NeuralNetwork n) {
		l = new Layer[n.l.length];
		for (int i = 0; i < l.length; i++) {
			l[i] = n.l[i].clone();
		}
	}
	
	private double[][] sigmoidDerivative(double[][] a) {
		double[][] r = new double[a.length][a[0].length];
		
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				r[i][j] = a[i][j]*(1-a[i][j]);
			}
		}
		return r;
	}
	
	public void train(double[][] inT, double[][] outT, int nbT) {
		for (int i = 0; i < nbT; i++) {
			
			double[][][] outL = new double[l.length][][];
			
			outL[0] = l[0].think(inT);
			for (int j = 1; j < l.length; j++) {
				outL[j] = l[j].think(outL[j-1]);
			}
			
			double[][] delta = null;
			for (int j = l.length-1; j >= 0; j--) {
				
				if (j==l.length-1)
					delta = Matrix.multiplyEach(Matrix.subtract(outT, outL[j]), sigmoidDerivative(outL[j]));
				else
					delta = Matrix.multiplyEach(Matrix.multiply(delta, Matrix.transpose(l[j+1].getSynW())), sigmoidDerivative(outL[j]));
				
				if (j-1 < 0)
					l[j].adjustW(Matrix.multiply(Matrix.transpose(inT), delta));
				else
					l[j].adjustW(Matrix.multiply(Matrix.transpose(outL[j-1]), delta));
			}
		}
	}
	
	private double[][] think(double[][] in, int i) {
		if (i==0)
			return l[i].think(in);
		return l[i].think(think(in,i-1));
	}
	
	public double[][] think(double[][] in) {
		return think(in,l.length-1);
	}
	
	public void mutate(double rate) {
		for (int i = 0; i < l.length; i++) {
			l[i].mutate(rate);
		}
	}
	
	public NeuralNetwork clone() {
		return new NeuralNetwork(this);
	}
	
	public String WeightsToString() {
		String s = "";
		for (int i = 0; i < l.length; i++) {
			s+="\tLayer " + i + " :\n";
			s+= Matrix.getString(l[i].getSynW())+"\n";
		}
		return s;
	}
}
