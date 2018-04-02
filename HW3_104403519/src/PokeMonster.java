/*** 
 * @Author 侯凱翔
 * 學號: 104403519
 * 系級: 資管3A
 * HW3: 寶可夢大師
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
    
    // 在這邊實作FunctionArea的按鈕功能
    private FunctionArea ButtonList = new FunctionArea() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	// 增加糖果btn
        	if (e.getSource() == this.CandyBtn) { 
                if (NowStatus.getCandy() < 100) {
                    NowStatus.setCandy(NowStatus.getCandy() + 1);
                }
                CheckEvolved(NowStatus);
                CheckImg(NowStatus);
            }
        	
        	// 存檔btn
            if (e.getSource() == this.SaveBtn) {
                try {
                    FileOutputStream fos = new FileOutputStream(save.getSelectedFile().getAbsolutePath());
                    try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(NowStatus);
                        oos.flush(); //清空
                    }
                    JOptionPane.showMessageDialog(PokeMonster.this, "Save successed!", "Noticed", JOptionPane.PLAIN_MESSAGE);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
            
            // 開啟舊檔btn
            if (e.getSource() == this.OpenBtn) {
                save.setFileSelectionMode(JFileChooser.FILES_ONLY); // 用JFileChooser選擇檔案位置
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
                        SaveBtn.setEnabled(true); //將儲存按鈕啟用 預設為關閉
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }                    
                    setFilePathText(save.getSelectedFile().getAbsolutePath());
                    // 顯示寶可夢取名
                    if (!"".equals(NowStatus.getNickname())) { 
                        ButtonList.textField.setText(NowStatus.getNickname());
                    }
                }
            }
            
            // 另存新檔btn
            if (e.getSource() == this.SaveAsBtn) {
                save.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = save.showDialog(SaveAsBtn, "Save");
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fos = new FileOutputStream(save.getSelectedFile().getAbsolutePath());
                        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                            oos.writeObject(NowStatus);
                            oos.flush(); // 清空
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
    
    private String[] ImgPath = new String[3]; //放要顯示的圖片
    private int[] CandyCount = new int[3];
    private int j = 0;
    private PokeSerializable NowStatus = new PokeSerializable("", "small", 0); // 初始設定

    private JFileChooser save = new JFileChooser();

    public PokeMonster() {
        // 版面配置
    	this.setTitle("Pokemon");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(true);
        
        ButtonList.textField.addActionListener(new TextListener());
        ImgPanel.setBackground(Color.WHITE);
        add(ImgPanel, BorderLayout.CENTER);
        add(ButtonList, BorderLayout.SOUTH);

        
        InputStream i = getClass().getResourceAsStream("pokemon.txt"); // 讀取pokemon.txt
        try (InputStreamReader fr = new InputStreamReader(i) { //以inputStream來讀取每一行的資料
        }) {
            BufferedReader reader = new BufferedReader(fr);
            while (reader.ready()) {
                String[] line = reader.readLine().split(" "); // 以文字間空格分開 並存入陣列中
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
            mosterImg = ImageIO.read(this.getClass().getResource("Img\\" + ImgPath[0])); //以讀取到的字串來載入圖片
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        picLabel = new JLabel(new ImageIcon(mosterImg));
        ImgPanel.add(picLabel);
    }

    // 用status:small,mid,large決定放哪張圖
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

    // 進化 
    public void CheckEvolved(PokeSerializable p) {
        if (p.getCandy() == 25 && "small".equals(p.getMonster())) {
            JOptionPane.showMessageDialog(PokeMonster.this, "Your monster is evolved!", "Noticed", JOptionPane.PLAIN_MESSAGE);
            p.setCandy(0); //糖果數歸零
            p.setMonster("mid");
        } else if (p.getCandy() == 100 && "mid".equals(p.getMonster())) {
            JOptionPane.showMessageDialog(PokeMonster.this, "Your monster is evolved!", "Noticed", JOptionPane.PLAIN_MESSAGE);
            JOptionPane.showMessageDialog(PokeMonster.this, "Congratuation!! Your monster has final evolved", "Noticed", JOptionPane.PLAIN_MESSAGE);
            p.setMonster("large");
        }
        ButtonList.setCandyText(p.getCandy(), p.getMonster());
    }
    
    // 設定小名 須按enter輸入
    public class TextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NowStatus.setNickname(ButtonList.textField.getText());
            JOptionPane.showMessageDialog(PokeMonster.this, "Set nickname successed!", "Noticed", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
