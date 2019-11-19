import java.util.ArrayList;
/**
 * Descrição: A dama do jogo de Damas se move
 * na diagonal, e em qualquer sentido. Ela pode
 * percorrer toda diagonal, desde que o caminho 
 * esteja livre(sem obstáculos). Movimento de
 * ataque é obrigatório sempre que possível.
 * 
 * @author (João Hudson) 
 * @version (30/09/2018)
 */
public class Dama extends Peca
{
    //Pecas capturadas:
    private ArrayList<Peca> pecasCapturadas;
    
    /**
     * Cria uma dama, posiciona em sua casa, e a envia para seu jogador.
     * 
     * @param casa A casa da dama.
     * @param jogador O jogador proprietário da dama.
     * @param tipo O tipo da dama.
     */
    public Dama(Casa casa, Jogador jogador, int tipo)
    {
        //executa o construtor da peça:
        super(casa, jogador, tipo);
        //cria uma lista de peças captuadas:
        pecasCapturadas = new ArrayList<>();
    }
    
    /**
     * Verifica se o movimento da dama foi na diagonal.
     * 
     * @param xOrigem A posição x da casa origem da dama.
     * @param yOrigem A posição y da casa origem da dama.
     * @return true se a direção estiver correta, false do contrário.
     */
    private boolean verificaDirecao(int xOrigem, int yOrigem, int novoX, int novoY)
    {
        //true quando a distância percorrida for válida, ou seja, quando a distância percorrida pela Dama for diferente de zero.
        boolean distanciaValida = novoX != xOrigem && novoY != yOrigem;
        //true quando o movimento for na diagonal.
        boolean direcaoValida = (novoX - xOrigem) == (novoY - yOrigem) || (novoX - xOrigem) == -(novoY - yOrigem);
        //retorna true em caso da direção ser válida.
        return distanciaValida && direcaoValida;//verifica se o movimento foi diferente de zero e se a direção é válida(na diagonal).
    }
    
    /**
     * Verifica se o movimento da dama na coordenada x foi positivo.
     * 
     * @param novoX A posição x da casa destino da dama.
     * @return true se for positivo, ou false se for negativo.
     */
    private boolean verificaXPositivo(int novoX)
    {
        //coordenada x atual da dama:
        int xPosition = super.getMinhaCasa().getX();
        //informa se o sentido é positivo na direção x:
        return novoX > xPosition;
    }
    
    /**
     * Verifica se o movimento da dama na coordenada y foi positivo.
     * 
     * @param novoY A posição y da casa destino da dama.
     * @return true se for positivo, ou false se for nagativo.
     */
    private boolean verificaYPositivo(int novoY)
    {
        //coordenada Y atual da dama:
        int yPosition = getMinhaCasa().getY();
        //informa se o sentido é positivo na direção y:
        return novoY > yPosition;
    }
    
    /**
     * Verifica se o movimento foi válido.
     * 
     * @param xPosition A posição x da casa oriegm da dama.
     * @param yPosition A posição y da casa origem da dama.
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true se o movimento foi válido, ou false do contrário.
     */
    private boolean verificaMovimento(int xPosition, int yPosition, int novoX, int novoY)
    {
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = super.getMinhaCasa().getTabuleiro();
        //informa se o movimento foi válido(true) ou não(false):
        boolean movimento = false;
        //verifica se a direção está correta:
        if( verificaDirecao(xPosition, yPosition, novoX, novoY) )
        {
            //true se o sentido na coordenada x for positiva, e false do contrário:
            boolean xPositivo = verificaXPositivo(novoX);
            //true se o sentido na coordenada y for positiva, e false do contrário:
            boolean yPositivo = verificaYPositivo(novoY);
            //caso a direção seja válida, o movimento pode ser válido:
            movimento = true;
            //sentido nordeste:
            if(xPositivo && yPositivo)
            {   
                //verifica se existe um obstáculo(peça) no percurso sentido nordeste, caso exista, movimento será inválido(false), ou
                //caso não encontre, o movimento será válido(continuará true):
                for(int x =  (xPosition + 1), y = (yPosition + 1); x <= novoX; x++, y++)
                {
                    //armazena uma casa da diagonal:
                    Casa casa = getMinhaCasa().getTabuleiro().getCasa(x,y);
                    //se a casa estiver ocupada:
                    if(!casa.estaVazia())
                    {
                        //movimento inválido:
                        movimento = false;
                        //sai do laço:
                        break;
                    }
                }
            }
            //sentido sudeste:
            else if(xPositivo && !yPositivo)
            {   
                //verifica se existe um obstáculo(peça) no percurso sentido sudeste, caso exista, movimento será inválido(false), ou
                //caso não encontre, o movimento será válido(continuará true):
                for(int x =  (xPosition + 1), y = (yPosition - 1); x <= novoX; x++, y--)
                {
                    //armazena uma casa da diagonal:
                    Casa casa = tabuleiro.getCasa(x,y);
                    //se a casa estiver ocupada:
                    if(!casa.estaVazia())
                    {
                        //movimento inválido:
                        movimento = false;
                        //sai do laço:
                        break;
                    }
                }
            }
            //sentido noroeste:
            else if(!xPositivo && yPositivo)
            {   
                //verifica se existe um obstáculo(peça) no percurso sentido noroeste, caso exista, movimento será inválido(false), ou
                //caso não encontre, o movimento será válido(continuará true):
                for(int x =  (xPosition - 1), y = (yPosition + 1); x >= novoX; x--, y++)
                {
                    //armazena uma casa da diagonal:
                    Casa casa = tabuleiro.getCasa(x,y);
                    //se a casa estiver ocupada:
                    if(!casa.estaVazia())
                    {
                        //movimento inválido:
                        movimento = false;
                        //sai do laço:
                        break;
                    }
                }
            }
            //sentido sudoeste:
            else if(!xPositivo && !yPositivo)
            {   
                //verifica se existe um obstáculo(peça) no percurso sentido sudoeste, caso exista, movimento será inválido(false), ou
                //caso não encontre, o movimento será válido(continuará true):
                for(int x =  (xPosition - 1), y = (yPosition - 1); x >= novoX; x--, y--)
                {
                    //armazena uma casa da diagonal:
                    Casa casa = tabuleiro.getCasa(x,y);
                    //se a casa estiver ocupada:
                    if(!casa.estaVazia())
                    {
                        //movimento inválido:
                        movimento = false;
                        //sai do laço:
                        break;
                    }
                }
            }
        }
        else
        {
            //direção inválida, movimento inválido:
            movimento = false;
        }
        //retona true em caso de movimento válido, e false em caso de inválido:
        return movimento;
    }
    
    /**
     * Localiza uma peça alvo para movimentos no sentido
     * nordeste, e caso encontre, captura a peça.
     * 
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true caso localize a peça, ou false do contrário.
     * */
    private boolean localizaAlvoNE(int novoX, int novoY)
    {
        //indica se o ataque foi bem sucedido(true) ou não(false):
        boolean ataque;
        //indica se existe uma peça aliada(obstáculo) no caminho:
        boolean obstaculo = false;
        //peças inimigas no caminho.
        int nPecasInimigas = 0;
        //casa da peça da dama:
        Casa minhaCasa = getMinhaCasa();
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = minhaCasa.getTabuleiro();
        //casa destino:
        Casa destino = tabuleiro.getCasa(novoX, novoY);
        //uma possível casa alvo:
        Casa suspeitaCasaAlvo = null;
        //itera do início ao destino:
        for(int x = (minhaCasa.getX() + 1), y = (minhaCasa.getY() + 1); x <= novoX; x++, y++)
        {
            //recebe uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa está ocupada por uma peça aliada, ou uma peça que já foi capturada:
            if((!casa.estaVazia() && !verificaInimigo(x,y)) || pecasCapturadas.contains(casa.getPeca()))
            {
                //informa obstáculo:
                obstaculo = true;
                //encerra o laço:
                break;
            }
            //verifica se a casa está ocupada por uma peça alvo:
            if(verificaInimigo(x,y))
            {
                //conta a número de peças inimigas encontradas na diagonal:
                nPecasInimigas++;
                //guarda a casa suspeita de ser a casa de uma peça alvo:
                suspeitaCasaAlvo = casa;
            }
        }
        //caso tenha encontrada apenas uma peça inimiga, nenhum obstáculo e a casa destino esteja vazia, ataque é verdadeiro:
        ataque = !obstaculo && (nPecasInimigas == 1) && destino.estaVazia();
        //caso o ataque seja bem sucedido, a suspeita da casa da possível peça alvo, foi confirmada:
        if(ataque)
        {
            //adiciona a peça capturada na lista:
            pecasCapturadas.add(suspeitaCasaAlvo.getPeca());
        }
        //retorna true caso o ataque seja possível:
        return ataque;
    }
    
    /**
     * Localiza uma peça alvo para movimentos no sentido
     * sudeste, e caso encontre, captura a peça.
     * 
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true caso localize a peça, ou false do contrário.
     * */
    private boolean localizaAlvoSE(int novoX, int novoY)
    {
        //indica se o ataque foi bem sucedido(true) ou não(false):
        boolean ataque;
        //indica se existe uma peça aliada(obstáculo) no caminho:
        boolean obstaculo = false;
        //peças inimigas no caminho:
        int nPecasInimigas = 0;
        //casa da peça da dama:
        Casa minhaCasa = getMinhaCasa();
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = minhaCasa.getTabuleiro();
        //casa destino:
        Casa destino = tabuleiro.getCasa(novoX, novoY);
        //uma possível casa alvo:
        Casa suspeitaCasaAlvo = null;
        //itera do início ao destino:
        for(int x = (minhaCasa.getX() + 1), y = (minhaCasa.getY() - 1); x <= novoX; x++, y--)
        {
            //recebe uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa está ocupada por uma peça aliada, ou uma peça que já foi capturada:
            if((!casa.estaVazia() && !verificaInimigo(x,y)) || pecasCapturadas.contains(casa.getPeca()))
            {
                //informa obstáculo:
                obstaculo = true;
                //encerra o laço:
                break;
            }
            //verifica se a casa está ocupada por uma peça alvo:
            if(verificaInimigo(x,y))
            {
                //conta a número de peças inimigas encontradas na diagonal:
                nPecasInimigas++;
                //guarda a casa suspeita de ser a casa de uma peça alvo:
                suspeitaCasaAlvo = casa;
            }
        }
        //caso tenha encontrada apenas uma peça inimiga, nenhum obstáculo e a casa destino esteja vazia, ataque é verdadeiro:
        ataque = !obstaculo && (nPecasInimigas == 1) && destino.estaVazia();
        //caso o ataque seja bem sucedido, a suspeita da casa da possível peça alvo, foi confirmada:
        if(ataque)
        {
            //adiciona a peça capturada na lista:
            pecasCapturadas.add(suspeitaCasaAlvo.getPeca());
        }
        //retorna true caso o ataque seja possível:
        return ataque;
    }
    
    /**
     * Localiza uma peça alvo para movimentos no sentido
     * noroeste, e caso encontre, captura a peça.
     * 
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true caso localize a peça, ou false do contrário.
     * */
    private boolean localizaAlvoNO(int novoX, int novoY)
    {
        //indica se o ataque foi bem sucedido(true) ou não(false):
        boolean ataque;
        //indica se existe uma peça aliada(obstáculo) no caminho:
        boolean obstaculo = false;
        //peças inimigas no caminho:
        int nPecasInimigas = 0;
        //casa da peça da dama:
        Casa minhaCasa = getMinhaCasa();
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = minhaCasa.getTabuleiro();
        //casa destino:
        Casa destino = tabuleiro.getCasa(novoX, novoY);
        //uma possível casa alvo:
        Casa suspeitaCasaAlvo = null;
        //itera do início ao destino:
        for(int x = (minhaCasa.getX() - 1), y = (minhaCasa.getY() + 1); x >= novoX; x--, y++)
        {
            //recebe uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa está ocupada por uma peça aliada, ou uma peça que já foi capturada:
            if((!casa.estaVazia() && !verificaInimigo(x,y)) || pecasCapturadas.contains(casa.getPeca()))
            {
                //informa obstáculo:
                obstaculo = true;
                //encerra o laço:
                break;
            }
            //verifica se a casa está ocupada por uma peça alvo:
            if(verificaInimigo(x,y))
            {
                //conta a número de peças inimigas encontradas na diagonal:
                nPecasInimigas++;
                //guarda a casa suspeita de ser a casa de uma peça alvo:
                suspeitaCasaAlvo = casa;
            }
        }
        //caso tenha encontrada apenas uma peça inimiga, nenhum obstáculo e a casa destino esteja vazia, ataque é verdadeiro:
        ataque = !obstaculo && (nPecasInimigas == 1) && destino.estaVazia();
        //caso o ataque seja bem sucedido, a suspeita da casa da possível peça alvo, foi confirmada:
        if(ataque)
        {
            //adiciona a peça capturada na lista:
            pecasCapturadas.add(suspeitaCasaAlvo.getPeca());
        }
        //retorna true caso o ataque seja possível:
        return ataque;
    }
    
    /**
     * Localiza uma peça alvo para movimentos no sentido
     * sudoeste, e caso encontre, captura a peça.
     * 
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true caso localize a peça, ou false do contrário.
     */
    private boolean localizaAlvoSO(int novoX, int novoY)
    {
        //indica se o ataque foi bem sucedido(true) ou não(false):
        boolean ataque;
        //indica se existe uma peça aliada(obstáculo) no caminho:
        boolean obstaculo = false;
        //peças inimigas no caminho:
        int nPecasInimigas = 0;
        //casa da peça da dama:
        Casa minhaCasa = getMinhaCasa();
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = minhaCasa.getTabuleiro();
        //casa destino:
        Casa destino = tabuleiro.getCasa(novoX, novoY);
        //uma possível casa alvo:
        Casa suspeitaCasaAlvo = null;
        //itera do início ao destino:
        for(int x = (minhaCasa.getX() - 1), y = (minhaCasa.getY() - 1); x >= novoX; x--, y--)
        {
            //recebe uma casa da diagonal:
            Casa casa = tabuleiro.getCasa(x,y);
            //verifica se a casa está ocupada por uma peça aliada, ou uma peça que já foi capturada:
            if((!casa.estaVazia() && !verificaInimigo(x,y)) || pecasCapturadas.contains(casa.getPeca()))
            {
                //informa obstáculo:
                obstaculo = true;
                //encerra o laço:
                break;
            }
            //verifica se a casa está ocupada por uma peça alvo.
            if(verificaInimigo(x,y))
            {
                //conta a número de peças inimigas encontradas na diagonal:
                nPecasInimigas++;
                //guarda a casa suspeita de ser a casa de uma peça alvo:
                suspeitaCasaAlvo = casa;
            }
        }
        //caso tenha encontrada apenas uma peça inimiga, nenhum obstáculo e a casa destino esteja vazia, ataque é verdadeiro:
        ataque = !obstaculo && (nPecasInimigas == 1) && destino.estaVazia();
        //caso o ataque seja bem sucedido, a suspeita da casa da possível peça alvo, foi confirmada:
        if(ataque)
        {
            //armazena em peça alvo:
            pecasCapturadas.add(suspeitaCasaAlvo.getPeca());
        }
        //retorna true caso o ataque seja possível:
        return ataque;
    }
    
    /**
     * Localiza uma peça alvo, informa se ela foi encontrada
     * e então captura ela.
     * 
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true para alvo encontrado, ou false caso contrário.
     */
    private boolean capturaAlvo(int novoX, int novoY)
    {
        //true se o sentido do movimento em X for positivo, false do contrário:
        boolean xPositivo = verificaXPositivo(novoX);
        //true se o sentido do movimento em Y for positivo, false do contrário:
        boolean yPositivo = verificaYPositivo(novoY);
        //indica se a peça alvo foi encontrada:
        boolean alvoEncontrado = false;
        //condição para movimento de sentido nordeste:
        if(xPositivo && yPositivo)
        {   
            //busca o alvo, informa se ele foi encontrado, e em caso afirmativo, adiciona a lista de peças capturadas:
            alvoEncontrado = localizaAlvoNE(novoX, novoY);
        }
        //sentido sudeste:
        else if(xPositivo && !yPositivo)
        {
            //busca o alvo, informa se ele foi encontrado, e em caso afirmativo, adiciona a lista de peças capturadas:
            alvoEncontrado = localizaAlvoSE(novoX, novoY);
        }
        //sentido noroeste:
        else if(!xPositivo && yPositivo)
        {
            //busca o alvo, informa se ele foi encontrado, e em caso afirmativo, adiciona a lista de peças capturadas:
            alvoEncontrado = localizaAlvoNO(novoX, novoY);
        }
        //sentido sudoeste:
        else if(!xPositivo && !yPositivo)
        {
            //busca o alvo, informa se ele foi encontrado, e em caso afirmativo, adiciona a lista de peças capturadas:
            alvoEncontrado = localizaAlvoSO(novoX, novoY);
        }
        //retorna true para alvo encontrado, ou false caso contrário:
        return alvoEncontrado;
    }
    
    /**
     * Informa se é possível atacar no sentido Nordeste.
     * 
     * @param xPosition A posição x da casa origem da dama.
     * @param yPosition A posição y da casa origem da dama.
     * retorna true se houver uma possibilidade, ou false do contrário.
     */
    private boolean verificaProxAlvoNE(int xPosition, int yPosition)
    {
        //informa se é possível atacar no sentido nordeste:
        boolean ataque = false;
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = getMinhaCasa().getTabuleiro();
        //itera da casa vizinha até os extremos do sentido nordeste:
        for(int x = (xPosition + 1), y = (yPosition + 1); x < 8; x++, y++)
        {
            //armazena cada casa da diagonal:
            Casa casa = tabuleiro.getCasa(x, y);
            //verifica se a casa existe, e se está ocupada:
            if( (casa != null) && !casa.estaVazia() )
            {
                //verifica se a peça encontrada é um alvo:
                if(verificaInimigo(x,y))
                {
                    //obtém a próxima casa:
                    Casa possivelDestino = tabuleiro.getCasa(x+1,y+1);
                    //verifica se a próxima casa existe, se está vazia, e se o alvo encontrado ainda não foi capturado:
                    ataque = (possivelDestino != null) && possivelDestino.estaVazia() && 
                    !pecasCapturadas.contains(tabuleiro.getCasa(x,y).getPeca());
                }
                //encerra o laço:
                break;
            }
        }
        //retorna true em caso de movimento possível no sentido nordeste,false do contrário:
        return ataque;
    }
    
    /**
     * Informa se é possível atacar no sentido Sudeste.
     * 
     * @param xPosition A posição x da casa origem da dama.
     * @param yPosition A posição y da casa origem da dama.
     * @return true se houver uma possibilidade, ou false do contrário.
     */
    private boolean verificaProxAlvoSE(int xPosition, int yPosition)
    {
        //informa se é possível atacar no sentido sudeste:
        boolean ataque = false;
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = getMinhaCasa().getTabuleiro();
        //itera da casa vizinha até os extremos do sentido sudeste:
        for(int x = (xPosition + 1), y = (yPosition - 1); x < 8; x++, y--)
        {
            //armazena cada casa da diagonal:
            Casa casa = tabuleiro.getCasa(x, y);
            //verifica se a casa existe, e se está ocupada:
            if( (casa != null) && !casa.estaVazia() )
            {
                //verifica se a peça encontrada é um alvo:
                if(verificaInimigo(x,y))
                {
                    //obtém a próxima casa:
                    Casa possivelDestino = tabuleiro.getCasa(x+1,y-1);
                    //verifica se a próxima casa existe, se está vazia, e se o alvo encontrado ainda não foi capturado:
                    ataque = (possivelDestino != null) && possivelDestino.estaVazia() && 
                    !pecasCapturadas.contains(tabuleiro.getCasa(x,y).getPeca());
                }
                //encerra o laço:
                break;
            }
        }
        //retorna true em caso de movimento possível no sentido sudeste,false do contrário:
        return ataque;
    }
    
    /**
     * Informa se é possível atacar no sentido Noroeste.
     * 
     * @param xPosition A posição x da casa origem da dama.
     * @param yPosition A posição y da casa origem da dama.
     * retorna true se houver uma possibilidade, ou false do contrário.
     */
    private boolean verificaProxAlvoNO(int xPosition, int yPosition)
    {
        //informa se é possível atacar no sentido noroeste:
        boolean ataque = false;
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = getMinhaCasa().getTabuleiro();
        //itera da casa vizinha até os extremos do sentido noroeste:
        for(int x = (xPosition - 1), y = (yPosition + 1); x >= 0; x--, y++)
        {
            //armazena cada casa da diagonal:
            Casa casa = tabuleiro.getCasa(x, y);
            //verifica se a casa existe, e se está ocupada:
            if( (casa != null) && !casa.estaVazia() )
            {
                //verifica se a peça encontrada é um alvo:
                if(verificaInimigo(x,y))
                {
                    //obtém a próxima casa:
                    Casa possivelDestino = tabuleiro.getCasa(x-1,y+1);
                    //verifica se a próxima casa existe, se está vazia, e se o alvo encontrado ainda não foi capturado:
                    ataque = (possivelDestino != null) && possivelDestino.estaVazia() && 
                    !pecasCapturadas.contains(tabuleiro.getCasa(x,y).getPeca());
                }
                //encerra o laço:
                break;
            }
        }
        //retorna true em caso de movimento possível no sentido noroeste,false do contrário:
        return ataque;
    }
    
    /**
     * Informa se é possível atacar no sentido Sudoeste.
     * 
     * @param xPosition A posição x da casa origem da dama.
     * @param yPosition A posição y da casa origem da dama.
     * retorna true se houver uma possibilidade, ou false do contrário.
     */
    private boolean verificaProxAlvoSO(int xPosition, int yPosition)
    {
        //informa se é possível atacar no sentido sudoeste:
        boolean ataque = false;
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = getMinhaCasa().getTabuleiro();
        //itera da casa vizinha até os extremos do sentido sudoeste:
        for(int x = (xPosition - 1), y = (yPosition - 1); x >= 0; x--, y--)
        {
            //armazena cada casa da diagonal:
            Casa casa = tabuleiro.getCasa(x, y);
            //verifica se a casa existe, e se está ocupada:
            if( (casa != null) && !casa.estaVazia() )
            {
                //verifica se a peça encontrada é um alvo:
                if(verificaInimigo(x,y))
                {
                    //obtém a próxima casa:
                    Casa possivelDestino = tabuleiro.getCasa(x-1,y-1);
                    //verifica se a próxima casa existe, se está vazia, e se o alvo encontrado ainda não foi capturado:
                    ataque = (possivelDestino != null) && possivelDestino.estaVazia() && 
                    !pecasCapturadas.contains(tabuleiro.getCasa(x,y).getPeca());
                }
                //encerra o laço:
                break;
            }
        }
        //retorna true em caso de movimento possível no sentido sudoeste,false do contrário:
        return ataque;
    }
    
    /**
     * Verifica se é possível capturar uma peça.
     * 
     * @return true caso seja possível, ou false do contrário.
     */
    public boolean verificaProxAlvo()
    {
        //posição atual da dama na coordenada X:
        int x = getMinhaCasa().getX();
        //posição atual da dama na coordenada Y:
        int y = getMinhaCasa().getY();
        //verifica se é possível comer uma peça, retorna true caso seja possível e false caso não seja:
        return verificaProxAlvoNE(x,y) || verificaProxAlvoSE(x,y) || verificaProxAlvoNO(x,y) || verificaProxAlvoSO(x,y);
    }
    
    /**
     * Move a peça normalmente(sem capturar).
     * 
     * @param novoX A posição x da casa destino da dama.
     * @param novoY A posição y da casa destino da dama.
     * @return true se o movimento foi válido, ou false do contrário.
     */
    public boolean mover(int novoX, int novoY)
    {
        //informa se o movimento foi válido(true) ou não(false):
        boolean movimento = false;
        //recebe a casa da peça:
        Casa minhaCasa = getMinhaCasa();
        //tabuleiro do jogo:
        Tabuleiro tabuleiro = minhaCasa.getTabuleiro();
        //recebe a casa destino da dama:
        Casa destino = tabuleiro.getCasa(novoX, novoY);
        //condição de coordenadas do destino inválidas:
        if(destino == null)
        {
            movimento = false;
        }
        //verifica se o movimento comum foi válido:
        else if(verificaMovimento(minhaCasa.getX(), minhaCasa.getY(),novoX, novoY))
        {
            //remove a peça da casa atual dela:
            minhaCasa.removerPeca();
            //adiciona uma nova casa a peça(a casa destino):
            setCasa(destino);
            //coloca a peça na casa destino:
            destino.colocarPeca(this);
            //informa que o movimento foi válido:
            movimento = true;
        }
        //retona true em caso de movimento válido, e false em caso de inválido:
        return movimento;
    }
    
    /**
     * Move essa dama de forma ofensiva, ou seja,
     * de forma que ela capture uma peça inimiga, 
     * obedecendo os critérios para se capturar outra
     * peça.
     * 
     * @param novoX A posição x da casa destino.
     * @param novoY A posição y da casa destino.
     * @return true caso o ataque seja válido, ou false do contrário.
     */
    public boolean comer(int novoX, int novoY)
    {
        //indica se o movimento foi válido:
        boolean ataque = false;
        //recebe a casa da peça da Dama:
        Casa minhaCasa = getMinhaCasa();
        //recebe a casa destino da peça da Dama:
        Casa destino = minhaCasa.getTabuleiro().getCasa(novoX, novoY);
        //indica se um alvo foi encontrado, e então captura-o:
        boolean alvoEncontrado = capturaAlvo(novoX, novoY);
        //condição de coordenadas do destino inválidas:
        if(destino == null)
        {
            ataque = false;
        }
        //verifica se o movimento de ataque foi válido, e se um alvo foi encontrado:
        else if(verificaDirecao(minhaCasa.getX(), minhaCasa.getY(), novoX, novoY) && alvoEncontrado)
        {
            //remove a peça da casa atual dela:
            minhaCasa.removerPeca();
            //muda a casa da peça:
            setCasa(destino);
            //coloca a peça na nova casa:
            destino.colocarPeca(this);
            //informa que o ataque foi válido:
            ataque = true;
        }
        //retorna true em casa de ataque bem sucedido, ou false do contrário:
        return ataque;
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
    
    /**
     * A peça já é uma dama, então não faz
     * nada.
     */
    public void viraDama()
    {
        //vazio.
    }
}
