package com.trochewiedzy.aes;

import javax.swing.*;
import java.awt.*;

public class App {
    private JPanel mainPanel;
    private JTabbedPane textTabbedPane;
    private JTabbedPane fileTabbedPane;

    // Dialogs
    final JFileChooser fc = new JFileChooser();

    // Plaintext encryption components
    private JTextArea encryptPlaintextTextArea;
    private JTextArea encryptKeyTextArea;
    private JTextArea encryptCiphertextTextArea;
    private JButton encryptTextButton;

    // Plaintext decryption components
    private JTextArea decryptCiphertextTextArea;
    private JTextArea decryptKeyTextArea;
    private JTextArea decryptPlaintextTextArea;
    private JButton decryptTextButton;

    // File encryption components
    private JButton loadPlaintextFileButton;
    private JButton loadEncryptionKeyFileButton;
    private JButton encryptAndSaveOutputButton;

    // File decryption components
    private JButton loadCiphertextFileButton;
    private JButton loadDecryptionKeyFileButton;
    private JButton decryptAndSaveOutputButton;

    public App() {
        loadPlaintextFileButton.addActionListener(e -> fc.showOpenDialog(null));
        loadEncryptionKeyFileButton.addActionListener(e -> fc.showOpenDialog(null));
        encryptAndSaveOutputButton.addActionListener(e -> fc.showSaveDialog(null));

        loadCiphertextFileButton.addActionListener(e -> fc.showOpenDialog(null));
        loadDecryptionKeyFileButton.addActionListener(e -> fc.showOpenDialog(null));
        decryptAndSaveOutputButton.addActionListener(e -> fc.showSaveDialog(null));
    }

    public static void main(String[] args) {
        App app = new App();

        JFrame frame = new JFrame(Constants.APP_NAME);
        frame.setContentPane(app.mainPanel);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
