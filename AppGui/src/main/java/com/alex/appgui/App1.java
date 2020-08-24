/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alex.appgui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JFrame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author alexg
 */
public class App1 extends JFrame {
    private JFrame fIn , fOut, fCmd;
    private JLabel mousePosLab;
    private PointerInfo mousePos;
    private Point workingPoint;
    private Canvas canvasIn, canvasOut;
    private Range functionPoint;
    private JButton bc1, bc2, bc3;
    
    public App1 (){
        fIn = new JFrame("input");
        fOut = new JFrame("output");
        fCmd= new JFrame("comandi");
        bc1 = new JButton("Clear");
        bc2 = new JButton("xxx");
        bc3 = new JButton("yyy");
        JPanel panelCmd = new JPanel();
        fCmd.add(panelCmd);
        panelCmd.setLayout(new GridLayout(3,1));
        panelCmd.add(bc1);
        panelCmd.add(bc2);
        panelCmd.add(bc3);
        bc1.addActionListener(new ActionCmd());
        mousePosLab = new JLabel("> ");
        canvasIn = new CanvasIn();
        canvasOut = new CanvasOut();
        mousePos = MouseInfo.getPointerInfo();
        functionPoint = new Range(NomeRange.PARAMETRI_DELLA_FUNZIONE,EtichettaRange.FASE,0,EtichettaRange.AMPIEZZA,0);
        workingPoint = new Point();
        
        fIn.setSize(CostantiGrafiche.FinoutDIM);
        fOut.setSize(CostantiGrafiche.FinoutDIM);
        fIn.setLocation(CostantiGrafiche.FinPOS);
        fOut.setLocation(CostantiGrafiche.FoutPOS);
        fCmd.setLocation(CostantiGrafiche.CmdPOS);
        fCmd.setSize(CostantiGrafiche.CmdDIM);
        fCmd.setResizable(false);
        fIn.add(canvasIn,"Center");
        fIn.add(mousePosLab,"South");
        fOut.add(canvasOut);
        canvasIn.setSize(fIn.getSize());
        //canvasIn.setSize(fIn.get);
        canvasOut.setSize(fOut.getSize());
        canvasIn.setBackground(Color.white);
        canvasOut.setBackground(Color.gray);
        
        fIn.pack();
        //fOut.pack();
        System.out.print("\nMouse : " + mousePos.getLocation());
        
        MouseCanvasInListener mcin = new MouseCanvasInListener( mousePos  );
        canvasIn.addMouseMotionListener( mcin);
        canvasIn.addMouseListener(mcin);
        
        fIn.setVisible(true);
        fOut.setVisible(true);
        fCmd.setVisible(true);
        
    }
   
    public class ActionCmd implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(bc1)) 
                canvasOut.repaint();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class MouseCanvasInListener extends MouseAdapter {
        private boolean mouseIn;

        public MouseCanvasInListener(PointerInfo startingPos){
            if (   (startingPos.getLocation().x > CostantiGrafiche.FinPOS.x) &&
                    (startingPos.getLocation().y > CostantiGrafiche.FinPOS.y) &&
                    (startingPos.getLocation().x < (CostantiGrafiche.FinPOS.x + CostantiGrafiche.FinoutDIM.height)) &&
                    (startingPos.getLocation().y < (CostantiGrafiche.FinPOS.y + CostantiGrafiche.FinoutDIM.height))    )  {
           //if (startingPos.getLocation().x > CostantiGrafiche.FinPOS.x)  {
                mouseIn = true;
                System.out.print("\nMouse sulla finestra");
           
                System.out.print("\nx1 " + CostantiGrafiche.FinPOS.x);
                System.out.print("\ny1 " + CostantiGrafiche.FinPOS.y);
                System.out.print("\nx2 " + (CostantiGrafiche.FinPOS.x + CostantiGrafiche.FinoutDIM.height));
                System.out.print("\ny2 " + (CostantiGrafiche.FinPOS.y + CostantiGrafiche.FinoutDIM.height));
           }
            
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (mouseIn) {
                //trovo il raggio
                functionPoint.val2_double = Math.sqrt( Math.pow(workingPoint.x, 2)+Math.pow(workingPoint.y, 2)) ;
                //calcolo l'angolo
                if ((workingPoint.x > 0)&&(workingPoint.y > 0))
                functionPoint.val1_double = (360 * Math.asin(workingPoint.y / functionPoint.val2_double) ) / (2* Math.PI);
                else if ((workingPoint.x < 0)&&(workingPoint.y > 0))
                    functionPoint.val1_double =180 - (360 * Math.asin(workingPoint.y / functionPoint.val2_double) ) / (2* Math.PI);
                else if ((workingPoint.x < 0)&&(workingPoint.y < 0))
                    functionPoint.val1_double =180 + -1 *(360 * Math.asin(workingPoint.y / functionPoint.val2_double) ) / (2* Math.PI);
                else if ((workingPoint.x > 0)&&(workingPoint.y < 0))
                    functionPoint.val1_double =360 + (360 * Math.asin(workingPoint.y / functionPoint.val2_double) ) / (2* Math.PI);
                
                
                //Disegno sulla finestra di Input
                Graphics g = canvasIn.getGraphics();
                g.drawOval(canvasIn.getMousePosition().x - CostantiGrafiche.DOTDIM.val1_int, 
                        canvasIn.getMousePosition().y - CostantiGrafiche.DOTDIM.val1_int, 
                        CostantiGrafiche.DOTDIM.val2_int ,
                        CostantiGrafiche.DOTDIM.val2_int );
                mousePosLab.setText("> Rel " + workingPoint.x + "," + workingPoint.y +
                        "    Acquisito: " + functionPoint.val1_double + " , " + functionPoint.val2_double) ;

                //Disegno sulla finestra di Output
                Graphics h = canvasOut.getGraphics();
                //inizio codice per il disegno della funzione
                h.setColor(Color.blue);
                setAxes(canvasOut,h);
                drawFunction(canvasOut,h);
            }
        }


        public void drawFunction( Canvas canvas , Graphics h){
        double x0= CostantiGrafiche.DOMINIO_X.val1_double;
                double T_LIMITE = canvas.getBounds().width - CostantiGrafiche.GRAPH_OUT_SYSCO.x;
                CostantiGrafiche.TIME_INCREMENT = CostantiGrafiche.DOMINIO_X.val2_double / T_LIMITE;
                for (double t=0 ; t<= T_LIMITE ; t++){
                //for (double i= CostantiGrafiche.DOMINIO_X.val1_double ; i<= CostantiGrafiche.DOMINIO_X.val2_double ; i+=1) {
                    setPixel (canvasOut ,h,x0,t);
                    x0 += CostantiGrafiche.TIME_INCREMENT;
                    //System.out.print("\n punto" + i);
                }
        }

        
        public void setAxes( Canvas canvas , Graphics h){
            System.out.print("\n devo disegnare le scale sugli assi");
            //disegno le scale sugli assi in base al fattore di scala
        }
        
        public void setPixel(Canvas canvas , Graphics h , double x , double t){
            double y = functionPoint.val2_double * f(x);
            try { TimeUnit.MILLISECONDS.sleep(5); } catch (InterruptedException i ) {System.exit(0); }
            System.out.print("\nx= "+x +" ; y= " + y);
            h.drawLine((int)t + CostantiGrafiche.GRAPH_OUT_SYSCO.x,
                    (int)y + CostantiGrafiche.GRAPH_OUT_SYSCO.y,
                    (int)t +1 + CostantiGrafiche.GRAPH_OUT_SYSCO.x,
                    (int)y +1 + CostantiGrafiche.GRAPH_OUT_SYSCO.y  );
        }
        
        public double f(double x){
            return Math.sin(x);
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            mouseIn = true;
            System.out.print("\nmouse in");
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            mouseIn = false;
            System.out.print("\nmouse out");
        }
        
        @Override
        public void mouseMoved(MouseEvent e){
            
            workingPoint.x = mousePos.getLocation().x - fIn.getLocation().x - 6 - CostantiGrafiche.FinCENTRO.x;
            workingPoint.y = -1 * ( mousePos.getLocation().y - fIn.getLocation().y - 30 - CostantiGrafiche.FinCENTRO.y);
            if (mouseIn) {
                mousePos = MouseInfo.getPointerInfo();
                //mousePosLab.setText("> " + mousePos.getLocation().toString());
                mousePosLab.setText("> Rel " + workingPoint.x + "," + workingPoint.y ) ;
                //mousePosLab.repaint();
                //System.out.print("pippo");
            }
        }
        
    }

    
    /** 
     * creo la finestra di input con la grafica di base
     */
    public class CanvasIn extends Canvas {
        @Override
        public void paint(Graphics g){
            int dx = CostantiGrafiche.FinoutDIM.width / 6;
            int dy = CostantiGrafiche.FinoutDIM.height / 6;
            g.setColor(Color.gray);            
            g.fillOval(0, 0, 
                    CostantiGrafiche.FinoutDIM.width, CostantiGrafiche.FinoutDIM.height);
            g.setColor(Color.blue);            
            g.fillOval(0+dx , 0+dy , 
                    CostantiGrafiche.FinoutDIM.width -2*dx , CostantiGrafiche.FinoutDIM.height -2*dy);
            g.setColor(Color.red);            
            g.fillOval(0+2*dx, 0+2*dy, 
                    CostantiGrafiche.FinoutDIM.width -4*dx, CostantiGrafiche.FinoutDIM.height -4*dy);

            g.setColor(Color.black);
            g.drawLine(CostantiGrafiche.GRAPH_IN_SYSCO.x , 0, 
                    CostantiGrafiche.GRAPH_IN_SYSCO.x , CostantiGrafiche.FinoutDIM.height);
            
            g.drawLine(0, CostantiGrafiche.GRAPH_IN_SYSCO.y ,
                    CostantiGrafiche.FinoutDIM.width , CostantiGrafiche.GRAPH_IN_SYSCO.y );
            
        }
    }
    
    
    public class CanvasOut extends Canvas {
        @Override
        public void paint(Graphics g){
            
            //disegno degli assi cartesiani
            g.setColor(Color.black);
            g.drawLine(CostantiGrafiche.GRAPH_OUT_SYSCO.x , 0, 
                    CostantiGrafiche.GRAPH_OUT_SYSCO.x , CostantiGrafiche.FinoutDIM.height);
            
            g.drawLine(0, CostantiGrafiche.GRAPH_OUT_SYSCO.y ,
                    CostantiGrafiche.FinoutDIM.width , CostantiGrafiche.GRAPH_OUT_SYSCO.y );
            
        }
    }
    
}
