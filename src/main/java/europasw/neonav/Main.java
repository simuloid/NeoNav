/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.awt.geom.*;
import java.util.List;

/**
 *
 * @author Steve
 */
public class Main {
    static double distance(List<Double> a, List<Double> b) {
        double d = 0;
        for (int i = 0; i < a.size(); ++i) {
            double dx = a.get(i) - b.get(i);
            d += dx * dx;
        }

       return Math.sqrt(d);
    }
    public static void main(String[] args) {
        System.out.println("Starting");
        World w = new World();
        w.addObject(new Rectangle2D.Float(30, 40, 10, 10));
        Pose mystery = w.randomPose();
        List<Double> scan = w.ranges(mystery, Pixie.sensorAngles);
        System.out.println("Halfway");
        ParticleFilter filter = new ParticleFilter(50, w);
        System.out.println("made filter");
        for (int i = 0; i < 100; ++i) {
            filter.scoreAgainst(scan);
            // System.out.println(filter.particles);
            System.out.println("best: " + filter.best);
            filter.improve();
        }
    }
}
