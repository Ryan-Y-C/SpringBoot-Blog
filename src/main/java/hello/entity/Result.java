package hello.entity;

public abstract class Result<T> extends Status{
    private T data;

    public Result(String status, String msg, T data) {
        super(status,msg);
        this.data = data;
    }
    protected Result(String status, T data) {
        this(status,"",data);
    }

    public Object getData() {
        return data;
    }
}