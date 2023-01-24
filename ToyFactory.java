import java.util.HashMap;
import java.util.Map;

public class ToyFactory {
    private int productionRate; // toys per hour
    private long hoursSpentWaiting;
    private int toysProduced;
    private ToyRecipe toyRecipe;
    private Map<Component, Integer> storedComponents;

    public ToyFactory(int productionRate) {
        this.productionRate = productionRate;
        this.storedComponents = new HashMap<>();
        this.toyRecipe = new ToyRecipe();

        // Initialize with recipe to make weasel™ soft toys
        this.storedComponents.put(Component.FUR, 0);
        this.storedComponents.put(Component.FILLING, 0);
        this.storedComponents.put(Component.NOSE, 0);
        this.storedComponents.put(Component.EYE, 0);

        this.toyRecipe.addComponent(Component.FUR, 1);
        this.toyRecipe.addComponent(Component.FILLING, 1);
        this.toyRecipe.addComponent(Component.NOSE, 1);
        this.toyRecipe.addComponent(Component.EYE, 2);
    }

    /**
     * Advances this toy factory one step (hour) forward in the simulation.
     * The factory produces toys if there are enough components available.
     * <p>
     * Otherwise adds one hour to waiting hours counter and does nothing.
     * @return The number of toys produced during this tick
     */
    public int tick() {
        if(!this.toyRecipe.canProduceWith(this.storedComponents, this.productionRate)) {
            // No toys could be produced during this tick
            this.hoursSpentWaiting++;
            return 0;
        }

        // Producing toys decreases the number of stored components and increases total toy count
        this.toysProduced += this.toyRecipe.produce(this.storedComponents, this.productionRate);

        // Return toys produced during this tick
        return this.productionRate;
    }
    
    /**
     * Calculates the utilization rate of this factory using the {@code hoursSpentWaiting} class variable.
     * @param hoursPassed Hours passed since this factory started operating
     * @return The utilization rate of this factory
     */
    public float getUtilizationRate(int hoursPassed) {
        return 1f - ((float) this.hoursSpentWaiting / (float) hoursPassed);
    }

    public long getHoursSpentWaiting() {
        return this.hoursSpentWaiting;
    }

    public int getToysProduced() {
        return this.toysProduced;
    }

   public Map<Component, Integer> getStoredComponents() {
        return this.storedComponents;
   }
}

class ToyRecipe {
    private Map<Component, Integer> requiredComponents;

    public ToyRecipe() {
        this.requiredComponents = new HashMap<>();
    }

    /**
     * Checks if this recipe can be produced with the provided {@code componentList} and {@code productionRate}.
     * @param componentList Available components and their amounts
     * @param productionRate Target number of products to be produced per tick (hour)
     * @return True if the components provided fulfill the requirements to produce this recipe with the
     *  given production rate otherwise returns false
     */
    public boolean canProduceWith(Map<Component, Integer> componentList, int productionRate) {
        for(var entry : componentList.entrySet()) {
            if(!this.requiredComponents.containsKey(entry.getKey())) {
                return false;
            }
            int requiredAmount = this.requiredComponents.get(entry.getKey()) * productionRate;
            if(entry.getValue() < requiredAmount) {
                return false;
            }
        }
        return true;
    }

    /**
     * Consumes given components to produce this recipe with the rate of {@code productionRate}.
     * @param componentsToConsume Components to use to produce the recipe
     * @param productionRate Number of products to be produced per tick (hour)
     * @return Number of products created. The same as {@code productionRate}
     */
    public int produce(Map<Component, Integer> componentsToConsume, int productionRate) {
        for(var entry : componentsToConsume.entrySet()) {
            int requiredAmount = this.requiredComponents.get(entry.getKey()) * productionRate;
            entry.setValue(entry.getValue() - requiredAmount);
        }
        return productionRate;
    }

    public void addComponent(Component component, int requiredAmount) {
        this.requiredComponents.put(component, requiredAmount);
    }

    public void removeComponent(Component component) {
        this.requiredComponents.remove(component);
    }

    public void removeAllComponents() {
        this.requiredComponents.clear();
    }
}
