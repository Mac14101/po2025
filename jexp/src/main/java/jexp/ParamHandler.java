package jexp;

public class ParamHandler implements Handler {
    private String name;
    private int depth;

    public ParamHandler(String name) {
        this.name = name;
        this.depth = 0;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        request.readParam(this.name, this.depth);
        next.next();
    }
}
