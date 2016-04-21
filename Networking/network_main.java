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
			new Thread(new socket_handler(choice)).start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}