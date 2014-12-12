/*
*		ITC01 Java 2D/3D
*	  Home task on Java 2D
* Draw Animated logo of the University of Aizu
*@author Tetsushi Haresaku 
*@varsion 1.0 
*/
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Univ extends JFrame
{
    private Board bord1=new Board();

    public static void main(String[] argv){
	new Univ();
    }
    public Univ(){
	add(bord1);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setTitle("University of Aizu Logo");
        setVisible(true);
    }


}

class Board extends JPanel implements Runnable, ActionListener{

    private Thread animator;
    private double theta=0;//Degree of Rotate text
	private double fps = 30.0;//Frame Per Second
    private double t =0.0;//Degree of Rotate Circle
    private Boolean rotationToggle=false, running=false;
    private Color defaultColor = new Color(65,158,158);//DefaultColor
    private JButton toggleButton, startStopButton;
	private JButton fpsPlus, fpsMinus, colorChooseButton;
	private JLabel fpsLabel = new JLabel((new Double(fps)).toString());

    //Constructor
	public Board(){
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
		toggleButton = new JButton("Invert Rotation");//Invert Rotaion Button
		fpsPlus = new JButton("FPS+");//Double Frame Per Second Button
		fpsMinus = new JButton("FPS-");//Half Frame Per Second Button
		startStopButton = new JButton("Animation Start");//Start and Stop Animation Button
		colorChooseButton = new JButton("Color Choose");//Color Chooser Button
		
		//set Action Event
		startStopButton.addActionListener(this);
		fpsPlus.addActionListener(this);
		fpsMinus.addActionListener(this);
		toggleButton.addActionListener(this);
		colorChooseButton.addActionListener(this);
		
		add(startStopButton);
		add(fpsMinus);
		add(fpsLabel);
		add(fpsPlus);
		add(colorChooseButton);
		add(toggleButton);
    }

    //Button Events
    public void actionPerformed(ActionEvent e){
	Object targetObj = e.getSource();

		if(targetObj == toggleButton){
			if(rotationToggle==false)rotationToggle = true;
			else rotationToggle = false;
		}
		else if (targetObj == fpsPlus) {
			fps*=2;
			fpsLabel.setText((new Double(fps)).toString());
		}
		else if(targetObj == fpsMinus){
			fps/=2;	
			fpsLabel.setText((new Double(fps)).toString());
		}
		else if(targetObj == startStopButton){
				if (running == false) {
					startStopButton.setText("Animation Stop");
					running = true;
					animator = new Thread(this);
					animator.start();
				}
			else {
				startStopButton.setText("Animation Start");
				running = false;
			}
		}
		else if(targetObj == colorChooseButton){
			JColorChooser colorChooser = new JColorChooser();
			defaultColor = colorChooser.showDialog(this, "Choose Logo Color", defaultColor);
			if (running==false) {
				repaint();
			}
		}
		
		
    }


	
	//Draw logo of University of Aizu
    public void paint(Graphics g)
    {
	super.paint(g);

	Graphics2D g2 =  (Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setPaint(defaultColor);

	Ellipse2D outerCircle = new Ellipse2D.Double(50.0,50.0,400.0,400.0);
	Ellipse2D innerCircle = new Ellipse2D.Double(50.0+60*Math.cos(Math.toRadians(45)),50.0+60*Math.sin(Math.toRadians(45)),320.0,320.0);
	Dimension d = this.getSize();
	AffineTransform baseAffine =  new AffineTransform();
	baseAffine.setToRotation(t,250 ,250);
	g2.setTransform(baseAffine);
	g2.draw(outerCircle);
	g2.draw(innerCircle);

	//Draw any Lines
	GeneralPath path = new GeneralPath();
	path.moveTo(146f,165f);
	double x = 146+40*Math.cos(Math.toRadians(-30));
	double y = 165+40*Math.sin(Math.toRadians(-30));
	path.lineTo(x,y);
	x+=40*Math.cos(Math.toRadians(30));
	y+=40*Math.sin(Math.toRadians(30));
	path.lineTo(x,y);

	x+=25*Math.cos(Math.toRadians(-25));
	y+=25*Math.sin(Math.toRadians(-25));
	path.lineTo(x,y);

	x+=25*Math.cos(Math.toRadians(25));
	y+=25*Math.sin(Math.toRadians(25));
	path.lineTo(x,y);

	x+=20*Math.cos(Math.toRadians(-25));
	y+=20*Math.sin(Math.toRadians(-25));
	path.lineTo(x,y);

	x+=65*Math.cos(Math.toRadians(25));
	y+=55*Math.sin(Math.toRadians(25));
	path.lineTo(x,y);

	g2.setStroke(new BasicStroke(10));
	g2.draw(path);

	path.moveTo(214.0f, 308.0f);
	path.lineTo(392.0f, 308.0f);

	path.moveTo(294.0f, 328.0f);
	path.lineTo(340.0f, 328.0f);

	path.moveTo(280.0f, 354.0f);
	path.lineTo(357.0f, 354.0f);

	path.moveTo(249.0f, 380.0f);
	path.lineTo(327.0f, 380.0f);

	g2.setStroke(new BasicStroke(5));
	g2.draw(path);
		
	
	//Printing Text
	Font font = new Font("Dialog", Font.PLAIN, 34);
	g2.setFont(font);

	g2.drawString(" T H E",130f,230f);
	g2.drawString("U N I V E R S I T Y",110f,275f);
	g2.drawString(" O F",130f, 320f);
	g2.drawString(" A I Z U",130f, 365f);

	//print Font by circle
	String str1 = "Knowledge for Humanity . 1993 . to Advance";
	AffineTransform at = new AffineTransform();

	g2.setFont(new Font("Dialog",Font.BOLD,16));
	for(int i=0;i<str1.length();i++){
	    x=250+178*Math.cos(theta);
	    y=250+178*Math.sin(theta);
	    at.setToRotation(theta+Math.toRadians(90),(float)x,(float)y);
	    g2.setTransform(at);
	    theta+=(2*Math.PI)/str1.length();
	    if(theta >= 2*Math.PI)theta -= 2*Math.PI;
	    g2.drawString(str1.substring(i,i+1),(float)x,(float)y);
	}


	g.dispose();
    }

    //For runnning Animation
    public void run(){
		int DELAY = 16;
        while (running) {
			double oldTime = System.currentTimeMillis();
			double milliTime = 1000/fps;

			if(rotationToggle == false){
	    
				t+= 2*Math.PI / fps;
				theta += 2*Math.PI / fps;
			
				if(theta >= 2*Math.PI) theta -= 2*Math.PI;

				if (t > 2*Math.PI) t -=2*Math.PI;
				repaint();
			}
			else if(rotationToggle == true){
				t-= 2*Math.PI/fps;
				theta -= 2*Math.PI/fps;

				if(theta <= 0) theta += 2*Math.PI;

				if (t < 0) t = 2*Math.PI;
				repaint();
			}
			
			double newTime = System.currentTimeMillis();
			DELAY = (int)(milliTime-(newTime - oldTime));
			if(DELAY < 2) DELAY = 2;
			
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) { }
       }

    }
}

