package glavny.inf.elte.hu.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SingSingWebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/extjs/**").permitAll()
                .antMatchers(HttpMethod.GET,"/").authenticated()
                .antMatchers(HttpMethod.GET, "/prisoncell/*","prisoncell/free_space").authenticated()
                .antMatchers(HttpMethod.GET, "/prisoner/*").authenticated()
                .antMatchers(HttpMethod.GET,"/prisoner/release_date/*").authenticated()
                .antMatchers(HttpMethod.GET,"/user/self").authenticated()
                .antMatchers(HttpMethod.POST,"/prisoncell/new","prisoncell/delete").authenticated()
                .antMatchers(HttpMethod.POST, "/prisoner/new","/prisoner/save").authenticated()
                .antMatchers(HttpMethod.POST,"/user/password").authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl( "/user/dispatch" )
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new SingSingUserService();
    }
}