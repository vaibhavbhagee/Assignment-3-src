package Game_Engine;

public class Paddle{
	int orientation;	// 1 for horizontal and 2 for vertical
	double d1;
	double d2;
	int playerID;
	int current_power;
	int paddle_speed;
	double delta;		// gap from the base
	boolean disable = false;

	public Paddle(int orientation, double d1, double d2, double delta, int playerID){
		this.orientation = orientation;
		this.d1 = d1;
		this.d2 = d2;
		this.delta = delta;
		this.current_power = 0;
		paddle_speed = 0;
		this.playerID = playerID;
	}

	public double mod(double a)
	{
		if(a>0) return a;
		else return (-a);
	}

	public String to_String(){
		return (d1+"#"+d2+"#"+current_power+"#"+paddle_speed+"#"+disable+";");
	}

	public void from_String(String val, boolean invert){
		String[] s = val.split("#");
		if(invert){
			d2 = Var.height - Double.parseDouble(s[0]);
			d1 = Var.height - Double.parseDouble(s[1]);
		}else{
			d1 = Double.parseDouble(s[0]);
			d2 = Double.parseDouble(s[1]);
		}
		current_power = Integer.parseInt(s[2]);
		paddle_speed = Integer.parseInt(s[3]);
		disable = Boolean.parseBoolean(s[4]);
	}

	public void movePaddle(double x, double y, int level_AI){
		if(disable) return;
		double center = (d1+d2)/2;
		double factor1 = 1.9*3*Math.abs(center-x)/Var.width;
		double factor2 = 1.9*3*Math.abs(center-y)/Var.height;
		if(orientation==1 && playerID==0){
			if(y<286)
			{
				if(center>x && mod(center-x)>10 ){
					d1 -= Var.speed*Var.level_AI[level_AI]*factor1;
					d2 -= Var.speed*Var.level_AI[level_AI]*factor1;
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<x && mod(center-x)>10 ){
					d1 += Var.speed*Var.level_AI[level_AI]*factor1;
					d2 += Var.speed*Var.level_AI[level_AI]*factor1;
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}

		}else if(orientation==1 && playerID==2){
			if(y>300)
			{
				if(center>x && mod(center-x)>10 ){
					d1 -= Var.speed*Var.level_AI[level_AI]*factor1;
					d2 -= Var.speed*Var.level_AI[level_AI]*factor1;
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<x && mod(center-x)>10 ){
					d1 += Var.speed*Var.level_AI[level_AI]*factor1;
					d2 += Var.speed*Var.level_AI[level_AI]*factor1;
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}
		}else if(orientation==2 && playerID==3){
			if(x>300)
			{
				if(center>y && mod(center-y)>10 ){
					d1 -= Var.speed*Var.level_AI[level_AI]*factor2;
					d2 -= Var.speed*Var.level_AI[level_AI]*factor2;
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<y && mod(center-y)>10 ){
					d1 += Var.speed*Var.level_AI[level_AI]*factor2;
					d2 += Var.speed*Var.level_AI[level_AI]*factor2;
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}
		}else if(orientation==2 && playerID==1){
			if(x<286)
			{
				if(center>y && mod(center-y)>10 ){
					d1 -= Var.speed*Var.level_AI[level_AI]*factor2;
					d2 -= Var.speed*Var.level_AI[level_AI]*factor2;
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<y && mod(center-y)>10 ){
					d1 += Var.speed*Var.level_AI[level_AI]*factor2;
					d2 += Var.speed*Var.level_AI[level_AI]*factor2;
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}
		}else{
			paddle_speed = 0;
		}
		paddle_speed /=2;
		approveTheseValues(d1,d2,d2-d1);
	}

	public void set_power_up(int level_AI){
		int x = (int)(Math.random()*100);
		if(level_AI==1)
		{
			if(x<87)
				current_power = 0;
			else if(x<90)
				current_power = 1;
			else if(x<93)
				current_power = 2;
			else if(x<96)
				current_power = 3;
			else if(x<98)
				current_power = 4;
			else if(x<100)
				current_power = 5;
		}
		else if(level_AI==2)
		{
			if(x<70)
				current_power = 0;
			else if(x<76)
				current_power = 1;
			else if(x<84)
				current_power = 2;
			else if(x<90)
				current_power = 3;
			else if(x<95)
				current_power = 4;
			else if(x<100)
				current_power = 5;
		}
		else if(level_AI ==3 )
		{
			if(x<50)
				current_power = 0;
			else if(x<73)
				current_power = 1;
			else if(x<82)
				current_power = 2;
			else if(x<87)
				current_power = 3;
			else if(x<96)
				current_power = 4;
			else if(x<100)
				current_power = 5;
		}
		//System.out.println("current_power: "+current_power);
	}

	public void approveTheseValues(double a,double b,double or){
		if(a<0){
			d1=0;
			d2=or;
		}
		else if(b>586){
			d2=586;
			d1=586-or;
		}
	}
	public void disable(){
		disable = true;
		d1 = 10000000;
		d2 = 10000000;
	}

}