package baseclass;

import model.HelloService;
import model.IHello;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author jinneng.liao
 * CGLIB 代理
 */
public class CglibProxy {

    public static void main(String[] args) {
        testProxy();
    }

    public static void testProxy() {
        // 不能对接口进行代理
//        IHello proxyInstance = (IHello) new CustomCglibProxyFactory(IHello.class, new AddLogMethodInterceptor()).createProxyInstance();
//        proxyInstance.morning("jack");

        System.out.println("====================");

        // 对类进行代理
        HelloService helloServiceInstance =
                (HelloService) new CustomCglibProxyFactory(HelloService.class,new AddLogMethodInterceptor()).createProxyInstance();
        helloServiceInstance.morning("jack");

        System.out.println("====================");

        // 继承代理类
//        System.out.println(helloServiceInstance.getClass().getSuperclass());
//        for (Class<?> anInterface : helloServiceInstance.getClass().getInterfaces()) {
//            // 实现 net.sf.cglib.proxy.Factory
//            System.out.println(anInterface.getName());
//        }
    }


    /**
     * 自定义的cglib代理类工厂
     */
    public static class CustomCglibProxyFactory {
        // 被代理类的class
        private Class<?> clazz;
        // cglib方法拦截器
        private MethodInterceptor methodInterceptor;

        public CustomCglibProxyFactory(Class<?> clazz, MethodInterceptor methodInterceptor) {
            this.clazz = clazz;
            this.methodInterceptor = methodInterceptor;
        }

        public Object createProxyInstance() {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(methodInterceptor);
            enhancer.setUseCache(true);//默认为true,缓存代理类；如果不缓存，则每次将生成新的代理类，可能导致oom
            return enhancer.create();
        }

    }

    /**
     * 对原方法增加方法执行前后日志的cglib方法拦截器
     */
    public static class AddLogMethodInterceptor implements MethodInterceptor {

        /**
         * @param o cglib代理类
         * @param method 拦截的方法(被代理类的方法)
         * @param objects 方法的入参(被代理类的方法的入参)
         * @param methodProxy cglib代理类的方法
         * @return
         * @throws Throwable
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String proxyClassName = o.getClass().getName();
            String methodName = method.getName();

            System.out.println("proxyClassName:["+ proxyClassName +"], methodName:["+ methodName +"], start");

            methodProxy.invokeSuper(o, objects);

            System.out.println("proxyClassName:["+ proxyClassName +"], methodName:["+ methodName +"], end");

            return null;
        }
    }

}
