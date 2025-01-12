import java.awt.*;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
public class Lane {
    private final CopyOnWriteArrayList<Car> cars = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Thread> carThreads = new CopyOnWriteArrayList<>();
    private final AtomicBoolean greenLight = new AtomicBoolean(false);
    private final AtomicBoolean isOccupied = new AtomicBoolean(false);
    private final int x;
    private final int y;
    private final Point straightTarget;
    private final Point turningTarget;

    public Lane(int x, int y, Point straightTarget, Point turningTarget) {
        this.x = x;
        this.y = y;
        this.straightTarget = straightTarget;
        this.turningTarget = turningTarget;

    }
    public synchronized void deleteCarFromLane(int index) {
        if (index >= 0 && index < cars.size()) {

            Car car = cars.get(index);
            Thread thread = carThreads.get(index);

            cars.remove(index);
            carThreads.remove(index);

            car.stopCar(); // Signal the car to stop
            thread.interrupt(); // Interrupt the thread for safety
        }
    }
    public void addCar(Car car){
        cars.add(car); // Add the car to the list
        Thread carThread = new Thread(car); // Create a new thread for the car
        carThreads.add(carThread); // Add the thread to the thread list
        carThread.start(); // Start the car thread
    }

    public CopyOnWriteArrayList<Car> getCars() {
        // Return the list as-is since CopyOnWriteArrayList is thread-safe
        return cars;
    }

    public void setGreenLight(boolean greenLight) {
        this.greenLight.set(greenLight);
    }

    public boolean getGreenLight() {
        return greenLight.get();
    }

    public boolean isOccupied() {
        sensor();
        return isOccupied.get();
    }

    //Detects number of cars waithing for green light
    public int sensor() {
        int carIndex = 0;

        for (Car car : cars) {
            if (Math.abs(car.getX() - this.x) + Math.abs(car.getY() - this.y) < 20) {
                isOccupied.set(true);
                return (cars.size() - carIndex);
            }
            carIndex++;
        }

        isOccupied.set(false);
        return 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public Point getStraightTarget() {
        return straightTarget;
    }

    public Point getTurningTarget() {
        return turningTarget;
    }
}
