import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimalParser {

    // Finds the first integer in a line as the age (e.g., "4 years old" -> 4)
    private static final Pattern AGE_PATTERN = Pattern.compile("\\b(\\d{1,2})\\b");

    public static ParsedAnimal parseLine(String line) {
        // Example tolerant parsing:
        // - species: checks for known words in the line
        // - age: first integer
        // - name: tries to grab a trailing name-like token, fallback "Unknown"

        String lower = line.toLowerCase(Locale.ROOT);

        String species = null;
        if (lower.contains("hyena")) species = "Hyena";
        else if (lower.contains("lion")) species = "Lion";
        else if (lower.contains("tiger")) species = "Tiger";
        else if (lower.contains("bear")) species = "Bear";

        if (species == null) {
            throw new IllegalArgumentException("Could not detect species from: " + line);
        }

        int age = 0;
        Matcher m = AGE_PATTERN.matcher(line);
        if (m.find()) {
            age = Integer.parseInt(m.group(1));
        } else {
            throw new IllegalArgumentException("Could not detect age from: " + line);
        }

        // name heuristic: last word that starts with a letter
        String name = "Unknown";
        String[] parts = line.trim().split("\\s+");
        for (int i = parts.length - 1; i >= 0; i--) {
            if (parts[i].matches("[A-Za-z][A-Za-z'-]*")) {
                name = parts[i];
                break;
            }
        }

        return new ParsedAnimal(species, name, age);
    }

    public static class ParsedAnimal {
        public final String species;
        public final String name;
        public final int age;

        public ParsedAnimal(String species, String name, int age) {
            this.species = species;
            this.name = name;
            this.age = age;
        }
    }
}