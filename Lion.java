public class Lion extends Animal {
    private int maneSize;

    public Lion(String name, int age) {
        super(name, age, "Lion");
        this.maneSize = 5; // arbitrary “unique” trait
    }

    @Override
    public String getExtraInfo() {
        return "maneSize=" + maneSize;
    }
}