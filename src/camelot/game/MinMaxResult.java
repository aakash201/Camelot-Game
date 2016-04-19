/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

/**
 *
 * @author aakashtyagi
 */
public class MinMaxResult {
    
    Move move;
    double val;
    MinMaxResult()
    {
 
    }
    MinMaxResult(Move move, double val)
    {
        this.move = move;
        this.val = val;
    }
    
}
