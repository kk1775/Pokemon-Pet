/*** 
 * @Author 侯凱翔
 * 學號: 104403519
 * 系級: 資管3A
 * HW3: 寶可夢大師
***/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class FunctionArea extends JPanel implements ActionListener{
    private JLabel givename; // 說明文字(取名須按Enter)
	protected JTextField textField; // 取名輸入區
    private JLabel CandyCount; //糖果數
    private JLabel FilePathText; //左下方顯示檔案來源
    private JPanel CandyBtnList = new JPanel();
    private JPanel SaveBtnList = new JPanel();

    protected final JButton CandyBtn; // 給糖果
    protected final JButton OpenBtn; // 開啟舊檔
    protected final JButton SaveBtn; // 存檔
    protected final JButton SaveAsBtn; // 另存新檔
        
    public FunctionArea(){
        super(new GridLayout(5,0));
        givename = new JLabel();
        givename.setText("幫你的寶可夢取個名字吧! (輸入完畢按Enter)");
        add(givename);
        
        textField = new JTextField();        
        add(textField);
        
        CandyBtn = new JButton("Give Candy!");
        CandyBtn.addActionListener(this);
        CandyCount = new JLabel("0/25");
        CandyBtnList.add(CandyBtn);
        CandyBtnList.add(CandyCount);
        add(CandyBtnList);
        
        OpenBtn = new JButton("Open Game");
        OpenBtn.addActionListener(this);
        SaveBtn = new JButton("Save");
        SaveBtn.addActionListener(this);
        SaveAsBtn = new JButton("Save As");
        SaveAsBtn.addActionListener(this);
        SaveBtnList.add(OpenBtn);
        SaveBtn.setEnabled(false); // 預設New File時還沒有存檔位置，所以設為false不給按
        SaveBtnList.add(SaveBtn);
        SaveBtnList.add(SaveAsBtn);
        add(SaveBtnList);
        
        FilePathText = new JLabel("New File"); // 預設檔案來源為:New File
        add(FilePathText);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    // 設定糖果文字
    public void setCandyText(int count,String status){        
        if(status=="small"){
            CandyCount.setText(count+"/25");
        }else if(status=="mid"){
            CandyCount.setText(count+"/100");            
        }else if(status=="large"){
            CandyCount.setText("100/100");
        }        
    }
    
    // 顯示檔案來源
    public void setFilePathText(String Path){        
            FilePathText.setText("File Load From:"+Path);
    }
}
