
package clientturtlegraphics;

/**
 *Created By: John Bassler, Alex Thomas
 * CS460
 * Client for turtle graphics project 
 * Dr. Otte
 * 11/22/2016
 * This program is the server for a turtle graphics project.
 * It displays a board with a turtle on it. 
 * takes input from the client and move turtle accordingly
 */
import ch.aplu.turtle.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.lang.NullPointerException;
public class Server {
	//private variables to be used in this class 
	private static Turtle ninja = new Turtle();
	private static int movementLength;
	private static int degree;
	private String address = "127.0.0.1";
	private int port = 62795;
	private static int count = 0;
	private static int showTurtle = 0;
        
    // connection handler to handle clients inside main 
    public static void main(String[] args)
	{
		connection_handler();
	}

	//multiple connections from multiple clients are handled here
	static void connection_handler()
	{
		try
		{
			ServerSocket server = new ServerSocket(62795);
			String str;
			while(true)
			{
				Socket client = server.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				str = reader.readLine();
				movementSwitch(str);
				reader.close();
				client.close();
			}
		}
		catch(IOException e)
		{
			System.out.println("connection error \n\n" + e);
		}
	}
	//using a switch to handle strings that come from the cleitn. 
	static void movementSwitch(String input)
	{
		switch(input.charAt(0))
		{
			case 'n':
				movementLength = getMovementLength(input);
				ninja.fd(movementLength);
				break;
			case 's':
				movementLength = getMovementLength(input);
				ninja.bk(movementLength);
				break;
			case 'e':
				degree = getDegrees(input);
				ninja.rt(degree);
				break;
			case 'w':
				degree = getDegrees(input);
				ninja.lt(degree);
				break;
			case 'p':
				penToggle();
				break;
			case 'a':
				changeDegrees(input);
				break;
			case 'd':
				changeMovementLength(input);
				break;
			default:
				System.out.println("error check movementSwitch.");
				break;
		}
	}
	//has the ability to change the degrees the turtle moves
	static void changeDegrees(String input)
	{
		degree = Integer.parseInt(input.substring("angle = ".length()));
	}
	//has the ability to change the movemnt length the turtle can move from the client 
	static void changeMovementLength(String input)
	{
		movementLength = Integer.parseInt(input.substring("distance = ".length()));
	}
	//controls if the turtle has the pen down or up on the board. 
	static void penToggle()
	{
		if(count % 2 == 0)
		{
			ninja.pu();
		}
		else
		{
			ninja.pd();
		}
		count++;
	}

	//gets the movement length 
	static int getMovementLength(String input)
	{
		return Integer.parseInt(input.substring(1));
	}
	
	// gets the degree's
	static int getDegrees(String input)
	{
		return Integer.parseInt(input.substring(1));
	}
}
