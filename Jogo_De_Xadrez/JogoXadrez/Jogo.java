import java.io.Serializable;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream; 
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
/**
 * Classe principal do jogo.
 * 
 * @author (João Hudson) 
 * @version (21/10/2018)
 */
public class Jogo implements Serializable
{
    //jogador Branco:
    private Jogador   jogadorBranco;
    //jogador Preto:
    private Jogador   jogadorPreto;
    //tabuleiro do jogo:
    private Tabuleiro tabuleiro;
    //jogador que está jogando atualmente:
    private Jogador   jogadorAtual;
    //o próximo a jogar:
    private Jogador   proximoJogador;
    //jogador vencedor:
    private Jogador   jogadorVencedor;
    //indica que o jogo acabou:
    private boolean   fimDeJogo;
    //indica que a partida é contra uma IA:
    private boolean modoVsIA;
    
    /**
     * Cria o jogo.
     */
    public Jogo(boolean vsIA)
    {
        if(vsIA)
        {
            jogoVsIA();
            modoVsIA = true;
        }
        else
        {
            jogoComum();
            modoVsIA = false;
        }
    }
    
    private void jogoComum()
    {
        //cria o tabuleiro do jogo:
        tabuleiro = new Tabuleiro();
        //cria o primeiro jogador começando com o turno:
        jogadorBranco = new Jogador(this, Jogador.NOME_JOGADOR_BRANCO, tabuleiro, true);
        //cria o segundo jogador na espera do turno:
        jogadorPreto = new Jogador(this, Jogador.NOME_JOGADOR_PRETO, tabuleiro, false);
        //posiciona as peças:
        posicionador();
        //define o jogador branco como jogador inicial:
        jogadorBranco.ganhaVez();
        jogadorPreto.perdeVez();
        //obtém o jogador atual, e o próximo jogador:
        atualizaJogadorAtual();
        //informa que ainda não há um vencedor:
        jogadorVencedor = null;
        //informa que o jogo não acabou:
        fimDeJogo = false;
    }
    
    private void jogoVsIA()
    {
        //cria o tabuleiro do jogo:
        tabuleiro = new Tabuleiro();
        //cria o primeiro jogador começando com o turno:
        jogadorBranco = new Jogador(this, Jogador.NOME_JOGADOR_BRANCO, tabuleiro, true);
        //cria o segundo jogador na espera do turno:
        jogadorPreto = new JogadorIA(this,tabuleiro);
        //posiciona as peças:
        posicionador();
        //define o jogador branco como jogador inicial:
        jogadorBranco.ganhaVez();
        jogadorPreto.perdeVez();
        //obtém o jogador atual, e o próximo jogador:
        atualizaJogadorAtual();
        //informa que ainda não há um vencedor:
        jogadorVencedor = null;
        //informa que o jogo não acabou:
        fimDeJogo = false;
    }
    
    /**
     * Posiciona um peão na casa especificada,
     * se a casa for adequada.
     * 
     * @param x A posição x da casa na qual se deseja posicionar
     * o peão.
     * @param y A posição x da casa na qual se deseja posicionar
     * o peão.
     */
    private void posicionaPeao(int x, int y)
    {
        //obtém a casa da coordenada especificada:
        Casa casa = tabuleiro.getCasa(x,y);
        //posiciona o peão branco:
        if(y == 1)
        {
            new Peao(casa,jogadorBranco,Peca.PEAO_BRANCO);
        }
        //posiciona o peão preto:
        else if(y == 6)
        {
            new Peao(casa, jogadorPreto, Peca.PEAO_PRETO);
        }
    }
    
    /**
     * Posiciona uma torre na casa especificada,
     * se a casa for adequada.
     * 
     * @param x A posição x da casa na qual se deseja posicionar
     * a torre.
     * @param y A posição x da casa na qual se deseja posicionar
     * a torre. 
     */
    private void posicionaTorre(int x, int y)
    {
        //obtém a casa da coordenada especificada:
        Casa casa = tabuleiro.getCasa(x,y);
        //posiciona a torres branca:
        if( ((x == 0) || (x == 7)) && (y == 0)  )
        {
            new Torre(casa, jogadorBranco, Peca.TORRE_BRANCA);
        }
        //posiciona a torre preta:
        else if( ((x == 0) || (x == 7)) && (y == 7) )
        {
            new Torre(casa, jogadorPreto, Peca.TORRE_PRETA);
        }
    }
    
    /**
     * Posiciona um cavalo na casa especificada,
     * se a casa for adequada.
     * 
     * @param x A posição x da casa na qual se deseja posicionar
     * o cavalo.
     * @param y A posição x da casa na qual se deseja posicionar
     * o cavalo. 
     */
    private void posicionaCavalo(int x, int y)
    {
        //obtém a casa da coordenada especificada:
        Casa casa = tabuleiro.getCasa(x,y);
        //posiciona o cavalo branco:
        if( ((x == 1) || (x == 6)) && (y == 0)  )
        {
            new Cavalo(casa, jogadorBranco, Peca.CAVALO_BRANCO);
        }
        //posiciona o cavalo preto:
        else if( ((x == 1) || (x == 6)) && (y == 7)  )
        {
            new Cavalo(casa, jogadorPreto, Peca.CAVALO_PRETO);
        }
    }
    
    /**
     * Posiciona um bispo na casa especificada,
     * se a casa for adequada.
     * 
     * @param x A posição x da casa na qual se deseja posicionar
     * o bispo.
     * @param y A posição x da casa na qual se deseja posicionar
     * o bispo. 
     */
    private void posicionaBispo(int x, int y)
    {
        //obtém a casa da coordenada especificada:
        Casa casa = tabuleiro.getCasa(x,y);
        //posiciona o bispo branco:
        if( ((x == 2) || (x == 5)) && (y == 0)  )
        {
            new Bispo(casa, jogadorBranco, Peca.BISPO_BRANCO);
        }
        //posiciona o bispo preto:
        else if( ((x == 2) || (x == 5)) && (y == 7)  )
        {
            new Bispo(casa, jogadorPreto, Peca.BISPO_PRETO);
        }
    }
    
    /**
     * Posiciona uma dama na casa especificada,
     * se a casa for adequada.
     * 
     * @param x A posição x da casa na qual se deseja posicionar
     * a dama.
     * @param y A posição x da casa na qual se deseja posicionar
     * a dama. 
     */
    private void posicionaDama(int x, int y)
    {
        //obtém a casa da coordenada especificada:
        Casa casa = tabuleiro.getCasa(x,y);
        //posiciona a dama branca:
        if( (x == 3) && (y == 0) )
        {
            new Dama(casa, jogadorBranco, Peca.DAMA_BRANCA);
        }
        //posiciona a dama preta:
        else if( (x == 3) && (y == 7))
        {
            new Dama(casa, jogadorPreto, Peca.DAMA_PRETA);
        }
    }
    
    /**
     * Posiciona um rei na casa especificada,
     * se a casa for adequada.
     * 
     * @param x A posição x da casa na qual se deseja posicionar
     * o rei.
     * @param y A posição x da casa na qual se deseja posicionar
     * o rei. 
     */
    private void posicionaRei(int x, int y)
    {
        //obtém a casa da coordenada especificada:
        Casa casa = tabuleiro.getCasa(x,y);
        //posiciona o rei branco:
        if( (x == 4) && (y == 0) )
        {
            new Rei(casa, jogadorBranco, Peca.REI_BRANCO);
        }
        //posiciona o rei preto:
        else if( (x == 4) && (y == 7) )
        {
            new Rei(casa, jogadorPreto, Peca.REI_PRETO);
        }
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
                //posiciona os peões:
                posicionaPeao(i,j);
                //posiciona as torres:
                posicionaTorre(i,j);
                //posiciona os cavalos:
                posicionaCavalo(i,j);
                //posiciona os bispos:
                posicionaBispo(i,j);
                //posiciona as damas:
                posicionaDama(i,j);
                //posiciona os reis:
                posicionaRei(i,j);
            }
        }   
    }
    
    /**
     * Grava o jogo no arquivo especificado.
     * 
     * @param nomeArquivo O nome do arquivo onde
     * se deseja salvar o jogo.
     * @return true se a gravação foi bem-sucedida,
     * false do contrário.
     */
    
    private byte[] converterEmByte()
    {
        byte[] jogoEmBytes = null;
        try
        {
            ByteArrayOutputStream gravadorDeByte = new ByteArrayOutputStream();
            ObjectOutputStream gravadorDeObjeto = new ObjectOutputStream(gravadorDeByte);
            gravadorDeObjeto.writeObject(this);
            gravadorDeObjeto.flush();
            gravadorDeObjeto.close();
            jogoEmBytes = gravadorDeByte.toByteArray();
            
            return jogoEmBytes;
        }
        catch(Exception e)
        {
            return null;
        }
    }
    
    /**
     * Obtém o jogo salvo no arquivo especificado.
     * 
     * @param O nome do arquivo no qual o jogo
     * se encontra salvo.
     * @return O jogo salvo.
     */
    private Jogo carregarJogo(byte[] jogoEmByte)
    {
        Jogo jogoCarregado = null;
        try
        {
            ByteArrayInputStream leitorDeBytes = new ByteArrayInputStream(jogoEmByte);
            ObjectInputStream leitorDeObjeto = new ObjectInputStream(leitorDeBytes);
            jogoCarregado = (Jogo)leitorDeObjeto.readObject();
            leitorDeObjeto.close();
            leitorDeBytes.close();
            return jogoCarregado;
        }
        catch(Exception e)
        {
            return jogoCarregado;
        }
    }
    
    /**
     * Atualiza o jogador atual(quem está jogando neste momento),
     * e o próximo jogador(o próximo a jogar).
     */
    private void atualizaJogadorAtual()
    {
        //atualiza o jogador atual e o próximo jogador:
        if(jogadorBranco.getMinhaVez())
        {
            jogadorAtual = jogadorBranco;
            proximoJogador = jogadorPreto;
        }
        else 
        {
            jogadorAtual = jogadorPreto;
            proximoJogador = jogadorBranco;
        }
    }
    
    /**
     * Verifica se houve empate, então declara empate.
     * Obs.: esse método só deve ser usado ao término
     * da partida.
     */
    private void verificaEmpate()
    {
        if(!jogadorAtual.verificaXeque())
        {
           //indica que não há vencedor, pois deu empate: 
           jogadorVencedor = null;
        }
    }
    
    /**
     * Verifica se o jogo terminou, então declara fim de jogo.
     * Obs.: este método deve ser usado deve ser usado depois do
     * movimento, e da atualização dos turnos.
     */
    private void verificaFimDeJogo()
    {
        if(jogadorAtual.getNumeroDeJogadasPossiveis() == 0)
        {
            //informa fim de jogo:
            fimDeJogo = true;
            //se o jogador atual que perdeu, é o branco, o preto é o vencedor; se for o preto, o branco é o vencedor:
            jogadorVencedor = (jogadorAtual.getNome() == Jogador.NOME_JOGADOR_BRANCO) ? jogadorPreto : jogadorBranco;
            //verifica se houve empate:
            verificaEmpate();
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
        //atualiza o jogador atual e o próximo jogador:
        atualizaJogadorAtual();
    }
    
    /**
     * Seleciona uma peça do jogador atual(que tem o turno
     * neste momento) para ser movida, e informa se a 
     * peça selecionada é válida.
     * 
     * @param xOrigem A posição x da casa origem da
     * peça selecionada.
     * @param yOrigem A posição y da casa origem da
     * peça selecionada.
     * @return true se a seleção for válida, false do
     * contrário.
     */
    public boolean selecionaPeca(int xOrigem, int yOrigem)
    {
        //true se a seleção for válida, false do contrário:
        boolean selecao = false;
        //verifica se o jogo ainda não acabou:
        if(!fimDeJogo)
        {
            //seleciona a peça:
            selecao = jogadorAtual.selecionaPeca(xOrigem, yOrigem);
        }
        //informa se a seleção foi válida:
        return selecao;
    }
    
    /**
     * Move a peça selecionada do jogador atual(que tem o turno neste momento), e 
     * informa se o movimento foi válido ou não.
     * Obs.: é necessário selecionar uma peça antes de usar esse método. Esta versão do 
     * método "mover" deve ser usada para qualquer tipo de movimento, exceto movimentos
     * de promoção.
     * 
     * @param xDestino A posição x da casa destino da peça.
     * @param yDestino A posição y da casa destino da peça.
     * 
     * @return true se o movimento foi válido, false do contrário.
     */
    public boolean mover(int xDestino, int yDestino)
    {
        //informa movimento bem sucedido(true) ou mal sucedido(false):
        boolean movimento = false;
        //verifica se o jogo não acabou:
        if(!fimDeJogo)
        {
            //move a peça selecionada até o destino especificado:
            movimento = jogadorAtual.moverPeca(xDestino, yDestino);
            //atualiza o turnos do jogadores:
            atualizaTurno();
            //verifica se o jogo acabou:
            verificaFimDeJogo();
        }
        //informa se o movimento foi bem sucedido:
        return movimento;
    }
    
    /**
     * Move a peça selecionada do jogador atual(que tem o turno neste momento), tranforma
     * a peça no tipo especificado, e informa se o movimento foi concluído ou cancelado.
     * Obs.: é necessário selecionar uma peça antes de usar esse método. Esta versão do 
     * método "mover" deve ser usada apenas para movimento de promoção, de forma que é
     * necessário verificar antes, se o movimento especificado é um movimento de promoção
     * válido.
     * 
     * @param xDestino A posição x da casa destino da peça.
     * @param yDestino A posição y da casa destino da peça.
     * @param tipo O tipo de peça a ser transformada a peça movida.
     * Obs.: Esse tipo deve ser especificado, usando as constantes
     * de tipo da classe Peca, o tipo rei não pode ser escolhido.
     * Caso deseje cancelar o movimento, deve ser passado um tipo
     * peão como parâmetro. 
     * @return true se o movimento concluído, false se foi cancelado.
     */
    public boolean mover(int xDestino, int yDestino, int tipo)
    {
        //informa movimento bem sucedido(true) ou mal sucedido(false):
        boolean movimento = false;
        //verifica se o jogo não acabou:
        if(!fimDeJogo)
        {
            movimento = jogadorAtual.moverPeca(xDestino, yDestino, tipo);
            //atualiza o turnos do jogadores:
            atualizaTurno();
            //verifica se o jogo acabou:
            verificaFimDeJogo();
        }
        //informa se o movimento foi bem sucedido:
        return movimento;
    }
    
    /**
     * Faz a IA mover uma peça.
     * Obs.: este método só deve ser usado
     * no modo de jogo vsIA, e deve ser usado
     * sempre que for a vez da IA(jogador preto).
     * Obs.: o movimento da IA sempre será válido.
     * 
     * @return o movimento feito pela IA, ou null
     * caso a IA não tem movimento possível, ou por
     * mal uso do método(usado no modo de jogo errado).
     */
    public Movimento moverIA()
    {
        Movimento movimento = null;
        if(modoVsIA)
        {
            //obtém a IA:
            JogadorIA ia = (JogadorIA)jogadorPreto;
            //faz a IA mover uma peça:
            movimento = ia.mover();
            //atualiza o turnos do jogadores:
            atualizaTurno();
            //verifica se o jogo acabou:
            verificaFimDeJogo();
        }   
        
        return movimento;
    }
    
    /**
     * Informa se o movimento especificado é um
     * movimento de promoção válido.
     * Obs.: para usar este método, é necessário
     * selecionar uma peça antes.
     * 
     * @return true se o movimento especificado
     * for um movimento de promoção válido, false
     * do contrário.
     */
    public boolean verificaPromocao(int xDestino, int yDestino)
    {
        return jogadorAtual.verificaPromocao(xDestino, yDestino);
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
     * @return O jogador vencedor do jogo, 
     * ou null em caso de empate. 
     */
    public Jogador getJogadorVencedor()
    {
        return jogadorVencedor;
    }
    
    /**
     * Obtém o jogador branco.
     * 
     * @return O jogador branco.
     */
    public Jogador getJogadorBranco()
    {
        return jogadorBranco;
    }
    
    /**
     * Obtém o jogador preto.
     * 
     * @return O jogador preto.
     */
    public Jogador getJogadorPreto()
    {
        return jogadorPreto;
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
    
    /**
     * Obtém uma cópia do jogo atual.
     * 
     * @return A cópia do jogo atual.
     */
    public Jogo getCopia()
    {
        byte[] jogoEmByte = converterEmByte();
        return carregarJogo(jogoEmByte);
    }
    
    /**
     * Salva o jogo, e informa se o processo foi bem-sucedido.
     * 
     * @return true se foi bem-sucedido, false do contrário.
     */
    public boolean salvarJogo()
    {
        try
        {
            FileOutputStream gravadorDeArquivo = new FileOutputStream("save/save.dat");
            ObjectOutputStream gravadorDeObjeto = new ObjectOutputStream(gravadorDeArquivo);
            gravadorDeObjeto.writeObject(this);
            gravadorDeObjeto.flush();
            gravadorDeObjeto.close();
            gravadorDeArquivo.flush();
            gravadorDeArquivo.close();
            
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    /**
     * Carrega o jogo salvo.
     * 
     * @return O jogo salvo, ou null 
     * em caso de problemas ao acessar
     * o jogo salvo. 
     */
    public Jogo carregarJogo()
    {
        Jogo jogoSalvo = null;
        try
        {
            FileInputStream leitorDeArquivo = new FileInputStream("save/save.dat");
            ObjectInputStream leitorDeObjeto = new ObjectInputStream(leitorDeArquivo);
            jogoSalvo = (Jogo)leitorDeObjeto.readObject();
            leitorDeArquivo.close();
            leitorDeObjeto.close();
            
            return jogoSalvo;
        }
        catch(Exception e)
        {
            return jogoSalvo;
        }
    }
}
