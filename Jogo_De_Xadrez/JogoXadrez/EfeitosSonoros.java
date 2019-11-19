import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
/**
 * Descrição: Objeto responsável por produzir os efeitos sonoros
 * do jogo de Xadrez.
 * 
 * @author (João Hudson) 
 * @version (14/11/2018)
 */
public class EfeitosSonoros
{
    private boolean   sonAtivo;
    private boolean   musicaAtiva;
    private AudioClip sonAtaque;
    private AudioClip sonMovimento;
    private AudioClip sonMusicaDeFundo;
    private AudioClip sonErro;
    private AudioClip sonVitoria;
    
    /**
     * Cria um objeto que toca os efeitos sonoros do jogo.
     */
    public EfeitosSonoros()
    {
        sonAtivo = true;
        musicaAtiva = true;
        URL urlAtaque = getClass().getResource("EfeitosSonoros/ataque.wav");
        URL urlMovimento = getClass().getResource("EfeitosSonoros/movimento.wav");
        URL urlMusicaDeFundo = getClass().getResource("EfeitosSonoros/musicaDeFundo.wav");
        URL urlErro = getClass().getResource("EfeitosSonoros/erro.wav");
        URL urlVitoria = getClass().getResource("EfeitosSonoros/vitoria.wav");
         
        sonAtaque = Applet.newAudioClip(urlAtaque);
        sonMovimento = Applet.newAudioClip(urlMovimento);
        sonMusicaDeFundo = Applet.newAudioClip(urlMusicaDeFundo);
        sonErro = Applet.newAudioClip(urlErro);
        sonVitoria = Applet.newAudioClip(urlVitoria);
    }
    
    /**
     * Toca o efeito sonoro de ataque.
     */
    public void playAtaque()
    {
        if(sonAtivo)
        {
            sonAtaque.play();
        }
    }
    
    /**
     * Toca o efeito sonoro de movimento.
     */
    public void playMovimento()
    {
        if(sonAtivo)
        {
            sonMovimento.play();
        }
    }
    
    /**
     * Emite um son de erro.
     */
    public void playErro()
    {
        if(sonAtivo)
        {
            sonErro.play();
        }
    }
    
    /**
     * Toca a música de vitória encerrando a música
     * de fundo.
     */
    public void playVitoria()
    {
        pauseMusicaDeFundo();
        if(sonAtivo)
        {
            sonVitoria.play();
        }
    }
    
    /**
     * Toca a música de fundo.
     */
    public void playMusicaDeFundo()
    {
        if(musicaAtiva)
        {
            sonMusicaDeFundo.loop();
        }
    }
    
    /**
     * Para a música de fundo.
     */
    public void pauseMusicaDeFundo()
    {
        sonMusicaDeFundo.stop();
    }
    
    /**
     * Ativa/Desativa o son.
     * 
     * @param sonAtivo Se o son deve está ativo.
     */
    public void setSon(boolean sonAtivo)
    {
        this.sonAtivo = sonAtivo;
    }
    
    /**
     * Ativa/Desativa a música.
     * 
     * @param musicaAtiva Se a música deve está ativa.
     */
    public void setMusica(boolean musicaAtiva)
    {
        this.musicaAtiva = musicaAtiva;
        if(musicaAtiva)
        {
            playMusicaDeFundo();
        }
        else
        {
            pauseMusicaDeFundo();
        }
    }
    
    /**
     * Informa se o son está ativo.
     * 
     * @return True se estiver ativo.
     */
    public boolean sonAtivo()
    {
        return sonAtivo;
    }
    
    /**
     * Informa se a música está ativa.
     * 
     * @return True se estiver ativa.
     */
    public boolean musicaAtiva()
    {
        return musicaAtiva;
    }
}
