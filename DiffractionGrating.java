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
    private ArrayList<WykresyPrazki> wykresy2List;
    JSlider slider1;
	private JSlider slider2;
    private JTextField waveField, gratingField;
    private JCheckBox red, green, blue, black;
    private JMenuItem save, open, restart, exit;
    private Map<Color, Boolean> wavelengthSet;
    private JPanel chartsPanel;
    private JPanel pradkiPanel;
    static Color purple = new Color(102, 0, 153);

    public DiffractionGrating() {
        setTitle("Diffraction Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        wykresyList = new ArrayList<>();
        wykresy2List = new ArrayList<>();
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
        
        pradkiPanel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.WHITE);
                for (WykresyPrazki wykres : wykresy2List) {
                    if (wavelengthSet.getOrDefault(wykres.getLineColor(), true)) {
                        wykres.paintComponent(g);
                    }
                }
            }
        };

        slider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider1 = new JSlider(380, 720);
        slider1.setValue(380);
        slider2.setValue(50);
        
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
                updateWykresyWithInitialValues();
                chartsPanel.repaint();
            }
        });

        green.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.GREEN, isChecked);
                updateWykresyWithInitialValues();
                chartsPanel.repaint();
            }
        });

        blue.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.BLUE, isChecked);
                updateWykresyWithInitialValues();
                chartsPanel.repaint();
            }
        });

        black.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
                wavelengthSet.put(Color.WHITE, isChecked);
                updateWykresyWithInitialValues();
                chartsPanel.repaint();
            }
        });
        pradkiPanel.setLayout(new GridLayout(8, 8));
        pradkiPanel.setPreferredSize(new Dimension(150, 200));

        JPanel right = new JPanel();
        JPanel left = new JPanel();
        right.setLayout(new GridLayout(8, 1));
        left.setLayout(new GridLayout(8, 1));
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setBackground(Color.BLACK);
        right.setPreferredSize(new Dimension(150, 200));
        left.setPreferredSize(new Dimension(150, 200));
        

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
    
        right.add(slider1);
        
        menuBar.add(menu);
        setJMenuBar(menuBar);

        right.add(waveLabel);

        right.add(waveField);
        left.add(pradkiPanel);
        bottom.add(gratingLabel);
        bottom.add(gratingField);
        bottom.add(slider2);
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        slider1.addChangeListener(new SliderChangeListener(this));
        slider2.addChangeListener(new SliderChangeListener2(this));
        exit.addActionListener(exitListener);
        restart.addActionListener(restartListener);
        save.addActionListener(saveListener);
        open.addActionListener(openListener);
        
        waveField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(waveField.getText());
                    if (value >= 380 && value <= 720) {
                        slider1.setValue(value);
                        setLambdaAndUpdate(value, Color.WHITE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wartość musi być pomiędzy 380 a 720 nm.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Wprowadź poprawną liczbę.");
                }
            }
        });

        gratingField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(gratingField.getText());
                    if (value >= 0 && value <= 100) {
                        slider2.setValue(value);
                        updateGratingConstant(value);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wartość musi być pomiędzy 0 a 100.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Wprowadź poprawną liczbę.");
                }
            }
        });

        updateWykresyWithInitialValues();
        
        setVisible(true);
    }
    
    private void updateWykresyWithInitialValues() {
        double initialD = slider2.getValue() * 0.00001;
        gratingField.setText(String.valueOf(slider2.getValue()));
        for (WykresyDyfrakcji wykres : wykresyList) {
            wykres.updateD(initialD);
        }
        for (WykresyPrazki wykres : wykresy2List) {
            wykres.updateD(initialD);
        }
        chartsPanel.repaint();
        pradkiPanel.repaint();
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
            wykresy2List.clear();
            addWykresDyfrakcji(Color.RED);
            addWykresDyfrakcji(Color.GREEN);
            addWykresDyfrakcji(Color.BLUE);
            addWykresDyfrakcji(Color.WHITE);
            updateWykresyWithInitialValues();
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
            Parameters2 parameters2 = new Parameters2(wykresy2List);
            outputStream.writeObject(parameters);
            outputStream.writeObject(parameters2);
            System.out.println("Parametry zostały zapisane do pliku.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania parametrów: " + e.getMessage());
        }
    }

    private void loadParametersFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Parameters parameters = (Parameters) inputStream.readObject();
            Parameters2 parameters2 = (Parameters2) inputStream.readObject();
            parameters.applyToWykresy(wykresyList);
            parameters2.applyToWykresy2(wykresy2List);
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

        WykresyDyfrakcji wykres = new WykresyDyfrakcji(d, lambda, 10, this);
        wykres.setLineColor(color);
        wykres.updateLambda(lambda);
        
        WykresyPrazki wykres2 = new WykresyPrazki(d, lambda, 10, this);
        wykres2.setLineColor(color);
        wykres2.updateLambda(lambda);

        wykresyList.add(wykres);
        wykresy2List.add(wykres2);
        wavelengthSet.put(color, true);
    }


    public ArrayList<WykresyDyfrakcji> getWykresyList() {
        return wykresyList;
    }
    
    public ArrayList<WykresyPrazki> getWykresy2List() {
        return wykresy2List;
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
                    for (WykresyPrazki wykres2 : parent.getWykresy2List()) {
                        wykres2.updateD(d);
                    }
                    parent.chartsPanel.repaint();
                    parent.pradkiPanel.repaint();
                }
            }
        }
    }

    public void setLambdaAndUpdate(int value, Color color) {
        double lambda = value * 0.0000001;
        waveField.setText(String.valueOf(value));
        for (WykresyDyfrakcji wykres : wykresyList) {
            if (wavelengthSet.getOrDefault(wykres.getLineColor(), true)) {
                if (wykres.getLineColor().equals(color) || wykres.getLineColor().equals(Color.WHITE)) {
                    wykres.updateLambda(lambda);
                    if (wykres.getLineColor().equals(Color.WHITE)) {
                        wykres.setLineColor(funk(value));
                    }
                }
            }
        }
        for (WykresyPrazki wykres2 : wykresy2List) {
            if (wavelengthSet.getOrDefault(wykres2.getLineColor(), true)) {
                if (wykres2.getLineColor().equals(color) || wykres2.getLineColor().equals(Color.WHITE)) {
                    wykres2.updateLambda(lambda);
                    if (wykres2.getLineColor().equals(Color.WHITE)) {
                        wykres2.setLineColor(funk(value));
                    }
                }
            }
        }
        pradkiPanel.repaint();
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

    private void updateGratingConstant(int value) {
        double d = value * 0.00001;
        for (WykresyDyfrakcji wykres : wykresyList) {
            wykres.updateD(d);
        }
        chartsPanel.repaint();
        for (WykresyPrazki wykres2 : wykresy2List) {
            wykres2.updateD(d);
        }
        pradkiPanel.repaint();
    }
    public int getSlider1Value() {
        return slider1.getValue();
    }
    static Color funk(int value) {
        if (value >= 380 && value < 436) {
            return purple;
        } else if (value >= 436 && value < 495) {
            return Color.BLUE;
        } else if (value >= 495 && value < 566) {
            return Color.GREEN;
        } else if (value >= 566 && value < 589) {
            return Color.YELLOW;
        } else if (value >= 589 && value < 627) {
            return Color.ORANGE;
        } else if (value >= 627 && value <= 780) {
            return Color.RED;
        } else {
            return Color.WHITE;
        }
    }
}
