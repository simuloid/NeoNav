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
}
