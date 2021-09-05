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
            return enhancer.create();
        }

    }

    /**
     * 对原方法增加方法执行前后日志的cglib方法拦截器
     */
    public static class AddLogMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println(method.getName()+" start");

            methodProxy.invokeSuper(o, objects);

            System.out.println(method.getName()+" end");

            return null;
        }
    }

}
