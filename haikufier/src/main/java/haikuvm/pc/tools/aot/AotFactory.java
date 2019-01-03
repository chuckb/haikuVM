package haikuvm.pc.tools.aot;


public class AotFactory {

    private String aotOption;

    public AotFactory(String aotOption) {
        this.aotOption=aotOption;
    }

    public Aot get(String clazz) {
        if (aotOption.equals("data")) {
            return new Compile2Data(clazz);
//            return new Compile2Macros(clazz);
        } else {
            return new Compile2C(clazz);
        }
    }

}
