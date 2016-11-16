/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package europasw.neonav;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author srowe
 */
public class Pixie {
   Pose pose;
   static Random dice = new Random();
   double score;
   int age = 0;
//   static Double[] sensorAngles = { Math.toRadians(60), 0.0, Math.toRadians(-60) };
   static Double[] sensorAngles = { 0.0, Math.toRadians(180) };
   
   static {
       int n = 36;
       sensorAngles = new Double[n];
       double da = 360.0 / n;
       for (int i = 0; i < n; ++i) {
           sensorAngles[i] = i * da;
       }
   }

   Pixie() {
   }
   Pixie(Pixie p) {
      this.pose = new Pose(p.pose);
      this.score = p.score;
   }
   Pixie(Pose pose) {
      double vx = dice.nextGaussian();
      double vy = dice.nextGaussian();
      double va = dice.nextGaussian();
      this.pose = new Pose(pose.x + vx, pose.y + vy, pose.heading + va);
   }
   @Override
   public String toString() {
      return pose.toString() + String.format(" score %.2f", score);
   }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pixie other = (Pixie) obj;
        return this.pose.equals(other.pose);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.pose);
        return hash;
    }

    static Comparator<Pixie> compareScores = new Comparator<Pixie>() {

       @Override
       public int compare(Pixie t, Pixie t1) {
            return t.score < t1.score ? -1 : (t.score > t1.score) ? 1 : 0;
       }
    };
}
