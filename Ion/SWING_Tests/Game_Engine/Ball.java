package Game_Engine;
public class Ball{
	static int ball_count = 0;
	int ball_number;// ball number
	double posX;	// x coordinate of ball
	double posY;	// y coordinate of ball
	double theetha;	// tan_inverse
	double spin;
	double diameter;	// diameter of ball

	public Ball(double posX, double posY, double theetha, double diameter){
		this.ball_number = ball_count;
		this.posX = posX;
		this.posY = posY;
		spin = Var.speed*Math.PI/Var.freq/Var.spin_factor;	
		this.theetha = theetha;
		this.diameter = diameter;
		++ball_count;
	}

	public void set_velocity(double t){
		if(t<0) theetha = t + 2*Math.PI;
		else theetha = t;
	}

	public void update_position(){
		//System.out.println("Theetha: "+(theetha*180/Math.PI)+" ");
		spin *= Var.spin_decay_factor;
		theetha += spin;
		if(theetha > 2*Math.PI) theetha -= 2*Math.PI;
		if(theetha < 0) theetha += 2*Math.PI;
		posX += Math.cos(theetha) * Var.speed * (Var.speed_increase + 1);
		posY += Math.sin(theetha) * Var.speed * (Var.speed_increase + 1);
	}
	public void addSpin(double x){
		spin += Var.speed*x*Math.PI/Var.freq/Var.spin_factor/100;
		if(spin>0.045) spin=0.045;
		if(spin<-0.045) spin=-0.045;
		//System.out.println(spin*100);
	}

	public String to_String(){
		return (posX+"#"+posY+"#"+theetha+"#"+spin+";");
	}
	public void from_String(String val, int their_joining_order, int my_joining_order){
		int difference = my_joining_order - their_joining_order;
		if(difference < 0) difference+=4;
		String[] s = val.split("#");
		spin = Double.parseDouble(s[3]);
		if(difference == 0){
			posX = Double.parseDouble(s[0]);
			posY = Double.parseDouble(s[1]);
			theetha = Double.parseDouble(s[2]);
		}else if(difference == 1){
			posX = -Double.parseDouble(s[1]);
			posY = Double.parseDouble(s[0]);
			theetha = Double.parseDouble(s[2]) + Math.PI/2;
			if(theetha > 2*Math.PI) theetha -= 2*Math.PI;
		}else if (difference == 2){
			posX = -Double.parseDouble(s[0]);
			posY = -Double.parseDouble(s[1]);
			theetha = Double.parseDouble(s[2]) + Math.PI;
			if(theetha > 2*Math.PI) theetha -= 2*Math.PI;
		}else if (difference == 3){
			posX = Double.parseDouble(s[1]);
			posY = -Double.parseDouble(s[0]);
			theetha = Double.parseDouble(s[2]) + 3*Math.PI/2;
			if(theetha > 2*Math.PI) theetha -= 2*Math.PI;
		}		
	}
}