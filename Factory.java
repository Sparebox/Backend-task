import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a factory that makes products from components determined by the supplied recipe.
 */
public class Factory {
    private int productionRate; // Products per hour
    private long hoursSpentWaiting;
    private int unitsProduced;
    private ProductRecipe productRecipe;
    private Map<Component, Integer> storedComponents;

    public Factory(int productionRate) {
        this.productionRate = productionRate;
        this.storedComponents = new HashMap<>();
        this.productRecipe = new ProductRecipe();
    }

    /**
     * Advances this factory one step (hour) forward in the simulation.
     * The factory produces the supplied recipe if there are enough components available.
     * <p>
     * Otherwise adds one hour to waiting hours counter and returns 0.
     * @return The number of products produced during this tick
     */
    public int tick() {
        if(!this.productRecipe.canProduceWith(this.storedComponents, this.productionRate)) {
            // No toys could be produced during this tick
            this.hoursSpentWaiting++;
            return 0;
        }

        // Producing products decreases the number of stored components and increases total product count
        this.unitsProduced += this.productRecipe.produce(this.storedComponents, this.productionRate);

        // Return number of products produced during this tick
        return this.productionRate;
    }
    
    /**
     * Calculates the utilization rate of this factory using the {@code hoursSpentWaiting} class variable.
     * @param hoursPassed Hours passed since this factory started operating
     * @return The utilization rate of this factory
     */
    public double getUtilizationRate(long hoursPassed) {
        return 1.0 - ((double) this.hoursSpentWaiting / (double) hoursPassed);
    }

    // Getters and setters

    public void setProductRecipe(ProductRecipe recipe) {
        for(var component : recipe.getComponents()) {
            // Initialize stored components with zero amounts if they are not present beforehand
            this.storedComponents.putIfAbsent(component, 0);
        }
        this.productRecipe = recipe;
    }

    public long getHoursSpentWaiting() {
        return this.hoursSpentWaiting;
    }

    public int getUnitsProduced() {
        return this.unitsProduced;
    }

   public Map<Component, Integer> getStoredComponents() {
        return this.storedComponents;
   }
}

/**
 * Represents a map of required components in order to create a product.
 * Use the {@code addComponent} and {@code removeComponent} methods to add a component or remove a component from 
 * the recipe respectively.
 */
class ProductRecipe {
    private Map<Component, Integer> requiredComponents;

    public ProductRecipe() {
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
        for(var requirement : this.requiredComponents.entrySet()) {
            if(!componentList.containsKey(requirement.getKey())) {
                // Required component is not in the given component list
                return false;
            }
            int requiredAmount = requirement.getValue() * productionRate;
            if(componentList.get(requirement.getKey()) < requiredAmount) {
                // Not enough components of this type to begin production
                return false;
            }
        }
        return true;
    }

    /**
     * Consumes given components to produce this recipe with the rate of {@code productionRate}.
     * It is assumed that this recipe can be produced with the components given.
     * @param componentsToConsume Components to use to produce the recipe
     * @param productionRate Number of products to be produced per tick (hour)
     * @return Number of products created. The same as {@code productionRate}
     */
    public int produce(Map<Component, Integer> componentsToConsume, int productionRate) {
        for(var requirement : this.requiredComponents.entrySet()) {
            int requiredAmount = requirement.getValue() * productionRate;
            componentsToConsume.compute(requirement.getKey(), (k, v) -> v - requiredAmount);
        }
        return productionRate;
    }

    public Set<Component> getComponents() {
        return this.requiredComponents.keySet();
    }

    /**
     * Define a required component for this recipe.
     * @param component The required component for production
     * @param requiredAmount The quantity of this component that is required to complete the recipe
     */
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
