package com.in28minutes.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.in28minutes.rest.webservices.restful_web_services.util.EncoderUtil;
import com.in28minutes.rest.webservices.restful_web_services.util.JWTUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UserJpaResource {

	private UserRepository repository;

	private PostRepository postRepository;

	private AuthenticationManager authenticationManager;

	public UserJpaResource(UserRepository repository, PostRepository postRepository, AuthenticationManager authenticationManager) {
		this.repository = repository;
		this.postRepository = postRepository;
		this.authenticationManager = authenticationManager;
	}

	@Hidden
	@GetMapping("/")
	public RedirectView redirectWithUsingRedirectView(
			RedirectAttributes attributes) {
		// redirects to swagger documentation
		return new RedirectView("/swagger-ui/index.html");
	}

	// GET /users
	@GetMapping("/jpa/users")
	public List<UserDTO> retrieveAllUsers() {
		return repository.findAll().stream().map((User user) -> {
			return new UserDTO(user.getUserName(), user.getBirthDate());
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/jpa/users/details")
	public UserDTO retrieveUser(final Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Optional<User> user = repository.findById(userDetails.getUsername());
		
		if(user.isEmpty())
			throw new UserNotFoundException("userName " + userDetails.getUsername() + " does not exists!");
		return new UserDTO(user.get().getUserName(), user.get().getBirthDate());
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
		Optional<User> userOptional = repository.findById(userDTO.getUserName());

		if(userOptional.isPresent())
			throw new DuplicateUserNameException("userName " + userDTO.getUserName() + " already exists!");
		User user = new User(userDTO.getUserName(), userDTO.getBirthDate(), EncoderUtil.getEncoder().encode(userDTO.getPassword()));
		User savedUser = repository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{userName}")
						.buildAndExpand(savedUser.getUserName())
						.toUri();   
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/remove")
	public void deleteUser(final Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		repository.deleteById(userDetails.getUsername());
	}

	
	@GetMapping("/jpa/users/posts")
	public List<Post> retrievePostsForUser(final Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Optional<User> user = repository.findById(userDetails.getUsername());
		
		if(user.isEmpty())
			throw new UserNotFoundException("userName " + userDetails.getUsername() + " does not exists!");
		
		return user.get().getPosts();

	}
	
	@PostMapping("/jpa/users/posts")
	public ResponseEntity<Object> createPostForUser(final Authentication authentication, @Valid @RequestBody PostDTO postDTO) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Optional<User> user = repository.findById(userDetails.getUsername());

		if(user.isEmpty())
			throw new UserNotFoundException("userName " + userDetails.getUsername() + " does not exists!");

		Post post = new Post();
		post.setDescription(postDTO.getDescription());
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();   

		return ResponseEntity.created(location).build();

	}

	@PostMapping("/jpa/users/login")
	public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword()));
		if(authentication.isAuthenticated())
			return new ResponseEntity<>(JWTUtil.generateToken(userLoginDTO.getUserName()), HttpStatus.OK);
		return new ResponseEntity<>("Failure", HttpStatus.UNAUTHORIZED);
	}
}
