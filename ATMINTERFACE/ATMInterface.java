import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ATMInterface extends JFrame {
    private JTextField pinField;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton transactionHistoryButton;
    private JButton quitButton;
    private JLabel balanceLabel;
    private JTextArea transactionHistoryArea;

    private double balance = 1000.0;
    private ArrayList<String> transactionHistory = new ArrayList<>();

    public ATMInterface() {
        setTitle("ATM Interface");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel pinLabel = new JLabel("Enter PIN:");
        pinField = new JTextField(10);
        topPanel.add(pinLabel);
        topPanel.add(pinField);

        JPanel buttonPanel = new JPanel();
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");
        transactionHistoryButton = new JButton("Transaction History");
        quitButton = new JButton("Quit");
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(transactionHistoryButton);
        buttonPanel.add(quitButton);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transfer();
            }
        });

        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTransactionHistory();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });

        JPanel balancePanel = new JPanel();
        balanceLabel = new JLabel("Balance: $1000.00");
        balancePanel.add(balanceLabel);

        JPanel transactionPanel = new JPanel();
        transactionPanel.setLayout(new BorderLayout());
        JLabel transactionLabel = new JLabel("Transaction History:");
        transactionHistoryArea = new JTextArea(10, 20);
        transactionHistoryArea.setEditable(false);
        transactionPanel.add(transactionLabel, BorderLayout.NORTH);
        transactionPanel.add(new JScrollPane(transactionHistoryArea), BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(balancePanel, BorderLayout.SOUTH);
        add(transactionPanel, BorderLayout.EAST);
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the deposit amount:"));
            if (amount > 0) {
                balance += amount;
                updateBalanceLabel();
                addTransactionToHistory("Deposit: $" + String.format("%.2f", amount));
                JOptionPane.showMessageDialog(this, "Deposit successful.");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid deposit amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid amount.");
        }
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the withdrawal amount:"));
            if (amount > 0) {
                if (amount <= balance) {
                    balance -= amount;
                    updateBalanceLabel();
                    addTransactionToHistory("Withdrawal: $" + String.format("%.2f", amount));
                    JOptionPane.showMessageDialog(this, "Withdrawal successful.");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid withdrawal amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid amount.");
        }
    }

    private void transfer() {
        try {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter the transfer amount:"));
            if (amount > 0) {
                if (amount <= balance) {
                    String recipient = JOptionPane.showInputDialog("Enter the recipient's account number:");
                    if (recipient != null && !recipient.isEmpty()) {
                        balance -= amount;
                        updateBalanceLabel();
                        addTransactionToHistory("Transfer: $" + String.format("%.2f", amount) + " to account " + recipient);
                        JOptionPane.showMessageDialog(this, "Transfer successful.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid recipient account number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid transfer amount.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid amount.");
        }
    }

    private void showTransactionHistory() {
        StringBuilder historyBuilder = new StringBuilder();
        for (String transaction : transactionHistory) {
            historyBuilder.append(transaction).append("\n");
        }
        JOptionPane.showMessageDialog(this, historyBuilder.toString(), "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    private void quit() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + String.format("%.2f", balance));
    }

    private void addTransactionToHistory(String transaction) {
        transactionHistory.add(transaction);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMInterface().setVisible(true);
            }
        });
    }
}
