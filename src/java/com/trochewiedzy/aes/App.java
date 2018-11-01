package com.trochewiedzy.aes;

import com.trochewiedzy.aes.crypto.AES;
import com.trochewiedzy.aes.crypto.ECB;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

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
    private JTextArea encryptFileKeyTextArea;
    private JButton encryptAndSaveOutputButton;

    // File decryption components
    private JButton loadCiphertextFileButton;
    private JTextArea decryptFileKeyTextArea;
    private JButton decryptAndSaveOutputButton;

    private int[] encryptionInput;
    private int[] decryptionInput;

    public App() {
        encryptTextButton.addActionListener(e -> encryptPlaintext());
        decryptTextButton.addActionListener(e -> decryptPlaintext());

        loadPlaintextFileButton.addActionListener(e -> loadPlaintext());
        encryptAndSaveOutputButton.addActionListener(e -> encryptFile());

        loadCiphertextFileButton.addActionListener(e -> loadCiphertext());
        decryptAndSaveOutputButton.addActionListener(e -> decryptFile());
    }

    private void encryptPlaintext() {
        String plaintextText = encryptPlaintextTextArea.getText();
        String keyText = encryptKeyTextArea.getText();

        try {
            if (plaintextText.length() == 0) {
                throw new Exception("Plaintext can't be empty.");
            } else if (keyText.length() == 0) {
                throw new Exception("Key can't be empty.");
            }

            byte[] inputBytes = plaintextText.getBytes();
            byte[] keyBytes = keyText.getBytes();

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
        String ciphertextText = decryptCiphertextTextArea.getText();
        String keyText = decryptKeyTextArea.getText();

        try {
            if (ciphertextText.length() == 0) {
                throw new Exception("Ciphertext can't be empty.");
            } else if (keyText.length() == 0) {
                throw new Exception("Key can't be empty.");
            }

            byte[] inputBytes = DatatypeConverter.parseHexBinary(ciphertextText);
            byte[] keyBytes = keyText.getBytes();

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

    private void loadPlaintext() {
        try {
            int result = fc.showOpenDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            byte[] inputBytes = Files.readAllBytes(file.toPath());

            encryptionInput = new int[inputBytes.length];

            for (int i = 0; i < encryptionInput.length; i++) {
                encryptionInput[i] = (int) inputBytes[i] & 0xFF;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void loadCiphertext() {
        try {
            int result = fc.showOpenDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            byte[] inputBytes = Files.readAllBytes(file.toPath());

            decryptionInput = new int[inputBytes.length];

            for (int i = 0; i < decryptionInput.length; i++) {
                decryptionInput[i] = (int) inputBytes[i] & 0xFF;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void encryptFile() {
        String keyText = encryptFileKeyTextArea.getText();

        try {
            if (encryptionInput == null) {
                throw new Exception("You need to open input file first.");
            } else if (keyText.length() == 0) {
                throw new Exception("Key can't be empty.");
            }

            int result = fc.showSaveDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            byte[] keyBytes = keyText.getBytes();

            int[] key = new int[keyBytes.length];

            for (int i = 0; i < key.length; i++) {
                key[i] = (int) keyBytes[i] & 0xFF;
            }

            ECB ecb = new ECB(AES.Type.KEY_128, key);

            int[] output = ecb.encrypt(encryptionInput);
            byte[] outputBytes = new byte[output.length];

            for (int i = 0; i < outputBytes.length; i++) {
                outputBytes[i] = (byte) output[i];
            }

            Files.write(file.toPath(), outputBytes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void decryptFile() {
        String keyText = decryptFileKeyTextArea.getText();

        try {
            if (decryptionInput == null) {
                throw new Exception("You need to open input file first.");
            } else if (keyText.length() == 0) {
                throw new Exception("Key can't be empty.");
            }

            int result = fc.showSaveDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            byte[] keyBytes = keyText.getBytes();

            int[] key = new int[keyBytes.length];

            for (int i = 0; i < key.length; i++) {
                key[i] = (int) keyBytes[i] & 0xFF;
            }

            ECB ecb = new ECB(AES.Type.KEY_128, key);

            int[] output = ecb.decrypt(decryptionInput);
            byte[] outputBytes = new byte[output.length];

            for (int i = 0; i < outputBytes.length; i++) {
                outputBytes[i] = (byte) output[i];
            }

            Files.write(file.toPath(), outputBytes);
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
