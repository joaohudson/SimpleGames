import java.io.Serializable;
/**
 * Descrição: Tabuleiro do jogo, que possui
 * as casas do jogo.
 * 
 * @author (João Hudson) 
 * @version (20/09/2018)
 */
public class Tabuleiro implements Serializable
{
    //armazena todas as casas do tabuleiro:
    private Casa[][] casas;
    
    /**
     * Cria o tabuleiro do jogo.
     */
    public Tabuleiro()
    {
        casas = new Casa[8][8];
        //cria as casas do jogo:
        inicializador();
    }
    
    /**
     * Cria e armazena todas as casas do tabuleiro
     * no array bi-dimensional casas.
     */
    private void inicializador()
    {
        //repete até completar o tabuleiro:
        for(int i = 0; i < 8; i++)
        {
            //repete até completar cada linha do tabuleiro:
            for(int j = 0; j < 8; j++)
            {   
                //instancía casas com coordenadas coincidentes com os índices
                // do array, e passa esse tabuleiro como parâmetro:
                casas[i][j] = new Casa(this, i, j);
            }
        }
    }
    
    /**
     * Obtém uma casa do tabuleiro.
     * 
     * @param x Posição x da casa.
     * @param y Posição y da casa.
     * @return A casa especificada.
     */
    public Casa getCasa(int x, int y)
    {
        //condição de casa válida:
        if(x >= 0 && x < 8 && y >= 0 && y < 8)
        {
            return casas[x][y];
        }
        return null;//condição de casa inválida.
    }
}
