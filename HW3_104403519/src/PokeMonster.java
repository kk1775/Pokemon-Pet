/*** 
 * @Author �J�͵�
 * �Ǹ�: 104403519
 * �t��: ���3A
 * HW3: �_�i�ڤj�v
***/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PokeMonster extends JFrame {

    private BufferedImage mosterImg;
    private JPanel ImgPanel = new JPanel();
    private JLabel picLabel;
    
    // �b�o���@FunctionArea�����s�\��
    private FunctionArea ButtonList = new FunctionArea() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	// �W�[�}�Gbtn
        	if (e.getSource() == this.CandyBtn) { 
                if (NowStatus.getCandy() < 100) {
                    NowStatus.setCandy(NowStatus.getCandy() + 1);
                }
                CheckEvolved(NowStatus);
                CheckImg(NowStatus);
            }
        	
        	// �s��btn
            if (e.getSource() == this.SaveBtn) {
                try {
                    FileOutputStream fos = new FileOutputStream(save.getSelectedFile().getAbsolutePath());
                    try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(NowStatus);
                        oos.flush(); //�M��
                    }
                    JOptionPane.showMessageDialog(PokeMonster.this, "Save successed!", "Noticed", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
            
            // �}������btn
            if (e.getSource() == this.OpenBtn) {
                save.setFileSelectionMode(JFileChooser.FILES_ONLY); // ��JFileChooser����ɮצ�m
                int result = save.showDialog(SaveAsBtn, "Open");
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        PokeSerializable openFile;
                        FileInputStream fis = new FileInputStream(save.getSelectedFile().getAbsolutePath());
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        openFile = (PokeSerializable) ois.readObject();
                        ois.close();
                        NowStatus.setCandy(openFile.getCandy());
                        if ("small".equals(openFile.getMonster())) {
                            NowStatus.setMonster("small");
                        } else if ("mid".equals(openFile.getMonster())) {
                            NowStatus.setMonster("mid");
                        } else if ("large".equals(openFile.getMonster())) {
                            NowStatus.setMonster("large");
                        }
                        NowStatus.setNickname(openFile.getNickname());
                        CheckImg(NowStatus);
                        setCandyText(NowStatus.getCandy(), NowStatus.getMonster());
                        SaveBtn.setEnabled(true); //�N�x�s���s�ҥ� �w�]������
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }                    
                    setFilePathText(save.getSelectedFile().getAbsolutePath());
                    // ����_�i�ڨ��W
                    if (!"".equals(NowStatus.getNickname())) { 
                        ButtonList.textField.setText(NowStatus.getNickname());
                    }
                }
            }
            
            // �t�s�s��btn
            if (e.getSource() == this.SaveAsBtn) {
                save.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = save.showDialog(SaveAsBtn, "Save");
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fos = new FileOutputStream(save.getSelectedFile().getAbsolutePath());
                        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                            oos.writeObject(NowStatus);
                            oos.flush(); // �M��
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    setFilePathText(save.getSelectedFile().getAbsolutePath());
                    SaveBtn.setEnabled(true);
                }
            }
        }
    };
    
    private String[] ImgPath = new String[3]; //��n��ܪ��Ϥ�
    private int[] CandyCount = new int[3];
    private int j = 0;
    private PokeSerializable NowStatus = new PokeSerializable("", "small", 0); // ��l�]�w

    private JFileChooser save = new JFileChooser();

    public PokeMonster() {
        // �����t�m
    	this.setTitle("Pokemon");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        
        ButtonList.textField.addActionListener(new TextListener());
        ImgPanel.setBackground(Color.WHITE);
        add(ImgPanel, BorderLayout.CENTER);
        add(ButtonList, BorderLayout.SOUTH);

        
        InputStream i = getClass().getResourceAsStream("pokemon.txt"); // Ū��pokemon.txt
        try (InputStreamReader fr = new InputStreamReader(i) { //�HinputStream��Ū���C�@�檺���
        }) {
            BufferedReader reader = new BufferedReader(fr);
            while (reader.ready()) {
                String[] line = reader.readLine().split(" "); // �H��r���Ů���} �æs�J�}�C��
                ImgPath[j] = line[0];
                if (!"full".equals(line[1])) {
                    CandyCount[j] = Integer.parseInt(line[1]);
                    j++;
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        try {
            mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[0])); //�HŪ���쪺�r��Ӹ��J�Ϥ�
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        picLabel = new JLabel(new ImageIcon(mosterImg));
        ImgPanel.add(picLabel);
    }

    // ��status:small,mid,large�M�w����i��
    public void CheckImg(PokeSerializable p) {
        if (p.getMonster() == "small") {
            ImgPanel.remove(picLabel);
            try {
                mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[0]));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            picLabel = new JLabel(new ImageIcon(mosterImg));
            ImgPanel.add(picLabel);
        } else if (p.getMonster() == "mid") {
            ImgPanel.remove(picLabel);
            try {
                mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[1]));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            picLabel = new JLabel(new ImageIcon(mosterImg));
            ImgPanel.add(picLabel);
        } else if (p.getMonster() == "large") {
            ImgPanel.remove(picLabel);
            try {
                mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[2]));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            picLabel = new JLabel(new ImageIcon(mosterImg));
            ImgPanel.add(picLabel);
        }

    }

    // �i�� 
    public void CheckEvolved(PokeSerializable p) {
        if (p.getCandy() == 25 && "small".equals(p.getMonster())) {
            JOptionPane.showMessageDialog(PokeMonster.this, "Your monster is evolved!", "Noticed", JOptionPane.PLAIN_MESSAGE);
            p.setCandy(0); //�}�G���k�s
            p.setMonster("mid");
        } else if (p.getCandy() == 100 && "mid".equals(p.getMonster())) {
            JOptionPane.showMessageDialog(PokeMonster.this, "Your monster is evolved!", "Noticed", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(PokeMonster.this, "Congratuation!! Your monster has final evolved", "Noticed", JOptionPane.PLAIN_MESSAGE);
            p.setMonster("large");
        }
        ButtonList.setCandyText(p.getCandy(), p.getMonster());
    }
    
    // �]�w�p�W ����enter��J
    public class TextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NowStatus.setNickname(ButtonList.textField.getText());
            JOptionPane.showMessageDialog(PokeMonster.this, "Set nickname successed!", "Noticed", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
