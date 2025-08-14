package SystemDesign.JobScheduler;

import java.util.*;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(new DefaultPriorityComparator());

        scheduler.schedule(new DatabaseJob("DB001", Job.Priority.HIGH));
        scheduler.schedule(new NetworkJob("NW001", Job.Priority.MEDIUM));
        scheduler.schedule(new ComputationJob("CM001", Job.Priority.LOW));

        scheduler.runAll();
    }
}

// ===== Job Base =====
abstract class Job {
    enum Priority {LOW, MEDIUM, HIGH}

    private final Priority priority;
    private final String id;

    protected Job(String id, Priority priority) {
        this.id = Objects.requireNonNull(id);
        this.priority = Objects.requireNonNull(priority);
    }

    public Priority getPriority() { return priority; }
    public String getId() { return id; }

    public abstract void execute();

    @Override
    public String toString() {
        return "ID: " + id + " | Priority: " + priority;
    }
}

interface Cancelable {
    void cancel();
}

// ===== Job Implementations =====
class DatabaseJob extends Job {
    public DatabaseJob(String id, Priority priority) {
        super(id, priority);
    }
    @Override
    public void execute() {
        System.out.println("[Database] Job executed for " + getId());
    }
}

class NetworkJob extends Job {
    public NetworkJob(String id, Priority priority) {
        super(id, priority);
    }
    @Override
    public void execute() {
        System.out.println("[Network] Request sent for " + getId());
    }
}

class ComputationJob extends Job implements Cancelable {
    public ComputationJob(String id, Priority priority) {
        super(id, priority);
    }
    @Override
    public void execute() {
        System.out.println("[Computation] Job executed for " + getId());
    }
    @Override
    public void cancel() {
        System.out.println(this + " has been canceled.");
    }
}

// ===== Priority Handling =====
class DefaultPriorityComparator implements Comparator<Job> {
    @Override
    public int compare(Job a, Job b) {
        // HIGH → LOW
        return b.getPriority().ordinal() - a.getPriority().ordinal();
    }
}

// ===== Scheduler =====
class Scheduler {
    private static final Logger logger = Logger.getLogger(Scheduler.class.getName());
    private final List<Job> jobs = new ArrayList<>();
    private final Comparator<Job> priorityComparator;

    public Scheduler(Comparator<Job> priorityComparator) {
        this.priorityComparator = priorityComparator;
    }

    public void schedule(Job job) {
        jobs.add(job);
        logger.info(job + " scheduled.");
    }

    public boolean removeJob(String jobId) {
        return jobs.removeIf(j -> j.getId().equals(jobId));
    }

    public void runAll() {
        if (jobs.isEmpty()) {
            logger.warning("No jobs to run!");
            return;
        }

        jobs.sort(priorityComparator);

        for (Job job : jobs) {
            // Example: cancel before execution if some condition matches
            if (job instanceof Cancelable cancelable && job.getPriority() == Job.Priority.LOW) {
                cancelable.cancel();
                continue; // skip execution if canceled
            }
            job.execute();
        }
    }
}
