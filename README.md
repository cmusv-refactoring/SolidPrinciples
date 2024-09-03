### SOLID Principles

The SOLID principles are a set of five design principles in object-oriented programming that help create more understandable, flexible, and maintainable software. They were introduced by Robert C. Martin ("Uncle Bob") to guide developers in building systems that are easy to scale and refactor.

1. **Single Responsibility Principle (SRP):**  
   A class should have only one reason to change, meaning it should have only one job or responsibility.

2. **Open-Closed Principle (OCP):**  
   Software entities (classes, modules, functions, etc.) should be open for extension but closed for modification. You should be able to add new functionality without changing existing code.

3. **Liskov Substitution Principle (LSP):**  
   Objects of a superclass should be replaceable with objects of its subclasses without affecting the correctness of the program. In other words, subclasses should behave in a way that does not break the expectations set by their parent class.

4. **Interface Segregation Principle (ISP):**  
   Clients should not be forced to depend on interfaces they do not use. It's better to have many small, specific interfaces than one large, general-purpose interface.

5. **Dependency Inversion Principle (DIP):**  
   High-level modules should not depend on low-level modules; both should depend on abstractions. Additionally, abstractions should not depend on details, but details should depend on abstractions.

By following the SOLID principles, developers can create more modular and robust software that is easier to test, maintain, and extend.

### Refactoring Operations

We apply several refactoring operations to remove the violations.

**STEP 1: Preparing to remove the violation of the Single Responsibility Principle**
- We need to split the `UserOperations` into two new interfaces: `UserOperations` and `NotificationOperations`.
- Extract interface from `UserOperations` interface.

This refactoring will help to alleviate the violation of the single responsibility principle. Now, we need to cascade this refactoring to be able to remove the entire violation.

**STEP 2: Eliminating the violation of the Single Responsibility Principle**
- Split `RegularUserService` into two distinct services to eliminate the SRP violation. One to handle managing user data and another to send notifications.
   - First, apply the Extract Delegate refactoring to create a new class (`NotificationService`) with the three methods in charge of sending notifications.
   - Then, fix the extensions and remove the delegated methods:
      - Remove the extension that `UserOperations` does to `NotificationOperations`.
      - `NotificationService` should now extend `NotificationOperations`.
      - Remove the override from `AdminUserService` (this violation should be addressed separately).

**STEP 3: Eliminating the violations of Interface Segregation Principle and Open/Closed Principle**
- Split `NotificationOperations` into different notification mechanisms, thus solving both ISP and OCP violations.

After these refactorings, we should see an exception: `RegularUserService` does not support sending push notifications. This happens because there are two violations of principles: First, `NotificationService` is forced to implement all methods from `NotificationOperation`, even those it does not need (i.e., ISP violation). Second, the method `sendTaxNotification` in `RegularUserService` violates the Open-Closed Principle by requiring modifications whenever a new type of notification is added.

To remove these violations, we can use the Strategy Pattern by defining an interface `NotificationSender` and creating separate classes for each notification type. This way, new notification types can be added by extending the code rather than modifying it.

- Define the `NotificationSender` Interface.
- Implement Concrete Notification Senders.
- `NotificationService` does not need to implement `NotificationOperations`.
- Delete the `NotificationOperations`.
- Use a Factory for Handling Notifications.
- Remove the methods to send notifications in `NotificationService`.

Now, we need to fix the behavior associated with admin, who can also send PUSH notifications. We add a logic to check the type of the user in `sendNotification`.

- Rename `RegularUserService` to a more generic name, so it can match the new refactored version.
- Delete `AdminUserService`.
- Create a subpackage for the senders.

**Benefits of the Refactored Design**
- Adheres to the Open-Closed Principle: New notification types can be added without modifying the existing code, thus making the system more maintainable and flexible.
- Easy to Test and Maintain: Each notification type is encapsulated in its own class, making testing straightforward.
- Reduces Risk of Errors: Since existing code is not modified, there's less risk of inadvertently introducing new bugs.

**STEP 4: The `User` class provides a `deleteAccount` method that always deletes the user account.**  
The `AdminUser` subclass changes the behavior of `deleteAccount` by introducing a condition (`if (hasSuperAdminRights)`). If `hasSuperAdminRights` is true, the account is not deleted, which is a different behavior from the superclass. In the client class (`UserService`), the `deleteAccount` method deletes the user's account by printing a message. We assume it would perform a deletion operation in a real system. Everything works as expected. However, if you call this method with an `AdminUser`, the output is misleading. The message "Account deleted successfully." is printed even though the account was not actually deleted due to the overridden behavior in `AdminUser`. This breaks the Liskov Substitution Principle because the subclass (`AdminUser`) does not fulfill the expectations of the superclass (`User`).

- To adhere to the Liskov Substitution Principle, we should ensure that the `AdminUser` class provides behavior that is consistent with the `User` class.
- **Remove the Override**: Do not override `deleteAccount` in `AdminUser` to change the expected behavior.
- **Introduce a Separate Method**: Provide a method in `AdminUser` that checks permissions or super-admin rights explicitly without altering the behavior of `deleteAccount`.

**STEP 5: `UserService` violates the Dependency Inversion Principle**  
`UserService` directly depends on a concrete implementation of a `PostgresDriver` class for database operations. This dependency violates the Dependency Inversion Principle because `UserService` (a high-level module) depends directly on `PostgresDriver` (a low-level module).

- Create `DatabaseDriver` interface.
- Implement the `PostgresDriver` Class by extending the new interface and move it to a newly created drivers subpackage.
- Modify the `UserService` Class to Depend on the Abstraction.

We use dependency injection to remove the violation. Dependency Injection (DI) is a design pattern used in software development to achieve Inversion of Control (IoC) between classes and their dependencies. Instead of a class creating its dependencies internally, these dependencies are "injected" into the class from an external source.

**Benefits of the Refactored Design**
- **Adheres to the Dependency Inversion Principle**: High-level `UserService` depends on the `DatabaseDriver` abstraction, not a concrete implementation.
- **Easily Extensible**: You can easily switch to a different database by implementing a new class (e.g., `MySQLDriver`) without changing the `UserService` class.
- **Improves Testability**: You can mock or stub the `DatabaseDriver` interface during testing, which makes it easier to write unit tests for `UserService`.
