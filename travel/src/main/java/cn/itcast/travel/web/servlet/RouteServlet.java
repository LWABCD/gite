package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.AddFavoriteService;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.AddFavoriteServiceImpl;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.sun.tools.classfile.StackMapTable_attribute;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    private AddFavoriteService addFavoriteService=new AddFavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接收rname线路名称
        String rname = request.getParameter("rname");
        if(rname!=null) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }

            int cid = 0;
        //2.处理参数,还要考虑当前台传递的cid为"null"的情况
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

            //当前页码，如果不传递，则默认为第1页
            int currentPage = 0;
            if (currentPageStr != null && currentPageStr.length() > 0) {
                currentPage = Integer.parseInt(currentPageStr);
            } else {
                currentPage = 1;
            }

            //每页显示条数，如果不传递，则每页默认显示5条记录
            int pageSize = 0;
            if (pageSizeStr != null && pageSizeStr.length() > 0) {
                pageSize = Integer.parseInt(pageSizeStr);
            } else {
                pageSize = 5;
            }

            PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
            writeValue(routePageBean, response);
        }

    /**
     * 查询Route对象
     * @param request
     * @param response
     * @throws IOException
     */
        public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
            //接收rid
            int rid=Integer.parseInt(request.getParameter("rid"));
            //查询route对象
            Route route=routeService.findOne(rid);
            //响应到前台
            writeValue(route, response);
        }

    /**
     * 判断用户是否收藏过该路线
     * @param request
     * @param response
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取rid
        int rid=Integer.parseInt(request.getParameter("rid"));
        //判断用户是否登录
        User user= (User) request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            //用户尚未登录
            uid=0;
        }else{
            //用户已经登录
            uid=user.getUid();
        }
        //调用FavoriteService查询用户是否收藏过该路线
        boolean flag=favoriteService.isFavorite(rid, uid);
        //把数据响应到前台
        writeValue(flag, response);
    }

    /**
     * 添加用户收藏的信息
     * @param request
     * @param response
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response){
        //接收rid
        int rid= Integer.parseInt(request.getParameter("rid"));
        //获取登录用户的uid
        User user= (User) request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            //用户尚未登录
            return;
        }else{
            //用户已登录
            uid=user.getUid();
        }
        //调用addFavoriteService插入用户收藏的信息
        addFavoriteService.addFavorite(rid,new Date(),uid);
    }

    }
