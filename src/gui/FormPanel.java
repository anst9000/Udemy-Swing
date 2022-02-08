package gui;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JLabel ageLabel;
	private JLabel employmentLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okButton;

	private JList<AgeCategory> ageList;
	private JComboBox<String> employmentCombo;
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel citizenLabel;
	private JLabel taxLabel;

	private ButtonGroup genderGroup;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;

	private IFormListener formListener;

	public FormPanel() {
		Dimension dimension = getPreferredSize();
		dimension.width = 250;
		setPreferredSize( dimension );

		Border innerBorder = BorderFactory.createTitledBorder( "Add Person" );
		Border outerBorder = BorderFactory.createEmptyBorder( 5, 5, 5, 5 );
		setBorder( BorderFactory.createCompoundBorder( outerBorder, innerBorder ) );

		nameLabel = new JLabel( "Name: " );
		occupationLabel = new JLabel( "Occupation: " );
		ageLabel = new JLabel( "Age: " );
		employmentLabel = new JLabel( "Employment: " );
		citizenLabel = new JLabel( "US Citizen: " );
		taxLabel = new JLabel( "Tax ID: " );

		nameField = new JTextField( 10 );
		occupationField = new JTextField( 10 );
		taxField = new JTextField( 10 );

		ageList = new JList<>();

		employmentCombo = new JComboBox<>();
		citizenCheck = new JCheckBox();

		maleRadio = new JRadioButton( "male" );
		femaleRadio = new JRadioButton( "female" );
		genderGroup = new ButtonGroup();

		okButton = new JButton( "OK" );

		// Set up mnemonics
		okButton.setMnemonic( KeyEvent.VK_O );
		nameLabel.setDisplayedMnemonic( KeyEvent.VK_N );
		nameLabel.setLabelFor( nameField );

		// Set up JList
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
		ageModel.addElement( new AgeCategory( 0, "Under 18" ) );
		ageModel.addElement( new AgeCategory( 1, "18 to 65" ) );
		ageModel.addElement( new AgeCategory( 2, "Above 65" ) );
		ageList.setModel( ageModel );

		ageList.setPreferredSize( new Dimension( 110, 70 ) );
		ageList.setBorder( BorderFactory.createEtchedBorder() );
		ageList.setSelectedIndex( 1 );


		// Set up JComboBox
		DefaultComboBoxModel<String> employmentModel = new DefaultComboBoxModel<>();
		employmentModel.addElement( "employed" );
		employmentModel.addElement( "self-employed" );
		employmentModel.addElement( "unemployed" );
		employmentCombo.setModel( employmentModel );
		employmentCombo.setSelectedIndex( 0 );

		// Set up the checkbox and tax ID
		taxLabel.setEnabled( false );
		taxField.setEnabled( false );

		citizenCheck.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled( isTicked );
				taxField.setEnabled( isTicked );
			}

		} );

		// Set up gender radios
		maleRadio.setSelected( true );
		maleRadio.setActionCommand( "male" );
		femaleRadio.setActionCommand( "female" );
		genderGroup.add( maleRadio );
		genderGroup.add( femaleRadio );

		layoutComponents();

		okButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCategory = ageList.getSelectedValue();
				String employmentCategory = (String) employmentCombo.getSelectedItem();
				boolean usCitizen = citizenCheck.isSelected();
				String taxId = taxField.getText();
				String gender = genderGroup.getSelection().getActionCommand();

				if ( name.equals( "" ) || occupation.equals( "" ) ) {
					return;
				}

				FormEvent event = new FormEvent( this, name, occupation, ageCategory.getId(), employmentCategory,
						usCitizen, taxId, gender );

				if ( formListener != null ) {
					formListener.formSubmitted( event );
				}

				nameField.setText( "" );
				occupationField.setText( "" );

				ageList.setSelectedIndex( 1 );
				employmentCombo.setSelectedIndex( 0 );

				citizenCheck.setSelected( false );
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled( isTicked );
				taxField.setEnabled( isTicked );
				taxField.setText( "" );
				maleRadio.setSelected( true );
			}

		} );
	}

	public void layoutComponents() {
		setLayout( new GridBagLayout() );

		GridBagConstraints gc = new GridBagConstraints();

//		**** First row ****
		gc.gridy = 0;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( nameLabel, gc );

		gc.gridx = 1;
		gc.gridy = 0;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.LINE_START;
		add( nameField, gc );


//		**** Second row ****	gc.gridy = 1;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( occupationLabel, gc );

		gc.gridx = 1;
		gc.gridy = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.LINE_START;
		add( occupationField, gc );


//		**** Third row ****	gc.gridy = 2;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( ageLabel, gc );

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( ageList, gc );


//		**** Fourth row ****	gc.gridy = 3;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( employmentLabel, gc );

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( employmentCombo, gc );


//		**** Fifth row ****	gc.gridy = 4;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( citizenLabel, gc );

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( citizenCheck, gc );


//		**** Sixth row ****	gc.gridy = 5;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( taxLabel, gc );

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( taxField, gc );


//		**** Seventh row ****	gc.gridy = 6;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.05;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets( 0, 0, 0, 5 );
		add( new JLabel( "Gender: " ), gc );

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( maleRadio, gc );

//		**** Eighth row ****	gc.gridy = 7;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( femaleRadio, gc );

//		**** Ninth row ****	gc.gridy = 8;
		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 2.0;

		gc.gridx = 1;
		gc.insets = new Insets( 0, 0, 0, 0 );
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add( okButton, gc );
	}

	public void setFormListener( IFormListener formListener ) {
		this.formListener = formListener;
	}

}

class AgeCategory {

	private int id;
	private String text;

	public AgeCategory( int id, String text ) {
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText( String text ) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
