
public class EndSpace extends Space {

    private int numCaptured = 0;
    private int numBearing = 0;

    public EndSpace(Colour colour)
    {
        super(0, colour);
    }

    public int getCaptured()
    {
        return numCaptured;
    }

    public EndSpace(int cap, int bear, Colour colour)
    {
        super(cap+bear, colour);
        numCaptured = cap;
        numBearing = bear;
    }

    public int getBearing() {
        return numBearing;
    }

    public void addCounter(Type type)
    {
        if (type == Type.CAPTURE)
        {
            numCaptured++;
        }
        if (type == Type.BEAROFF)
        {
            numBearing++;
        }
        addCounter();
    }

    public void removeCaptured()
    {
        numCaptured --;
    }

    public String toString(){
        return Integer.toString(numCaptured) + " captured";
    }




}
