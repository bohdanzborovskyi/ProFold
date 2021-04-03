package model;

public class Point 
{
	private int x;
	private int y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	} 
	
	public boolean equals(Point p) 
	{
		if((this.y == p.y) && (this.x == p.x))
			return true;
		else return false;
	}
	
	public String toString() 
	{
		return " " + this.y + " " + this.x;
	}
	
	
	public static Point moveDown(Point p) 
	{
		p.setY(p.getY()-1);
		return new Point(p.getX(),p.getY());
	}
	
	public static Point moveUp(Point p) 
	{
		p.setY(p.getY()+1);
		return new Point(p.getX(),p.getY());
	}
	
	public static Point moveLeft(Point p) 
	{
		p.setX(p.getX()-1);
		return new Point(p.getX(),p.getY());
	}
	
	public static Point moveRight(Point p) 
	{
		p.setX(p.getX()+1);
		return new Point(p.getX(),p.getY());
	}
	
	
	
	
	
	
}
