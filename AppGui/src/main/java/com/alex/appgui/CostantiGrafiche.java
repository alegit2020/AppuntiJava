/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alex.appgui;

import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author alexg
 */
public class CostantiGrafiche {
    public static final Dimension MENUDIM = new Dimension (150,100); //la larghezza minima è definita dal titolo..120c.a
    public static final Point MENUPOS = new Point(10,10);

    public static final Point CmdPOS = new Point(10 , MENUPOS.y + MENUDIM.height + 10);
    public static final Dimension CmdDIM = new Dimension (MENUDIM.width ,200);
    public static final Dimension FinoutDIM = new Dimension (500 ,500);
    public static final Point FinPOS = new Point(MENUPOS.x + MENUDIM.width + 10 ,10);
    public static final Point FoutPOS = new Point(FinPOS.x + FinoutDIM.width + 50 , FinPOS.y);

    public static final Point FinCENTRO = new Point(CostantiGrafiche.FinoutDIM.width /2 , CostantiGrafiche.FinoutDIM.height / 2);
    //public static final Point DOTDIM = new Point ( 2,4); //NOTA ! non viene usato come punto ma come dimensione per centrare l'ovale sul cursore
    public static final Range DOTDIM = new Range(NomeRange.DOT , EtichettaRange.OFFSET , 2 , EtichettaRange.DIAMETRO, 4);
    
    public static final Point GRAPH_IN_SYSCO = new Point (FinoutDIM.width / 2 , FinoutDIM.height / 2 );
    public static final Point GRAPH_OUT_SYSCO = new Point (FinoutDIM.width / 6 , FinoutDIM.height / 2 );

    
    //  COSTANTI PER IL DISEGNO DELLA FUNZIONE
    public static Range DOMINIO_X = new Range(NomeRange.TEMPO , EtichettaRange.DA , 0.0 , EtichettaRange.A , 10.0);
    public static double TIME_INCREMENT = 1.0;
    public static long TIME_STEP = Math.round((DOMINIO_X.val2_double - DOMINIO_X.val1_double ) / TIME_INCREMENT);
}
