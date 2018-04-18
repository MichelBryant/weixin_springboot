package com.beyondlmz;

import com.beyondlmz.interceptor.LoginInterceptor;
import com.beyondlmz.interceptor.MyInterceptor1;
import com.beyondlmz.interceptor.MyInterceptor2;
import com.beyondlmz.servlet.CallBackServlet;
import com.beyondlmz.servlet.IndexQRServlet;
import com.beyondlmz.servlet.WeixinServlet;
import com.beyondlmz.servlet.WeixinTokenServlet;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching
@ComponentScan
@MapperScan("com.beyondlmz.mapper")
public class DemoApplication extends WebMvcConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	/**
	 * 配置数据源DataSource
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource();
	}

	/**
	 * 配置SqlSessionFactory
	 * Mybatis 一次缓存 session
	 * 二级缓存 SqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * 配置事务管理器(TransactionManager)
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}


	/**
	 * SpringBoot 注册Servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean testWxServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WeixinServlet());
		registration.addUrlMappings("/wx.action");
		return registration;
	}

	/**
	 * SpringBoot注册Servlet
	 * 微信授权登录servlet注册
	 * @return
	 */
	@Bean
	public ServletRegistrationBean testWxCallBack() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CallBackServlet());
		registration.addUrlMappings("/wxCallBack.action");
		return registration;
	}

	/**
	 * 微信JS-SDK 调用token验证配置
	 * @return
	 */
	@Bean
	public ServletRegistrationBean testWxTokenServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WeixinTokenServlet());
		registration.addUrlMappings("/wxToken.action");
		return registration;
	}

	/**
	 * 添加拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		//registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/welcome.action");
		//registry.addInterceptor(new MyInterceptor2()).excludePathPatterns("/welcome.action");
		//registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
