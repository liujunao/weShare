package common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author lenovo
 * @date 2017/10/2
 */
public class HtmlCommon {

    //region -- 获取请求参数
    /**
     * 获取请求的参数
     * @param request HttpServletRequest 对象
     * @param name 参数的键
     * @return 返回结果的字符串
     */
    public  String getParameter(HttpServletRequest request,String name){
        String result="";
        if(request.getParameter(name)!=null){
            result=request.getParameter(name);
        }
        return result;
    }
    //endregion

    /**
     * 弹框并跳转
     *
     * @param response HttpServletResponse response
     * @param msg      弹框信息
     * @param url      跳转地址
     * @throws IOException
     */
    public void showMsg(HttpServletResponse response, String msg, String url) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.println("<script type='text/javascript'>");
        out.println("alert('" + msg + "');window.location='" + url + "'");
        out.println("</script>");
        out.flush();
        out.close();
    }

    public void redirect(HttpServletResponse response, String url) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.flush();
        out.println("<script type='text/javascript'>");
        out.println("window.location='" + url + "'");
        out.println("</script>");
        out.flush();
        out.close();
    }

    //弹框
    public void showMsg(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.flush();
        printWriter.write("<script>");
        printWriter.write("alert('" + msg + "');history.back(-1);");
        printWriter.write("<script>");
        printWriter.flush();
        printWriter.close();

    }

    public HttpSession getSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        return servletRequestAttributes.getRequest();
    }
}
