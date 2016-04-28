package Game_Engine;
public class Paddle{
	int orientation;	// 1 for horizontal and 2 for vertical
	double d1;
	double d2;
	int playerID;
	int current_power;
	int paddle_speed;
	double delta;		// gap from the base

	public Paddle(int orientation, double d1, double d2, double delta, int playerID){
		this.orientation = orientation;
		this.d1 = d1;
		this.d2 = d2;
		this.delta = delta;
		this.current_power = 0;
		paddle_speed = 0;
		this.playerID = playerID;
		//System.out.println("PADDLE VALUES: "+d1+" "+d2);
	}

	public double mod(double a)
	{
		if(a>0) return a;
		else return (-a);
	}

	public String to_String(){
		return (d1+"#"+d2+"#"+current_power+"#"+paddle_speed+";");
	}

	public void from_String(String val, int difference){
		String[] s = val.split("#");
		// System.out.println("paddle");
		if(difference==0){
			d1 = Double.parseDouble(s[0]);
			d2 = Double.parseDouble(s[1]);
		}
		else if(difference==1){
			d2 = Var.height - Double.parseDouble(s[0]);
			d1 = Var.height - Double.parseDouble(s[1]);
		}else if(difference==2){
			d2 = Var.length - Double.parseDouble(s[0]);
			d1 = Var.length - Double.parseDouble(s[1]);
		}else if(difference==3){
			d1 = Double.parseDouble(s[0]);
			d2 = Double.parseDouble(s[1]);
		}
		// d1 = Double.parseDouble(s[0]);
		// d2 = Double.parseDouble(s[1]);
		current_power = Integer.parseInt(s[2]);
		paddle_speed = Integer.parseInt(s[3]);
	}

	public void movePaddle(double x, double y, int level_AI){
		double factor = 1;
		double center = (d1+d2)/2;
		if(orientation==1 && playerID==0){
			if(y<286)
			{
				if(center>x && mod(center-x)>10 ){
					d1 -= Var.speed*1.9*Var.level_AI[level_AI];
					d2 -= Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<x && mod(center-x)>10 ){
					d1 += Var.speed*1.9*Var.level_AI[level_AI];
					d2 += Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}

		}else if(orientation==1 && playerID==2){
			if(y>300)
			{
				if(center>x && mod(center-x)>10 ){
					d1 -= Var.speed*1.9*Var.level_AI[level_AI];
					d2 -= Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<x && mod(center-x)>10 ){
					d1 += Var.speed*1.9*Var.level_AI[level_AI];
					d2 += Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}
		}else if(orientation==2 && playerID==3){
			if(x>300)
			{
				if(center>y && mod(center-y)>10 ){
					d1 -= Var.speed*1.9*Var.level_AI[level_AI];
					d2 -= Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<y && mod(center-y)>10 ){
					d1 += Var.speed*1.9*Var.level_AI[level_AI];
					d2 += Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}
		}else if(orientation==2 && playerID==1){
			if(x<286)
			{
				if(center>y && mod(center-y)>10 ){
					d1 -= Var.speed*1.9*Var.level_AI[level_AI];
					d2 -= Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(-Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}else if(center<y && mod(center-y)>10 ){
					d1 += Var.speed*1.9*Var.level_AI[level_AI];
					d2 += Var.speed*1.9*Var.level_AI[level_AI];
					paddle_speed = (int)(Var.speed*1.9)*(int)Var.level_AI[level_AI];
				}
			}
		}else{
			paddle_speed = 0;
		}
		paddle_speed /=5;
		approveTheseValues(d1,d2,d2-d1);
	}

	public void approveTheseValues(double a,double b,double or)
	{
		if(a<0)
		{
			//System.out.println("too left" + playerID);
			d1=0;
			d2=or;
		}
		else if(b>586)
		{
			//System.out.println("too right" +playerID);
			d2=586;
			d1=586-or;
		}
	}


}