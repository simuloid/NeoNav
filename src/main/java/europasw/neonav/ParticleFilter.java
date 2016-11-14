/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.util.ArrayList;
import java.util.Collections;
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
    final int size;
    final World world;
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
    
    public void update(double dt) {
        for (Pixie p: particles) {
            p.pose.update(dt);
        }
    }
    
    public void shake(double dt) {
        for (Pixie p: particles) {
            p.pose.shake(dt);
        }
    }
    
    public void move(double distance) {
        for (Pixie p: particles) {
            p.pose.forward(distance);
        }
    }
    
    public void rotate(float degrees) {
        for (Pixie p: particles) {
            p.pose.rotate(degrees);
        }
    }

    public void rotate(double radians) {
        for (Pixie p: particles) {
            p.pose.rotate(radians);
        }
    }

    public Pixie weightedSample() {
       double roll = Math.random();
       for (Pixie p: particles) {
          if (roll < p.score) {
             return p;
          }
          roll -= p.score;
       }
       return null;
    }
    public List<Pixie> weightedSamples(int howMany) {
       List<Pixie> rc = new ArrayList<>(howMany);
       for (int i = 0; i < howMany; ++i) {
          rc.add(weightedSample());
       }
       return rc;
    }
    public void resample(double replaceFraction) {
        synchronized (particles) {
            Collections.sort(particles, Pixie.compareScores);
            Collections.reverse(particles);
        }
        int numToReplace = (int)(particles.size() * replaceFraction);
        System.out.println("NumToReplace: " + numToReplace);
        List<Pixie> newGuys = new ArrayList<>(particles.size());
        for (int i = 0; i < particles.size(); ++i) {
           Pixie p = null;
           if (i < numToReplace) {
              p = new Pixie(this.weightedSample().pose);
//              p = new Pixie(world.randomPose());
           }
           else {
              p = new Pixie(particles.get(i));
           }
           newGuys.add(p);
        }
        synchronized (particles) {
           particles.clear();
           particles.addAll(newGuys);
        }
    }

    Pixie localBest(Pixie dude) {
        Pixie best = null;
        for (Pixie p: particles) {
            if (dude.pose.distance(p.pose) < world.width*0.1) {
                if (best == null || best.score < p.score) {
                    best = p;
                }
            }
        }
        return best;
    }
    
    public void improve() {
        improve(0.01);
    }

    public Pose getAveragePose() {
       double x = 0;
       double y = 0;
       double dx = 0;
       double dy = 0;
       for (Pixie p: particles) {
          x += p.pose.x;
          y += p.pose.y;
          dx += Math.cos(p.pose.heading);
          dy += Math.sin(p.pose.heading);
       }
       x /= particles.size();
       y /= particles.size();
       double a = Math.atan2(dy/particles.size(), dx/particles.size());
       return new Pose(x, y, a);
    }
    // Nudge everybody toward best
    public void improve(double fraction) {
        resample(1.0);
        for (Pixie p: particles) {
            p.pose.updateToward(best.pose, fraction*0.1);
            Pixie b = localBest(p);
            if (b != null) {
                p.pose.updateToward(b.pose, fraction);
            }
        }
    }
    public void scoreAgainst(List<Double> measurement) {
        double bestScore = 0;
        double t = 0;
        for (Pixie p: particles) {
            List<Double> sample = world.ranges(p.pose, Pixie.sensorAngles);
            p.score = Main.distance(sample, measurement);
            p.score = Math.exp(-p.score);
            //System.out.println("score: " + p.score);
            t += p.score;
            if (p.score > bestScore) {
                bestScore = p.score;
                best = p;
            }
        }
        for (Pixie p: particles) {
           p.score = p.score/t;
        }
        synchronized (particles) {
            Collections.sort(particles, Pixie.compareScores);
            Collections.reverse(particles);
        }
        System.out.println(particles);
    }
}
