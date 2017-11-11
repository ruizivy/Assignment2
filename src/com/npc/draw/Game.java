package com.npc.draw;
import java.io.*;
import java.util.*;

import javax.swing.*;
import com.sun.glass.events.KeyEvent;
import java.awt.*;

public class Game extends JPanel implements Runnable{

	public static int width  = 300;
	public static int height = width/16 * 9;
	public static int scale = 3;
	public static int formWidth = 880;
	public static int formHeight = 460;
	public static int formX = 0;
	public static int formY = 200;
	
	public boolean isRunning = true;
	int speedX = 1 ; 
	int speedY = 0;
	public Thread thread;
	public static long counter = 0;
	public static long score = 0;
	public static long frameCounter = 0;
	public static long restart = 40;
	private static int positionX = formX;
	private static int positionY = formY;
	public static int centerX = 0;
	public static int centerY =0;
	
	public static Random rnd = new Random();
	private static  int enemyPositionX = rnd.nextInt(100);
	private static int enemyPositionY = rnd.nextInt(100);
	private String dir = "D";
	
	public static Rectangle r1 = new Rectangle();
	public static Rectangle r2 = new Rectangle();
	
	
	public JFrame frame;
	public Keyboard keys;
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(isRunning)
			{
				Thread.sleep(10);
				Update();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public Game(){
		Dimension size = new Dimension(width * scale , height * scale);
		this.setPreferredSize(size);
		frame = new JFrame();
		keys = Keyboard.getInstance();
		frame.addKeyListener(keys);
	}
	public void Start()
	{
		isRunning = true;
		thread = new Thread(this, "");
		thread.start();		
	}
	public void Stop()
	{
		isRunning = false;
		try
		{
			thread.join();
		}catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		render(g);
	}
	public void Update()
	{
		repaint();
		switch(dir)
		{
		case "W": positionY -= 1; break;
		case "S": positionY += 1; break;
		case "D": positionX += 1; break;
		case "A": positionX -= 1; break;
		}
	}
	public void render(Graphics g)
	{	
		if(counter++ % 100/2 == 0){
			frameCounter++;
		}
		if(intersect())
		{
			score++;
		}
		UpdatePosition();
		g.setColor(Color.WHITE);
		g.fill3DRect(10, 10, formWidth, formHeight, false);
		g.setColor(Color.BLACK);
		g.drawString("Running Count : " + frameCounter, 10, 20);
		g.drawString("Score : " + score , 10 , 40);
		g.setColor(Color.BLUE);
		g.fillRect(positionX, positionY, 10,10);
		g.setColor(Color.RED); 
		g.fillRect(enemyPositionX, enemyPositionY, 10, 10);
		
		if(borderIntersects()) {
			this.Stop();
		}
		
	}
	public void render1(Graphics g)
	{
		g.fillRect(enemyPositionX, enemyPositionY, 10, 10);
		g.setColor(Color.RED);
	}
	private void UpdatePosition()
	{
		if(keys.isDown(KeyEvent.VK_D))
		{	
			positionX += 1;	
			dir = "D";
		}
		else if(keys.isDown(KeyEvent.VK_A))
		{
			positionX -= 1;
			dir = "A";
		}
		else if(keys.isDown(KeyEvent.VK_W))
		{
			positionY -= 1;
			dir = "W";
		}
		else if(keys.isDown(KeyEvent.VK_S))
		{
			positionY += 1;
			dir = "S";
		}
	}
	public static boolean intersect() {
		r1.setBounds(positionX, positionY, 10, 10);
		r2.setBounds(enemyPositionX, enemyPositionY, 10, 10);
		if(r1.intersects(r2)) {
			enemyPositionX = rnd.nextInt(formWidth-20);
			enemyPositionY = rnd.nextInt(formHeight-20)+formY;
			return true;
		}
		return false;
	}
	
	public boolean borderIntersects() {
		if(positionX + 20 > formX + formWidth) {
			positionX = 20;
			
			return true;
		}
		if(positionX < formX ) {
			positionX = 20;
			
			return true;
		}
		if(positionY + 20 > formY + formHeight) {
			positionY = 20;
		
			return true;
		}
		if(positionY < formY) {
			positionY = 20;
			
			return true;
		}
		return false;
	}

}
