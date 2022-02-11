package gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener
{
	private JButton saveButton;
	private JButton refreshButton;

	private IToolbarListener toobarListener;

	public Toolbar()
	{
    saveButton = new JButton( );
		refreshButton = new JButton( );

    saveButton.setToolTipText("Save");
    refreshButton.setToolTipText("Refresh");

    // Dimension buttonSize = refreshButton.getPreferredSize();
    Dimension buttonSize = new Dimension(100, 26);
    System.out.println("buttonSize " + buttonSize);
    saveButton.setPreferredSize(buttonSize);
    refreshButton.setPreferredSize(buttonSize);

    saveButton.setIcon(createIcon("/images/Save16.gif"));
    refreshButton.setIcon(createIcon("/images/Refresh16.gif"));

		saveButton.addActionListener( this );
		refreshButton.addActionListener( this );

    // Get rid of the border if you want draggable toolbar
		setBorder( BorderFactory.createEtchedBorder() );
    // setFloatable(false);

    add( saveButton );
		add( refreshButton );
	}

  public ImageIcon createIcon(String path) {
    Image image = null;

    try {
      image = ImageIO.read(getClass().getResource(path));
    } catch (IOException e) {
      System.err.println("Unable to load image: " + path);
      e.printStackTrace();
    }

    return new ImageIcon(image);
  }

	public void setToobarListener( IToolbarListener toobarListener ) {
		this.toobarListener = toobarListener;
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
		JButton clicked = (JButton) e.getSource();

		if ( clicked == saveButton ) {
			if ( toobarListener != null ) {
				toobarListener.saveEventOccured( );
			}
		} else if ( clicked == refreshButton ) {
			if ( toobarListener != null ) {
				toobarListener.refreshEventOccured( );
			}
		}
	}

}
