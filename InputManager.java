public class InputManager
{
    private GameManager gameManager;

    public void setGameManager(GameManager gameManager)
    {
        this.gameManager = gameManager;
    }

    public void click(double mouseX, double mouseY)
    {
        //System.out.printf("click!\n");
        //System.out.printf("(%f, %f)\n", mouseX, mouseY);
        gameManager.click(mouseX, mouseY);
    }
}