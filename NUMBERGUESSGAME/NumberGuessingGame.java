import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGuessingGame extends JFrame {
    private int targetNumber;
    private int attempts;

    private JTextField textField;
    private JButton guessButton;
    private JLabel resultLabel;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Generate a random target number between 1 and 100
        targetNumber = (int) (Math.random() * 100) + 1;
        attempts = 0;

        JLabel instructionLabel = new JLabel("Guess a number between 1 and 100:");
        textField = new JTextField(10);
        guessButton = new JButton("Guess");
        resultLabel = new JLabel("");

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateGuess();
            }
        });

        add(instructionLabel);
        add(textField);
        add(guessButton);
        add(resultLabel);
    }

    private void validateGuess() {
        try {
            int guess = Integer.parseInt(textField.getText());
            attempts++;

            if (guess < targetNumber) {
                resultLabel.setText("Too low! Guess higher.");
            } else if (guess > targetNumber) {
                resultLabel.setText("Too high! Guess lower.");
            } else {
                resultLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                guessButton.setEnabled(false);
            }

            textField.setText("");
            textField.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}
