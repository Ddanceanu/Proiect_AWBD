package ro.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/autori/**",
                                "/categorii/**",
                                "/cititori/**",
                                "/edituri/**",
                                "/imprumuturi/**",
                                "/roluri/**",
                                "/utilizatori/**",
                                "/carti/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/autori/nou", "/autori/editare/**", "/autori/stergere/**",
                                "/categorii/nou", "/categorii/editare/**", "/categorii/stergere/**",
                                "/cititori/nou", "/cititori/editare/**", "/cititori/stergere/**",
                                "/edituri/nou", "/edituri/editare/**", "/edituri/stergere/**",
                                "/imprumuturi/nou", "/imprumuturi/editare/**", "/imprumuturi/stergere/**",
                                "/roluri/nou", "/roluri/editare/**", "/roluri/stergere/**",
                                "/utilizatori/nou", "/utilizatori/editare/**", "/utilizatori/stergere/**",
                                "/carti/nou", "/carti/editare/**", "/carti/stergere/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/carti", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
