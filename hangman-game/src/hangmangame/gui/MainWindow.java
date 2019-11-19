package hangmangame.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.lang.Runnable;
import java.awt.EventQueue;

import hangmangame.engine.*;
/**
 * Descrição: Janela principal do jogo
 * da forca.
 * 
 * @author (João Hudson) 
 * @version (24/11/2018)
 */
public class MainWindow extends JFrame
{
    private static final String CHARS_READ_PREFIX = "Letras citadas: ";
    private static final String WORD_PREFIX = "Palavra: ";
    
    private DollPanel dollPanel;
    private JTextField textField;
    private JButton button;
    private JLabel statusLabel;
    private JLabel wordLabel;
    private JLabel charsReadLabel;
    private Game game;
    
    public MainWindow()
    {
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout(5,5));
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        
        JPanel outputPanel = creatOutputPanel();
        contentPane.add(outputPanel, BorderLayout.CENTER);
        
        JPanel inputPanel = creatInputPanel();
        contentPane.add(inputPanel, BorderLayout.SOUTH);
        
        makeMenuBar();
        
        creatNewGame();
        
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Jogo da Forca");
        setResizable(false);
        setLocationByPlatform(true);
        setVisible(true);
    }
    
    public static void main(String[] agrs)
    {
        EventQueue.invokeLater(new Runnable(){
            public void run()
            {
                new MainWindow();
            }
        });
    }
    
    private void creatNewGame()
    {
        game = new HangmanGame();
        actualize();
        init();
    }
    
    private void makeMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        
        JMenuItem newGame = new JMenuItem("Novo Jogo");
        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                creatNewGame();
            }
        });
        menu.add(newGame);
        menu.add(new JSeparator());
        
        JMenuItem about = new JMenuItem("Sobre");
        about.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                showAbout();
            }
        });
        menu.add(about);
        menu.add(new JSeparator());
        
        JMenuItem quit = new JMenuItem("Sair");
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        menu.add(quit);
    }
    
    private JPanel creatOutputPanel()
    {
        JPanel outputPanel = new JPanel();
        
        JPanel resultPanel = new JPanel(new BorderLayout());
        outputPanel.add(resultPanel);
        
        charsReadLabel = new JLabel(CHARS_READ_PREFIX);
        resultPanel.add(charsReadLabel, BorderLayout.NORTH);
        
        dollPanel = new DollPanel();
        resultPanel.add(dollPanel, BorderLayout.CENTER);
        
        wordLabel = new JLabel(WORD_PREFIX);
        resultPanel.add(wordLabel, BorderLayout.SOUTH);
        
        return outputPanel;
    }
    
    private JPanel creatInputPanel()
    {
        JPanel flowPanel = new JPanel();
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        flowPanel.add(inputPanel);
        
        JPanel textPanel = creatTextPanel();
        inputPanel.add(textPanel, BorderLayout.CENTER);
        
        JPanel panelButton = creatPanelButton();
        inputPanel.add(panelButton, BorderLayout.EAST);
        
        return flowPanel;
    }
    
    private JPanel creatPanelButton()
    {
        JPanel panelButton = new JPanel(new FlowLayout());
        panelButton.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        button = creatButton();
        panelButton.add(button);
        
        return panelButton;
    }
    
    private JPanel creatTextPanel()
    {
        JPanel textPanel = new JPanel(new FlowLayout());
        
        JPanel gridPanel = new JPanel(new GridLayout(0,1));
        textPanel.add(gridPanel);
        
        JLabel promptLabel = new JLabel("Digite uma letra/palavra:");
        gridPanel.add(promptLabel);
        
        textField = creatTextField();
        gridPanel.add(textField);
        
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.red);
        gridPanel.add(statusLabel);
        
        return textPanel;
    }
    
    private JTextField creatTextField()
    {
        JTextField textField = new JTextField(10);
        textField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                inputText();
            }
        });
        
        return textField;
    }
    
    private JButton creatButton()
    {
        JButton button = new JButton("Enter");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                inputText();
            }
        });
        
        return button;
    }
    
    private void inputText()
    {
        String text = textField.getText();
        
        if(text.length() > 0)
        {
            game.read(text);
            actualize();
        }
        textField.setText("");
    }
    
    private void actualize()
    {
        showWord();
        showCharsRead();
        showDoll(game.getErrorsNumber());
        
        if(game.isError())
        {
            showErrorMessage();
        }
        else if(game.isRepeated())
        {
            showRepeatedCharMessage();
        }
        else
        {
            clearErrorMessage();
        }
        
        if(game.isFinished() && game.isVictory())
        {
            showVictory();
            finish();
        }
        else if(game.isFinished())
        {
            showLose();
            finish();
        }
    }
    
    private void showWord()
    {
        String word = game.getWord();
        wordLabel.setText(WORD_PREFIX + word);
    }
    
    private void showCharsRead()
    {
        String charsRead = game.getCharactersRead();
        charsReadLabel.setText(CHARS_READ_PREFIX + charsRead);
    }
    
    private void showErrorMessage()
    {
        statusLabel.setText("Letra errada!");
    }
    
    private void showRepeatedCharMessage()
    {
        statusLabel.setText("Letra repetida!");
    }
    
    private void clearErrorMessage()
    {
        statusLabel.setText(" ");
    }
    
    private void showDoll(int errorsNumber)
    {
        dollPanel.show(errorsNumber);
    }
    
    private void showVictory()
    {
        JOptionPane.showMessageDialog(this, "Você venceu!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showLose()
    {
        JOptionPane.showMessageDialog(this, "Você perdeu!", "Derrota", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void finish()
    {
        textField.setEnabled(false);
        button.setEnabled(false);
    }
    
    private void init()
    {
        textField.setEnabled(true);
        button.setEnabled(true);
    }
    
    private void showAbout()
    {
        JOptionPane.showMessageDialog(this, "Autor: João Hudson de Lacerda Oliveira\n" +
                                            "Versão: 2.0", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }
}
