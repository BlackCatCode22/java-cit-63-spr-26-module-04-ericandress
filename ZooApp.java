import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ZooApp {

    public static void main(String[] args) {
        String arrivingFile = "arrivingAnimals.txt";
        String namesFile = "animalNames.txt";
        String outputFile = "newAnimals.txt";

        ArrayList<Animal> animals = new ArrayList<>();
        HashMap<String, Integer> speciesCounts = new HashMap<>();
        HashMap<String, ArrayList<Animal>> bySpecies = new HashMap<>();

        // Load optional name pool
        ArrayList<String> namePool = readAllLinesSafe(namesFile);

        // 1) Read arriving animals and create objects
        List<String> arrivingLines = readAllLinesSafe(arrivingFile);
        int nameIndex = 0;

        for (String line : arrivingLines) {
            if (line.trim().isEmpty()) continue;

            AnimalParser.ParsedAnimal parsed = AnimalParser.parseLine(line);

            String name = parsed.name;
            // If arriving file doesn't really contain a usable name, you can swap in a namePool name:
            if (name.equalsIgnoreCase("Unknown") && !namePool.isEmpty()) {
                name = namePool.get(nameIndex % namePool.size());
                nameIndex++;
            }

            Animal animal = animalNames.create(parsed.species, name, parsed.age);
            animals.add(animal);

            // 2) counting species
            speciesCounts.put(animal.getSpecies(), speciesCounts.getOrDefault(animal.getSpecies(), 0) + 1);

            // 3) group species for report output
            bySpecies.putIfAbsent(animal.getSpecies(), new ArrayList<>());
            bySpecies.get(animal.getSpecies()).add(animal);
        }

        // sort animals within each species (by name, then age)
        for (ArrayList<Animal> list : bySpecies.values()) {
            list.sort(Comparator.comparing(Animal::getName).thenComparingInt(Animal::getAge));
        }

        // 4) Write report
        writeReport(outputFile, bySpecies, speciesCounts);
        System.out.println("Report created: " + outputFile);
    }

    private static ArrayList<String> readAllLinesSafe(String filename) {
        try {
            Path p = Paths.get(filename);
            if (!Files.exists(p)) return new ArrayList<>();
            return new ArrayList<>(Files.readAllLines(p));
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void writeReport(String outputFile,
                                    HashMap<String, ArrayList<Animal>> bySpecies,
                                    HashMap<String, Integer> speciesCounts) {
        // Consistent order in output
        List<String> order = Arrays.asList("Hyena", "Lion", "Tiger", "Bear");

        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            out.println("=== NEW ANIMALS REPORT ===");
            out.println();

            for (String species : order) {
                ArrayList<Animal> list = bySpecies.getOrDefault(species, new ArrayList<>());

                out.println("Species: " + species);
                out.println("Count: " + speciesCounts.getOrDefault(species, 0));
                out.println("---------------------------------------------");

                if (list.isEmpty()) {
                    out.println("(none)");
                } else {
                    for (Animal a : list) {
                        out.println(a.toString());
                    }
                }
                out.println();
            }

            // totals (optional)
            int total = 0;
            for (int c : speciesCounts.values()) total += c;
            out.println("TOTAL ANIMALS: " + total);

        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }
}