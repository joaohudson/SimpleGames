import java.util.ArrayList;
/**
 * Descrição: Jogador do jogo de Damas,
 * que possui seu conjunto individual de
 * peças, e seu turno.
 * 
 * @author (João Hudson) 
 * @version (30/09/2018)
 */
public class Jogador
{
    //nome deste jogador:
    private String nome;
    //peças deste jogador:
    private ArrayList<Peca> pecas;
    //armazena uma peça que está atacando(atacou e vai atacar), até o fim do ataque:
    private Peca pecaAtacante;
    //informa quando é o turno desse jogador:
    private boolean minhaVez;
    
    /**
     * Cria um jogador, inicialmente como turno false, e lista de peças vazias.
     * 
     * @param nome O nome do jogador.
     */
    public Jogador(String nome)
    {
        //recebe um nome:
        this.nome = nome;
        //cria uma lista para suas peças:
        pecas = new ArrayList<Peca>();
        //indica que inicialmente não existe peça atacante:
        pecaAtacante = null;
    }
    
    /**
     * Verifica se há possibilidade de um movimento
     * de ataque.
     * 
     * Return true se for possível algum peça do jogador atacar,
     * false do contrário.
     */
    private boolean verificaAtaque()
    {
        //itera por toda lista de peças:
        for(Peca peca : pecas)
        {
            //verifica se essa peça pode comer outra:
            if(peca.verificaProxAlvo())
            {
                //informa que há movimento de ataque possível:
                return true;
            }
        }
        //informa que não há movimento de ataque possível:
        return false;
    }
    
    /**
     * Verifica se a peça é uma peça atacante, ou seja,
     * se atacou e deve atacar denovo.
     * 
     * @param peca A peça a ser verificada.
     * @param ataque Um valor boolean que informe se o
     * ataque foi válido(true) ou não(false).
     * 
     * @return true se a peça é uma peça atacante, false 
     * do contrário.
     */
    private void verificaPecaAtacante(Peca peca, boolean ataque)
    {
        //indica se há uma possibilidade de comer outra peça:
        boolean possivelAlvo = peca.verificaProxAlvo();
        //condição em que a peça já atacou(ataque) e vai atacar de novo(possivel alvo):
        if(ataque && possivelAlvo)
        {
            //atribui a peça a peça atacante indicando próximo ataque:
            pecaAtacante = peca;
        }
        //condição em que a peça não pode atacar mais:
        else if(!possivelAlvo)
        {
            //informa que não há mais ataques possíveis para essa peça(peça atacante não existe mais):
            pecaAtacante = null;
        }
    }
    
    /**
     * Move a peça de forma ofensiva(para comer outra peça),
     * informa se o ataque foi válido, e passa a vez ao efetuar
     * o último ataque.
     */
    private boolean ataque(Peca peca, int xDestino, int yDestino)
    {
        //informa ataque bem sucedido(true) ou não(false):
        boolean ataque;
        //verifica se esse é o primeiro ataque:
        if(pecaAtacante == null)
        {
            //tenta comer uma peça:
            ataque = peca.comer(xDestino, yDestino);
            //verifica se a peça vai atacar de novo:
            verificaPecaAtacante(peca, ataque);
            //verifica se o ataque foi bem sucedido e foi o último:
            if(ataque && (pecaAtacante == null))
            {
                //passa a vez:
                perdeVez();
                //elimina as peças capturadas:
                peca.eliminaAlvos();
            }
        }
        //verifica se a peça selecionada já atacou:
        else if(pecaAtacante != null && peca == pecaAtacante)
        {
            //tenta comer a peça:
            ataque = peca.comer(xDestino, yDestino);
            //verifica se a peça vai atacar de novo:
            verificaPecaAtacante(peca, ataque);
            //verifica se o ataque foi bem sucedido e foi o último:
            if(ataque && (pecaAtacante == null))
            {
                //passa a vez:
                perdeVez();
                //elimina as peças capturadas:
                peca.eliminaAlvos();
            }
        }
        else
        {
            //a peça selecionada não pode atacar, então indica movimento inválido.
            ataque = false;
        }
        //informa se o movimento de ataque foi válido(se comeu outra peça):
        return ataque;
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
     * Move a peça da origem designada, até o destino designado,
     * informa se o movimento foi válido, e passa a vez, caso
     * seja o último movimento.
     * 
     * @param xPosition A posição x da casa origem da peça.
     * @param yPosition A posição y da casa origem da peça.
     * @param xDestino A posição x da casa destino da peça.
     * @param yDestino A posição y da casa destino da peça.
     * @return true se o movimento foi válido, false do contrário.
     */
    public boolean moverPeca(int xPosition, int yPosition, int xDestino, int yDestino)
    {
        //informa movimento bem sucedido(quando verdadeiro):
        boolean movimento = false;
        //recebe uma peça com a posição especificada:
        Peca peca = getPeca(xPosition, yPosition);
        //condição para peça não existente:
        if(peca == null)
        {
            //informa movimento inválido:
            movimento = false;
        }
        //verifica se é possível comer uma peça:
        else if(verificaAtaque() && minhaVez)
        {   
            //come uma peça, informa se comeu e passa a vez quando não for possível comer mais:
            movimento = ataque(peca, xDestino, yDestino);
        }
        //condição para mover a peça normalmete:
        else if(minhaVez)
        {
            //move a peça e informa se moveu:
            movimento = peca.mover(xDestino, yDestino);
            //se o movimento foi bem sucedido:
            if(movimento)
            {
                //passa a vez:
                perdeVez();
            }
        }
        //verifica se acabou a vez:
        if(!minhaVez)
        {
            //vira Dama quando chegar aos extremos da coordenada Y:
            peca.viraDama();
        }
        //informa se o movimento foi válido ou não:
        return movimento;
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
            if(peca.getMinhaCasa().getX() == xPosition && peca.getMinhaCasa().getY() == yPosition)
            {
                //retorna a peça encontrada:
                return peca;
            }
        }
        //retorna null em caso de coordenadas inválidas:
        return null;
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
}
