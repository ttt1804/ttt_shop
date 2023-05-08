package com.ttt.ttt_shop;

import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TttShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(TttShopApplication.class, args);


	}

}
//@SpringBootApplication
//public class TttShopApplication implements CommandLineRunner {
//
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	public static void main(String[] args) {
//		SpringApplication.run(TttShopApplication.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		User user = new User();
//		user.setUsername("username1");
//		user.setPassword(passwordEncoder.encode("12345"));
//		userRepository.save(user);
//	}
//}