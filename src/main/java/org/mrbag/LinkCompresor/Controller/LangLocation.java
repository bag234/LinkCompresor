package org.mrbag.LinkCompresor.Controller;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class LangLocation extends AcceptHeaderLocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		
		if (request.getLocale().getLanguage().equalsIgnoreCase("ru"))
			return Locale.of("ru");
		
		return Locale.ENGLISH;
	}
	
	@Bean
	LocaleResolver getMe() {
		return this;
	}
	
}
