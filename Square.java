public class Square
{
    private Piece piece;
    private Rectangle rectangle;

    private String colour;

    private boolean selected = false;
    private boolean movable = false;

    public Square(int x, int y, String colour, int squareWidth)
    {
        this.colour = colour;
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

    public void click()
    {
        selected = !selected;

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
        selected = false;
        rectangle.setColour(colour);
    }
}