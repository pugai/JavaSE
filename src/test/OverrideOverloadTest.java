package test;

/**
 * @author ctl
 * @date 2019/11/27
 */
public class OverrideOverloadTest {
    public static void main(String[] args) {
        String string = "";
        Parent parent = new Child();
        parent.print(string);

    }
}

class Parent {
    void print(Object a) {
        System.out.println("Parent - Object");
    }
}
class Child extends Parent {
    void print(String a) {
        System.out.println("Child - String");
    }
}
