package baseclass;

import model.HelloService;
import model.IHello;

import java.lang.reflect.InvocationHandler;
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
        Class<HelloService> clazz = HelloService.class;
        IHello proxyInstance =
                (IHello) new CustomJdkProxyFactory(clazz, new AddLogInvocationHandler(clazz)).createProxyInstance();
        proxyInstance.morning("Lily");
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
     * 自定义的jdk代理类工厂
     * (代理类对目标类的方法进行增强)
     */
    public static class CustomJdkProxyFactory {
        // 被代理的目标类class
        private Class<?> clazz;
        // 方法的调用处理类
        private InvocationHandler handler;

        public CustomJdkProxyFactory(Class<?> clazz, InvocationHandler handler) {
            this.clazz = clazz;
            this.handler = handler;
        }

        public Object createProxyInstance() {
            return Proxy.newProxyInstance(
                    clazz.getClassLoader(), // 用于定义代理类的类加载器
                    clazz.getInterfaces(), // 代理类要实现的接口（interface）
                    handler); // 方法的调用处理类
        }
    }

    /**
     * 对原方法增加方法执行前后日志的JDK方法调用类
     */
    public static class AddLogInvocationHandler implements InvocationHandler {
        // 被代理的目标类
        private Class<?> clazz;

        public AddLogInvocationHandler(Class<?> clazz) {
            this.clazz = clazz;
        }

        /**
         * @param proxy 代理类
         * @param method  被代理类的方法
         * @param args 被代理类的方法入参
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String proxyClassName = proxy.getClass().getName();
            String methodName = method.getName();

            System.out.println("originClassName:["+clazz.getName()+"], proxyClassName:["+ proxyClassName +"], " +
                    "methodName:["+ methodName +"], start");

            method.invoke(clazz.newInstance(), args);

            System.out.println("originClassName:["+clazz.getName()+"], proxyClassName:["+ proxyClassName +"], " +
                    "methodName:["+ methodName +"], end");
            return null;
        }
    }

}
