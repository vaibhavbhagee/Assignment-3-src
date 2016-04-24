package Game_Engine;
public class Paddle{
	int orientation;	// 1 for horizontal and 2 for vertical
	double d1;
	double d2;
	int current_power;
	int paddle_speed;
	double delta;		// gap from the base

	public Paddle(int orientation, double d1, double d2, double delta){
		this.orientation = orientation;
		this.d1 = d1;
		this.d2 = d2;
		this.delta = delta;
		this.current_power = 0;
		paddle_speed = 0;
		//System.out.println("PADDLE VALUES: "+d1+" "+d2);
	}
	public void movePaddle(double x, double y){
		double factor = 1;
		double center = (d1+d2)/2;
		if(orientation==1){
			if(center>x){
				d1 -= Var.speed * factor;
				d2 -= Var.speed * factor;
			}else{
				d1 += Var.speed * factor;
				d2 += Var.speed * factor;
			}
		}else{
			if(center>y){
				d1 -= Var.speed * factor;
				d2 -= Var.speed * factor;
			}else{
				d1 += Var.speed * factor;
				d2 += Var.speed * factor;
			}
		}
		//System.out.println(center + " ajkshdkj " + y);
	}
}