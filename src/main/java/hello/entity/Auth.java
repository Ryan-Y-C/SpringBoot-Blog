package hello.entity;

public class Auth extends Result{
    private boolean isLogin;
    public Auth(String status, boolean isLogin, Object data) {
        super(status,data);
        this.isLogin = isLogin;
    }

    public boolean getIsLogin() {
        return isLogin;
    }
}
