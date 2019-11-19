import java.io.Serializable;
/**
 * Descrição: Um objeto que guarda dados de movimento
 * da peça.
 * 
 * @author (João Hudson) 
 * @version (12/10/2018)
 */
public class Movimento implements Serializable
{
    //tipos dos movimentos:
    public static final int EN_PASSANT = 1;
    public static final int ROQUE = 2;
    public static final int COMUM = 3;
    public static final int ATAQUE = 4;
    public static final int PROMOCAO_PASSIVA = 5;
    public static final int PROMOCAO_OFENSIVA = 6;
    
    //casa origem da peça:
    private Casa origem;
    //casa destino da peça: 
    private Casa destino;
    //tipo do movimento:
    private int tipo;
    
    /**
     * Cria um objeto que guarda informações de um movimento.
     * 
     * @param origem A casa origem da peça.
     * @param destino A casa destino da peça.
     * @param tipo O tipo de movimento.
     */
    public Movimento(Casa origem, Casa destino, int tipo)
    {
        this.origem = origem;
        this.destino = destino;
        this.tipo = tipo;
    }
    
    /**
     * Localiza a casa do peão alvo do movimento
     * en passant.
     * 
     * @return A casa do peão alvo do movimento en passant,
     * ou null se o movimenton não for desse tipo.
     */
    private Casa localizaPeao()
    {
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = origem.getTabuleiro();
        //Obtém a posição x da casa origem:
        int yOrigem = origem.getY();
        //obtém a posição x da casa destino:
        int xDestino = destino.getX();
        //se o movimento for do tipo en passant, retorna a casa do peão alvo, do contrário retorna null:
        return tipo == EN_PASSANT ? tabuleiro.getCasa(xDestino, yOrigem) : null;
    }
    
    /**
     * Localiza a casa origem da torre do movimento roque.
     * 
     * @return A casa origem da torre do roque.
     */
    private Casa localizaTorre()
    {
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = origem.getTabuleiro();
        //obtém a posição x da casa destino:
        int xDestino = destino.getX();
        //obtém a posição x da casa origem:
        int xOrigem = origem.getX();
        //obtém a posição y da casa origem:
        int yOrigem = origem.getY();
        //true se o sentido do movimento no eixo x for positivo, false do contrário:
        boolean sentidoDireito = xDestino > xOrigem;
        //se o sentido no x for positivo, retorna a torre direita:
        if(sentidoDireito)
        {
            return tabuleiro.getCasa(7, yOrigem);
        }
        //se o sentido no y for negativo, retorna a torre esquerda:
        else
        {
            return tabuleiro.getCasa(0, yOrigem);
        }
    }
    
    /**
     * Localiza o destino da torre do movimento roque.
     * 
     * @return A casa destino da torre do roque.
     */
    private Casa localizaDestinoTorre()
    {
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = origem.getTabuleiro();
        //obtém a posição x da casa destino do rei:
        int xDestino = destino.getX();
        //obtém a posição x da casa origem do rei:
        int xOrigem = origem.getX();
        //obtém a posição y da casa origem do rei:
        int yOrigem = origem.getY();
        //true se o sentido do movimento no eixo x for positivo, false do contrário:
        boolean sentidoDireito = xDestino > xOrigem;
        //se o sentido no x for positivo, então obtém o destino para a torre localizado a direita da origem do rei:
        if(sentidoDireito)
        {
            return tabuleiro.getCasa(xOrigem + 1, yOrigem);
        }
        //se o sentido no x for negativo, então obtém o destino para a torre localizado a esquerda da origem do rei:
        else
        {
            return tabuleiro.getCasa(xOrigem - 1, yOrigem);
        }
    }
    
    /**
     * Obtém a casa destino da peça.
     * 
     * @return A casa destino da peça.
     */
    public Casa getDestino()
    {
        return destino;
    }
    
    /**
     * Obtém a casa origem da peça.
     * 
     * @return A casa origem da peça.
     */
    public Casa getOrigem()
    {
        return origem;
    }
    
    /**
     * Obtém o tipo do movimento.
     * 
     * @return O tipo do movimento.
     */
    public int getTipo()
    {
        return tipo;
    }
    
    /**
     * Obtém a casa da peça alvo.
     * 
     * @return A casa da peça alvo, ou null caso
     * o movimento seja passivo.
     */
    public Casa getCasaAlvo()
    {
        switch(tipo)
        {
            case ATAQUE:
                return destino;
            
            case PROMOCAO_OFENSIVA:
                return destino;
                
            case EN_PASSANT:
                return localizaPeao();
                
            default:
                return null;
        }
    }
    
    /**
     * Obtém a casa da torre do movimento roque.
     * 
     * @return A casa da torre do roque, ou null
     * caso o movimento não seja roque.
     */
    public Casa getCasaTorre()
    {
        return tipo == ROQUE ? localizaTorre() : null;
    }
    
    /**
     * Obtém a casa destino da torre do movimento roque.
     * 
     * @return A casa destino da torre do roque, ou null
     * caso o movimento não seja roque.
     */
    public Casa getDestinoTorre()
    {
        return tipo == ROQUE? localizaDestinoTorre() : null;
    }
}
