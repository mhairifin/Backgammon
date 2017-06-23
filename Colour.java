
public enum Colour {
    RED, WHITE;

    private Colour opp;

    static
    {
        RED.opp = WHITE;
        WHITE.opp = RED;
    }

    /**
     * Gets the home board number for this particular colour
     * @return
     */
    public int getHome()
    {
        if (this == RED)
        {
            return 25;
        }
        else
        {
            return 0;
        }
    }

    /*
    returns the opposite colour
     */
    public Colour getOpp()
    {
        return opp;
    }

}
