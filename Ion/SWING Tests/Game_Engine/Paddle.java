package Game_Engine;
public class Paddle{
	int orientation;	// 1 for horizontal and 2 for vertical
	double d1;
	double d2;
	double speed;
	int current_power;
	double delta;		// gap from the base

	public Paddle(int orientation, double d1, double d2, double delta){
		this.orientation = orientation;
		this.d1 = d1;
		this.d2 = d2;
		this.delta = delta;
		this.speed = 0;
		this.current_power = 0;
		//System.out.println("PADDLE VALUES: "+d1+" "+d2);
	}
}