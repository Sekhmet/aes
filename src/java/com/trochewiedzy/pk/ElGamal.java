package com.trochewiedzy.pk;

import com.trochewiedzy.pk.crypto.ECB;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;

public class ElGamal {
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

    private byte[] encryptionInput;
    private byte[] decryptionInput;

    SecureRandom sc = new SecureRandom();
    BigInteger p = BigInteger.probablePrime(1024, sc);
    BigInteger g = new BigInteger("3");

    public ElGamal() {
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
            } else if (isTooLong(keyText)) {
                throw new Exception("Key too long");
            }

            // Generate keys
            BigInteger a = new BigInteger(keyText.getBytes());

            BigInteger b = g.modPow(a, p);

//        BigInteger[] pubKey = {b, g, p};
//        BigInteger[] privKey = {a, g, p};

            // Encrypt
            BigInteger M = new BigInteger(encryptPlaintextTextArea.getText().getBytes());

            BigInteger k = BigInteger.probablePrime(1024, sc);
            BigInteger c1 = g.modPow(k, p);
            BigInteger c2 = M.multiply(b.modPow(k, p)).mod(p);

            encryptCiphertextTextArea.setText(c1 + "-" + c2);
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
            } else if (isTooLong(keyText)) {
                throw new Exception("Key too long");
            }

            // Generate keys
            BigInteger a = new BigInteger(keyText.getBytes());

//        BigInteger[] pubKey = {b, g, p};
//        BigInteger[] privKey = {a, g, p};

            String[] parts = ciphertextText.split("-");

            BigInteger c1 = new BigInteger(parts[0]);
            BigInteger c2 = new BigInteger(parts[1]);

            // Decrypt
            BigInteger M = c2.multiply(c1.modPow(a, p).modInverse(p)).mod(p);

            decryptPlaintextTextArea.setText(new String(M.toByteArray(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void loadPlaintext() {
        try {
            int result = fc.showOpenDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            encryptionInput = Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void loadCiphertext() {
        try {
            int result = fc.showOpenDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            decryptionInput = Files.readAllBytes(file.toPath());
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
            } else if (isTooLong(keyText)) {
                throw new Exception("Key too long");
            }

            int result = fc.showSaveDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            // Generate keys
            BigInteger a = new BigInteger(keyText.getBytes());

            BigInteger b = g.modPow(a, p);

//        BigInteger[] pubKey = {b, g, p};
//        BigInteger[] privKey = {a, g, p};

            // Encrypt
            BigInteger M = new BigInteger(encryptionInput);

            BigInteger k = BigInteger.probablePrime(1024, sc);
            BigInteger c1 = g.modPow(k, p);
            BigInteger c2 = M.multiply(b.modPow(k, p)).mod(p);

            Files.write(file.toPath(), c1.toByteArray());
//            Files.write(file.toPath(), c2.toByteArray(), StandardOpenOption.APPEND);
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
            } else if (isTooLong(keyText)) {
                throw new Exception("Key too long");
            }

            int result = fc.showSaveDialog(null);

            if (result != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            String ciphertextText = new String(decryptionInput, StandardCharsets.UTF_8);

            // Generate keys
            BigInteger a = new BigInteger(keyText.getBytes());

//        BigInteger[] pubKey = {b, g, p};
//        BigInteger[] privKey = {a, g, p};

            String[] parts = ciphertextText.split("-");

            BigInteger c1 = new BigInteger(parts[0]);
            BigInteger c2 = new BigInteger(parts[1]);

            // Decrypt
            BigInteger M = c2.multiply(c1.modPow(a, p).modInverse(p)).mod(p);


            Files.write(file.toPath(), M.toByteArray());
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private boolean isTooLong(String key) {
        return key.getBytes().length > 1024;
    }

    public static void main(String[] args) {
        ElGamal app = new ElGamal();

        JFrame frame = new JFrame(Constants.APP_NAME);
        frame.setContentPane(app.mainPanel);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
