package DeviceMonitoringSystem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Device Monitoring System.");

        List<Device> devices = List.of(
                new Router("router", "active"),
                new Sensor("sensor", "active"),
                new DatabaseServer("DBSever", "Active"),
                new SmartHub("SmartHub", "active")
        );

        List<DataSender> datasenders = List.of(
                new SmartHub("SmartHub2", "Active")
        );

        List<DataReceiver> datareceivers = List.of(
                new DatabaseServer("DBSever2", "Active")
        );


        // Connect all devices
        for (Device d : devices) {
            d.connect();
        }

        for (DataSender ds : datasenders) {
            ds.sendData();
        }

        for (DataReceiver dr : datareceivers) {
            dr.storeData();
        }

    }
}

abstract class Device {
    String id;
    String status;

    Device(String id, String status) {
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
    Router (String id, String status) {
        super(id, status);
    }

    @Override
    void connect() {
        System.out.println("Router connected " + this.id);
    }
}

class Sensor extends Device {
    Sensor (String id, String status) {
        super(id, status);
    }

    @Override
    void connect() {
        System.out.println("Sensor Connected " + this.id);
    }
}

class DatabaseServer extends Device implements DataReceiver{
    DatabaseServer(String id, String status) {
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
    SmartHub(String id, String status) {
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


