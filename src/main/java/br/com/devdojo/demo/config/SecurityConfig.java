package br.com.devdojo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }


/*    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("william").password("devdojo").roles("USER")
                .and()
                .withUser("admin").password("devdojo").roles("USER", "ADMIN");
    }*/
            @Bean
            @Override
            public UserDetailsService userDetailsService() {

                PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

                final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
                UserDetails user = userBuilder
                        .username("user")
                        .password("123456")
                        .roles("USER")
                        .build();

                UserDetails admin = userBuilder
                        .username("admin")
                        .password("654321")
                        .roles("USER","ADMIN")
                        .build();

                return new InMemoryUserDetailsManager(user, admin);
            }
}
