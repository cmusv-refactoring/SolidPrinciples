package solid.user;

public class User {
    private int id;
    private String username;
    private int age;
    private double salary;

    // Constructor
    public User(int id, String username, int age, double salary) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.salary = salary;
    }

    // Method to calculate tax based on age and salary
    public double calculateTax() {
        double taxRate;
        if (age < 30) {
            taxRate = 0.10; // 10% tax rate
        } else if (age <= 60) {
            taxRate = 0.20; // 20% tax rate
        } else {
            taxRate = 0.15; // 15% tax rate
        }
        return salary * taxRate;
    }

    // Regular users can delete themselves
    public void deleteAccount() {
        System.out.println("User account deleted: " + username);
    }

    // Getters and setters (optional, for accessing private fields)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}

