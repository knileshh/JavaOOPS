package SystemDesign.NotificationService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Notification Service is Live");

        Notifications n1 = new EmailNotification();
        Notifications n2 = new SMSNotification();
        Notifications n3 = new PushNotification();

        n1.send("Welcome to the app!");
        n2.send("Your OTP is 1234");
        n3.send("See what's in your feed!");

        Factory.Notifications nTwo = new Factory("My FACTORY IMPLEMENTATION", Notifications.Type.PUSH);
        ((Factory.Notifications2)nTwo).send();
    }
}

abstract class Notifications {
    enum Type {EMAIL, SMS, PUSH}

    Type type;

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

// My implementation of Factory

class Factory{

    Factory(String message, Notifications.Type type) {
        getNotification(message, type);
    }

    public Notifications2 getNotification(String message, Notifications.Type type) {

        Notifications2 n2 = null;

        if (type == Notifications.Type.EMAIL) {
            n2 = new EmailNotification2(message);
        } else if (type == Notifications.Type.SMS) {
            n2 = new SMSNotification2(message);
        } else if (type == Notifications.Type.PUSH) {
            n2 =  new PushMessage2(message);
        }

        return n2;
    }

    static abstract class Notifications2 {
        abstract void send();
    }

    static class EmailNotification2 extends Notifications2 {
        final private String message;

        private EmailNotification2(String message) {
                this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public void send() {
            System.out.println(getMessage() + "EmailNotification2");
        }
    }

    static class SMSNotification2 extends Notifications2 {
        final private String message;

        private SMSNotification2(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public void send() {
            System.out.println(getMessage() + "SMSNotificatoin");
        }
    }

    static class PushMessage2 extends Notifications2 {
        final private String message;

        private PushMessage2(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        @Override
        public void send() {
            System.out.println(getMessage() + "PushNotificatoin");
        }
    }
}
