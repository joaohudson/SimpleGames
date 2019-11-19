
/**
 * Descrição: Peça genérica do jogo, a qual é base para
 * criação das outras peças do jogo.
 * 
 * @author (João Hudson) 
 * @version (30/09/2018)
 */
public abstract class Peca
{
    //constantes que representam os tipos das peças:
    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;
    
    //jogador proprietário da peça:
    private Jogador jogador;
    //casa da peça:
    private Casa minhaCasa;
    //tipo da peça:
    private int tipo;
    
    /**
     * Construtor base para criação das demais peças do jogo:
     * cria uma peça, posiciona em sua casa, e a envia para 
     * seu jogador.
     * 
     * @param casa A casa da peça.
     * @param jogador O jogador proprietário da peça.
     * @param tipo O tipo da peça.
     */
    public Peca(Casa casa, Jogador jogador, int tipo)
    {
        this.minhaCasa = casa;
        //posiciona a peça na casa:
        this.minhaCasa.colocarPeca(this);
        this.jogador = jogador;
        //envia a peça para seu jogador:
        this.jogador.addPeca(this);
        this.tipo = tipo;
    }
    
    public abstract void viraDama();
    
    public abstract boolean mover(int xDetino, int yDestino);
    
    public abstract boolean comer(int xDestino, int yDestino);
    
    public abstract void eliminaAlvos();
    
    public abstract boolean verificaProxAlvo();
    
    /**
     * Verifica se a peça da casa determinada
     * é uma peça alvo.
     * 
     * @param A posição X  da casa dessa peça.
     * @param A posição Y  da casa dessa peça.
     * 
     * @return true em caso da peça ser uma peça alvo,
     * e false do contrário.
     */
    public boolean verificaInimigo(int x, int y)
    {
        //armazena uma peça da posição x e y:
        Peca  peca = minhaCasa.getTabuleiro().getCasa(x,y).getPeca();
        //verifica se essa peça existe:
        if(peca != null)
        {
            //verifica se a peça é um alvo:
            return jogador != peca.getJogador();
        }
        //informa que a peça não existe, logo também não é um alvo:
        return false;
    }
    
    /**
     * Descarta esta peça.
     */
    public void descartar()
    {
        //remove essa peça do jogador:
        jogador.removePeca(this);
        //remove a peça da casa:
        minhaCasa.removerPeca();
    }
    
    /**
     * Muda a casa atual da peça.
     * 
     * @param casa A casa atual da peça.
     */
    public void setCasa(Casa casa)
    {
        minhaCasa = casa;
    }
    
    /**
     * Retorna o jogador desta peça.
     * 
     * @return O jogador desta peça.
     */
    public Jogador getJogador()
    {
        return jogador;
    }
    
    /**
     * Retorna a casa desta peça.
     * 
     * @return A casa desta peça.
     */
    public Casa getMinhaCasa()
    {
        return minhaCasa;
    }
    
    /**
     * Retorna o tipo desta peça.
     * 
     * @return O tipo desta peça.
     */
    public int getTipo()
    {
        return tipo;
    }
}
