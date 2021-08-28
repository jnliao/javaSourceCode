package baseclass;

import model.Student;

import java.lang.reflect.*;
import java.util.Objects;

/**
 * @author jinneng.liao
 */
public class BaseClassTest{

    public static void main(String[] args) throws Exception {
        //testInteger();
        //testClass();
        //testClass3();
        testMethod();

    }

    /**
     * Integer
     */
    public static void testInteger(){
        // 缓存类
        // 自动装箱
        //  默认情况系数，-128 =< i <= 127 的时候，第一次声明会将 i 的值放入缓存中，
        //  第二次j直接取缓存里面的数据，而不是重新创建一个Integer 对象
        Integer i = 10;
        Integer j = 10;
        System.out.println(i == j);

        Integer a = 128;
        Integer b = 128;
        System.out.println(a == b);

        int k = 10;
        System.out.println(k == i);
        int kk = 128;
        System.out.println(kk == a);

        Integer m = new Integer(10);
        Integer n = new Integer(10);
        System.out.println(m == n);

        // equals
        m.equals(n);
        System.out.println(Objects.equals(m,n));

        // 装箱
        Integer x = Integer.valueOf(10);
        // 拆箱
        int y = x.intValue();
    }

    /**
     * Class
     */
    public static void testClass() throws Exception {
        Class<BaseClassTest> aClass1 = BaseClassTest.class;
        Class<? extends BaseClassTest> aClass2 = new BaseClassTest().getClass();
        Class<?> aClass3 = Class.forName("baseclass.BaseClassTest");

        System.out.println("testClass:"+ aClass1.getName());

        Method testClass = BaseClassTest.class.getMethod("testClass");
        testClass.invoke(null);

        Constructor<BaseClassTest> constructor = BaseClassTest.class.getConstructor();
        BaseClassTest baseClassTest = constructor.newInstance();

        BaseClassTest baseClassTest1 = BaseClassTest.class.newInstance();
    }

    public static void testClass2() {

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method);
                if (method.getName().equals("morning")) {
                    System.out.println("Good morning, " + args[0]);
                }
                return null;
            }
        };

        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(), // 传入ClassLoader
                new Class[] { Hello.class }, // 传入要实现的接口
                handler); // 传入处理调用方法的InvocationHandler

        hello.morning("Bob");
    }

    interface Hello {
        void morning(String name);
    }

    public static void testClass3() throws Exception {
        Student student = new Student();

        Class<? extends Student> aClass = student.getClass();
        Field nameField = aClass.getDeclaredField("name");
        nameField.setAccessible(true);

        String name = (String) nameField.get(student);
        System.out.println(name);

        nameField.set(student,"mark");

        String name2 = (String) nameField.get(student);
        System.out.println(name2);

    }


    public static void testMethod() throws Exception {
        Class<BaseClassTest> baseClassTestClass = BaseClassTest.class;
        Method testMethod = baseClassTestClass.getMethod("testMethod2",String.class,Integer.class);

        testMethod.invoke(BaseClassTest.class.newInstance(),"jack",18);
        System.out.println(testMethod.getName());

        Method testMethod2 = baseClassTestClass.getMethod("testMethod2");
        testMethod2.invoke(BaseClassTest.class.newInstance());

        Method[] methods = baseClassTestClass.getMethods();
    }

    public static void testMethod2(){
        System.out.println("null");
    }

    public static void testMethod2(String name){
        System.out.println(name);
    }

    public static void testMethod2(String name,Integer age){
        System.out.println(name+"-"+age);
    }


}
