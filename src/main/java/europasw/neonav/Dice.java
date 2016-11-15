/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package europasw.neonav;

import java.util.Random;

/**
 *
 * @author Steve
 */
public class Dice extends Random {
    private Dice() {
    }
    private static final Dice instance = new Dice();
    public static final Dice get() {
        return instance;
    }
}
