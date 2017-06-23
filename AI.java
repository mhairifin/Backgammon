import java.util.*;

public class AI extends Player {

    public AI(Space [] board, Colour colour)
    {
        super(board, colour);
    }

    /**
     * Gets a move, based on the move with the best score
     * @param rolls the dice rolls that can be used
     * @return the best Move
     */
    public Move getMove(ArrayList<Integer> rolls)
    {
    	ArrayList<Move> possMoves = new ArrayList<Move>();
        for (Integer roll: rolls)
        {
            possMoves.addAll(getPossMoves(roll, board));
        }

        if (possMoves.size() == 0)
        {
            return (new Move(-1, -1, this));
        }

        ArrayList<Integer> scores = new ArrayList<Integer>();
        for (Move move: possMoves)
        {
            scores.add(getScore(move));
        }

        ArrayList<Integer> topIndices = new ArrayList<Integer>();
        int topScore = 0;
        for (int i = 0; i<scores.size(); i++) //Sorts for move with the best score
        {
        	if (scores.get(i)>topScore)
        	{
        		topScore = scores.get(i);
                topIndices.clear(); //clear the list of topIndices, so it only has idnices of top scorers
                topIndices.add(i);
        	}
            else if (scores.get(i) == topScore)
            {
                topIndices.add(i);
            }
        }
        int random = (int) (topIndices.size() * Math.random());
        int topScoreIndex = topIndices.get(random); //picks randomly from the moves with equally high scores
        return possMoves.get(topScoreIndex);
    }

    /**
     * Evaluates a move and gives it a score
     * @param move the move to be evaluated
     * @return the integer score
     */
    public int getScore(Move move)
    {
        switch(move.getType())
        {
            case BEAROFF: return 3;
            case CAPTURE: return 2;
            default: return 1;
        }
    }

    public String toString()
    {
        return "AI";
    }
}
