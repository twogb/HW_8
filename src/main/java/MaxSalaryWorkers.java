public class MaxSalaryWorkers {

    private String name;
    private int projectSalary;

    public MaxSalaryWorkers(String name, int projectSalary) {
        this.name = name;
        this.projectSalary = projectSalary;
    }

    public String getName() {
        return name;
    }

    public int getProjectSalary() {
        return projectSalary;
    }

    @Override
    public String toString() {
        return "MaxSalaryWorkers{" +
                "name='" + name + '\'' +
                ", projectSalary=" + projectSalary +
                '}';
    }
}
