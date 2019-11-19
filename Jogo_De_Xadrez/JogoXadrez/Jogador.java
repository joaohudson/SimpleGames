import java.util.ArrayList;
import java.io.Serializable;
/**
 * Descrição: Jogador do jogo, que possui
 * seu conjunto individual de peças, e seu
 * turno.
 * 
 * @author (João Hudson) 
 * @version (18/10/2018)
 */
public class Jogador implements Serializable
{
    //nomes do jogadores:
    public static final String NOME_JOGADOR_BRANCO = "Jogador Branco";
    public static final String NOME_JOGADOR_PRETO = "Jogador Preto";
    
    //nome deste jogador:
    private String nome;
    //peças deste jogador:
    private ArrayList<Peca> pecas;
    //histórico de movimentos do jogador:
    private ArrayList<Movimento> historico;
    //informa quando é o turno desse jogador:
    private boolean minhaVez;
    //informa se o rei deste jogador já passou por xeque:
    private boolean reiPassouPorXeque;
    //a peça selecionada para ser movida:
    private Peca pecaSelecionada;
    //tabuleiro do jogo:
    private Tabuleiro tabuleiro;
    //jogo atual:
    private Jogo jogo;
    
    /**
     * Cria um jogador, inicialmente com lista de peças vazia.
     * 
     * @param jogo O jogo atual.
     * @param nome O nome do jogador. 
     * Obs.: o nome do jogador deve ser especificado
     * pelas constantes de nome da classe Jogador.
     * @param tabuleiro O tabuleiro do jogo.
     * @param minhaVez Se é a vez deste jogador ou não.
     */
    public Jogador(Jogo jogo, String nome, Tabuleiro tabuleiro, boolean minhaVez)
    {
        //recebe um nome:
        this.nome = nome;
        //recebe o tabuleiro do jogo:
        this.tabuleiro = tabuleiro;
        //cria uma lista para suas peças:
        pecas = new ArrayList<Peca>();
        //cria a o histórico de movimentos do jogador:
        historico = new ArrayList<Movimento>();
        //define se é vez do jogador ou não:
        this.minhaVez = minhaVez;
        //indica que inicialmente não há peça selecionada:
        pecaSelecionada = null;
        //recebe o jogo atual:
        this.jogo = jogo;
        //informa que inicialmente o rei não passou por xeque:
        reiPassouPorXeque = false;
    }
    
    /**
     * Obtém o Rei do jogador.
     * 
     * @return O rei deste jogador.
     */
    private Rei pesquisaRei()
    {
        for(Peca peca : pecas)
        {
            //se a instância apontada for do tipo Rei, converte sua referência para o tipo Rei e a retorna:
            if(peca instanceof Rei)
            {
                return (Rei)peca;
            }
        }
        //esse trecho não deve ser executado:
        return null;
    }
    
    /**
     * Limita o tamanho do histórico a no máximo 1000.
     */
    private void limitadorDeHistorico()
    {
        while(historico.size() > 1000)
        {
            historico.remove(0);
        }
    }
    
    /**
     * Adiciona o movimento especificado ao histórico do jogador,
     * guardando no máximo 1000 movimentos, após atingir esse
     * limite os movimentos mais antigos serão descartados, para
     * guardar os novos.
     * 
     * @param movimento O último movimento realizado.
     */
    private void addHistorico(Movimento movimento)
    {
        //limita o histórico a no máximo 1000:
        limitadorDeHistorico();
        //adiciona o movimento ao histórico do jogador:
        historico.add(movimento);
    }
    
    /**
     * Obtém um movimento da lista de possibilidades
     * de acordo com o destino especificado, ou seja,
     * um movimento que leve a peça para esse destino.
     * Obs.: para usar esse método, é necessário 
     * selecionar uma peça antes.
     * 
     * @param destino O destino do movimento.
     * @return O movimento que leva ao destino
     * especificado, ou null se o movimento for
     * inválido.
     */
    private Movimento pesquisaMovimento(Casa destino)
    {
        //Obtém a lista de movimentos possíveis da peça selecionada, sem os movimentso que possa deixar o rei em xeque:
        ArrayList<Movimento> movimentos = getMovimentos();
        //itera pela lista:
        for(Movimento movimento : movimentos)
        {
            //verifica se o movimento leva ao destino especificado:
            if(destino == movimento.getDestino())
            {
                //retorna o movimento:
                return movimento;
            }
        }
        //retorna null informando que não há movimento válido que leve a esse destino:
        return null;
    }
    
    /**
     * Move a peça segundo o movimento de ataque
     * designado.
     * 
     * @param movimento O objeto que contém as informações
     * do movimento a ser efetuado.
     */
    private void movimentoDeAtaque(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        
        Peca peca = destino.getPeca();
        
        peca.descartar();
        
        pecaSelecionada.mover(destino);
    }
    
    /**
     * Move a peça segundo o movimento comum
     * designado.
     * 
     * @param movimento O objeto que contém as informações
     * do movimento a ser efetuado.
     */
    private void movimentoComum(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        
        pecaSelecionada.mover(destino);
    }
    
    /**
     * Move o rei, e a torre correspondente, fazendo o movimento
     * roque.
     * 
     * @param movimento O objeto que contém as informações
     * do movimento a ser efetuado.
     */
    private void movimentoRoque(Movimento movimento)
    {
        Casa destinoRei = movimento.getDestino();
        Casa destinoTorre = movimento.getDestinoTorre();
        Casa origemTorre = movimento.getCasaTorre();
        Peca torre = origemTorre.getPeca();
        
        pecaSelecionada.mover(destinoRei);
        torre.mover(destinoTorre);
    }
    
    /**
     * Move o peão para comer uma peão que andou 2
     * casas e parou ao seu lado, fazendo o movimento
     * en passant.
     * 
     * @param movimento O objeto que contém as informações
     * do movimento a ser efetuado. 
     */
    private void movimentoEnPassant(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        Casa casaAlvo =  movimento.getCasaAlvo();
        Peca peaoAlvo = casaAlvo.getPeca();
        
        peaoAlvo.descartar();
        pecaSelecionada.mover(destino);
    }
    
    /**
     * Move a peça segundo o movimento de promoção
     * passiva designado(movimento de promoção onde
     * o peão não come outra peça).
     * 
     * @param movimento O objeto que contém as informações
     * do movimento a ser efetuado.
     */
    private void movimentoPromocaoPassiva(Movimento movimento)
    {
        movimentoComum(movimento);
    }
    
    /**
     * Move a peça segundo o movimento de promoção
     * ofensiva designado(movimento de promoção onde
     * o peão come outra peça).
     * 
     * @param movimento O objeto que contém as informações
     * do movimento a ser efetuado.
     */
    private void movimentoPromocaoOfensiva(Movimento movimento)
    {
        movimentoDeAtaque(movimento);
    }
    
    /**
     * Seleciona um tipo de movimento de acordo com os dados
     * especificados no objeto movimento, e então efetua o movimento.
     * 
     * @param movimento O objeto que contém os dados do movimento.
     */
    private void selecionaMovimento(Movimento movimento)
    {
        //verifica qual é o tipo do movimento, então efetua o mesmo:
        switch(movimento.getTipo())
        {
            case Movimento.ATAQUE:
                movimentoDeAtaque(movimento);
                break;
                
            case Movimento.COMUM:
                movimentoComum(movimento);
                break;
                
            case Movimento.EN_PASSANT:
                movimentoEnPassant(movimento);
                 break;
            
            case Movimento.PROMOCAO_OFENSIVA:
                movimentoPromocaoOfensiva(movimento);
                break;
                
            case Movimento.PROMOCAO_PASSIVA:
                movimentoPromocaoPassiva(movimento);
                break;
                
            case Movimento.ROQUE:
                movimentoRoque(movimento);
        }
    }
    
    /**
     * Verifica se o rei pode ser comido
     * por uma outra peça inimiga.
     * 
     * @param rei O rei do jogador.
     * @param inimigo A peça que pode está ameaçando
     * a vítima.
     * @return true se esta peça ameaçar o rei, false
     * do contrário.
     */
    private boolean verificaPerigo(Rei rei, Peca inimigo)
    {
        //obtém a lista de movimentos da peça que talvez possa comer o rei:
        ArrayList<Movimento> movimentos = inimigo.getMovimentos();
        //obtém a casa do rei
        Casa casaDoRei = rei.getCasa();
        for(Movimento movimento : movimentos)
        {
            //verifica se esse movimento ameaça o rei:
            if(movimento.getDestino() == casaDoRei)
            {
                //informa que o rei está em perigo:
                return true;
            }
        }
        //informa que o rei não está sob ameaça desta peça:
        return false;
    }
    
    /**
     * Obtém um movimento da lista de possibilidades
     * de acordo com o destino especificado, ou seja,
     * um movimento que leve a peça para esse destino.
     * Obs.: para usar esse método, é necessário 
     * selecionar uma peça antes.
     * 
     * @param destino O destino do movimento.
     * @return O movimento que leva ao destino
     * especificado, ou null se o movimento for
     * inválido.
     */
    private Movimento pesquisaMovimentoTeste(Casa destino)
    {
        //Obtém a lista de movimentos possíveis da peça selecionada:
        ArrayList<Movimento> movimentos = pecaSelecionada.getMovimentos();
        //itera pela lista:
        for(Movimento movimento : movimentos)
        {
            //verifica se o movimento leva ao destino especificado:
            if(destino == movimento.getDestino())
            {
                //retorna o movimento:
                return movimento;
            }
        }
        //retorna null informando que não há movimento válido que leve a esse destino:
        return null;
    }
    
    /**
     * Move a peça das coordenadas origem especificadas, até as coordenadas
     * destino especificadas, sem fazer promoção, e nem evitar xeque.
     * Obs.: O movimento especificado deve ser válido segundo as regras,
     * excetuando a regra do xeque.
     * 
     * @param xOrigem A coordenada x da casa origem.
     * @param yOrigem A coordenada y da casa origem.
     * @param xOrigem A coordenada x da casa destino.
     * @param yOrigem A coordenada y da casa destino.
     */
    protected void movimentoDeTeste(int xOrigem, int yOrigem, int xDestino, int yDestino)
    {
        pecaSelecionada = getPeca(xOrigem, yOrigem);
        Casa destino = tabuleiro.getCasa(xDestino, yDestino);
        Movimento movimento = pesquisaMovimentoTeste(destino);
        selecionaMovimento(movimento);
    }
    
    /**
     * Remove todos os possíveis movimentos de uma peca especificada
     * que ameace o rei deste jogador, e então retorna a lista filtrada
     * das possibilidades.
     * 
     * @return A lista de possibilidades da peça especificada,
     * com os movimentos que colocam o rei em xeque removidos.
     */
    private ArrayList<Movimento> protegeRei(Peca peca)
    {
        //obtém os possíveis movimentos da peça:
        ArrayList<Movimento> movimentos = peca.getMovimentos();
        //cria uma lista para os movimentos proibidos:
        ArrayList<Movimento> movimentosProibidos = new ArrayList<>();
        for(Movimento movimento : movimentos)
        {
            //obtém uma cópia do jogo:
            Jogo simulacao = jogo.getCopia();
            //obtém o jogador da simulação equivalente a este jogador:
            Jogador jogadorS = getNome().equals(Jogador.NOME_JOGADOR_BRANCO) ? simulacao.getJogadorBranco() :
                                simulacao.getJogadorPreto();
            //obtém as coordenadas de origem e destino do movimento:
            Casa origem = movimento.getOrigem();
            Casa destino = movimento.getDestino();
            int xOrigem = origem.getX();
            int yOrigem = origem.getY();
            int xDestino = destino.getX();
            int yDestino = destino.getY();
            //move a peça da simulação:
            jogadorS.movimentoDeTeste(xOrigem, yOrigem, xDestino, yDestino);
            //verifica se o movimento resultou em xeque do rei do jogador:
            if(jogadorS.verificaXeque())
            {
                //adiciona este movimento a lista de movimentos proibidos:
                movimentosProibidos.add(movimento);
            }
        }
        //elimina todos os movimentos da lista da peca:
        for(Movimento movimento : movimentosProibidos)
        {
            movimentos.remove(movimento);
        }
        
        return movimentos;
    }
    
    /**
     * Caso o rei seja em posto em xeque, registra essa ocorrência.
     */
    private void registraPassagemPorXeque()
    {
        if(verificaXeque())
        {
            reiPassouPorXeque = true;
        }
    }
    
    /**
     * Transforma a peça selecionada em uma peça especificada.
     * Obs.: esse método só deve ser usado quando a peça selecionada
     * for um peão, e estiver na condição correta para ser promovida.
     * 
     * @param tipo O tipo da peça. Obs.: Esse tipo deve ser
     * especificado pelas constantes definidas na classe
     * Peca. Para cancelar a transformação, deve ser passado uma
     * constante do tipo de tipo peão.
     */
    private void transformarPeao(int tipo)
    {
        Peao peaoPromocao = (Peao)pecaSelecionada;
        peaoPromocao.transformar(tipo);
    }
    
    /**
     * Adiciona uma peça à lista de peças
     * do jogador.
     * 
     * @param peca A peça a ser adicionada ao jogador.
     */
    public void addPeca(Peca peca)
    {
        pecas.add(peca);
    }
    
    /**
     * Informa se o rei deste jogador está em xeque.
     * 
     * @return true se o rei estiver em xeque, false
     * do contrário.
     */
    public boolean verificaXeque()
    {
        //obtém o rei deste jogador:
        Rei rei = pesquisaRei();
        //obtém o jogador inimgo:
        Jogador inimigo = getJogadorInimigo();
        //obtém as peças inimigas:
        ArrayList<Peca> pecasInimigas = inimigo.getPecas();
        //analisa todas as peças inimigas:
        for(Peca pecaInimiga : pecasInimigas)
        {
            //analisa se cada peça pode ameaçar o rei:
            if(verificaPerigo(rei, pecaInimiga))
            {
                //informa que o rei está em xeque:
                return  true;
            }
        }
        //informa que o rei não está em xeque:
        return false;
    }

    /**
     * Remove a peça designada da lista de peças
     * do jogador.
     * 
     * @param peca A peça a ser removida da lista.
     */
    public void removePeca(Peca peca)
    {
        //remove a peça:
        pecas.remove(peca);
    }
    
    /**
     * Recebe o turno
     */
    public void ganhaVez()
    {
        minhaVez = true;
    }
    
    /**
     * Perde o turno.
     */
    public void perdeVez()
    {
        minhaVez = false;
    }
    
    /**
     * Seleciona uma peça do jogador para ser movida,
     * e informa a seleção foi válida.
     * 
     * @param xPosition A posição x da peça.
     * @param yPosition A posição y da peça.
     * @return true se a peça selecionada for válida,
     * false do contrário. 
     */
    public boolean selecionaPeca(int xPosition, int yPosition)
    {
        //caso o rei seja em posto em xeque, registra essa ocorrência:
        registraPassagemPorXeque();
        //obtém a peça selecionada:
        pecaSelecionada = getPeca(xPosition, yPosition);
        //informa se a peça selecionada pertence ao jogador:
        return pecaSelecionada != null;
    }
    
    /**
     * Move a peça até o destino designado, informa se o movimento foi válido,
     * e passa a vez caso o movimento tenha sido válido. 
     * Obs.: é necessário selecionar uma peça antes de movê-la. Esta versão do método
     * "moverPeca" deve ser ultilizada para qualquer tipo de movimento, exceto movimentos
     * de promoção.
     * 
     * @param xDestino A posição x da casa destino da peça.
     * @param yDestino A posição y da casa destino da peça.
     * @return true se o movimento foi válido, false do contrário.
     */
    public boolean moverPeca(int xDestino, int yDestino)
    {
        //informa movimento bem sucedido(quando verdadeiro):
        boolean movimentoValido = false;
        //obtém a casa destino da peça:
        Casa destino = tabuleiro.getCasa(xDestino, yDestino);
        //condição para peça inválida, ou movimento inválido para esta versão do método:
        if(pecaSelecionada == null || verificaPromocao(xDestino, yDestino))
        {
            //informa movimento inválido:
            movimentoValido = false;
        }
        //condição para mover a peça:
        else if(minhaVez)
        {
            //obtém o movimento da peça selecionada, que leva ao destino designado:
            Movimento movimento = pesquisaMovimento(destino);
            //verifica se o movimento foi válido:
            if(movimento != null)
            {
                //seleciona e efetua um tipo de movimento, de acordo com o movimento escolhido:
                selecionaMovimento(movimento);
                //informa que o movimento foi válido:
                movimentoValido = true;
                //adiciona o movimento ao histórico do jogador:
                addHistorico(movimento);
                //passa a vez:
                perdeVez();
            }
        }
        //informa se o movimento foi válido ou não:
        return movimentoValido;
    }
    
    /**
     * Move a peça até o destino designado, transforma a peça no tipo especificado,
     * e passa a vez caso o movimento não tenha sido cancelado.
     * Obs.: é necessário selecionar uma peça antes de movê-la. Esta versão do método
     * "moverPeca" deve ser usada apenas para movimentos de promoção, de forma que antes é
     * necessário verificar, se o movimento a ser feito é um movimento de promoção válido.
     * 
     * @param xDestino A posição x da casa destino da peça.
     * @param yDestino A posição y da casa destino da peça.
     * @param tipo O tipo de peça a ser transformada a peça movida.
     * Obs.: Esse tipo deve ser especificado, usando as constantes
     * de tipo da classe Peca, o tipo rei não pode ser escolhido.
     * Caso deseje cancelar o movimento, deve ser passado um tipo
     * peão como parâmetro.
     * @return true se o movimento de promoção foi concluido, false
     * se foi cancelado.
     */
    public boolean moverPeca(int xDestino, int yDestino, int tipo)
    {
        //informa se o movimento de promoção foi concluído, ou cancelado:
        boolean promocao = false;
        //obtém a casa destino da peça:
        Casa destino = tabuleiro.getCasa(xDestino, yDestino);
        //condição para mover a peça:
        if(verificaPromocao(xDestino, yDestino))
        {
            //obtém o movimento da peça selecionada, que leva ao destino designado:
            Movimento movimento = pesquisaMovimento(destino);
            //verifica se o movimento não foi cancelado:
            if((tipo != Peca.PEAO_BRANCO) && (tipo != Peca.PEAO_PRETO))
            {
                //seleciona e efetua um tipo de movimento, de acordo com o movimento escolhido:
                selecionaMovimento(movimento);
                //transforma o peão:
                transformarPeao(tipo);
                //informa que o movimento foi válido:
                promocao = true;
                //adiciona o movimento ao histórico do jogador:
                addHistorico(movimento);
                //passa a vez:
                perdeVez();
            }
        }
        //informa se o movimento foi válido ou não:
        return promocao;
    }
    
    /**
     * Informa se o movimento especificado, é um movimento
     * de promoção válido.
     * Obs.: para usar este método, é necessário
     * selecionar uma peça antes.
     * 
     * @return true se o movimento especificado, é um
     * movimento de promoção válido, false do contrário.
     */
    public boolean verificaPromocao(int xDestino, int yDestino)
    {
        //true se o movimento especificado, for um movimento de promoção válido:
        boolean promocao = false;
        //obtém o destino especificado:
        Casa destino = tabuleiro.getCasa(xDestino, yDestino);
        //verifica se foi selecionada uma peça:
        if(pecaSelecionada != null && minhaVez)
        {
            //obtém o movimento especificado:
            Movimento movimento = pesquisaMovimento(destino);
            //verifica se foi encontrado o movimento, na lista de possibilidades da peça selecionada:
            if(movimento != null)
            {
                //obtém o tipo do movimento:
                int tipo = movimento.getTipo();
                //verifica se é um movimento de promoção:
                promocao = tipo == Movimento.PROMOCAO_OFENSIVA || tipo == Movimento.PROMOCAO_PASSIVA;
            }
        }
        //retorna true se o movimento for um movimento de promoção válido, false contrário:
        return promocao;
    }
    
    /**
     * Obtém uma peça do jogador pelas coordenadas da casa dela.
     * 
     * @param xPosition A posição x da casa da peça desejada.
     * @param yPosition A posição y da casa da peça desejada.
     * @return Uma peça do jogador, ou null caso as coordenadas
     * não forem da casa de alguma peça do jogador.
     */
    public Peca getPeca(int xPosition, int yPosition)
    {
        //itera por todo array list procurando uma peça com corresponda as coordenadas:
        for(Peca peca : pecas)
        {   
            //verifica se a peça corresponde as coordenadas especificadas:
            if(peca.getCasa().getX() == xPosition && peca.getCasa().getY() == yPosition)
            {
                //retorna a peça encontrada:
                return peca;
            }
        }
        //retorna null em caso de coordenadas inválidas:
        return null;
    }
    
    /**
     * Obtém o histórico de movimentos deste jogador.
     * 
     * @return O histórico de movimentos deste jogador.
     */
    public ArrayList<Movimento> getHistorico()
    {
        return historico;
    }
    
    /**
     * Obtém o jogador adversário.
     * 
     * @return O jogador adversário.
     */
    public Jogador getJogadorInimigo()
    {
        if(nome.equals(NOME_JOGADOR_BRANCO))
        {
            return jogo.getJogadorPreto();
        }
        else
        {
            return jogo.getJogadorBranco();
        }
    }
    
    /**
     * Obtém o número de peças atual do jogador.
     * 
     * @return O número de peças atual do jogador.
     */
    public int nPecas()
    {
        return pecas.size();
    }
    
    /**
     * Obtém o nome do jogador.
     * 
     * @return O nome do jogador.
     * Obs.: para indentificar o jogador
     * pelo nome, o nome retornado deve
     * ser interpretado usando as constantes
     * de nome da classe Jogador.
     */
    public String getNome()
    {
        return nome;
    }
    
    /**
     * Informa se é a vez do jogador.
     * 
     * @return true caso seja a vez do jogador, false caso contrário.
     */
    public boolean getMinhaVez()
    {
        return minhaVez;
    }
    
    /**
     * Obtém a lista de peças deste jogador.
     * 
     * @return Um array list das peças deste jogador.
     */
    public ArrayList<Peca> getPecas()
    {
        return pecas;
    }
    
    /**
     * Obtém o número de jogadas possíveis deste jogador
     * no turno atual.
     * 
     * @return O número de jogadas possíveis deste jogador
     * no turno atual.
     */
    public int getNumeroDeJogadasPossiveis()
    {
        int numeroDeJogadasPossiveis = 0;
        for(Peca peca : pecas)
        {
            //incrementa a contagem
            numeroDeJogadasPossiveis += protegeRei(peca).size();
        }
        
        return numeroDeJogadasPossiveis;
    }
    
    /**
     * Obtém os possíveis movimentos da peça selecionada,
     * excluindo qualquer movimento que deixe o rei em xeque.
     * Obs.: é necessário selecionar uma peça antes de usar
     * este método.
     * 
     * @return A lista de possibilidades de movimentos da
     * peça selecionada.
     */
    public ArrayList<Movimento> getMovimentos()
    {
       return protegeRei(pecaSelecionada); 
    }
    
    /**
     * Verifica se o rei deste jogador passou por um xeque.
     * 
     * @return true se o rei passou ou passa por
     * um xeque,false do contrário.
     */
    public boolean verificaPassagemPorXeque()
    {
        return reiPassouPorXeque;
    }
    
    /**
     * Obtém o jogo atual.
     * 
     * @return O jogo atual.
     */
    public Jogo getJogo()
    {
        return jogo;
    }
}
