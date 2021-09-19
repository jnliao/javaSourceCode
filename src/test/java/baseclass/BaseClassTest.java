package baseclass;

import model.HelloService;
import model.IHello;
import model.Student;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author jinneng.liao
 */
public class BaseClassTest{
    private String name;

    public BaseClassTest() {
    }

    public BaseClassTest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseClassTest{" +
                "name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) throws Exception {
        //testInteger();
        //testClass();
        //testClass3();
        //testMethod();
        //testConstructor();
        //testProxy();
        //testString();
        testObjects();
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

    public static void testConstructor() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        // 获取构造器类
        Class<BaseClassTest> baseClassTestClass = BaseClassTest.class;
        Constructor<?>[] declaredConstructors = baseClassTestClass.getDeclaredConstructors();
        Constructor<?>[] constructors = baseClassTestClass.getConstructors();
        Constructor<BaseClassTest> constructor = baseClassTestClass.getConstructor(String.class);
        Constructor<BaseClassTest> constructor2 = baseClassTestClass.getDeclaredConstructor(String.class);

        // 获取该构造器的参数列表和类型
        Class<?>[] parameterTypes = constructor2.getParameterTypes();
        Parameter[] parameters = constructor2.getParameters();
        System.out.println(Arrays.toString(parameters));
        System.out.println(Arrays.toString(parameterTypes));

        Class<BaseClassTest> declaringClass = constructor.getDeclaringClass();
        System.out.println(declaringClass.getName());

        // 生成一个构造器的声明类的对象实例
        constructor2.setAccessible(true);
        BaseClassTest baseClass = constructor2.newInstance("BaseClass");
        System.out.println(baseClass.toString());
    }

    public static void testProxy() {
        // 调用处理类实现 InvocationHandler 接口，重写 invoke 方法，在invoke方法中重写接口的方法(或对原方法进行增强)
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("morning")) {
                    System.out.println("Good morning, " + args[0]);
                }
                return null;
            }
        };

        // 1、为接口生成代理类
        IHello helloProxyInstance = (IHello) Proxy.newProxyInstance(
                IHello.class.getClassLoader(), // 用于定义代理类的类加载器
                new Class[]{IHello.class}, // 代理类要实现的接口（interface）
                handler); // 方法的调用处理类

        helloProxyInstance.morning("Bob");

        // 代理类名称 - baseclass.$Proxy0
        Class<? extends IHello> aClass = helloProxyInstance.getClass();
        System.out.println(aClass.getName());
        // 代理类实现的接口 - baseclass.BaseClassTest$Hello
        for (Class<?> anInterface : aClass.getInterfaces()) {
            System.out.println(anInterface.getName());
        }
        // 代理类继承的类 - java.lang.reflect.Proxy
        System.out.println(aClass.getSuperclass().getName());

        // 2、只能代理接口，不能代理类。因为代理类本身已经extends了Proxy，而java是不允许多重继承的
        HelloService helloServiceProxyInstance = (HelloService) Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class[] { HelloService.class },
                handler);

        helloServiceProxyInstance.morning("Bob"); // 报错
    }

    public static void testString() throws NoSuchFieldException, IllegalAccessException {
        // hashcode
        String str = "aa";
        int i = str.hashCode();
        int i2 = str.hashCode();

        char[] chars = new char[]{'a','b','c'};
//        System.out.println(chars[3]);
        String abc = new String(chars);
        System.out.println(abc);
        System.out.println(abc.hashCode());

        // equals
        String a="abc";
        String b="abc";
        String c="fbc";
        int d = 1;

        System.out.println(a.equals(a));
        System.out.println(a.equals(b));
        System.out.println(a.equals(c));
        System.out.println(a.equals(d));

        String a2= new String("abc");
        System.out.println(a.equals(a2));

        // startsWith
        System.out.println(a.startsWith("a",0));
        System.out.println(a.startsWith("a",1));

        // toCharArray
        char[] chars1 = a.toCharArray();

        char[] chars2 = new char[2];
        System.arraycopy(chars1,0,chars2,0,2);
        System.out.println(chars2);

        // 通过反射的方式修改字符串的值
        System.out.println("通过反射的方式修改字符串的值");
        String b1 = "abd";
        System.out.println(b1);

        Field value = b1.getClass().getDeclaredField("value");
        value.setAccessible(true);
        char[] array = (char[]) value.get(b1);
        array[0] = 'A';
        System.out.println(b1);

        // subString  从指定位置（beginIndex）开始，将String的旧数组中指定数量的元素拷贝到新数组中，然后返回String对象
        // 指定数量: Math.min(value.length - beginIndex, endIndex - beginIndex))，其中value指原字符串的数组值
        String subString = "abc";
        System.out.println(subString.substring(1,2)); // b

        // indexOf
        // 作用：获取目标字符串str1在源字符串str2中第一次出现的索引(下标)位置。
        //"abcde".indexOf("bcd") ， "abcde"-源字符串；"bcd"-目标字符串。

        // 原理：(1)先获取目标字符串str1的第一个字符在源字符串str2中的索引位置
        // (2)在上述基础上，依次判断str1剩余字符在str2中是否存在(依次比较单个字符是否相等)，
        // 且字符之间无间隔(即存在且连续)，从str2的索引位置+1开始判断。
        // (3)上述判断均通过，则返回字符串str1在指定字符串str2中第一次出现的索引(下标)位置 i
        System.out.println(subString.indexOf("bcd"));

        // contains
        // 原理：判断目标字符串在源字符串中第一次出现的位置是否大于-1
        subString.contains("bcd");
    }

    public static void testObjects(){
        System.out.println(Objects.equals("a","b"));

        char[] array1 = {'a','b','c'};
        char[] array2 = {'a','b','c'};
        System.out.println(Arrays.equals(array1, array2));

        System.out.println(Objects.equals(array1,array2));
        System.out.println(Objects.deepEquals(array1,array2));
    }
}
