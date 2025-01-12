
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable{
    private final AtomicInteger x = new AtomicInteger();
    private final AtomicInteger y = new AtomicInteger();
    private int targetX, targetY; // Destination position
    private final int speed;        // Movement speed
    private boolean isFirst;
    private boolean isMoving;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private int time = 0;
    DataPanel dataPanel;
    final Lane lane;
    private String cotinueInDirection;
    public Car(int x, int y, int targetX, int targetY, int speed, Lane lane, DataPanel dataPanel){
        this.lane = lane;
        this.x.set(x);
        this.y.set(y);
        this.targetX = targetX;
        this.targetY = targetY;
        this.speed = speed;
        this.dataPanel = dataPanel;
        isMoving = false;
        if (targetY == 275) {
            cotinueInDirection = "up";
        } else if (targetY == 600) {
            cotinueInDirection = "down";
        } else if (targetX == 275) {
            cotinueInDirection = "left";
        } else if (targetX == 600) {
            cotinueInDirection = "right";
        }
    }
    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(this::updateTime, 0, 10, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(this::move, 0, 25, TimeUnit.MILLISECONDS);
    }
    private void updateTime() {
        time++;
    }

    public synchronized void move() {
        if(!isFirst) {
            if (isAtTarget(lane.getX(), lane.getY())) {
                isFirst = true;
            } else {
                if(canMove()) {
                    moveTowardsTarget(lane.getX(), lane.getY());
                }
            }
        }
        else {
            if(!isMoving){
                carStatus();
            }
            if(isMoving) {
                if (isAtTarget(targetX, targetY)) {
                    if(targetX == 0 || targetY == 0 || targetX == 900 || targetY == 900){
                        lane.deleteCarFromLane(getCarIndex());
                    }
                    updateTarget();
                } else {
                    moveTowardsTarget(targetX, targetY);
                }
            }
        }
    }
    private void carStatus(){
        if(lane.getGreenLight() && isFirst){
            isMoving = true;
        }
    }

    private synchronized boolean isAtTarget(int targetX, int targetY) {
        return Math.abs(x.get() - targetX) < speed && Math.abs(y.get() - targetY) < speed;
    }

    private synchronized void moveTowardsTarget(int targetX, int targetY) {
        if (Math.abs(targetX - x.get()) < speed) {
            x.set(targetX);
        } else {
            x.addAndGet((int) (Math.signum(targetX - x.get()) * speed));
        }

        if (Math.abs(targetY - y.get()) < speed) {
            y.set(targetY);
        } else {
            y.addAndGet((int) (Math.signum(targetY - y.get()) * speed));
        }
    }

    private boolean canMove() {
        CopyOnWriteArrayList<Car> cars = lane.getCars();
        int currentIndex = cars.indexOf(this);

        if (currentIndex > 0) {
            Car carAhead = cars.get(currentIndex - 1);
            synchronized (carAhead) {
                int carAheadX = carAhead.getX();
                int carAheadY = carAhead.getY();
                int distanceToCarAhead = Math.abs(carAheadX - x.get()) + Math.abs(carAheadY - y.get());
                return distanceToCarAhead - 30 > speed; // Allow movement if there’s enough gap
            }
        }
        return true; // No car ahead, can move
    }

    private void updateTarget() {
        if (targetX != 0 && targetX != 900 && targetY != 0 && targetY != 900) {
            dataPanel.updateData(time);
            switch (cotinueInDirection) {
                case "left":
                    targetX = 0;
                    break;
                case "right":
                    targetX = 900;
                    break;
                case "up":
                    targetY = 0;
                    break;
                case "down":
                    targetY = 900;
                    break;
            }
        }
    }

    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }
    public synchronized void stopCar() {
        scheduler.shutdownNow(); // Gracefully shut down the scheduler
    }

    private int getCarIndex() {
        synchronized (lane) {
            return lane.getCars().indexOf(this);
        }
    }
}

