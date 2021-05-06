package pe.com.sedapal.agc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import pe.com.sedapal.agc.util.FileStorageProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties({ FileStorageProperties.class })
@EnableAsync
//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.EnableWebMvcConfiguration.class})
public class InicioSpring extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InicioSpring.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(InicioSpring.class, args);
	}
	
	@Bean("agcThreadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("AgcAsyncTask-");
		return executor;
	}

}
