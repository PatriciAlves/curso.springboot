package curso.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	private ImprlementacaoUserDetailService implImprlementacaoUserDetailService;

	@Override // configura as solicitações de acesso por http
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()// desativa as configurações padrões do spring
				.authorizeRequests() // permitir restringir acessos
				.antMatchers(HttpMethod.GET, "/").permitAll() // qualquer usuario acessa inicialmente
				.anyRequest().authenticated().and().formLogin().permitAll().and().logout() // mapeia a url e invalida
																							// usuario nao autenticado
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

	@Override // cria autenticação com o banco de dados ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*auth.userDetailsService(implementacaoUserDetailService)
		.passwordEncoder(NoOpPasswordEncoder.getInstance());*/
		

		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("patricia")
				.password("1234").roles("ADMIN");
		

	}

	@Override // Ignora url especificas
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/materialize/**");

	}

}
