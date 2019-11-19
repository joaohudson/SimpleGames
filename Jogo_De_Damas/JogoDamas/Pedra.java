
import java.util.ArrayList;
/**
 * Descrição: Pedra do jogo de Damas,
 * se move de forma comum, ou seja,
 * uma casa por vez, e somente no sentido
 * específico da pedra. Em movimentos de
 * ataque pode-se comer voltando, movimento de
 * ataque é obrigatório sempre que possível,
 * e vira Dama ao chegar no outro lado do tabuleiro.
 * 
 * @author (João Hudson) 
 * @version (30/09/2018)
 */
public class Pedra extends Peca
{
    //indica sentido positivo:
    private final boolean sentidoPositivo;
    //lista de peças capturadas:
    private ArrayList<Peca> pecasCapturadas;
    
    /**
     * Cria uma pedra, posiciona em sua casa, e a envia para seu jogador.
     * 
     * @param casa A casa onde a pedra será posicionada.
     * @param jogador O jogador proprietário da pedra.
     * @param sentido O sentido da pedra, que quando true,
     * será sentido positivo, e quando false
     * negativo.
     * @param tipo O tipo da pedra.
     */
    public Pedra(Casa casa, Jogador jogador, boolean sentidoPositivo, int tipo)
    {
        //executa o construtor da peça:
        super(casa,jogador,tipo);
        //informa se o sentido da peça é positivo ou negativo:
        this.sentidoPositivo = sentidoPositivo;
        //informa que inicalmente, não há peças capturadas:
        pecasCapturadas = new ArrayList<>();
    }
    
    /**
     * Verifica se o movimento foi na diagonal.
     * 
     * @param novoX A posição x da casa destino.
     * @param novoY A posição y da casa destino.
     * @param distancia A distância percorrida pela peça,
     * até sua casa destino.
     * @return true se o movimento foi na diagonal, false
     * do contrário.
     */
    private boolean verificaMovimento(int novoX, int novoY, int distancia)
    {
        //casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //verifica se a direção está correta:
        return (novoX - minhaCasa.getX() == distancia || novoX - minhaCasa.getX() == -distancia) 
                                              && 
               (novoY - minhaCasa.getY() == distancia || novoY - minhaCasa.getY() == -distancia);
    }
    
    /**
     * Verifica se o sentido do movimento está correto.
     * 
     * @param novoY A posição y da casa destino.
     * @return true se o sentido estiver correto, false do contrário.
     */
    private boolean verificaSentido(int novoY)
    {
        //casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //verifica se o sentido está correto:
        if(sentidoPositivo)
        {
            return novoY > minhaCasa.getY();
        }
        else
        {
            return novoY < minhaCasa.getY();
        }
    }
    
    /**
     * Localiza uma peça alvo, informa se ela foi encontrada
     * e então captura ela.
     * 
     * @param novoX A posição x da casa destino.
     * @param novoY A posição y da casa destino.
     * @return true se a peça alvo foi encontrada, false do contrário.
     */
    private boolean capturaAlvo(int novoX, int novoY)
    {
        //casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //coordenadas do alvo:
        int xAlvo, yAlvo;
        //encontra a coordenada x do possível alvo:
        if(novoX > minhaCasa.getX())
        {
            xAlvo = novoX - 1;
        }
        else
        {
            xAlvo = novoX + 1;
        }
        //encontra a coordenada y do possível  alvo:
        if(novoY > minhaCasa.getY())
        {
            yAlvo = novoY - 1;
        }
        else
        {
            yAlvo = novoY + 1;
        }
        //possível destino:
        Casa possivelCasaDestino = minhaCasa.getTabuleiro().getCasa(novoX, novoY);
        //armazena a casa do possível alvo:
        Casa possivelCasaAlvo = minhaCasa.getTabuleiro().getCasa(xAlvo, yAlvo);
        //verifica se existe uma peça inimiga e se é possível comê-la:
        if(possivelCasaDestino.estaVazia() && verificaInimigo(xAlvo, yAlvo) && !pecasCapturadas.contains(possivelCasaAlvo.getPeca()))
        {
            //adiciona a peça capturada a lista:
            pecasCapturadas.add(possivelCasaAlvo.getPeca());
            //retorna true indicando alvo encontrado:
            return true;
        }
        else
        {
            //retorna false em caso de peça não encontrada:
            return false;
        }
    }
    
    /**
     * Verifica se existe uma possibilidade da pedra
     * capturar outra peça.
     * 
     * @return true se for possível capturar uma peça,
     * false do contrário.
     */
    public boolean verificaProxAlvo()
    {
        //casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = minhaCasa.getTabuleiro();
        //coordenada x atual da peça:
        int x = minhaCasa.getX();
        //coordenada y atual da peça:
        int y = minhaCasa.getY();
        //verdadeiro se existir um alvo a nordeste:
        boolean alvoNordeste = false;
        //verdadeiro se existir um alvo a sudeste:
        boolean alvoSudeste = false;
        //verdadeiro se existir um alvo a noroeste:
        boolean alvoNoroeste = false;
        //verdadeiro se existir um alvo a sudoeste:
        boolean alvoSudoeste = false;
        //verifica se a casa após a casa nordeste existe:
        if(tabuleiro.getCasa(x+2, y+2) != null)
        {  
            alvoNordeste = verificaInimigo(x+1,y+1) &&//verifica se a peça nordeste é um alvo.
            tabuleiro.getCasa(x+2,y+2).estaVazia() &&//verifica se a próxima casa está vazia para poder comer a peça.
            !pecasCapturadas.contains(tabuleiro.getCasa(x+1, y+1).getPeca());//verifica se o alvo encontrado ainda não foi capturado.
        }
        //verifica se a casa após a casa sudeste existe:
        if(tabuleiro.getCasa(x+2, y-2) != null)
        {
            alvoSudeste = verificaInimigo(x+1,y-1) &&//verifica se a peça sudeste é um alvo.
            tabuleiro.getCasa(x+2,y-2).estaVazia() &&//verifica se a próxima casa está vazia para poder comer a peça.
            !pecasCapturadas.contains(tabuleiro.getCasa(x+1, y-1).getPeca());//verifica se o alvo encontrado ainda não foi capturado.
        }
        //verifica se a casa após a casa noroeste existe:
        if(tabuleiro.getCasa(x-2, y+2) != null)
        {
            alvoNoroeste = verificaInimigo(x-1,y+1) &&//verifica se a peça noroeste é um alvo.
            tabuleiro.getCasa(x-2,y+2).estaVazia() &&//verifica se a próxima casa está vazia para poder comer a peça.
            !pecasCapturadas.contains(tabuleiro.getCasa(x-1, y+1).getPeca());//verifica se o alvo encontrado ainda não foi capturado.
        }
        //verifica se a casa após a casa sudoeste existe:
        if(tabuleiro.getCasa(x-2, y-2) != null)
        {
            alvoSudoeste = verificaInimigo(x-1,y-1) &&//verifica se a peça sudoeste é um alvo.
            tabuleiro.getCasa(x-2,y-2).estaVazia() &&//verifica se a próxima casa está vazia para poder comer a peça.
            !pecasCapturadas.contains(tabuleiro.getCasa(x-1, y-1).getPeca());//verifica se o alvo encontrado ainda não foi capturado.
        }
        //verifica se existe pelomenos 1 alvo:
        return alvoNordeste || alvoSudeste || alvoNoroeste || alvoSudoeste;
    }
    
    /**
     * Caso tenha chegado ao outro lado do tabuleiro,
     * transforma a pedra em dama.
     */
    public void viraDama()
    {   
        //obtém a casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //obtém o jogador da pedra:
        Jogador jogador = super.getJogador();
        //obtém o tipo da pedra:
        int tipo = super.getTipo();
        //condição para peça virar Dama:
        if( (sentidoPositivo && (minhaCasa.getY() == 7)) || (!sentidoPositivo && (minhaCasa.getY() == 0)) )
        {
            //obtém o tipo da dama correspondente a peça:
            tipo = (tipo == Peca.PEDRA_BRANCA) ? Peca.DAMA_BRANCA : Peca.DAMA_VERMELHA;
            //descarta a pedra:
            super.descartar();
            //cria uma dama, posiciona na casa da pedra, e envia para o jogador da pedra:
            new Dama(minhaCasa, jogador, tipo);
        }
    }
    
    /**
     * Move essa pedra de forma ofensiva, ou seja,
     * de forma que ela capture uma peça inimiga, 
     * obedecendo os critérios para se capturar outra
     * peça.
     * 
     * @param A coordenada x do destino da peça.
     * @param A coordenada y do destino da peça.
     * 
     * @return true caso o movimento seja
     * válido, ou false do contrário.
     */
    public boolean comer(int novoX, int novoY)
    {
        //casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //indica se um alvo foi encontrado, e então captura-o:
        boolean alvoEncontrado = capturaAlvo(novoX, novoY);
        //Condição para coordenadas inválidas:
        if(novoX < 0 || novoX >= 8 || novoY < 0 || novoY >= 8)
        {
            //informa movimento inválido:
            return false;
        }
        //verifica se o movimento foi válido, e se foi encontrado um alvo:
        else if( verificaMovimento(novoX, novoY, 2) && alvoEncontrado )
        {
            //obtém a casa destino:
            Casa destino = minhaCasa.getTabuleiro().getCasa(novoX, novoY);
            //remove a pedra da casa atual:
            minhaCasa.removerPeca();
            //muda a casa da pedra:
            super.setCasa(destino);
            //coloca a pedra na nova casa:
            destino.colocarPeca(this);
            //informa ataque bem-sucedido:
            return true;
        }
        else
        {
            //informa movimento inválido:
            return false;
        }
    }
    
    /**
     * Move a pedra de forma comum, ou seja,
     * sem capturar outra peça. E obedecendo
     * os critérios para movimento comum.
     * 
     * @param A coordenada X do destino da pedra.
     * @param A coordenada Y do destino da pedra.
     * 
     * @return true se o movimento foi válido,
     * false do contrário.
     */
    public boolean mover(int novoX, int novoY)
    {
        //casa da pedra:
        Casa minhaCasa = super.getMinhaCasa();
        //Condição para coordenadas inválidas:
        if(novoX < 0 || novoX >= 8 || novoY < 0 || novoY >= 8)
        {
            //informa que o movimento foi inválido:
            return false;
        }
        //condição de movimento comum válido:
        else if(verificaMovimento(novoX, novoY, 1) && verificaSentido(novoY) && minhaCasa.getTabuleiro().getCasa(novoX,novoY).estaVazia())
        {
            //obtém casa destino da pedra:
            Casa destino = minhaCasa.getTabuleiro().getCasa(novoX, novoY);
            //remove a pedra da casa atual:
            minhaCasa.removerPeca();
            //muda a casa da pedra:
            super.setCasa(destino);
            //coloca a pedra na nova casa:
            destino.colocarPeca(this);
            //informa que o movimento foi válido:
            return true;
        }
        else
        {
            //informa movimento inválido:
            return false;
        }
    }
    
    /**
     * Elimina as peças capturadas.
     */
    public void eliminaAlvos()
    {
        //descarta as peças capturadas:
        for(Peca peca : pecasCapturadas)
        {
            peca.descartar();
        }
        //limpa a lista de peças:
        pecasCapturadas.clear();
    }
}
