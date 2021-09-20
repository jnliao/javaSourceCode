package simplecollection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jinneng.liao
 * 简单集合
 */
public class SimpleCollectionTest {

    public static void main(String[] args) {
        testArrayList();
    }

    public static void testArrayList(){
        // 数据结构与构造函数
        // 数据结构为数组，元素存放在此数组中 - Object[] elementData
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("a");

        ArrayList<String> arrayList2 = new ArrayList<>(20);

        // 将目标集合转成数组并赋值给集合的数组变量-elementData
        ArrayList<String> arrayList3 = new ArrayList<>(arrayList1);

        MyList strings1 = new MyList();
        MyList strings2 = new MyList(strings1);

        // 常用方法
        // add(E e)方法
        ArrayList<String> strList1 = new ArrayList<>();
        strList1.add("a");

        ArrayList<String> strList2 = new ArrayList<>(0);
        strList2.add("a");

        ArrayList<String> strList3 = new ArrayList<>(1);
        strList3.add("a");
        strList3.add("a");

        // add(int index, E element)方法
        // 往指定位置添加元素(原位置及之后的元素整体后移一位)
        ArrayList<String> strList4 = new ArrayList<>(5);
        strList4.add(0,"a");
        strList4.add(0,"b");
        strList4.add(0,"c");
        strList4.add(1,"d");
        System.out.println(strList4);

        // addAll(Collection c)方法
        ArrayList<String> strList5 = new ArrayList<>(6);
        strList5.add("a");
        ArrayList<String> strList6 = new ArrayList<>(6);
        strList6.add("a");
        strList6.add("b");

        strList5.addAll(strList6);
        System.out.println(strList5);

    }

    static class MyList extends ArrayList<String>{
        public MyList(int initialCapacity) {
            super(initialCapacity);
        }

        public MyList() {
            super();
        }

        public MyList(Collection<? extends String> c) {
            super(c);
        }

        @Override
        public String[] toArray() {
            return new String[]{"a","b","c"};
        }
    }

    public static void tesPlusPlus(){
        String[] strArray = {"a", "b", "c"};
        int i = 0;
        strArray[i++] = "q";
        strArray[i++] = "w";
        strArray[i++] = "e";

//        strArray[++i] = "q";
//        strArray[++i] = "w";
//        strArray[++i] = "e";
        System.out.println(i);
    }



}
