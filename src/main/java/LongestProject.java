public class LongestProject {
    private String name;

    public LongestProject(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "LongestProject{" +
                "name='" + name + '\'' +
                '}';
    }
}
