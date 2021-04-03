package logic;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


import model.Point;
import view.Main;

public class Algorythm
{
	public static String[]protein = null;
	public static int[][]points, temppoints = null;
	public static Point center;
	public static String step;	
	public static Set<Point> usedPoints;
	public static boolean endStatus = true,checkFind = false;
	public static int max,cos, circuitLength= 0, wayCountAll = 0;
	public static HashMap<Point,String> nextPoints = new HashMap<Point,String>();
	public static LinkedList<Point> nextGetPoints = new LinkedList<Point>();
	public static LinkedList<Point> temporaryPoints = new LinkedList<Point>();
	public static HashMap<Point,String> nextPointsTemp = new HashMap<Point,String>();
	public static LinkedList<Point> nextGetPointsTemp = new LinkedList<Point>();
	public static int i1=1,i2=1,i3=1,i4=1,i5=1,i6=1,i7=1,i8=1,i9=1,i10 =1;
	public static HashMap<Point,Integer> randomPoints = new HashMap<Point,Integer>();
	public static int nestedLevel = 0;
	public static Point toSavePointLeft = null,toSavePointUp = null,toSavePointRight = null,toSavePointDown = null, toSaveStartPoint = null;	
	public static String state = "";
	
	public static void transformInputString(String input) 
	{
		protein = input.split("");		
	}
	
	public static void createPointsCircuit(int length) 
	{		
		circuitLength = length;
		usedPoints = new HashSet<Point>();
		points = new int[length][length];
		step = "down";
		if(length%2!=0) 
		{
			center = new Point((length+1)/2,(length+1)/2);
		}else 
		{
			center = new Point(length/2,length/2);
		}	
	}
	
	public static Point randomStep(Point point, int i, int HP) 
	{			
		if(nestedLevel==0) 
		{
			toSaveStartPoint = new Point(point.getX(),point.getY());
			toSavePointLeft = new Point(point.getX()-1,point.getY());
			toSavePointUp = new Point(point.getX(),point.getY()+1);
			toSavePointRight = new Point(point.getX()+1,point.getY());
			toSavePointDown = new Point(point.getX(),point.getY()-1);
		}		
		nestedLevel++;
		int random = 1;
		int savedIndex = i;
		wayCountAll =0;
		randomPoints = new HashMap<Point,Integer>();
		i++;
		switch(random) 
		{
		case 1: // krok w lewo 			
			if(nestedLevel ==1) state = "left";	
			if((i!=0 && points[point.getY()][point.getX()-1]==0) && isNotFirstHPoint()) 
			{
				point.setX(point.getX()-1);			
				step = "left";
				points[point.getY()][point.getX()]= HP;
				int counter = 0;								
				do 
				 { 		
					 int wayCount  = nextHProtein(i);
					 wayCountAll = wayCountAll + wayCount;
					 if(wayCountAll>4 || counter>3) 
					 {								 	
						 	if(nestedLevel>2) break;
						 	savedIndex++;
						 	Point newP = randomStep(point,savedIndex,HP);
						 	savedIndex--;
						 	if(newP!=null) 
						 	{						 	
							nextGetPointsTemp = new LinkedList<Point>();
							nextPointsTemp = new HashMap<Point,String>();
							nextPointsTemp.put(newP,step);	
							nextGetPointsTemp.add(newP);
							break;
						 	}else break;
					 }
					 if(!checkFind)
					 {
						 checkFind = findWay(wayCountAll,point);
					 }					 
					 counter++;
					 i = i + wayCount;				
				 }while(!checkFind ); 				 
				 i = savedIndex+1;				 
				 if(checkFind) 
				 {							 
					 randomPoints.put(returnStatePoint(),wayCountAll);
				 }
				 checkFind = false;
				 wayCountAll = 0;
				 points[point.getY()][point.getX()]= 0;
				 point.setX(point.getX()+1);				 
				 nextGetPointsTemp = new LinkedList<Point>();
				 nextPointsTemp = new HashMap<Point,String>();
				 
			}else if((i==0 || !isNotFirstHPoint()) && points[point.getY()][point.getX()-1]==0)
			{
				point.setX(point.getX()-1);			
				step = "left";
				break;
			}
			
		case 2:	 // krok w górę	
			if(nestedLevel ==1) state = "up";
			if(i!=0 && isNotFirstHPoint()  && points[point.getY()+1][point.getX()]==0) 
			{
				point.setY(point.getY()+1);
				step = "up";
				points[point.getY()][point.getX()]= HP;
				int counter = 0;								
				do 
				 { 		
					 int wayCount  = nextHProtein(i);
					 wayCountAll = wayCountAll + wayCount;
					 if(wayCountAll>4 || counter>3) 
					 {					 	
						 	
						 	if(nestedLevel>2) break;
						 	savedIndex++;
						 	Point newP = randomStep(point,savedIndex,HP);
						 	savedIndex--;
						 	if(newP!=null) 
						 	{						 	
							nextGetPointsTemp = new LinkedList<Point>();
							 nextPointsTemp = new HashMap<Point,String>();
							nextPointsTemp.put(newP,step);	
							nextGetPointsTemp.add(newP);
							break;
						 	}else break;
					 }
					 if(!checkFind)
					 {
					 checkFind = findWay(wayCountAll,point);
					 }					
					 counter++;
					 i = i + wayCount;				
				 }while(!checkFind);				 
				 i = savedIndex+1;
				 if(checkFind) 
				 {	
					 randomPoints.put(returnStatePoint(),wayCountAll);
				 }
				 checkFind = false;
				 wayCountAll = 0;
				 points[point.getY()][point.getX()]= 0;	
				 point.setY(point.getY()-1);			
				 nextGetPointsTemp = new LinkedList<Point>();
				 nextPointsTemp = new HashMap<Point,String>();
				 
			}else if((i==0 || !isNotFirstHPoint())  && points[point.getY()+1][point.getX()]==0)
			{
				point.setY(point.getY()+1);
				step = "up";
				break;
			}
		
		case 3: // krok w prawo
			if(nestedLevel ==1) state = "right";
			if(i!=0 && isNotFirstHPoint()  && points[point.getY()][point.getX()+1]==0) 
			{
				point.setX(point.getX()+1);
				step = "right";
				points[point.getY()][point.getX()]= HP;
				int counter = 0;								
				do 
				 { 		
					 int wayCount  = nextHProtein(i);
					 wayCountAll = wayCountAll + wayCount;
					 if(wayCountAll>4 || counter>3) 
					 {		
						 
						 	if(nestedLevel>2) break;
						 	savedIndex++;
						 	Point newP = randomStep(point,savedIndex,HP);						 	
						 	savedIndex--;
						 	if(newP!=null) 
						 	{						 	
							nextGetPointsTemp = new LinkedList<Point>();
							 nextPointsTemp = new HashMap<Point,String>();
							nextPointsTemp.put(newP,step);	
							nextGetPointsTemp.add(newP);
							break;
						 	}else break;
					 }
					 if(!checkFind)
					 {
					 checkFind = findWay(wayCountAll,point);
					 }				
					 counter++;
					 i = i + wayCount;				
				 }while(!checkFind);				
				 i = savedIndex+1;
				 if(checkFind) 
				 {	
					 randomPoints.put(returnStatePoint(),wayCountAll);
				 }
				 checkFind = false;
				 wayCountAll = 0;
				 points[point.getY()][point.getX()]= 0;
				 point.setX(point.getX()-1);
				 nextGetPointsTemp = new LinkedList<Point>();
				 nextPointsTemp = new HashMap<Point,String>();
				 
			}
			else  if((i==0 || !isNotFirstHPoint())&& points[point.getY()][point.getX()+1]==0)
			{
				point.setX(point.getX()+1);
				step = "right";
				break;
			}			
		case 4: // krok w dół	
			if(nestedLevel ==1) state = "down";
			if(i!=0 && isNotFirstHPoint()  && points[point.getY()-1][point.getX()]==0) 
			{
				point.setY(point.getY()-1);
				step = "down";
				points[point.getY()][point.getX()]= HP;
				int counter = 0;								
				do 
				 { 			
					 int wayCount  = nextHProtein(i);
					 wayCountAll = wayCountAll + wayCount;
					 if(wayCountAll>4 || counter>3) 
					 {														    
						 	if(nestedLevel>2) break;
						 	savedIndex++;
						 	Point newP = randomStep(point,savedIndex,HP);		
						 	savedIndex--;
						 	if(newP!=null) 
						 	{						 	
							nextGetPointsTemp = new LinkedList<Point>();
							nextPointsTemp = new HashMap<Point,String>();
							nextPointsTemp.put(newP,step);	
							nextGetPointsTemp.add(newP);
							break;
						 	}else break;
					 }
					 if(!checkFind)
					 {
					 checkFind = findWay(wayCountAll,point);
					 }					
					 counter++;
					 i = i + wayCount;				
				 }while(!checkFind);				 
				 i = savedIndex+1;
				 
				 if(checkFind) 
				 {	
					 randomPoints.put(returnStatePoint(),wayCountAll);
				 }
				 checkFind = false;
				 wayCountAll = 0;
				 points[point.getY()][point.getX()]= 0;
				 point.setY(point.getY()+1);				
				 nextGetPointsTemp = new LinkedList<Point>();
				 nextPointsTemp = new HashMap<Point,String>();
				

			}else if((i==0 || !isNotFirstHPoint()) && points[point.getY()-1][point.getX()]==0)
			{
				point.setY(point.getY()-1);
				step = "down";
				break;
			}			
		}
		if(i!=0 && isNotFirstHPoint())
		{
			nestedLevel--;
			i--;
			if(randomPoints.isEmpty() && nestedLevel == 0) 
			{
				Point randomPoint = randomPoint(toSaveStartPoint);	
				return randomPoint;
			}
			else 
			{
			int min = 10;
			for(Map.Entry<Point,Integer> randomPoint : randomPoints.entrySet() ) 
			{
				if(min> randomPoint.getValue()) min = randomPoint.getValue();
			}
			for(Map.Entry<Point,Integer> randomPoint : randomPoints.entrySet() ) 
			{
				if(min == randomPoint.getValue()) point = randomPoint.getKey();
				break;
			}
			}
		}
		return point;
	}
	
	
	
	public static Point setPoint(String protein, int i) 
	{	
		nestedLevel=0;
		int count = 0;
		Point p,k = null;			
		if(protein.equals("p")) 
		{			
		do
			{						 
				 k = new Point(center.getX(),center.getY());
				 if(i==0) {p = randomStep(k,i,1);
				 }
				 else 
				 {
					 if(nextGetPointsTemp.isEmpty()) 
					 {
						 int counter=0;
						 int savedIndex = i;
						 do 
						 { 								 
							 int wayCount  = nextHProtein(i);
							 wayCountAll = wayCountAll + wayCount;
							 if(wayCountAll>4 || counter>3) 
							 {
								 	Point newP = randomStep(k,savedIndex,1);
								 	if(newP!=null) 
								 	{
								 	checkFind = true;								 	
									nextPointsTemp.put(newP,step);	
									nextGetPointsTemp.add(newP);
								 	}
							 }
							 if(!checkFind) 
							 {
							 checkFind = findWay(wayCountAll,k);
							 }	
							 counter++;
							 i = i + wayCount;							 
						 }while(!checkFind);
						 i = savedIndex;
						 checkFind = false;
						 wayCountAll = 0;				 
					}
					 p = nextGetPointsTemp.getFirst();
					 nextGetPointsTemp.removeFirst();
					 for(Map.Entry<Point, String> pointCut : nextPointsTemp.entrySet())
					 {
						 if(pointCut.getKey().getX()==p.getX() && pointCut.getKey().getY()==p.getY()) 
						 {
							 step = pointCut.getValue();						 
						 }
					 }
					 nextPointsTemp.remove(p);				 
				 }				
				 if(checkProtein(p)) 
				 {						 
					 center = p;	
					 points[center.getY()][center.getX()]=1;	
					 break;
				 }
				 else
				 {	  
					 nextGetPointsTemp = new LinkedList<Point>();
					 nextPointsTemp = new HashMap<Point,String>();
					 count++;
				 }
				 if(count >15) 
				 {
					 endStatus = false;
				 }
			}while(endStatus);						
			return center;
		}else 
		{			
			do 
			{				 
				 k = new Point(center.getX(),center.getY());
				 if(i==0) {p = k;}
				 else 
				 {
					 if(!isNotFirstHPoint()) 
					 {
						 	Point newP = randomStep(k,i,2);
							nextPointsTemp.put(newP,step);	
							nextGetPointsTemp.add(newP);
					 }
					 else 
					 {
						 if(nextGetPointsTemp.isEmpty()) 
						 {
							 int counter = 0;
							 int savedIndex = i;
							 do 
							 { 
								 int wayCount  = nextHProtein(i);
								 wayCountAll = wayCountAll + wayCount;
								 if(wayCountAll>4 || counter>3) 
								 {
									 	Point newP = randomStep(k,savedIndex,2);
										if(newP!=null)
										{
											checkFind = true;
											nextPointsTemp.put(newP,step);	
											nextGetPointsTemp.add(newP);
										}
								 }
								 if(!checkFind) 
								 {
								 checkFind = findWay(wayCountAll,k);
								 }
								 counter++;
								 i = i + wayCount;								 
							 }while(!checkFind);
							 i = savedIndex;
							 checkFind = false;
							 wayCountAll = 0;								
						 }
					 }
					 p = nextGetPointsTemp.getFirst();
					 nextGetPointsTemp.removeFirst();	
					 for(Map.Entry<Point, String> pointCut : nextPointsTemp.entrySet())
					 {
						 if(pointCut.getKey().getX()==p.getX() && pointCut.getKey().getY()==p.getY()) 
						 {
							 step = pointCut.getValue();						
						 }
					 }				 	
					 nextPointsTemp.remove(p);				
				 }				 
				 if(checkProtein(p)) 
				 {				
					 center = p;				
					 points[center.getY()][center.getX()]=2;
					 break ;
				 }
				 else count++;
				 if(count >15) 
				 {
					 endStatus = false;
				 }
			}while(endStatus);				
			return center;
		}		
	}
	
	
	
	public static boolean checkProtein(Point point) 
	{	
		boolean status = false;
		switch(step) 
		{
		case "left":
			if((points[point.getY()][point.getX()]==0) 
					&&			   
			   ((points[point.getY()-1][point.getX()]==0) || 
			   (points[point.getY()+1][point.getX()]==0) || 
			   (points[point.getY()][point.getX()-1]==0)) )	
			{
				status = true;	
				break;
			}else
			{
				status = false;
				break;
			}
		case "right":			
			if((points[point.getY()][point.getX()]==0)
					&& 
			   ((points[point.getY()-1][point.getX()]==0) || 
			   (points[point.getY()+1][point.getX()]==0)  || 
			   (points[point.getY()][point.getX()+1]==0)) )		
			{
				status = true;	
				break;
			}else
			{
				status = false;
				break;
			}
		case "down":			
			if((points[point.getY()][point.getX()]==0) 
					&& 
			   ((points[point.getY()-1][point.getX()]==0) || 
			   (points[point.getY()][point.getX()+1]==0)  || 
			   (points[point.getY()][point.getX()-1]==0)))
			{
				status = true;	
				break;
			}else
			{
				status = false;
				break;
			}			
		case "up":			
			if((points[point.getY()][point.getX()]==0)
					&&
			   ((points[point.getY()][point.getX()+1]==0) || 
			   (points[point.getY()+1][point.getX()]==0)  || 
			   (points[point.getY()][point.getX()-1]==0))) 
			{
				status = true;	
				break;
			}else
			{
				status = false;
				break;
			}		
		}
		return status;
	}
	

	
	public static int countPoints() 
	{
		int count = 0;
		for(int y=0;y<points.length;y++) 
		{
			for(int x=0;x<points[y].length;x++) 
			{
				if(points[y][x]==2) 
				{
					if(points[y][x+1]==2) 
					{
						if(!checkPoint(new Point(x+1,y),new Point(x,y))) 
						{
							count++;
						}
					}else if(points[y][x-1]==2) 
					{
						if(!checkPoint(new Point(x-1,y),new Point(x,y))) 
						{
							count++;
						}
					}else if(points[y-1][x]==2) 
					{
						if(!checkPoint(new Point(x,y-1),new Point(x,y))) 
						{
							count++;
						}
					}else if(points[y+1][x]==2) 
					{
						if(!checkPoint(new Point(x,y+1),new Point(x,y))) 
						{
							count++;
						}
					}
				}
			}
		}	
		if(max<count) 
		{
			max = count;
		}
		System.out.println("Count: " + count + " Max:-" + max);
		return count;
	}
	
	public static boolean checkPoint(Point p, Point p2) 
	{
		boolean check = false;
	out:for(Map.Entry<Point, Point> entry : Main.mapPoints.entrySet()) 
		{
			Point p3 = entry.getKey();
			Point p4 = entry.getValue();			
			if(((p3.equals(p)&&p4.equals(p2))||((p3.equals(p2)&&p4.equals(p)))))
					{								
							check = true;
							break out;
					}			
		}
		if(!check) 
		{
			Main.mapPoints.put(p,p2);
		}
		return check;
	}	

	

	
	
	
	
	

	
	public static int nextHProtein(int currentIndex) 
	{
		int position = 0;
		for(int i=currentIndex; i<protein.length; i++) 
		{
			position++;
			if(protein[i].equals("h"))
			{				
				break;
			}
		}
		return position;
	}
	
	public static boolean findWay(int count, Point currentPoint) 
	{	
		if(count==0)return false;		
		temppoints = fillTempPoints();
		nextPointsTemp = new HashMap<Point,String>();	
		nextGetPointsTemp = new LinkedList<Point>();
		boolean find = false;		
		String [][] ways = makeWays(count);
	out:for(int i=0;i<ways.length;i++) 
		{
			Point savedPoint = new Point(currentPoint.getX(),currentPoint.getY());			
			for(int k=0;k<ways[i].length;k++) 
			{
				temppoints = fillTempPoints();
				nextPointsTemp = new HashMap<Point,String>();	
				nextGetPointsTemp = new LinkedList<Point>();				
				move(savedPoint,count,ways[i][k]);
				Point last = nextGetPointsTemp.getLast();
				if(isCleanWay() && isNearHPoint(last,nextPointsTemp.get(last))) 
				{	
					find = true;
					break out;
				}
				else 
				{
					temppoints = fillTempPoints();
					nextPointsTemp = new HashMap<Point,String>();	
					nextGetPointsTemp = new LinkedList<Point>();
				}
			}
		}
		return find;		
	}
	
	public static boolean isCleanWay() 
	{
		temporaryPoints = new LinkedList<Point>();
		Point prev = null;
		boolean check = false;
		for(Point p: nextGetPointsTemp)
		{	
			if(prev!=null) 
			{
				if(points[p.getY()][p.getX()]==0 && temppoints[p.getY()][p.getX()]==0 && isNearPoints(prev, p) && isNotInTemporary(p)) 
				{
					temppoints[p.getY()][p.getX()]=3;
					temporaryPoints.add(p);
					check = true;
				}
				else
				{
					check = false;
					break;
				}
			}
			else 
			{
				if(points[p.getY()][p.getX()]==0 && temppoints[p.getY()][p.getX()]==0 && isNotInTemporary(p)) 
				{
					temppoints[p.getY()][p.getX()]=3;
					temporaryPoints.add(p);
					check = true;
				}
				else
				{
					check = false;
					break;
				}
			}
			prev = p;
		}
		return check;
	}
	
	public static String[][] makeWays(int position)
	{		
		String [][] ways = new String[(int)Math.pow(4, position)][1];
		for(int i=0; i<ways.length;i++) 
		{		
			for(int k=0;k<ways[i].length;k++) 
			{
				if(position == 1) 	{ways[i][k]= String.valueOf(i1); fillVariationTable(1);	}
				if(position == 2) 	{ways[i][k]= String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}	
				if(position == 3) 	{ways[i][k]= String.valueOf(i3) + String.valueOf(i2) +  String.valueOf(i1); fillVariationTable(1);}
				if(position == 4) 	{ways[i][k]= String.valueOf(i4) + String.valueOf(i3) +  String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
				if(position == 5) 	{ways[i][k]= String.valueOf(i5) + String.valueOf(i4) +  String.valueOf(i3) + String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
				if(position == 6) 	{ways[i][k]= String.valueOf(i6) + String.valueOf(i5) +  String.valueOf(i4) + String.valueOf(i3) + String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
				if(position == 7) 	{ways[i][k]= String.valueOf(i7) + String.valueOf(i6) +  String.valueOf(i5) + String.valueOf(i4) + String.valueOf(i3) + String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
				if(position == 8) 	{ways[i][k]= String.valueOf(i8) + String.valueOf(i7) +  String.valueOf(i6) + String.valueOf(i5) + String.valueOf(i4) + String.valueOf(i3) + String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
				if(position == 9) 	{ways[i][k]= String.valueOf(i9) + String.valueOf(i8) +  String.valueOf(i7) + String.valueOf(i6) + String.valueOf(i5) + String.valueOf(i4) + String.valueOf(i3) + String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
				if(position == 10) 	{ways[i][k]= String.valueOf(i10) + String.valueOf(i9) +  String.valueOf(8) + String.valueOf(i7) + String.valueOf(i6) + String.valueOf(i5) + String.valueOf(i4) + String.valueOf(i3) + String.valueOf(i2) + String.valueOf(i1); fillVariationTable(1);}
			}
		}	
		i1=1;i2=1;i3=1;i4=1;i5=1;i6=1;i7=1;i8=1;i9=1;i10=1;
		return ways;
	}
	
	public static void move(Point p,int number, String variation ) 
	{
		for(int i=0;i<number;i++) 
		{			
			int k = Integer.parseInt(String.valueOf(variation.charAt(i)));
			if(k==1) 
			{
				Point newP = Point.moveDown(p);				
				nextGetPointsTemp.add(newP);
				nextPointsTemp.put(newP, "down");
			}else if(k==2) 
			{
				Point newP = Point.moveUp(p);
				nextGetPointsTemp.add(newP);
				nextPointsTemp.put(newP, "up");
			}else if(k==3) 
			{
				Point newP = Point.moveLeft(p);
				nextGetPointsTemp.add(newP);
				nextPointsTemp.put(newP, "left");
			}else if(k==4) 
			{
				Point newP = Point.moveRight(p);
				nextGetPointsTemp.add(newP);
				nextPointsTemp.put(newP, "right");
			}
		}		
	}
	
	public static int[][]fillTempPoints()
	{
		int [][] points = new int[circuitLength][circuitLength];
		for(int i=0; i<points.length;i++) 
		{
			for(int k=0; k<points[i].length;k++) 
			{
				points[i][k] = 0;
			}
		}
		return points;
	}
	
	public static void fillVariationTable(int add) 
	{				
			i1= i1+add;
			if(i1==5) 
			{
				i2++;
				i1=1;
			}
			if(i2==5) 
			{
				i3++;
				i2=1;
			}
			if(i3==5) 
			{
				i4++;
				i3=1;
			}
			if(i4==5) 
			{
				i5++;
				i4=1;
			}
			if(i5==5) 
			{
				i6++;
				i5=1;
			}
			if(i6==5) 
			{
				i7++;
				i6=1;
			}
			if(i7==5) 
			{
				i8++;
				i7=1;
			}
			if(i8==5) 
			{
				i9++;
				i8=1;
			}
			if(i9==5) 
			{
				i10++;
				i9=1;
			}		
	}
	
	public static boolean isNearPoints(Point p1, Point p2) 
	{
		if(((p1.getY()-p2.getY()==1 && p1.getX()-p2.getX()==0) || (p1.getY()-p2.getY()==0 && p1.getX()-p2.getX()==1))) return true;
		else return false;
	}
	
	public static boolean isNotInTemporary(Point p) 
	{
		boolean check = true;
		for(Point temp: temporaryPoints) 
		{
			if(p.getY()==temp.getY() && p.getX()==temp.getX())
			{
				check = false;
				break;
			}
		}
		return check;
	}
	
	public static boolean isNotInPoints(Point p) 
	{
		boolean check = true;
		for(Point temp: nextGetPointsTemp) 
		{
			if(p.getY()==temp.getY() && p.getX()==temp.getX())
			{
				check = false;
				break;
			}
		}
		return check;
	}
	
	public static boolean isNearHPoint(Point p, String stepPoint) 
	{
		boolean check = false;
		switch(stepPoint) 
		{
		case "up":
			if(points[p.getY()+1][p.getX()]==2 || points[p.getY()][p.getX()-1]==2 || points[p.getY()][p.getX()+1]==2) 
			{			
				check = true;
				break;
			}
			break;
		case "down":
			if(points[p.getY()-1][p.getX()]==2 || points[p.getY()][p.getX()-1]==2 || points[p.getY()][p.getX()+1]==2) 
			{
				check = true;
				break;
			}
			break;
		case "left":
			if(points[p.getY()+1][p.getX()]==2 || points[p.getY()][p.getX()-1]==2 || points[p.getY()-1][p.getX()]==2) 
			{
				check = true;
				break;
			}
			break;
		case "right":
			if(points[p.getY()+1][p.getX()]==2 || points[p.getY()][p.getX()+1]==2 || points[p.getY()-1][p.getX()]==2) 
			{
				check = true;
				break;
			}
			break;
		}
		return check;		
	}
	
	public static boolean isNotFirstHPoint() 
	{
		boolean check = false;
	out:for(int i=0;i<points.length;i++) 
		{
			for(int k=0;k<points[i].length;k++) 
			{
				if(points[i][k]==2) 
				{
					check = true;
					break out;
				}
			}
		}
		return check;
	}
	
	public static Point returnStatePoint() 
	{
		Point statePoint = null;
		if(state.equals("left")) statePoint = toSavePointLeft;
		else if(state.equals("right")) statePoint = toSavePointRight;
		else if(state.equals("up")) statePoint = toSavePointUp;
		else if(state.equals("down")) statePoint = toSavePointDown;
		return statePoint;
	}
	
	public static Point randomPoint(Point  current) 
	{	
		boolean free = true;
		Point random = null;
		do 
		{
		int rand = 1 + (int) (Math.random()*4);
		switch(rand) 
		{
		case 1:
			if(points[current.getY()][current.getX()-1]==0)
			{
				free = false;
				random = new Point(current.getX()-1,current.getY());
				step = "left";				
				break;
			}			
			break;
		case 2:
			if(points[current.getY()][current.getX()+1]==0)
			{
				free = false;				
				random = new Point(current.getX()+1,current.getY());
				step = "right";
				break;
			}			
			break;
		case 3:
			if(points[current.getY()-1][current.getX()]==0)
			{
				free = false;			
				random = new Point(current.getX(),current.getY()-1);
				step = "down";
				break;
			}			
			break;
		case 4:
			if(points[current.getY()+1][current.getX()]==0)
			{
				free = false;			
				random = new Point(current.getX(),current.getY()+1);
				step = "up";
				break;
			}			
			break;			
		}
		}while(free);
		return random;
	}
	
	
	
}


