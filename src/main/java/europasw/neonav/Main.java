/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.awt.BorderLayout;
import java.awt.geom.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Steve
 */
public class Main {
    static double distance(List<Double> a, List<Double> b) {
        double d = 0;
        for (int i = 0; i < a.size(); ++i) {
            double dx = a.get(i) - b.get(i);
            d += Math.abs(dx);
        }

       return Math.sqrt(d);
    }
    public static void main(String[] args) {
        World w = new World(100, 100);
        for (int i = 0; i < w.width/5; ++i) {
            w.addObject(new Rectangle2D.Float((float) (w.width*Math.random()), (float)(w.height*Math.random()), 2, 2));
        }
        
        
        
        ParticleFilter filter = new ParticleFilter(5, w);
        
        
        Pose mystery = w.randomPose();
        JFrame f = new JFrame("Particles");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 800);
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(new FilterView(filter), BorderLayout.CENTER);
        f.setVisible(true);
        for (int i = 0; i < 500; ++i) {
            f.repaint();
            List<Double> scan = w.ranges(mystery, Pixie.sensorAngles);            
            filter.scoreAgainst(scan);

            System.out.println("best: " + filter.best);
            
            filter.improve(0.01);
            //filter.shake(0.000001);
            try {
                Thread.sleep(20L);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
//            mystery.rotate(2f);
//            filter.rotate(2f);
        }
        System.out.println("Revealed: " + mystery + " scores: " + w.ranges(mystery, Pixie.sensorAngles));
        System.out.println("Best guess: " + filter.best + " scores: " + w.ranges(filter.best.pose, Pixie.sensorAngles));
    }
}
