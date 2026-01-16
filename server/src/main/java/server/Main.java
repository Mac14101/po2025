package server;

import database.ApplicationDatabase;
import entities.User;
import jexp.Router;
import jexp.Server;
import jexp.defaultHandlers.SessionHandler;
import jexp.defaultHandlers.SessionRefreshHandler;
import server.handlers.authentication.AuthenticationHandler;
import server.handlers.authentication.SessionDestroyHandler;
import server.handlers.users.AllUsersHandler;
import server.handlers.users.CreateUserHandler;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, Router.RouterError, IOException {
        ApplicationDatabase database = ApplicationDatabase.getInstance();
        database.connect("database.db");
        database.initialize(new User(null, "admin@gmail.com", "Admin", "Admin", "admin123", User.Role.ADMIN.toString()));
        Server server = new Server();
        server.use("/", new SessionHandler());
        server.use("/session/", new SessionRefreshHandler());
        server.post("/login/", new AuthenticationHandler());
        server.get("/logout/", new SessionDestroyHandler());
        server.get("/user/", new AllUsersHandler());
        server.post("/user/", new CreateUserHandler());
        server.listen(8080);
    }
}