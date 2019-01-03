package haikuvm.pc.tools;

public enum Condition {
    MAIN, CLINIT,
    ;
    String desc="";
    public Condition desc(String desc) {
        this.desc=desc;
        return this;
    }
}
