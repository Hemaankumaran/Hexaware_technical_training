package com.springboot.automobileinsurancesystem.config;

import com.springboot.automobileinsurancesystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    // remember you can call add(user) only from one place in whole project,
    // otherwise circular dependency

    private final UserService userService;
    private final JwtFilter jwtFilter;

//    @Bean
//    public UserDetailsService users() { // hardcoded users -> authenticated ones
//        UserDetails customer = User.builder()
//                .username("me")
//                .password("{noop}welcome")
//                .authorities("CUSTOMER") // prefer authorities
//                .build();
//        UserDetails officer = User.builder()
//                .username("you")
//                .password("{noop}blahblah")
//                .authorities("OFFICER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}admin")
//                .authorities("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(customer, officer, admin);
//    }

    @Bean
    // ADMIN -> admin - admin
    // OFFICER -> officer - officer
    // OFFICER -> dhoni - dhoni
    // OFFICER -> bumrah - bumrah
    // CUSTOMER -> neg - neg
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // disable csrf - Cross Site Request Forgery
                .cors(Customizer.withDefaults()) // enable cors - Cross Origin Resource Sharing

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // admin
                        .requestMatchers(HttpMethod.POST, "/api/admin/add")
                                .permitAll()

                        // auth
                        .requestMatchers(HttpMethod.GET, "/api/auth/login")
                                .authenticated()

                        // claim
                        .requestMatchers(HttpMethod.POST,"/api/claim/add/{customerPolicyId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/claim/get/{claimId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/claim/get/status/{claimStatus}")
                                .hasAnyAuthority("OFFICER", "ADMIN")

                        // customer
                        .requestMatchers(HttpMethod.POST,"/api/customer/add")
                                .authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/customer/signup")
                                .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/customer/get")
                                .hasAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/api/customer/get/{id}")
//                                .hasAnyAuthority("OFFICER", "ADMIN")

                        // customer_policy
                        .requestMatchers(HttpMethod.POST,"/api/customerpolicy/add/{policyId}/{customerId}/{officerId}/{vehicleId}")
                                .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/customerpolicy/add/{policyId}/{officerId}/{vehicleId}")
                                .hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT,"/api/customerpolicy/quote/{customerPolicyId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/customerpolicy/get/customer/{customerId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/customerpolicy/update/status/{customerPolicyId}/{policyStatus}")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/customerpolicy/update/officer/{customerPolicyId}/{officerId}")
                                .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/customerpolicy/approve/{customerPolicyId}/{startDate}")
                                .hasAuthority("OFFICER")
                        .requestMatchers(HttpMethod.DELETE,"/api/customerpolicy/delete/{customerPolicyId}")
                                .hasAuthority("ADMIN")

                        // HCV, ICV, LCV
                        .requestMatchers(HttpMethod.POST,"/api/hcv/add/{vehicleId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/hcv/get/{HCVId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/icv/add/{vehicleId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/icv/get/{ICVId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/lcv/add/{vehicleId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/lcv/get/{LCVId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")

                        // officer
                        .requestMatchers(HttpMethod.POST,"/api/officer/add")
                                .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/officer/signup")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/officer/get/{id}")
                                .hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/officer/get/customer")
                                .hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/officer/get/designation")
                                .hasAnyAuthority("OFFICER", "ADMIN")

                        // payment
                        .requestMatchers(HttpMethod.POST,"/api/payment/add/{customerPolicyId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/payment/get/{paymentId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")

                        // policy
                        .requestMatchers(HttpMethod.POST,"/api/policy/add")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/policy/get")
                                .permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/policy/get/{policyId}")
                                .authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/policy/get/filter")
                                .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/policy/suggestions")
                                .permitAll()

                        // vehicle
                        .requestMatchers(HttpMethod.POST,"/api/vehicle/add")
                                .authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/vehicle/get/{vehicleId}")
                                .hasAnyAuthority("OFFICER", "ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/vehicle/get/customer")
                                .hasAuthority("CUSTOMER")

                        .anyRequest().permitAll() // Any API not listed above
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults()); // Uses Basic Authentication // Spring understands that I am using this technique for hardcoded users
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }
    */
}

// Phase 0
// pom.xml -> starter.security -> username: user
//                             -> password: generates automatically
//
// Phase I
// spring docs -> In-Memory Authentication -> hardcoded users
//
// spring docs -> SecurityFilterChain(java)
// CSRF disabled → Usually done for REST APIs (especially when using tools like Postman or stateless APIs)
// CORS enabled → Allows frontend apps
// OPTIONS -> preflight requests for CORS
//
// Phase II
// User class -> inject in Customer & Officer
// save user and customer entities
// add password encryption
// add AuthenticationManager(Bean) to change from hardcoded users to db saved users
// implement UserDetails in UserDetailsService, UserDetails in User

// IMP -> after setting the PasswordEncoder, don't use the old credentials where password is not encrypted!!

// Phase III -> JWT Token
// dependencies - JJWT core, JJWT impl, JJWT Jackson
// JwtUtility
// JwtFilter - extends OncePerRequestFilter
// SecurityConfig
// remove authentication manager, addFilterBefore() in securityConfig
/*
change -> .anyRequest().authenticated() -> .anyRequest().permitAll()
because, before attempting to permitAll() in requestMatchers,
it hits jwtFilter, so it checks for token, nothing, so 401
 */
// AuthController