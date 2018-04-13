package com.beyondlmz.config;

import com.beyondlmz.security.LoginSuccessHandler;
import com.beyondlmz.serviceImpl.UserCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by liumingzhong on 2017/12/19.
 */
@Configuration          // 配置文件
@EnableWebSecurity      // 开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true)  //AOP
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //路由策略和访问权限的简单配置
        http
                .authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers("/css/**", "/js/**","/img/**",
                        "/webjars/**", "**/favicon.ico", "/wx.action",
                        "/wx/**","/wxToken.action",
                        "/wxCallBack.action","/index/**").permitAll()
                // ROLE_USER的权限才能访问的资源
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                // 任何尚未匹配的URL只需要验证用户即可访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.action")//启用自定义登陆页面
                .failureUrl("/login.action?error")         //登陆失败返回URL:/login?error
                .defaultSuccessUrl("/index/index.action")         //登陆成功跳转URL
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout.action")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/login.action").permitAll();                       //登陆页面全部权限可访问

        super.configure(http);
    }

    /**
     * 配置内存用户
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("beyondlmz").password("123").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
    }

}