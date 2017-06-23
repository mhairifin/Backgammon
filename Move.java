import java.util.ArrayList;

public class Move {
    private int start;
    private int end;

    private Type type;

    private Player player;


    public Move(int start, int end, Player player)
    {
        this.start = start;
        this.end = end;
        this.player = player;
    }

    public int getStart()
    {
        return start;
    }

    public int getEnd()
    {
        return end;
    }

    public Type getType()
    {
        return type;
    }

    public boolean validate(ArrayList<Integer> diceRolls, Space[] board)
    {
        if (start == -1 && end == -1)
        {
            boolean valid = true;
            ArrayList<Move> moves = new ArrayList<Move>();
            for (Integer roll: diceRolls)
            {
                moves.addAll(player.getPossMoves(roll, board));
                if (moves.size() != 0)
                {
                    valid = false;
                }
            }
            if (!valid)
            {
                System.out.println("Possible moves that could be made");
                for (Move move: moves)
                {
                    System.out.println(move);
                }
            }
            return valid;
        }
        else if (start < 0 || start > 25 || end < 0 || end > 25)
        {
            return false;
        }

        Space startSpace = board[start];
        Space endSpace = board[end];
        if (startSpace.getNumCounters() == 0)
        {
            return false;
        }
        if (startSpace.getColour() != player.getColour() && !(startSpace instanceof EndSpace))
        {
            return false;
        }
        if (startSpace instanceof EndSpace)
        {
            if (((EndSpace)startSpace).getCaptured() == 0) {
                return false;
            }
            if (player.getColour() == startSpace.getColour())
            {
                return false;
            }
        }
        if (endSpace.getNumCounters() > 1 && endSpace.getColour() != player.getColour())
        {
            return false;
        }
        if (endSpace.getNumCounters() == 1 && endSpace.getColour() != player.getColour()) {
            type = Type.CAPTURE;
        }
        else if (end == 0 || end == 25)
        {
            type = Type.BEAROFF;
            if (!player.bearoffAllowed())
            {
                return false;
            }
        }
        else
        {
            type = Type.NORMAL;
        }

        if (!(start == 0 || start == 25) && player.hasCaptured())
        {
            return false;
        }

        int diff = start-end;
        diff = Math.abs(diff);
        boolean found = diceRolls.contains((Integer)diff);
        if (!found)
        {
            return false;
        }
        return player.rightWay(start, end);
    }

    public String toString()
    {
        if (start == -1 && end == -1)
        {
            return "No possible move";
        }
        else if (type == Type.BEAROFF)
        {
            return "Bearoff from " + start;
        }
        else if (start == 0 || start == 25)
        {
            return "Play captured piece to " + end;
        }
        else if (type == Type.CAPTURE)
        {
            return start + " to capture " + end;
        }
        else {
            return start + " to " + end;
        }
    }

}
