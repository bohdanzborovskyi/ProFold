package view;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;


import logic.Algorythm;
import model.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;

public class Main extends Composite {
	public static Point prev, prevP = null;
	public static Display display;
	static Text text;
	static Canvas canvas,savedCanvas;
	static Button btnBuduj;
	static Shell shell;
	static String newS = "";
	static boolean end = true;
	static int nr = 1;
	public static Map<Point,Point> mapPoints;

	
	public static void main(String[] args) {
		
		display = new Display();
	    shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));
		shell.setSize(1500, 800);
		Main main = new Main(shell,SWT.NONE);
		
		text.addListener(SWT.Verify, new Listener() 
		{
			public void handleEvent(Event e) 
			{
				Text source = (Text)e.widget;
				
				final String oldS = source.getText();
		        newS = oldS.substring(0, e.start) + e.text + oldS.substring(e.end);

		        System.out.println(oldS + " -> " + newS);
			}
		});			
		
		
		
		btnBuduj.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e) 
			{	
				int count = 0;
				do
				{					
	out:		{				
				GC gc = new GC(canvas);
				canvas.drawBackground(gc, 10, 93, 900, 700);
				Algorythm.transformInputString(newS);								
				Algorythm.createPointsCircuit(Algorythm.protein.length*4);
				mapPoints = new LinkedHashMap<>();
				Algorythm.nextPointsTemp = new HashMap<Point,String>();
				Algorythm.nextGetPointsTemp = new LinkedList<Point>();
				for(int i=0;i<Algorythm.protein.length;i++) 
				{						
					String s = Algorythm.protein[i];					
					Point p = Algorythm.setPoint(s,i);														
					if(s.equals("p"))
					{
					//	prevP = new Point(p.getX(),p.getY());
						drawCircle(p,gc);				
					}
					else
					{
						if(prevP!=null) 
						{							
							mapPoints.put(prevP,p);							
						}
					//	prevP = new Point(p.getX(),p.getY());
						drawFillCircle(p,gc);						
					}
					if(Algorythm.endStatus==false)
					{
						Algorythm.endStatus = true;
						break out;
					}
			    }
				count++;
				if(Algorythm.countPoints()>24)
				{	
					System.out.println(mapPoints);
					saveCanvas(canvas);
					end = false;					
				}
				System.out.println("Proba!!!!!!!!!!!!!!!!!!!!: " + count);				
				prev = null;
				prevP = null;
				gc.dispose();				
				}							
	 			}while(end);
	//			}while(false);
			}});		
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();			
		}			
		
		display.dispose();
		
	}
	
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Main(Composite parent, int style) {

		
		super(parent, style);
	
		text = new Text(this, SWT.BORDER);
		text.setBounds(10, 44, 434, 25);
		
		Label lblWpiszFormul = new Label(this, SWT.NONE);
		lblWpiszFormul.setBounds(10, 10, 114, 15);
		lblWpiszFormul.setText("Wpisz formul\u0119");
		
		btnBuduj = new Button(this, SWT.NONE);
		btnBuduj.setBounds(491, 42, 75, 25);
		btnBuduj.setText("Buduj");
		
		canvas = new Canvas(this, SWT.NONE);
		canvas.setBounds(10, 93, 900, 700);

	}

	
	
	public static void drawFillCircle(Point p,GC gc) 
	{	
		Color blueCol = new Color(display, 0, 0, 255);
		Color red = new Color(display,255,0,0);
        gc.setBackground(blueCol);
        if(prev==null)gc.setBackground(red);
        gc.fillOval(p.getX()*10, p.getY()*10, 10, 10);
		if(prev!=null) 
		{
			gc.drawLine(prev.getX()+5, prev.getY()+5,p.getX()*10+5, p.getY()*10+5);
		}
		prevP = new Point(p.getX(),p.getY());
		prev = new Point(p.getX()*10, p.getY()*10);

	}
	
	public static void drawCircle(Point p, GC gc) 
	{
		Color red = new Color(display,255,0,0);       
        if(prev==null)
        {
        	gc.setBackground(red);
        	gc.fillOval(p.getX()*10, p.getY()*10, 10, 10);
        }else 
        {
        	gc.drawOval(p.getX()*10, p.getY()*10, 10, 10);
        	gc.drawLine(prev.getX()+5, prev.getY()+5,p.getX()*10+5, p.getY()*10+5);
        }		
		prevP = new Point(p.getX(),p.getY());
		prev = new Point(p.getX()*10, p.getY()*10);			
	}	
	
	public static void saveCanvas(Canvas canvas) 
	{
		Image drawable = new Image(display,canvas.getBounds());
		GC gc = new GC(drawable);			
		canvas.print(gc);			
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] {drawable.getImageData()};
		String path = "D:\\swt.png";
		try {			
			File image = new File("D:\\" + "swt.png");
			image.createNewFile();
		} catch (IOException e) {e.printStackTrace();	
		}				
		loader.save(path,SWT.IMAGE_PNG);
//		drawable.dispose();
//		gc.dispose();
	}
		
}
