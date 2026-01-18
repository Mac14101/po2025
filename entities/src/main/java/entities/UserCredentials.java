package entities;

/**
 * Obiekt zawierający dane do uwierzytelniania.
 */
public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredentials() {
        this.email = null;
        this.password = null;
    }

    /**
     * Zwraca adres e-mail.
     *
     * @return adres e-mail
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Ustawia adres e-mail.
     *
     * @param email adres e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Zwraca hasło.
     *
     * @return hasło
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Ustawia hasło
     *
     * @param password hasło
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
