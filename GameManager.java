public class GameManager
{
    private InputManager inputManager;
    private Board board;
    private int squareWidth;
    private int width;
    private int height;
    private int startPointX;
    private int startPointY;

    public GameManager(int squareWidth, int width, int height, int startPointX, int startPointY)
    {
        this.squareWidth = squareWidth;
        this.width = width;
        this.height = height;
        this.startPointX = startPointX;
        this.startPointY = startPointY;
    }

    public void setInputManager(InputManager inputManager)
    {
        this.inputManager = inputManager;
    }

    public void setBoard(Board board)
    {
        this.board = board;
    }

    public boolean withinBounds(double testX, double testY, int lowerBoundX, int lowerBoundY, int upperBoundX, int upperBoundY)
    {
        return(testX >= lowerBoundX & testX <= upperBoundX & testY <= lowerBoundY & testY >= upperBoundY);
    }

    public void click(double mouseX, double mouseY)
    {
        //identify what square on the board was clicked
        //first identify all bounds
        int leftBound = startPointX - (squareWidth / 2);
        int rightBound = (startPointX + (squareWidth * 8)) + (squareWidth / 2);
        int upBound = startPointY - (squareWidth / 2);
        int downBound = (startPointY + (squareWidth * 8)) + (squareWidth / 2);
        System.out.printf("left bound: %d\n", leftBound);
        System.out.printf("right bound: %d\n", rightBound);
        System.out.printf("up bound: %d\n", upBound);
        System.out.printf("down bound: %d\n", downBound);

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
}