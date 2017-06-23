import java.util.ArrayList;
import java.util.Arrays;

public class Jackson extends Player{

    public Jackson(Space [] board, Colour colour)
    {
        super(board, colour);
    }


    /**
     * Gets move, looking ahead slightly for best possible move
     * @param rolls
     * @return
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
        Space[] temp = copy(board);
        for (Move move: possMoves)
        {
            run(move, temp);
            ArrayList<Integer> test = copy(rolls);
            test.remove(((Integer) Math.abs(move.getEnd() - move.getStart())));
            if (test.size() == 1) scores.add(getScore(move));
            else
            {
                scores.add(getScore(move)+getScore(getMove(test)));
            }
        }
        int topScoreIndex = 0;
        int topScore = 0;
        for (int i = 0; i<scores.size(); i++)
        {
            if (scores.get(i)>topScore)
            {
                topScore = scores.get(i);
                topScoreIndex = i;
            }
        }
        return possMoves.get(topScoreIndex);
    }

    /**
     * Copies an integer ArrayList
     * @param toCopy
     * @return
     */
    public ArrayList<Integer> copy(ArrayList<Integer> toCopy)
    {
        ArrayList<Integer> copy = new ArrayList<Integer>();
        for (Integer num: toCopy)
        {
            copy.add(num);
        }
        return copy;
    }

    /**
     * Copies a Space[]
     * @param board
     * @return
     */
    public Space[] copy(Space[] board)
    {
        Space[] temp = new Space[board.length];
        temp[0] = new EndSpace(((EndSpace)board[0]).getCaptured(), ((EndSpace)board[0]).getBearing(),((EndSpace)board[0]).getColour());
        for (int i = 1; i< board.length-1; i++)
        {
            temp[i] = new Space(board[i].getNumCounters(), board[i].getColour());
        }
        temp[board.length-1] = new EndSpace(((EndSpace)board[25]).getCaptured(), ((EndSpace)board[25]).getBearing(),((EndSpace)board[25]).getColour());
        return temp;
    }

    /**
     * Returns the score of the move
     * @param move
     * @return
     */
    public int getScore(Move move)
    {
        int type = 0;
        if (move.getType() == null)
        {
            return 0;
        }
        switch(move.getType())
        {
            case BEAROFF: type = 90;
                break;
            case CAPTURE: type = 50;
                break;
            case NORMAL: type = 20;
                break;
        }

        int protect;
        if (board[move.getStart()].getNumCounters() -1 == 0)
        {
            protect = 10;
        }
        else if (board[move.getStart()].getNumCounters() -1 == 1)
        {
            protect = 0;
        }
        else
        {
            protect = 5;
        }

        int score = type + protect;
        return score;
    }

    public String toString()
    {
        return "Jackson";
    }
}
