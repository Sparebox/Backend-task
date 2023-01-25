import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static public final LocalDateTime SIM_START_TIME = LocalDateTime.of(2023, 4, 1, 0, 0);

    static public long hoursPassedSinceStart = 0;

    public static void main(String[] args) {
        // Create a factory with production rate of 500 products per hour
        Factory factory = new Factory(500);

        // Initialize factory with recipe to make weasel™ soft toys
        ProductRecipe weaselSoftToy = new ProductRecipe();
        weaselSoftToy.addComponent(Component.FUR, 1);
        weaselSoftToy.addComponent(Component.FILLING, 1);
        weaselSoftToy.addComponent(Component.NOSE, 1);
        weaselSoftToy.addComponent(Component.EYE, 2);
        factory.setProductRecipe(weaselSoftToy);

        // Create subcontractors
        List<ComponentProducer> subcontractors = new ArrayList<>();
        subcontractors.add(new ComponentProducer(Component.FUR,     40, 200, 10, factory));
        subcontractors.add(new ComponentProducer(Component.FILLING, 450, 10, 12, factory));
        subcontractors.add(new ComponentProducer(Component.NOSE,    60, 100, 8, factory));
        subcontractors.add(new ComponentProducer(Component.EYE,     90, 300, 14, factory));

        // Run the simulation until one million toys are produced
        while(factory.getUnitsProduced() < 1e6) {
            factory.tick();
            for(var subcontractor : subcontractors) {
                subcontractor.tick();
            }
            hoursPassedSinceStart++;
        }

        LocalDateTime simEndTime = SIM_START_TIME.plusHours(hoursPassedSinceStart);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");

        System.out.println(String.format("Millionth toy produced after %d hours on %s", hoursPassedSinceStart, simEndTime.format(dateFormatter)));
        System.out.println(String.format("Hours spent waiting: %d", factory.getHoursSpentWaiting()));
        System.out.println(String.format("Utilization rate: %.1f %%", factory.getUtilizationRate(hoursPassedSinceStart) * 100f));
    }
}