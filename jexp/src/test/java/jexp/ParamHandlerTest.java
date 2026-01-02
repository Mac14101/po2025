package jexp;

import junit.framework.TestCase;
import org.mockito.Mockito;

public class ParamHandlerTest extends TestCase {

    public void testHandle() throws JExpError {
        ParamHandler handler = new ParamHandler(":id",1);
        Request request= Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next= Mockito.mock(Next.class);
        handler.handle(request,response,next);
        Mockito.verify(request,Mockito.times(1)).readParam(":id",1)
        ;Mockito.verify(next,Mockito.times(1)).next();
    }
}