package Image;

public class Image {
	
	private char[][] img; //[x][y]

	private int w, h;
	
	public Image(String s, int w, int h) {
		this.w = w; this.h = h;
		this.img = new char[w][h];
		
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				img[j][i] = ' ';
			}
		}
		String[] ts = s.split("\n");
		for (int i = 0; i < ts.length; i++) {
			for (int j = 0; j < ts[i].length(); j++) {
				img[j][i] = ts[i].charAt(j);
			}
		}
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				s += " "+img[j][i]+" ";
			}
			s += "\n";
		}
		return s;
	}

	public double[] normalize() {
		double[] d = new double[w*h+3];
		int cpt = 0,x1=-1,y1=-1,x2=-1,y2=-1;
		
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (img[j][i] != ' ')
					d[i*j+j+3] = 0;
				else {
					d[i*j+j] = 1;
					cpt++;
					if (j<x1 || x1 == -1)
						x1 = j;
					if (j>x2 || x2 == -1)
						x2 = j;
					if (i<y1 || y1 == -1)
						y1 = i;
					if (i>y2 || y2 == -1)
						y2 = i;
				}
			}
		}
		d[0] = cpt/(h*w);
		d[1] = (x2-x1)/10;
		d[2] = (y2-y1)/10;
		
		return d;
	}
	
}
