public class ToyFactory {
    static int PRODUCTION_RATE = 500; // toys per hour

    private int toysProduced;
    private int numOfFurs;
    private int numOfFillings;
    private int numOfNoses;
    private int numOfEyes;

    public int tick() {
        boolean enoughComponents = 
            numOfFurs > 0 && numOfFillings > 0 && numOfNoses > 0 && numOfEyes > 1;
        
        if(!enoughComponents) {
            // No toys were produced this tick
            return 0;
        }

        // Update stored component counts and total toys
        this.numOfEyes -= 2;
        this.numOfFurs -= 1;
        this.numOfNoses -= 1;
        this.numOfFillings -= 1;
        this.toysProduced += PRODUCTION_RATE;

        // Return toys produced this tick
        return PRODUCTION_RATE;
    }

    public float getUtilizationRate() {
        return 0f;
    }

    public int getToysProduced() {
        return this.toysProduced;
    }

    public int getNumOfFurs() {
        return numOfFurs;
    }

    public void setNumOfFurs(int numberOfFurs) {
        this.numOfFurs = numberOfFurs;
    }

    public int getNumOfFillings() {
        return numOfFillings;
    }

    public void setNumOfFillings(int numberOfToppings) {
        this.numOfFillings = numberOfToppings;
    }

    public int getNumOfNoses() {
        return numOfNoses;
    }

    public void setNumOfNoses(int numberOfNoses) {
        this.numOfNoses = numberOfNoses;
    }

    public int getNumOfEyes() {
        return numOfEyes;
    }

    public void setNumOfEyes(int numberOfEyes) {
        this.numOfEyes = numberOfEyes;
    }
}
