public class Square
{
    private Piece piece;
    private Rectangle rectangle;

    public Square(int x, int y, String colour, int squareWidth)
    {
        rectangle = new Rectangle(x, y, squareWidth, squareWidth, colour);
    }

    public void addTo(GameArena arena)
    {
        arena.addRectangle(rectangle);
    }

    public void setPiece(Piece p)
    {
        piece = p;
    }

    public Piece getPiece()
    {
        return piece;
    }
}