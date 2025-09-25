package org.mrbag.LinkCompresor.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.extern.slf4j.Slf4j;

class InfoMessages {
	
	Set<String> ruMessages = new HashSet<String>();
	
	Set<String> enMessages = new HashSet<String>();
	
	public InfoMessages(ResourceLoader load) throws FileNotFoundException, IOException {
		Scanner rus = new Scanner(load.getResource("classpath:ruinfo.txt").getInputStream());
		Scanner ens = new Scanner(load.getResource("classpath:eninfo.txt").getInputStream());
		while (rus.hasNextLine())
			ruMessages.add(rus.nextLine());
		while (ens.hasNextLine())
			enMessages.add(ens.nextLine());	
	}
	
	public InfoMessages(String rus, String eng) {
		ruMessages.add(rus);
		enMessages.add(eng);
	}
	
	public String randRus() {
		return ruMessages.stream().skip(ThreadLocalRandom.current().nextInt(ruMessages.size())).findFirst().get();
	}
	
	public String randEng() {
		return enMessages.stream().skip(ThreadLocalRandom.current().nextInt(ruMessages.size())).findFirst().get();
	}
}

@Service
@Slf4j
public class TranslateService {

	InfoMessages info;
	
	public TranslateService(ResourceLoader load) {
		try {
			this.info = new InfoMessages(load);
		} catch (IOException e) {
			this.info = new InfoMessages("Простой сервис для сокращения ссылок :)", "Simple service for create short link ;)");
			log.warn("Fatal load message info resolver: " + e);
		}
	}
	
	public void setRuModel(Model m) {
		m.addAttribute("info", info.randRus());
	}
	
	public void setEnModel(Model m) {
		m.addAttribute("info", info.randEng());
	}
	
}
