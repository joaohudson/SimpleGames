import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Point;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.lang.InterruptedException;

/**
 * Tela do jogo.
 * Respons�vel por reagir aos cliques feitos pelo jogador.
 * 
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author João Hudson.
 */
public class JanelaPrincipal extends JFrame {

    private Jogo jogo;
    private boolean primeiroClique;
    private boolean modoVsIA;
    private CasaGUI casaClicadaOrigem;
    private CasaGUI casaClicadaDestino;
    private ArrayList<Movimento> movimentos;
    private EfeitosSonoros efeitosSonoros;
    
    /**
     * Responde aos cliques realizados no tabuleiro.
     * 
     * @param casaClicada Casa que o jogador clicou.
     */
    public void reagir(CasaGUI casaClicada)
    {
        //verifica se o jogo acabou:
        if(jogo.getFimDeJogo())
        {
            efeitosSonoros.playErro();
        }
        else if (primeiroClique) 
        {
            int xOrigem = casaClicada.getPosicaoX();
            int yOrigem = casaClicada.getPosicaoY();
            if (jogo.selecionaPeca(xOrigem,yOrigem)) {
                //captura o primeiro click:
                casaClicadaOrigem = casaClicada;
                destacaMovimentos(casaClicadaOrigem);
                primeiroClique = false;
            }
            else {
                //alerta que o click foi em uma posição inválida:
                efeitosSonoros.playErro();
            }
        }
        else 
        {
            //captura o segundo click:
            casaClicadaDestino = casaClicada;
            //verifica se o jogador deseja selecionar outra peça:
            if(verificaAliado(casaClicadaOrigem, casaClicadaDestino))
            {
                //atualiza a casa origem:
                casaClicadaOrigem = casaClicadaDestino;
                int xOrigem = casaClicadaOrigem.getPosicaoX();
                int yOrigem = casaClicadaOrigem.getPosicaoY();
                //troca a seleção:
                jogo.selecionaPeca(xOrigem, yOrigem);
                //muda a seleção da peça:
                tabuleiroGUI.removerMarcacoes();
                destacaMovimentos(casaClicadaOrigem);
                //encerra o método:
                return;
            }
            //move a peça:
            moverPeca(casaClicadaDestino.getPosicaoX(), casaClicadaDestino.getPosicaoY());
            tabuleiroGUI.removerMarcacoes();
            primeiroClique = true;
            //atualiza o tabuleiro:
            atualizar();
            //move a IA e atualiza o tabuleiro caso seja o modo de jogo vsIA:
            moverIA();
            //quando o jogo acabar mostra o jogador vitorioso:
            if(jogo.getFimDeJogo())
            {
                //encerra a música de fundo e toca a música de vitória:
                efeitosSonoros.playVitoria();
                //informa qual foi o jogador vencedor:
                printResultado();
            }
        }
    }
    
    /**
     * Verifica se há um peão que deve ser promovido, e então abre
     * um painel de opções para promovê-lo.
     */
    private void moverPeca(int xDestino, int yDestino)
    {
        //quando true indica que o movimento foi válido, o contrário quando false:
        boolean movimentoValido = false;
        //quando true indica que o movimento foi cancelado, o contrário quando false:
        boolean movimentoCancelado = false;
        //jogador atual do jogo:
        Jogador jogadorAtual = jogo.getJogadorAtual();
        //verifica se há uma promoção a ser feita:
        if(jogo.verificaPromocao(xDestino, yDestino))
        {
            int tipo = selecionaPromocao(jogo.getJogadorAtual());
            movimentoCancelado = !jogo.mover(xDestino, yDestino, tipo);
            movimentoValido = true;
        }
        else
        {
            movimentoValido = jogo.mover(xDestino, yDestino);
        }
        //toca o efeito de acordo com o movimetno realizado:
        tocaEfeito(jogadorAtual, movimentoValido, movimentoCancelado);
    }
    
    /**
     * Move a IA e atualiza o tabuleiro,
     * se estiver no modo vsIA e na vez
     * dela.
     */
    private void moverIA()
    {
        if(modoVsIA && jogo.getJogadorPreto().getMinhaVez())
        {
            Movimento movimento = jogo.moverIA();
            tocaEfeito(jogo.getJogadorPreto(), true, false);
            atualizar();
        }
    }
    
    /**
     * Toca um efeito sonoro de acordo com o último movimento
     * do jogador especificado, e com as condições especificadas.
     * 
     * @param jogador O jogador que realizou o último movimento.
     * @param movimentoValido Se o movimento foi válido.
     * @param movimentoCancelado Se o movimento foi cancelado.
     */
    private void tocaEfeito(Jogador jogador, boolean movimentoValido, boolean movimentoCancelado)
    {
        if(movimentoCancelado)
        {
            //não faz nada...
        }
        else if(movimentoValido)
        {
            ArrayList<Movimento> historico = jogador.getHistorico();
            int tamanho = historico.size();
            Movimento movimento = historico.get(tamanho - 1);
            
            switch(movimento.getTipo())
            {
                case Movimento.ATAQUE:
                case Movimento.EN_PASSANT:
                case Movimento.PROMOCAO_OFENSIVA:
                    efeitosSonoros.playAtaque();
                    break;
                    
                case Movimento.COMUM:
                case Movimento.PROMOCAO_PASSIVA:
                case Movimento.ROQUE:
                    efeitosSonoros.playMovimento();
            }
        }
        else
        {
            efeitosSonoros.playErro();
        }
    }
    
    /**
     * Abre um painel de opções para selecionar uma peça.
     * 
     * @param jogadorAtual O jogador que irá promover um de seus
     * peões, para que seja exibido o painel de opções adequado.
     * @return O número inteiro correspondente a peça selecionada.
     * Obs.: Esse número deve ser interpretado usando as constantes
     * da classe Peca.
     */
    private int selecionaPromocao(Jogador jogadorAtual)
    {
        switch(jogadorAtual.getNome())
        {
            case Jogador.NOME_JOGADOR_BRANCO:
                return selecionaPecaBranca();
                
            case Jogador.NOME_JOGADOR_PRETO:
                return selecionaPecaPreta();
            //apenas para compilar, trecho não executado:  
            default:
                return 0;
        }
    }
    
    /**
     * Abre um painel de opções para selecionar uma peça
     * branca.
     * 
     * @return O número inteiro correspondente a peça selecionada.
     * Obs.: Esse número deve ser interpretado usando as constantes
     * da classe Peca. Caso a seleção tenha sido cancelada, retorna
     * o tipo do próprio peão(PEAO_BRANCO).
     */
    private int selecionaPecaBranca()
    {
        //obtém o ícone de promoção:
        Icon promocao = new ImageIcon("IconePromocao/promocao.png");
        //cria um array de objetos:
        Object[] opcoes = new Object[4];
        //adiciona cada ícone no array:
        opcoes[0] = new ImageIcon("PecasXadrez/dama_branca.png");
        opcoes[1] = new ImageIcon("PecasXadrez/torre_branca.png");
        opcoes[2] = new ImageIcon("PecasXadrez/cavalo_branco.png");
        opcoes[3] = new ImageIcon("PecasXadrez/bispo_branco.png");
        //exibe um painel de opções, pedindo para selecionar um dos ícones:
        Object opcao = JOptionPane.showInputDialog(this, "Selecione uma opção:", "Promoção", JOptionPane.INFORMATION_MESSAGE,
                       promocao, opcoes, opcoes[0]);
        //obtém o tipo da peça selecionada e então retorna-o, caso o jogador tenha cancelado a seleção retorna o tipo PEAO_BRANCO:               
        return (opcao == null) ? Peca.PEAO_BRANCO : converteTipo((ImageIcon)opcao);
    }
    
    /**
     * Abre um painel de opções para selecionar uma peça
     * preta.
     * 
     * @return O número inteiro correspondente a peça selecionada.
     * Obs.: Esse número deve ser interpretado usando as constantes
     * da classe Peca.Caso a seleção tenha sido cancelada, retorna
     * o tipo do próprio peão(PEAO_PRETO).
     */
    private int selecionaPecaPreta()
    {
        //obtém o ícone de promoção:
        Icon promocao = new ImageIcon("IconePromocao/promocao.png");
        //cria um array de objetos:
        Object[] opcoes = new Object[4];
        //adiciona cada ícone no array:
        opcoes[0] = new ImageIcon("PecasXadrez/dama_preta.png");
        opcoes[1] = new ImageIcon("PecasXadrez/torre_preta.png");
        opcoes[2] = new ImageIcon("PecasXadrez/cavalo_preto.png");
        opcoes[3] = new ImageIcon("PecasXadrez/bispo_preto.png");
        //exibe um painel de opções, pedindo para selecionar um dos ícones:
        Object opcao = JOptionPane.showInputDialog(this, "Selecione uma opção:", "Promoção", JOptionPane.INFORMATION_MESSAGE,
                       promocao, opcoes, opcoes[0]);
        //obtém o tipo da peça selecionada e então retorna-o, caso o jogador tenha cancelado a seleção retorna o tipo PEAO_BRANCO:               
        return (opcao == null) ? Peca.PEAO_PRETO : converteTipo((ImageIcon)opcao);
    }
    
    /**
     * Obtém o número inteiro correspondente ao ícone da peça
     * especificado.
     * 
     * @param iconPeca O ícone da peça. Obs.: O ícone não pode
     * ser null.
     * @return O número inteiro correspondente ao ícone.
     * Obs.: Esse número deve ser interpretado usando as
     * constantes da classe Peca.
     */
    private int converteTipo(ImageIcon iconPeca)
    {
        //caso o ícone exista, então retorna a constante correspondente:
        switch(iconPeca.getDescription())
        {
            case "PecasXadrez/dama_branca.png":
                return Peca.DAMA_BRANCA;
                
            case "PecasXadrez/torre_branca.png":
                return Peca.TORRE_BRANCA;
                
            case "PecasXadrez/cavalo_branco.png":
                return Peca.CAVALO_BRANCO;
                
            case "PecasXadrez/bispo_branco.png":
                return Peca.BISPO_BRANCO;
                
            case "PecasXadrez/dama_preta.png":
                return Peca.DAMA_PRETA;
                
            case "PecasXadrez/torre_preta.png":
                return Peca.TORRE_PRETA;
                
            case "PecasXadrez/cavalo_preto.png":
                return Peca.CAVALO_PRETO;
                
            case "PecasXadrez/bispo_preto.png":
                return Peca.BISPO_PRETO;
            //apenas para compilar, trecho não executado:    
            default :
                 return 0;
        }
    }
    
    /**
     * Informa o qual foi o resulado da partida.
     */
    private void printResultado()
    {
        //ícone de vtória:
        Icon iconeVitoria = new ImageIcon("IconeVitoria/vitoria.png");
        //ícone de empate:
        Icon iconeEmpate = new ImageIcon("IconeEmpate/empate.png");
        //recebe o jogador vencedor:
        Jogador jogadorVencedor = jogo.getJogadorVencedor();
        if(jogadorVencedor != null)
        {
            //recebe o nome do jogador vencedor:
            String nomeDoVencedor = jogadorVencedor.getNome();
            //informa o nome do jogador vencedor:
            JOptionPane.showConfirmDialog(this,nomeDoVencedor + " Venceu!", "Fim de Jogo", JOptionPane.DEFAULT_OPTION, 
                                         JOptionPane.INFORMATION_MESSAGE, iconeVitoria);
        }
        else
        {
            JOptionPane.showConfirmDialog(this,"Jogo Empatado!", "Fim de Jogo", JOptionPane.DEFAULT_OPTION, 
                                         JOptionPane.INFORMATION_MESSAGE, iconeEmpate);
        }
    }
    
    /**
     * Verifica se as peças das casas especificadas
     * são da mesma cor, ou seja, peças aliadas.
     * 
     * @param casa1 A casa da primeira peça a ser comparada.
     * @param casa2 A casa da segunda peça a ser comparada.
     * @return true se as peças forem da mesma cor, false
     * do contrário.
     */
    private boolean verificaAliado(CasaGUI casa1, CasaGUI casa2)
    {
        int corDaCasa1 = casa1.getCorPeca();
        int corDaCasa2 = casa2.getCorPeca();
        
        return corDaCasa1 == corDaCasa2;
    }
    
    /**
     * Obtém uma casa gráfica equivalente a
     * casa especificada, ou seja, com as 
     * mesmas coordenadas.
     * 
     * @param casa A casa ser "convertida".
     * @return A casa gráfica equivalente
     * a casa especificada.
     */
    private CasaGUI converteCasaGUI(Casa casa)
    {
        int x = casa.getX();
        int y = casa.getY();
        
        return tabuleiroGUI.getCasa(x,y);
    }
    
    /**
     * Obtém uma casa do jogo equivalente a uma
     * casa gráfica especificada, ou seja, com
     * as mesmas coordenadas.
     * 
     * @param casaGUI A casa gráfica a ser "convertida".
     * @return A casa do jogo equivalente a casa gráfica
     * especificada.
     */
    private Casa converteCasa(CasaGUI casaGUI)
    {
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        int x = casaGUI.getPosicaoX();
        int y = casaGUI.getPosicaoY();
        
        return tabuleiro.getCasa(x,y);
    }
    
    /**
     * Destaca o movimento de ataque.
     * 
     * @param movimento O movimento do tipo ataque.
     */
    private void destacaMovimentoDeAtaque(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        CasaGUI destinoGUI = converteCasaGUI(destino);
        destinoGUI.destacarAlvo();
    }
    
    /**
     * Destaca o movimento comum.
     * 
     * @param movimento O movimento do tipo comum.
     */
    private void destacaMovimentoComum(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        CasaGUI destinoGUI = converteCasaGUI(destino);
        destinoGUI.destacarMovimento();
    }
    
    /**
     * Destaca o movimento roque.
     * 
     * @param movimento O movimento do tipo roque.
     */
    private void destacaMovimentoRoque(Movimento movimento)
    {
        Casa origemRei = movimento.getOrigem();
        Casa origemTorre = movimento.getCasaTorre();
        Casa destinoRei = movimento.getDestino();
        
        converteCasaGUI(origemRei).destacarSelecao();
        converteCasaGUI(origemTorre).destacarSelecao();
        converteCasaGUI(destinoRei).destacarEspecial();
    }
    
    /**
     * Destaca o movimento promoção passiva.
     * 
     * @param movimento O movimento do tipo promoção passiva.
     */
    private void destacaMovimentoPromocaoPassiva(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        converteCasaGUI(destino).destacarEspecial();
    }
    
    /**
     * Destaca o movimento promoção ofensiva.
     * 
     * @param movimento O movimento do tipo promoção ofensiva.
     */
    private void destacaMovimentoPromocaoOfensiva(Movimento movimento)
    {
        destacaMovimentoDeAtaque(movimento);
    }
    
    /**
     * Destaca o movimento en passant.
     * 
     * @param movimento O movimento do tipo en passant.
     */
    private void destacaMovimentoEnPassant(Movimento movimento)
    {
        Casa destino = movimento.getDestino();
        Casa casaAlvo = movimento.getCasaAlvo();
        
        converteCasaGUI(destino).destacarEspecial();
        converteCasaGUI(casaAlvo).destacarAlvo();
    }
    
    /**
     * Destaca o movimento de acordo com seu
     * tipo.
     * 
     * @param O movimento a ser destacado.
     */
    private void destacaMovimento(Movimento movimento)
    {
        switch(movimento.getTipo())
        {
            case Movimento.ATAQUE:
                destacaMovimentoDeAtaque(movimento);
                break;
            
            case Movimento.COMUM:
                destacaMovimentoComum(movimento);
                break;
                
            case Movimento.ROQUE:
                destacaMovimentoRoque(movimento);
                break;
                
            case Movimento.PROMOCAO_PASSIVA:
                destacaMovimentoPromocaoPassiva(movimento);
                break;
                
            case Movimento.PROMOCAO_OFENSIVA:
                destacaMovimentoPromocaoOfensiva(movimento);
                break;
                
            case Movimento.EN_PASSANT:
                destacaMovimentoEnPassant(movimento);
        }
    }
    
    /**
     * Destaca todos os possíveis movimento da peça.
     * 
     * @param casaClicada A casa da peça selecionada.
     */
    private void destacaMovimentos(CasaGUI casaClicada)
    {  
        Casa casa = converteCasa(casaClicada);
        Peca peca = casa.getPeca();
        Jogador jogadorAtual = peca.getJogador();
        movimentos = jogadorAtual.getMovimentos();
        casaClicada.destacarSelecao();
        for(Movimento movimento : movimentos)
        {
            destacaMovimento(movimento);
        }
    }

    /**
     * Construtor da classe.
     */
    public JanelaPrincipal() {
        initComponents();
        efeitosSonoros = new EfeitosSonoros();
        menuInicial();
        // configura action listener para o menu novo
        menuNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarNovoJogo(modoVsIA);//cria um novo jogo.
            }
        });
        
        menuContinuar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                continuarJogo();
            }
        });
        
        menuSalvar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               guardarJogo(); 
            }
        });

        // configura action listener para o menu sair
        menuSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuMusica.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               alternarMusica(); 
            }
        });
        
        menuSon.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               alternarSon(); 
            }
        });
        
        menuSobre.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e)
           {
               showAbout();
           }
        });
        
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.setTitle("Xadrez de Bruxo");
        this.setIconeJogo();
        this.setCursorJogo();
        super.setVisible(true);
        super.pack();
    }
    
    /**
     * Define o cursor para o mouse do jogo.
     */
    private void setCursorJogo()
    {
        Toolkit ferramentas = Toolkit.getDefaultToolkit();
        Point ponto = new Point(0,0);
        Image imagem = ferramentas.createImage("CursorJogo/cursor.png");
        Cursor cursor = ferramentas.createCustomCursor(imagem, ponto, "CursorJogo/cursor.png");
        setCursor(cursor);
    }
    
    /**
     * Define o ícone do jogo.
     */
    private void setIconeJogo()
    {
        Toolkit ferramentas = Toolkit.getDefaultToolkit();
        setIconImage(ferramentas.createImage("IconeJogo/icone_jogo.png"));
    }

    /**
     * Cria um novo jogo e atualiza a janela.
     * 
     * @param modoVsIA se o modo do jogo é contra
     * a IA ou não(J vs J neste caso).
     */
    private void criarNovoJogo(boolean modoVsIA) 
    {
        //cria um novo jogo:
        jogo = new Jogo(modoVsIA);
        //indica qual o modo do jogo:
        this.modoVsIA = modoVsIA;
        //reseta o tabuleiro e a música de fundo:
        reset();
    }
    
    private void continuarJogo()
    {
        if(jogo.carregarJogo() != null)
        {
            jogo = jogo.carregarJogo();
            reset();
        }
        else
        {
            efeitosSonoros.playErro();
        }
    }
    
    private void guardarJogo()
    {
        if(!jogo.getFimDeJogo())
        {    
            jogo.salvarJogo();
        }
        else
        {
            efeitosSonoros.playErro();
        }
    }
    
    private void alternarSon()
    {
        if(efeitosSonoros.sonAtivo())
        {
            efeitosSonoros.setSon(false);
        }
        else
        {
            efeitosSonoros.setSon(true);
        }
    }
    
    private void alternarMusica()
    {
        if(efeitosSonoros.musicaAtiva())
        {
            efeitosSonoros.setMusica(false);
        }
        else
        {
            efeitosSonoros.setMusica(true);
        }
    }
    
    private void showAbout()
    {
        JOptionPane.showMessageDialog(this, "Autores:\nJoão Hudson de Lacerda Oliveira\n" +
                                            "Emmanuella Faustino Albuquerque\n\n" +
                                            "Versão: 4.1",
                                      "Sobre", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("IconeJogo/icone_jogo.png"));
    }
    
    private void reset()
    {
        //reseta/inicializa o click:
        primeiroClique = true;
        casaClicadaOrigem = null;
        casaClicadaDestino = null;
        //remove quaisquer marcações de jogos anteriores:
        tabuleiroGUI.removerMarcacoes();
        //atualiza o tabuleiro:
        atualizar();
        //encerra a música de fundo:
        efeitosSonoros.pauseMusicaDeFundo();
        //toca a música de fundo:
        efeitosSonoros.playMusicaDeFundo();
    }
    
    /**
     * Exibe um menu de escolha.
     */
    private void menuInicial()
    {
        Icon icone = new ImageIcon("IconeJogo/icone_jogo.png");
        Object opcao;
        Object[] opcoes = new Object[2];
        opcoes[0] = "J vs J";
        opcoes[1] = "J vs IA";
        
        opcao = JOptionPane.showInputDialog(this, "Selecione o modo de jogo:", "Xadrez de Bruxo", JOptionPane.QUESTION_MESSAGE,
                                    icone, opcoes, opcoes[0]);
        if(opcao == null)
        {
            efeitosSonoros.pauseMusicaDeFundo();
            System.exit(0);
        }
        
        switch((String)opcao)
        {
            case "J vs J":
                criarNovoJogo(false);
                break;
                
            case "J vs IA":
                criarNovoJogo(true);
        }
    }

    private void atualizar() {
        tabuleiroGUI.atualizar(jogo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLinhas = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlColunas = new javax.swing.JPanel();
        lbl_a = new javax.swing.JLabel();
        lbl_b = new javax.swing.JLabel();
        lbl_c = new javax.swing.JLabel();
        lbl_d = new javax.swing.JLabel();
        lbl_e = new javax.swing.JLabel();
        lbl_f = new javax.swing.JLabel();
        lbl_g = new javax.swing.JLabel();
        lbl_h = new javax.swing.JLabel();
        tabuleiroGUI = new TabuleiroGUI(this);
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuNovo = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuContinuar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuSalvar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuSair = new javax.swing.JMenuItem();
        menuOpcoes = new javax.swing.JMenu();
        menuMusica = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuSon = new javax.swing.JMenuItem();
        menuAjuda = new javax.swing.JMenu();
        menuSobre = new javax.swing.JMenuItem();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlLinhas.setLayout(new java.awt.GridLayout(8, 1));

        jLabel3.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("7");
        pnlLinhas.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("6");
        pnlLinhas.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("5");
        pnlLinhas.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("4");
        pnlLinhas.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("3");
        pnlLinhas.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("2");
        pnlLinhas.add(jLabel8);

        jLabel2.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("1");
        pnlLinhas.add(jLabel2);

        jLabel1.setFont(new java.awt.Font("Abyssinica SIL", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("0");
        pnlLinhas.add(jLabel1);

        pnlColunas.setLayout(new java.awt.GridLayout(1, 8));

        lbl_a.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_a.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_a.setText("0");
        pnlColunas.add(lbl_a);

        lbl_b.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_b.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_b.setText("1");
        pnlColunas.add(lbl_b);

        lbl_c.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_c.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_c.setText("2");
        pnlColunas.add(lbl_c);

        lbl_d.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_d.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_d.setText("3");
        pnlColunas.add(lbl_d);

        lbl_e.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_e.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_e.setText("4");
        pnlColunas.add(lbl_e);

        lbl_f.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_f.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_f.setText("5");
        pnlColunas.add(lbl_f);

        lbl_g.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_g.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_g.setText("6");
        pnlColunas.add(lbl_g);

        lbl_h.setFont(new java.awt.Font("Arimo", 0, 18)); // NOI18N
        lbl_h.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_h.setText("7");
        pnlColunas.add(lbl_h);

        menuArquivo.setText("Jogo");

        menuNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNovo.setText("Novo");
        menuArquivo.add(menuNovo);
        menuArquivo.add(jSeparator1);
        
        menuContinuar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuContinuar.setText("Continuar");
        menuArquivo.add(menuContinuar);
        menuArquivo.add(jSeparator2);
        
        menuSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        menuSalvar.setText("Guardar");
        menuArquivo.add(menuSalvar);
        menuArquivo.add(jSeparator3);

        menuSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuSair.setText("Sair");
        menuArquivo.add(menuSair);
        
        menuOpcoes.setText("Opções");
        
        menuSon.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK));
        menuSon.setText("Son (ON/OFF)");
        menuOpcoes.add(menuSon);
        menuOpcoes.add(jSeparator4);
        
        menuMusica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.SHIFT_MASK));
        menuMusica.setText("Música (ON/OFF)");
        menuOpcoes.add(menuMusica);
        
        menuAjuda.setText("Ajuda");
        
        menuSobre.setText("Sobre");
        menuAjuda.add(menuSobre);
        
        jMenuBar1.add(menuArquivo);
        jMenuBar1.add(menuOpcoes);
        jMenuBar1.add(menuAjuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLinhas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlColunas, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addComponent(tabuleiroGUI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLinhas, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabuleiroGUI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlColunas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JLabel lbl_a;
    private javax.swing.JLabel lbl_b;
    private javax.swing.JLabel lbl_c;
    private javax.swing.JLabel lbl_d;
    private javax.swing.JLabel lbl_e;
    private javax.swing.JLabel lbl_f;
    private javax.swing.JLabel lbl_g;
    private javax.swing.JLabel lbl_h;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenu menuOpcoes;
    private javax.swing.JMenu menuAjuda;
    private javax.swing.JMenuItem menuNovo;
    private javax.swing.JMenuItem menuContinuar;
    private javax.swing.JMenuItem menuSalvar;
    private javax.swing.JMenuItem menuMusica;
    private javax.swing.JMenuItem menuSon;
    private javax.swing.JMenuItem menuSair;
    private javax.swing.JMenuItem menuSobre;
    private javax.swing.JPanel pnlColunas;
    private javax.swing.JPanel pnlLinhas;
    private TabuleiroGUI tabuleiroGUI;
    // End of variables declaration//GEN-END:variables

}
