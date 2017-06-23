import java.util.ArrayList;

public class Jax extends Jackson {

    public Jax(Space[] board, Colour colour) {
        super(board, colour);
    }

    /**
     * Returns the best move from available rolls
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
        for (Move move: possMoves)
        {
            scores.add(getScore(move));
        }
        ArrayList<Integer> topIndices = new ArrayList<Integer>();
        int topScore = 0;
        for (int i = 0; i<scores.size(); i++)
        {
            if (scores.get(i)>topScore)
            {
                topScore = scores.get(i);
                topIndices.clear();
                topIndices.add(i);
            }
            else if (scores.get(i) == topScore)
            {
                topIndices.add(i);
            }
        }
        int random = (int) (topIndices.size() * Math.random());
        int topScoreIndex = topIndices.get(random);
        return possMoves.get(topScoreIndex);
    }

    /**
     * Evaluates a move and gives it a score
     * @param move
     * @return
     */
    public int getScore(Move move) {
        int type = 0;
        if (move.getType() == null) {
            return 0;
        }
        switch (move.getType()) {
            case BEAROFF:
                type = 90;
                break;
            case CAPTURE:
                type = 50;
                break;
            case NORMAL:
                type = 20;
                break;
        }

        int protect; //Prioritizes not leaving counters alone
        if (board[move.getStart()].getNumCounters() - 1 == 0) {
            protect = 10;
        } else if (board[move.getStart()].getNumCounters() - 1 == 1) {
            protect = 0;
        } else {
            protect = 5;
        }

        int future = 0;
        Space[] temp = copy(board);
        run(move, temp);
        future = evaluateBoard(temp);

        int score = type + protect + future;
        return score;

    }

    /**
     * Evaluates a board as to how good it is
     * @param temp
     * @return
     */
    public int evaluateBoard(Space[] temp)
    {
        int total = 360;
        for (int i = 1; i<temp.length-1; i++)
        {
            if (board[i].getColour() == colour)
            {
                total -= Math.abs(colour.getHome());
            }
        }
        int num = (total/360)*10;
        return num;
    }

    public String toString()
    {
        return "Jax";
    }
}
