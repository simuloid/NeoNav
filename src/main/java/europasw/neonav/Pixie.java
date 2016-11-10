/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package europasw.neonav;

import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author srowe
 */
public class Pixie {
   Pose pose;
   double score;
   static Double[] sensorAngles = { Math.toRadians(60), 0.0, Math.toRadians(-60) };
   
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

    static Comparator<Pixie> comparePixies = new Comparator<Pixie>() {

       @Override
       public int compare(Pixie t, Pixie t1) {
            return t.score < t1.score ? -1 : (t.score > t1.score) ? 1 : 0;
       }
    };
}
