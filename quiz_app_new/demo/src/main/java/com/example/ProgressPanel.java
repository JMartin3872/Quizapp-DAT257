package com.example;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import javax.swing.JPanel;

public class ProgressPanel extends JPanel{
    int progress = 0;
    Color progressColor;
    int colorGrade;
    int targetProgress = 100; // Default value
    boolean isComplete = false;

    public ProgressPanel(){
        this.setBackground(new Color(80,100,230));
    }

    public void setTargetProgress(int target){
        this.targetProgress = target;
        repaint();
    }

    public void UpdateProgress(int progress_value){
        if (!isComplete){
            progress = progress_value;
            getColor();

            if (progress >= targetProgress){
                progress = targetProgress;
                isComplete = true;
            }
        }
        repaint();
    }

    public Color getColor(){
        colorGrade = (int)(0 + progress*5.1);   // 255/50=5.1
        if (colorGrade <= 255){
            progressColor = new Color(255, colorGrade,0);
        } else if (colorGrade > 255 && colorGrade < 510){
            progressColor = new Color(510 - colorGrade, 255,0);
        } else {
            progressColor = new Color(0, 0,255);
        }
        return progressColor;
    }

    @Override
    public void paint(Graphics g){  // Draws a circular progressbar
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(this.getWidth()/2, this.getHeight()/2);
        g2.rotate(Math.toRadians(270));
        Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
        Ellipse2D circle = new Ellipse2D.Float(0,0,90,90);
        arc.setFrameFromCenter(new Point(0,0), new Point(80,80));
        circle.setFrameFromCenter(new Point(0,0), new Point(65,65));
        arc.setAngleStart(1);
        arc.setAngleExtent(-progress*3.6);  // 360/100=3.6
        g2.setColor(progressColor);
        g2.draw(arc);
        g2.fill(arc);
        g2.setColor(new Color(80,100,230));
        g2.draw(circle);
        g2.fill(circle);
        g2.setColor(progressColor);
        g2.rotate(Math.toRadians(90));
        g.setFont(new Font("Verdana", Font.PLAIN, 30));
        FontMetrics fm=g2.getFontMetrics();
        Rectangle2D r=fm.getStringBounds(progress+"%", g);
        int x = (0 - (int)r.getWidth())/2;
        int y = (0 -(int)r.getHeight())/2 + fm.getAscent();
        g2.drawString(progress+"%", x,y);

    }
    
}

