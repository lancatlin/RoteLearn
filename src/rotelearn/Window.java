package rotelearn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Window {
    private JFrame frame;
    private JList<Card> cardList;
    private JTextArea question;
    private JTextArea answer;
    private JButton nextButton;
    private JButton saveButton;
    private JButton modeButton;
    private JLabel questionLabel;
    private JLabel answerLabel;
    private JLabel printLabel;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JPanel listPanel;
    private boolean mode;
    private DefaultListModel<Card> cards;

    public static void main(String[] args) {
        Window root = new Window();
        root.go();
    }

    public void go(){
        JFrame frame = new JFrame("RoteLearn");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        //設定
        nextButton.addActionListener(new NextCardListener());
        modeButton.addActionListener(new ModeListener());

        cards = new DefaultListModel<>();
        cardList.setModel(cards);

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

        frame.setSize(600, 800);
        frame.setVisible(true);
    }

    private class NextCardListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Card card = new Card(question.getText(), answer.getText());
            cards.add(0, card);
            clearCard();
        }
    }

    private class SaveMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            Card card = new Card(question.getText(), answer.getText());
            cards.add(0, card);
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

            for(Card c : (Card[]) cards.toArray()){
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
