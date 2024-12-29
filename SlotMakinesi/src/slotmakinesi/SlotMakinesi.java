/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package slotmakinesi;

import javax.swing.SwingUtilities;

/**
 *
 * @author gadoş
 */
public class SlotMakinesi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SlotMachinePanelWithImages frame = new SlotMachinePanelWithImages();
            frame.setVisible(true);
            
              SlotMachineController controller = frame.getController();
        new ArduinoController(controller); // Arduino denetleyicisi başlat
        });
        
    }
}
