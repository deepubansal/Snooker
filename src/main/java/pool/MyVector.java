package pool;

import java.awt.Point;



class MyVector
  {
   
     double x;
     double y;
     double z;
     
     final static double neighbourtozero = 2;
     public MyVector(double a,double b)
       {
         x = a; y = b; z = 0; 
       }

     public MyVector (MyVector a)
     {
    	 super();
    	 x= a.x;
    	 y = a.y;
    	 z = a.z;
     }
     public MyVector(double a,double b, double c)
     {
       x = a; y = b; z = c; 
     }

     public MyVector(MyVector a, MyVector b)
     {
    	 x = a.getX()-b.getX();
    	 y = a.getY()-b.getY();
    	 z = a.getZ()-b.getZ();
     }
	
     
     public MyVector(Point a, Point b)
     {
    	 x = a.getX()-b.getX();
    	 y = a.getY()-b.getY();
    	 z = 0;
     }
     
     public double dotProduct(MyVector n)
     {
         double prodx= x * n.getX();
         double prody= y * n.getY();
         double prodz= z * n.getZ();
         return (prodx+prody+prodz);
     }
    
     public MyVector dotProduct(double n)
     {
          return new MyVector(x * n, y * n, z * n);
     }
     
     public MyVector subtract(MyVector n)
     {
          return (new MyVector(x - n.getX(),y - n.getY(), z - n.getZ()));
     }
     
     public MyVector add(MyVector n)
     {
          return (new MyVector(x + n.getX(),y + n.getY(), z + n.getZ()));
     }

    /* public String toString()
     {
    	 return "["+(int)x+","+(int)y+","+(int)z+"]";
     }
     */
     public String toString()
     {
    	 return "["+x+","+y+","+z+"]";
     }
     public double magnitude()
     {
    	 return Math.sqrt(x*x+y*y+z*z);
     }
     
     public boolean isZero()
     {
    	
    	 if ((x >= -neighbourtozero) && (x <= neighbourtozero) && (z >= -neighbourtozero) && (z <= neighbourtozero) && (y >= -neighbourtozero) && (y <= neighbourtozero))
    		 return true;
    	 else
    		 return false;
     }
     
     public double getX() {
		return x;
	}



	public void setX(double x) {
		this.x = x;
	}



	public double getY() {
		return y;
	}



	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	 
	public MyVector crossProduct(MyVector n)
	{
		double ay = y;
		double ax = x;
		double az = z;
		double by = n.y;
		double bx = n.x;
		double bz = n.z;
		
		double crossX = ay*bz-az*by;
		double crossY = az*bx-ax*bz;
		double crossZ = ax*by-ay*bx;
		
		return (new MyVector(crossX,crossY,crossZ));
			
	}
	
	public MyVector normalize()
	{
		return (new MyVector(this.dotProduct(1/this.magnitude())));
	}
	 
	public double distanceFrom(MyVector n)
	{
		return this.subtract(n).magnitude();
	}
	
	public void roundOff()
	{
		x = (int)(x+0.5);
		y = (int)(y+0.5);
		z = (int)(z+0.5);
	}
  }   