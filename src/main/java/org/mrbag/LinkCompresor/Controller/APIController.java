package org.mrbag.LinkCompresor.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

import org.mrbag.LinkCompresor.LinkRepository;
import org.mrbag.LinkCompresor.Entity.KeyLinkAttach;
import org.mrbag.LinkCompresor.Entity.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = { "/api/*" })
public class APIController {

	LinkRepository repo;

	@Autowired
	public void setRepo(LinkRepository repo) {
		this.repo = repo;
	}

	@GetMapping("/health")
	public ResponseEntity<?> getHealthApp() {
		try {
			return ResponseEntity.internalServerError()
					.body("PONG".equalsIgnoreCase(repo.getAlias().getConnectionFactory().getConnection().ping()));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(false);
		}
	}

	@GetMapping("/{id}")
	@ResponseBody
	public KeyLinkAttach getLinkInfo(@PathParam("id") String id) {
		return repo.get(id);
	}

	@PostMapping("/")
	@ResponseBody
	public KeyLinkAttach putLink(@RequestBody Link link) {
		return new KeyLinkAttach(repo.put(link), link);
	}

}
