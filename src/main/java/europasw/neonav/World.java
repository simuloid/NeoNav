/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the simulated world.  Contains a list of simulated object that can
be detected
 * @author Steve
 */
public class World {
    int width;
    int height;
    
    public World() {
        this(100, 100);
    }
    
    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public double angleDifference(double from, double to) {
        return Math.atan2(Math.sin(to-from), Math.cos(to-from));
    }
    public double angleSum(double a1, double a2) {
        return Math.atan2(Math.sin(a1+a2), Math.cos(a1+a2));
    }
    /**
     *
     * @param heading
     * @param angles
     * @return
     */
    public List<Double> ranges(Pose p, Double... angles) {
        List<Double>  values = new ArrayList<>(angles.length);
        System.out.format("From %.1f to %.1f is %.1f degrees\n", 
                Math.toDegrees(2*p.heading), Math.toDegrees(0), 
                Math.toDegrees(angleDifference(2*p.heading, 0)));
        for (double a: angles) {
            double angle = angleSum(a, p.heading);
            double range = rangeToObject(p.x, p.y, angle);
        }
        return values;
    }

    private double rangeToObject(double x, double y, double angle) {
        System.out.format("Returning range to nearest from %.2f, %.2f heading %.1f\n", x, y, Math.toDegrees(angle));
        return 0;
    }
}