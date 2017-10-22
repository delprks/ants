//ANTS VERSION 1.0 WRITTEN BY DANIEL PARKS
//FOR THE DEGREE OF BSC COMPUTER SCIENCE
//UNDER SUPERVISION OF DR SEBASTIAN DANICIC

//MAY 2005, GOLDSMITHS COLLEGE, UNIVERSITY OF LONDON
//LONDON, UNITED KINGDOM

//NOTE: THIS CODE IS BEST RUN UNDER WINDOWS XP IN TextPad, WITH THE SCREEN 
//RESOLUTION OF AT LEAST 800x600.


import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import javax.swing.*;
import java.util.*;

public class Ants extends JComponent implements Runnable
{
	private Paint paint;

//**************************** THESE INTEGERS WILL BE LATER USED IN THE CODE
	int [ ] x,y,r,dx,dy,x2,y2;
	static int number=0;
	static int n;

//**************************** THESE TWO VECTORS ARE USED TO RECORD THE MOVEMENT OF
//**************************** ANTS ON SCREEN
	Vector nextX=new Vector();
	Vector nextY=new Vector();
	int i2=0;

//**************************** IMAGES OF TARGETS AND BACKGROUND
	private Image bkGround = Toolkit.getDefaultToolkit().getImage(getClass().getResource("bkground.jpg"));
	private Image food1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("food1.jpg"));
	private Image food2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("leaf1.jpg"));
	private Image ant1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ant1.jpg"));

static int numberOfAnts=80;
	private int dragFrX = 0;
       private int dragFrY = 0;

//**************************** THESE TWO ARE LATER USED IN THE MOUSE SECTION 
//OF CODE
	private boolean canDrag=false;
	private boolean canDrag2=false;
	static boolean [ ] start = new boolean[numberOfAnts];

//**************************** THESE TWO BOOLEANS ARE USED TO DETERMINE WHICH 
//**************************** EXPERIMENT IS IN PROGRESS
	static boolean experimentOne=false;
	static boolean experimentTwo=false;

	public static int pointCount=0;
	public static int points[]=new int [1000];

//**************************** FIRST POSITIONS OF TARGETS AND POSITION OF 
//HOME/NEST
//*************** 1ST TARGET
	static int target1X=10;
	static int target1Y=300;

//*************** 2ND TARGET
	static int target2X=140;
	static int target2Y=150;

//*************** HOME/NEST
	static int homeX=250;
	static int homeY=510;

//**************************** END OF TARGETS
	Ant[] t = new Ant[numberOfAnts];

	boolean  [][] occupied=new boolean[600][600];

  	public Ants(int Height,int Width)
  	{
		for (int i=0;i<numberOfAnts;i++)start[i]=false;
		for (int i=0;i<600;i++)
		for (int j=0;j<600;j++)
		occupied[i][j]=false;

		for (int i=0;i<numberOfAnts;i++)
		{
//**************************** POSITION OF THE HOME/NEST, WHICH ANTS START 
//**************************** THEIR MOVEMENT FROM
			int x=homeX;
			int y=homeY;

			occupied[x][y]=false;

//**************************** THE ANT
			t[i]= new Ant(i, x, y,(int)Math.round(Math.random()*15),(int)Math.round(Math.random()*15),5,600,600,false);
            t[i].start();
			addMouseListener(new mousey());
			addMouseMotionListener(new mousey2());
		}

		Thread t = new Thread(this);
   		t.start(  );
        }

    public void run(  )
	{
    	try
		{
      		while (true)
      		{
        		Thread.sleep(50);

//**************************** DRAW THE ANTS
				repaint();
     		}
    	}
    	catch (InterruptedException ie) {}
  	}

  	public void paint(Graphics g)
	{
    	Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//**************************** BACKGROUND IMAGE
		g2.drawImage(bkGround, 0, 0, null);
//**************************** END OF BACKGROUND

//**************************** RIGHT-HAND SIDE MENUES AND EXPLANATIONS
		g2.setColor(Color.black);
		g2.fillRect (605, 10, 157, 20);
		g2.setColor(Color.white);
		g2.drawString("Ants Version 1.0", 640, 25);
		g2.setColor(Color.red);
		g2.fillRect (605, 30, 157, 20);
		g2.setColor(Color.black);
		g2.drawString("By Daniel Parks", 632, 45);
		g2.drawString("Please choose one of the", 605, 80);
		g2.drawString("following experiments:", 605, 93);

//**************************** START OF EXPERIMENT #1 MENUE
		g2.setColor(Color.red);
		g2.fillRect (605, 105, 157, 20);
		g2.setColor(Color.black);
		g2.drawString("Experiment #1", 642, 120);
		g2.setColor(Color.gray);
		g2.fillRect (605, 125, 157, 193);
		g2.setColor(Color.black);
		g2.drawString("In this experiment, ants", 610, 147);
		g2.drawString("move randomly on the", 610, 160);
		g2.drawString("screen until they find the", 610, 173);
		g2.drawString("targets and then they go ", 610, 186);
		g2.drawString("straight back to their nest.", 610, 199);
		g2.drawString("Click on the start button", 610, 225);
		g2.drawString("to start the experiment.", 610, 238);
		g2.drawString("When it starts, you can", 610, 251);
		g2.drawString("click again on the button to", 610, 264);
		g2.drawString("add to the number of ants.", 610, 282);
		g2.setColor(Color.white);
		g2.fillRect (607, 290, 152, 26);
		g2.setColor(Color.black);
		g2.fillRect (610, 293, 146, 20);
		g2.setColor(Color.white);
		g2.drawString("Click here to start E. #1", 620, 307);
//**************************** END OF EXPERIMENT #1 MENUE

//**************************** START OF EXPERIMENT #2 MENUE
		g2.setColor(Color.red);
		g2.fillRect (605, 323, 157, 20);
		g2.setColor(Color.black);
		g2.drawString("Experiment #2", 642, 338);
		g2.setColor(Color.gray);
		g2.fillRect (605, 343, 157, 165);
		g2.setColor(Color.black);
		g2.drawString("After the start of this", 610, 365);
		g2.drawString("experiment, one ant leaves", 610, 378);
		g2.drawString("the nest, leaving behind a", 610, 391);
		g2.drawString("trail of pheromone.", 610, 404);
		g2.drawString("When the leading ant finds", 610, 420);
		g2.drawString("one of targets, another ant", 610, 433);
		g2.drawString("leaves the nest and follows", 610, 446);
		g2.drawString("the pheromone trail to the", 610, 459);
		g2.drawString("target.(ONLY CLICK ONCE)", 610, 472);
		g2.setColor(Color.white);
		g2.fillRect (607, 480, 152, 26);
		g2.setColor(Color.black);
		g2.fillRect (610, 483, 146, 20);
		g2.setColor(Color.white);
		g2.drawString("Click here to start E. #2", 620, 497);
//**************************** END OF EXPERIMENT #2 MENUE

//**************************** THE POSITIONS OF TARGETS ON SCREEN
		g2.setColor(Color.gray);
		g2.fillRect (607, 515, 152, 52);
		g2.setColor(Color.white);
		g2.fillRect (610, 518, 146, 45);
		g2.setColor(Color.black);
		g2.drawString("Target #1: ", 628, 535);
		g2.drawString("Target #2: ", 628, 555);
//**************************** END OF POSITIONS OF TARGETS

//**************************** THE EXIT BUTTON
		g2.setColor(Color.gray);
		g2.fillRect (607, 570, 152, 26);
		g2.setColor(Color.black);
		g2.fillRect (610, 573, 146, 20);
		g2.setColor(Color.white);
		g2.drawString("EXIT", 669, 587);
//**************************** END OF THE EXIT BUTTON

//**************************** END OF MENUES


//**************************** TARGET #1
		g2.drawImage(food1, target1X, target1Y, null);
		g2.setColor(Color.blue);
		g2.drawString("Target #1",target1X+25,target1Y+50);
		g2.setColor(Color.black);
		g2.drawString(target1X+", "+target1Y, 690, 535);

//**************************** END OF TARGET #1

//**************************** TARGET #2
		g2.drawImage(food2, target2X, target2Y, null);
		g2.setColor(Color.blue);
		g2.drawString("Target #2",target2X+20,target2Y+65);
		g2.setColor(Color.black);
		g2.drawString(target2X+", "+target2Y, 690, 555);

//**************************** END OF TARGET #2

//**************************** DRAW THE ANT
		for (int i=0;i<numberOfAnts;i++)
		{
			g2.setColor(Color.red);
			g2.drawString("x="+t[i].x , t[i].x+10, t[i].y+10);
			g2.drawString("y="+t[i].y , t[i].x+10, t[i].y+20);
			g2.drawImage(ant1, t[i].x, t[i].y, null);
		}

//**************************** END OF ANT
  }

  public static void main(String[] args)
  {

    JFrame f = new JFrame("Ants");

    f.addWindowListener(new WindowAdapter()
    { public void windowClosing(WindowEvent we) { System.exit(0); }
    });

    Container c = f.getContentPane(  );
    c.setLayout(new BorderLayout(  ));
    c.add(new Ants(600,600));

//**************************** SIZE OF SCREEN
    f.setSize(777, 636);
    f.setLocation(100, 100);
    f.setVisible(true);
    JOptionPane.showMessageDialog(null, "Welcome to Ants v.1.0!\n\nThis program has                                  
    been written by\nDaniel Parks for the degree of\nBSc (Honours) in Computer
    Science.\n\n Please press the OK bottun to continue.");

JOptionPane.showMessageDialog(null, "In this program, two choices based on the behavior of ants in the \nnature, are available:\n\nIn option one: ants move randomly on the screen until they find their \ntarget, and then they go back to their nest.\n\nIn option two: one ant leaves the nest in search of food, leaving behind\na trail of pheromone. When it finally finds the food, it stays there\nand a second ant follows its trail and finds the food.\n\nNote: This program is best run in Windows XP, with the\nscreen resolution of at least 800x600 (recommended resolution: 1024x768)\n\nNote: It is possible to move the targets on screen using the mouse\nat any time, but it is recommended to only move them before the start of\nexperiments. Also, it is only possible to run one of the experiments at any time, and\nto start another experience, you should exit the program and start it again. \n\nPlease press the OK button to start one of these experiments.\n");
}

class Ant extends Thread
{
	private int xPos, yPos;
	public int x,x2;
	public int y,y2;
	int dx;
	int dy;
	public int r;
	int height;
	int width;
	int me;
	boolean foundTarget=false;

//**************************** HERE EACH ANT IS A SEPERATE THREAD
Ant(int m,int initx,int inity,int dx,int dy,int radius,int height, int width, boolean fTarget)
	{
//**************************** "me" IS USED TO IDENTIFY EACH ANT BY A UNIQUE NUMBER
		me=m;

//**************************** STARTING POINT
		this.x=initx;
		this.y=inity;

//**************************** HOW MUCH TO MOVE IN EACH STEP
		this.dx=dx;
		this.dy=dy;

//**************************** RADIUS OF ANT
		this.r=radius;

//**************************** SIZE OF SQUARE IN WHICH ANT IS BOUNCING
		this.height=height;
		this.width=width;

//**************************** foundTarget WILL BE TRUE IF AN ANT FINDS ONE OF TARGETS
		foundTarget=fTarget;
	}

	boolean inRange(int x)
	{
		return x>=0 &&x<599;
	}

  	public void run()
	{
    	while(true)
		{

//**************************** IF ONE OF THE EXPERIMENTS HAS STARTED..
		if (start[me])
	{
			i2++;
//**************************** RECORD THE MOVEMENT OF ANT IN A VECTOR:
			nextX.addElement(t[0].x);
			nextY.addElement(t[0].y);
//**************************** BOUNCE THE ANT IF IT HAS HIT AN EDGE
			if ((x - r + dx < 0) || (x + r + dx > width)) dx = -dx;
      		if ((y - r + dy < 0) || (y + r + dy > height)) dy = -dy;
//**************************** IF ANTS FIND THE TARGETS:
			if (((x >= target1X && x<= target1X+100) && (y >= target1Y && y <= target1Y+36)) || ((x >= target2X && x<= target2X+100) && (y >= target2Y && y <= target2Y+40)))
			{
//**************************** IF THE USER HAS CHOSEN THE SECOND EXPERIMENT, THIS 
//**************************** PROCESS SHOULD BE
//**************************** FOLLOWED SO THE SECOND ANT FOLLOW THE PATH OF THE FIRST 
//**************************** ANT:
				if (experimentTwo)
				{
					nextX.insertElementAt(250, 0);
					nextY.insertElementAt(510, 0);
					for (int ii=0; !nextX.isEmpty(); ii++)
					{
						t[me+1].x=new Integer(nextX.elementAt(ii).toString());
						t[me+1].y=new Integer(nextY.elementAt(ii).toString());
						try { Thread.sleep(60); } catch (InterruptedException e) { ; }
					}
					dx=0;
					dy=0;
				}

//**************************** THIS PART IS USED IN THE FIRST EXPERIMENT:
//**************************** THE SCREEN HAS BEEN DIVIDED INTO 20 SECTIONS,
//**************************** AND DEPENDING ON WHICH SECTION OF THE SCREEN AN ANT HAS
//**************************** FOUND ONE OF THE TARGETS, IT WILL GO TO ITS NEST FROM 
//**************************** THIS SECTION.

//**************************** FIRST HALF OF THE SCREEN:
				if (x>=250 && x<=340) n=0; else
				if (x>=225 && x<=250) n=1; else
				if (x>=200 && x<=225) n=2; else
				if (x>=175 && x<=200) n=3; else
				if (x>=150 && x<=175) n=4; else
				if (x>=125 && x<=150) n=5; else
				if (x>=100 && x<=125) n=6; else
				if (x>=75 && x<=100) n=7; else
				if (x>=50 && x<=75) n=8; else
				if (x>=25 && x<=50) n=9; else
				if (x>=0 && x<=25) n=10; else

//**************************** SECOND HALF OF THE SCREEN:
				if (x>=340 && x<=365) n=-1; else
				if (x>=365 && x<=390) n=-2; else
				if (x>=390 && x<=415) n=-3; else
				if (x>=415 && x<=440) n=-4; else
				if (x>=440 && x<=465) n=-5; else
				if (x>=465 && x<=490) n=-6; else
				if (x>=490 && x<=515) n=-7; else
				if (x>=515 && x<=540) n=-8; else
				if (x>=540 && x<=600) n=-9;

				dx=n;
				dy=10;

//**************************** AN ANT HAS FOUND ONE OF THE TARGETS, SO foundTarget IS 
//**************************** SET TO TRUE:
				t[me].foundTarget=true;
				}

//**************************** THE FOLLOWING SECTION IS USED TO STOP THE MOVEMENT OF 
//**************************** ANTS (IN EXPERIMENT ONE) AFTER THEY'VE FOUND THE FOOD 
//**************************** AND HAVE RETURNED TO THEIR NEST.
				if (t[me].foundTarget && ((x >= homeX && x<= homeX+100) && (y >= homeY && y <= homeY+50)))
				{
					t[me].dx=0;
					t[me].dy=0;
				}

//**************************** IT IS USED TO MOVE ANTS A LITTLE BIT:
			 {x += dx;  y += dy;}

		}

//**************************** NOW PAUSE BEFORE DRAWING THE ANT AGAIN.
		try { Thread.sleep(150); } catch (InterruptedException e) { ; }
    	}
  	}
}
//**************************** END OF ANT

class mousey2 extends MouseMotionAdapter
{

//**************************** mouseDragged. IT IS USED SO THAT THE USER CAN MOVE THE
//**************************** TARGETS ON SCREEN USING THE MOUSE.
	public void mouseDragged(MouseEvent e)
		{
	        if (canDrag)
	        {
//**************************** POSITION OF FIRST TARGET:
	            target1X = e.getX() - dragFrX;
	            target1Y = e.getY() - dragFrY;
	          	repaint();
			}

			if (canDrag2) {
//**************************** POSITION OF SECOND TARGET
				target2X = e.getX() - dragFrX;
	            target2Y = e.getY() - dragFrY;
	            repaint();
	        }
    	}

//**************************** END mouseDragged
}

class mousey extends MouseAdapter
{
	int numberOfMovingAnts=0;

//**************************** mousePressed IS USED TO DETERMINE WHICH PART OF THE 
//**************************** SCREEN THE USER HAS PRESSED THE LEFT-BUTTON OF MOUSE 
//**************************** AND TAKE THE APPROPERIATE ACTION.

	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
	    int y = e.getY();

//**************************** 1ST TARGET
	if ((x >= target1X && x<= target1X+100) && (y >= target1Y && y <= target1Y+36))
	   {
	       canDrag = true;
	       dragFrX = x - target1X;  // how far from left
	       dragFrY = y - target1Y;  // how far from top

	    } else {canDrag=false;}

//**************************** 2ND TARGET
	if ((x >= target2X && x<= target2X+100) && (y >= target2Y && y <= target2Y+40))
	   {
		   canDrag2 = true;
		   dragFrX = x - target2X;  // how far from left
           dragFrY = y - target2Y;  // how far from top
		} else { canDrag2 = false; }

//**************************** HOME. (NOTE: IN THE FIRST EXPERIMENT, THE USER CAN ALSO
//**************************** CLICK ON THE NEST TO ADD TO THE NUMBER OF ANTS.)

	    if ((x >= homeX && x<= homeX+100) && (y >= homeY && y <= homeY+50))
	    {
		  if (experimentOne==true)
		  {start[numberOfMovingAnts] = true;numberOfMovingAnts++;}
	    }

//**************************** THE EXIT BUTTON
	    if ((x >= 610 && x<= 756) && (y >= 573 && y <= 593))
	    {
			System.exit(0);
	    }

//**************************** EXPERIMENT #1 BUTTON
		if (((x >= 610 && x<= 756) && (y >= 293 && y <= 313)) && experimentTwo==false)
		{
			start[numberOfMovingAnts] = true; numberOfMovingAnts++;
			experimentOne=true;
		}

//**************************** EXPERIMENT #2 BUTTON
		if (((x >= 610 && x<= 756) && (y >= 483 && y <= 503))  && experimentOne==false )
		{
			start[numberOfMovingAnts] = true; numberOfMovingAnts=1;
			experimentTwo=true;
		}
    }

//**************************** END OF mousePressed

//**************************** mouseExited
    public void mouseExited(MouseEvent e)
    {
        canDrag = false;
        canDrag2 = false;
    }

//**************************** END OF mouseExited

  }
}

