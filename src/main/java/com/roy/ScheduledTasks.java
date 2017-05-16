package com.roy;

import java.io.File;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	@Scheduled(fixedRate = 24 * 60 * 1000 * 3)
	public void deletTempFiles() {
		try {
			int deletedFiles = 0;
			File tmp = new File("tmp_images");
			
			for (File file : tmp.listFiles()) {
					file.delete();
					deletedFiles += 1;
			}
			
			log.info("Number of deleted files : " + deletedFiles);
			
			
		}catch(Exception ex) {
			log.error("Couldn't delete temporary files because : " + ex.getMessage());
		}
	}
}
