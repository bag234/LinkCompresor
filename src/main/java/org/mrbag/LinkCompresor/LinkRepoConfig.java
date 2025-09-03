package org.mrbag.LinkCompresor;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;
import org.mrbag.LinkCompresor.Entity.Link;
import org.mrbag.LinkCompresor.Entity.StringKeyGenerator.XorShiftStringGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.lettuce.core.RedisURI;

@Configuration
public class LinkRepoConfig {

	RedisConnectionFactory configFactory(RedisConfiguration config) {
		return new LettuceConnectionFactory(config);
	}
	
	@Bean("mainConfig")
	RedisConnectionFactory  mainConfig(@Value("${app.data-main}") String url) {
		return configFactory(LettuceConnectionFactory.createRedisConfiguration(RedisURI.create(url)));
	}
	
	@Bean("aliasConfig")
	RedisConnectionFactory aliasConfig(@Value("${app.data-alias}") String url) {
		return configFactory(LettuceConnectionFactory.createRedisConfiguration(RedisURI.create(url)));
	}
	
	
	
	@Bean("mainTemplate")
	@Primary
	RedisTemplate<String, Link> mainRedisTemplate(@Qualifier("mainConfig") RedisConnectionFactory conf){
		RedisTemplate<String, Link> temp = new RedisTemplate<>();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		temp.setConnectionFactory(conf);
		temp.setKeySerializer(new StringRedisSerializer());
		temp.setValueSerializer(new Jackson2JsonRedisSerializer<Link>(mapper, Link.class));
		
		return temp;
	}
	
	@Bean("aliasTemplate")
	RedisTemplate<String, String> aliasRedisTemplate(@Qualifier("aliasConfig") RedisConnectionFactory conf){
		RedisTemplate<String, String> temp = new RedisTemplate<>();
		
		temp.setConnectionFactory(conf);
		temp.setKeySerializer(new StringRedisSerializer());
		temp.setValueSerializer(new StringRedisSerializer());
		
		return temp;
	}
	
	@Bean
	//XXX only test;
	IStringKeyGenerator configKeyGenerator() {
		return new XorShiftStringGenerator(0);
	}
	
	
}
