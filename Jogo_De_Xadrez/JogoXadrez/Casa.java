import java.io.Serializable;
/**
 * Descrição: Casa do jogo, onde as peças
 * são posicionadas.
 * 
 * @author (João Hudson) 
 * @version (23/08/2018)
 */
public class Casa implements Serializable
{
    //armazena o tabuleiro no qual a casa está contida:
    private Tabuleiro tabuleiro;
    //posição x da casa:
    private int x;
    //posição y da casa:
    private int y;
    //peça que está ocupando a casa:
    private Peca peca;
    
    /**
     * Cria uma casa.
     * 
     * @param tabuleiro Tabuleiro do jogo.
     * @param x Posição x da casa.
     * @param y Posição y da casa.
     */
    public Casa(Tabuleiro tabuleiro, int x, int y)
    {
        this.tabuleiro = tabuleiro;
        this.x = x;
        this.y = y;
        this.peca = null;
    }
    
    /**
     * Obtém o tabuleiro do jogo.
     * 
     * @return O tabuleiro do jogo.
     */
    public Tabuleiro getTabuleiro() 
    {
        return tabuleiro;
    }
    
    /**
     * Obtém a posição x da casa.
     * 
     * @return A posição x da casa.
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Obtém a posição y da casa.
     * 
     * @return A posição y da casa.
     */
    public int getY() 
    {
        return y;
    }
    
    /**
     * Obtém a peça da casa.
     * 
     * @return A peça da casa.
     */
    public Peca getPeca()
    {
        return peca;
    }

    /**
     * Remove a peça da casa.
     */
    public void removerPeca() 
    {
        peca = null;
    }
    
    /**
     * Adiciona uma peça a casa.
     * 
     * @param peca A peça a ser adicionada na casa.
     */
    public void colocarPeca(Peca peca)
    {
        this.peca = peca;
    }
    
    /**
     * Informa se a casa está vazia.
     * 
     * @return true se estiver vazia, false do contrário.
     */
    public boolean estaVazia()
    {
        return peca == null;
    }
}
