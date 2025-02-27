public class GameManager
{
    private InputManager inputManager;
    private Board board;
    private GameArena arena;
    private int squareWidth;
    private int width;
    private int height;
    private int startPointX;
    private int startPointY;
    private int gameStatus = 0;

    private boolean turn = false; //true: black, false: white

    /**
     * constructor for game manager
     * @param squareWidth takes the square width
     * @param width takes the window's entire width
     * @param height takes the window's entire height
     * @param startPointX takes the startpointX to start drawing rectangles from
     * @param startPointY takes the startpointY to start drawing rectangles from
     */
    public GameManager(int squareWidth, int width, int height, int startPointX, int startPointY)
    {
        this.squareWidth = squareWidth;
        this.width = width;
        this.height = height;
        this.startPointX = startPointX;
        this.startPointY = startPointY;
    }

    //functionality

    /**
     * takes an X and Y to test, and identifies if it is within the 4 bounds also passed to it
     * @param testX x to test
     * @param testY y to test
     * @param lowerBoundX lower x bound to compare with
     * @param lowerBoundY lower y bound to compare with
     * @param upperBoundX upper x bound to compare with
     * @param upperBoundY upper y bound to compare with
     * @return true or false
     */
    public boolean withinBounds(double testX, double testY, int lowerBoundX, int lowerBoundY, int upperBoundX, int upperBoundY)
    {
        return(testX >= lowerBoundX & testX <= upperBoundX & testY <= lowerBoundY & testY >= upperBoundY);
    }

    /**
     * click, called originally by gamearena, passed to inputmanager and then to gamemanager
     * if within bounds of the board, identify what square has been clicked on?
     * @param mouseX the mouse's x
     * @param mouseY the mouse's y
     */
    public void click(double mouseX, double mouseY)
    {
        //identify what square on the board was clicked
        //first identify all bounds
        int leftBound = startPointX - (squareWidth / 2);
        int rightBound = (startPointX + (squareWidth * 8)) + (squareWidth / 2);
        int upBound = startPointY - (squareWidth / 2);
        int downBound = (startPointY + (squareWidth * 8)) + (squareWidth / 2);
        //System.out.printf("left bound: %d\n", leftBound);
        //System.out.printf("right bound: %d\n", rightBound);
        //System.out.printf("up bound: %d\n", upBound);
        //System.out.printf("down bound: %d\n", downBound);

        if(withinBounds(mouseX, mouseY, leftBound, downBound, rightBound, upBound))
        {
            //what square was it?
            int column = ((int)mouseY / squareWidth) - 1;
            int row = ((int)mouseX / squareWidth) - 1;
            //System.out.printf("Square %d, %d?\n", row, column);
            //send this message to the board
            board.click(row, column);
        }

        //send that message to the board
    }

    /**
     * updates all pieces turn attribute
     */
    public void updateGame()
    {
        turn = !turn;

        //update all pieces
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Piece p = board.getBoard()[i][j].getPiece();
                if(p != null)
                    p.setTurn((turn && p.getColour() == 0) | (!turn && p.getColour() == 1));
            }
        }

        board.checkForPromotion();

        if(board.isCheck(board.getBoard(), turn))
            System.out.printf("Check!\n");
        
        gameStatus = board.checkGameEnd(turn);
    }

    //accessors and mutators

    /**
     * sets the gamemanager's inputmanagerfor later reference
     * @param inputManager
     */
    public void setInputManager(InputManager inputManager)
    {
        this.inputManager = inputManager;
    }

    /**
     * sets the gamemanager's board for later reference
     * @param board
     */
    public void setBoard(Board board)
    {
        this.board = board;
        this.board.setGameManager(this);
    }

    /**
     * sets the gamemanager's arena for later reference
     */
    public void setArena(GameArena arena)
    {
        this.arena = arena;
    }

    public boolean getTurn()
    {
        return turn;
    }

    public int getGameStatus()
    {
        return gameStatus;
    }
}