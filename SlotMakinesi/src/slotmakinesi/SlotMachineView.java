/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slotmakinesi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SlotMachineView {
    private JPanel reelPanel;
    private JPanel controlPanel;
    private JLabel resultLabel;
    private JLabel betLabel;
    private JLabel balanceLabel;
    private JButton spinButton;
    private JButton increaseBetButton;
    private JButton decreaseBetButton;

    public SlotMachineView(SlotMachineModel model) {
        reelPanel = new JPanel(new GridLayout(3, 5));
        controlPanel = new JPanel();
        resultLabel = new JLabel("");
        betLabel = new JLabel("Bet: " + model.getBet());
        balanceLabel = new JLabel("Balance: " + model.getBalance());
        spinButton = new JButton("Spin");
        increaseBetButton = new JButton("+ Bet");
        decreaseBetButton = new JButton("- Bet");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                JLabel reel = new JLabel();
                reel.setHorizontalAlignment(SwingConstants.CENTER);
                reel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                model.getReels()[row][col] = reel;
                reelPanel.add(reel);
            }
        }

        controlPanel.add(spinButton);
        controlPanel.add(increaseBetButton);
        controlPanel.add(decreaseBetButton);
        controlPanel.add(betLabel);
        controlPanel.add(balanceLabel);
    }

    public JPanel getReelPanel() {
        return reelPanel;
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    public JLabel getBetLabel() {
        return betLabel;
    }

    public JLabel getBalanceLabel() {
        return balanceLabel;
    }

    public JButton getSpinButton() {
        return spinButton;
    }

    public JButton getIncreaseBetButton() {
        return increaseBetButton;
    }

    public JButton getDecreaseBetButton() {
        return decreaseBetButton;
    }
}

    

