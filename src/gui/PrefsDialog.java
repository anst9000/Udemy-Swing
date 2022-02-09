package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;


public class PrefsDialog extends JDialog {

	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
  private JTextField userField;
  private JPasswordField passwordField;

  private IPrefsListener prefsListener;

	public PrefsDialog( JFrame parent ) {
		super( parent, "Preferences", false );

		okButton = new JButton( "OK" );
		cancelButton = new JButton( "Cancel" );

		spinnerModel = new SpinnerNumberModel( 3306, 0, 9999, 1 );
		portSpinner = new JSpinner( spinnerModel );

    userField = new JTextField(10);
    passwordField = new JPasswordField(10);
    passwordField.setEchoChar('*');

    layoutControls();

		okButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				Integer port = (Integer) portSpinner.getValue();
        String user = userField.getText();
        String password = new String (passwordField.getPassword());

        if (prefsListener != null) {
          prefsListener.preferencesSet(user, password, port);
        }

				setVisible( false );
			}

		} );

		cancelButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				setVisible( false );
			}
		} );

		setSize( 300, 225 );
		setLocationRelativeTo( parent );
	}

  public void setDefaults(String user, String password, int port) {
    userField.setText(user);
    passwordField.setText(password);
    portSpinner.setValue(port);
  }

  public void setPrefsListener(IPrefsListener prefsListener) {
    this.prefsListener = prefsListener;
  }

  private void layoutControls() {
    JPanel controlsPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    int space = 10;
    Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
    Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

    controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));


    // ****** Controls Panel ******
    controlsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

    Insets rightPadding = new Insets(0, 0, 0, 15);
    Insets noPadding = new Insets(0, 0, 0, 0);

    // ****** First row ******
    gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;

    gc.gridx = 0;
    gc.anchor = GridBagConstraints.EAST;
    gc.insets = rightPadding;
		controlsPanel.add( new JLabel( "User: " ), gc );

		gc.gridx++;
    gc.anchor = GridBagConstraints.WEST;
    gc.insets = noPadding;
		controlsPanel.add( userField, gc );

    // ****** Second row ******
    gc.gridy++;
    gc.weightx = 1;
    gc.weighty = 1;
    gc.fill = GridBagConstraints.NONE;

    gc.gridx = 0;
    gc.anchor = GridBagConstraints.EAST;
    gc.insets = rightPadding;
    controlsPanel.add( new JLabel( "Password: " ), gc );

    gc.gridx++;
    gc.anchor = GridBagConstraints.WEST;
    gc.insets = noPadding;
    controlsPanel.add( passwordField, gc );


    // ****** Third row ******
    gc.gridy++;
    gc.weightx = 1;
    gc.weighty = 1;
    gc.fill = GridBagConstraints.NONE;

    gc.gridx = 0;
    gc.anchor = GridBagConstraints.EAST;
    gc.insets = rightPadding;
		controlsPanel.add( new JLabel( "Port: " ), gc );

		gc.gridx++;
    gc.anchor = GridBagConstraints.WEST;
    gc.insets = noPadding;
		controlsPanel.add( portSpinner, gc );


    // ****** Buttons Panel ******
    buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add( okButton);
		buttonsPanel.add( cancelButton);

    Dimension buttonSize = cancelButton.getPreferredSize();
    okButton.setPreferredSize(buttonSize);

    // ****** The Dialog ******
    setLayout(new BorderLayout());
    add(controlsPanel, BorderLayout.CENTER);
    add(buttonsPanel, BorderLayout.SOUTH);
  }

}