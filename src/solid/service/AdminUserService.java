package solid.service;

import solid.user.User;

public class AdminUserService extends RegularUserService {
    @Override
    public void sendPushNotification(User user, String message) {
        System.out.println("Sending PUSH to " + user.getUsername() + " -> " + message);;
    }
}
