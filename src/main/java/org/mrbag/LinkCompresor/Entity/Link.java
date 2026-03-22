package org.mrbag.LinkCompresor.Entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {
	
	@Builder.Default
	private boolean isAd = true;
	
	@Builder.Default
	private boolean isSkeep = false;

	private String link;
	
	private String uidDevice;
	
	@Builder.Default
	private Boolean layer = false;

	@Builder.Default
	private LocalDateTime ctime = LocalDateTime.now();

	public boolean isLayer(){
		return layer == null ? false : layer;
	}
	
}

