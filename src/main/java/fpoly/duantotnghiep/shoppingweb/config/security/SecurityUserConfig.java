package fpoly.duantotnghiep.shoppingweb.config.security;

import fpoly.duantotnghiep.shoppingweb.service.impl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityUserConfig {
    private final UserService userService;

    public SecurityUserConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean("user")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] adminPermitAll = {"/user/AngularJs/**", "/user/assets/**", "/user/css/**", "/user/images/**", "/user/js/**",
                "/user/quen-mat-khau", "/image/**", "/user/dat-lai-mat-khau/**"};
        http
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                                .anyRequest().permitAll()
                )
                .userDetailsService(userService)
                .formLogin(login -> login.loginPage("/dang-nhap")
                        .loginProcessingUrl("/dang-nhap")
                        .defaultSuccessUrl("/home", false)
                        .failureUrl("/dang-nhap?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
//                .httpBasic(Customizer.withDefaults())
                .logout(l -> l
//                        .logoutUrl("/admin/logout")
                                .logoutSuccessUrl("/dang-nhap")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                );

        return http.build();
    }

    @Bean("userPassword")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
