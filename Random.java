import java.util.ArrayList;

public class Random extends Player {
    public Random(Space [] board, Colour colour)
    {
        super(board, colour);
    }

    /**
     * Gets random move using rolls
     * @param rolls
     * @return
     */
    public Move getMove(ArrayList<Integer> rolls)
    {
        ArrayList<Move> possMoves = new ArrayList<Move>();
        for (int roll: rolls)
        {
            possMoves.addAll(getPossMoves(roll, board));
        }
        if (possMoves.size() != 0) {
            int random = (int) (possMoves.size() * Math.random());
            return possMoves.get(random);
        }
        else
        {
            return new Move(-1, -1, this);
        }
    }

    public String toString()
    {
        return "Random";
    }
}
