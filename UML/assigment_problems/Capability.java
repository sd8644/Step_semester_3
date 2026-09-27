import java.util.*;

interface Capability {
    String getName();
    boolean apply(String deviceName, Object value);
}

class PowerCapability implements Capability {
    private boolean isOn = false;

    public String getName() { return "Power"; }

    public boolean apply(String deviceName, Object value) {
        if (value instanceof String && (((String) value).equalsIgnoreCase("ON") || ((String) value).equalsIgnoreCase("OFF"))) {
            this.isOn = ((String) value).equalsIgnoreCase("ON");
            System.out.println(deviceName + ": " + ((String) value).toUpperCase() + ".");
            return true;
        }
        System.out.println("Rejected: " + deviceName + " power must be ON or OFF.");
        return false;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    public String getName() { return "Brightness"; }

    public boolean apply(String deviceName, Object value) {
        if (value instanceof Integer && (Integer) value >= 0 && (Integer) value <= 100) {
            this.brightness = (Integer) value;
            System.out.println(deviceName + ": brightness set to " + value + "%.");
            return true;
        }
        System.out.println("Rejected: " + deviceName + " brightness must be between 0 and 100%.");
        return false;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 24;

    public String getName() { return "Temperature"; }

    public boolean apply(String deviceName, Object value) {
        if (value instanceof Integer && (Integer) value >= 16 && (Integer) value <= 30) {
            this.temperature = (Integer) value;
            System.out.println(deviceName + ": temperature set to " + value + "°C.");
            return true;
        }
        System.out.println("Rejected: " + deviceName + " temperature must be between 16°C and 30°C.");
        return false;
    }
}

class Device {
    private final String name;
    private final Map capabilities = new LinkedHashMap<>();

    public Device(String name) { this.name = name; }
    public String getName() { return name; }
    public void addCapability(Capability c) { capabilities.put(c.getName(), c); }
    public boolean hasCapability(String name) { return capabilities.containsKey(name); }
    public Capability getCapability(String name) { return capabilities.get(name); }
}

class Scene {
    private final String name;
    private final List> steps = new ArrayList<>();

    public Scene(String name) { this.name = name; }
    public void addStep(String capability, Object value) { steps.add(new AbstractMap.SimpleEntry<>(capability, value)); }

    public void execute(List devices) {
        System.out.println("Scene '" + name + "' started.");
        int actions = 0;
        for (Map.Entry step : steps) {
            for (Device dev : devices) {
                if (dev.hasCapability(step.getKey()) && dev.getCapability(step.getKey()).apply(dev.getName(), step.getValue())) {
                    actions++;
                }
            }
        }
        System.out.println("Scene '" + name + "' completed: " + actions + " actions applied.");
    }
}

public class SmartLabControlPanel {
    public static void main(String[] args) {
        Device labAc = new Device("Lab AC");
        labAc.addCapability(new PowerCapability());
        labAc.addCapability(new TemperatureCapability());

        Device ceilingLights = new Device("Ceiling Lights");
        ceilingLights.addCapability(new PowerCapability());
        ceilingLights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List devices = Arrays.asList(labAc, ceilingLights, projector);

        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep("Power", "ON");
        lectureMode.addStep("Brightness", 40);
        lectureMode.addStep("Temperature", 24);
        lectureMode.execute(devices);

        labAc.getCapability("Temperature").apply(labAc.getName(), 12);

        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");
        projector.getCapability("Brightness").apply(projector.getName(), 70);
    }
}