public class ApplicationClient extends Client {
    private static ApplicationClient instance;

    private ApplicationClient() {
        super();
    }

    private static ApplicationClient getInstance() {
        if (instance == null) {
            instance = new ApplicationClient();
        }
        return instance;
    }
}
