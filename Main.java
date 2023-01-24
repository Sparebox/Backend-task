import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    static public final LocalDateTime SIM_START_TIME = LocalDateTime.of(2023, 4, 1, 0, 0);

    static public Random random = new Random();
    static public long hoursPassedSinceStart = 0;

    public static void main(String[] args) throws InterruptedException {
        ToyFactory toyFactory = new ToyFactory(500);

        // Create subcontractors
        List<ComponentProducer> subcontractors = new ArrayList<>();
        subcontractors.add(new ComponentProducer(Component.FUR,     40, 200, 10, toyFactory, false));
        subcontractors.add(new ComponentProducer(Component.FILLING, 450, 10, 12, toyFactory, false));
        subcontractors.add(new ComponentProducer(Component.NOSE,    60, 100, 8, toyFactory, true));
        subcontractors.add(new ComponentProducer(Component.EYE,     90, 300, 14, toyFactory, false));

        // Run the simulation until one million toys are produced
        while(toyFactory.getToysProduced() < 1e6) {
            toyFactory.tick();
            for(var subcontractor : subcontractors) {
                subcontractor.tick();
            }
            hoursPassedSinceStart++;
        }

        LocalDateTime simEndTime = SIM_START_TIME.plusHours(hoursPassedSinceStart);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");

        System.out.println(String.format("Millionth toy produced after %d hours on %s", hoursPassedSinceStart, simEndTime.format(dateFormatter)));
        System.out.println(String.format("Hours spent waiting: %d", toyFactory.getHoursSpentWaiting()));
        System.out.println(String.format("Utilization rate: %.1f %%", toyFactory.getUtilizationRate(hoursPassedSinceStart) * 100f));
    }
}