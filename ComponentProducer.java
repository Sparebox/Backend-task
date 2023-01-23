import java.util.ArrayList;
import java.util.List;

public class ComponentProducer {
    private Component component;
    private int unitsPerHour;
    private int packageSize;
    private int hoursToDeliver;
    private int units;
    private List<Delivery> deliveries;
    private ToyFactory deliveryDestination;

    public ComponentProducer(
        Component component,
        int unitsPerHour, 
        int packageSize, 
        int hoursToDeliver, 
        ToyFactory deliveryDestination
    ) {
        this.component = component;
        this.unitsPerHour = unitsPerHour;
        this.packageSize = packageSize;
        this.hoursToDeliver = hoursToDeliver;
        this.deliveries = new ArrayList<>();
        this.deliveryDestination = deliveryDestination;
    }

    public void tick() {
        // Tick deliveries in delivery list
        var iter = this.deliveries.listIterator();
        while(iter.hasNext()) {
            var delivery = iter.next();
            if(delivery.getHoursTillArrival() == 0) {
                finishDelivery(delivery);
                iter.remove();
            } else {
                delivery.setHoursTillArrival(delivery.getHoursTillArrival() - 1);
            }
        }

        // Produce more components
        this.units += this.unitsPerHour;

        // Check if there are enough components for packaging
        if(this.units >= this.packageSize) {
            this.units -= this.packageSize;
            this.deliveries.add(new Delivery(this.packageSize, this.hoursToDeliver));
        }
    }

    private void finishDelivery(Delivery delivery) {
        switch(this.component) {
            case FUR:
                int numOfFurs = this.deliveryDestination.getNumOfFurs();
                this.deliveryDestination.setNumOfFurs(numOfFurs + delivery.getUnits());
                break;
            case FILLING:
                int numOfToppings = this.deliveryDestination.getNumOfFillings();
                this.deliveryDestination.setNumOfFillings(numOfToppings + delivery.getUnits());
                break;
            case NOSE:
                int numOfNoses = this.deliveryDestination.getNumOfNoses();
                this.deliveryDestination.setNumOfNoses(numOfNoses + delivery.getUnits());
                break;
            case EYE:
                int numOfEyes = this.deliveryDestination.getNumOfEyes();
                this.deliveryDestination.setNumOfEyes(numOfEyes + delivery.getUnits());
                break;
        }
    }

    // Getters

    public int getUnitsPerHour() {
        return this.unitsPerHour;
    }

    public int getPackageSize() {
        return this.packageSize;
    }

    public int getHoursToDeliver() {
        return this.hoursToDeliver;
    }
}

class Delivery {
    private int units;
    private int hoursTillArrival;

    public Delivery(int units, int hoursTillArrival) {
        this.units = units;
        this.hoursTillArrival = hoursTillArrival;
    }

    public int getUnits() {
        return units;
    }

    public int getHoursTillArrival() {
        return hoursTillArrival;
    }

    public void setHoursTillArrival(int hours) {
        this.hoursTillArrival = hours;
    }
}
