public class Board
{
    private GameManager gameManager;
    private GameArena arena;
    //i is x coord, j is y coord
    private Square[][] board = new Square[8][8];

    //0 : pawn, 1: knight, 2: bishop, 3: rook, 4: queen, 5: king
    private int[] pieceSetup = {3, 1, 2, 4, 5, 2, 1, 3};
    private String[] pieceNames = {"Pawn", "Knight" , "Bishop", "Rook", "Queen", "King"};
    private String[] colours = {"#b4b4b4", "#1f4f2d"};
    //grey : #b4b4b4
    //green: #1f4f2d
    private int selectedX = -1;
    private int selectedY = -1;

    /**
     * constructor, takes the squarewidth and the coordinates of startpoints to start drawing rectangles from
     * @param squareWidth squarewidth
     * @param startPointX rectangle startpoint x value
     * @param startPointY rectangle startpoint y value
     */
    public Board(int squareWidth, int startPointX, int startPointY)
    {
        int colour = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                colour = (i + j) % 2;
                board[i][j] = new Square((i * squareWidth) + startPointX, (j * squareWidth) + startPointY, colours[colour], squareWidth);
            }
        }

        //set columns 2 to 5 to null
        for(int i = 0; i < 8; i++)
        {
            for(int j = 2; j < 6; j++)
            {
                board[i][j].setPiece(null);
            }
        }

        //add all pieces
        //ROOKS
        int[][] rookCoords = {{0, 0}, {7, 0}, {0, 7}, {7, 7}};
        colour = 0;
        for(int i = 0; i < rookCoords.length; i++)
        {
            if(i >= 2)
                colour = 1;
            int rookX = rookCoords[i][0];
            int rookY = rookCoords[i][1];
            Piece.Rook r = new Piece.Rook(colour, (startPointX + (squareWidth / 2) + (squareWidth * rookX)), (startPointY + (squareWidth / 2) + (squareWidth * rookY)), rookX, rookY);
            board[rookX][rookY].setPiece(r);
        }

        //BISHOPS
        int[][] bishopCoords = {{2, 0}, {5, 0}, {2, 7}, {5, 7}};
        colour = 0;
        for(int i = 0; i < bishopCoords.length; i++)
        {
            if(i >= 2)
                colour = 1;
            int bishopX = bishopCoords[i][0];
            int bishopY = bishopCoords[i][1];
            Piece.Bishop b = new Piece.Bishop(colour, (startPointX + (squareWidth / 2) + (squareWidth * bishopX)), (startPointY + (squareWidth / 2) + (squareWidth * bishopY)), bishopX, bishopY);
            board[bishopX][bishopY].setPiece(b);
        }

        //QUEENS
        int[][] queenCoords = {{3, 0}, {3, 7}};
        colour = 0;
        for(int i = 0; i < queenCoords.length; i++)
        {
            if(i >= 1)
                colour = 1;
            int queenX = queenCoords[i][0];
            int queenY = queenCoords[i][1];
            Piece.Queen q = new Piece.Queen(colour, (startPointX + (squareWidth / 2) + (squareWidth * queenX)), (startPointY + (squareWidth / 2) + (squareWidth * queenY)), queenX, queenY);
            board[queenX][queenY].setPiece(q);
        }

        //then add all pawns
        for(int i = 0; i < 8; i++)
        {
            for(int j = 1; j < 7; j+=5)
            {
                colour = 0;
                if(j == 6)
                    colour++;
                Piece.Pawn p = new Piece.Pawn(colour, (startPointX + (squareWidth / 2) + (squareWidth * i)), (startPointY + (squareWidth / 2) + (squareWidth * j)), i, j);
                board[i][j].setPiece(p);
            }
        }
    }

    //functionality

    /**
     * outputs the board: adds every piece's balls and rectangles to the arena for display
     * @param arena arena to add to
     */
    public void outputBoard(GameArena arena)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[j][i].getPiece() != null)
                {
                    //System.out.printf("%s ", board[j][i].getPiece().getPieceName());
                    board[j][i].getPiece().addTo(arena);
                } 
                //else
                    //System.out.printf(" ");
                board[j][i].addTo(arena);
            }
            //System.out.printf("\n");
        }
    }

    /**
     * resets all squares, making them not selected or movable
     */
    public void resetBoard()
    {
        //reset the whole board
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                board[i][j].reset();
            }
        }
    }

    /**
     * originally identified by the gamearena, passed to the input manager, passed to the gamemanager which identifies what square has been clicked
     * now passed to the board with parameters identifying which square exactly was clicked, so it can identify what to do next
     * clicking already selected square deselects it
     * clicking a square with nothing on it does nothing
     * clicking a square with a piece when it is its turn selects the square (if not already selected)
     * clicking a square that is movable moves the piece selected there
     * @param squareX the square clicked X
     * @param squareY the square clicked Y
     */
    public void click(int squareX, int squareY)
    {
        Square newSquare = board[squareX][squareY];

        //does new square have anything on it?
        //is current selected == new selected?
            //yes: 'click' the square, set current selected to -1
            //no: 'reset' currently selected (as long as not -1) and 'click' new square, as well as setting current selected to new selected
        if(newSquare.getPiece() == null && !newSquare.isMovable())
        {
            //ignore
            //System.out.printf("empty and unmovable\n");
        }
        else if(newSquare.getPiece() != null && !newSquare.getPiece().isTurn() && !newSquare.isMovable())
        {
            //ignore - piece clicked on when it's not its turn and the currently selected piece can't move there
            //System.out.printf("not that piece's turn, and unmovable\n");
        }
        else if(selectedX == squareX && selectedY == squareY)
        {
            newSquare.click();
            selectedX = -1;
            selectedY = -1;
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    Square s = board[i][j];
                    s.setMovable(false);
                    s.update();
                }
            }
            resetBoard();
        }
        else
        {
            //System.out.printf("click on different square\n");
            //if movable, then move the piece
            if(newSquare.isMovable())
            {
                Square selectedSquare = board[selectedX][selectedY];
                //System.out.printf("square is movable\n");
                //if selected square is occupied, delete all the visuals
                if(newSquare.getPiece() != null)
                {
                    newSquare.getPiece().removeFrom(arena);
                }

                newSquare.setPiece(selectedSquare.getPiece());
                newSquare.setPieceCoordinates(squareX, squareY);
                selectedSquare.setPiece(null);
                selectedX = -1;
                selectedY = -1;

                //reset board
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                        Square s = board[i][j];
                        s.reset();
                        s.update();
                    }
                }
                resetBoard();
                //turn has been taken, update game manager
                gameManager.updateGame();
            }
            //otherwise change the selected square
            else
            {
                resetBoard();
                //System.out.printf("square is not movable\n");
                selectedX = squareX;
                selectedY = squareY;
                Square selectedSquare = board[selectedX][selectedY];
                selectedSquare.click();

                //now find all moves that can be done
                int[][] moves = new int[8][8];
                Piece p = selectedSquare.getPiece();
                if(p != null)
                    moves = p.getMoveSquares(board);
                //using that, change 'movable' variable for each square
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                        Square s = board[i][j];
                        s.setMovable(moves[i][j] > 0);
                        s.update();
                    }
                }
            }
            
        }
    }

    //accessors and mutators

    /**
     * sets the board's game manager for later reference
     * @param gameManager
     */
    public void setGameManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /**
     * sets the board's game arena for later reference
     * @param gameArena
     */
    public void setGameArena(GameArena gameArena)
    {
        this.arena = gameArena;
    }

    /**
     * returns the board's playing board (array of squares)
     */
    public Square[][] getBoard()
    {
        return board;
    }
}