public abstract class Animal {

    // Instance variables (encapsulation)
    private String name;
    private int age;
    private String species;

    // Constructor
    public Animal(String name, int age, String species) {
        this.name = name;
        this.age = age;
        this.species = species;
    }

    // The Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getSpecies() {
        return species;
    }

    // The Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    // Abstract method (forces subclasses to implement it)
    public abstract String getExtraInfo();

    // Used for report printing
    @Override
    public String toString() {
        return species + " | " + name + " | Age: " + age + " | " + getExtraInfo();
    }
}