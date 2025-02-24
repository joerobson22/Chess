public class Piece
{
    protected String[] colours = {"White", "Black"};

    protected String pieceName;
    protected int pieceNum;
    protected int colour;

    protected Ball[] balls = new Ball[5];
    protected Rectangle[] rectangles = new Rectangle[5];
    protected int ballNum;
    protected int rectangleNum;

    protected int x;
    protected int y;

    
    public Piece(String pieceName, int pieceNum, int colour)
    {
        this.pieceName = pieceName;
        this.pieceNum = pieceNum;
        this.colour = colour;
    }
    

    public String getPieceName()
    {
        return pieceName;
    }

    public int getPieceNum()
    {
        return pieceNum;
    }

    public int getColour()
    {
        return colour;
    }

    public boolean isSameColour(int col)
    {
        return(col == colour);
    }

    //inheritance- find how to make children of piece
    //have private balls and rectangles, and unique addTo() methods that 'board' should call when it's being displayed
    //each square has one piece, so this piece just needs to be able to be transferred around between squares
    public static class Pawn extends Piece
    {
        public Pawn(int colour)
        {
            super("Pawn", 0, colour);
            ballNum = 1;
            rectangleNum = 2;
            Ball head = new Ball(x, y - 10, 10, colours[colour]);
            balls[0] = head;
        }

        public void addTo(GameArena arena)
        {
            for(int i = 0; i < ballNum; i++)
            {
                arena.addBall(balls[i]);
            }

            for(int i = 0; i < rectangleNum; i++)
            {
                arena.addRectangle(rectangles[i]);
            }
        }
    }

}