import java.util.ArrayList;
import java.io.Serializable;
/**
 * Descrição: Peça genérica do jogo, a qual é base para
 * criação das outras peças do jogo.
 * 
 * @author (João Hudson) 
 * @version (09/10/2018)
 */
public abstract class Peca implements Serializable
{
    //constantes de tipo das peças brancas:
    public static final int PEAO_BRANCO = 1;
    public static final int TORRE_BRANCA = 2;
    public static final int CAVALO_BRANCO = 3;
    public static final int BISPO_BRANCO = 4;
    public static final int DAMA_BRANCA = 5;
    public static final int REI_BRANCO = 6;
    //constantes de tipo das peças brancas:
    public static final int PEAO_PRETO = -1;
    public static final int TORRE_PRETA = -2;
    public static final int CAVALO_PRETO = -3;
    public static final int BISPO_PRETO = -4;
    public static final int DAMA_PRETA = -5;
    public static final int REI_PRETO = -6;
    
    //jogador proprietário da peça:
    private Jogador jogador;
    //casa da peça:
    private Casa casa;
    //tipo da peça:
    private int tipo;
    //registra quantas vezes a peça se moveu:
    private int numeroDeMovimentos;
    //lista de movimentos possíveis:
    protected ArrayList<Movimento> movimentos;
    
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
        this.casa = casa;
        //posiciona a peça na casa:
        this.casa.colocarPeca(this);
        this.jogador = jogador;
        //envia a peça para seu jogador:
        this.jogador.addPeca(this);
        this.tipo = tipo;
        //cria uma lista para guardar os movimentos possíveis:
        movimentos = new ArrayList<>();
        //inicializa a contagem:
        numeroDeMovimentos = 0;
    }
    
    /**
     * Move a peça.
     * 
     * @param destino A casa destino para onde a peça deve ser movida.
     */
    public void mover(Casa destino)
    {
        //remove a peça da casa atual:
        casa.removerPeca();
        //muda a casa da peça p/ casa destino:
        casa = destino;
        //coloca e peça na nova casa:
        casa.colocarPeca(this);
        //incrementa a contagem de movimentos:
        numeroDeMovimentos++;
    }
    
    /**
     * Obtém o número de movimentos desta peça, ou seja,
     * quantas vezes ela se moveu.
     * 
     * @return O número de movimentos desta peça.
     */
    public int getNumeroDeMovimentos()
    {
        return numeroDeMovimentos;
    }
    
    /**
     * Obtém uma lista de movimentos possíveis desta peça.
     * Obs.: esta lista pode incluir movimentos que deixe
     * o rei em xeque.
     * 
     * @return A lista de movimentos possíveis desta peça.
     */
    public abstract ArrayList<Movimento> getMovimentos();
    
    /**
     * Verifica se a peça correspondente as coordenadas
     * especificadas, é uma peça inimiga:
     * 
     * @param A posição X  da casa dessa peça.
     * @param A posição Y  da casa dessa peça.
     * 
     * @return true em caso da peça ser uma peça inimiga,
     * e false do contrário.
     */
    public boolean verificaInimigo(int x, int y)
    {
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = this.casa.getTabuleiro();
        //obtém uma casa do tabuleiro de acordo com as coordenadas:
        Casa casa = tabuleiro.getCasa(x,y);
        //se a casa existir e possuir uma peça inimga, retorna true, false do contrário:
        return verificaInimigo(casa);
    }
    
    /**
     * Verifica se a peça correspondente a casa 
     * especificada, é uma peça inimiga.
     * 
     * @param casa A casa da peça.
     * 
     * @return true em caso da peça ser uma peça inimiga,
     * e false do contrário.
     */
    public boolean verificaInimigo(Casa casa)
    {
        //verifica se a casa existe:
        if(casa != null)
        {
            //obtém a peça da casa(caso ela tenha):
            Peca peca = casa.getPeca();
            //retorna true se a casa possuir uma peça inimiga:
            return verificaInimigo(peca);
        }
        //a casa não possui peça, então retorna false:
        return false;
    }
    
    /**
     * Verifica se a peça especificada é uma peça
     * inimiga.
     * 
     * @param peca A peça a ser verificada.
     * 
     * @return true em caso da peça ser uma peça inimiga,
     * e false do contrário.
     */
    public boolean verificaInimigo(Peca peca)
    {
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
        casa.removerPeca();
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
    public Casa getCasa()
    {
        return casa;
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
