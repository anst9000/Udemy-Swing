package gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener
{
	private JButton helloButton;
	private JButton goodbyeButton;

	private IStringListener stringListener;

	public Toolbar()
	{
		helloButton = new JButton( "hello" );
		goodbyeButton = new JButton( "goodbye" );

		helloButton.addActionListener( this );
		goodbyeButton.addActionListener( this );

		setBorder( BorderFactory.createEtchedBorder() );

		setLayout( new FlowLayout( FlowLayout.LEFT ) );
		add( helloButton );
		add( goodbyeButton );
	}

	public void setStringListener( IStringListener stringListener ) {
		this.stringListener = stringListener;
	}

	@Override
	public void actionPerformed( ActionEvent e )
	{
		JButton clicked = (JButton) e.getSource();

		if ( clicked == helloButton ) {
			if ( stringListener != null ) {
				stringListener.textEmitted( "hello\n" );
			}
		} else if ( clicked == goodbyeButton ) {

			if ( stringListener != null ) {
				stringListener.textEmitted( "goodbye\n" );
			}
		}
	}

}
