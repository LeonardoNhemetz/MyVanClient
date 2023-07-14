package myvan.myvanclient.Backgrounds;

/**
 * Created by Leonardo on 15/12/2016.
 */

public class User {
    static String user_name,password,table;

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        User.user_name = user_name;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getTable() {
        return table;
    }

    public static void setTable(String table) {
        User.table = table;
    }
}
