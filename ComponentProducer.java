import java.util.ArrayList;
import java.util.List;

public class ComponentProducer {
    private Component component;
    private int unitsPerHour;
    private int packageSize;
    private int hoursToDeliver;
    private int producedUnits;
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

    /**
     * Advances this component producer one step (hour) forward in the simulation.
     * Goes through the delivery list and checks if any deliveries have arrived at their destination.
     * If a delivery has arrived it is removed from the list and its contents are added to the 
     * appropriate component count of the destination factory.
     * <p>
     * Produces as many units as is defined in the {@code unitsPerHour} class variable.
     * If {@code packageSize} units have accumulated then they are packaged and added to the delivery list.
     */
    public void tick() {
        // Update deliveries in delivery list
        var iter = this.deliveries.listIterator();
        while(iter.hasNext()) {
            var delivery = iter.next();
            if(delivery.getHoursTillArrival() == 0) {
                // Remove delivery from the list as it has been delivered
                delivery.finishDelivery(this.deliveryDestination);
                iter.remove();
            } else {
                // This delivery is now one hour closer to arrival
                delivery.setHoursTillArrival(delivery.getHoursTillArrival() - 1);
            }
        }

        // Produce components
        this.producedUnits += this.unitsPerHour;

        // Check if there are enough components for packaging and delivering
        if(this.producedUnits >= this.packageSize) {
            this.producedUnits -= this.packageSize;
            this.deliveries.add(new Delivery(this.component, this.packageSize, this.hoursToDeliver));
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

/**
 * This class represents one delivery unit containing the type of component, quantity and time remaining until arrival.
 */
class Delivery {
    private Component component;
    private int units;
    private int hoursTillArrival;

    public Delivery(Component component, int units, int hoursTillArrival) {
        this.component = component;
        this.units = units;
        this.hoursTillArrival = hoursTillArrival;
    }

     /**
     * Sends the contents of this delivery to the destination factory.
     * @param destination The destination factory to be delivered to
     */
    public void finishDelivery(ToyFactory destination) {
        destination.getStoredComponents().compute(this.component, (k, v) -> v + this.units);
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
