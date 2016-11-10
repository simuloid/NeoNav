/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Steve
 */
public class Pose {
    public double x;
    public double y;
    public double heading; // radians
    public double dx;
    public double dy;
    public double dHeading;

    void update(double dt) {
        forward(dt);
   }
   void updateToward(Pose target, double fraction) {
        List<Double> diff = Pose.difference(target, this);
        x += diff.get(0) * fraction;
        y += diff.get(1) * fraction;
        heading = World.angleSum(heading, diff.get(2) * fraction);       
   }
   void updateTrajectoryToward(Pose target, double fraction) {
        List<Double> diff = Pose.difference(target, this);
        dx = (1 - fraction) * dx + diff.get(0) * fraction;
        dy = (1 - fraction) * dy + diff.get(1) * fraction;
        dHeading = World.angleSum(dHeading, diff.get(2) * fraction);       
   }
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
       this.dx = other.dx;
       this.dy = other.dy;
       this.dHeading = other.dHeading;
    }

    public double distance(Pose other) {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
    public static List<Double> difference(Pose p1, Pose p2) {
        Double[] diff = new Double[3];
        diff[0] = p1.x - p2.x;
        diff[1] = p1.y - p2.y;
        diff[2] = World.angleDifference(p2.heading, p1.heading);
        return Arrays.asList(diff);
    }
    @Override
    public String toString() {
       return String.format("<%.2f, %.2f, %.1f>", x, y, Math.toDegrees(heading));
    }
    
    public void shake(double fraction) {
        x += fraction + (Math.random() * 2 - 1);
        y += fraction + (Math.random() * 2 - 1);
        heading += fraction + (Math.random() * 2 - 1);
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
