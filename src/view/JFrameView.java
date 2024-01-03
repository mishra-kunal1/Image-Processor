package view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.GUIFeaturesInterface;
import controller.ImageUtil;
import model.Image;
import model.RGBPixel;

/**
 * Represents the JFrame view of the program. That supports the GUI.
 */
public class JFrameView extends JFrame implements IViewGUI {
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton grayscaleButton;
  private JButton horizontalFlipButton;
  private JButton verticalFlipButton;
  private JButton colorCorrectButton;
  private JButton sepiaButton;
  private JButton rgbButton;
  private JButton compressButton;
  private JButton levelAdjustButton;
  private JButton brightenButton;
  private JButton ditherButton;

  private JToggleButton splitViewButton;

  private JPanel operationsPanel;
  private JPanel multiOperationsPanel;
  private JPanel histogramPanel;
  private JPanel fileOperationsPanel;
  private JPanel imagePanel;

  private JScrollPane imageScrollPane;
  private JComboBox<String> rgbComponentDropdown;
  private JSpinner compressSpinner;
  private JSpinner bSpinner;
  private JSpinner mSpinner;
  private JSpinner wSpinner;
  private JSpinner brightenSpinner;
  private JSpinner splitSpinner;

  private JLabel imageLabel;
  private JLabel histogramLabel;

  private boolean fileOpened;
  private boolean fileSaved;

  private boolean splitView;

  public JFrameView() {
    super();
    setTitle("GRIME");
    setSize(1100, 864);
    setLayout(new BorderLayout());

    splitView = false;
    fileOpened = false;
    fileSaved = true;
    fileOperationsPanel = new JPanel();
    fileOperationsPanel.setBorder(BorderFactory.createTitledBorder("File"));
    fileOperationsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
    fileOpenButton = new JButton("Open a file");
    fileSaveButton = new JButton("Save a file");
    fileOperationsPanel.add(fileOpenButton);
    fileOperationsPanel.add(fileSaveButton);

    operationsPanel = new JPanel();
    operationsPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    operationsPanel.setLayout(new BoxLayout(operationsPanel, BoxLayout.PAGE_AXIS));

    blurButton = new JButton("Blur");
    operationsPanel.add(blurButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    sharpenButton = new JButton("Sharpen");
    operationsPanel.add(sharpenButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    grayscaleButton = new JButton("Grayscale");
    operationsPanel.add(grayscaleButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    colorCorrectButton = new JButton("Color Correct");
    operationsPanel.add(colorCorrectButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    horizontalFlipButton = new JButton("Horizontal Flip");
    operationsPanel.add(horizontalFlipButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    verticalFlipButton = new JButton("Vertical Flip");
    operationsPanel.add(verticalFlipButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    sepiaButton = new JButton("Sepia");
    operationsPanel.add(sepiaButton);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    ditherButton = new JButton("Dither");
    operationsPanel.add(ditherButton);
    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    multiOperationsPanel = new JPanel();
    multiOperationsPanel.setLayout(new BoxLayout(multiOperationsPanel, BoxLayout.PAGE_AXIS));
    operationsPanel.add(multiOperationsPanel);

    operationsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    JPanel rgbPanel = new JPanel();
    rgbPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    rgbPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    String[] rgbOptions = {"Red", "Green", "Blue"};
    rgbComponentDropdown = new JComboBox<>(rgbOptions);
    rgbButton = new JButton("Component");
    rgbPanel.add(rgbComponentDropdown);
    rgbPanel.add(rgbButton);
    multiOperationsPanel.add(rgbPanel);

    JPanel compressPanel = new JPanel();
    compressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    compressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel compressLabel = new JLabel("");
    compressSpinner = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
    compressButton = new JButton("Compress");
    compressPanel.add(compressLabel);
    compressPanel.add(compressSpinner);
    compressPanel.add(compressButton);
    multiOperationsPanel.add(compressPanel);

    JPanel levelAdjustPanel = new JPanel();
    levelAdjustPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    levelAdjustPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    levelAdjustPanel.setLayout(new BoxLayout(levelAdjustPanel, BoxLayout.Y_AXIS));

    JPanel bPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel bLabel = new JLabel("B");
    SpinnerNumberModel bModel = new SpinnerNumberModel(0, 0, 255, 1);
    bSpinner = new JSpinner(bModel);
    bPanel.add(bLabel);
    bPanel.add(bSpinner);
    levelAdjustPanel.add(bPanel);

    JPanel mPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel mLabel = new JLabel("M");
    SpinnerNumberModel mModel = new SpinnerNumberModel(128, 0, 255, 1);
    mSpinner = new JSpinner(mModel);
    levelAdjustButton = new JButton("Level Adjust");
    mPanel.add(mLabel);
    mPanel.add(mSpinner);
    mPanel.add(levelAdjustButton);
    levelAdjustPanel.add(mPanel);

    JPanel wPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel wLabel = new JLabel("W");
    SpinnerNumberModel wModel = new SpinnerNumberModel(255, 0, 255, 1);
    wSpinner = new JSpinner(wModel);
    wPanel.add(wLabel);
    wPanel.add(wSpinner);
    levelAdjustPanel.add(wPanel);

    bSpinner.addChangeListener(
        e -> {
          int bValue = (int) bSpinner.getValue();
          mModel.setMinimum(bValue + 1);
        });

    mSpinner.addChangeListener(
        e -> {
          int mValue = (int) mSpinner.getValue();
          wModel.setMinimum(mValue + 1);

          bModel.setMaximum(mValue - 1);
        });

    wSpinner.addChangeListener(
        e -> {
          int wValue = (int) wSpinner.getValue();
          mModel.setMaximum(wValue - 1);
        });

    multiOperationsPanel.add(levelAdjustPanel);

    JPanel brightenPanel = new JPanel();
    brightenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    brightenPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JLabel brightenLabel = new JLabel("");
    brightenSpinner = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));
    brightenButton = new JButton("Brighten");
    brightenPanel.add(brightenLabel);
    brightenPanel.add(brightenSpinner);
    brightenPanel.add(brightenButton);
    multiOperationsPanel.add(brightenPanel);

    JPanel splitPanel = new JPanel();
    splitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    splitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    splitSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 99, 1));
    splitViewButton = new JToggleButton("Split View");
    splitPanel.add(splitSpinner);
    splitPanel.add(splitViewButton);
    multiOperationsPanel.add(splitPanel);

    histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(165, 165));
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setLayout(new BorderLayout());
    operationsPanel.add(histogramPanel);

    imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image"));
    imagePanel.setLayout(new BorderLayout());

    JLabel imagePlaceholder = new JLabel("Image will appear here");
    imagePlaceholder.setHorizontalAlignment(JLabel.CENTER);
    imagePanel.add(imagePlaceholder, BorderLayout.CENTER);

    add(fileOperationsPanel, BorderLayout.NORTH);
    add(operationsPanel, BorderLayout.WEST);
    add(imagePanel, BorderLayout.CENTER);

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    addWindowListener(
        new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
            if (fileSaved) {
              System.exit(0);
            } else {
              showExitQuery();
            }
          }
        });

    setVisible(true);
  }

  @Override
  public void setImage(Image image) {
    imagePanel.removeAll();
    try {
      imageLabel = new JLabel(new ImageIcon(ImageUtil.convertToBuffered(image)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    imageLabel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    imageScrollPane =
        new JScrollPane(
            imageLabel,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imagePanel.add(imageScrollPane);
    imagePanel.revalidate();
    imagePanel.repaint();
  }

  @Override
  public void setHistogram(Image image) {
    histogramPanel.removeAll();
    histogramLabel =
        new JLabel() {
          @Override
          protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            RGBPixel[][] pixels = image.getRGBPixelMatrix();
            int pixelSize = 1;
            for (int i = 0; i < image.getHeight(); i++) {
              for (int j = 0; j < image.getWidth(); j++) {
                if (i < pixels.length && j < pixels[i].length) {
                  g.setColor(
                      new Color(
                          pixels[i][j].getRedComponent(),
                          pixels[i][j].getGreenComponent(),
                          pixels[i][j].getBlueComponent()));
                  g.fillRect(j * pixelSize, i * pixelSize, pixelSize, pixelSize);
                }
              }
            }
          }
        };
    histogramLabel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    histogramPanel.add(histogramLabel);
    histogramPanel.revalidate();
    histogramPanel.repaint();
  }

  @Override
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void showSaveQuery(GUIFeaturesInterface guiFeatures) {
    int result =
        JOptionPane.showConfirmDialog(
            this,
            "Do you want to save the current image before proceeding?",
            "Save",
            JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      saveFile(guiFeatures);
    } else if (result == JOptionPane.NO_OPTION) {
      fileSaved = true;
      openFile(guiFeatures);
    }
  }

  private void showExitQuery() {
    int result =
        JOptionPane.showConfirmDialog(
            this,
            "Do you want to save the current image before exiting?",
            "Save",
            JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      fileSaveButton.doClick();
    } else if (result == JOptionPane.NO_OPTION) {
      System.exit(0);
    }
  }

  private void showSplitQuery(GUIFeaturesInterface guiFeatures) {
    int result =
        JOptionPane.showConfirmDialog(
            this, "Do you want to apply the preview Image?", "Save", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      guiFeatures.saveSplitImage();
      guiFeatures.showImage();
    } else if (result == JOptionPane.NO_OPTION) {
      guiFeatures.showImage();
    }
  }

  private void openFile(GUIFeaturesInterface guiFeatures) {
    if (fileSaved) {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter =
          new FileNameExtensionFilter("PNG, JPG, BMP, PPM Images", "png", "jpg", "bmp", "ppm");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(JFrameView.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        guiFeatures.loadImage(filename);
        fileSaved = false;
        fileOpened = true;
      }
    } else {
      showSaveQuery(guiFeatures);
    }
  }

  private void saveFile(GUIFeaturesInterface guiFeatures) {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images (*.png)", "png");
    fchooser.addChoosableFileFilter(filter);
    filter = new FileNameExtensionFilter("JPEG Images (*.jpg, *.jpeg)", "jpg", "jpeg");
    fchooser.addChoosableFileFilter(filter);
    filter = new FileNameExtensionFilter("BMP Images (*.bmp)", "bmp");
    fchooser.addChoosableFileFilter(filter);
    filter = new FileNameExtensionFilter("PPM Images (*.ppm)", "ppm");
    fchooser.addChoosableFileFilter(filter);
    int retvalue = fchooser.showSaveDialog(JFrameView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String extension = "";
      String description = fchooser.getFileFilter().getDescription();

      if (description.contains("PNG")) {
        extension = ".png";
      } else if (description.contains("JPEG")) {
        extension = ".jpg";
      } else if (description.contains("BMP")) {
        extension = ".bmp";
      } else if (description.contains("PPM")) {
        extension = ".ppm";
      }
      String fileName = f.getAbsolutePath();
      String filePath = fileName + extension;
      guiFeatures.saveImage(filePath);
      fileSaved = true;
    }
  }

  @Override
  public void addFeatures(GUIFeaturesInterface guiFeatures) {
    fileOpenButton.addActionListener(evt -> openFile(guiFeatures));
    fileSaveButton.addActionListener(
        evt -> {
          if (fileOpened) {
            saveFile(guiFeatures);
          }
        });
    sepiaButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.sepiaOperation();
          }
        });
    horizontalFlipButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.horizontalFlipOperation();
          }
        });
    verticalFlipButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.verticalFlipOperation();
          }
        });
    blurButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.blurOperation();
          }
        });
    sharpenButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.sharpenOperation();
          }
        });
    grayscaleButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.greyScaleOperation();
          }
        });
    colorCorrectButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.colorCorrectionOperation();
          }
        });
    compressButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.compressionOperation((Integer) compressSpinner.getValue());
          }
        });
    brightenButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.brightnessOperation((Integer) brightenSpinner.getValue());
          }
        });
    levelAdjustButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.adjustLevelsOperation(
                (Integer) bSpinner.getValue(),
                (Integer) mSpinner.getValue(),
                (Integer) wSpinner.getValue());
          }
        });
    rgbButton.addActionListener(
        evt -> {
          if (fileOpened) {
            String selectedComponent = (String) rgbComponentDropdown.getSelectedItem();
            if ("Red".equals(selectedComponent)) {
              guiFeatures.redComponentOperation();
            } else if ("Green".equals(selectedComponent)) {
              guiFeatures.greenComponentOperation();
            } else if ("Blue".equals(selectedComponent)) {
              guiFeatures.blueComponentOperation();
            }
          }
        });
    splitViewButton.addActionListener(
        evt -> {
          if (fileOpened) {
            splitView = splitViewButton.isSelected();
            if (!splitView) {
              showSplitQuery(guiFeatures);
            }
          }
        });
    ditherButton.addActionListener(
        evt -> {
          if (fileOpened) {
            guiFeatures.ditherOperation();
          }
        });
  }

  @Override
  public boolean getSplitStatus() {
    return splitView;
  }

  @Override
  public int getSplitPercentage() {
    return (int) splitSpinner.getValue();
  }
}
