import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrafficLightControler implements Runnable {
    private final CopyOnWriteArrayList<Lane> lanes = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    int lasTLaneWithGreen = -1; // - 1 signals no lane had green light

    public TrafficLightControler() {
        lanes.add(new Lane(330, 280, new Point(330, 600), new Point(275, 330)));//lane0 / straoght / truning
        lanes.add(new Lane(390, 280, new Point(390, 600), new Point(600, 510)));//lane1

        lanes.add(new Lane(510, 600, new Point(510, 275), new Point(275, 390)));//lane2
        lanes.add(new Lane(570, 600, new Point(570, 275), new Point(600, 570)));//lane3

        lanes.add(new Lane(280, 510, new Point(600, 510), new Point(510, 275)));//lane4
        lanes.add(new Lane(280, 570, new Point(600, 570), new Point(330, 600)));//lane5

        lanes.add(new Lane(600, 330, new Point(275, 330), new Point(570, 275)));//lane6
        lanes.add(new Lane(600, 390, new Point(275, 390), new Point(390, 600)));//lane7
    }

    public void ChangeLights(){
        if(getLasTLaneWithGreen() != -1){
            lanes.get(getLasTLaneWithGreen()).setGreenLight(false);
            lanes.get(getLasTLaneWithGreen()+1).setGreenLight(false);
        }
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        int topLaneQueueSize = lanes.get(0).sensor() + lanes.get(1).sensor();
        int bottomLaneQueueSize = lanes.get(2).sensor() + lanes.get(3).sensor();
        int leftLaneQueueSize = lanes.get(4).sensor() + lanes.get(5).sensor();
        int rightLaneQueueSize = lanes.get(6).sensor() + lanes.get(7).sensor();
        switch (getLasTLaneWithGreen()){
            case 0:
                topLaneQueueSize = 0;
                break;
            case 2:
                bottomLaneQueueSize = 0;
                break;
            case 4:
                leftLaneQueueSize = 0;
                break;
            case 6:
                rightLaneQueueSize = 0;
                break;
        }
        int biggestDemand = Math.max(topLaneQueueSize,Math.max(bottomLaneQueueSize,Math.max(leftLaneQueueSize, rightLaneQueueSize)));
        if(lanes.size() >= 8 && biggestDemand > 0) {
            if (topLaneQueueSize == biggestDemand ) {
                lanes.get(0).setGreenLight(true);
                lanes.get(1).setGreenLight(true);
                setLasTLaneWithGreen(0);
            } else if (bottomLaneQueueSize == biggestDemand) {
                lanes.get(2).setGreenLight(true);
                lanes.get(3).setGreenLight(true);
                setLasTLaneWithGreen(2);
            } else if (leftLaneQueueSize == biggestDemand) {
                lanes.get(4).setGreenLight(true);
                lanes.get(5).setGreenLight(true);
                setLasTLaneWithGreen(4);
            } else {
                lanes.get(6).setGreenLight(true);
                lanes.get(7).setGreenLight(true);
                setLasTLaneWithGreen(6);
            }
        }
    }
    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(this::ChangeLights, 0, 5, TimeUnit.SECONDS);
    }
    public CopyOnWriteArrayList<Lane> getLanes() {
        return lanes;
    }

    public synchronized int getLasTLaneWithGreen() {
        return lasTLaneWithGreen;
    }

    public synchronized void setLasTLaneWithGreen(int lasTLaneWithGreen) {
        this.lasTLaneWithGreen = lasTLaneWithGreen;
    }
}
