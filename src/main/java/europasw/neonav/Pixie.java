/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package europasw.neonav;

/**
 *
 * @author srowe
 */
public class Pixie {
   Pose pose;
   double score;
   
   @Override
   public String toString() {
      return pose.toString() + String.format(" score %.2f", score);
   }
}
