package SystemDesign.DiscordReadStateService;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ReadStateService service = new ReadStateService(3);

        // Insert states
        service.updateReadState("user1", "channel1", 2, "msg100");
        service.updateReadState("user2", "channel2", 5, "msg200");
        service.updateReadState("user3", "channel3", 1, "msg300");

        // Cache has 3 items
        service.printCache();

        // Insert 4th (should evict user1:channel1 because of LRU)
        service.updateReadState("user4", "channel4", 7, "msg400");
        service.printCache();

        // Try to get user1's state (will return a default since it was evicted)
        System.out.println("user1 state: " + service.getReadState("user1", "channel1"));

        // Access user2 to refresh it in LRU order
        System.out.println("user2 state: " + service.getReadState("user2", "channel2"));

        // Insert another, should evict user3 this time
        service.updateReadState("user5", "channel5", 9, "msg500");
        service.printCache();
    }
}

class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true); // true = access order
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

class ReadState {
    private int mentions;
    private String lastReadMessageId;

    public ReadState(int mentions, String lastReadMessageId) {
        this.mentions = mentions;
        this.lastReadMessageId = lastReadMessageId;
    }

    public int getMentions() {
        return mentions;
    }

    public void setMentions(int mentions) {
        this.mentions = mentions;
    }

    public String getLastReadMessageId() {
        return lastReadMessageId;
    }

    public void setLastReadMessageId(String lastReadMessageId) {
        this.lastReadMessageId = lastReadMessageId;
    }

    @Override
    public String toString() {
        return "ReadState{" +
                "mentions=" + mentions +
                ", lastReadMessageId='" + lastReadMessageId + '\'' +
                '}';
    }
}

class ReadStateService {
    private final LRUCache<String, ReadState> cache;

    public ReadStateService(int capacity) {
        this.cache = new LRUCache<>(capacity);
    }

    private String makeKey(String userId, String channelId) {
        return userId + ":" + channelId;
    }

    public ReadState getReadState(String userId, String channelId) {
        String key = makeKey(userId, channelId);
        return cache.getOrDefault(key, new ReadState(0, "none"));
    }

    public void updateReadState(String userId, String channelId, int mentions, String lastMsgId) {
        String key = makeKey(userId, channelId);
        ReadState state = new ReadState(mentions, lastMsgId);
        cache.put(key, state);
    }

    public int getCacheSize() {
        return cache.size();
    }

    public void printCache() {
        System.out.println("Cache contents: " + cache);
    }
}
