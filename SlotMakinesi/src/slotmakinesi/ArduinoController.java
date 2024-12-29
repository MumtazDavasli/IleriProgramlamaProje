/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slotmakinesi;
import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
/**
 *
 * @author gadoş
 */
import com.fazecast.jSerialComm.SerialPort;

public class ArduinoController {
    private final SlotMachineController slotController;

    public ArduinoController(SlotMachineController slotController) {
        this.slotController = slotController;
        setupSerialCommunication();
    }

    private void setupSerialCommunication() {
        SerialPort serialPort = SerialPort.getCommPort("COM11");
        // İlk bağlı portu seç
        serialPort.setBaudRate(9600);

        
        if (!serialPort.openPort()) {
            System.out.println("Seri port açılamadı!");
            return;
        }
         System.out.println("Seri port başarıyla açıldı!");

        Thread thread = new Thread(() -> {
            byte[] buffer = new byte[1]; // Tek bayt okuyacağız (joystick durumu)

            while (serialPort.isOpen()) {
                if (serialPort.readBytes(buffer, buffer.length) > 0) {
                    char input = (char) buffer[0];
                    handleJoystickInput(input);
                }
            }
        });

        thread.setDaemon(true); // Program kapandığında bu thread otomatik kapanacak
        thread.start();
    }

    private void handleJoystickInput(char input) {
        switch (input) {
            case 'R': // Sağ
                System.out.println("Joystick sağa itildi: Bahis artırılıyor.");
                slotController.increaseBet(); // Bet artır
                break;
            case 'L': // Sol
                System.out.println("Joystick sola itildi: Bahis azaltılıyor.");
                slotController.decreaseBet(); // Bet azalt
                break;
            case 'P': // Orta (bastırma)
                System.out.println("Joystick basıldı: Slot çevriliyor.");
                slotController.spinReels(); // Slot'u döndür
                break;
            default:
                System.out.println("Bilinmeyen giriş: " + input);
        }
    }
}



