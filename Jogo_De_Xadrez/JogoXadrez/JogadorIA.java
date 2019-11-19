import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;
/**
 * Descrição: Uma IA para jogar como adversário.
 * 
 * @author (João Hudson) 
 * @version (24/10/2018)
 */
public class JogadorIA extends Jogador implements Serializable
{
    private Random rand;
    private ArrayList<Movimento> movimentosPrioridade0;
    private ArrayList<Movimento> movimentosPrioridade1;
    private ArrayList<Movimento> movimentosPrioridade2;
    private ArrayList<Movimento> movimentosPrioridade3;
    private ArrayList<Movimento> movimentosPrioridade4;
    private ArrayList<Movimento> movimentosPrioridade5;
    private ArrayList<Movimento> movimentosPrioridade6;
    
    /**
     * Cria uma IA que joga automaticamente.
     * 
     * @param jogo O jogo atual.
     * @param tabuleiro O tabuleiro do jogo.
     */
    public JogadorIA(Jogo jogo, Tabuleiro tabuleiro)
    {
        super(jogo, Jogador.NOME_JOGADOR_PRETO, tabuleiro, false);
        rand = new Random();
        movimentosPrioridade0 = new ArrayList<Movimento>();
        movimentosPrioridade1 = new ArrayList<Movimento>();
        movimentosPrioridade2 = new ArrayList<Movimento>();
        movimentosPrioridade3 = new ArrayList<Movimento>();
        movimentosPrioridade4 = new ArrayList<Movimento>();
        movimentosPrioridade5 = new ArrayList<Movimento>();
        movimentosPrioridade6 = new ArrayList<Movimento>();
    }
    
    /**
     * Limpa todas as listas de prioridade.
     */
    private void limpaListas()
    {
        movimentosPrioridade0.clear();
        movimentosPrioridade1.clear();
        movimentosPrioridade2.clear();
        movimentosPrioridade3.clear();
        movimentosPrioridade4.clear();
        movimentosPrioridade5.clear();
        movimentosPrioridade6.clear();
    }
    
    /**
     * Move a peça especificada por suas coordenadas da IA simulação especificada, e em caso
     * de promoção promove aleatoriamente a peça. Não é selecionado movimento que coloque o
     * próprio rei em xeque.
     * 
     * @param xOrigem A posição x da origem da peça.
     * @param yOrigem A posição y da origem da peça.
     * @param xDestino A posição x do destino.
     * @param yDestino A posição y do destino.
     * @param simulacaoIA A IA da simulação.
     */
    private void movimentoDeTeste(int xOrigem, int yOrigem, int xDestino, int yDestino, Jogador simulacaoIA)
    {
        simulacaoIA.selecionaPeca(xOrigem, yOrigem);
        if(simulacaoIA.verificaPromocao(xDestino, yDestino))
        {
            simulacaoIA.moverPeca(xDestino, yDestino, selecionaPromocao());
        }
        else
        {
            simulacaoIA.moverPeca(xDestino, yDestino);
        }
    }
    
    /**
     * Registra o movimento de ataque(que bote ele em xeque)
     * ao rei inimigo.
     * 
     * @param movimento O movimento da IA a ser analisado.
     * @param simulacao A copia don jogo atual.
     */
    private void registraAmeacaAoRei(Movimento movimento, Jogo simulacao)
    {
        //reseta a simulação:
        simulacao = simulacao.getCopia();
        //obtém o jogador equivalente a IA da simulação:
        Jogador jogadorSIA = simulacao.getJogadorPreto();
        //obtém o jogador inimigo da simulação:                    
        Jogador jogadorSInimigo = simulacao.getJogadorBranco();
        //pega as coordenadas de origem e destino do movimento:
        Casa origem = movimento.getOrigem();
        Casa destino = movimento.getDestino();
        int xOrigem = origem.getX();
        int yOrigem = origem.getY();
        int xDestino = destino.getX();
        int yDestino = destino.getY();
        //move a peça da IA simulação:
        movimentoDeTeste(xOrigem, yOrigem, xDestino, yDestino, jogadorSIA);
        //verifica se o movimento resultou em xeque do rei do jogador inimigo da simulação:
        if(jogadorSInimigo.verificaXeque())
        {
            //adiciona este movimento a lista de movimentos proibidos:
            movimentosPrioridade0.add(movimento);
        }
    }
    
    /**
     * Registra os movimentos da lista dada, nas listas
     * de prioridades seguindo seu critério.
     */
    private void registraPrioridades(ArrayList<Movimento> movimentos)
    {
        //Obtém a simulação do jogo no seu estado atual:
        Jogo simulacao = getJogo();
        for(Movimento movimento : movimentos)
        {
            //registra movimentos que ameacem o rei do inimigo com prioridade máxima:
            registraAmeacaAoRei(movimento, simulacao);
            //registra os demais movimentos segundo um cirtério de prioridade:
            switch(movimento.getTipo())
            {
                case Movimento.PROMOCAO_OFENSIVA:
                    movimentosPrioridade1.add(movimento);
                    break;
                    
                case Movimento.PROMOCAO_PASSIVA:
                    movimentosPrioridade2.add(movimento);
                    break;
                    
                case Movimento.EN_PASSANT:
                    movimentosPrioridade3.add(movimento);
                    break;
                    
                case Movimento.ROQUE:
                    movimentosPrioridade4.add(movimento);
                    break;
                    
                case Movimento.ATAQUE:
                    movimentosPrioridade5.add(movimento);
                    break;
                    
                case Movimento.COMUM:
                    movimentosPrioridade6.add(movimento);
                    break;
            }
        }
    }
    
    /**
     * Registra todos os movimentos das peças nas suas devidas listas.
     */
    private void registraMovimentos()
    {
        ArrayList<Peca> pecas = getPecas();
        //limpa as possibilidades de movimentos antigos:
        limpaListas();
        for(Peca peca : pecas)
        {
            int x = peca.getCasa().getX();
            int y = peca.getCasa().getY();
            //seleciona cada peça da IA
            selecionaPeca(x,y);
            //registra os movimentos de cada peça:
            registraPrioridades(getMovimentos());
        }
    }
    
    /**
     * Seleciona um movimento.
     * 
     * @return O movimento selecionado, ou null
     * caso não exista movimento válido.
     */
    private Movimento selecionaMovimento()
    {
        registraMovimentos();
        Movimento movimento = null;
        
        if(movimentosPrioridade0.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade0.size());
            movimento = movimentosPrioridade0.get(index);
        }
        else if(movimentosPrioridade1.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade1.size());
            movimento = movimentosPrioridade1.get(index);
        }
        else if(movimentosPrioridade2.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade2.size());
            movimento = movimentosPrioridade2.get(index);
        }
        else if(movimentosPrioridade3.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade3.size());
            movimento = movimentosPrioridade3.get(index);
        }
        else if(movimentosPrioridade4.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade4.size());
            movimento = movimentosPrioridade4.get(index);
        }
        else if(movimentosPrioridade5.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade5.size());
            movimento = movimentosPrioridade5.get(index);
        }
        else if(movimentosPrioridade6.size() > 0)
        {
            int index = rand.nextInt(movimentosPrioridade6.size());
            movimento = movimentosPrioridade6.get(index);
        }
        
        return movimento;
    }
    
    /**
     * Seleciona um tipo para promoção.
     */
    private int selecionaPromocao()
    {
        switch(rand.nextInt(3))
        {
            case 0:
                return Peca.DAMA_PRETA;
                
            case 1:
                return Peca.TORRE_PRETA;
                
            case 2:
                return Peca.BISPO_PRETO;
                
            case 3:   
                return Peca.CAVALO_PRETO;
                
            default:
            //trecho não executado:
                return 0;
        }
    }
    
    /**
     * Faz com que a IA mova uma peça.
     * Obs.: o movimento feito pela IA
     * sempre é válido enquanto houver 
     * movimento possível para ser feitor.
     * 
     * @return O movimento realizado, pode
     * retorna null caso não exista movimento
     * possível para ser feito pela IA.
     */
    public Movimento mover()
    {
        Movimento movimento = selecionaMovimento();
        if(movimento != null)
        {
            Casa origem = movimento.getOrigem();
            Casa destino = movimento.getDestino();
            int tipo = movimento.getTipo();
            int xOrigem = origem.getX();
            int yOrigem = origem.getY();
            int xDestino = destino.getX();
            int yDestino = destino.getY();
            
            selecionaPeca(xOrigem, yOrigem);
            if(verificaPromocao(xDestino, yDestino))
            {
                moverPeca(xDestino, yDestino, selecionaPromocao());
            }
            else
            {
                moverPeca(xDestino, yDestino);
            }
        }
        
        return movimento;
    }
}
