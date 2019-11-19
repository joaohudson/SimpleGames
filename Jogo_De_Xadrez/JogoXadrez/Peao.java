import java.util.ArrayList;
import java.io.Serializable;
/**
 * Os peões se movem somente para frente, uma casa por vez. Com exceção do primeiro movimento no qual ele pode se mover 2 casas para frente.
 * 
 * @author (Emmanuella Faustino Albuquerque) 
 * @version (05/10/2018)
 */
public class Peao extends Peca implements Serializable
{
    private ArrayList<Movimento> historicoDeMovimentos;
    public Peao(Casa casa, Jogador jogador, int tipo)
    {
        super(casa, jogador, tipo);
        historicoDeMovimentos = new ArrayList();
    }

    /**
     * Analisa os movimentos possíveis.
     */
    private void analisaMovimentos()
    {
        // Limpa o ArrayList de movimetos possíveis a cada rodada.
        movimentos.clear();
        // Verifica e adiciona no ArrayList se os movimentos forem possíveis.
        enPassant();
        verificaPrimeiroMovimento();
        verificaPraCima();
        verificaNordeste();
        verificaNoroeste();
        verificaSudoeste();  
        verificaSudeste();
    }

    /**
     * Tansforma o peão ao chegar nas extremidades.
     */
    public void transformar(int tipoDaPeca)
    {   
        // Se o destino for a extremidade Y == 7 e o peão for branco:
        if(getCasa().getY() == 7 && getTipo() == Peca.PEAO_BRANCO){
            Casa destino = getCasa();
            Jogador jogador = getJogador();
            // Descarta o peão branco:
            descartar();
            transformarPeaoBranco(tipoDaPeca);
        }
        // Caso contrário, se o destino for a extremidade Y == 0 e o peão for preto:
        else if(getCasa().getY() == 0 && getTipo() == Peca.PEAO_PRETO){
            Casa destino = getCasa();
            Jogador jogador = getJogador();
            // Descarta o peão preto:
            descartar();
            transformarPeaoPreto(tipoDaPeca);
        }
    }

    /**
     * Transforma o peão preto, dado o tipo de peça escolhido pelo jogador.
     * Caso deseje cancelar a transformação é passado como parâmetro o tipo peão.
     * @param tipoDaPeca, definição do tipo int da peça.
     */
    private void transformarPeaoPreto(int tipoDaPeca)
    {   
        switch(tipoDaPeca) 
        {
            case Peca.TORRE_PRETA:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Torre(getCasa(), getJogador(), Peca.TORRE_PRETA);               
                break;
            }
            case Peca.CAVALO_PRETO:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Cavalo(getCasa(), getJogador(), Peca.CAVALO_PRETO); 
                break;
            }
            case Peca.BISPO_PRETO:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Bispo(getCasa(), getJogador(), Peca.BISPO_PRETO); 
                break;
            }
            case Peca.DAMA_PRETA:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Dama(getCasa(), getJogador(), Peca.DAMA_PRETA); 
                break;
            }
        }
    }

    /**
     * Transforma o peão branco, dado o tipo de peça escolhido pelo jogador.
     * Caso deseje cancelar a transformação é passado como parâmetro o tipo peão.
     * @param tipoDaPeca, definição do tipo int da peça.
     */
    private void transformarPeaoBranco(int tipoDaPeca)
    {
        switch(tipoDaPeca) 
        {
            case Peca.TORRE_BRANCA:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Torre(getCasa(), getJogador(), Peca.TORRE_BRANCA);               
                break;
            }
            case Peca.CAVALO_BRANCO:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Cavalo(getCasa(), getJogador(), Peca.CAVALO_BRANCO); 
                break;
            }
            case Peca.BISPO_BRANCO:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Bispo(getCasa(), getJogador(), Peca.BISPO_BRANCO); 
                break;
            }
            case Peca.DAMA_BRANCA:
            {
                // Cria a nova peça, no lugar do peão antigo:
                new Dama(getCasa(), getJogador(), Peca.DAMA_BRANCA); 
                break;
            }
        }
    }

    /**
     * Verifica se o movimento pra o lado esquerdo/ horizontal é válido e adiciona na lista de movimentos se for.
     */
    private boolean verificaLadoEsquerdo()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na horizontal/ do lado esquerdo:
        Casa destino = tabuleiro.getCasa(origem.getX() - 1,  origem.getY());
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            if(destino.getPeca() != null && destino.getPeca().getNumeroDeMovimentos() == 1 && analisaUltimoMovimentoPassant(destino)
            && (destino.getPeca().getTipo() == Peca.PEAO_BRANCO || destino.getPeca().getTipo() == Peca.PEAO_PRETO )){
                return true;
            }
        }
        return false;
    }

    /**
     * Analisa o último movimento do jogador, dado o histórico de movimentos.
     * @param destino, destino onde a peça alvo do movimento passant se encontra.
     * @return true, retorna verdade se o último movimento do adversário foi um 
     * movimento duplo, pois o destino especificado em verificaLadoEsquerdo() || 
     * verificaLadoDireito() é o destino da peça alvo.
     */
    private boolean analisaUltimoMovimentoPassant(Casa destino)
    {
        int size = getJogador().getJogadorInimigo().getHistorico().size();
        historicoDeMovimentos = getJogador().getJogadorInimigo().getHistorico();
        Movimento ultimoMovimento = historicoDeMovimentos.get(size - 1);
        if(ultimoMovimento.getDestino() == destino){
            return true;
        }
        return false;
    }

    /**
     * Verifica se o movimento pra o lado direito/ horizontal é válido e adiciona na lista de movimentos se for.
     */
    private boolean verificaLadoDireito()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na horizontal/ pra o lado direito:
        Casa destino = tabuleiro.getCasa(origem.getX() + 1,  origem.getY());
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            if(destino.getPeca() != null && destino.getPeca().getNumeroDeMovimentos() == 1 && analisaUltimoMovimentoPassant(destino)
            && (destino.getPeca().getTipo() == Peca.PEAO_BRANCO || destino.getPeca().getTipo() == Peca.PEAO_PRETO )){
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o primeiro movimento é válido e adiciona na lista de movimentos se for.
     */
    private void verificaPrimeiroMovimento()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Se for um Peão branco:
        if(origem.getPeca().getTipo() == Peca.PEAO_BRANCO ){
            verificaDestinoPuladoBranco(origem);
        }
        // Se for um Peão preto:
        else if(origem.getPeca().getTipo() == Peca.PEAO_PRETO ){
            verificaDestinoPuladoPreto(origem);
        }
    }

    /**
     * Verifica se o destinoPulado do peão branco não tem peça, se não tiver movimento é válido e é adicionado na lista de movimentos.
     * @param origem, casa onde a peça se encontra atualmente.
     */
    private void verificaDestinoPuladoBranco(Casa origem)
    {
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino do meio do primeiro movimento do peão.
        Casa destinoPulado = tabuleiro.getCasa(origem.getX(),  origem.getY() + 1);
        // Destino final do primeiro movimento, caso o jogador tenha escolhido andar 2 casas.
        Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() + 2);
        // Se o destino não for nulo e não tiver peça no destino pulado:
        if(destino != null && destinoPulado.getPeca() == null){
            adicionaPrimeiroMovimentoBranco(origem);       
        }
    }

    /**
     * Verifica se o destinoPulado do peão preto não tem peça, se não tiver movimento é válido e é adicionado na lista de movimentos.
     * @param origem, casa onde a peça se encontra atualmente.
     */
    private void verificaDestinoPuladoPreto(Casa origem)
    {
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino do meio do primeiro movimento do peão.
        Casa destinoPulado = tabuleiro.getCasa(origem.getX(),  origem.getY() - 1);
        // Destino final do primeiro movimento, caso o jogador tenha escolhido andar 2 casas.
        Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() - 2);
        // Se o destino não for nulo e não tiver peça no destino pulado:
        if(destino != null && destinoPulado.getPeca() == null){
            adicionaPrimeiroMovimentoPreto(origem);       
        }       
    }

    /**
     * Adiciona o movimento de 2 casas, quando possível, na lista de movimentos.
     * @param origem, casa onde a peça se encontra atualmente.
     */
    private void adicionaPrimeiroMovimentoBranco(Casa origem)
    {
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino final do primeiro movimento, caso o jogador tenha escolhido andar 2 casas.
        Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() + 2);
        // Se o número de movimentos da peça for 0 e não tiver peça no destino:
        if(this.getNumeroDeMovimentos() == 0 && destino.getPeca() == null){
            // Adiciona na lista de movimentos possíveis.
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.COMUM);
            movimentos.add(movimento);
        }   
    }

    /**
     * Adiciona o movimento de 2 casas, quando possível, na lista de movimentos.
     * @param origem, casa onde a peça se encontra atualmente.
     */
    private void adicionaPrimeiroMovimentoPreto(Casa origem)
    {
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino final do primeiro movimento, caso o jogador tenha escolhido andar 2 casas.
        Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() - 2);
        // Se o número de movimentos da peça for 0 e não tiver peça no destino:
        if(this.getNumeroDeMovimentos() == 0 && destino.getPeca() == null){
            // Adiciona na lista de movimentos possíveis.
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.COMUM);
            movimentos.add(movimento);
        }  
    }

    /**
     * Verifica se o movimento normal/para cima do peão é válido e adiciona na lista de movimentos se for.
     */
    private void verificaPraCima()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Inicializa a casa ainda sem parâmetros;
        Casa destino = null;
        // Se a peça for um peão branco:
        if(origem.getPeca().getTipo() == Peca.PEAO_BRANCO ){
            // O sentido do destino será pra cima:
            destino = tabuleiro.getCasa(origem.getX(),  origem.getY() + 1);
        }
        // Se a peça for um peão preto:
        else if(origem.getPeca().getTipo() == Peca.PEAO_PRETO ){
            // O sentido do destino será pra baixo:
            destino = tabuleiro.getCasa(origem.getX(),  origem.getY() - 1);
        }

        // Se o destino for diferente de nulo e não tiver peça:
        if(destino != null && destino.getPeca() == null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento diaginal(nordeste)/para comer do peão branco é válido e adiciona na lista de movimentos se for.
     */
    private void verificaNordeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Se a peça for um peão branco:
        if(origem.getPeca().getTipo() == Peca.PEAO_BRANCO ){
            // Destino na diagonal nordeste:
            Casa destino = tabuleiro.getCasa(origem.getX() + 1, origem.getY() + 1);
            // Se destino for diferente de nulo(caso verifique uma casa fora dos limites) e destino não tiver peça.
            if(destino != null && destino.getPeca() != null){
                // Adiciona na lista de movimentos possíveis.
                adicionaPossiveisMovimentos(destino);
            }
        }
    }

    /**
     * Verifica se o movimento diaginal(noroeste)/para comer do peão branco é válido e adiciona na lista de movimentos se for.
     */
    private void verificaNoroeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Se a peça for um peão branco:
        if(origem.getPeca().getTipo() == Peca.PEAO_BRANCO ){
            // Destino na diagonal noroeste:
            Casa destino = tabuleiro.getCasa(origem.getX() - 1,  origem.getY() + 1);
            // Se destino for diferente de nulo(caso verifique uma casa fora dos limites) e destino não tiver peça.
            if(destino != null && destino.getPeca() != null){
                // Adiciona na lista de movimentos possíveis.
                adicionaPossiveisMovimentos(destino);
            }
        }
    }

    /**
     * Verifica se o movimento diaginal(sudoeste)/para comer do peão preto é válido e adiciona na lista de movimentos se for.
     */
    private void verificaSudoeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Se a peça for um peão preto:
        if(origem.getPeca().getTipo() == Peca.PEAO_PRETO ){
            // Destino na diagonal sudoeste:
            Casa destino = tabuleiro.getCasa(origem.getX() - 1,  origem.getY() - 1);
            // Se destino for diferente de nulo(caso verifique uma casa fora dos limites) e destino não tiver peça.
            if(destino != null && destino.getPeca() != null){
                // Adiciona na lista de movimentos possíveis.
                adicionaPossiveisMovimentos(destino);
            }
        }
    }

    /**
     * Verifica se o movimento diaginal(sudeste)/para comer do peão preto é válido e adiciona na lista de movimentos se for.
     */
    private void verificaSudeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Se a peça for um peão preto:
        if(origem.getPeca().getTipo() == Peca.PEAO_PRETO ){
            // Destino na diagonal sudeste:
            Casa destino = tabuleiro.getCasa(origem.getX() + 1,  origem.getY() - 1);
            // Se destino for diferente de nulo(caso verifique uma casa fora dos limites) e destino não tiver peça.
            if(destino != null && destino.getPeca() != null){
                // Adiciona na lista de movimentos possíveis.
                adicionaPossiveisMovimentos(destino);
            }
        }
    }
    
    /**
     * Adiciona se possível, o movimento en Passant na lista de movimentos válidos. 
     * Caso, tais peças estejam na 5° fileira de casas em relação ao jogador.
     */
    private void enPassant()
    {
        if(this.getCasa().getY() == 4 && this.getCasa().getPeca().getTipo() == 1){
            adicionaPassantEsquerdoBranca();
            adicionaPassantDireitoBranca();
        }

        else if(this.getCasa().getY() == 3 && this.getCasa().getPeca().getTipo() == - 1){
            adicionaPassantEsquerdoPreta();
            adicionaPassantDireitoPreta();
        }
    }

    /**
     * Adiciona o passant no lado esquerdo do peão branco, se possível.
     */
    private void adicionaPassantEsquerdoBranca()
    {   
        if(verificaLadoEsquerdo()){
            Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
            // Destino na diagonal será a nova casa origem da peça.
            Casa destino = tabuleiro.getCasa(getCasa().getX() - 1,  getCasa().getY() + 1);
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.EN_PASSANT);
            movimentos.add(movimento);
        }  
    }

    /**
     * Adiciona o passant no lado esquerdo do peão preto, se possível.
     */
    private void adicionaPassantEsquerdoPreta()
    {   
        if(verificaLadoEsquerdo()){
            Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
            // Destino na diagonal será a nova casa origem da peça.
            Casa destino = tabuleiro.getCasa(getCasa().getX() - 1,  getCasa().getY() - 1);
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.EN_PASSANT);
            movimentos.add(movimento);
        }  
    }

    /**
     * Adiciona o passant no lado direito do peão branco, se possível.
     */
    private void adicionaPassantDireitoBranca()
    {   
        if(verificaLadoDireito()){
            Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
            // Destino na diagonal será a nova casa origem da peça.
            Casa destino = tabuleiro.getCasa(getCasa().getX() + 1,  getCasa().getY() + 1);
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.EN_PASSANT);
            movimentos.add(movimento);
        } 
    }

    /**
     * Adiciona o passant no lado direito do peão preto, se possível.
     */
    private void adicionaPassantDireitoPreta()
    {
        if(verificaLadoDireito()){
            Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
            // Destino na diagonal será a nova casa origem da peça.
            Casa destino = tabuleiro.getCasa(getCasa().getX() + 1,  getCasa().getY() - 1);
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.EN_PASSANT);
            movimentos.add(movimento);
        } 
    }

    /**
     * Adiciona possíveis movimentos de acordo com o destino;
     * @param destino, casa para onde a peça irá se mover.
     */
    private void adicionaPossiveisMovimentos(Casa destino)
    {
        Casa casa = getCasa();
        // Se no destino tiver uma peça:
        if(destino.getPeca() != null){
            // Só adiciona movimento se for uma peça inimiga.
            if(verificaInimigo(destino)){ 
                Movimento movimento;
                movimento = new Movimento(casa, destino, Movimento.ATAQUE);
                if(seMovimentoTransformar(casa, destino)){
                    movimento = new Movimento(casa, destino, Movimento.PROMOCAO_OFENSIVA );
                }
                movimentos.add(movimento);
            }
        } // Caso contrário, se no destino não tiver nenhuma peça adiciona como movimento válido.
        else{
            Movimento movimento;
            movimento = new Movimento(casa, destino, Movimento.COMUM);
            if(seMovimentoTransformar(casa, destino)){
                movimento = new Movimento(casa, destino, Movimento.PROMOCAO_PASSIVA);
            }
            movimentos.add(movimento);
        }
    }

    /**
     * Verifica se é possível transformar dada a localização da peça.
     * @param origem, casa onde a peça se encontra atualmente.
     * @param destino, casa para onde a peça irá se mover.
     * @return true, verdade se extremidade y = 7 tem um peão branco 
     * ou se extremidade y = 0 tem um peão preto.
     */
    private boolean seMovimentoTransformar(Casa origem, Casa destino)
    {
        if(destino.getY() == 7 && getTipo() == Peca.PEAO_BRANCO){
            return true;
        }
        else if(destino.getY() == 0 && getTipo() == Peca.PEAO_PRETO){
            return true;
        }
        return false;
    }

    /**
     * Pega os movimentos possíveis da peça atual.
     */
    public ArrayList<Movimento> getMovimentos()
    {
        // Define a lista de movimentos possíveis.
        analisaMovimentos();
        // Retorna a lista de movimentos possíveis.
        return movimentos;
    }
}
