package Networking;

import java.io.*;
import java.lang.*;
import java.util.*;

public class network_main 
{
	public network_main ()
	{

	}

	public static void main(String[] args) 
	{
		System.out.println("Welcome to the chatroom:");
		System.out.println("Please enter one of the following options:");
		System.out.println("1. Start a chat session");
		System.out.println("2. Join a chat session");

		String choice = System.console().readLine("Enter your Choice: ");

		try
		{
			if(choice.equals("1"))
			{
				new Thread(new socket_handler(choice)).start();
			}
			else
			{
				String ip_addr = System.console().readLine("Enter IP Address: ");
				socket_handler a = new socket_handler(choice);
				new Thread(a).start();
				a.connect_to_user(ip_addr);	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}