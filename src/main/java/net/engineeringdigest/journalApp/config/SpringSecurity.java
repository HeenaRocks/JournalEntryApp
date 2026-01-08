//package net.engineeringdigest.journalApp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SpringSecurity  {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/journal/**").permitAll()  //wildcard pattren
//                        .requestMatchers("/journal/**", "/user/**").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("kkkk")
//                .password(passwordEncoder.encode("hhhhh123"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }
//}


package net.engineeringdigest.journalApp.config;


import net.engineeringdigest.journalApp.filter.JWTFilter;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.swing.text.html.HTML;

//@Configuration
//@EnableWebSecurity
//public class SpringSecurity extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .antMatchers("/journal/**", "/user/**").authenticated()
////                .antMatchers("/admin/**").hasRole("ADMIN")
////                .anyRequest().permitAll();
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
////
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()  //cross site request forgery
//                .authorizeRequests()
//                .antMatchers("/user").permitAll()
//                .antMatchers("/user/**").authenticated() // anything else requires login
//                .antMatchers("/journal/**","/user/**").authenticated()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .anyRequest().permitAll()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        //                .antMatchers("/user").permitAll()       // signup allowed
////                .antMatchers(HttpMethod.POST, "/user").permitAll()
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}

@Configuration
@EnableWebSecurity
//@Profile("dev")
public class SpringSecurity extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .csrf().disable()  //cross site request forgery
                .authorizeRequests().antMatchers("/public/**").permitAll()//USE IT TO CREATE NEW USER AND  lets say if you want to permit without any authorization to anyone
                .antMatchers("/user/**","/journal/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                        .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}