/**
 * Provides an interface where inputs detected by the gamearena can be specifically distributed to different members of this class
 * @author Joe Robson
 */
public class InputManager
{
    private GameManager gameManager;

    /**
     * sets the input manager's gamemanager to call when an input has been made
     * @param gameManager
     */
    public void setGameManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    /**
     * originally identified by gamearena, calls click on the gamemanager for that to identify what to do
     * @param mouseX the mouse's x
     * @param mouseY the mouse's y
     */
    public void click(double mouseX, double mouseY)
    {
        //System.out.printf("click!\n");
        //System.out.printf("(%f, %f)\n", mouseX, mouseY);
        gameManager.click(mouseX, mouseY);
    }
}