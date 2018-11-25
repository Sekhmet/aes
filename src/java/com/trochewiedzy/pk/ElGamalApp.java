package com.trochewiedzy.pk;

import com.trochewiedzy.pk.crypto2.BigNumber;
import com.trochewiedzy.pk.crypto2.ElGamal;
import com.trochewiedzy.pk.utils.KeyPair;

import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class ElGamalApp {
    private static final int BLOCK_SIZE = 128;
    private static final int CHUNK_SIZE = 256;

    private JPanel mainPanel;

    // Dialogs
    final JFileChooser fc = new JFileChooser();

    private JTabbedPane textTabbedPane;
    private JTabbedPane fileTabbedPane;

    // Plaintext encryption components
    private JTextArea encryptPlaintextTextArea;
    private JTextArea encryptCiphertextTextArea;
    private JButton encryptTextButton;

    // Plaintext decryption components
    private JTextArea decryptCiphertextTextArea;
    private JTextArea decryptPlaintextTextArea;
    private JButton decryptTextButton;

    // File encryption components
    private JButton loadPlaintextFileButton;
    private JButton encryptAndSaveOutputButton;

    // File decryption components
    private JButton loadCiphertextFileButton;
    private JButton decryptAndSaveOutputButton;

    private JButton generateNewKeypairButton;

    private JLabel publicP;
    private JLabel publicG;
    private JLabel publicB;
    private JLabel privateP;
    private JLabel privateG;
    private JLabel privateA;

    private byte[] encryptionInput;
    private byte[] decryptionInput;

    ElGamal elGamal = new ElGamal();
    KeyPair keyPair = new KeyPair();

    public ElGamalApp() {
        generateNewKeypair();

        generateNewKeypairButton.addActionListener(e -> generateNewKeypair());

        encryptTextButton.addActionListener(e -> encryptPlaintext());
        decryptTextButton.addActionListener(e -> decryptPlaintext());

        loadPlaintextFileButton.addActionListener(e -> loadPlaintext());
        encryptAndSaveOutputButton.addActionListener(e -> encryptFile());

        loadCiphertextFileButton.addActionListener(e -> loadCiphertext());
        decryptAndSaveOutputButton.addActionListener(e -> decryptFile());
    }

    private void generateNewKeypair() {
        keyPair.generate();

        publicP.setText(formatBigNumber("p", keyPair.getPublicKey().p));
        publicG.setText(formatBigNumber("g", keyPair.getPublicKey().g));
        publicB.setText(formatBigNumber("b", keyPair.getPublicKey().b));

        privateP.setText(formatBigNumber("p", keyPair.getPrivateKey().p));
        privateG.setText(formatBigNumber("g", keyPair.getPrivateKey().g));
        privateA.setText(formatBigNumber("a", keyPair.getPrivateKey().a));
    }

    private String formatBigNumber(String name, BigNumber number) {
        return name + ": " + number.toString().substring(0, 20) + "...";
    }

    private void encryptPlaintext() {
        String input = encryptPlaintextTextArea.getText();

        try {
            if (input.length() == 0) {
                throw new Exception("Plaintext can't be empty.");
            }

            BigNumber[] result;

            for (int x = 0; x < input.length() % 128; x++) {
                input += '\u0000';
            }

            StringBuilder outputBuilder = new StringBuilder();

            for (int i = 0; i < input.length() / 128; i++) {
                BigNumber m = new BigNumber(1, input.substring(128 * i, 128 * (i + 1)).getBytes());
                result = elGamal.encryption(m, keyPair.getPublicKey());
                outputBuilder.append(DatatypeConverter.printHexBinary(result[0].toByteArray()));
                outputBuilder.append(DatatypeConverter.printHexBinary(result[1].toByteArray()));
            }

            encryptCiphertextTextArea.setText(outputBuilder.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void decryptPlaintext() {
        String input = decryptCiphertextTextArea.getText();

        try {
            if (input.length() == 0) {
                throw new Exception("Ciphertext can't be empty.");
            }

            String temp;
            byte[] bytes, bResult;
            byte[] b = new byte[128];

            String output = "";

            BigNumber[] r = new BigNumber[2];

            for (int i = 0; i < input.length() / 512; i++) {
                temp = input.substring(512 * i, 512 * (i + 1));

                bytes = DatatypeConverter.parseHexBinary(temp);
                for (int x = 0; x < 128; x++)
                    b[x] = bytes[x];
                r[0] = new BigNumber(b);
                for (int j = 128, x = 0; x < 128; x++, j++)
                    b[x] = bytes[j];
                r[1] = new BigNumber(b);
                BigNumber result = elGamal.decrypt(r, keyPair.getPrivateKey());
                bResult = result.toByteArray();
                String k = new String(bResult);
                output += k;
            }

            decryptPlaintextTextArea.setText(output);
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
        try {
            if (encryptionInput == null) {
                throw new Exception("You need to open input file first.");
            }

            int dialogResult = fc.showSaveDialog(null);

            if (dialogResult != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            byte[] padding = {0};

            byte paddingCount = encryptionInput.length % BLOCK_SIZE == 0 ? 0 : (byte) (BLOCK_SIZE - (encryptionInput.length % BLOCK_SIZE));

            for (int i = 0; i < paddingCount; i++) {
                encryptionInput = addToArray(encryptionInput, padding);
            }

            byte[] splits;
            byte[] output = new byte[0];

            BigNumber[] result;

            for (int i = 0; i < encryptionInput.length / BLOCK_SIZE; i++) {
                splits = split(encryptionInput, BLOCK_SIZE * i, BLOCK_SIZE * (i + 1));

                BigNumber as = new BigNumber(1, splits);

                result = elGamal.encryption(as, keyPair.getPublicKey());

                output = addToArray(output, limit(result[0].toByteArray(), 128));
                output = addToArray(output, limit(result[1].toByteArray(), 128));
            }

            output = addToArray(output, new byte[]{ paddingCount });

            Files.write(file.toPath(), output);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void decryptFile() {
        try {
            if (decryptionInput == null) {
                throw new Exception("You need to open input file first.");
            }

            int dialogResult = fc.showSaveDialog(null);

            if (dialogResult != JFileChooser.APPROVE_OPTION) return;

            File file = fc.getSelectedFile();

            int addBytes = (int) decryptionInput[decryptionInput.length - 1];
            decryptionInput = remove(decryptionInput, 1);

            byte[] splits;
            byte[] output = new byte[0];
            BigNumber[] c = new BigNumber[2];
            BigNumber result;

            for (int i = 0; i < decryptionInput.length / 256; i++) {
                splits = split(decryptionInput, 256 * i, 256 * (i + 1));

                byte[] halves = new byte[128];

                for (int x = 0; x < 128; x++) {
                    halves[x] = splits[x];
                }

                c[0] = new BigNumber(1, halves);

                for (int x = 128, j = 0; j < 128; x++, j++) {
                    halves[j] = splits[x];
                }

                c[1] = new BigNumber(1, halves);

                result = elGamal.decrypt(c, keyPair.getPrivateKey());

//                System.out.println(result.toByteArray().length);

//                result.setSignum(1);

                byte[] r = limit(result.toByteArray(), 128);

                output = addToArray(output, r);
            }

            output = remove(output, addBytes);

            Files.write(file.toPath(), output);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public byte[] limit(byte[] input, int limit) {
        if (input.length == limit) return input;

        int difference = input.length - limit;

        if (difference > 0) {
            return Arrays.copyOfRange(input, difference, limit + difference);
        }

        byte[] result = new byte[limit];

        for (int i = 0; i < limit; i++) {
            result[i] = i < -difference ? 0 : input[i + difference];
        }

        return result;
    }


    public byte[] split(byte[] bytes, int b, int e) {
        byte[] result = new byte[e - b];
        for (int i = b, x = 0; i < e; i++, x++)
            result[x] = bytes[i];

        return result;
    }

    public byte[] addToArray(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i];

        for (int i = a.length, x = 0; x < b.length; i++, x++)
            result[i] = b[x];
        return result;
    }

    public byte[] remove(byte[] a, int count) {
        byte[] result = new byte[a.length - count];
        for (int i = 0; i < result.length; i++)
            result[i] = a[i];
        return result;
    }

    public static void main(String[] args) {
        ElGamalApp app = new ElGamalApp();

        JFrame frame = new JFrame(Constants.APP_NAME_ELGAMAL);
        frame.setContentPane(app.mainPanel);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
