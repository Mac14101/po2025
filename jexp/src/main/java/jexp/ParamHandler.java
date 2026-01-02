package jexp;

public class ParamHandler implements Handler {
    private String name;
    private int depth;
    public ParamHandler(String name, int depth) {
        this.name = name;
        this.depth = depth;
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        request.readParam(this.name, this.depth);
        next.next();
    }
}
