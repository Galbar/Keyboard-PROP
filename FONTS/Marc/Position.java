public class Position {
	public Float x;
	public Float y;
	
	/*
        Pre: -
	Post: Es crea una nova instància.
        */
	public Position(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Position clone()
    {
    	return new Position(this.x, this.y);
    }
}

