package solid.service;

import solid.persistence.PostgresDriver;
import solid.user.User;

/**
 * Why This Violates the Interface Segregation Principle (ISP):
 * <ul>
 *   <li><b>Forces Irrelevant Implementations:</b> The <code>RegularUserService</code> class is forced to implement
 *   methods (<code>sendEmailNotification</code>, <code>sendSMSNotification</code>, <code>sendPushNotification</code>)
 *   that it does not need or use.</li>
 *   <li><b>Unsupported Operations:</b> The class throws <code>UnsupportedOperationException</code> for some of these methods,
 *   which is a clear sign that it is being forced to implement methods that are not relevant to its role.</li>
 *   <li><b>Low Cohesion:</b> The <code>UserOperations</code> interface is too broad and covers multiple
 *   responsibilities (user management and notification sending), which are unrelated for some implementing classes.</li>
 * </ul>
 *
 * <p>
 * Why This Violates the Dependency Inversion Principle (DIP):
 * </p>
 * <ul>
 *   <li><b>High-Level Module Depends on Low-Level Module:</b> The <code>UserService</code> class directly depends on the
 *   concrete <code>PostgresDriver</code> class. This makes it difficult to change the database layer without modifying the
 *   <code>UserService</code> class.</li>
 *   <li><b>Lack of Abstractions:</b> There is no abstraction or interface that defines the behavior of a database driver,
 *   making the <code>UserService</code> class tightly coupled to the <code>PostgresDriver</code>.</li>
 * </ul>
 *
 * <p>
 * Why This Violates the Open-Closed Principle (OCP):
 * </p>
 * <ul>
 *   <li><b>Modification Required for Each New Notification Type:</b> Every time a new notification type is needed
 *   (e.g., "In-App Message" or "Webhook"), you must modify the <code>sendNotification</code> method to add a new
 *   <code>else if</code> block and create a corresponding method for the new notification type.</li>
 *   <li><b>High Risk of Errors:</b> Frequent modifications increase the risk of introducing bugs, especially if the existing
 *   code is already complex.</li>
 *   <li><b>Difficult to Test and Maintain:</b> As more notification types are added, the <code>NotificationService</code> class
 *   becomes harder to test and maintain due to its increasing size and complexity.</li>
 * </ul>
 *
 * <p>
 * Why This Violates the Single Responsibility Principle (SRP):
 * </p>
 * <ul>
 *   <li><b>Managing User Data:</b> The <code>RegularUserService</code> class has methods like <code>createUser</code> and
 *   <code>deleteUser</code> that handle user data in the database.</li>
 *   <li><b>Sending Notifications:</b> The class also contains methods like <code>sendWelcomeEmail</code> and
 *   <code>sendPasswordResetEmail</code> that are responsible for sending different types of emails.</li>
 * </ul>
 */

public class RegularUserService implements UserOperations {
    private PostgresDriver postgresDriver;

    public RegularUserService() {
        // Initialize RegularUserService with a PostgresDriver
        this.postgresDriver = new PostgresDriver();;
    }

    public User getUserById(int id) {
        User fetchedUser = postgresDriver.query(id);

        if (fetchedUser == null) {
            System.out.println("User with ID " + id + " not found.");
            return null;
        }
        System.out.println("Fetched User: " + fetchedUser.getUsername() + ", " + fetchedUser.getAge() + "\n");
        return fetchedUser;
    }

    @Override
    public void addUser(User user) {
        postgresDriver.save(user);
    }

    @Override
    public void removeUser(User user) {
        boolean del = postgresDriver.delete(user.getId());
        if (del) {
            user.deleteAccount();
            System.out.println("Account deleted successfully.\n");
        } else {
            System.out.println("User with id " + user.getId() + " not found!\n");
        }
    }

    // Method to send a notification based on notification type
    public void sendTaxNotification(User user, String message, String notificationType) {
        message = message + " Value: " + user.calculateTax();

        if (notificationType.equals("EMAIL")) {
            sendEmailNotification(user, message);
        } else if (notificationType.equals("SMS")) {
            sendSMSNotification(user, message);
        } else if (notificationType.equals("PUSH")) {
            sendPushNotification(user, message);
        } else {
            throw new IllegalArgumentException("Unknown notification type: " + notificationType);
        }
    }

    @Override
    public void sendEmailNotification(User user, String message) {
        System.out.println("Sending EMAIL to " + user.getUsername() + " -> " + message);
    }

    @Override
    public void sendSMSNotification(User user, String message) {
        System.out.println("Sending SMS to " + user.getUsername() + " -> " + message);
    }

    @Override
    public void sendPushNotification(User user, String message) {
        throw new UnsupportedOperationException("RegularUserService does not support sending push notifications.");
    }
}
