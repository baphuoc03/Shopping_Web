package fpoly.duantotnghiep.shoppingweb.config.security;

import fpoly.duantotnghiep.shoppingweb.service.seucrity.CustomerService;
import fpoly.duantotnghiep.shoppingweb.service.seucrity.UserAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityCustomerConfig {
    private final CustomerService customerService;

    public SecurityCustomerConfig(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean("Filter-user")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.disable())
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                                .anyRequest().permitAll()
                )
                .userDetailsService(customerService)
                .formLogin(login -> login.loginPage("/dang-nhap")
                        .loginProcessingUrl("/dang-nhap")
                        .defaultSuccessUrl("/trang-chu", false)
                        .failureUrl("/dang-nhap/error")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
//                .httpBasic(Customizer.withDefaults())
                .logout(l -> l
                        .logoutUrl("/logout")
                                .logoutSuccessUrl("/trang-chu")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true)
                );

        return http.build();
    }

//    @Bean("Encoder-user")
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
