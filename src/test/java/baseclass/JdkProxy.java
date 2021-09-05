package baseclass;

import model.HelloService;
import model.IHello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jinneng.liao
 * JDK 动态代理
 */
public class JdkProxy {

    public static void main(String[] args) throws Exception {
        testProxy();
    }

    public static void testProxy() {
        // 1、生成接口（IHello）的代理类-重写接口的方法
        IHello helloProxyInstance = (IHello) new HelloProxy().getProxyInstance();
        helloProxyInstance.morning("jack");

        System.out.println("====================");

        // 2、生成目标类（HelloService）实现的接口（IHello）的代理类-重写并增强目标类的方法
        MethodProxyAdvance methodProxyAdvance = new MethodProxyAdvance();
        methodProxyAdvance.setTarget(new HelloService());
        IHello helloServiceProxyInstance = (IHello) methodProxyAdvance.getProxyInstance();
        helloServiceProxyInstance.morning("jack");

    }

    /**
     * 代理类实现目标接口的方法
     */
    public static class HelloProxy {
        // 方法调用处理类实现 InvocationHandler 接口，重写 invoke 方法，
        // 在invoke方法中重写接口的方法(或对原方法进行增强)
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                System.out.println("Good morning, " + args[0]);
                return null;
            }
        };

        public Object getProxyInstance() {
            return Proxy.newProxyInstance(
                    IHello.class.getClassLoader(), // 用于定义代理类的类加载器
                    new Class[]{IHello.class}, // 代理类要实现的接口（interface）
                    handler); // 方法的调用处理类
        }
    }

    /**
     * 代理类对目标类的方法进行增强
     */
    public static class MethodProxyAdvance {
        // 需要被代理的目标类
        private Object target;

        public void setTarget(Object target) {
            this.target = target;
        }

        // 方法调用处理类实现 InvocationHandler 接口，重写 invoke 方法，
        // 在invoke方法中重写接口的方法，对原方法进行增强
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                System.out.println("Method start");

                method.invoke(target, args);

                System.out.println("Method end");
                return null;
            }
        };

        public Object getProxyInstance() {
            return Proxy.newProxyInstance(
                    target.getClass().getClassLoader(), // 用于定义代理类的类加载器
                    target.getClass().getInterfaces(), // 代理类要实现的接口（interface）
                    handler); // 方法的调用处理类
        }
    }

}
