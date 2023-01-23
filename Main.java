import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static LocalDateTime SIM_START_TIME = LocalDateTime.of(2023, 4, 1, 0, 0);

    public static void main(String[] args) throws InterruptedException {
        ToyFactory toyFactory = new ToyFactory();
        int hoursPassed = 0;

        // Create subcontractors
        List<ComponentProducer> subcontractors = new ArrayList<>();
        subcontractors.add(new ComponentProducer(Component.FUR, 40, 200, 10, toyFactory));
        subcontractors.add(new ComponentProducer(Component.FILLING, 45, 10, 12, toyFactory));
        subcontractors.add(new ComponentProducer(Component.NOSE, 60, 100, 8, toyFactory));
        subcontractors.add(new ComponentProducer(Component.EYE, 90, 300, 14, toyFactory));

        while(toyFactory.getToysProduced() < 1e6) {
            toyFactory.tick();
            for(var subcontractor : subcontractors) {
                subcontractor.tick();
            }
            hoursPassed += 1;
            System.out.println(String.format("Hour %d: Toys produced: %d", hoursPassed, toyFactory.getToysProduced()));
        }

        System.out.println(String.format("Hours passed: %d", hoursPassed));
    }
}