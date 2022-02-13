package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import controller.Controller;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private TablePanel tablePanel;
	private PrefsDialog prefsDialog;
  private Preferences prefs;
  private JSplitPane splitPane;
  private JTabbedPane tabbedPane;
  private MessagePanel messagePanel;

	private Controller controller;

	public MainFrame()
	{
		super( "Welcome" );

		setLayout( new BorderLayout() );

		toolbar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		prefsDialog = new PrefsDialog( this );
    tabbedPane = new JTabbedPane();
    messagePanel = new MessagePanel(this);

    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabbedPane);
    splitPane.setOneTouchExpandable(true);

    tabbedPane.addTab("Person Database", tablePanel);
    tabbedPane.addTab("Messages", messagePanel);

    prefs = Preferences.userRoot().node("db");

		controller = new Controller();
		tablePanel.setData( controller.getPeople() );

		tablePanel.setPersonTableListener(new IPersonTableListener() {
			@Override
			public void rowDeleted(int row) {
				controller.removePerson( row );
			}
		} );

    tabbedPane.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int tabIndex = tabbedPane.getSelectedIndex();

        if (tabIndex == 1) {
          messagePanel.refresh();
        }
      }
    });

    prefsDialog.setPrefsListener(new IPrefsListener() {
      @Override
      public void preferencesSet(String user, String password, int port) {
        prefs.put("user", user);
        prefs.put("password", password);
        prefs.putInt("port", port);

        try {
          controller.configure(port, user, password);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(MainFrame.this, "Unable to re-connect to DB.");
        }
      }

    });

    String user = prefs.get("user", "");
    String password = prefs.get("password", "");
    Integer port = prefs.getInt("port", 3306);

    prefsDialog.setDefaults(user, password, port);

    try {
      controller.configure(port, user, password);
    } catch (Exception e) {
      System.err.println("Cannot connect DB.");
    }

		fileChooser = new JFileChooser( "C:\\Temp\\test.per" );
		fileChooser.setAcceptAllFileFilterUsed( false );

		FileFilter filter = new PersonFileFilter();
		fileChooser.addChoosableFileFilter( filter );
		fileChooser.setFileFilter( filter );

		setJMenuBar( createMenuBar() );

		toolbar.setToobarListener( new IToolbarListener() {

			@Override
			public void saveEventOccured( ) {
        connect();

        try {
          controller.save();
        } catch (SQLException e) {
          JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to DB.", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
        }
			}

      @Override
      public void refreshEventOccured() {
        refresh();
      }
    } );

		formPanel.setFormListener( new IFormListener() {

			@Override
			public void formSubmitted( FormEvent event ) {
				controller.addPerson( event );
				tablePanel.refresh();
			}
		} );

		add( toolbar, BorderLayout.PAGE_START );
		add( splitPane, BorderLayout.CENTER );

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        controller.disconnect();
        dispose();

        // Run the garbage collector
        System.gc();
      }
    });

		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );

    refresh();

		setMinimumSize( new Dimension( 600, 500 ) );
		setSize( 600, 500 );
		pack();
		setLocationRelativeTo( null );
		setVisible( true );
	}

  private void refresh() {
    connect();

    try {
      controller.load();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from DB.", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
    }

    tablePanel.refresh();
  }

  private void connect() {
    try {
      controller.connect();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to DB.", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
    }
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

        if (menuItem.isSelected()) {
          splitPane.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
        }

        formPanel.setVisible( menuItem.isSelected() );
			}

		} );

		fileMenu.setMnemonic( KeyEvent.VK_F );
		exitItem.setMnemonic( KeyEvent.VK_X );

		exitItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK ) );
		importDataItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_I, ActionEvent.CTRL_MASK ) );
		prefsItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.CTRL_MASK ) );

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
          WindowListener[] windowListeners = getWindowListeners();

          for (WindowListener listener : windowListeners) {
            listener.windowClosing(new WindowEvent(MainFrame.this, 0));
          }
				}

				return;
			}

		} );

		return menuBar;
	}
}
