package slotmakinesi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SlotMachineModel {
    private ImageIcon explosionImage;

    private static final String[] SYMBOLS = {"Ampul", "Pil", "Direk", "Trafo", "Şimşek", "Tesla"};
    private static final int[][] PAYOUT_TABLE = {
        {  0,0, 5, 20, 40, 50, 60, 110},
        {  0,0, 12, 30, 45, 55, 65, 120},
        {  0,0, 15, 35, 50, 60, 100, 150},
        {  0,0, 20, 60, 80, 100, 150, 250},
        {  0,0, 30, 70, 120, 160, 200, 500},
        {  0,50, 50, 100, 300, 500, 2000, 5000}
    };
    private static final int[] SYMBOL_WEIGHTS = {50, 40, 30, 20, 10, 5}; // Ağırlıklar

    private JLabel[][] reels;
    private Random random;
    private int bet;
    private int balance;
    private ImageIcon[] images;

    public SlotMachineModel() {
        reels = new JLabel[3][5];
        random = new Random();
        bet = 100;
        balance = 10000;
        images = new ImageIcon[SYMBOLS.length];
        loadImages();
    }

    private void loadImages() {
        for (int i = 0; i < SYMBOLS.length; i++) {
            String imagePath = "/images/" + SYMBOLS[i] + ".jpg";
            java.net.URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                images[i] = new ImageIcon(scaledImage);
            }
        }

        // Patlama görselini yükle
        String explosionPath = "/images/expolosion.jpg";
        java.net.URL explosionUrl = getClass().getResource(explosionPath);
        if (explosionUrl != null) {
            ImageIcon originalIcon = new ImageIcon(explosionUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            explosionImage = new ImageIcon(scaledImage);
        }
    }

    public ImageIcon getExplosionImage() {
        return explosionImage;
    }

    public JLabel[][] getReels() {
        return reels;
    }

    public ImageIcon[] getImages() {
        return images;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int calculateWinnings() {
    int totalWinnings = 0;
    int[] symbolFrequency = new int[SYMBOLS.length];

    // Reel'deki sembollerin sıklığını hesapla
    for (JLabel[] reelRow : reels) {
        for (JLabel reel : reelRow) {
            int symbolIndex = getSymbolIndex(reel.getIcon());
            if (symbolIndex != -1) {
                symbolFrequency[symbolIndex]++;
            }
        }
    }

    // Ödeme tablosuna göre kazancı hesapla
    for (int i = 0; i < SYMBOLS.length; i++) {
        int count = symbolFrequency[i];
        if (count >= 5) { // Ödeme yapan semboller için
            int baseWinnings = PAYOUT_TABLE[i][Math.min(count, PAYOUT_TABLE[i].length - 1)];
            totalWinnings += (baseWinnings * bet)/100; // Kazancı bahis ile çarp
        }
    }

    return totalWinnings;
}

    public List<Point> getWinningSymbolCoordinates() {
        List<Point> winningSymbols = new ArrayList<>();
        int[] symbolFrequency = new int[SYMBOLS.length];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                int symbolIndex = getSymbolIndex(reels[row][col].getIcon());
                if (symbolIndex != -1) {
                    symbolFrequency[symbolIndex]++;
                }
            }
        }

        for (int i = 0; i < SYMBOLS.length; i++) {
            if (symbolFrequency[i] >= 5) { // Ödeme yapan semboller
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 5; col++) {
                        int symbolIndex = getSymbolIndex(reels[row][col].getIcon());
                        if (symbolIndex == i) {
                            winningSymbols.add(new Point(row, col));
                        }
                    }
                }
            }
        }

        return winningSymbols;
    }

    public int getSymbolIndex(Icon icon) {
        for (int i = 0; i < images.length; i++) {
            if (images[i] == icon) {
                return i;
            }
        }
        return -1;
    }

    private int getRandomSymbolIndex() {
        int totalWeight = 0;
        for (int weight : SYMBOL_WEIGHTS) {
            totalWeight += weight;
        }

        int randomValue = random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (int i = 0; i < SYMBOL_WEIGHTS.length; i++) {
            cumulativeWeight += SYMBOL_WEIGHTS[i];
            if (randomValue < cumulativeWeight) {
                return i;
            }
        }
        return SYMBOL_WEIGHTS.length - 1; // Güvenlik için
    }

    public void spinReels() {
        Random random = new Random();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 5; col++) {
                int symbolIndex = getRandomSymbolIndex(); // Ağırlık bazlı sembol seçimi
                reels[row][col].setIcon(images[symbolIndex]);
            }
        }
    }
}
