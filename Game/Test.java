import Game_Engine.*;
import java.util.*;

class Test{
	Board b;
	DataForEngine d;
	Timer t;
	public Test(){
		d = new DataForEngine();
		b = new Board(0,0,"bancho",1,2);
		b.setParams(500,500);
		b.acceptIP(System.console().readLine("Enter IP: "));
		b.setGameMode(2);
		t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				b.update(d);
			}
		},0,500);
	}
	public static void main(String[] args) {
		new Test();
	}
}