package baseclass;

import java.lang.reflect.Method;

/**
 * @author jinneng.liao
 */
public class MethodInvoke {

    public static void main(String[] args) throws Exception {
        Method animalMethod = Animal.class.getDeclaredMethod("print");
        Method catMethod = Cat.class.getDeclaredMethod("print");

        Animal animal = new Animal();
        Cat cat = new Cat();

        // 使用method.invoke()执行方法调用时，初始获取method对象时，可以先调用一次setAccessable(true) （使override=true），
        // 使得后面每次调用invoke()时，节省一次类和方法修饰符的判断，略微提升性能。业务允许的情况下，Field同样可以如此操作。
        animalMethod.setAccessible(true);
        // 按照多态的特性，子类调用父类方法，方法执行时会最终链接到子类的方法上
        animalMethod.invoke(cat);
        animalMethod.invoke(animal);

        catMethod.invoke(cat);
        // IllegalArgumentException: object is not an instance of declaring class -> animal不是声明的class（Cat）的实例对象
        catMethod.invoke(animal);
    }

    public static class Animal {
        protected void print() {
            System.out.println("Animal.print()");
        }

    }

    public static class Cat extends Animal {
        @Override
        public void print() {
            System.out.println("Cat.print()");
        }

    }

}
