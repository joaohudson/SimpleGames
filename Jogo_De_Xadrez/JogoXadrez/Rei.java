import java.util.ArrayList;
import java.io.Serializable;
/**
 * Escreva a descrição da classe Rei aqui.
 * 
 * @author (Emmanuella Faustino Albuquerque) 
 * @version (05/10/2018)
 */
public class Rei extends Peca implements Serializable
{
    public Rei(Casa casa, Jogador jogador, int tipo)
    {
        super(casa, jogador, tipo);
        jogador.ganhaVez();
    }

    /**
     * Analisa os movimentos possíveis.
     */
    private void analisaMovimento()
    {
        // Limpa o ArrayList de movimetos possíveis a cada rodada.
        movimentos.clear();
        // Verifica e adiciona no ArrayList se os movimentos forem possíveis.
        verificaRoque();
        verificaNordeste();
        verificaNoroeste();
        verificaSudoeste();
        verificaSudeste();
        verificaLadoEsquerdo();
        verificaLadoDireito();
        verificaPraBaixo();
        verificaPraCima();
    }

    /**
     * Verifica se o movimento nordeste/ diagonal é válido e adiciona na lista de movimentos se for.
     */
    private void verificaNordeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na diagonal nordeste:
        Casa destino = tabuleiro.getCasa(origem.getX() + 1,  origem.getY() + 1);
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento noroeste/ diagonal é válido e adiciona na lista de movimentos se for.
     */
    private void verificaNoroeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na diagonal noroeste:
        Casa destino = tabuleiro.getCasa(origem.getX() - 1,  origem.getY() + 1);
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento pra o lado esquerdo/ horizontal é válido e adiciona na lista de movimentos se for.
     */
    private void verificaLadoEsquerdo()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na horizontal/ do lado esquerdo:
        Casa destino = tabuleiro.getCasa(origem.getX() - 1,  origem.getY());
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento sudeste/ deiagonal é válido e adiciona na lista de movimentos se for.
     */
    private void verificaSudoeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na diagonal/ sudoeste:
        Casa destino = tabuleiro.getCasa(origem.getX() - 1,  origem.getY() - 1);
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento pra baixo/ vertical é válido e adiciona na lista de movimentos se for.
     */
    private void verificaPraBaixo()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na vertical/ para baixo:
        Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() - 1);
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null ){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento sudeste/ diagonal é válido e adiciona na lista de movimentos se for.
     */
    private void verificaSudeste()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na diagonal/ sudeste:
        Casa destino = tabuleiro.getCasa(origem.getX() + 1,  origem.getY() - 1);
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null ){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento pra o lado direito/ horizontal é válido e adiciona na lista de movimentos se for.
     */
    private void verificaLadoDireito()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na horizontal/ pra o lado direito:
        Casa destino = tabuleiro.getCasa(origem.getX() + 1,  origem.getY());
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null ){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Verifica se o movimento pra cima/ vertical é válido e adiciona na lista de movimentos se for.
     */
    private void verificaPraCima()
    {
        // Recebe as coordenadas da casa da peça atual.
        Casa origem = this.getCasa();
        // Pega o tabuleiro referente a casa, onde a peça se encontra.
        Tabuleiro tabuleiro = this.getCasa().getTabuleiro();
        // Destino na vertical/ pra cima:
        Casa destino = tabuleiro.getCasa(origem.getX(),  origem.getY() + 1);
        // Se destino for diferente de nulo(caso verifique uma casa fora dos limites).
        if(destino != null){
            // Adiciona na lista de movimentos possíveis.
            adicionaPossiveisMovimentos(destino);
        }
    }

    /**
     * Adiciona o movimento roque na lista de movimentos caso possível.
     */
    private void verificaRoque()
    {
        // Adiciona o movimento do roque, na menor direção entre o rei e a torre.
        roquePequeno();
        // Adiciona o movimento do roque, na maior direção entre o rei e a torre.
        roqueGrande();
    }

    /**
     * Roque pequeno, aquele dado entre a menor distância entre o rei e a torre.
     * Adiciona o movimento do roque pequeno, caso as determinações sejam verdadeiras.
     */
    private void roquePequeno()
    {
        // Casa destino do rei no roque, único movimento onde o rei pode andar duas casas:
        Casa destino = getCasa().getTabuleiro().getCasa(getCasa().getX() + 2,  getCasa().getY());
        // Casa por onde o rei passa, está casa deve estar vazia para o rei poder atravessar:
        Casa destinoPulado = getCasa().getTabuleiro().getCasa(getCasa().getX() + 1,  getCasa().getY());
        // Casa onde a torre se encontra, antes do seu primeiro movimento:
        Casa OrigemTorre = getCasa().getTabuleiro().getCasa(getCasa().getX() + 3,  getCasa().getY());
        // Verifica se as casas estão de acordo com o protocolo e se o rei não passou por um xeque:
        if(fixRoquepequeno(destino, destinoPulado, OrigemTorre) && getCasa().getPeca().getJogador().verificaPassagemPorXeque() == false){
            // Caso estes fatos sejam verdadeiros, adiciona-se a lista de movimentos:
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.ROQUE);
            movimentos.add(movimento);
        }
    }

    /**
     * Roque grande, aquele dado entre a maior distância entre o rei e a torre.
     * Adiciona o movimento do roque grande, caso as determinações sejam verdadeiras.
     */
    private void roqueGrande()
    {
        // Casa destino do rei no roque, único movimento onde o rei pode andar duas casas:
        Casa destino = getCasa().getTabuleiro().getCasa(getCasa().getX() - 2,  getCasa().getY());
        // Casa por onde o rei passa, está casa deve estar vazia para o rei poder atravessar:
        Casa destinoPulado = getCasa().getTabuleiro().getCasa(getCasa().getX() - 1,  getCasa().getY());
        // Casa por onde o rei passa, está casa deve estar vazia para o rei poder atravessar:
        Casa destinoSemPeca = getCasa().getTabuleiro().getCasa(getCasa().getX() - 3,  getCasa().getY());
        // Casa por onde o rei passa, está casa deve estar vazia para o rei poder atravessar:
        Casa OrigemTorre = getCasa().getTabuleiro().getCasa(getCasa().getX() + - 4,  getCasa().getY());
        // Verifica se as casas estão de acordo com o protocolo e se o rei não passou por um xeque:
        if(fixRoqueGrande(destino, destinoPulado, destinoSemPeca, OrigemTorre) && getCasa().getPeca().getJogador().verificaPassagemPorXeque() == false){
            // Caso estes fatos sejam verdadeiros, adiciona-se a lista de movimentos:
            Movimento movimento = new Movimento(getCasa(), destino, Movimento.ROQUE);
            movimentos.add(movimento);
        }
    }

    /**
     * Garante que as verificações entre os espaços das casas ocupadas e vazias entre as duas peças seja como ditam as regras.
     * @param destino, Casa destino do rei.
     * @param destinoPulado, Casa por onde o rei irá passar.
     * @param origemTorre, Casa onde a torre se encontra antes de se mover pela pemeira vez.
     */
    private boolean fixRoquepequeno(Casa destino, Casa destinoPulado, Casa OrigemTorre)
    {
        // Se for o primeiro movimento do rei, analisa as casas ao lado, até chegar na torre:
        if(destino!= null && destinoPulado != null && OrigemTorre != null && destino.getPeca() == null && 
        destinoPulado.getPeca() == null && OrigemTorre.getPeca() != null && getCasa().getPeca().getNumeroDeMovimentos() == 0 &&
        OrigemTorre.getPeca().getNumeroDeMovimentos() == 0 ){
            // Se as casas estiverem vazias e a análise da torre for bem sucedida:
            return true;
        }       
        // Senão o movimento do roque não é válido:
        return false;
    }

    /**
     * Garante que as verificações entre os espaços das casas ocupadas e vazias entre as duas peças seja como ditam as regras.
     * @param destino, Casa destino do rei.
     * @param destinoPulado, Casa por onde o rei irá "passar".
     * @param destinoSemPeca, Casa por onde o rei irá "passar".
     * @param origemTorre, Casa onde a torre se encontra antes de se mover pela pemeira vez.
     */
    private boolean fixRoqueGrande(Casa destino, Casa destinoPulado, Casa destinoSemPeca, Casa OrigemTorre)
    {
        // Se for o primeiro movimento do rei, analisa as casas ao lado, até chegar na torre:
        if(destino!= null && destinoPulado != null && destinoSemPeca != null && OrigemTorre != null && destino.getPeca() == null && 
        destinoPulado.getPeca() == null && destinoSemPeca.getPeca() == null && OrigemTorre.getPeca() != null
        && getCasa().getPeca().getNumeroDeMovimentos() == 0 && OrigemTorre.getPeca().getNumeroDeMovimentos() == 0 ){
            // Se as casas estiverem vazias e a análise da torre for bem sucedida:
            return true;
        }       
        // Senão o movimento do roque não é válido:
        return false;
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
