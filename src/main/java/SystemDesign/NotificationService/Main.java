package SystemDesign.NotificationService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Notification Service is Live");

        Notifications n1 = Factory.getNotification(Notifications.Type.EMAIL);
        Notifications n2 = Factory.getNotification(Notifications.Type.SMS);
        Notifications n3 = Factory.getNotification(Notifications.Type.PUSH);

        n1.send("Welcome to the app!");
        n2.send("Your OTP is 1234");
        n3.send("See what's in your feed!");
    }
}

abstract class Notifications {
    enum Type {EMAIL, SMS, PUSH}

    abstract void send(String message);
}

class EmailNotification extends Notifications {

    void send(String message) {
        System.out.println("Sending EMAIL: " + message);
    }
}

class SMSNotification extends Notifications {

    void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class PushNotification extends Notifications {

    void send(String message) {
        System.out.println("Sending PUSH: " + message);
    }
}

class Factory {
    public static Notifications getNotification(Notifications.Type type) {
        return switch (type) {
            case EMAIL -> new EmailNotification();
            case SMS -> new SMSNotification();
            case PUSH -> new PushNotification();
        };
    }
}