import java.util.ArrayList;

public class Harvey extends AI {
    public Harvey(Space [] board, Colour colour)
    {
        super(board, colour);
    }

    public int getScore(Move move)
    {
        int type = 0;
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
        return "Harvey";
    }
}
