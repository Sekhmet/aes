package com.trochewiedzy.aes;

import com.trochewiedzy.aes.crypto.AES;
import com.trochewiedzy.aes.crypto.ECB;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
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
        encryptTextButton.addActionListener(e -> encryptPlaintext());
        decryptTextButton.addActionListener(e -> decryptPlaintext());

        loadPlaintextFileButton.addActionListener(e -> fc.showOpenDialog(null));
        loadEncryptionKeyFileButton.addActionListener(e -> fc.showOpenDialog(null));
        encryptAndSaveOutputButton.addActionListener(e -> fc.showSaveDialog(null));

        loadCiphertextFileButton.addActionListener(e -> fc.showOpenDialog(null));
        loadDecryptionKeyFileButton.addActionListener(e -> fc.showOpenDialog(null));
        decryptAndSaveOutputButton.addActionListener(e -> fc.showSaveDialog(null));
    }

    private void encryptPlaintext() {
        try {
            byte[] inputBytes = encryptPlaintextTextArea.getText().getBytes();
            byte[] keyBytes = encryptKeyTextArea.getText().getBytes();

            int[] input = new int[inputBytes.length];
            int[] key = new int[keyBytes.length];

            for (int i = 0; i < input.length; i++) {
                input[i] = (int) inputBytes[i] & 0xFF;
            }

            for (int i = 0; i < key.length; i++) {
                key[i] = (int) keyBytes[i] & 0xFF;
            }

            ECB ecb = new ECB(AES.Type.KEY_128, key);

            int[] output = ecb.encrypt(input);

            StringBuilder outputBuilder = new StringBuilder();

            for (int i = 0; i < output.length; i++) {
                outputBuilder.append(String.format("%02X", output[i]));
            }

            encryptCiphertextTextArea.setText(outputBuilder.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void decryptPlaintext() {
        try {
            byte[] inputBytes = DatatypeConverter.parseHexBinary(decryptCiphertextTextArea.getText());
            byte[] keyBytes = encryptKeyTextArea.getText().getBytes();

            int[] input = new int[inputBytes.length];
            int[] key = new int[keyBytes.length];

            for (int i = 0; i < input.length; i++) {
                input[i] = (int) inputBytes[i] & 0xFF;
            }

            for (int i = 0; i < key.length; i++) {
                key[i] = (int) keyBytes[i] & 0xFF;
            }

            ECB ecb = new ECB(AES.Type.KEY_128, key);

            int[] output = ecb.decrypt(input);
            byte[] outputBytes = new byte[output.length];

            for (int i = 0; i < outputBytes.length; i++) {
                outputBytes[i] = (byte) output[i];
            }

            decryptPlaintextTextArea.setText(new String(outputBytes, "UTF-8"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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
