package server.handlers.authentication;

import entities.Message;
import entities.UserChangePassword;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class UpdateUserPasswordHandler extends DatabaseHandler {
    public UpdateUserPasswordHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        UserChangePassword userChangePassword = request.getBody(UserChangePassword.class);
        if (userChangePassword.newPassword == null || !userChangePassword.newPassword.equals(userChangePassword.newPasswordConfirm)) {
            Message message = new Message();
            message.addMessage("password", "Hasła nie są takie same.");
            response.status(400);
            response.json(message);
            return;
        }
        this.database.updateUserPassword(Integer.valueOf(request.getSession("userId")), userChangePassword.newPassword);
    }
}
