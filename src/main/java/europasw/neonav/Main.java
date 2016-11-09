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
public class Main {
    public static void main(String[] args) {
        World w = new World();
        for (float a = -180f; a <= 180f; a += 1f) {
            Pose p = new Pose(0, 0, a);
            w.ranges(p, Math.toRadians(120), 0.0, Math.toRadians(-120));
            System.out.println();
        }
    }
}
