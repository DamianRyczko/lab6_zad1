import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class CrossroadsSimulation extends JPanel {
    CopyOnWriteArrayList<Lane> lanes;
    TrafficLightControler trafficLightControler;

    public CrossroadsSimulation(TrafficLightControler trafficLightControler) {
        this.trafficLightControler = trafficLightControler;
        lanes = trafficLightControler.getLanes();
        setBackground(Color.gray);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.setBackground(Color.GRAY);
        g2d.setColor(new Color(13, 74, 20));

        g2d.fillRect(0, 0, 300, 300);
        g2d.fillRect(600, 0, 300, 300);
        g2d.fillRect(0, 600, 300, 300);
        g2d.fillRect(600, 600, 300, 300);

        g2d.fillRect(420, 0, 60, 300);
        g2d.fillRect(420, 600, 60, 300);
        g2d.fillRect(0, 420, 300, 60);
        g2d.fillRect(600, 420, 300, 60);

        g2d.setColor(Color.WHITE);

        g2d.drawLine(360,0,360,300);
        g2d.drawLine(540,0,540,300);

        g2d.drawLine(0,360,300,360);
        g2d.drawLine(0,540,300,540);

        g2d.drawLine(360,600,360,900);
        g2d.drawLine(540,600,540,900);

        g2d.drawLine(600,360,900,360);
        g2d.drawLine(600,540,900,540);



        // top
        if(lanes.get(0) != null && lanes.get(1) != null) {
            int x = 265;
            int y = 240;
            int radius = 28;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, radius+2, 60);
            g2d.fillRect(x+160, y, radius+2, 60);

            if(lanes.get(0).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x+30, y+40, 10, 20);

            if(lanes.get(1).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }

            g2d.fillRect(x+150, y+40, 10, 20);

            if(lanes.get(0).getGreenLight()){
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x, y, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x, y+30, radius, radius);
            }

            if(lanes.get(1).getGreenLight()){
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x+160, y, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x+160, y+30, radius, radius);

            }

        }

        // bottom
        if(lanes.get(2) != null && lanes.get(3) != null) {
            int x = 445;
            int y = 600;
            int radius = 28;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, radius+2, 60);
            g2d.fillRect(x+160, y, radius+2, 60);

            if(lanes.get(2).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x+30, y, 10, 20);
            if(lanes.get(3).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x+150, y, 10, 20);

            if(lanes.get(2).getGreenLight()) {
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x, y+30, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x, y, radius, radius);

            }

            if(lanes.get(3).getGreenLight()) {
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x+160, y+30, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x+160, y, radius, radius);
            }

        }

        //left
        if(lanes.get(4) != null && lanes.get(5) != null) {
            int x = 240;
            int y = 445;
            int radius = 28;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, 60, radius+2);
            g2d.fillRect(x, y+160, 60, radius+2);

            if(lanes.get(4).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x+40, y+30, 20, 10);
            if(lanes.get(5).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x+40, y+150, 20, 10);

            if(lanes.get(4).getGreenLight()) {
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x, y, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x+30, y, radius, radius);

            }

            if(lanes.get(5).getGreenLight()) {
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x, y+160, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x+30, y+160, radius, radius);
            }



        }

        // right
        if(lanes.get(6) != null && lanes.get(7) != null) {
            int x = 600;
            int y = 265;
            int radius = 28;
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, 60, radius+2);
            g2d.fillRect(x, y+160, 60, radius+2);

            g2d.setColor(Color.BLUE);
            if(lanes.get(6).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x, y+30, 20, 10);
            if(lanes.get(7).isOccupied()){
                g2d.setColor(Color.ORANGE);
            }
            else{
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(x, y+150, 20, 10);

            if(lanes.get(6).getGreenLight()) {
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x+30, y, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x, y, radius, radius);
            }

            if(lanes.get(7).getGreenLight()) {
                g2d.setColor(Color.GREEN);
                g2d.fillOval(x+30, y+160, radius, radius);
            }
            else{
                g2d.setColor(Color.RED);
                g2d.fillOval(x, y+160, radius, radius);
            }


            // draw cars

            for (Lane lane : lanes) {
                for (Car car : lane.getCars()) {
                    g2d.setColor(Color.CYAN);
                    g2d.fillOval(car.getX(), car.getY(), 20, 20);
                }
            }

        }
    }
}
