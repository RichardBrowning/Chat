public class User {
    private int userID;
    private String username;
    private int password;
    private boolean ifAdmin;

    //construct default user
    public User(int userID, String username, int password, boolean ifAdmin){

    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    //getter
    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

    public boolean getAdmin() {
        return ifAdmin;
    }


    public void changePassword() {

    }

    public void changeUsername() {

    }
    public void check(){

    }

}
