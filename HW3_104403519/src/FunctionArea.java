/*** 
 * @Author �J�͵�
 * �Ǹ�: 104403519
 * �t��: ���3A
 * HW3: �_�i�ڤj�v
***/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class FunctionArea extends JPanel implements ActionListener{
    private JLabel givename; // ������r(���W����Enter)
	protected JTextField textField; // ���W��J��
    private JLabel CandyCount; //�}�G��
    private JLabel FilePathText; //���U������ɮרӷ�
    private JPanel CandyBtnList = new JPanel();
    private JPanel SaveBtnList = new JPanel();

    protected final JButton CandyBtn; // ���}�G
    protected final JButton OpenBtn; // �}������
    protected final JButton SaveBtn; // �s��
    protected final JButton SaveAsBtn; // �t�s�s��
        
    public FunctionArea(){
        super(new GridLayout(5,0));
        givename = new JLabel();
        givename.setText("���A���_�i�ڨ��ӦW�r�a! (��J������Enter)");
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
        SaveBtn.setEnabled(false); // �w�]New File���٨S���s�ɦ�m�A�ҥH�]��false������
        SaveBtnList.add(SaveBtn);
        SaveBtnList.add(SaveAsBtn);
        add(SaveBtnList);
        
        FilePathText = new JLabel("New File"); // �w�]�ɮרӷ���:New File
        add(FilePathText);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    // �]�w�}�G��r
    public void setCandyText(int count,String status){        
        if(status=="small"){
            CandyCount.setText(count+"/25");
        }else if(status=="mid"){
            CandyCount.setText(count+"/100");            
        }else if(status=="large"){
            CandyCount.setText("100/100");
        }        
    }
    
    // ����ɮרӷ�
    public void setFilePathText(String Path){        
            FilePathText.setText("File Load From:"+Path);
    }
}
