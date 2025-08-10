package DesignPatterns;

public class BuilderPattern {
    public static void main(String[] args) {
        System.out.println("Builder pattern is on!");

        Router router = new Router.Builder().setId("001").setIp("192.168.0.1").setMaxConnection(256).build();

    }
}

class Router {
    private String id;
    private String ip;
    private int maxConnection;

    private Router(Builder b) {
        this.id = b.id;
        this.ip = b.ip;
        this.maxConnection = b.maxConnection;

        System.out.println(this);
    }

    public static class Builder {
        private String id;
        private String ip;
        private int maxConnection;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setMaxConnection(int maxConnection) {
            this.maxConnection = maxConnection;
            return this;
        }

        Router build() {
            if (this.id == null) throw new IllegalStateException("ID is required");
            if (this.ip == null) throw new IllegalStateException("IP is required");
            if (this.maxConnection == 0) this.maxConnection = 256;

            return new Router(this);
        }
    }
}
