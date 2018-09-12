package com.zhaodongxx.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;


/**
 * <P></P>
 *
 * @author zhaodong
 * @version v1.0
 * @since 2018/1/31 15:31
 * @Description 日志拦截器
 * 记录信息: 访问时间-Controller路径-对应方法名-请求参数信息-请求相对路径-请求处理时长
 */
@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {
    //public static final Logger LOGGER = Logger.getLogger(LogInterceptor.class);

    @Resource
    public MyWebMvcConfigurer webMvcConfigurer;

    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

    //private String getParamString(MappingChange.Map<String, String[]> map) {
    //    StringBuilder sb = new StringBuilder();
    //    for(Map.Entry<String,String[]> e:map.entrySet()){
    //        sb.append(e.getKey()).append("=");
    //        String[] value = e.getValue();
    //        if(value != null && value.length == 1){
    //            sb.append(value[0]).append("\t");
    //        }else{
    //            sb.append(Arrays.toString(value)).append("\t");
    //        }
    //    }
    //    return sb.toString();
    //}

    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Throwable e) {
        if (e == null){
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 该方法将在请求处理之前进行调用。
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        startTimeThreadLocal.set(startTime);        //线程绑定变量（该数据只有当前请求的线程可见）
        if (handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("-----------------------开始计时:").append(new SimpleDateFormat("hh:mm:ss.SSS").format(startTime)).append("-------------------------------------\n");
            HandlerMethod h = (HandlerMethod) handler;
            sb.append("Controller: ").append(h.getBean().getClass().getName()).append("\n");
            sb.append("Method    : ").append(h.getMethod().getName()).append("\n");
            //sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
            sb.append("URI       : ").append(request.getRequestURI()).append("\n");
            log.debug(sb.toString());
            log.info("【"+request.getRequestURI() + "】  Controller: " +h.getBean().getClass().getName()
                    +"  -IP: "+webMvcConfigurer.getIpAddress( request));
        }
        return true;
    }

    /**
     * 在当前请求进行处理之后，也就是Controller 方法调用之后执行，但是它会在DispatcherServlet 进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller 处理之后的ModelAndView 对象进行操作。
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //long startTime = (Long) request.getAttribute("startTime");
        //long endTime = System.currentTimeMillis();
        //long executeTime = endTime - startTime;
        //if(handler instanceof HandlerMethod){
        //    StringBuilder sb = new StringBuilder(1000);
        //    sb.append("CostTime  : ").append(executeTime).append("ms").append("\n");
        //    sb.append("-------------------------------------------------------------------------------");
        //    LOGGER.debug(sb.toString());
        //}
    }


    /**
     * 该方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。这个方法的主要作用是用于进行资源清理工作的。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 打印JVM信息。
        if (log.isDebugEnabled()){
            long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis();  //2、结束时间

            //如果controller报错，则记录异常错误
            if(ex != null){
                log.debug("Controller异常: " + getStackTraceAsString(ex));
            }

            //log.debug("计时结束："+ new SimpleDateFormat("hh:mm:ss.SSS").format(endTime) + " 耗时：" + DateUtils.formatDateTime(endTime - beginTime) + " URI:" +
            //        request.getRequestURI()+ " 最大内存: " +Runtime.getRuntime().maxMemory()/1024/1024+ "m  已分配内存: " +Runtime.getRuntime().totalMemory()/1024/1024+ "m  已分配内存中的剩余空间: " +Runtime.getRuntime().freeMemory()/1024/1024+ "m  最大可用内存: " +
            //        (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024 + "m");
            startTimeThreadLocal.remove();
        }
    }



}
