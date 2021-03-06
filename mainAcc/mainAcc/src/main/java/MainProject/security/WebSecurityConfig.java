package MainProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import MainProject.security.jwt.AuthEntryPointJwt;
import MainProject.security.jwt.AuthTokenFilter;
import MainProject.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers("/register/**").permitAll().and()
				.authorizeRequests().antMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated();
//				.authorizeRequests().antMatchers("/register/**").permitAll()
//				.antMatchers("/h2-console/**").permitAll()
//				.antMatchers("/eureka/**").permitAll()
//				.antMatchers(HttpMethod.GET, "/") .hasRole("ADMIN")
//				.antMatchers("/manage/health**").permitAll()
//				.antMatchers("/manage/**").hasRole("ACTUATOR")
//				.antMatchers("/eureka/css/**","/eureka/images/**","/eureka/fonts/**", "/eureka/js/**").permitAll()
//				.antMatchers("/eureka/**").hasRole("SYSTEM")
//				.anyRequest().authenticated();

//		http.csrf().disable().antMatcher("/**")
//				.authorizeRequests()
//				.anyRequest().permitAll();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}
