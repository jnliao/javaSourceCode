package baseclass;

import com.sun.org.apache.bcel.internal.generic.ALOAD;

import java.util.Objects;

/**
 * @author jinneng.liao
 */
public class BaseClassTest{

    public static void main(String[] args){
        testInteger();
        //testClass();
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
    public static void testClass(){
        System.out.println("testClass:"+BaseClassTest.class.getName());
    }


}
