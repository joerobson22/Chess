public class Piece
{
    private String pieceName;
    private int pieceNum;
    private int colour;

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
}