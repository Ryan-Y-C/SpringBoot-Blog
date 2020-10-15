package hello.entity;

public class Status {
    private String status;
    private String msg;

    public static Status failStatus(String msg) {
        return new Status("fail", msg);
    }

    public static Status successStatus(String msg) {
        return new Status("ok", msg);
    }

    protected Status(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
