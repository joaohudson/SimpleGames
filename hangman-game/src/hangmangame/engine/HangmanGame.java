package hangmangame.engine;

import java.util.ArrayList;
import java.util.Random;
/**
 * Escreva a descrição da classe HangmanGame aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class HangmanGame implements Game
{
    private boolean finished;
    private boolean victory;
    private boolean inputError;
    private boolean charRepeated;
    private int     errorsNumber;
    private String  privateWord;
    private String  publicWord;
    private String  charactersRead;
    private Random  rand;
    private ArrayList<String> wordList;
    
    public HangmanGame()
    {
        finished = false;
        victory = false;
        inputError = false;
        charRepeated = false;
        errorsNumber = 0;
        rand = new Random();
        wordList = new ArrayList<String>();
        initWordList();
        privateWord = lookWord();
        publicWord = occultWord(privateWord);
        charactersRead = "";
    }
    
    public void read(String inputText)
    {
        if(!finished)
        {
            if(inputText.length() == 1)
            {
                charRead(inputText.charAt(0));
            }
            else
            {
                wordRead(inputText);
            }
            checkFinish();
        }
    }
    
    public String getCharactersRead()
    {
        return charactersRead;
    }
    
    public String getWord()
    {
        return publicWord;
    }
    
    public int getErrorsNumber()
    {
        return errorsNumber;
    }
    
    public boolean isError()
    {
        return inputError;
    }
    
    public boolean isFinished()
    {
        return finished;
    }
    
    public boolean isVictory()
    {
        return victory;
    }
    
    public boolean isRepeated()
    {
        return charRepeated;
    }
    
    private void charRead(char character)
    {
        boolean contains = privateWord.contains("" + character);
        charRepeated = charactersRead.contains("" + character);
        
        if(charRepeated)
        {
            inputError = false;
        }
        else if(contains)
        {
            reveal(character);
            inputError = false;
        }
        else
        {
            inputError = true;
            errorsNumber++;
        }
        
        charactersRead += charactersRead.contains(character + "") ? "" : character;
    }
    
    private void wordRead(String word)
    {
        if(privateWord.equals(word))
        {
            publicWord = privateWord;
            inputError = false;
        }
        else
        {
            inputError = true;
            errorsNumber = 6;
        }
    }
    
    private void reveal(char character)
    {
        char[] publicArray = arrayCharConvert(publicWord);
        
        for(int i = 0;  i < privateWord.length(); i++)
        {
            if(privateWord.charAt(i) == character)
            {
                publicArray[i] = character;
            }
        }
        
        publicWord = publicWord.valueOf(publicArray);
    }
    
    private char[] arrayCharConvert(String string)
    {
        int length = string.length();
        char[] charArray = new char[length];
        
        for(int i = 0; i < length; i++)
        {
            charArray[i] = string.charAt(i);
        }
        
        return charArray;
    }
    
    private void checkFinish()
    {
        if(privateWord.equals(publicWord))
        {
            finished = true;
            victory = true;
        }
        else if (errorsNumber >= 6)
        {
            finished = true;
            victory = false;
        }
    }
    
    private String lookWord()
    {
        int sizeList = wordList.size();
        int index = rand.nextInt(sizeList);
        
        return wordList.get(index);
    }
    
    private String occultWord(String word)
    {
        String occultWord = ""; 
        int length = word.length();
        
        for(int i = 0; i < length; i++)
        {
            occultWord += '*';
        }
        
        return occultWord;
    }
    
    private void initWordList()
    {
        wordList.add("carro");
        wordList.add("bicicleta");
        wordList.add("casa");
        wordList.add("barco");
        wordList.add("otorrinolaringologista");
        wordList.add("mar");
        wordList.add("cadeira");
        wordList.add("motorista");
        wordList.add("cachoeira");
        wordList.add("tomada");
        wordList.add("escada");
    }
}
