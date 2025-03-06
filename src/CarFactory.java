import java.util.concurrent.CopyOnWriteArrayList;

public class CarFactory {
    CopyOnWriteArrayList<Lane> lanes;
    DataPanel dataPanel;
    TrafficLightControler trafficLightControler;
    public CarFactory(TrafficLightControler trafficLightControler, DataPanel dataPanel) {
        this.trafficLightControler = trafficLightControler;
        this.lanes = trafficLightControler.getLanes();
        this.dataPanel = dataPanel;
    }

    public void generateCar(){
        if(isCarAdded()){
            int laneIndex = laneToAddCar();
            int speed = 4;
            int x;
            int y;
            if(laneIndex == 4 || laneIndex == 5 || laneIndex == 6 || laneIndex == 7){
                y = lanes.get(laneIndex).getY();
                if(laneIndex == 4 || laneIndex == 5){
                    x = 0;
                }
                else{
                    x = 900;
                }
            }
            else{
                x = lanes.get(laneIndex).getX();
                if(laneIndex == 0 || laneIndex == 1){
                    y = 0;
                }
                else{
                    y = 900;
                }
            }

            int targetX = 0;
            int targetY = 0;
            boolean isTurning = decideCarMovment();

            if(isTurning){
                targetX = (int)lanes.get(laneIndex).getTurningTarget().getX();
                targetY = (int)lanes.get(laneIndex).getTurningTarget().getY();
            }
            else{
                targetX = (int)lanes.get(laneIndex).getStraightTarget().getX();
                targetY = (int)lanes.get(laneIndex).getStraightTarget().getY();
            }

            Car car = new Car(x, y, targetX, targetY, speed, lanes.get(laneIndex), dataPanel);
            lanes.get(laneIndex).addCar(car);
        }
    }
    public boolean isCarAdded(){
        double randomDouble = Math.random();
        // 60% chance to add a car
        return randomDouble > 0.4;
    }
    public int laneToAddCar(){
        return (int) (Math.random() * 8);
    }
    private boolean decideCarMovment(){
        double randomDouble = Math.random();
        // 50% chance for turning
        return randomDouble > 0.5;
    }
}
