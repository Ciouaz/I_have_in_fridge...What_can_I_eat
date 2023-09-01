package org.example;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ProductManager {
    private final Path productsPath;

    public ProductManager() throws URISyntaxException {
        ClassLoader classLoader = ProductManager.class.getClassLoader();
//        productsPath = Paths.get(Objects.requireNonNull(classLoader.getResource("src/main/resources/products.txt")).toURI());
        productsPath = Paths.get("src/main/resources/products.txt");
    }

    public List<String> getAllProducts() throws IOException {

        return Files.readAllLines(productsPath, StandardCharsets.UTF_8);

        }

    public void addProduct(String product) throws IOException {
        HashSet<String> products = new HashSet<>(getAllProducts());



        if (!products.contains(product)) {
            Files.writeString(productsPath, System.lineSeparator() + product, StandardOpenOption.APPEND);

        } else {
            JOptionPane.showMessageDialog(null,
                    "Masz już taki produkt w lodówce.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteProduct(String product) throws IOException {
        HashSet<String> products = new HashSet<>(getAllProducts());

        if (products.contains(product)) {

            products.remove(product);
            String productsToSave = String.join(System.lineSeparator(), products);
            Files.writeString(productsPath, productsToSave);

        } else {
            JOptionPane.showMessageDialog(null,
                    "Nie masz takiego produktu w lodówce.",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
