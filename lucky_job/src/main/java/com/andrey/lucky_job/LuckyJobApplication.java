package com.andrey.lucky_job;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin
@NpmPackage(value = "@fontsource/open-sans", version = "4.5.0")
public class LuckyJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckyJobApplication.class, args);
	}

}
