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
import java.awt.Point;    // Point sınıfı için
import java.util.ArrayList; // ArrayList sınıfı için
import java.util.List;
public class SlotMachineController {
     private final SlotMachineModel model;
    private final SlotMachineView view;

    public SlotMachineController(SlotMachineModel model, SlotMachineView view) {
        this.model = model;
        this.view = view;
    }

    public void setupListeners() {
        view.getSpinButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinReels();
            }
        });

        view.getIncreaseBetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setBet(model.getBet() + 10);
                view.getBetLabel().setText("Bet: " + model.getBet());
            }
        });

        view.getDecreaseBetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getBet() > 10) {
                    model.setBet(model.getBet() - 10);
                    view.getBetLabel().setText("Bet: " + model.getBet());
                }
            }
        });
    }

   public void spinReels() {
        if (model.getBalance() < model.getBet()) {
            view.getResultLabel().setText("Insufficient balance!");
            return;
        }

        model.setBalance(model.getBalance() - model.getBet());
        view.getBalanceLabel().setText("Balance: " + model.getBalance());

        Timer timer = new Timer(50, null);
        final int[] steps = {0};
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 5; col++) {
                        int symbolIndex = random.nextInt(model.getImages().length);
                        model.getReels()[row][col].setIcon(model.getImages()[symbolIndex]);
                    }
                }
                steps[0]++;
                if (steps[0] > 20) {
                    ((Timer) e.getSource()).stop();
                    finalizeSpin();
                }
            }
        });
        timer.start();
    }

    private void finalizeSpin() {
    Random random = new Random();
    for (int row = 0; row < 3; row++) {
        for (int col = 0; col < 5; col++) {
            int symbolIndex = random.nextInt(model.getImages().length);
            model.getReels()[row][col].setIcon(model.getImages()[symbolIndex]);
        }
    }

    int winnings = model.calculateWinnings();
    model.setBalance(model.getBalance() + winnings);
    view.getBalanceLabel().setText("Balance: " + model.getBalance());

    if (winnings > 0) {
        view.getResultLabel().setText("Tebrikler,Kazandınız: " + winnings);

        // Ödeme yapan sembolleri bul
        List<Point> winningSymbols = model.getWinningSymbolCoordinates();

        // Patlama efektini uygula
        triggerExplosionEffect(winningSymbols);
    } else {
        view.getResultLabel().setText("Kazanamadınız,Bir daha deneyin");
    }
}


private void triggerExplosionEffect(List<Point> winningSymbols) {
    for (Point point : winningSymbols) {
        int row = point.x;
        int col = point.y;

        // Patlama efektini uygula
        JLabel reel = model.getReels()[row][col];
        Icon originalIcon = reel.getIcon();
        reel.setIcon(model.getExplosionImage());

        // Patlama efektini kaldır ve eski sembole dön
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reel.setIcon(originalIcon);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}



public void increaseBet() {
    model.setBet(model.getBet() + 10);
    view.getBetLabel().setText("Bet: " + model.getBet());
}

public void decreaseBet() {
    if (model.getBet() > 10) {
        model.setBet(model.getBet() - 10);
        view.getBetLabel().setText("Bet: " + model.getBet());
    }
}    
}