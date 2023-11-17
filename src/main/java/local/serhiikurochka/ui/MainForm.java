package local.serhiikurochka.ui;

import local.serhiikurochka.CaesarCipher;

import javax.swing.*;
import java.awt.event.*;

public class MainForm extends JFrame {
    private JTextField txtFile;
    private JTextField txtKey;
    private JLabel filePath;
    private JLabel key;
    private JButton btnEncrypt;
    private JButton btnBruteForce;
    private JPanel mainPanel;
    private JLabel lblMessage;

    public MainForm() {
        setContentPane(mainPanel);
        setTitle("Caesar Cipher by Serhii Kurochka");
        setSize(500, 210);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        btnEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaesarCipher caesarCipher = new CaesarCipher();
                String filePath = txtFile.getText();
                if (filePath.contains("[ENCRYPTED]")) {
                    caesarCipher.decryptFile(filePath, Integer.parseInt(txtKey.getText()));
                    lblMessage.setText("File is decrypted");
                } else {
                    caesarCipher.encryptFile(filePath, Integer.parseInt(txtKey.getText()));
                    lblMessage.setText("File is encrypted");
                }

            }
        });

        btnBruteForce.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaesarCipher caesarCipher = new CaesarCipher();
                String filePath = txtFile.getText();
                int key = caesarCipher.bruteForceFile(filePath);
                lblMessage.setText("Encryption key is " + key);
               // txtKey.setText(String.valueOf(key));
            }
        });


        btnEncrypt.addMouseListener(new MouseAdapter() {
        });
        btnEncrypt.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                String filePath = txtFile.getText();
                if (filePath.contains("[ENCRYPTED]")) {
                    btnEncrypt.setToolTipText("Decrypt \"" + filePath + "\" file " + " with key - " + txtKey.getText());
                } else {
                    btnEncrypt.setToolTipText("Encrypt \"" + filePath + "\" file " + " with key - " + txtKey.getText());
                }
            }
        });
    }
}
