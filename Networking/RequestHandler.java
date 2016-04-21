public class RequestHandler implements Runnable
{
	private HashMap<String,ConnectionObject> peerList; //List of peers connected

	private PriorityQueue<String> messageQueue; //Queue of received messages

	private is_pseudo_server; //Returns if the client is a pseudo server or not

	public RequestHandler()
	{
		this.peerList = new HashMap<String,ConnectionObject>();
		this.messageQueue = new PriorityQueue<String>();
		this.is_pseudo_server = false;
	} 

	/**
	 * Method to handle connections with peers
	 */
	public void run()
	{
		/**
		 * Function to check for connection requests from peers 
		 * and start a new thread to handle each connection.
		 * If the person already exists and got disconnected then
		 * restart the thread
		 */
	}

	public boolean is_pseudo_server()
	{
		return this.is_pseudo_server;
	}

	public void update_is_pseudo_server()
	{
		/**
		 * Function updates the pseudo server on connection or disconnection
		 * of a client from the game
		 */
	}

	public void broadcast(String message)
	{
		/**
		 * Broadcast the message to all the connected clients
		 */
	}

	public void update_player_type()
	{
		/**
		 * Function to update the type (Human/AI) upon disconnection
		 * of a client from the game
		 */
	}

	public PriorityQueue<String> get_all_messages()
	{
		/**
		 * Returns the queue of messages and reinstantiates it
		 */
	}

	public void ping_all_clients()
	{
		/**
		 * Function to ping all the clients periodically
		 * in order to check if someone has disconnected
		 */
	}
}

public class ConnectionObject
{
	private ConnectionHandler this_thread; // The thread object which handles this connection

	private Date timestamp; // Timestamp of the last received message

	private int player_index; // Index of the player in the game

	public boolean is_human;

	public ConnectionObject()
	{
		this.this_thread = new ConnectionHandler();
		this.timestamp = new Date();
		this.player_index = 0;
		this.is_human = false;
	}

	public ConnectionHandler getThread()
	{
		return this.this_thread;
	}

	public Date getTimestamp()
	{
		return this.timestamp;
	}

	public int getPlayerIndex()
	{
		return this.player_index;
	}

	public void setThread(ConnectionHandler newThread)
	{
		this.this_thread = newThread;
	}

	public void setTimestamp()
	{
		this.timestamp = new Date();
	}

	public void setPlayerIndex(int p_index)
	{
		this.player_index = p_index;
	}
}

public class ConnectionHandler implements Runnable
{
	private String thread_name; 
	// Name of the thread

	private boolean is_sender; 
	// Flag to identify the thread as sender or receiver

	private RequestHandler parent; 
	// Parent master Request Handler thread

	private DatagramSocket socket = null; 
	// Socket to be connected

	private InetAddress ip_addr; 
	// IP address if required

	public ConnectionHandler(String name, boolean is_sender, RequestHandler parentThread)
	{
		this.thread_name = name;
		this.is_sender = is_sender;
		this.parent = parentThread;
		this.socket = new DatagramSocket();
	}

	public void run()
	{
		/**
		 * Overloaded run method of the thread
		 */
	}

	public void connect_as_client(String ip_address)
	{
		/**
		 * Function to connect to the given ip address
		 * over a specified port through sockets to 
		 * interchange data
		 */
	}

	public void handleMessages()
	{
		/**
		 * Based on is_sender this function will behave
		 * in a different manner as to send data and wait
		 * for response or send response to incoming packet
		 */
	}

	public void sendMessage(String message)
	{
		/**
		 * Function to send message to the connected 
		 * peer
		 */
	}

	public void send_dummy_message()
	{
		/**
		 * Function to periodically send
		 * dummy messages to the peer to
		 * check for connection losses
		 */
	}
}