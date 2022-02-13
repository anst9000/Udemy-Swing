package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel
{
	private JTextArea textArea;

	public TextPanel()
	{
		textArea = new JTextArea();
    textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    textArea.setBackground(new Color(225, 255, 255));
    textArea.setForeground(Color.BLACK);

		setLayout( new BorderLayout() );
		add( new JScrollPane( textArea ), BorderLayout.CENTER );
	}

	public void appendText( String text )
	{
		textArea.append( text );
	}

  public void setText(String content) {
    textArea.setText(content);
  }

}
