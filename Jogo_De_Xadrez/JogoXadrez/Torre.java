import java.util.ArrayList;
import java.io.Serializable;
/**
 * A torre se move em linha reta horizontalmente e verticalmente pelo número de casas não ocupadas, 
 * até atingir o final do tabuleiro ou ser bloqueado por outra peça. Ele não pode pular outras peças.
 * A torre captura no mesmo caminho em que se move, ocupando a casa onde se encontra a peça adversária. 
 * 
 * @author (Emmanuella Faustino Albuquerque) 
 * @version (05/10/2018)
 */
public class Torre extends Peca implements Serializable
{
    public Torre(Casa casa, Jogador jogador, int tipo)
    {
        super(casa, jogador, tipo);
    }

    /**
     * Analisa os movimentos possíveis.
     */
    private void analisaMovimento()
    {
        // Limpa o ArrayList de movimetos possíveis a cada rodada.
        movimentos.clear();
        // Verifica e adiciona no ArrayList se os movimentos forem possíveis.
        verificaLadoEsquerdo();
        verificaLadoDireito();
        verificaPraBaixo();
        verificaPraCima();
    }

    /**
     * Verifica se o movimento pra cima/ vertical é válido e adiciona na lista de movimentos se for.
     * @return boolean, false se tem alguma peça pra cima, se tem cancela a análise de movimento.
     * Ou true se não tem nenhuma peça e a analise foi totalmente concluída.
     */
    private boolean verificaPraCima()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Enquanto casa(x,y+1) for possível, adiciona o movimento até encontrar uma peça aliada ou inimiga.
        for(int i = 1; i < 8; i++){
            // Destino final do movimento pra cima: [y+1] até quando for possível.
            Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() + i);
            // Se o destino existir:
            if(destino != null ){
                // Se não tiver peça no destino: 
                if(destino.getPeca() == null){
                    // Adiciona na lista de movimentos possíveis.
                    adicionaMovimentosSemPeca(destino);
                }
                // Caso contrário, se tiver peça:
                else{
                    // Se a peça for aliada, a analise de movimento pra cima acaba e a casa da peça aliada não é adicionada a lista de movimentos:
                    if(restringeMovimentoComPecaIgual(destino) == true){
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }
                    // Se a peça for inimiga, a analise de movimento pra cima acaba, mas a casa da peça alvo é adicionada ao possível movimento:
                    else if(restringeMovimentosComPeca(destino) == true){    
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }   
                }
            }
        }
        // return true se nenhuma peça atrapalhou o movimento, seja uma peça aliada ou inimiga.
        return true;
    }

    /**
     * Restringe movimento, caso tenha uma peça aliada no caminho.
     * @return boolean, true se a peça for aliada, pausando assim qualquer possível movimento de analise.
     */
    private boolean restringeMovimentoComPecaIgual(Casa destino)
    {
        // Se for um aliado:
        if(verificaInimigo(destino.getX(), destino.getY()) == false){
            return true;
        }
        return false;
    }

    /**
     * Restringe movimento, caso tenha uma peça inimiga no caminho.
     * @return boolean, true se a peça for inimiga, pausando assim qualquer possível movimento de análise.
     */
    private boolean restringeMovimentosComPeca(Casa destino)
    {
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Primeira peça encontrada na análise de movimento.
        int primeiraPeca = 0;
        // Se a peça for inimiga e for a primeira peça a barrar o movimento então a análise para de procurar novos movimentos. 
        if(verificaInimigo(destino.getX(), destino.getY()) == true && primeiraPeca == 0 ){
            // Adiciona na lista de movimentos possíveis.
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.ATAQUE);
            movimentos.add(movimento);
            // Para parar na primeira peça que barra o movimento.
            primeiraPeca++;
            return true;
        }   
        return false;
    }

    /**
     * Adiciona os movimentos possíveis, dado um destino.getPeca nulo(Sem peça);
     */
    private void adicionaMovimentosSemPeca(Casa destino)
    {   
        if(destino != null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento pra baixo/ vertical é válido e adiciona na lista de movimentos se for.
     * @return boolean, false se tem alguma peça pra baixo, se tem cancela a análise de movimento.
     * Ou true se não tem nenhuma peça e a analise foi totalmente concluída.
     */
    private boolean verificaPraBaixo()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Enquanto casa(x,y-1) for possível, adiciona o movimento até encontrar uma peça aliada ou inimiga.
        for(int i = 1; i < 8; i++){
            Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() - i);
            if(destino != null ){
                // Se não tiver peça no destino: 
                if(destino.getPeca() == null){
                    // Adiciona na lista de movimentos possíveis.
                    adicionaMovimentosSemPeca(destino);
                }
                // Caso contrário, se tiver peça:
                else{
                    // Se a peça for aliada, a analise de movimento pra cima acaba e a casa da peça aliada não é adicionada a lista de movimentos:
                    if(restringeMovimentoComPecaIgual(destino) == true){
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }
                    // Se a peça for inimiga, a analise de movimento pra cima acaba, mas a casa da peça alvo é adicionada ao possível movimento:
                    else if(restringeMovimentosComPeca(destino) == true){    
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }   
                }
            }
        }
        // return true se nenhuma peça atrapalhou o movimento, seja uma peça aliada ou inimiga.
        return true;
    }

    /**
     * Verifica se o movimento pra o lado direito/ horizontal é válido e adiciona na lista de movimentos se for.
     * @return boolean, false se tem alguma peça no lado direito, se tem cancela a análise de movimento.
     * Ou true se não tem nenhuma peça e a analise foi totalmente concluída.
     */
    private boolean verificaLadoDireito()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Enquanto casa(x + 1, y) for possível, adiciona o movimento até encontrar uma peça aliada ou inimiga.
        for(int i = 1; i < 8; i++){
            Casa destino = tabuleiro.getCasa(origem.getX() + i,  origem.getY());
            if(destino != null ){
                // Se não tiver peça no destino: 
                if(destino.getPeca() == null){
                    // Adiciona na lista de movimentos possíveis.
                    adicionaMovimentosSemPeca(destino);
                }
                // Caso contrário, se tiver peça:
                else{
                    // Se a peça for aliada, a analise de movimento pra cima acaba e a casa da peça aliada não é adicionada a lista de movimentos:
                    if(restringeMovimentoComPecaIgual(destino) == true){
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }
                    // Se a peça for inimiga, a analise de movimento pra cima acaba, mas a casa da peça alvo é adicionada ao possível movimento:
                    else if(restringeMovimentosComPeca(destino) == true){    
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }   
                }
            }
        }
        // return true se nenhuma peça atrapalhou o movimento, seja uma peça aliada ou inimiga.
        return true;
    }

    /**
     * Verifica se o movimento pra o lado esquerdo/ horizontal é válido e adiciona na lista de movimentos se for.
     * @return boolean, false se tem alguma peça no lado esquerdo, se tem cancela a análise de movimento.
     * Ou true se não tem nenhuma peça e a analise foi totalmente concluída.
     */
    private boolean verificaLadoEsquerdo()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Enquanto casa(x - 1, y) for possível, adiciona o movimento até encontrar uma peça aliada ou inimiga.
        for(int i = 1; i < 8; i++){
            Casa destino = tabuleiro.getCasa(origem.getX() - i,  origem.getY());
            if(destino != null ){
                // Se não tiver peça no destino: 
                if(destino.getPeca() == null){
                    // Adiciona na lista de movimentos possíveis.
                    adicionaMovimentosSemPeca(destino);
                }
                // Caso contrário, se tiver peça:
                else{
                    // Se a peça for aliada, a analise de movimento pra cima acaba e a casa da peça aliada não é adicionada a lista de movimentos:
                    if(restringeMovimentoComPecaIgual(destino) == true){
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }
                    // Se a peça for inimiga, a analise de movimento pra cima acaba, mas a casa da peça alvo é adicionada ao possível movimento:
                    else if(restringeMovimentosComPeca(destino) == true){    
                        // Falso se a análise de movimento for cancelada e a restrinção for verdadeira.
                        return false;
                    }   
                }
            }
        }
        // return true se nenhuma peça atrapalhou o movimento, seja uma peça aliada ou inimiga.
        return true;
    }

    /**
     * Adiciona possíveis movimentos de acordo com o destino;
     * @param destino, casa para onde a peça irá se mover.
     */
    private void adicionaPossiveisMovimentos(Casa destino)
    {
        // Se no destino tiver uma peça:
        if(destino.getPeca() != null){
            // Só adiciona movimento se for uma peça inimiga.
            if(verificaInimigo(destino)){
                Movimento movimento = new Movimento(getCasa(), destino, Movimento.ATAQUE);
                movimentos.add(movimento);
            }
        } // Caso contrário, se no destino não tiver nenhuma peça adiciona como movimento válido.
        else{
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.COMUM);
            movimentos.add(movimento);
        }
    }

    /**
     * Pega os movimentos possíveis da peça atual.
     */
    public ArrayList<Movimento> getMovimentos()
    {
        // Define a lista de movimentos possíveis.
        analisaMovimento();
        // Retorna a lista de movimentos possíveis.
        return movimentos;
    }
}
