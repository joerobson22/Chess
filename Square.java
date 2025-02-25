public class Square
{
    private Piece piece;
    private Rectangle rectangle;

    private String colour;

    private boolean selected = false;
    private boolean movable = false;
    private int x;
    private int y;
    private int squareWidth;

    public Square(int x, int y, String colour, int squareWidth)
    {
        this.x = x;
        this.y = y;
        this.squareWidth = squareWidth;
        this.colour = colour;
        rectangle = new Rectangle(x, y, squareWidth, squareWidth, colour, 1);
    }

    public void addTo(GameArena arena)
    {
        arena.addRectangle(rectangle);
    }

    public void setPiece(Piece p)
    {
        piece = p;
    }

    public void setPieceCoordinates(int squareX, int squareY)
    {
        if(piece != null)
            piece.setCoordinates(x + (squareWidth / 2), y + (squareWidth / 2), squareX, squareY);
            if(piece.getPieceNum() == 0)
                piece.hasMoved();
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setMovable(boolean movable)
    {
        this.movable = movable;
    }

    public boolean isMovable()
    {
        return movable;
    }

    public void click()
    {
        selected = !selected;
        update();
    }

    public void update()
    {
        if(selected)
        {
            rectangle.setColour("red");
        }
        else if(movable)
        {
            rectangle.setColour("yellow");
        }
        else
        {
            reset();
        }
    }

    public void reset()
    {
        movable = false;
        selected = false;
        rectangle.setColour(colour);
    }
}