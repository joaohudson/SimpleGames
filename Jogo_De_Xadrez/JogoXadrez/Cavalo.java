import java.util.ArrayList;
import java.io.Serializable;
/**
 * Descrição: O cavalo do jogo de Xadrez, pode se mover
 * apenas em forma de "L", pode pular outras peças, e comer
 * apenas no fim do movimento.
 * 
 * @author (João Hudson) 
 * @version (08/10/2018)
 */
public class Cavalo extends Peca implements Serializable
{
    /**
     * Cria um cavalo, posiciona em sua casa, e envia para seu jogador.
     * 
     * @param casa A casa do cavalo.
     * @param jogador O jogador do cavalo.
     * @param tipo O tipo do cavalo(branco ou preto).
     */
    public Cavalo(Casa casa, Jogador jogador, int tipo)
    {
        super(casa, jogador, tipo);
    }
    
    /**
     * Verifica se o movimento até a casa especificada é válido.
     * Caso seja, registra esse movimento de acordo com seu tipo,
     * e guarda na lista de movimentos possíveis.
     * 
     * @param casa O destino deste movimento.
     */
    private void adicionaMovimento(Casa casa)
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //verifica se a casa existe, e se está vazia:
        if(casa != null && casa.estaVazia())
        {
            //cria um registro de um possível movimento comum, e guarda na lista:
            Movimento movimento = new Movimento(casaOrigem, casa, Movimento.COMUM);
            movimentos.add(movimento);
        }
        //verifica se a casa existe, e se possui uma peça inimiga:
        else if(casa != null && verificaInimigo(casa))
        {
            //cria um registro de um possível movimento de ataque, e guarda na lista:
            Movimento movimento = new Movimento(casaOrigem, casa, Movimento.ATAQUE);
            movimentos.add(movimento);
        }
    }
    
    /**
     * Analisa os movimentos possíveis no sentido inicial norte,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosNorte()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //obtém um possível destino:
        Casa destino1 = tabuleiro.getCasa(xPosition + 1, yPosition + 2);
        //obtém outro possível destino:
        Casa destino2 = tabuleiro.getCasa(xPosition - 1, yPosition + 2);
        //adiciona na lista os movimentos correspondentes aos destinos encontrados, se os mesmos forem possíveis:
        adicionaMovimento(destino1);
        adicionaMovimento(destino2);
    }
    
    /**
     * Analisa os movimentos possíveis no sentido inicial sul,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosSul()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //obtém um possível destino:
        Casa destino1 = tabuleiro.getCasa(xPosition + 1, yPosition - 2);
        //obtém outro possível destino:
        Casa destino2 = tabuleiro.getCasa(xPosition - 1, yPosition - 2);
        //adiciona na lista os movimentos correspondentes aos destinos encontrados, se os mesmos forem possíveis:
        adicionaMovimento(destino1);
        adicionaMovimento(destino2);
    }
    
    /**
     * Analisa os movimentos possíveis no sentido inicial leste,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosLeste()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //obtém um possível destino:
        Casa destino1 = tabuleiro.getCasa(xPosition + 2, yPosition + 1);
        //obtém outro possível destino:
        Casa destino2 = tabuleiro.getCasa(xPosition + 2, yPosition - 1);
        //adiciona na lista os movimentos correspondentes aos destinos encontrados, se os mesmos forem possíveis:
        adicionaMovimento(destino1);
        adicionaMovimento(destino2);
    }
    
    /**
     * Analisa os movimentos possíveis no sentido inicial oeste,
     * e então adiciona estes movimentos na lista.
     */
    private void analisaMovimentosOeste()
    {
        //obtém a casa origem da peça:
        Casa casaOrigem = getCasa();
        //obtém o tabuleiro do jogo:
        Tabuleiro tabuleiro = casaOrigem.getTabuleiro();
        //obtém a posição x da casa origem:
        int xPosition = casaOrigem.getX();
        //obtém a posição y da casa origem:
        int yPosition = casaOrigem.getY();
        //obtém um possível destino:
        Casa destino1 = tabuleiro.getCasa(xPosition - 2, yPosition + 1);
        //obtém outro possível destino:
        Casa destino2 = tabuleiro.getCasa(xPosition - 2, yPosition - 1);
        //adiciona na lista os movimentos correspondentes aos destinos encontrados, se os mesmos forem possíveis:
        adicionaMovimento(destino1);
        adicionaMovimento(destino2);
    }
    
    /**
     * Atualiza a lista de movimentos possíveis do
     * cavalo.
     */
    private void atualizaMovimentos()
    {
        //limpa a lista
        movimentos.clear();
        //adiciona os possíveis movimentos do sentido norte na lista:
        analisaMovimentosNorte();
        //adiciona os possíveis movimentos do sentido sul na lista:
        analisaMovimentosSul();
        //adiciona os possíveis movimentos do sentido leste na lista:
        analisaMovimentosLeste();
        //adiciona os possíveis movimentos do sentido oeste na lista:
        analisaMovimentosOeste();
    }
    
    public ArrayList<Movimento> getMovimentos()
    {
        //atualiza a lista de movimentos possíveis:
        atualizaMovimentos();
        //retorna a lista:
        return movimentos;
    }
}
