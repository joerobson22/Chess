public class Board
{
    //i is x coord, j is y coord
    private Square[][] board = new Square[8][8];

    //0 : pawn, 1: knight, 2: bishop, 3: rook, 4: queen, 5: king
    private int[] pieceSetup = {3, 1, 2, 4, 5, 2, 1, 3};
    private String[] pieceNames = {"Pawn", "Knight" , "Bishop", "Rook", "Queen", "King"};
    private String[] colours = {"#b4b4b4", "#1f4f2d"};

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

        //then add all pieces
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                int pieceNum = pieceSetup[i];
                Piece p = new Piece(pieceNames[pieceNum], pieceNum, j);
                board[i][j * 7].setPiece(p);
            }
            for(int j = 1; j < 7; j+=5)
            {
                int pieceNum = 0;
                colour = 0;
                if(j == 6)
                    colour++;
                Piece p = new Piece(pieceNames[pieceNum], pieceNum, colour);
                board[i][j].setPiece(p);
            }
        }
    }

    public void outputBoard(GameArena arena)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[j][i].getPiece() != null)
                    System.out.printf("%s ", board[j][i].getPiece().getPieceName());
                else
                    System.out.printf(" ");
                board[j][i].addTo(arena);
            }
            System.out.printf("\n");
        }
    }
}