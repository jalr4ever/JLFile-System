package Entity;

/**
 * @program: JLFile-OS
 * @description: User's information
 * <p>
 * Created by Jalr on 2018/12/21.
 */
public class User {

    //这里的用户是个顶级的特殊目录，对普通用户不可见
    private String userAccount;

    private String userPassword;

    public String getUserAccout() {
        return userAccount;
    }

    public void setUserAccout(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
