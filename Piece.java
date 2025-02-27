/**
 * A single piece, with multiple children that inherit from it and behave different
 * @author Joe Robson
 */
public class Piece
{
    protected String[] colours = {"Black", "White"};

    protected String pieceName;
    protected int pieceNum;
    protected int colour;
    protected boolean isTurn;

    protected Ball[] balls = new Ball[6];
    protected Rectangle[] rectangles = new Rectangle[6];
    protected int ballNum = 0;
    protected int rectangleNum = 0;

    protected int squareX;
    protected int squareY;
    protected int movDir;
    protected int x;
    protected int y;
    protected int[] moveSetX;
    protected int[] moveSetY;
    protected int maxMoves;

    /**
     * Constructor
     * @param pieceName name of the piece (pawn, rook, knight etc)
     * @param pieceNum piece number for further identification
     * @param colour piece colour to idenfity what team it is on
     * @param x piece's x coordinate, used for displaying it in the gamearena
     * @param y piece's y coordinate, used for displaying it in the gamearena
     * @param squareX piece's initial square X
     * @param squareY piece's initial square Y
     */
    public Piece(String pieceName, int pieceNum, int colour, int x, int y, int squareX, int squareY)
    {
        this.pieceName = pieceName;
        this.pieceNum = pieceNum;
        this.colour = colour;
        isTurn = colour == 1;
        this.x = x;
        this.y = y;
        this.squareX = squareX;
        this.squareY = squareY;
    }

    //functionality
    public void updateVisuals()
    {
        
    }

    /**
     * takes a game arena and adds all balls and rectangles to it
     * @param arena
     */
    public void addTo(GameArena arena)
    {
        //System.out.printf("adding to arena\n");
        for(int i = 0; i < ballNum; i++)
        {
            arena.addBall(balls[i]);
        }

        for(int i = 0; i < rectangleNum; i++)
        {
            arena.addRectangle(rectangles[i]);
        }
    }

    /**
     * takes a game arena and removes all balls and rectangles from it
     * @param arena
     */
    public void removeFrom(GameArena arena)
    {
        //System.out.printf("removing from arena\n");
        for(int i = 0; i < ballNum; i++)
        {
            arena.removeBall(balls[i]);
        }

        for(int i = 0; i < rectangleNum; i++)
        {
            arena.removeRectangle(rectangles[i]);
        }
    }

    /**
     * takes an x and y coordinate and determines if they are within the board
     * @param x
     * @param y
     * @return true or false
     */
    protected boolean inBounds(int x, int y)
    {
        return(x >= 0 && x < 8 && y >= 0 && y < 8);
    }

    /**
     * identifies if a piece can move to a given square and what will happen if it does
     * @param board the board, containing all squares and pieces
     * @param destX the destination x
     * @param destY the destination y
     * @return  0: cannot move there, 1: can move there, won't take a piece, 2: can move there, will take a piece
     */
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

    /**
     * returns a 2d array of integers 0-2 that identifies where a piece can move
     * @param board the board, containing all squares and pieces
     * @return 2d array
     */
    public int[][] getMoveSquares(Square[][] board)
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

        //now go through all linear moves in the moveset
        int numMoves = moveSetX.length;
        for(int i = 0; i < numMoves; i++)
        {
            for(int j = 1; j < maxMoves + 1; j++)
            {
                int destX = squareX + (j * moveSetX[i]);
                int destY = squareY + (j * moveSetY[i]);
                int moveVal = 0;
                if(inBounds(destX, destY))
                {
                    moveVal = canMoveThere(board, destX, destY);
                    moves[destX][destY] = moveVal;
                }
                    
                
                //if out of bounds, or piece blocking the way, stop checking this move direction
                if(moveVal == 0 | moveVal == 2)
                {
                    break;
                }
            }
        }

        //now look at all special moves unique to the piece
        moves = getSpecialMoves(board, moves);

        return moves;
    }

    /**
     * for pieces with unique behaviour (pawn, knight) where movement is not linear, this function allows them to behave differently
     * @param board the board, containing all squares and pieces
     * @param moves the 2d array passed from getMoves() that will be returned after modification
     * @return
     */
    public int[][] getSpecialMoves(Square[][] board, int[][] moves)
    {
        return moves;
    }    


    //accessors and mutators

    /**
     * sets a piece's x and y both visually in the game arena, and functionally in the grid
     * @param x new gamearena x
     * @param y new gamearena y
     * @param squareX new grid x
     * @param squareY new grid y
     */
    public void setCoordinates(int x, int y, int squareX, int squareY)
    {
        this.x = x;
        this.y = y;
        this.squareX = squareX;
        this.squareY = squareY;
        updateVisuals();
    }

    /**
     * identifies if it is a piece's turn based on its colour
     * @return
     */
    public boolean isTurn()
    {
        return this.isTurn;
    }

    /**
     * tells the piece if it is its turn or not
     * @param turn
     */
    public void setTurn(boolean turn)
    {
        this.isTurn = turn;
    }
    
    /**
     * gets the piece's name
     * @return
     */
    public String getPieceName()
    {
        return pieceName;
    }

    /**
     * gets the piece's number
     */
    public int getPieceNum()
    {
        return pieceNum;
    }

    /**
     * gets the piece's colour
     * @return
     */
    public int getColour()
    {
        return colour;
    }

    /**
     * identifies if, given a colour, a piece is on its team, and returns that fact
     * @return boolean col == colour; 
     * */
    public boolean isSameColour(int col)
    {
        return(col == colour);
    }

    public boolean justMovedDouble()
    {
        return false;
    }

    public void setJustMovedDouble(boolean val)
    {
        
    }

    public int getMovDir()
    {
        return 0;
    }

    /**
     * tells a piece it has moved
     */
    public void hasMoved()
    {
        
    }

    public int getEndY()
    {
        return -1;
    }

    //CHILDREN


    //each square has one piece, so this piece just needs to be able to be transferred around between squares
    //THE PAWN:
    /**
     * can only move forward once at a time, but cannot take pieces directly ahead of it
     * can only take pieces DIAGONALLY, but can only move diagonally when there is a piece there
     * can move forward twice on its first turn
     */
    public static class Pawn extends Piece
    {
        private boolean hasMoved = false;
        private boolean justMovedDouble = false;
        private int endY;
        /**
         * constructor for child pawn, uniquely sets itself up with name "pawn" and piecenum 0, as well as visuals and its moveset
         * @param colour
         * @param x
         * @param y
         * @param squareX
         * @param squareY
         */
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

            if(squareY == 1)
                endY = 7;
            else
                endY = 0;

            //set moveset
            moveSetX = new int[0];
            moveSetY = new int[0];
            maxMoves = 0;

            //update the visuals
            updateVisuals();
        }

        /**
         * pawn's unique getSpecialMoves() function, allowing it to behave uniquely
         */
        @Override public int[][] getSpecialMoves(Square[][] board, int[][] moves)
        {
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
                {
                    moves[destX][destY] = 1;
                    //if hasn't moved, look 2 ahead
                    if(!hasMoved)
                    {
                        destY += 1 * movDir;
                        if(canMoveThere(board, destX, destY) == 1)
                            moves[destX][destY] = 3;
                    }
                }   
                else
                    moves[destX][destY] = 0;

            

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
            
            
            //en passant
            //check left and right
            //if there is a pawn directly next to this pawn that has just moved double:
                //you can take on the square behind it
            destX = squareX - 1;
            destY = squareY;
            if(inBounds(destX, destY))
            {
                Piece p = board[destX][destY].getPiece();
                if(p != null)
                    if(p.justMovedDouble())
                    {
                        //the pawn to the LEFT has just moved double
                        //therefore we can TAKE on square destX, destY + (1 * movDir) --> signify with number 4
                        destY = squareY + (1 * movDir);
                        moves[destX][destY] = 4;
                    }
            }

            destX = squareX + 1;
            destY = squareY;
            if(inBounds(destX, destY))
            {
                Piece p = board[destX][destY].getPiece();
                if(p != null)
                    if(p.justMovedDouble())
                    {
                        //the pawn to the RIGHT has just moved double
                        //therefore we can TAKE on square destX, destY + (1 * movDir) --> signify with number 4
                        destY = squareY + (1 * movDir);
                        moves[destX][destY] = 4;
                    }
            }



            //return 2d array of moves that can occur
            return moves;
        }

        /**
         * updates the different components of the piece's visuals dynamically
         */
        @Override public void updateVisuals()
        {
            balls[0].setXPosition(x);
            balls[0].setYPosition(y - 5);

            rectangles[0].setXPosition(x - 5);
            rectangles[0].setYPosition(y);

            rectangles[1].setXPosition(x - 10);
            rectangles[1].setYPosition(y + 25);
        }

        /**
         * changes that the piece has moved to true, preventing it from jumping forward 2 squares
         */
        @Override public void hasMoved()
        {
            hasMoved = true;
        }

        /**
         * returns a pawn's end Y for ease of comparison later by the board
         */
        @Override public int getEndY()
        {
            return endY;
        }

        /**
         * returns the variable justMovedDouble for pawns too identify if they can en passant one another
         */
        @Override public boolean justMovedDouble()
        {
            return justMovedDouble;
        }

        /**
         * sets the justMovedDOUble variable
         */
        @Override public void setJustMovedDouble(boolean val)
        {
            justMovedDouble = val;
        }

        /**
         * gets the pawn's move direction
         */
        @Override public int getMovDir()
        {
            return movDir;
        }
    }


    //THE ROOK:
    /**
     * can move up, down, left, right as many times as possible, up until there is another piece in the way
     * if the piece is on the other team, it will take that piece
     */
    public static class Rook extends Piece
    {
        public Rook(int colour, int x, int y, int squareX, int squareY)
        {   
            //call super constructor
            super("Rook", 3, colour, x, y, squareX, squareY);

            //assign attributes unique to the rook
            ballNum = 0;
            rectangleNum = 5;
            //create new circle and rectangles and add to the arrays
            Rectangle body = new Rectangle(0, 0, 20, 25, colours[colour], 2);
            Rectangle base = new Rectangle(0, 0, 30, 15, colours[colour], 2);
            Rectangle peak1 = new Rectangle(0, 0, 8, 15, colours[colour], 2);
            Rectangle peak2 = new Rectangle(0, 0, 8, 15, colours[colour], 2);
            Rectangle peak3 = new Rectangle(0, 0, 8, 15, colours[colour], 2);
            rectangles[0] = body;
            rectangles[1] = base;
            rectangles[2] = peak1;
            rectangles[3] = peak2;
            rectangles[4] = peak3;

            //set moveset
            moveSetX = new int[4];
            moveSetY = new int[4];

            //left
            moveSetX[0] = -1;
            moveSetY[0] = 0;
            //right
            moveSetX[1] = 1;
            moveSetY[1] = 0;
            //up
            moveSetX[2] = 0;
            moveSetY[2] = -1;
            //down
            moveSetX[3] = 0;
            moveSetY[3] = 1;

            maxMoves = 8;

            //update the visuals
            updateVisuals();
        }

        @Override public void updateVisuals()
        {
            rectangles[0].setXPosition(x - 10);
            rectangles[0].setYPosition(y - 5);
            rectangles[1].setXPosition(x - 15);
            rectangles[1].setYPosition(y + 20);
            rectangles[2].setXPosition(x - 15);
            rectangles[2].setYPosition(y - 15);
            rectangles[3].setXPosition(x - 4);
            rectangles[3].setYPosition(y - 15);
            rectangles[4].setXPosition(x + 7);
            rectangles[4].setYPosition(y - 15);
        }
    }


    //THE BISHOP:
    /**
     * can move diagnoally in all directions up until there is another piece in the way
     * if the piece is on the other team, it will take that piece
     */
    public static class Bishop extends Piece
    {
        public Bishop(int colour, int x, int y, int squareX, int squareY)
        {   
            //call super constructor
            super("Bishop", 2, colour, x, y, squareX, squareY);

            //assign attributes unique to the bishop
            ballNum = 1;
            rectangleNum = 2;
            //create new circle and rectangles and add to the arrays
            Ball head = new Ball(0, 0, 23, colours[colour], 2);
            Rectangle body = new Rectangle(0, 0, 20, 40, colours[colour], 2);
            Rectangle base = new Rectangle(0, 0, 30, 15, colours[colour], 2);
            balls[0] = head;
            rectangles[0] = body;
            rectangles[1] = base;

            //set moveset
            moveSetX = new int[4];
            moveSetY = new int[4];

            //left
            moveSetX[0] = -1;
            moveSetY[0] = -1;
            //right
            moveSetX[1] = 1;
            moveSetY[1] = 1;
            //up
            moveSetX[2] = 1;
            moveSetY[2] = -1;
            //down
            moveSetX[3] = -1;
            moveSetY[3] = 1;

            maxMoves = 8;

            //update the visuals
            updateVisuals();
        }

        @Override public void updateVisuals()
        {
            balls[0].setXPosition(x);
            balls[0].setYPosition(y - 20);

            rectangles[0].setXPosition(x - 10);
            rectangles[0].setYPosition(y - 20);
            rectangles[1].setXPosition(x - 15);
            rectangles[1].setYPosition(y + 20);
        }
    }


    //THE QUEEN
    /**
     * can move diagonally and horizontally across the whole board, until there's a piece in the way
     */
    public static class Queen extends Piece
    {
        public Queen(int colour, int x, int y, int squareX, int squareY)
        {   
            //call super constructor
            super("Queen", 4, colour, x, y, squareX, squareY);

            //assign attributes unique to the queen
            ballNum = 0;
            rectangleNum = 6;
            //create new circle and rectangles and add to the arrays
            Rectangle base = new Rectangle(0, 0, 68, 20, colours[colour], 2);
            Rectangle prong1 = new Rectangle(0, 0, 8, 25, colours[colour], 2);
            Rectangle prong2 = new Rectangle(0, 0, 8, 30, colours[colour], 2);
            Rectangle prong3 = new Rectangle(0, 0, 8, 35, colours[colour], 2);
            Rectangle prong4 = new Rectangle(0, 0, 8, 30, colours[colour], 2);
            Rectangle prong5 = new Rectangle(0, 0, 8, 25, colours[colour], 2);
            rectangles[0] = base;
            rectangles[1] = prong1;
            rectangles[2] = prong2;
            rectangles[3] = prong3;
            rectangles[4] = prong4;
            rectangles[5] = prong5;

            //set moveset
            moveSetX = new int[8];
            moveSetY = new int[8];

            //left
            moveSetX[0] = -1;
            moveSetY[0] = 0;
            //right
            moveSetX[1] = 1;
            moveSetY[1] = 0;
            //up
            moveSetX[2] = 0;
            moveSetY[2] = -1;
            //down
            moveSetX[3] = 0;
            moveSetY[3] = 1;

            //leftup
            moveSetX[4] = -1;
            moveSetY[4] = -1;
            //rightup
            moveSetX[5] = 1;
            moveSetY[5] = 1;
            //rightdown
            moveSetX[6] = 1;
            moveSetY[6] = -1;
            //leftdown
            moveSetX[7] = -1;
            moveSetY[7] = 1;

            maxMoves = 8;

            //update the visuals
            updateVisuals();
        }

        @Override public void updateVisuals()
        {
            //base
            rectangles[0].setXPosition(x - 34);
            rectangles[0].setYPosition(y);
            //prongs
            rectangles[1].setXPosition(x - 34);
            rectangles[1].setYPosition(y - 15);
            rectangles[2].setXPosition(x - 19);
            rectangles[2].setYPosition(y - 20);
            rectangles[3].setXPosition(x - 4);
            rectangles[3].setYPosition(y - 25);
            rectangles[4].setXPosition(x + 11);
            rectangles[4].setYPosition(y - 20);
            rectangles[5].setXPosition(x + 26);
            rectangles[5].setYPosition(y - 15);
        }
    }

    
    //KING
    /** 
     * can move in all directions but only one square
     */
    public static class King extends Piece
    {
        public King(int colour, int x, int y, int squareX, int squareY)
        {   
            //call super constructor
            super("King", 5, colour, x, y, squareX, squareY);

            //assign attributes unique to the King
            ballNum = 2;
            rectangleNum = 2;
            //create new circle and rectangles and add to the arrays
            Ball ball1 = new Ball(0, 0, 30, colours[colour], 2);
            Ball ball2 = new Ball(0, 0, 30, colours[colour], 2);
            Rectangle rectangle1 = new Rectangle(0, 0, 60, 20, colours[colour], 2);
            Rectangle rectangle2 = new Rectangle(0, 0, 10, 30, colours[colour], 2);
            balls[0] = ball1;
            balls[1] = ball2;
            rectangles[0] = rectangle1;
            rectangles[1] = rectangle2;

            //set moveset
            moveSetX = new int[8];
            moveSetY = new int[8];

            //left
            moveSetX[0] = -1;
            moveSetY[0] = 0;
            //right
            moveSetX[1] = 1;
            moveSetY[1] = 0;
            //up
            moveSetX[2] = 0;
            moveSetY[2] = -1;
            //down
            moveSetX[3] = 0;
            moveSetY[3] = 1;

            //leftup
            moveSetX[4] = -1;
            moveSetY[4] = -1;
            //rightup
            moveSetX[5] = 1;
            moveSetY[5] = 1;
            //rightdown
            moveSetX[6] = 1;
            moveSetY[6] = -1;
            //leftdown
            moveSetX[7] = -1;
            moveSetY[7] = 1;

            maxMoves = 1;

            //update the visuals
            updateVisuals();
        }

        @Override public void updateVisuals()
        {
            balls[0].setXPosition(x - 15);
            balls[0].setYPosition(y);
            balls[1].setXPosition(x + 15);
            balls[1].setYPosition(y);

            rectangles[0].setXPosition(x - 30);
            rectangles[0].setYPosition(y);
            rectangles[1].setXPosition(x - 5);
            rectangles[1].setYPosition(y - 30);
        }
    }


    //KNIGHT
    /**
     * knight: can move in 'L' shapes and can jump over pieces
     */
    public static class Knight extends Piece
    {
        /**
         * constructor for child Knight, uniquely sets itself up with name "Knight" and piecenum 0, as well as visuals and its moveset
         * @param colour
         * @param x
         * @param y
         * @param squareX
         * @param squareY
         */
        public Knight(int colour, int x, int y, int squareX, int squareY)
        {   
            //call super constructor
            super("Knight", 1, colour, x, y, squareX, squareY);

            //assign attributes unique to the Knight
            ballNum = 2;
            rectangleNum = 2;
            //create new circle and rectangles and add to the arrays
            Ball head = new Ball(0, 0, 30, colours[colour], 2);
            Ball nose = new Ball(0, 0, 20, colours[colour], 2);
            Rectangle body = new Rectangle(0, 0, 20, 25, colours[colour], 2);
            Rectangle base = new Rectangle(0, 0, 30, 15, colours[colour], 2);
            balls[0] = head;
            balls[1] = nose;
            rectangles[0] = body;
            rectangles[1] = base;

            //set moveset
            moveSetX = new int[8];
            moveSetY = new int[8];
            //1 up, 2 left
            moveSetX[0] = -1;
            moveSetY[0] = -2;
            //2 up, 1 left
            moveSetX[1] = -2;
            moveSetY[1] = -1;
            //2 up, 1 right
            moveSetX[2] = 1;
            moveSetY[2] = -2;
            //1 up, 2 right
            moveSetX[3] = 2;
            moveSetY[3] = -1;
            //1 down, 2 right
            moveSetX[4] = 2;
            moveSetY[4] = 1;
            //2 down, 1 right
            moveSetX[5] = 1;
            moveSetY[5] = 2;
            //2 down, 1 left
            moveSetX[6] = -1;
            moveSetY[6] = 2;
            //1 down, 2 left
            moveSetX[7] = -2;
            moveSetY[7] = 1;
            
            maxMoves = 0;

            //update the visuals
            updateVisuals();
        }

        /**
         * pawn's unique getSpecialMoves() function, allowing it to behave uniquely
         */
        @Override public int[][] getSpecialMoves(Square[][] board, int[][] moves)
        {
            //now go through all linear moves in the moveset
            int numMoves = moveSetX.length;
            for(int i = 0; i < numMoves; i++)
            {
                int destX = squareX + moveSetX[i];
                int destY = squareY + moveSetY[i];
                if(inBounds(destX, destY))
                {
                    moves[destX][destY] = canMoveThere(board, destX, destY);
                }
            }

            //return 2d array of moves that can occur
            return moves;
        }

        /**
         * updates the different components of the piece's visuals dynamically
         */
        @Override public void updateVisuals()
        {
            balls[0].setXPosition(x);
            balls[0].setYPosition(y - 8);
            balls[1].setXPosition(x - 7);
            balls[1].setYPosition(y - 3);

            rectangles[0].setXPosition(x - 10);
            rectangles[0].setYPosition(y - 5);
            rectangles[1].setXPosition(x - 15);
            rectangles[1].setYPosition(y + 20);
        }
    }
}