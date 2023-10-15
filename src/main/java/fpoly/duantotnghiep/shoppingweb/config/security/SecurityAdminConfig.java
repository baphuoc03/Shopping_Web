package fpoly.duantotnghiep.shoppingweb.config.security;

import fpoly.duantotnghiep.shoppingweb.service.impl.UserAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityAdminConfig {
    private final UserAdminService userAdminService;

    public SecurityAdminConfig(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] adminStatic = {"/admin/AngularJs/**","/admin/assets/**","/admin/css/**","/admin/images/**","/admin/js/**"};
        http
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers(adminStatic).permitAll()
//                        .requestMatchers("/detail").hasAnyAuthority("STAFF","ADMIN")
//                        .requestMatchers("/add").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userAdminService)
                .formLogin(login -> login.loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/trang-chu", false)
                        .failureUrl("/admin/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
//                .httpBasic(Customizer.withDefaults())
                .logout(l -> l
//                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
