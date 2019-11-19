package hangmangame.gui;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Escreva a descrição da classe DollPanel aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class DollPanel extends JPanel
{
    private static final Icon DEFAULT_IMAGE = new ImageIcon(DollPanel.class.getResource("images/default_doll.png"));
    private static final Icon IMAGE_DOLL_1 = new ImageIcon(DollPanel.class.getResource("images/image_doll_1.png"));
    private static final Icon IMAGE_DOLL_2 = new ImageIcon(DollPanel.class.getResource("images/image_doll_2.png"));
    private static final Icon IMAGE_DOLL_3 = new ImageIcon(DollPanel.class.getResource("images/image_doll_3.png"));
    private static final Icon IMAGE_DOLL_4 = new ImageIcon(DollPanel.class.getResource("images/image_doll_4.png"));
    private static final Icon IMAGE_DOLL_5 = new ImageIcon(DollPanel.class.getResource("images/image_doll_5.png"));
    private static final Icon IMAGE_DOLL_6 = new ImageIcon(DollPanel.class.getResource("images/image_doll_6.png"));
    
    JLabel imageLabel;
    
    public DollPanel()
    {
        setBorder(new EtchedBorder());
        
        imageLabel = creatImageLabel();
        add(imageLabel);
    }
    
    private JLabel creatImageLabel()
    {
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(DEFAULT_IMAGE);
        
        return imageLabel;
    }
    
    public void show(int errorsNumber)
    {
        switch(errorsNumber)
        {
            case 0:
                imageLabel.setIcon(DEFAULT_IMAGE);
                break;
                
            case 1:
                imageLabel.setIcon(IMAGE_DOLL_1);
                break;
            
            case 2:
                imageLabel.setIcon(IMAGE_DOLL_2);
                break;    
                
            case 3:
                imageLabel.setIcon(IMAGE_DOLL_3);
                break;
                
            case 4:
                imageLabel.setIcon(IMAGE_DOLL_4);
                break;
                
            case 5:
                imageLabel.setIcon(IMAGE_DOLL_5);
                break;
                
            case 6:
                imageLabel.setIcon(IMAGE_DOLL_6);
        }
        
        repaint();
    }
}
