package org.mrbag.LinkCompresor;

import org.mrbag.LinkCompresor.Entity.IStringKeyGenerator;
import org.mrbag.LinkCompresor.Entity.KeyLinkAttach;
import org.mrbag.LinkCompresor.Entity.Link;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LinkRepository {

	private final RedisTemplate<String, Link> template;  
	
	private final RedisTemplate<String, String> alias;
	
	private final IStringKeyGenerator generator; 

	private final int MAX_STEP_UNIQUE = 20;
	
	public LinkRepository(
			@Qualifier("mainTemplate") RedisTemplate<String, Link> template,
			@Qualifier("aliasTemplate") RedisTemplate<String, String> alias,
			IStringKeyGenerator generator
			) {
		this.template = template;
		this.alias = alias;
		this.generator = generator;
	}
	
	public String put(Link link) {
		if(alias.hasKey(link.getLink())) {
			return alias.opsForValue().get(link.getLink()); 
		}
		
		for (int i = 0; i < MAX_STEP_UNIQUE && template.hasKey(generator.get()); i++) {
			generator.next();
		}
		
		alias.opsForValue().set(link.getLink(), generator.get());
		template.opsForValue().set(generator.get(), link);
		return generator.next();
	}

	public KeyLinkAttach get(String key) {
		if (template.hasKey(key))
			return new KeyLinkAttach(key, template.opsForValue().get(key));
		return new KeyLinkAttach(key, null);
	}
	
	
}
