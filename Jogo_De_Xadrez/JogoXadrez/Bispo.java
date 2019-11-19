import java.util.ArrayList;
import java.io.Serializable;
/**
 * Descrição: O bispo do jogo de Xadrez, pode se
 * mover em qualquer sentido da diagonal, e comer
 * da mesma forma.
 * 
 * @author (João Hudson) 
 * @version (08/10/2018)
 */
public class Bispo extends Peca implements Serializable
{
    /**
     * Cria um bispo, posiciona em sua casa, e envia para seu jogador.
     * 
     * @param casa A casa do bispo.
     * @param jogador O jogador do bispo.
     * @param tipo O tipo do bispo(branco ou o).
     */
    public Bispo(Casa casa, Jogador jogador, int tipo)
    {
        super(casa, jogador, tipo);
    }
    
    /**
     * Analisa os movimentos possíveis no sentido nordeste,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosNE()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //itera no sentido nordeste partindo pela próxima casa nesse sentido:
        for(int x = (xPosition + 1), y = (yPosition + 1); x < 8; x++, y++)
        {
            //obtém uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa existe:
            if(casa != null)
            {
                //verifica se a casa está vazia:
                if(casa.estaVazia())
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.COMUM); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                }
                //verifica se a casa possui um alvo:
                else if(verificaInimigo(casa))
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.ATAQUE); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                    //sai do laço, pois não pode pular o inimgo:
                    break;
                }
                //caso a casa esteja ocupada por uma peça aliada, sai do laço:
                else
                {
                    //sai do laço, pois não pode pular aliado:
                    break;
                }
            }
        }
    }
    
    /**
     * Analisa os movimentos possíveis no sentido sudeste,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosSE()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //itera no sentido sudeste partindo pela próxima casa nesse sentido:
        for(int x = (xPosition + 1), y = (yPosition - 1); x < 8; x++, y--)
        {
            //obtém uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa existe:
            if(casa != null)
            {
                //verifica se a casa está vazia:
                if(casa.estaVazia())
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.COMUM); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                }
                //verifica se a casa possui um alvo:
                else if(verificaInimigo(casa))
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.ATAQUE); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                    //sai do laço, pois não pode pular o inimgo:
                    break;
                }
                //caso a casa esteja ocupada por uma peça aliada, sai do laço:
                else
                {
                    //sai do laço, pois não pode pular aliado:
                    break;
                }
            }
        }
    }
    
    /**
     * Analisa os movimentos possíveis no sentido noroeste,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosNO()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //itera no sentido noroeste partindo pela próxima casa nesse sentido:
        for(int x = (xPosition - 1), y = (yPosition + 1); x >= 0; x--, y++)
        {
            //obtém uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa existe:
            if(casa != null)
            {
                //verifica se a casa está vazia:
                if(casa.estaVazia())
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.COMUM); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                }
                //verifica se a casa possui um alvo:
                else if(verificaInimigo(casa))
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.ATAQUE); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                    //sai do laço, pois não pode pular o inimgo:
                    break;
                }
                //caso a casa esteja ocupada por uma peça aliada, sai do laço:
                else
                {
                    //sai do laço, pois não pode pular aliado:
                    break;
                }
            }
        }
    }
    
    /**
     * Analisa os movimentos possíveis no sentido sudoeste,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosSO()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //itera no sentido sudoeste partindo pela próxima casa nesse sentido:
        for(int x = (xPosition - 1), y = (yPosition - 1); x >= 0; x--, y--)
        {
            //obtém uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa existe:
            if(casa != null)
            {
                //verifica se a casa está vazia:
                if(casa.estaVazia())
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.COMUM); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                }
                //verifica se a casa possui um alvo:
                else if(verificaInimigo(casa))
                {
                    //obtém um movimento:
                    Movimento movimento = new Movimento(casaOrigem, casa, Movimento.ATAQUE); 
                    //adiciona esse movimento a lista:
                    movimentos.add(movimento);
                    //sai do laço, pois não pode pular o inimgo:
                    break;
                }
                //caso a casa esteja ocupada por uma peça aliada, sai do laço:
                else
                {
                    //sai do laço, pois não pode pular aliado:
                    break;
                }
            }
        }
    }
    
    /**
     * Atualiza a lista de movimentos possíveis
     * do bispo.
     */
    private void atualizaMovimentos()
    {
        //limpa a lista:
        movimentos.clear();
        //adiciona os possíveis movimentos do sentido nordeste na lista:
        analisaMovimentosNE();
        //adiciona os possíveis movimentos do sentido noroeste na lista:
        analisaMovimentosNO();
        //adiciona os possíveis movimentos do sentido sudeste na lista:
        analisaMovimentosSE();
        //adiciona os possíveis movimentos do sentido sudoeste na lista:
        analisaMovimentosSO();
    }
    
    public ArrayList<Movimento> getMovimentos()
    {
        //atualiza a lista de movimentos:
        atualizaMovimentos();
        //retorna a lista:
        return movimentos;
    }
}
