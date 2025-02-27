/**
 * Individual squares of the chess board, manages what piece is on it
 * @author Joe Robson
 */
public class Square
{
    private Piece piece;
    private Rectangle rectangle;

    private String colour;

    private boolean selected = false;
    private boolean movable = false;
    private boolean isDoubleMovable = false;
    private boolean enPassantable = false;
    private int x;
    private int y;
    private int squareWidth;

    /**
     * constructor, takes the square's x and y coordinates to display visually, as well as its hex colour and square width
     * @param x x coord
     * @param y y coord
     * @param colour hex colour
     * @param squareWidth square width for rectangle
     */
    public Square(int x, int y, String colour, int squareWidth)
    {
        this.x = x;
        this.y = y;
        this.squareWidth = squareWidth;
        this.colour = colour;
        rectangle = new Rectangle(x, y, squareWidth, squareWidth, colour, 1);
    }

    //functionality

    /**
     * takes a game arena and adds its rectangle to it
     * @param arena game arena
     */
    public void addTo(GameArena arena)
    {
        arena.addRectangle(rectangle);
    }

    /**
     * when the square is clicked, change the value of selected to !selected and update the visuals
     */
    public void click()
    {
        selected = !selected;
        update();
    }

    /**
     * updates the visuals- red if selected, yellow if movable, ordinary if otherwise
     */
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

    //resets all boolean variables and resets its colour
    public void reset()
    {
        enPassantable = false;
        isDoubleMovable = false;
        movable = false;
        selected = false;
        rectangle.setColour(colour);
    }

    //accessors and mutators

    /**
     * sets the square's piece for reference
     * @param p piece
     */
    public void setPiece(Piece p)
    {
        piece = p;
    }

    /**
     * sets the square's piece's coordinates and square coordinates so it can update its position and information for calculating moves
     * @param squareX this square's x on the grid
     * @param squareY this square's y on the grid
     */
    public void setPieceCoordinates(int squareX, int squareY)
    {
        if(piece != null)
            piece.setCoordinates(x + (squareWidth / 2), y + (squareWidth / 2), squareX, squareY);
            if(piece.getPieceNum() == 0)
                piece.hasMoved();
    }

    /**
     * gets the piece currently on the square (null if no piece)
     * @return the piece i suppose
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * sets if the square can be moved on or not
     * @param movable IS IT MOVABLE!?!??!?! true or false dingus
     */
    public void setMovable(boolean movable)
    {
        this.movable = movable;
    }

    /**
     * gets the value of 'movable' to identify if a piece can move there or not
     * @return movable
     */
    public boolean isMovable()
    {
        return movable;
    }


    public void setDoubleMovable(boolean isDoubleMovable)
    {
        this.isDoubleMovable = isDoubleMovable;
    }


    public boolean isDoubleMovable()
    {
        return isDoubleMovable;
    }

    public boolean getEnPassantable()
    {
        return enPassantable;
    }

    public void setEnPassantable(boolean val)
    {
        if(val)
            System.out.printf("I AM EN PASSANTABLE\n");
        enPassantable = val;
    }
    
}