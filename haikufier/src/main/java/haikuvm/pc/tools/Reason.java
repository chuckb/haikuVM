package haikuvm.pc.tools;

public class Reason {

    private String classname;
    private int id;
    private String reason;
    static private int count;

    public Reason(String classname, String reason) {
        this.classname=classname;
        this.reason=reason;
    }

    public String getClassname() {
        return classname;
    }

    public Reason useit() {
        id=++count;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public int getId() {
        return id;
    }

}
