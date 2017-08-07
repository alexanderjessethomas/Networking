
package clientturtlegraphics;

/**
 *Created By: John Bassler, Alex Thomas
 * CS460
 * Client for turtle graphics project 
 * Dr. Otte
 * 11/22/2016
 * This program is the gui client for a turtle graphics project.
 * It has four directional buttons that send datagrams if pressed
 * A textfield that is where desired length is entered 
 * A toggle button that says if the pen is down or not. 
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Client
{
	private static int movementDefault = 50;
	private static int degrees = 45;

	public static void main(String[] args)
	{
		
		JFrame frame = new JFrame();
		frame.setTitle("Turtle Graphics Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(6, 4));
		final JTextField movementText = new JTextField();
		final JButton movementButton = new JButton();
		final JTextField degreesText = new JTextField();
		final JButton degreesButton = new JButton();
		movementButton.setText("Movement length (in pixels): ");
		movementText.setText("50");

		//gets the desired movement length from the user
		movementButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				//TODO sets the distance currently in the JTextField
				try
				{
					movementDefault = Integer.parseInt(movementText.getText());
					sendString("distance = " + movementDefault);
				}
				catch(NumberFormatException e)
				{
					movementText.setText("50");
					movementDefault = 50;
					System.out.println("Formatting Error");
				}

			}
		}
		);
		
		degreesButton.setText("Set Angle");
		degreesText.setText("45");
		
		final JButton westButton = new JButton("West");
		//rotates the turtle west when the button is pressed by 45 degrees
		westButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				degrees = Integer.parseInt(degreesText.getText());
				sendString("w" + degrees);
			}
		}
		);
		JButton eastButton = new JButton("East");
		//rotates the turtle east when the button is pressed by 45 degrees
		eastButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				degrees = Integer.parseInt(degreesText.getText());
				sendString("e" + degrees);
			}
		}
		);
		final JButton northButton = new JButton("North");
		//moves the turtle north when the north button is pressed
		northButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				movementDefault = Integer.parseInt(movementText.getText());
				sendString("n" + movementDefault);
			}
		}
		);
		final JButton southButton = new JButton("South");
		//moves the turtle south when the south button is pressed
		southButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				movementDefault = Integer.parseInt(movementText.getText());
				sendString("s" + movementDefault);
			}
		}
		);
		final JButton penToggleButton = new JButton("Press to lift up pen");
		//toggles the pen on and off
		penToggleButton.addActionListener(
		new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				switch(penToggleButton.getText())
				{
					case "Press to lift up pen":
						penToggleButton.setText("Press to put down pen");
						sendString("pu");
						break;
					case "Press to put down pen":
						penToggleButton.setText("Press to lift up pen");
						sendString("pd");
						break;
					default:
						System.out.println(penToggleButton.getText());
						System.out.println("You need to look at pntgBTN code!");
						break;
				}
			}
		}
		);
                
                frame.add(new JLabel());
                frame.add(new JLabel());
                frame.add(new JLabel());
                frame.add(new JLabel());
                
		frame.add(new JLabel());
		frame.add(northButton);
		frame.add(new JLabel());
                frame.add(new JLabel());
              
		frame.add(westButton);
		frame.add(new JLabel());
		frame.add(eastButton);
                frame.add(new JLabel());
                
		frame.add(new JLabel());
		frame.add(southButton);
		frame.add(new JLabel());
                frame.add(new JLabel());
               
		frame.add(new JLabel());
		frame.add(new JLabel());
		frame.add(movementButton);
		frame.add(movementText);
                
		frame.add(new JLabel());
                frame.add(new JLabel());
                frame.add(penToggleButton);
                frame.add(new JLabel());
                
                //creates everything that is displayed on screen 
		frame.pack();
		frame.setVisible(true);

	}
        
	//creates a connection to the server, sends data, then closes connection
	static void sendString(String output)
	{
		// send user input as output
		try
		{
			String address = "127.0.0.1";
			int port = 62792;
			Socket clientSocket = new Socket(address, port);
			PrintWriter send = new PrintWriter(clientSocket.getOutputStream(), true);
			send.println(output);
			send.close();
			clientSocket.close();
		}
		catch(IOException e)
		{
			System.out.println("Connection error\n" + e);
		}
	}
}
