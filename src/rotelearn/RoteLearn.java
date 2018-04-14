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
    private ArrayList<Card> cards;
    DefaultListModel<Card> cardList;

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

        Font bigFont = new Font("sansserif", Font.BOLD, 24);
        question = new JTextArea(4, 10);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        answer = new JTextArea(4, 10);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(false);
        answer.setFont(bigFont);

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new NextCardListener());

        cards = new ArrayList<Card>();

        JLabel qLabel = new JLabel("Question:");
        qLabel.setFont(bigFont);
        JLabel aLabel = new JLabel("Answer:");
        aLabel.setFont(bigFont);

        mainPanel.add(qLabel);
        mainPanel.add(question);
        mainPanel.add(aLabel);
        mainPanel.add(answer);
        mainPanel.add(nextButton);

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
            cards.add(card);
            cardList.add(0, card);
            clearCard();
        }
    }

    private class SaveMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            Card card = new Card(question.getText(), answer.getText());
            cards.add(card);
            JFileChooser fileSaver = new JFileChooser();
            fileSaver.showSaveDialog(frame);
            saveFile(fileSaver.getSelectedFile());
        }
    }

    private class NewMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            cards.clear();
            clearCard();
        }
    }

    private void saveFile(File file){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for(Card c : cards){
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
}
