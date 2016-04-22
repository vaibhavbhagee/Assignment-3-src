public class Paddle{
	int orientation;	// 1 for horizontal and 2 for vertical
	double d1;
	double d2;
	double delta;		// gap from the base
	double posX1;		// end point 1
	double posX2;
	double posY1;		// end point 2
	double posY2;

	public Paddle(int orientation, double d1, double d2, double delta){
		this.orientation = orientation;
		this.d1 = d1;
		this.d2 = d2;
		this.delta = delta;
	}
}