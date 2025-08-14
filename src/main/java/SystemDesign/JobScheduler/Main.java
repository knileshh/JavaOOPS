package SystemDesign.JobScheduler;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("JobScheduler ON-LINE!");
        Scheduler scheduler = new Scheduler();

        scheduler.add(new DatabaseJob("DB001", Job.Priority.HIGH));
        scheduler.add(new NetworkJob("NW001", Job.Priority.MEDIUM));
        scheduler.add(new ComputationJob("CM001", Job.Priority.LOW));

        scheduler.run();
    }
}

// Abstract because we need to have the fields as well. Especially the enum!
abstract class Job {
    enum Priority {LOW, MEDIUM, HIGH}

    Priority priority;
    String id;

    Job(String id, Priority priority) {
        this.id = id;
        this.priority = priority;
    }

    abstract public void execute();

    @Override
    public String toString() {
        return "ID: " + this.id + " " + "Priority: " + this.priority;
    }
}

interface Cancelable {
    void cancel();
}


class DatabaseJob extends Job {

    public DatabaseJob(String id, Priority priority) {
        super(id, priority);
    }

    public void execute() {
        System.out.println("DB Job executed");
    }
}

class NetworkJob extends Job {

    public NetworkJob(String id, Priority priority) {
        super(id, priority);
    }

    public void execute() {
        System.out.println("NetworkJob Net Req");
    }
}


class ComputationJob extends Job implements Cancelable{
    public ComputationJob(String id, Priority priority) {
        super(id, priority);
    }

    public void execute() {
        System.out.println("Computation JOB");
    }

    public void cancel() {
        System.out.println(this.toString() + " is CANCELED of Scheduler");
    }
}

class Scheduler {
    List<Job> jobs = new ArrayList<>();

    public void add(Job jobs) {
        this.jobs.add(jobs);
        System.out.println(jobs  + " is ADDED to Scheduler");
    }

    public void run() {
        for (Job job : jobs) {
            if (job.priority == Job.Priority.HIGH) {
                job.execute();
            }
        }

        for (Job job: jobs) {
            if (job.priority == Job.Priority.MEDIUM) {
                job.execute();
            }
        }

        for (Job job : jobs) {
            if (job.priority == Job.Priority.LOW) {
                job.execute();

                // Conflicts but I'm testing
                if (job instanceof ComputationJob) {
                    // Ensure to cast else it won't find cancel method
//                    ((ComputationJob) job).cancel();
                    ((Cancelable) job).cancel();

                }
            }
        }
    }
}