package solid.service;

import solid.user.User;

/**
 * Low Cohesion:</b> The <code>UserOperations</code> interface is too broad and covers multiple
 * responsibilities (user management and notification sending), which are unrelated for some implementing classes.
 */
public interface UserOperations {
    void addUser(User user);
    void removeUser(User user);
    void sendEmailNotification(User user, String message);
    void sendSMSNotification(User user, String message);
    void sendPushNotification(User user, String message);
}

