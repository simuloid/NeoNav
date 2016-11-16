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
        for (int i = 0; i < w.width/30; ++i) {
            for (int j = 0; j < w.height/30; ++j) {
                w.addObject(new Rectangle2D.Float(
                        (float) (i*30+15*Dice.get().nextGaussian()), 
                        (float)(j*30+15*Dice.get().nextGaussian()), 2, 2));
            }
        }
        
        
        
        ParticleFilter filter = new ParticleFilter(300, w);
        
        
        Pose mystery = w.randomPose();
        JFrame f = new JFrame("Particles");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 800);
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(new FilterView(filter), BorderLayout.CENTER);
        f.setVisible(true);
        double bestScore = 0;
        double goodEnough = 0.36;
        int ic = 0;
        do {
            List<Double> scan = w.ranges(mystery, Pixie.sensorAngles);            
            
            filter.improve(0.01, 0.8, 0.5, scan);
            
            bestScore = filter.scoreAgainst(filter.best.pose, scan);
            if (bestScore > goodEnough) {
                System.out.println("Best score is high: " + bestScore);
                break;
            }
            f.repaint();
            System.out.println("                   Mystery: " + mystery);
            System.out.println("best: " + bestScore + "   " + filter.best);
            Pose bestAvg = filter.localAverage(filter.best.pose, 1.0);
            double bestAvgScore = filter.scoreAgainst(bestAvg, scan);
            System.out.println("local: " + bestAvgScore + "   " + bestAvg);
            //filter.shake(0.000001);
//            try {
//                Thread.sleep(100L);
//            } catch (Exception ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }

            if (Math.random() < 0.2) { // 20% chance of moving, of which
                if (Math.random() < 0.1) { // 10% of moves are turns
                   float turn = (float) (Math.random() * 2 - 1);
                   mystery.rotate(turn);
                   filter.rotate(turn);
                }
                else {
                   // Straight movement, but not very much.
                   double distance = Math.random() * 2 - 1;
                   mystery.forward(distance);
                   if (!w.isValidPose(mystery)) {
                      mystery.forward(-distance);
                   }
                   else {
                      filter.move(distance);
                   }
                }
            }
        
            ++ic;
        } while (goodEnough > bestScore);

        System.out.format("Converged after %d iterations.\n", ic);
        System.out.println("Revealed: " + mystery + " ranges: " + w.ranges(mystery, Pixie.sensorAngles));
      Pixie best = filter.best;
      List<Double> errors = w.fractionalError(Pose.difference(mystery, best.pose));
      System.out.println("best: " + best + " err: " + errors);
//        System.out.println("Best guess: " + best + " scores: " + w.ranges(best.pose, Pixie.sensorAngles));
//        Pose avg = filter.getAveragePose();
//      System.out.println("avg: " + avg + " err: " + Pose.difference(mystery, avg));
//        System.out.println("Avg scores: " + w.ranges(avg, Pixie.sensorAngles));
    }
}
