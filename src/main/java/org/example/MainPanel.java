package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class MainPanel extends JFrame {
    private JPanel mainPanel;
    private JTextField tfAddProducts;
    private JComboBox cbDelete;
    private JButton btnAdd;
    private JButton btnDelete;
    private JLabel lbProducts;
    private JButton btnBreakfast;
    private JButton btnLunch;
    private JButton btnBuyProducts;
    private JTextArea taProducts;
    private JTextArea taChatGPT;
    private JLabel lbTitle;
    private JTextPane tpProducts;
    ProductManager productManager = new ProductManager();
    ChatGPTHelper chatGPTHelper = new ChatGPTHelper();
    boolean thinking;

    MainPanel(JFrame parent) throws URISyntaxException, IOException {
        setTitle("Co zjeść?");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(750,650));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        taChatGPT.setLineWrap(true);
        taChatGPT.setWrapStyleWord(true);



        showProducts();

        setVisible(true);


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    productManager.addProduct(tfAddProducts.getText());
                    showProducts();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    productManager.deleteProduct((String)cbDelete.getSelectedItem()) ;
                    showProducts();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);

                }
            }
        });

        btnBreakfast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                thinking = true;

                Thread thread1 = new Thread(() -> {
                    lbTitle.setText("Trzy pomysły na ŚNIADANIE:");
                    thinking();
                });


                Thread thread2 = new Thread(() -> {
                    try {

                       String breakfastIdea = chatGPTHelper.getMealIdea(productManager.getAllProducts(), "ŚNIADANIE");
                       thinking = false;
                       taChatGPT.setText(breakfastIdea);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                thread1.start();
                thread2.start();
            }
        });

        btnLunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                thinking = true;

                Thread thread1 = new Thread(() -> {
                    lbTitle.setText("Trzy pomysły na OBIAD:");
                    thinking();
                });


                Thread thread2 = new Thread(() -> {
                    try {

                        String lunchIdea = chatGPTHelper.getMealIdea(productManager.getAllProducts(), "OBIAD");
                        thinking = false;
                        taChatGPT.setText(lunchIdea);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                thread1.start();
                thread2.start();
            }
        });

        btnBuyProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thinking = true;

                Thread thread1 = new Thread(() -> {
                    lbTitle.setText("Trzy pomysły na KOLACJĘ:");
                    thinking();
                });


                Thread thread2 = new Thread(() -> {
                    try {

                        String lunchIdea = chatGPTHelper.getMealIdea(productManager.getAllProducts(), "KOLACJĘ");
                        thinking = false;
                        taChatGPT.setText(lunchIdea);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                thread1.start();
                thread2.start();
            }
        });
    }

    public void thinking() {
        try{

            String dot = ".";

        while (thinking) {

            taChatGPT.setText("Myślę" + dot);
            Thread.sleep(500);
            dot += ".";

            if (dot.equals("......")) {
                dot = ".";
            }
        }

        } catch (InterruptedException e){
            e.printStackTrace();
        }    }

    public void showProducts() throws IOException {

        cbDelete.setModel(new DefaultComboBoxModel(productManager.getAllProducts().toArray(new String[0])));
        cbDelete.setSelectedIndex(-1);

        tfAddProducts.setText("");

        List<String> productsList = productManager.getAllProducts();
        String products = productsList.stream()
                        .map(String::valueOf)
                                .collect(Collectors.joining("\n", "", ""));

        taProducts.setText(products);
    }


}
