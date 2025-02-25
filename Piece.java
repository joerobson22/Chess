public class Piece
{
    protected String[] colours = {"White", "Black"};

    protected String pieceName;
    protected int pieceNum;
    protected int colour;
    protected boolean isTurn;

    protected Ball[] balls = new Ball[5];
    protected Rectangle[] rectangles = new Rectangle[5];
    protected int ballNum = 0;
    protected int rectangleNum = 0;

    protected int squareX;
    protected int squareY;
    protected int movDir;
    protected int x;
    protected int y;

    
    public Piece(String pieceName, int pieceNum, int colour, int x, int y, int squareX, int squareY)
    {
        this.pieceName = pieceName;
        this.pieceNum = pieceNum;
        this.colour = colour;
        isTurn = colour == 0;
        this.x = x;
        this.y = y;
        this.squareX = squareX;
        this.squareY = squareY;
    }

    public void setCoordinates(int x, int y, int squareX, int squareY)
    {
        this.x = x;
        this.y = y;
        this.squareX = squareX;
        this.squareY = squareY;
        updateVisuals();
    }

    public void updateVisuals()
    {
        
    }

    public boolean isTurn()
    {
        return this.isTurn;
    }

    public void setTurn(boolean turn)
    {
        this.isTurn = turn;
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

    public void removeFrom(GameArena arena)
    {
        for(int i = 0; i < ballNum; i++)
        {
            arena.removeBall(balls[i]);
        }

        for(int i = 0; i < rectangleNum; i++)
        {
            arena.removeRectangle(rectangles[i]);
        }
    }

    protected boolean inBounds(int x, int y)
    {
        return(x >= 0 && x < 8 && y >= 0 && y < 8);
    }

    protected int canMoveThere(Square[][] board, int destX, int destY)
    {
        if(board[destX][destY].getPiece() == null)
        {
            return 1;
        }
        else if(!board[destX][destY].getPiece().isSameColour(colour))
        {
            return 2;
        }
        return 0;
    }

    public int[][] getMoveSquares(Square[][] board)
    {
        int[][] moves = new int[8][8];
        return moves;
    }

    public void hasMoved()
    {

    }

    //each square has one piece, so this piece just needs to be able to be transferred around between squares
    public static class Pawn extends Piece
    {
        private boolean hasMoved = false;
        public Pawn(int colour, int x, int y, int squareX, int squareY)
        {   
            //call super constructor
            super("Pawn", 0, colour, x, y, squareX, squareY);

            //assign attributes unique to the pawn
            ballNum = 1;
            rectangleNum = 2;
            //create new circle and rectangles and add to the arrays
            Ball head = new Ball(0, 0, 20, colours[colour], 2);
            Rectangle body = new Rectangle(0, 0, 10, 25, colours[colour], 2);
            Rectangle base = new Rectangle(0, 0, 20, 10, colours[colour], 2);
            balls[0] = head;
            rectangles[0] = body;
            rectangles[1] = base;

            //set movement direction
            if(colour == 0)
            {
                movDir = 1;
            }
            else
            {
                movDir = -1;
            }

            //update the visuals
            updateVisuals();
        }

        @Override public int[][] getMoveSquares(Square[][] board)
        {
            int[][] moves = new int[8][8];
            //initialise
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    moves[i][j] = 0;
                }
            }

            //look at squares nearby
            int destX = squareX;
            int destY = squareY + (1 * movDir);
            //look one ahead
            if(inBounds(destX, destY))
                if(canMoveThere(board, destX, destY) == 1)
                    moves[destX][destY] = canMoveThere(board, destX, destY);
                else
                moves[destX][destY] = 0;

            //if hasn't moved, look 2 ahead
            if(!hasMoved)
            {
                destY += 1 * movDir;
                if(canMoveThere(board, destX, destY) == 1)
                    moves[destX][destY] = canMoveThere(board, destX, destY);
                else
                    moves[destX][destY] = 0;
            }

            //look for captures diagonally
            destX -= 1;
            destY = squareY + (1 * movDir);
            if(inBounds(destX, destY))
                if(canMoveThere(board, destX, destY) == 2)
                    moves[destX][destY] = 2;
            
            destX += 2;
            if(inBounds(destX, destY))
                if(canMoveThere(board, destX, destY) == 2)
                    moves[destX][destY] = 2;

            //return 2d array of moves that can occur
            return moves;
        }

        @Override public void updateVisuals()
        {
            balls[0].setXPosition(x);
            balls[0].setYPosition(y - 5);

            rectangles[0].setXPosition(x - 5);
            rectangles[0].setYPosition(y);

            rectangles[1].setXPosition(x - 10);
            rectangles[1].setYPosition(y + 25);
        }

        @Override public void hasMoved()
        {
            hasMoved = true;
        }
    }

}