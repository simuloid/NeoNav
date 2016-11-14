/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import javax.swing.JComponent;

/**
 *
 * @author Steve
 */
public class FilterView extends JComponent {
    private final ParticleFilter filter;
    final float botSize = 1f;
    Shape body;
    Shape nose;
    public FilterView(ParticleFilter filter) {
        this.filter = filter;
        body = new Ellipse2D.Float(0f-botSize/2f, 0.f-botSize/2f, botSize, botSize);
        nose = new Line2D.Float(0.0f, 0.00f, botSize/2f, 0.0f);
    }
    
    public void drawPixie(Graphics2D g2, Pixie p, Color bodyColor, Color noseColor) {
        g2.translate(p.pose.x, p.pose.y);
        g2.rotate(p.pose.heading);
        g2.setColor(bodyColor);
        g2.draw(body);
        g2.setColor(noseColor);
        g2.draw(nose);
        g2.rotate(-p.pose.heading);
        g2.translate(-p.pose.x, -p.pose.y);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double scaleX = 1.0 * getWidth() / filter.world.width;
        double scaleY = 1.0 * getHeight() / filter.world.height;
        g2.setTransform(AffineTransform.getScaleInstance(scaleX, scaleY));
        g2.setStroke(new BasicStroke(0.001f));
        g2.setColor(Color.RED);
        for (Shape s: filter.world.objects) {
            g2.draw(s);
        }
        synchronized (filter.particles) {
            for (Pixie p: filter.particles) {
                if (p == filter.best) {
                    drawPixie(g2, p, Color.GREEN, Color.BLUE);
                }
                else {
                    drawPixie(g2, p, Color.BLACK, Color.BLUE);
                }
            }
        }
    }
    public void paint2(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double scaleX = 1.0 * getWidth() / filter.world.width;
        double scaleY = 1.0 * getHeight() / filter.world.height;
        g2.setTransform(AffineTransform.getScaleInstance(scaleX, scaleY));
        g2.setStroke(new BasicStroke(0.001f));
        g2.setColor(Color.RED);
        for (Shape s: filter.world.objects) {
            g2.draw(s);
        }
        synchronized (filter.particles) {
            for (Pixie p: filter.particles) {
                if (p == filter.best) {
                    drawPixie(g2, p, Color.GREEN, Color.BLUE);
                }
                else {
                    drawPixie(g2, p, Color.BLACK, Color.BLUE);
                }
            }
        }
    }
}
