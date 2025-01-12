import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataPanel extends JPanel {
    AtomicInteger totalTime = new AtomicInteger();// 0.1 second intervals
    AtomicInteger totalNumberOfCars =  new AtomicInteger();
    AtomicInteger numberOfCarsInUnitOfTime = new AtomicInteger();

    JLabel avrageTimeLabel;
    JLabel crossroadFlowThrough;



    public DataPanel() {
        setLayout(new FlowLayout());
        avrageTimeLabel = new JLabel("Avrage wait time: 0");
        crossroadFlowThrough = new JLabel("Crossroad capacity in 1 minute: 0");
        avrageTimeLabel.setForeground(Color.white);
        crossroadFlowThrough.setForeground(Color.white);
        setBackground(new Color(38, 42, 56));
        add(avrageTimeLabel);
        add(crossroadFlowThrough);
    }

    public void updateData(int time){

        totalTime.addAndGet(time);
        numberOfCarsInUnitOfTime.incrementAndGet();
        totalNumberOfCars.incrementAndGet();
        updateAvrageTime();
    }
    public void updateAvrageTime(){
        if(totalNumberOfCars.get() != 0){
            avrageTimeLabel.setText(String.format("Avrage wait time: %.2f s", (float)(totalTime.get()/totalNumberOfCars.get())/100));
        }
    }
    // gets Called each minute from main
    public void updateCrossroadCapacity(){
        crossroadFlowThrough.setText(String.format("Crossroad capacity in 1 minute: %d", numberOfCarsInUnitOfTime.get()));
        numberOfCarsInUnitOfTime.getAndSet(0);
    }
}
