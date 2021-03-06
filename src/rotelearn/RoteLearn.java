package rotelearn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class RoteLearn {
    private JFrame frame;
    private JTextArea question;
    private JTextArea answer;
    private DefaultListModel<Card> cardList;
    private boolean mode = false;
    private JButton modeButton;

    public static void main(String[] args){
        RoteLearn root = new RoteLearn();
        root.go();
    }

    public void go(){
        //go method
        frame = new JFrame("RoteLearn");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //建立卡片清單
        cardList = new DefaultListModel<Card>();
        JList<Card> cardListBox = new JList<Card>(cardList);
        cardListBox.setFixedCellWidth(100);
        cardListBox.setFont(new Font("Arial", Font.BOLD, 15));

        Font bigFont = new Font("LucidaSansDemibold", Font.BOLD, 24);
        question = new JTextArea(4, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        answer = new JTextArea(4, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(false);
        answer.setFont(bigFont);

        //下張圖片按鈕
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new NextCardListener());
        //更換模式按鈕
        modeButton = new JButton("Play");
        modeButton.addActionListener(new ModeListener());

        JLabel qLabel = new JLabel("Question:");
        qLabel.setFont(bigFont);
        JLabel aLabel = new JLabel("Answer:");
        aLabel.setFont(bigFont);

        //擺放UI
        mainPanel.add(qLabel);
        mainPanel.add(question);
        mainPanel.add(aLabel);
        mainPanel.add(answer);
        mainPanel.add(nextButton);
        mainPanel.add(modeButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");

        newItem.addActionListener(new NewMenuListener());
        saveItem.addActionListener(new SaveMenuListener());
        
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.getContentPane().add(BorderLayout.WEST, cardListBox);
        frame.setSize(400, 600);
        frame.setVisible(true);

    }

    private class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            Card card = new Card(question.getText(), answer.getText());
            cardList.add(0, card);
            clearCard();
        }
    }

    private class SaveMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            Card card = new Card(question.getText(), answer.getText());
            cardList.add(0, card);
            JFileChooser fileSaver = new JFileChooser();
            fileSaver.showSaveDialog(frame);
            saveFile(fileSaver.getSelectedFile());
        }
    }

    private class NewMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            cardList.clear();
            clearCard();
        }
    }

    private void saveFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(Card c : (Card[]) cardList.toArray()){
                writer.write(c.getQuestion() + "\t");
                writer.write(c.getAnswer() + "\n");
            }
            writer.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private void clearCard(){
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    private class ModeListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            mode = !mode;
            if(mode){
                modeButton.setText("Build");
            }else{
                modeButton.setText("Play");
            }
        }
    }
}
