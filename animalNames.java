// animalNames portion
public class animalNames {
    public static Animal create(String species, String name, int age) {
        String s = species.trim().toLowerCase();
        //  returns the names of the hyenas, lions, bears, and tigers
        switch (s) {
            case "hyena":
                return new Hyena(name, age);
            case "lion":
                return new Lion(name, age);
            case "tiger":
                return new Tiger(name, age);
            case "bear":
                return new Bear(name, age);
            default:
                throw new IllegalArgumentException("Unknown species: " + species);
        }
    }
}
