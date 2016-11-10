/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Represents the simulated world.  Contains a list of simulated object that can
be detected
 * @author Steve
 */
public class World {
    int width;
    int height;
    static Random dice = new Random();
    
    Collection<Shape> objects = new ArrayList<>();
    public World() {
        this(100, 100);
    }
    
    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public Pose randomPose() {
        boolean good = false;
        Pose p = null;
        while (!good) {
            p = new Pose(dice.nextDouble()*width, dice.nextDouble()*height,2*dice.nextDouble()*Math.PI - Math.PI);
            good = true;
            for (Shape s: objects) {
                if (s.contains(p.x, p.y)) {
                    good = false;
                    break;
                }
            }
        }
//       p.dx = (dice.nextDouble()*2 - 1)*width*0.001;
//       p.dy = (dice.nextDouble()*2 - 1)*height*0.001;
//       p.dHeading = (dice.nextDouble()*2 - 1)*Math.PI*0.01;
       return p;
    }
    public static double angleDifference(double from, double to) {
        return Math.atan2(Math.sin(to-from), Math.cos(to-from));
    }
    public static double angleSum(double a1, double a2) {
        return Math.atan2(Math.sin(a1+a2), Math.cos(a1+a2));
    }
    /**
     * Returns the range in meters to the nearest object along the
     * given angles from the given Pose.
     * @param p The source pose from which measurements will be made
     * @param angles The list of angles to follow relative to the pose.
     * @return A List of ranges corresponding to the given angles.
     */
    public List<Double> ranges(Pose p, Double... angles) {
        List<Double>  values = new ArrayList<>(angles.length);
        for (double a: angles) {
            double angle = angleSum(a, p.heading);
            double range = rangeToObject(p.x, p.y, angle);
            values.add(range);
        }
        return values;
    }

    private double rangeToObject(double x, double y, double angle) {
//        System.out.format("Returning range to nearest from %.2f, %.2f heading %.1f\n", x, y, Math.toDegrees(angle));
      double r = 0;
      Pose p;
      do {
         p = new Pose(x, y, angle);
         p.forward(r);
         for (Shape s: objects) {
            if (s.contains(p.x, p.y)) {
               return r;
            }
         }
         r += 0.01;
      } while (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height);
      
      return r;
    }
    
    public void addObject(Shape s) {
       objects.add(s);
    }
}
