package hangmangame.engine;


/**
 * Escreva a descri��o da interface TextReder aqui.
 * 
 * @author (seu nome) 
 * @version (n�mero da vers�o ou data)
 */

public interface Game
{
    /**
     * Chamado quando uma entrada de texto
     * � efetuada.
     * 
     * @param inputText O texto inserido pelo jogador.
     */
    void read(String inputText);
    
    /**
     * Obt�m os caracteres lidos.
     * Obs.: inicialmente quando
     * n�o houver carcateres lidos
     * retorna um string vazio.
     * N�O retorna null;
     * 
     * @return Os caracteres lidos.
     */
    String getCharactersRead();
    
    /**
     * Obt�m a palavra atual que est� sendo revelada.
     * 
     * @return A palavra atual que est� sendo revelada.
     */
    String getWord();
    
    /**
     * Obt�m o n�mero de erros.
     * 
     * @return O n�mero de erros.
     */
    int getErrorsNumber();
    
    /**
     * Se o jogo acabou.
     * 
     * @return true se o jogo acabou.
     */
    boolean isFinished();
    
    /**
     * Se errou a �ltima entrada.
     * 
     * @return true se errou a �ltima
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
     * Se o �ltimo caractere foi repetido.
     * 
     * @return true se o �ltimo caractere foi
     * repetido.
     */
    boolean isRepeated();
}
