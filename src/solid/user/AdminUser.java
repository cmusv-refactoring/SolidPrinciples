package solid.user;

/**
 * Why This Violates the Liskov Substitution Principle:
 * <ul>
 *   <li><b>Unexpected Behavior Change:</b> The <code>AdminUser</code> class overrides the <code>deleteAccount</code> method
 *   to provide different behavior. For regular users, the <code>deleteAccount</code> method always deletes the user account,
 *   but for <code>AdminUser</code>, the deletion is conditional (<code>if (hasSuperAdminRights)</code>), meaning the behavior
 *   is not consistent with the parent class.</li>
 *   <li><b>Client Code Assumptions Broken:</b> Any client code that relies on the <code>deleteAccount</code> method to
 *   always delete an account will break if it encounters an <code>AdminUser</code> with super admin rights. This violates the
 *   Liskov Substitution Principle because the subclass (<code>AdminUser</code>) cannot fully substitute the superclass
 *   (<code>User</code>) without altering the program's expected behavior.</li>
 * </ul>
 */

public class AdminUser extends User {
    private boolean hasSuperAdminRights;

    public AdminUser(int id, String username, int age, double salary, boolean hasSuperAdminRights) {
        super(id, username, age, salary);
        this.hasSuperAdminRights = hasSuperAdminRights;
    }

    // Override the deleteAccount method
    @Override
    public void deleteAccount() {
        if (hasSuperAdminRights) {
            System.out.println("Admin user account cannot be deleted: " + getUsername());
        } else {
            super.deleteAccount();
        }
    }
}
