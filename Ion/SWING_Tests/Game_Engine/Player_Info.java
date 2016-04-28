package Game_Engine;
public class Player_Info{
	public String name;
	public String ip;
	public int joining_order;
	public Player_Info(String ip){
		this.name = null;
		this.ip = ip;
		joining_order = -100;
	}
}