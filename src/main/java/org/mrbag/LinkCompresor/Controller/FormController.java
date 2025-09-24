package org.mrbag.LinkCompresor.Controller;

import org.mrbag.LinkCompresor.LinkRepository;
import org.mrbag.LinkCompresor.Entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/form/*")
public class FormController {

	LinkRepository repo;
	
	@Value("${app.domain}")
	String domain;
	
	@Autowired
	public void setRepo(LinkRepository repo) {
		this.repo = repo;
	}
	
	@PostMapping("/add")
	public String AddFormLink(
			@RequestParam("Link") String link,
			@RequestParam("UID") String uid,
			@RequestParam(value = "Skeep", required = false, defaultValue = "false") boolean isSkeep,
			@RequestParam(value = "First", required = false, defaultValue = "false") boolean isFirst
			) {
		if(link.contains(domain))
			return "redirect:/?error=domain";
		
		Link l = Link.builder()
				.link(link)
				.uidDevice(uid)
				.isSkeep(isSkeep)
				.isAd(!isFirst)
				.build();
		try {
			repo.put(l);
		}
		catch (Exception e) {
			return "redirect:/?error=rerty";
		}
		
		return "redirect:/?access=" + repo.put(l);
	}
	
	
	
}
