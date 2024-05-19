package wykres;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class DiffractionGrating extends JFrame {
    private static final long serialVersionUID = 1L;
    private ArrayList<WykresyDyfrakcji> wykresyList;
    private JSlider slider1, slider2;
    private JTextField waveField, gratingField;
    private JCheckBox red, green, blue, black;
    private JMenuItem save, open, restart, exit;
    private Map<Color, Boolean> wavelengthSet;
    private JPanel chartsPanel;

    public DiffractionGrating() {
        setTitle("Diffraction Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        wykresyList = new ArrayList<>();
        wavelengthSet = new HashMap<>();

        chartsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                for (WykresyDyfrakcji wykres : wykresyList) {
                    if (wavelengthSet.getOrDefault(wykres.getLineColor(), true)) {
                        wykres.paintComponent(g);
                    }
                }
            }
        };
        chartsPanel.setLayout(new BorderLayout());
        add(chartsPanel, BorderLayout.CENTER);

        Color redColor = Color.RED;
        Color greenColor = Color.GREEN;
        Color blueColor = Color.BLUE;
        Color blackColor = Color.WHITE;

        addWykresDyfrakcji(redColor);
        addWykresDyfrakcji(greenColor);
        addWykresDyfrakcji(blueColor);
        addWykresDyfrakcji(blackColor);

        red = new JCheckBox("Red");
        green = new JCheckBox("Green");
        blue = new JCheckBox("Blue");
        black = new JCheckBox("Other");

        red.setBackground(Color.red);
        green.setBackground(Color.green);
        blue.setBackground(Color.blue);
        black.setBackground(Color.white);
        
        red.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.RED, isChecked);
                if (isChecked) {
                    addWykresDyfrakcji(Color.RED);
                } else {
                	addOrUpdateWykresDyfrakcji(Color.RED);
                }
                chartsPanel.repaint();
            }
        });

        green.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.GREEN, isChecked);
                if (isChecked) {
                    addWykresDyfrakcji(Color.GREEN);
                } else {
                	addOrUpdateWykresDyfrakcji(Color.GREEN);
                }
                chartsPanel.repaint();
            }
        });

        blue.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.BLUE, isChecked);
                if (isChecked) {
                    addWykresDyfrakcji(Color.BLUE);
                } else {
                	addOrUpdateWykresDyfrakcji(Color.BLUE);
                }
                chartsPanel.repaint();
            }
        });


        black.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.WHITE, isChecked);
                
                if (isChecked) {
                	addWykresDyfrakcji(blackColor);
                }
                chartsPanel.repaint();
            }
        });
        
        JPanel right = new JPanel();
        right.setLayout(new GridLayout(8, 1));
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(Color.BLACK);
        right.setPreferredSize(new Dimension(150, 200));

        JLabel waveLabel = new JLabel("Wavelength (nm):");
        waveLabel.setForeground(Color.WHITE);
        waveField = new JTextField(5);
        waveField.setPreferredSize(new Dimension(50, 30));

        JLabel gratingLabel = new JLabel("Grating constant:");
        gratingLabel.setForeground(Color.WHITE);
        gratingField = new JTextField(5);

        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        restart = new JMenuItem("Restart");
        exit = new JMenuItem("Exit");

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.add(save);
        menu.add(open);
        menu.add(restart);
        menu.add(exit);
        
        red.setSelected(true);
        green.setSelected(true);
        blue.setSelected(true);
        black.setSelected(true);
        
        waveField.setText("380");
        gratingField.setText("50");
        
        right.add(red);
        right.add(green);
        right.add(blue);
        right.add(black);
        
        slider1 = new JSlider(380, 720);
        right.add(slider1);
        
             
        menuBar.add(menu);
        setJMenuBar(menuBar);

        right.add(waveLabel);

        slider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        
        slider1.setValue(380);
        slider2.setValue(50);
        
        right.add(waveField);
        bottom.add(gratingLabel);
        bottom.add(gratingField);
        bottom.add(slider2);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        slider1.addChangeListener(new SliderChangeListener(this));
        slider2.addChangeListener(new SliderChangeListener2(this));
        exit.addActionListener(exitListener);
        restart.addActionListener(restartListener);
        save.addActionListener(saveListener);
        open.addActionListener(openListener);
        setVisible(true);
    }

    ActionListener exitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
    ActionListener restartListener = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            slider1.setValue(380);
            slider2.setValue(50);
            waveField.setText("380");
            gratingField.setText("50");
            red.setSelected(true);
            green.setSelected(true);
            blue.setSelected(true);
            black.setSelected(true);
            wavelengthSet.clear();
            wykresyList.clear();
            addWykresDyfrakcji(Color.RED);
            addWykresDyfrakcji(Color.GREEN);
            addWykresDyfrakcji(Color.BLUE);
            addWykresDyfrakcji(Color.WHITE);
            chartsPanel.repaint();
        }
    };
    ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveParametersToFile("parameters.txt");
        }
    };
    ActionListener openListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadParametersFromFile("parameters.txt");
        }
    };
    
    private void saveParametersToFile(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            Parameters parameters = new Parameters(wykresyList);
            outputStream.writeObject(parameters);
            System.out.println("Parametry zostały zapisane do pliku.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania parametrów: " + e.getMessage());
        }
    }

    private void loadParametersFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Parameters parameters = (Parameters) inputStream.readObject();
            parameters.applyToWykresy(wykresyList);
            System.out.println("Parametry zostały wczytane z pliku.");
            chartsPanel.repaint();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd podczas wczytywania parametrów: " + e.getMessage());
        }
    }
    
    private void addWykresDyfrakcji(Color color) {
        double lambda = 0.0005;
        double d = 0.002;

        if (color.equals(Color.RED) && wavelengthSet.getOrDefault(Color.RED, true)) {
            lambda = 650 * 0.0000001;
        } else if (color.equals(Color.GREEN) && wavelengthSet.getOrDefault(Color.GREEN, true)) {
            lambda = 550 * 0.0000001;
        } else if (color.equals(Color.BLUE) && wavelengthSet.getOrDefault(Color.BLUE, true)) {
            lambda = 450 * 0.0000001;
        }

        WykresyDyfrakcji wykres = new WykresyDyfrakcji(d, lambda, 10);
        wykres.setLineColor(color);
        wykres.updateLambda(lambda);

        wykresyList.add(wykres);
        wavelengthSet.put(color, true);
    }


    public ArrayList<WykresyDyfrakcji> getWykresyList() {
        return wykresyList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DiffractionGrating();
        });
    }

    public class SliderChangeListener implements ChangeListener {
        private DiffractionGrating parent;

        public SliderChangeListener(DiffractionGrating parent) {
            this.parent = parent;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int value = source.getValue();
                if (source == parent.slider1) {
                    parent.setLambdaAndUpdate(value, Color.WHITE);
                } else if (source == parent.slider2) {
                    double d = value * 0.00001;
                    parent.gratingField.setText(String.valueOf(value));
                    for (WykresyDyfrakcji wykres : parent.getWykresyList()) {
                        wykres.updateD(d);
                    }
                    parent.chartsPanel.repaint();
                }
            }
        }
    }


    private void setLambdaAndUpdate(int value, Color color) {
        double lambda = value * 0.0000001;
        waveField.setText(String.valueOf(lambda));
        for (WykresyDyfrakcji wykres : wykresyList) {
            if (wavelengthSet.getOrDefault(wykres.getLineColor(), true) && wykres.getLineColor().equals(color)) {
                wykres.updateLambda(lambda);
            }
        }
        chartsPanel.repaint();
    }



    public class SliderChangeListener2 implements ChangeListener {
        private DiffractionGrating parent;

        public SliderChangeListener2(DiffractionGrating parent) {
            this.parent = parent;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int value = source.getValue();
                double d = value * 0.00001;
                parent.gratingField.setText(String.valueOf(value));
                for (WykresyDyfrakcji wykres : parent.getWykresyList()) {
                    wykres.updateD(d);
                }
                parent.chartsPanel.repaint();
            }
        }
    }
    private void addOrUpdateWykresDyfrakcji(Color color) {
        double lambda = 0.0005;
        double d = 0.002;

        if (color.equals(Color.RED) && wavelengthSet.getOrDefault(Color.RED, true)) {
            lambda = 650 * 0.0000001;
        } else if (color.equals(Color.GREEN) && wavelengthSet.getOrDefault(Color.GREEN, true)) {
            lambda = 550 * 0.0000001;
        } else if (color.equals(Color.BLUE) && wavelengthSet.getOrDefault(Color.BLUE, true)) {
            lambda = 450 * 0.0000001;
        }

        boolean found = false;
        for (WykresyDyfrakcji wykres : wykresyList) {
            if (wykres.getLineColor().equals(color)) {
                wykres.updateLambda(lambda);
                found = true;
                break;
            }
        }

        if (!found) {
            WykresyDyfrakcji wykres = new WykresyDyfrakcji(d, lambda, 10);
            wykres.setLineColor(color);
            wykres.updateLambda(lambda);
            wykresyList.add(wykres);
        }
    }


    
}
