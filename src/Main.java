import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
// najwazniejsze jest zarządzanie wątkami
// jak ma czerowne nie ma przwa przkroczyćlini ( btw narysuj linie stopu na skrzyżowaniu)
// oraz to zeby były synchronizowane (pojazdy nie mogą na siebie najezdzaćna pasie) ( tam jak oczekująna zielone)
//moze być bezkolizyjne ( aka dwie linie obok siebie majązielone) lub kolizyjne aka (dwie linie mają zielone obok siebie i pasy na przeciwko majązielone)
//samochody muszą jechać np. nie mzoe być ze masz zielone i czekasz az zjedzie z tarczy xd
// ostateczny termin to 28stycznia ale jak chesz miećocenione zeby zaliczyć egzamin to 22 max;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();
        frame.setSize(1200, 900);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        DataPanel dataPanel = new DataPanel();
        TrafficLightControler trafficLightControler = new TrafficLightControler();
        CrossroadsSimulation crossroadsSimulation = new CrossroadsSimulation(trafficLightControler);
        crossroadsSimulation.setMinimumSize(new Dimension(900, 900));
        crossroadsSimulation.setMaximumSize(new Dimension(900, 900));
        crossroadsSimulation.setPreferredSize(new Dimension(900, 900));
        dataPanel.setPreferredSize(new Dimension(300, 900));
        frame.add(crossroadsSimulation, BorderLayout.WEST);
        frame.add(dataPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        CarFactory carFactory = new CarFactory(trafficLightControler, dataPanel);

        Thread trafficLightControllerThread = new Thread(trafficLightControler);
        trafficLightControllerThread.start();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        // Schedule the car generation task every 500ms
        scheduler.scheduleAtFixedRate(() -> {
            carFactory.generateCar();
            int activeThreadCount = Thread.activeCount();
            System.out.println("Number of active threads: " + activeThreadCount);
        }, 0, 500, TimeUnit.MILLISECONDS);

        // Schedule the repaint tasks for simulation and data panel
        scheduler.scheduleAtFixedRate(() -> {
            crossroadsSimulation.repaint();
            dataPanel.repaint();
        }, 0, 10, TimeUnit.MILLISECONDS);

        // Schedule the data panel update task every 60 seconds
        scheduler.scheduleAtFixedRate(dataPanel::updateCrossroadCapacity, 0, 60, TimeUnit.SECONDS);

    }
}