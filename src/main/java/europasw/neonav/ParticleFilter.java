/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Steve
 */
public class ParticleFilter {
    final List<Pixie> particles;
    Pixie best;
    private final int size;
    private final World world;
    public ParticleFilter(int size, World world) {
        particles = new ArrayList<>(size);
        this.size = size;
        this.world = world;
        while (particles.size() < size) {
            Pixie p = new Pixie();
            p.pose = world.randomPose();
            boolean add = particles.add(p);
        }
    }
    
    public void move(double distance) {
        for (Pixie p: particles) {
            p.pose.forward(distance);
        }
    }
    // Nudge everybody toward best
    public void improve() {
        final double fraction = 0.01;
        for (Pixie p: particles) {
            List<Double> diff = Pose.difference(best.pose, p.pose);
            p.pose.x += diff.get(0) * fraction;
            p.pose.y += diff.get(1) * fraction;
            p.pose.heading = World.angleSum(p.pose.heading, diff.get(2) * fraction);
        }
    }
    public void scoreAgainst(List<Double> measurement) {
        double bestScore = Double.POSITIVE_INFINITY;
        for (Pixie p: particles) {
            List<Double> sample = world.ranges(p.pose, Pixie.sensorAngles);
            p.score = Main.distance(measurement, sample);
            if (p.score < bestScore) {
                bestScore = p.score;
                best = p;
                // System.out.println(p);
            }
        }
    }
}
