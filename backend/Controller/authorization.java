package Controller;

public class authorization
{
    private final String Password;
    private final String Username;
    private boolean login_checker;

    public authorization(String username,String password) {
        Username=username;
        Password=password;
        this.login_checker=false;
    }
    public boolean Login(String enterUsername,String give_password){
        if (Username.equals(enterUsername) && Password.equals(give_password)){
            login_checker=true;
            System.out.println("Admin login succesfully\uD83D\uDC4D");
            return true;
        }
        System.out.println("!!wrong username or wrong password.Try again!!");
        return false;
    }
    public void logout(){
        login_checker=false;
        System.out.println("Admin logged out.");
    }

    public String getUsername() {
        return Username;
    }

    public boolean isLogin_checker() {
        return login_checker;
    }
    public String getPassword() {
        return Password;
    }

}

