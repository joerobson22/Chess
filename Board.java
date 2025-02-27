/**
 * The chess board itself
 * @author Joe Robson
 */
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

    private int startPointX;
    private int startPointY;
    private int squareWidth;

    /**
     * constructor, takes the squarewidth and the coordinates of startpoints to start drawing rectangles from
     * @param squareWidth squarewidth
     * @param startPointX rectangle startpoint x value
     * @param startPointY rectangle startpoint y value
     */
    public Board(int squareWidth, int startPointX, int startPointY)
    {
        this.startPointX = startPointX;
        this.startPointY = startPointY;
        this.squareWidth = squareWidth;

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

        
        //KNIGHTS
        int[][] knightCoords = {{1, 0}, {6, 0}, {1, 7}, {6, 7}};
        colour = 0;
        for(int i = 0; i < knightCoords.length; i++)
        {
            if(i >= 2)
                colour = 1;
            int knightX = knightCoords[i][0];
            int knightY = knightCoords[i][1];
            Piece.Knight k = new Piece.Knight(colour, (startPointX + (squareWidth / 2) + (squareWidth * knightX)), (startPointY + (squareWidth / 2) + (squareWidth * knightY)), knightX, knightY);
            board[knightX][knightY].setPiece(k);
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

        //KINGS
        int[][] kingCoords = {{4, 0}, {4, 7}};
        colour = 0;
        for(int i = 0; i < kingCoords.length; i++)
        {
            if(i >= 1)
                colour = 1;
            int kingX = kingCoords[i][0];
            int kingY = kingCoords[i][1];
            Piece.King k = new Piece.King(colour, (startPointX + (squareWidth / 2) + (squareWidth * kingX)), (startPointY + (squareWidth / 2) + (squareWidth * kingY)), kingX, kingY);
            board[kingX][kingY].setPiece(k);
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


    public void outputMoves(int[][] moves)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                System.out.printf("%d ", moves[j][i]);
            }
            System.out.printf("\n");
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
                Square s = board[i][j];
                s.reset();
                s.update();
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
        if(gameManager.getGameStatus() != 0)
        {
            //ignore
        }
        else if(newSquare.getPiece() == null && !newSquare.isMovable())
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

                if(newSquare.isDoubleMovable())
                {
                    newSquare.getPiece().setJustMovedDouble(true);
                }

                if(newSquare.getEnPassantable())
                {
                    //we need to get the move direction of the pawn that just moved diagonally
                    int movDir = newSquare.getPiece().getMovDir();
                    //flip the move direction
                    movDir *= -1;
                    //add it to the squareY
                    squareY += movDir;
                    //set that square's piece to null
                    board[squareX][squareY].getPiece().removeFrom(arena);
                    board[squareX][squareY].setPiece(null);
                }

                //reset board
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
                moves = checkForCheck(moves, selectedX, selectedY);
                //outputMoves(moves);
                //using that, change 'movable' variable for each square
                for(int i = 0; i < 8; i++)
                {
                    for(int j = 0; j < 8; j++)
                    {
                        Square s = board[i][j];
                        s.setMovable(moves[i][j] > 0);
                        s.setDoubleMovable(moves[i][j] == 3);
                        s.setEnPassantable(moves[i][j] == 4);
                        s.update();
                    }
                }
            }
            
        }
    }


    /**
     * checks all squares, looks at all possible moves for each piece
     * @param board
     * @return
     */
    public boolean isCheck(Square[][] board, boolean turn)
    {
        int colour = 1;
        int kingX = -1;
        int kingY = -1;
        if(turn)
            colour = 0;

        //find the king first
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Piece piece = board[i][j].getPiece();
                if(piece != null)
                {
                    //if king (piecenum == 5) and same colour as current turn: set kingX and kingY to that
                    if(piece.getPieceNum() == 5 && piece.isSameColour(colour))
                    {
                        //System.out.printf("king found!\n");
                        kingX = i;
                        kingY = j;
                        break;
                    }
                }
            }
            if(kingX > -1)
                break;
        }

        //then, using the board passed in, look at all squares
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Piece piece = board[i][j].getPiece();
                if(piece != null)
                {
                    if(!piece.isSameColour(colour))
                    {
                        //if it does, then check all its moves
                        int[][] moves = piece.getMoveSquares(board);
                        //if any of them have a 2 where the king is, then it is check
                        if(moves[kingX][kingY] == 2)
                            return true;
                    }
                }
            }
            
        }
        return false;
    }


    /**
     * function called within click() and checkGameEnd which removes any spaces that a piece could move to that would result in it being check
     * @param moves the 2d array of integers that determines where a piece could move to
     * @param currentX x coordinate of the piece
     * @param currentY y coordinate of the piece
     * @return the altered 2d array without any invalid moves
     */
    public int[][] checkForCheck(int[][] moves, int currentX, int currentY)
    {
        //check all move squares in the 2d array above 0
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(moves[i][j] > 0)
                {
                    //for a move square, move the piece on the board copy, and call isCheck, passing the altered board copy and turn in.
                    Piece oldPiece = board[i][j].getPiece();
                    board[i][j].setPiece(board[currentX][currentY].getPiece());
                    board[currentX][currentY].setPiece(null);
                    //if true, then that move cannot happen, so set that square to 0
                    if(isCheck(board, gameManager.getTurn()))
                    {
                        moves[i][j] = 0;
                    }
                    board[currentX][currentY].setPiece(board[i][j].getPiece());
                    board[i][j].setPiece(oldPiece);
                }
            }
        }
        return moves;
    }


    /**
     * checks if the game is either stalemate or checkmate
     * if the current player has no moves:
     * if it is check, they lost by checkmate
     * if it isn't, they have drawn by stalemate
     * otherwise, just quickly check if there are only kings, in which case, stalemate
     * @param turn whose turn it is
     * @return the game status- 0: fine, 1: checkmate, 2: stalemate
     */
    public int checkGameEnd(boolean turn)
    {
        int colour = 1;
        if(turn)
            colour = 0;
        //first check if the person whose turn it currently is has any moves
        if(noMoves(colour))
        {
            //if no moves:
            //if check: lose by checkmate
            if(isCheck(board, turn))
            {
                System.out.printf("CHECKMATE!\n");
                return 1;
            }
            //if not check: draw by stalemate
            else
            {
                System.out.printf("STALEMATE!\n");
                return 2;
            }
        }

        //now, check if there are only kings left
        if(justKings())
            return 2;

        return 0;
    }


    /**
     * checks if there are only kings left on the board
     * @return true or false
     */
    public boolean justKings()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Piece p = board[i][j].getPiece();
                if(p != null)
                    if(p.getPieceNum() < 5)
                        return false;
            }
        }
        return true;
    }


    /**
     * takes a tema colour and identifies if that team has any available moves
     * scans all pieces on that team, then scans all their moves until finding one that is greater than 0, which means it can move
     * @param colour colour of team whose turn it is
     * @return true or false- true means no moves, false means moves
     */
    public boolean noMoves(int colour)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Piece piece = board[i][j].getPiece();
                if(piece != null)
                {
                    if(piece.isSameColour(colour))
                    {
                        int[][] moves = checkForCheck(piece.getMoveSquares(board), i, j);
                        if(!movesEmpty(moves))
                        {
                            return false;
                        }   
                    }
                }
            }
        }
        return true;
    }


    /**
     * searches through a given move 2d array for any value > 0, which means it can move there
     * @param moves
     * @return
     */
    public boolean movesEmpty(int[][] moves)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(moves[i][j] > 0)
                {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * checks if any pawns have reached the end of the board by checking their endY
     */
    public void checkForPromotion()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Piece p = board[i][j].getPiece();
                if(p != null)
                {
                    if(p.getPieceNum() == 0 && j == p.getEndY())
                    {
                        //promote!
                        Piece.Queen q = new Piece.Queen(p.getColour(), (startPointX + (squareWidth / 2) + (squareWidth * i)), (startPointY + (squareWidth / 2) + (squareWidth * j)), i, j);
                        board[i][j].getPiece().removeFrom(arena);
                        board[i][j].setPiece(q);
                        q.addTo(arena);
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