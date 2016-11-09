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
        World w = new World();
        Double[] sensorAngles = { Math.toRadians(60), 0.0, Math.toRadians(-60) };
        w.addObject(new Rectangle2D.Float(30, 40, 10, 10));
        Pose mystery = w.randomPose();
        List<Double> scan = w.ranges(mystery, sensorAngles);
        for (int i = 0; i < 50; ++i) {
           Pixie p = new Pixie();
           p.pose = w.randomPose();
           List<Double> sample = w.ranges(p.pose, sensorAngles);
           p.score = distance(scan, sample);
           System.out.println(p);
        }
    }
}
