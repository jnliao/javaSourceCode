package model;

/**
 * @author jinneng.liao
 */
public class HelloService implements IHello {
    public void morning(String name) {
        System.out.println("Good morning, " + name);
    }
}
