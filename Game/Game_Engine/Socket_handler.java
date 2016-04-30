package Game_Engine;

import java.io.*;
import java.net.*;
import java.util.*;

public class Socket_handler extends Thread implements Runnable 
{
	public String my_ip_address;
	// IP Address of this peer

	public DatagramSocket socket = null;

	public HashMap<String,indiv_connection_handler> connect_list;
	//List of peers connected

	public Queue<String> message_queue;

	public String choice;

	public int users_joined = 0;

	public Timer timer;

	public connectivity_check c_check;

	public String getIp() throws SocketException 
	{

    	return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
            .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
            .filter(ip -> ip instanceof Inet4Address && ip.isSiteLocalAddress())
            .findFirst().orElseThrow(RuntimeException::new)
            .getHostAddress();
	}

	public Socket_handler(String input_choice) throws SocketException
	{
		this.socket = new DatagramSocket(9876);
		this.connect_list = new HashMap<String,indiv_connection_handler>();
		this.choice = input_choice;
		this.my_ip_address = this.getIp();
		this.message_queue = new LinkedList<String>();
	}

	public void run()
	{
		try
		{
			if (choice.equals("1"))
			{
		        this.connect_list.put(this.my_ip_address,new indiv_connection_handler(this.my_ip_address));

		        this.connect_list.get(this.my_ip_address).joining_order = this.users_joined;
		        this.users_joined++;
		        this.connect_list.get(this.my_ip_address).is_human = true;
		        this.update_pseudo_server();
			}

			// new Thread(new message_sender_thread(this)).start();

			this.timer = new Timer();

			this.c_check = new connectivity_check(this);

			this.timer.scheduleAtFixedRate(c_check,0,500);

			while(true)
			{
				byte[] incomingData = new byte[1024];

				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
	            	socket.receive(incomingPacket);
	            String response = new String(incomingPacket.getData(),incomingPacket.getOffset(),incomingPacket.getLength(),"UTF-8");
	            String[] decode = response.split(";");

	            if (decode[0].equals("Connection-Request"))
	            {
	                // Code to handle new user
	                this.new_user(decode[1]);

			    	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order();
			    	}

			    	System.out.println("Inside Connection-Request");

			    	this.print_hm();
	            }
	            else if (decode[0].equals("Message"))
	            {
	            	this.message_queue.add(response);
	            	this.print_q();
	            }
	            else if (decode[0].equals("New-User-Added"))
	            {
	            	System.out.println("Message Received:" + decode[1]);

	            	// Probably write a send response code here
	            	this.add_user(decode[1]);

	            	System.out.println("HashMap entries NUA");  
	            	for (String key: this.connect_list.keySet()) 
			    	{
						// this.connect_list.get(key).send_message(message);  
								
						System.out.println(key);
			    	}

			    	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order();
			    	}
	            }
	            else if (decode[0].equals("Handshake-Request"))
	            {
	            	System.out.println("Message Received:" + decode[0] + decode[1]);

	            	// Probably write a send response code here
	            	this.user_handshake(response);

	            	System.out.println("HashMap entries HR");  		
	            	for (String key: this.connect_list.keySet()) 
			    	{
						// this.connect_list.get(key).send_message(message);  
						
						System.out.println(key);
			    	}

			    	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order();
			    	}
	            }
	            else if (decode[0].equals("Connected-List")) //periodic update of connected peers list
	            {

	            	for (int i = 1; i< decode.length; i+=2)
					{
						if (this.connect_list.get(decode[i]) == null)
				        {
				        	this.connect_list.put(decode[i],new indiv_connection_handler(decode[i]));
				        	this.connect_list.get(decode[i]).joining_order = this.users_joined;
				        	this.connect_list.get(decode[i]).is_human = Boolean.parseBoolean(decode[i+1]);
				        	this.users_joined++;

				        	// Add to message queue
	        				this.message_queue.add("New-User-Added;"+decode[i]+"");
				        }
				        else
				        {
				        	// this.connect_list.get(decode[i]).is_human = Boolean.parseBoolean(decode[i+1]); //replaced true here
				        }
					}

			    	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order_ping();
			    	}
	            }
	            else if (decode[0].equals("Check-Connectivity"))
	            {
	            	// System.out.println("Message Received:" + decode[0]+" "+decode[1]);

	            	// Probably write a send response code here
	            	this.connect_list.get(decode[1]).send_message("Connection-verified;"+this.my_ip_address);
	            }
	            else if (decode[0].equals("Connection-verified"))
	            {
	            	// System.out.println("Message Received:" + decode[0]+" "+decode[1]);

	            	// Probably write a send response code here
	            	this.connect_list.get(decode[1]).received = true;
				}
	            else if (decode[0].equals("User-Disconnected"))
	            {
	            	System.out.println("Message Received:" + decode[0]+" "+decode[1]);

	            	// Probably write a send response code here
	            	this.connect_list.get(decode[1]).is_human = false;

	            	this.update_pseudo_server();

	            	this.message_queue.add(response);
			    	this.print_q();

	            	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order();
			    	}

	            	System.out.println("Inside User-Disconnected:");

	            	this.print_hm();
	            }
	            else if (decode[0].equals("User-Reconnected"))
	            {
	            	System.out.println("Message Received:" + decode[0]+" "+decode[1]);

	            	// Probably write a send response code here
	            	this.connect_list.get(decode[1]).is_human = true;
	            	// this.connect_list.get(decode[1]).received = true;

	            	this.update_pseudo_server();

			    	this.message_queue.add(response);
			    	this.print_q();

	            	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order();
			    	}

	            	System.out.println("Inside User-Reconnected:");

	            	this.print_hm();
	            }
	            else if (decode[0].equals("Joining-Order"))
	            {
	            	for(int i = 1; i<decode.length; i = i+2)
	            	{
	            		this.connect_list.get(decode[i]).joining_order = Integer.parseInt(decode[i+1]);
	            	}

	            	// System.out.println("Joining_Order:");

	            	// this.print_hm();
	            	this.message_queue.add(response);
	            	this.print_q();
	            }
	            else if (decode[0].equals("Joining-Order-Ping"))
	            {
	            	for(int i = 1; i<decode.length; i = i+2)
	            	{
	            		this.connect_list.get(decode[i]).joining_order = Integer.parseInt(decode[i+1]);
	            	}

	            	// System.out.println("Joining_Order:");

	            	// this.print_hm();
	            }
	            else
	            {
	            	this.message_queue.add(response);
	            	this.print_q();
	            }
	            // new Thread(new Responder(this.socket, packet)).start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//To print Hash Map
	public void print_hm()
	{
		System.out.println("HashMap status:");

    	for (String key: this.connect_list.keySet()) 
    	{
			System.out.println(key+": is_human: "+this.connect_list.get(key).is_human+": received: "+this.connect_list.get(key).received+": is_baap:"+this.connect_list.get(key).is_pseudo_server+": joining_order:"+this.connect_list.get(key).joining_order);
    	}
	}

	//To print queue
	public void print_q()
	{
		return;
   		//System.out.println(this.message_queue);
	}

	// To check for pseudo server
	public boolean is_pseudo_server()
	{
		try
		{
			if (this.connect_list.get(this.my_ip_address) != null)
				return this.connect_list.get(this.my_ip_address).is_pseudo_server;
			else
			{
				System.out.println("Abhi null hai");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception raised in is_baap");
			return false;
		}
	}

	public String my_ip_address()
	{
		try
		{
			return this.my_ip_address;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	// To connect to a new user through the network
	public void connect_to_user(String ip_address) throws Exception
	{
		if (this.connect_list.get(ip_address) == null)
        {
        	this.connect_list.put(ip_address,new indiv_connection_handler(ip_address));

        	this.connect_list.get(ip_address).joining_order = this.users_joined;
	        this.users_joined++;
	        this.connect_list.get(ip_address).is_human = true;
	        this.update_pseudo_server();
        }
        else
        {
        	this.connect_list.get(ip_address).is_human = true;
        }

        this.connect_list.get(ip_address).send_message("Connection-Request;"+my_ip_address+"");
	}

	public Queue<String> ret_q()
	{
		// System.out.println(this.message_queue);
		// return this.message_queue;

		Queue<String> retq = this.message_queue;
		this.message_queue = new LinkedList<String>();
		return retq;
	}

	// To be called on addition of a new user
	public void new_user(String ip_addr) throws Exception
	{
        System.out.println("New User: "+ip_addr+" joined.");

        if (this.connect_list.get(ip_addr) == null)
        {
        	this.connect_list.put(ip_addr,new indiv_connection_handler(ip_addr));

	        this.connect_list.get(ip_addr).joining_order = this.users_joined;
	        this.connect_list.get(ip_addr).is_human = true;
	        this.users_joined++;

	        // Add to message queue
	        this.message_queue.add("New-User-Added;"+ip_addr+"");
        }
        else
        {
        	this.connect_list.get(ip_addr).is_human = true;

        	// Add to message queue
	        this.message_queue.add("User-Reconnected;"+ip_addr+"");
        }

        this.update_pseudo_server();

        // this.send_message_to_all("New-User-Added;"+ip_addr+"");
        for (String key: this.connect_list.keySet()) 
    	{
			try
			{
				if (!key.equals(my_ip_address) && !key.equals(ip_addr))
					this.connect_list.get(key).send_message("New-User-Added;"+ip_addr+"");    		
			}
			catch(Exception e)
			{
				System.out.println(key+" not connected yet");
			}
    	}
        this.connect_list.get(ip_addr).send_message("Handshake-Request"+/*my_ip_address+";true"+*/this.get_ip_list()+"");
    }

    //To close the socket
    @Override
    public void interrupt()
    {
    	try
    	{
    		super.interrupt();  
    		this.c_check.cancel();
     		this.socket.close();
    		// this.socket.close();
    	}
    	catch (Exception e) {
    		System.out.println("Exception in close socket");
    	}
    }

    // To update the pseudo server on disconnection or reconnection
    public void update_pseudo_server()
    {
    	TreeMap t_map = new TreeMap(this.connect_list);

    	String low_key = (String)t_map.firstKey();
    	while(!this.connect_list.get(low_key).is_human)
	    {	
	    	System.out.println(low_key);

	    	t_map.remove(low_key);
	    	//TODO:Check for empty treemap here
	    	low_key = (String)t_map.firstKey();
	    }

	    	this.connect_list.get(low_key).is_pseudo_server = true;

    	for (String key: this.connect_list.keySet()) 
    	{
			if(!key.equals(low_key))
			{
				this.connect_list.get(key).is_pseudo_server = false;
			}	
    	}

    }

    // To send the joining order of peers to other peers
    public void send_joining_order() throws Exception
    {
    	String to_send = "Joining-Order";

    	for (String key: this.connect_list.keySet()) 
    	{
			to_send+=";"+key+";"+this.connect_list.get(key).joining_order+"";
    	}

    	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
    	{
    		this.message_queue.add(to_send);
    		this.print_q();
    	}    	

    	this.send_message_to_all(to_send);
    }

    public void send_joining_order_ping() throws Exception
    {
    	String to_send = "Joining-Order-Ping";

    	for (String key: this.connect_list.keySet()) 
    	{
			to_send+=";"+key+";"+this.connect_list.get(key).joining_order+"";
    	}

    	this.send_message_to_all(to_send);
    }

    public String get_ip_list()
    {
    	String ip_list = "";

    	for (String key: this.connect_list.keySet()) 
    	{
			ip_list = ip_list+";"+key+";"+this.connect_list.get(key).is_human;
    	}

    	return ip_list;
    }

    // To be called on adding a new user
    public void add_user(String ip_addr) throws Exception  
	{
        System.out.println("New User: "+ip_addr+" joined.");

        if (this.connect_list.get(ip_addr) == null)
        {
        	this.connect_list.put(ip_addr,new indiv_connection_handler(ip_addr));
        	this.connect_list.get(ip_addr).joining_order = this.users_joined;
	        this.connect_list.get(ip_addr).is_human = true;
	        this.users_joined++;

	        //Add to message queue
	        this.message_queue.add("User-Joined;"+ip_addr+"");
        }
        else
        {
        	this.connect_list.get(ip_addr).is_human = true;

        	//Add to message queue
        	this.message_queue.add("User-Reconnected;"+ip_addr+"");
        }

        this.update_pseudo_server();

        // this.send_message_to_all("New-User-Added;"+ip_addr+"");
        // this.connect_list.get(ip_addr).send_message("Handshake-Request;"+InetAddress.getLocalHost().getHostAddress()my_ip_address+"");
    }

    // To broadcast messages to all peers
    public void send_message_to_all(String message) throws Exception
    {
    	for (String key: this.connect_list.keySet()) 
    	{
			try
			{
				if (/*this.connect_list.get(key).is_human &&*/ !key.equals(my_ip_address))
					this.connect_list.get(key).send_message(message);    		
			}
			catch(Exception e)
			{
				System.out.println(key+" not connected yet");
			}
    	}
    }

    // To be called on connection confirmation from the user
    public void user_handshake(String resp) throws Exception
	{
        // System.out.println("New User: "+ip_addr+" joined.");

		String[] ip_addr = resp.split(";");

		for (int i = 1; i< ip_addr.length; i+=2)
		{
			if (this.connect_list.get(ip_addr[i]) == null)
	        {
	        	this.connect_list.put(ip_addr[i],new indiv_connection_handler(ip_addr[i]));
	        	this.connect_list.get(ip_addr[i]).joining_order = this.users_joined;
	        	this.connect_list.get(ip_addr[i]).is_human = Boolean.parseBoolean(ip_addr[i+1]);
	        	this.users_joined++;

	        	//Add to message queue
        		this.message_queue.add("User-Added;"+ip_addr[i]+"");
	        }
	        else
	        {
	        	if (this.connect_list.get(ip_addr[i]).is_human)
	        	{
	        		this.message_queue.add("User-Added;"+ip_addr[i]+"");	
	        	}
	        	else
	        	{
		        	if (Boolean.parseBoolean(ip_addr[i+1]))
		        	{
		        		this.connect_list.get(ip_addr[i]).is_human = Boolean.parseBoolean(ip_addr[i+1]); //replaced true here
		        		
		        		//Add to message queue
	        			this.message_queue.add("User-Reconnected;"+ip_addr[i]+"");
		        	}
		        	else
		        	{
		        		this.connect_list.get(ip_addr[i]).is_human = Boolean.parseBoolean(ip_addr[i+1]); //replaced true here
		        	}
	        	}
	        }
		}

		this.update_pseudo_server();
    }
	
}

class indiv_connection_handler
{
	public InetAddress IPAddress;
	public DatagramSocket socket = null;
	public boolean received = true;
	public boolean is_human = true;
	public boolean is_pseudo_server = false;
	public int joining_order = 0;

	public indiv_connection_handler (String ip_address) throws Exception
	{
		this.IPAddress = InetAddress.getByName(ip_address);
		this.socket = new DatagramSocket();
	}

	// Send message to the particular peer
	public void send_message(String message) throws Exception
	{
		byte[] data = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, this.IPAddress, 9876);
        this.socket.send(sendPacket);
	}
}

class message_sender_thread implements Runnable
{
	public Socket_handler sh;

	public message_sender_thread(Socket_handler s)
	{
		this.sh = s;
	}

	public void run()
	{
		try
		{	
			while (true)
			{
				String choice = System.console().readLine("Enter your Message: ");

				this.sh.send_message_to_all("Message;"+choice);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

// To check for disconnection and reconnection of peers
class connectivity_check extends TimerTask
{
	public Socket_handler sh;

	public connectivity_check(Socket_handler s)
	{
		this.sh = s;
	}

	public void run()
	{
		try
		{
			for (String key: this.sh.connect_list.keySet()) 
	    	{
				if (this.sh.connect_list.get(key).is_human && !this.sh.connect_list.get(key).received && !key.equals(this.sh.my_ip_address))
				{
					this.sh.connect_list.get(key).is_human = false;
					System.out.println("within timer function: User-Disconnected;"+key);

					//trial
					this.sh.update_pseudo_server();

					this.sh.message_queue.add("User-Disconnected;"+key);
					this.sh.print_q();

					// Print HM status inside
					this.sh.print_hm();

					this.sh.connect_list.get(key).received = false;
				}

				// Reconnection code here

				if (!this.sh.connect_list.get(key).is_human && this.sh.connect_list.get(key).received && !key.equals(this.sh.my_ip_address))
				{
					this.sh.connect_list.get(key).is_human = true;
					System.out.println("within timer function: User-Reconnected;"+key);

					//trial
					this.sh.update_pseudo_server();

					this.sh.message_queue.add("User-Reconnected;"+key);
					this.sh.print_q();

					// Print HM status inside
					this.sh.print_hm();

					this.sh.connect_list.get(key).received = false;
				}

				this.sh.connect_list.get(key).received = false;
	    	}

	    	this.sh.send_message_to_all("Connected-List"+this.sh.get_ip_list());
	    	this.sh.send_message_to_all("Check-Connectivity;"+this.sh.my_ip_address);
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
}