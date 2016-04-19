/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

import java.util.ArrayList;

/**
 *
 * @author aakashtyagi
 */
public class MiniMax {
    static double INF = 1000000000;
    
    public static MinMaxResult alphaBetaMin(double alpha,double beta,int depth, CamelotGame cg){
        cg.cnt++;
        int i, j;
        double score;
        if (depth == 0) {
            return new MinMaxResult(null, cg.getBoundingValue());
        }
        MinMaxResult res = new MinMaxResult(null, INF);
        ArrayList<Move> moveList = new ArrayList<>();
        Piece piece;
        //For all valid moves
        for(i=1; i<=16; i++){
            for(j=1; j<=12; j++){
                if(null != (piece = cg.getPiece(i, j)))
                {
                    if(piece.color == 1)
                    Merge(moveList, piece.getAllMoves(cg));
                    //moveList.addAll(piece.getAllMoves(cg));
                }
            }
        }
        
        for(Move move : moveList)
        {
            //CamelotGame prevState = new CamelotGame(cg);
            ArrayList<Piece> deadPieces = new ArrayList<Piece>();
            deadPieces = cg.singleMove(move);            
            MinMaxResult tempres = alphaBetaMax(alpha,beta,depth-1, cg);
            
            if(tempres.val <= alpha)
            {
                res.move = move;
                res.val = alpha;
                cg.revertMove(move,deadPieces);
                return res;
            }
            if(tempres.val < beta)
            {
                beta = tempres.val;
                res.val = beta;
                res.move = move;
            }
            cg.revertMove(move,deadPieces);
        }
           
        return res;
    }
 
    public static MinMaxResult alphaBetaMax(double alpha,double beta, int depth, CamelotGame cg){
        int i, j;
        cg.cnt++;
        if (depth == 0){
            return new MinMaxResult(null, cg.getBoundingValue());
        }
        
        MinMaxResult res = new MinMaxResult(null, -INF);
        ArrayList<Move> moveList = new ArrayList<>();
        Piece piece;
        //For all valid moves
        for(i=1; i<=16; i++){
            for(j=1; j<=12; j++){
                if(null != (piece = cg.getPiece(i, j)))
                {
                    if(piece.color == 0)
                    Merge(moveList, piece.getAllMoves(cg));
                }
            }
        }
        //if(depth == 1)
        System.out.println("MoveSize" + moveList.size());
        
        // Applying Max algo on obtained Moves
        
        for(Move move : moveList)
        {
            ArrayList<Piece> deadPieces = new ArrayList<Piece>();
            deadPieces = cg.singleMove(move); 
            //deadPieces = cg.singleMove(move);            
            MinMaxResult tempres = alphaBetaMin(alpha,beta,depth-1, cg);
            
            if(tempres.val >= beta)
            {
                res.move = move;res.val = beta;
                cg.revertMove(move,deadPieces);
                return res;
            }
            if(tempres.val > alpha)
            {
                alpha = tempres.val;
                res.val = alpha;
                res.move = move;
            }
            cg.revertMove(move,deadPieces);
        }
        
        return res;
    }
    
    public static MinMaxResult Maxi(int depth, CamelotGame cg){
        return (alphaBetaMax(-INF,INF,depth,cg));
    }
    public static void Merge(ArrayList<Move> moveList, ArrayList<Move> pieceList)
    {
        moveList.addAll(pieceList);
    }
    String getHash(CamelotGame cg)
    {
        String hashValue= new String();
        return hashValue;
    }
    
}
