package in.HCL.sanjib.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import in.HCL.sanjib.constants.UserRoles;

//@Configuration
//@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// Authentication object
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Authorize URLs
		http.authorizeRequests()

				// .antMatchers("/patient/register","/patient/save").permitAll()
				// .antMatchers("/patient/all").hasAuthority(UserRoles.ADMIN.name())
				// .antMatchers("/doctor/**").hasAuthority(UserRoles.ADMIN.name())
				// .antMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())

				.antMatchers("/patient/register","/patient/save","/user/showForgot","/user/genNewPwd").permitAll()
				.antMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())
				.antMatchers("/doctor/**").hasAuthority(UserRoles.ADMIN.name())
				.antMatchers("/appointment/register", "/appointment/save", "/appoinrment/all").hasAnyAuthority(UserRoles.ADMIN.name())
				.antMatchers("/appointment/view", "/appointment/viewSlot").hasAuthority(UserRoles.PATIENT.name())
		        .antMatchers("/slots/all","/slots/accept","/slots/reject").hasAuthority(UserRoles.ADMIN.name())
				.antMatchers("/slot/book").hasAuthority(UserRoles.PATIENT.name()).antMatchers("/user/login", "/login")
				.permitAll()

				.anyRequest().authenticated()

				.and().formLogin().loginPage("/user/login") // show Login Page
				.loginProcessingUrl("/login") // POST(do login)
				.defaultSuccessUrl("/user/setup", true).failureUrl("/user/login?error=true") // if login is failed

				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL
				.logoutSuccessUrl("/user/login?logout=true");

		// fORM Configuration

		// Logout details

		// exception handling

	}
	

}
