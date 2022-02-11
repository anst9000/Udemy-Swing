package gui;

import java.awt.Window;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog {

  private JButton cancelButton;
  private JProgressBar progressBar;

  public ProgressDialog(Window parent) {
    super(parent, "Messages Downloading", ModalityType.APPLICATION_MODAL);

    progressBar = new JProgressBar();
    cancelButton = new JButton("Cancel");

    // progressBar.setIndeterminate(true);

    setLayout(new FlowLayout());

    Dimension size = cancelButton.getPreferredSize();
    size.width =  400;
    progressBar.setPreferredSize(size);

    add(cancelButton);
    add(progressBar);

    pack();

    setLocationRelativeTo(parent);
  }

  public void setMaximum(int value) {
    progressBar.setMaximum(value);
  }

  public void setValue(int value) {
    progressBar.setValue(value);
  }

  @Override
  public void setVisible(final boolean visible) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        if (!visible) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        } else {
          progressBar.setValue(0);
        }

        ProgressDialog.super.setVisible(visible);
      }
    });
  }

}
