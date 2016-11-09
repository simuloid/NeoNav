/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

/**
 *
 * @author Steve
 */
public class Pose {
    public double x;
    public double y;
    public double heading; // radians
    
    public Pose() {
        this(0, 0, 0.0);
    }
    
    public Pose(double x, double y, float headingDegrees) {
        this(x, y, Math.toRadians(headingDegrees));
    }
    
    public Pose(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }
    
    public Pose(Pose other) {
       this.x = other.x;
       this.y = other.y;
       this.heading = other.heading;
    }

    @Override
    public String toString() {
       return String.format("<%.2f, %.2f, %.1f>", x, y, Math.toDegrees(heading));
    }
    
    public void forward(double meters) {
       x = x + meters * Math.cos(this.heading);
       y = y + meters * Math.sin(this.heading);
    }

    public void rotate(double radians) {
       this.heading = World.angleSum(this.heading, radians);
    }
    
    public void rotate(float degrees) {
       rotate(Math.toRadians(degrees));
    }
   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Pose other = (Pose) obj;
      if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
         return false;
      }
      if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
         return false;
      }
      if (Double.doubleToLongBits(this.heading) != Double.doubleToLongBits(other.heading)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
      hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
      hash = 97 * hash + (int) (Double.doubleToLongBits(this.heading) ^ (Double.doubleToLongBits(this.heading) >>> 32));
      return hash;
   }
}
