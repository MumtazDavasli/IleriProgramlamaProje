package slotmakinesi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlotMachinePanelWithImages extends JFrame {
    private static final int ROWS = 3;
    private static final int COLS = 5;
 private SlotMachineController controller;
    public SlotMachinePanelWithImages() {
        SlotMachineModel model=new SlotMachineModel();
        SlotMachineView view = new SlotMachineView(model);
         controller = new SlotMachineController(model, view);

        setTitle("Slot Machine with Images");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(view.getReelPanel(), BorderLayout.CENTER);
        add(view.getControlPanel(), BorderLayout.SOUTH);
        add(view.getResultLabel(), BorderLayout.NORTH);

        controller.setupListeners();
        
        }

public SlotMachineController getController() {
        return controller; // Controller'ı dışarıya sun
    }
}