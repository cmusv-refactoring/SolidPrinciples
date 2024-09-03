import solid.service.AdminUserService;
import solid.service.RegularUserService;
import solid.user.AdminUser;
import solid.user.User;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------------Adding users-------------------------");
        // Create User instances
        User user1 = new User(1, "john_doe", 25, 100000);
        User user2 = new User(2, "alice_smith", 35, 100000);
        User user3 = new AdminUser(0, "bob_jones", 65, 100000, true);

        RegularUserService regularUserService = new RegularUserService();
        AdminUserService adminUserService = new AdminUserService();

        // Save users 1 and 2 using RegularUserService
        regularUserService.addUser(user1);
        regularUserService.addUser(user2);

        adminUserService.addUser(user3);


        System.out.println("\n------------------Sending notifications-------------------------");

        // Send notifications using different services
        regularUserService.sendTaxNotification(user1, "Pay your taxes!", "EMAIL");
        regularUserService.sendTaxNotification(user2, "Pay your taxes!", "SMS");
        //regularUserService.sendTaxNotification(user2, "Pay your taxes!", "PUSH"); //This breaks the code

        adminUserService.sendTaxNotification(user3, "Pay your taxes!", "PUSH");

        System.out.println("\n------------------Fetching users-------------------------");

        // Fetch and display user by ID -> there is an encapsulation violation here -> bug proneness
        regularUserService.getUserById(1);
        adminUserService.getUserById(0);

        System.out.println("\n------------------Deleting users-------------------------");
        // Delete accounts
        regularUserService.removeUser(user1);
        adminUserService.removeUser(user2);
        regularUserService.removeUser(user3);

        adminUserService.removeUser(user3);

    }
}