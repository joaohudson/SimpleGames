package hangmangame.engine;


/**
 * Escreva a descrição da interface TextReder aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface Game
{
    /**
     * Chamado quando uma entrada de texto
     * é efetuada.
     * 
     * @param inputText O texto inserido pelo jogador.
     */
    void read(String inputText);
    
    /**
     * Obtém os caracteres lidos.
     * Obs.: inicialmente quando
     * não houver carcateres lidos
     * retorna um string vazio.
     * NÃO retorna null;
     * 
     * @return Os caracteres lidos.
     */
    String getCharactersRead();
    
    /**
     * Obtém a palavra atual que está sendo revelada.
     * 
     * @return A palavra atual que está sendo revelada.
     */
    String getWord();
    
    /**
     * Obtém o número de erros.
     * 
     * @return O número de erros.
     */
    int getErrorsNumber();
    
    /**
     * Se o jogo acabou.
     * 
     * @return true se o jogo acabou.
     */
    boolean isFinished();
    
    /**
     * Se errou a última entrada.
     * 
     * @return true se errou a última
     * entrada.
     */
    boolean isError();
    
    /**
     * Se o jogador venceu.
     * 
     * @return true se o jogador venceu.
     */
    boolean isVictory();
    
    /**
     * Se o último caractere foi repetido.
     * 
     * @return true se o último caractere foi
     * repetido.
     */
    boolean isRepeated();
}
