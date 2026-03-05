public class Hyena extends Animal {
    private String laughType;

    public Hyena(String name, int age) {
        super(name, age, "Hyena");
        this.laughType = "cackling";
    }

    @Override
    public String getExtraInfo() {
        return "laugh=" + laughType;
    }
}