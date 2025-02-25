public class Main
{
    public static void main(String[] args)
    {
        final int squareWidth = 80;
        final int width = 800;
        final int height = 800;
        int boardSize = squareWidth * 8;
        int startPointX = (width / 2) - (boardSize / 2);
        int startPointY = (height / 2) - (boardSize / 2);
        //create 'arena' with input manager and game manager
        GameArena arena = new GameArena(width, height, true);
        InputManager inputManager = new InputManager();
        GameManager gameManager = new GameManager(squareWidth, width, height, startPointX, startPointY);

        //set all children so they can communicate
        arena.setInputManager(inputManager);
        inputManager.setGameManager(gameManager);
        gameManager.setInputManager(inputManager);

        //create board
        Board board = new Board(squareWidth, startPointX, startPointY);
        board.outputBoard(arena);
        board.setGameArena(arena);
        gameManager.setBoard(board);
        gameManager.setArena(arena);

        while(true)
        {
            //pause for 1/50th of a second
            arena.pause();
        }
    }
}