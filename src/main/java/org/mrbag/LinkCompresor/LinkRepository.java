package org.mrbag.LinkCompresor;

import java.time.LocalDateTime;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;
import org.mrbag.LinkCompresor.Entity.KeyLinkAttach;
import org.mrbag.LinkCompresor.Entity.Link;
import org.mrbag.LinkCompresor.Layer.Layer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LinkRepository {

	private final RedisTemplate<String, Link> links;  
	
	private final RedisTemplate<String, String> alias;
	
	private final IStringKeyGenerator generator; 

	private final RedisTemplate<String, Layer> layers;

	private final int MAX_STEP_UNIQUE = 20;
	
	public LinkRepository(
			@Qualifier("mainTemplate") RedisTemplate<String, Link> template,
			@Qualifier("aliasTemplate") RedisTemplate<String, String> alias,
			RedisTemplate<String, Layer> layers,
			IStringKeyGenerator generator
			) {
		this.links = template;
		this.alias = alias;
		this.layers = layers;
		this.generator = generator;
	}
	
	public String put(Link link) {
		if(alias.hasKey(link.getLink())) {
			return alias.opsForValue().get(link.getLink()); 
		}
		
		int i = 0;
		while(links.hasKey(generator.get())) {
			generator.next();
			if (i >= MAX_STEP_UNIQUE) {
				log.warn("OWERFLOW List for save data");
			
				throw new RuntimeException("Wrong data, pleas late");
			}
		}
		
		alias.opsForValue().set(link.getLink(), generator.get());
		links.opsForValue().set(generator.get(), link);
		return generator.next();
	}

	public KeyLinkAttach get(String key) {
		if (links.hasKey(key))
			return new KeyLinkAttach(key, links.opsForValue().get(key));
		return new KeyLinkAttach(key, null);
	}
	
	public Link getLink(String key){
		if ( key != null && links.hasKey(key))
			return links.opsForValue().get(key);
		return new Link();
	}
	
	public void layer(String key, String ip){
		layers.opsForSet().add(key, new Layer(ip, LocalDateTime.now()));
	}
}
