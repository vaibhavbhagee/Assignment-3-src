import java.io.*;
import java.net.*;
import java.util.*;

public class socket_handler implements Runnable 
{
	public String my_ip_address;

	public DatagramSocket socket = null;

	//Enter HashMap here

	public HashMap<String,indiv_connection_handler> connect_list;

	public String choice;

	public int users_joined = 0;

	public String getIp() throws SocketException 
	{

    	return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
            .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
            .filter(ip -> ip instanceof Inet4Address && ip.isSiteLocalAddress())
            .findFirst().orElseThrow(RuntimeException::new)
            .getHostAddress();
	}

	public socket_handler(String input_choice) throws SocketException
	{
		this.socket = new DatagramSocket(9876);
		this.connect_list = new HashMap<String,indiv_connection_handler>();
		this.choice = input_choice;
		// this.my_ip_address = "10.192.45.162";
		this.my_ip_address = this.getIp();
	}

	public void run()
	{
		try
		{
			if (choice.equals("2"))
			{
				String ip_address = System.console().readLine("Enter the IP Address: ");

				if (this.connect_list.get(ip_address) == null)
		        {
		        	this.connect_list.put(ip_address,new indiv_connection_handler(ip_address));

		        	this.connect_list.get(ip_address).joining_order = this.users_joined;
			        this.users_joined++;
			        this.update_pseudo_server();
		        }

		        this.connect_list.get(ip_address).send_message("Connection-Request;"+/*InetAddress.getLocalHost().getHostAddress()*/my_ip_address+"");
			}
			else if (choice.equals("1"))
			{
		        this.connect_list.put(this.my_ip_address,new indiv_connection_handler(this.my_ip_address));

		        this.connect_list.get(this.my_ip_address).joining_order = this.users_joined;
		        this.users_joined++;
		        this.update_pseudo_server();
			}

			new Thread(new message_sender_thread(this)).start();

			Timer timer = new Timer();

			timer.scheduleAtFixedRate(new connectivity_check(this),0,1000);

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

	                System.out.println("HashMap entries CR");  
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
	            else if (decode[0].equals("Message"))
	            {
	            	System.out.println("Message Received:" + decode[1]);

	            	// Probably write a send response code here
	            	// String choice = System.console.readLine("Enter your Message: ");
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

			    	// if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	// {
			    	// 	this.send_joining_order();
			    	// }
	            }
	            else if (decode[0].equals("Handshake-Request"))
	            {
	            	System.out.println("Message Received:" + decode[1]);

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

	     //        	System.out.println("HashMap status");

	     //        	for (String key: this.connect_list.keySet()) 
			   //  	{
						// System.out.println(key+": is_human: "+this.connect_list.get(key).is_human+": received: "+this.connect_list.get(key).received);
						// // if (this.connect_list.get(key).is_human && !key.equals(my_ip_address))
						// // 	this.connect_list.get(key).send_message(message);    		
			   //  	}
				}
	            else if (decode[0].equals("User-Disconnected"))
	            {
	            	System.out.println("Message Received:" + decode[0]+" "+decode[1]);

	            	// Probably write a send response code here
	            	this.connect_list.get(decode[1]).is_human = false;

	            	this.update_pseudo_server();

	            	if (this.connect_list.get(this.my_ip_address).is_pseudo_server)
			    	{
			    		this.send_joining_order();
			    	}

	     //        	System.out.println("HashMap status");

	     //        	for (String key: this.connect_list.keySet()) 
			   //  	{
						// System.out.println(key+": is_human: "+this.connect_list.get(key).is_human+": received: "+this.connect_list.get(key).received);
						// // if (this.connect_list.get(key).is_human && !key.equals(my_ip_address))
						// // 	this.connect_list.get(key).send_message(message);    		
			   //  	}
	            }
	            else if (decode[0].equals("Joining-Order"))
	            {
	            	for(int i = 1; i<decode.length; i = i+2)
	            	{
	            		this.connect_list.get(decode[i]).joining_order = Integer.parseInt(decode[i+1]);
	            	}

	            	System.out.println("HashMap status JO");

	            	for (String key: this.connect_list.keySet()) 
			    	{
						System.out.println(key+": is_human: "+this.connect_list.get(key).is_human+": received: "+this.connect_list.get(key).received+": is_baap:"+this.connect_list.get(key).is_pseudo_server+": joining_order:"+this.connect_list.get(key).joining_order);
						// if (this.connect_list.get(key).is_human && !key.equals(my_ip_address))
						// 	this.connect_list.get(key).send_message(message);    		
			    	}
	            }
	            // new Thread(new Responder(this.socket, packet)).start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void new_user(String ip_addr) throws Exception
	{
        System.out.println("New User: "+ip_addr+" joined.");

        if (this.connect_list.get(ip_addr) == null)
        {
        	this.connect_list.put(ip_addr,new indiv_connection_handler(ip_addr));
        }

        this.connect_list.get(ip_addr).joining_order = this.users_joined;
        this.users_joined++;
        this.update_pseudo_server();

        this.send_message_to_all("New-User-Added;"+ip_addr+"");
        this.connect_list.get(ip_addr).send_message("Handshake-Request;"+/*InetAddress.getLocalHost().getHostAddress()*/my_ip_address+this.get_ip_list()+"");
    }

    public void update_pseudo_server()
    {
    	TreeMap t_map = new TreeMap(this.connect_list);

    	String low_key = (String)t_map.firstKey();
    	while(!this.connect_list.get(low_key).is_human)
	    {	
	    	System.out.println(low_key);

	    	t_map.remove(low_key);
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

    public void send_joining_order() throws Exception
    {
    	String to_send = "Joining-Order";

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
			ip_list = ip_list+";"+key;
    	}

    	return ip_list;
    }

    public void add_user(String ip_addr) throws Exception  
	{
        System.out.println("New User: "+ip_addr+" joined.");

        if (this.connect_list.get(ip_addr) == null)
        {
        	this.connect_list.put(ip_addr,new indiv_connection_handler(ip_addr));
        }

        this.connect_list.get(ip_addr).joining_order = this.users_joined;
        this.users_joined++;
        this.update_pseudo_server();

        // this.send_message_to_all("New-User-Added;"+ip_addr+"");
        // this.connect_list.get(ip_addr).send_message("Handshake-Request;"+InetAddress.getLocalHost().getHostAddress()my_ip_address+"");
    }

    public void send_message_to_all(String message) throws Exception
    {
    	for (String key: this.connect_list.keySet()) 
    	{
			if (this.connect_list.get(key).is_human && !key.equals(my_ip_address))
				this.connect_list.get(key).send_message(message);    		
    	}
    }

    public void user_handshake(String resp) throws Exception
	{
        // System.out.println("New User: "+ip_addr+" joined.");

		String[] ip_addr = resp.split(";");

		for (int i = 1; i< ip_addr.length; i++)
		{
			if (this.connect_list.get(ip_addr[i]) == null)
	        {
	        	this.connect_list.put(ip_addr[i],new indiv_connection_handler(ip_addr[i]));
	        	this.connect_list.get(ip_addr[i]).joining_order = this.users_joined;
	        	this.users_joined++;
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

	public void send_message(String message) throws Exception
	{
		byte[] data = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, this.IPAddress, 9876);
        this.socket.send(sendPacket);
        // System.out.println("Message sent from client");
        // DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        // Socket.receive(incomingPacket);
        // String response = new String(incomingPacket.getData(),incomingPacket.getOffset(),incomingPacket.getLength(),"UTF-8");
        // System.out.println("Response from server:" + response);
	}
}

class message_sender_thread implements Runnable
{
	public socket_handler sh;

	public message_sender_thread(socket_handler s)
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

class connectivity_check extends TimerTask
{
	public socket_handler sh;

	public connectivity_check(socket_handler s)
	{
		this.sh = s;
	}

	public void run()
	{
		try
		{
			// System.out.println("HashMap status inside timer");

	  //   	for (String key: this.sh.connect_list.keySet()) 
	  //   	{
			// 	System.out.println(key+": is_human: "+this.sh.connect_list.get(key).is_human+": received: "+this.sh.connect_list.get(key).received);
			// 	// if (this.connect_list.get(key).is_human && !key.equals(my_ip_address))
			// 	// 	this.connect_list.get(key).send_message(message);    		
	  //   	}

			for (String key: this.sh.connect_list.keySet()) 
	    	{
				if (this.sh.connect_list.get(key).is_human && !this.sh.connect_list.get(key).received && !key.equals(this.sh.my_ip_address))
				{
					this.sh.connect_list.get(key).is_human = false;
					System.out.println("within timer function: User-Disconnected;"+key);

					//trial
					this.sh.update_pseudo_server();

					this.sh.send_message_to_all("User-Disconnected;"+key);

	            	if (this.sh.connect_list.get(this.sh.my_ip_address).is_pseudo_server)
			    	{
			    		this.sh.send_joining_order();
			    	}

					
				}

				this.sh.connect_list.get(key).received = false;
	    	}

	    	this.sh.send_message_to_all("Check-Connectivity;"+this.sh.my_ip_address);
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
}