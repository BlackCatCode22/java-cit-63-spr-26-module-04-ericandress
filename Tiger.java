public class Tiger extends Animal {
    private String stripePattern;

    public Tiger(String name, int age) {
        super(name, age, "Tiger");
        this.stripePattern = "bold";
    }

    @Override
    public String getExtraInfo() {
        return "stripes=" + stripePattern;
    }
}
