package gui;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.ColorUIResource;

import model.Message;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;

import javax.swing.ImageIcon;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.BufferedInputStream;

/**
 * Note -- this demonstrates using an arbitrary
 * component as a list box renderer.
 */

public class MessageListRenderer implements ListCellRenderer {

  private JPanel panel;
  private JLabel label;
  private Color selectedColor;
  private Color normalColor;

  public MessageListRenderer() {
    panel = new JPanel();
    label = new JLabel();

    selectedColor = new ColorUIResource(210, 210, 255);
    normalColor = Color.WHITE;

    label.setFont(new Font("Monospaced", Font.BOLD, 14));

    label.setIcon(createIcon("/images/Information24.gif"));
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));

    panel.add(label);
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
      boolean cellHasFocus) {

    Message message = (Message) value;
    label.setText(message.getTitle());
    panel.setBackground(cellHasFocus ? selectedColor : normalColor);

    return panel;
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

}
