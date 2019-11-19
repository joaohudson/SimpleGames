
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Interface Grafica de uma Casa no tabuleiro do jogo.
 *
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 * @author João Hudson.
 */
public class CasaGUI extends JButton {

    //cores das casas:
    public static final Color COR_CLARA = new Color(250, 250, 250);
    public static final Color COR_ESCURA = new Color(0, 0, 0);
    //cores de marcação:
    private static final Color COR_SELECAO = new Color(0, 0, 1, 0.4f);
    private static final Color COR_MOVIMENTO = new Color(0, 1, 0, 0.4f);
    private static final Color COR_ALVO = new Color(1, 0, 0, 1.0f);
    private static final Color COR_ESPECIAL = new Color(1, 1, 0, 0.4f);
    //ícones das peças brancas:
    private static final Icon PEAO_BRANCO = new ImageIcon("PecasXadrez/peao_branco.png");
    private static final Icon TORRE_BRANCA = new ImageIcon("PecasXadrez/torre_branca.png");
    private static final Icon CAVALO_BRANCO = new ImageIcon("PecasXadrez/cavalo_branco.png");
    private static final Icon BISPO_BRANCO = new ImageIcon("PecasXadrez/bispo_branco.png");
    private static final Icon DAMA_BRANCA = new ImageIcon("PecasXadrez/dama_branca.png");
    private static final Icon REI_BRANCO = new ImageIcon("PecasXadrez/rei_branco.png");
    //ícones das peças pretas:
    private static final Icon PEAO_PRETO = new ImageIcon("PecasXadrez/peao_preto.png");
    private static final Icon TORRE_PRETA = new ImageIcon("PecasXadrez/torre_preta.png");
    private static final Icon CAVALO_PRETO = new ImageIcon("PecasXadrez/cavalo_preto.png");
    private static final Icon BISPO_PRETO = new ImageIcon("PecasXadrez/bispo_preto.png");
    private static final Icon DAMA_PRETA = new ImageIcon("PecasXadrez/dama_preta.png");
    private static final Icon REI_PRETO = new ImageIcon("PecasXadrez/rei_preto.png");
    // Cores das peças
    public static final int SEM_PECA = -1;
    public static final int PECA_BRANCA = 0;
    public static final int PECA_PRETA = 1;
    

    private int x;
    private int y;
    private Color cor;

    public CasaGUI(int x, int y, Color cor, TabuleiroGUI tabuleiro) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        setIcon(null);

        // Layout e cor
        setBackground(cor);
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(cor, 1));
        setContentAreaFilled(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabuleiro.getJanela().reagir((CasaGUI) e.getSource());
            }
        });
    }

    public int getPosicaoX() {
        return x;
    }

    public int getPosicaoY() {
        return y;
    }
    
    /**
     * Coloca um peça na casa, de acordo
     * com o tipo especificado.
     * 
     * @param tipo O tipo da peça.
     */
    public void colocarPeca(int tipo)
    {
        switch(tipo)
        {
            //peças brancas:
            case Peca.PEAO_BRANCO:
                setIcon(PEAO_BRANCO);
                break;
                
            case Peca.TORRE_BRANCA:
                setIcon(TORRE_BRANCA);
                break;
                
            case Peca.CAVALO_BRANCO:
                setIcon(CAVALO_BRANCO);
                break;
                
            case Peca.BISPO_BRANCO:
                setIcon(BISPO_BRANCO);
                break;
                
            case Peca.DAMA_BRANCA:
                setIcon(DAMA_BRANCA);
                break;
                
            case Peca.REI_BRANCO:
                setIcon(REI_BRANCO);
                break;
            //peças pretas:    
            case Peca.PEAO_PRETO:
                setIcon(PEAO_PRETO);
                break;
                
            case Peca.TORRE_PRETA:
                setIcon(TORRE_PRETA);
                break;
                
            case Peca.CAVALO_PRETO:
                setIcon(CAVALO_PRETO);
                break;
                
            case Peca.BISPO_PRETO:
                setIcon(BISPO_PRETO);
                break;
                
            case Peca.DAMA_PRETA:
                setIcon(DAMA_PRETA);
                break;
                
            case Peca.REI_PRETO:
                setIcon(REI_PRETO);
                break;
        }
    }

    public void removerPeca() {
        setIcon(null);
    }

    public boolean possuiPeca() {
        return getIcon() != null;
    }
    
    public int getCorPeca() {
        //ícone da peça:
        Icon icone = getIcon();
        //true se a peça for preta:
        boolean pecaPreta = icone == PEAO_PRETO || icone == TORRE_PRETA || icone == CAVALO_PRETO || icone == BISPO_PRETO ||
                icone == DAMA_PRETA || icone == REI_PRETO;
        //true se a peça for branca:
        boolean pecaBranca = icone == PEAO_BRANCO || icone == TORRE_BRANCA || icone == CAVALO_BRANCO || icone == BISPO_BRANCO ||
                icone == DAMA_BRANCA || icone == REI_BRANCO;
                
        if(pecaBranca)
        {
            return PECA_BRANCA;
        }
        else if(pecaPreta)
        {
            return PECA_PRETA;
        }
        else
        {
            return SEM_PECA;
        }
    }
    
    public void destacarSelecao() {
        setBackground(COR_SELECAO);
    }
    
    public void destacarMovimento() {
        setBackground(COR_MOVIMENTO);
    }
    
    public void destacarAlvo() {
        setBackground(COR_ALVO);
    }
    
    public void destacarEspecial()
    {
        setBackground(COR_ESPECIAL);
    }

    public void atenuar() {
        setBackground(cor);
    }

    /**
     * Pinta o componente com a cor de fundo, aceita valores RGBA
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
