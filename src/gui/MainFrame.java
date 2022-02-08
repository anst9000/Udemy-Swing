package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import controller.Controller;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private TextPanel textPanel;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;

	private Controller controller;

	public MainFrame()
	{
		super( "Welcome" );

		setLayout( new BorderLayout() );

		textPanel = new TextPanel();
		toolbar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog( this );

		controller = new Controller();
		tablePanel.setData( controller.getPeople() );
		tablePanel.setPersonTableListener(new IPersonTableListener() {
			@Override
			public void rowDeleted(int row) {
				controller.removePerson( row );
			}

		} );

		fileChooser = new JFileChooser( "C:\\Temp\\test.per" );
		fileChooser.setAcceptAllFileFilterUsed( false );

		FileFilter filter = new PersonFileFilter();
		fileChooser.addChoosableFileFilter( filter );
		fileChooser.setFileFilter( filter );

		setJMenuBar( createMenuBar() );

		toolbar.setStringListener( new IStringListener() {

			@Override
			public void textEmitted( String text ) {
				textPanel.appendText( text );
			}

		} );

		formPanel.setFormListener( new IFormListener() {

			@Override
			public void formSubmitted( FormEvent event ) {
				controller.addPerson( event );
				tablePanel.refresh();
			}
		} );

		add( formPanel, BorderLayout.WEST );
		add( toolbar, BorderLayout.NORTH );
		add( tablePanel, BorderLayout.CENTER );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setMinimumSize( new Dimension( 600, 500 ) );
		setSize( 600, 500 );
		pack();
		setLocationRelativeTo( null );
		setVisible( true );
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu( "File" );
		JMenuItem exportDataItem = new JMenuItem( "Export Data..." );
		JMenuItem importDataItem = new JMenuItem( "Import Data..." );
		JMenuItem exitItem = new JMenuItem( "Exit" );

		fileMenu.add( exportDataItem );
		fileMenu.add( importDataItem );
		fileMenu.addSeparator();
		fileMenu.add( exitItem );


		JMenu windowMenu = new JMenu( "Window" );
		JMenu showMenu = new JMenu( "Show" );
		JMenuItem prefsItem = new JMenuItem( "Preferences..." );

		JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem( "Person Form" );
		showFormItem.setSelected( true );

		showMenu.add( showFormItem );
		windowMenu.add( showMenu );
		windowMenu.add( prefsItem );

		menuBar.add( fileMenu );
		menuBar.add( windowMenu );

		prefsItem.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				prefsDialog.setVisible( true );
			}

		} );

		showFormItem.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();

				formPanel.setVisible( menuItem.isSelected() );
			}

		} );

		fileMenu.setMnemonic( KeyEvent.VK_F );
		exitItem.setMnemonic( KeyEvent.VK_X );

		exitItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK ) );
		importDataItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_I, ActionEvent.CTRL_MASK ) );

		importDataItem.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {

				if ( fileChooser.showOpenDialog( MainFrame.this ) == JFileChooser.APPROVE_OPTION ) {

					try {
						File file = fileChooser.getSelectedFile();
						System.out.println( file );
						fileChooser.setCurrentDirectory( file );

						controller.loadFromFile( file );
						tablePanel.refresh();
					} catch ( IOException ex ) {
						JOptionPane.showMessageDialog( MainFrame.this, "Could not load data from file.", "Error",
								JOptionPane.ERROR_MESSAGE );
					}
				}
			}
		} );

		exportDataItem.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {

				if ( fileChooser.showSaveDialog( MainFrame.this ) == JFileChooser.APPROVE_OPTION ) {

					try {
						File file = fileChooser.getSelectedFile();
						fileChooser.setCurrentDirectory( file );

						controller.saveToFile( file );
					} catch ( IOException ex ) {
						JOptionPane.showMessageDialog( MainFrame.this, "Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE );
					}
				}
			}
		} );

		exitItem.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent e ) {
				int action = JOptionPane.showConfirmDialog( MainFrame.this,
						"Do you really want to exit the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION );

				if ( action == JOptionPane.OK_OPTION ) {
					System.exit( 0 );
				}

				return;
			}

		} );

		return menuBar;
	}
}
