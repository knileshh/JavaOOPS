package SystemDesign.DeviceMonitoringSystem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Device Monitoring System.");

        List<Device> devices = List.of(
                new Router("router", Device.Status.Active),
                new Sensor("sensor", Device.Status.Active),
                new DatabaseServer("DBSever", Device.Status.Active),
                new SmartHub("SmartHub", Device.Status.Active)
        );


        for (Device d : devices) {
            if (d instanceof DataSender) ((DataSender)d).sendData();
            if (d instanceof DataReceiver) ((DataReceiver)d).storeData();

            d.connect();
        }

    }
}

abstract class Device {
    enum Status { Active, Inactive}
    String id;
    Status status;

    Device(String id, Status status) {
        this.id = id;
        this.status = status;

        System.out.println("Initialized" + this.id);
    }

    abstract void connect();
}

// Make sure to not have interfaces names as simple entities.
interface DataSender {
    void sendData();
}

interface DataReceiver {
    void storeData();
}

class Router extends Device{
    Router (String id, Status status) {
        super(id, status);
    }

    @Override
    void connect() {
        System.out.println("Router connected " + this.id);
    }
}

class Sensor extends Device {
    Sensor (String id, Status status) {
        super(id, status);
    }

    @Override
    void connect() {
        System.out.println("Sensor Connected " + this.id);
    }
}

class DatabaseServer extends Device implements DataReceiver{
    DatabaseServer(String id, Status status) {
        super(id, status);
    }

    @Override
    void connect() {
        System.out.println("Database Server Connected " + this.id);
    }

    @Override
    public void storeData() {
        System.out.println("Data stores in the DB server " + this.id);
    }
}

class SmartHub extends Device implements DataSender {
    SmartHub(String id, Status status) {
        super(id, status);
    }

    @Override
    void connect() {
        System.out.println("SmartHub connected " + this.id);
    }

    @Override
    public void sendData() {
        System.out.println("SmartHub sending data " + this.id );
    }
}


