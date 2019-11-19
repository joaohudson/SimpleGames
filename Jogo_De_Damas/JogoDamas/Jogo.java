import java.util.Random;
/**
 * Classe principal do jogo de Damas.
 * 
 * @author (João Hudson) 
 * @version (20/09/2018)
 */
public class Jogo
{
    //jogador Branco:
    private Jogador   jogador1;
    //jogador Vermelho:
    private Jogador   jogador2;
    //gerador de número aleatório:
    private Random    gerador;
    //tabuleiro do jogo:
    private Tabuleiro tabuleiro;
    //jogador que está jogando atualmente:
    private Jogador   jogadorAtual;
    //o próximo há jogar:
    private Jogador   proximoJogador;
    //jogador vencedor:
    private Jogador   jogadorVencedor;
    //indica que o jogo acabou:
    private boolean   fimDeJogo;
    
    /**
     * Cria o jogo.
     */
    public Jogo()
    {
        //cria o primeiro jogador:
        jogador1 = new Jogador("Jogador Branco");
        //cria o segundo jogador:
        jogador2 = new Jogador("Jogador Vermelho");
        //cria o tabuleiro do jogo:
        tabuleiro = new Tabuleiro();
        //cria um gerador de números aleatórios:
        gerador = new Random();
        //posiciona as peças:
        posicionador();
        //sortea qual jogador começa:
        sorteaVez();
        //obtém o jogador atual, e o próximo jogador:
        atualizaJogadorAtual();
        //informa que ainda não há um vencedor:
        jogadorVencedor = null;
        //informa que o jogo não acabou:
        fimDeJogo = false;
    }
    
    /**
     * Posiciona as peças em suas respectivas casas.
     */
    private void posicionador()
    {
        //itera a cada linha do tabuleiro:
        for(int j = 0; j < 8; j++)
        {
            //itera a cada coluna do tabuleiro:
            for(int i = 0; i < 8; i++)
            {
                //armazena as peças do primeiro jogador:
                if((j % 2) == (i % 2) && j <= 2)
                {
                    Casa casa = tabuleiro.getCasa(i,j);
                    casa.colocarPeca(new Pedra(casa, jogador1, true, 0));
                }
                //armazena as peças do segundo jogador:
                else if((j % 2) == (i % 2) && j >= 5)
                {
                    Casa casa = tabuleiro.getCasa(i,j);
                    casa.colocarPeca(new Pedra(casa, jogador2, false, 2));
                }
            }
        }   
    }
    
    /**
     * Sorteia quem vai começar.
     */
    private void sorteaVez()
    {
        //armazena um número aleatório:
        int rand;
        //gera um número aleatório entre 0 e 1:
        rand = gerador.nextInt(2);
        //resulta em um número entre 1 e 0:
        switch(rand)
        {
            //caso seja 0 jogador 1 começa:
            case 0:
            {
                jogador1.ganhaVez();
                jogador2.perdeVez();
                break;
            }
            //caso seja 1 jogador 2  começa:
            case 1:
            {
                jogador1.perdeVez();
                jogador2.ganhaVez();
                break;
            }
        }
    }
    
    /**
     * Atualiza o jogador atual(quem está jogando neste momento),
     * e o próximo jogador(o próximo a jogar).
     */
    private void atualizaJogadorAtual()
    {
        if(jogador1.getMinhaVez())
        {
            jogadorAtual = jogador1;
            proximoJogador = jogador2;
        }
        else 
        {
            jogadorAtual = jogador2;
            proximoJogador = jogador1;
        }
    }
    
    /**
     * Verifica se o jogo terminou, e informa o jogador vencedor.
     */
    private void verificaFimDeJogo()
    {
        if(jogador1.nPecas() == 0)
        {
            fimDeJogo = true;
            jogadorVencedor = jogador2;
        }
        else if(jogador2.nPecas() == 0)
        {
            fimDeJogo = true;
            jogadorVencedor = jogador1;
        }
    }
    
    /**
     * Atualiza o turno dos jogadores.
     */
    private void atualizaTurno()
    {
        //verifica se o jogador atual finalizou seu turno:
        if(!jogadorAtual.getMinhaVez())
        {
            //passa a vez ao próximo jogador:
            proximoJogador.ganhaVez();
        }
    }
    
    /**
     * Move uma peça, e informa se o movimento foi bem-sucedido(válido) ou não(inválido).
     * 
     * @param xOrigem A posição x da casa origem da peça.
     * @param yOrigem A posição y da casa origem da peça.
     * @param xDestino A posição x da casa destino da peça.
     * @param yDestino A posição y da casa destino da peça.
     * 
     * @return true se o movimento foi válido, false do contrário.
     */
    public boolean mover(int xOrigem, int yOrigem, int xDestino, int yDestino)
    {
        //informa movimento bem sucedido(true) ou mal sucedido(false):
        boolean movimento = false;
        //verifica se o jogo não acabou:
        if(!fimDeJogo)
        {
            //move a peça e informa se o movimento foi válido:
            movimento = jogadorAtual.moverPeca(xOrigem, yOrigem, xDestino, yDestino);
        }
        //verifica se o jogo acabou:
        verificaFimDeJogo();
        //atualiza o turno dos jogadores:
        atualizaTurno();
        //atualiza o jogador atual:
        atualizaJogadorAtual();
        //informa se o movimento foi bem sucedido:
        return movimento;
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
     * Obtém o jogador atual do jogo.
     * 
     * @return O jogador atual do jogo.
     */
    public Jogador getJogadorAtual()
    {
        return jogadorAtual;
    }
    
    /**
     * Obtém o jogador vencedor do jogo.
     * 
     * @return O jogador vencedor do jogo. 
     */
    public Jogador getJogadorVencedor()
    {
        return jogadorVencedor;
    }
    
    /**
     * Informa se o jogo acabou.
     * 
     * @return true se o jogo acabou, false do contrário.
     */
    public boolean getFimDeJogo()
    {
        return fimDeJogo;
    }
}
