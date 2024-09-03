package solid.persistence;

import solid.user.User;

import java.util.HashMap;

public class PostgresDriver {
    private static HashMap<Integer, User> users = new HashMap<>();

    // Method to simulate a database query
    public boolean save(User user) {
        System.out.println("Executing Save SQL: " + user);

        // Here, you would typically execute the query against a real PostgreSQL database and return a ResultSet.
        // This is just a simulation, so we'll return a list with the queried users.
        users.put(user.getId(), user);

        return true;
    }

    public User query(int id) {
        System.out.println("Executing SQL query");
        try {
            return users.get(id);
        } catch (IndexOutOfBoundsException  e) {
            return null;
        }
    }

    public boolean delete(int id) {
        System.out.println("Executing SQL delete");
        try {
            users.remove(id);
            return true;
        } catch (IndexOutOfBoundsException  e) {
            return false;
        }
    }
}

