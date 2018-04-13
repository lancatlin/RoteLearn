package rotelearn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Player {
    private ArrayList<Card> cards;
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea question;
    private JTextArea answer;
    private JButton nextButton;
    private boolean isShowAnswer;
    private Card swapCard;

    public static void main(String[] args){
        Player root = new Player();
        root.go();
    }

    public void go(){
        frame = new JFrame("Rote Learn Player");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        JLabel qLabel = new JLabel("Question:");
        JLabel aLabel = new JLabel("Answer:");

        mainPanel.add(qLabel);
        mainPanel.add(question);
        mainPanel.add(aLabel);
        mainPanel.add(answer);
        mainPanel.add(nextButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new OpenListener());
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 600);
        frame.setVisible(true);
    }

    private class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            if(isShowAnswer){
                answer.setText(swapCard.getAnswer());
            }else{
                Random random = new Random();
                swapCard = cards.get(random.nextInt(cards.size()));
                question.setText(swapCard.getQuestion());
            }
            isShowAnswer = !isShowAnswer;
        }
    }

    private class OpenListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            //pass
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            loadFile(fileOpen.getSelectedFile());
        }
    }

    private void loadFile(File file){
        cards = new ArrayList<Card>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null){
                makeCard(line);
            }
            reader.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void makeCard(String line){
        String[] s = line.split("\t");
        Card card = new Card(s[0], s[1]);
        cards.add(card);
        System.out.println("add one card");
    }
}
