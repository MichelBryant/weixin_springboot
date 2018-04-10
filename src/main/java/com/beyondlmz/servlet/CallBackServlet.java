package com.beyondlmz.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beyondlmz.entity.WxUserInfo;
import com.beyondlmz.service.WeixinService;
import com.beyondlmz.util.AuthorUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * @author liumingzhong
 */
public class CallBackServlet extends HttpServlet {

	private WeixinService weixinService;

	/*@Value(value = "${spring.datasource.url}")
	private String dbUrl;
	@Value(value = "${spring.datasource.driver-class-name}")
	private String dbDriver;
	@Value(value = "${spring.datasource.username}")
	private String dbName;
	@Value(value = "${spring.datasource.password}")
	private String dbPwd;
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet result = null;*/
	private Logger logger = LoggerFactory.getLogger(CallBackServlet.class);

	private WebApplicationContext webApplicationContext;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		weixinService=webApplicationContext.getBean(WeixinService.class);
	}
	/*@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		try{
			this.dbUrl=servletConfig.getInitParameter("dbUrl");
			this.dbDriver=servletConfig.getInitParameter("dbDriver");
			this.dbName=servletConfig.getInitParameter("dbName");
			this.dbPwd=servletConfig.getInitParameter("dbPwd");
			Class.forName(dbDriver);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		HttpSession session = req.getSession();
		logger.info("调用微信回调Servlet获取返回code----------");
		String code = req.getParameter("code");
		logger.info("获得的code为："+code);
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthorUtil.APPID
				+ "&secret="+ AuthorUtil.APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code ";
		try{
			logger.info("根据返回code获取openID及access_token----------");
			JSONObject jsonObject = AuthorUtil.doGetJson(url,"GET");
			String openid=jsonObject.getString("openid");
			String token = jsonObject.getString("access_token");

			String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+token
					+ "&openid="+openid
					+ "&lang=zh_CN ";
			logger.info("根据openID及access_token获取微信用户基本信息----------");
			JSONObject userInfo = AuthorUtil.doGetJson(infoUrl,"GET");
			logger.info("微信用户基本信息"+userInfo+"------------");
			//保存微信用户信息
			if(userInfo!=null&&!userInfo.get("openid").equals("")){
				//WxUserInfo wxUserInfo = getWxUserInfo(openid);
				WxUserInfo wxUserInfo = weixinService.getWxInfoByOpenId(openid);
				if(wxUserInfo.getOpenid()==null||wxUserInfo.getOpenid().equals("")){
					wxUserInfo.setOpenid((String)userInfo.get("openid"));
					wxUserInfo.setSex((int)userInfo.get("sex"));
					wxUserInfo.setCity((String)userInfo.get("city"));
					wxUserInfo.setCountry((String)userInfo.get("country"));
					wxUserInfo.setHeadimgurl((String)userInfo.get("headimgurl"));
					wxUserInfo.setLanguage((String)userInfo.get("language"));
					wxUserInfo.setNickname((String)userInfo.get("nickname"));
					wxUserInfo.setPrivilege(userInfo.get("privilege").toString());
					wxUserInfo.setProvince((String)userInfo.get("province"));
					try{
						weixinService.saveWxUserInfo(wxUserInfo);
					}catch (Exception e){
						throw new RuntimeException("系统异常！");
					}
				}else{
					weixinService.updateWxUserInfo(wxUserInfo);
				}
			}
			session.setAttribute("openId",openid);
				session.setAttribute("wxUser",userInfo);
			//两种登录方式

				//1.使用微信信息直接进行登录，无需注册绑定
			req.getRequestDispatcher("/index/toIndex.action").forward(req, resp);

				//2.将微信信息与现有账号体系进行绑定
				/*String nickName=getNickName(openid);

				if(!"".equals(nickName)&&nickName!=null){
					//绑定成功
					req.setAttribute("nickName", nickName);
					req.getRequestDispatcher("/index1.jsp").forward(req, resp);
				}else{
					//未绑定
					req.getRequestDispatcher("/login1.jsp").forward(req, resp);

				}*/
		}catch(Exception e){
			throw new RuntimeException("系统异常！");
		}
	}
	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		JSONObject userInfo = (JSONObject)req.getSession().getAttribute("userInfo");
		String openid = (String)userInfo.get("openid");
		String nickName = (String)userInfo.get("nickname");
		try{
			int temp = updateUser(openid,nickName, account, password);
			if(temp>0){
				System.out.println("绑定成功");
				req.setAttribute("nickName", nickName);
				req.getRequestDispatcher("/index.action").forward(req, resp);
			}else{
				System.out.println("绑定失败");
				req.getRequestDispatcher("/login.action").forward(req, resp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/

	/**
	 * 根据openID获取系统用户呢称
	 * @param openid
	 * @return
	 */
	/*public String getNickName(String openid){
		String nickname="";
		try {
			conn = DriverManager.getConnection(dbUrl,dbName,dbPwd);
			String sql = "select nickname from t_user where openid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, openid);
			result = ps.executeQuery();
			while(result.next()){
				nickname=result.getString("NICKNAME");
			}
			result.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nickname;
	}*/

	/**
	 * 更新用户信息
	 * @param openid
	 * @param nickName
	 * @param account
	 * @param password
	 * @return
	 */
	/*public int updateUser(String openid,String nickName,String account,String password){
		int temp=0;
		try {
			conn = DriverManager.getConnection(dbUrl,dbName,dbPwd);
			String sql = "update t_user set openid=?,nickname=? where account=? and password=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, openid);
			ps.setString(2, nickName);
			ps.setString(3, account);
			ps.setString(4, password);
			temp = ps.executeUpdate();

			result.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public  boolean saveWxUserInfo(WxUserInfo wxUserInfo){
		boolean flag=false;
		try {
			conn = DriverManager.getConnection(dbUrl,dbName,dbPwd);
			String sql = "insert t_weixin(openid,nickname,sex,language,city,province,country,headimgurl,privilege) values(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, wxUserInfo.getOpenid());
			ps.setString(2, wxUserInfo.getNickname());
			ps.setInt(3, wxUserInfo.getSex());
			ps.setString(4, wxUserInfo.getLanguage());
			ps.setString(5, wxUserInfo.getCity());
			ps.setString(6, wxUserInfo.getProvince());
			ps.setString(7, wxUserInfo.getCountry());
			ps.setString(8, wxUserInfo.getHeadimgurl());
			ps.setString(9, wxUserInfo.getPrivilege().toString());
			 if(ps.executeUpdate()>0){
				 flag=true;
			 }
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
*/
	/**
	 * 根据openID获取微信用户基本信息
	 * @param openid
	 * @return
	 */
	/*public WxUserInfo getWxUserInfo(String openid){
		WxUserInfo wxUserInfo =new WxUserInfo();
		try {
			conn = DriverManager.getConnection(dbUrl,dbName,dbPwd);
			String sql = "select * from t_weixin where openid=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, openid);
			result = ps.executeQuery();
			while(result.next()){
				wxUserInfo.setOpenid(result.getString("OPENID"));
				wxUserInfo.setNickname(result.getString("NICKNAME"));
				wxUserInfo.setSex(result.getInt("SEX"));
				wxUserInfo.setLanguage(result.getString("LANGUAGE"));
				wxUserInfo.setCity(result.getString("CITY"));
				wxUserInfo.setProvince(result.getString("PROVINCE"));
				wxUserInfo.setCountry(result.getString("COUNTRY"));
				wxUserInfo.setHeadimgurl(result.getString("HEADIMGURL"));
				wxUserInfo.setPrivilege(result.getString("PRIVILEGE"));
			}
			result.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wxUserInfo;
	}*/

}
