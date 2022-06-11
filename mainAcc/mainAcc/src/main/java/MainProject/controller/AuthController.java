package MainProject.controller;

import MainProject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import MainProject.payload.request.LoginRequest;
import MainProject.payload.request.SignupRequest;
import MainProject.payload.response.JwtResponse;
import MainProject.payload.response.MessageResponse;
import MainProject.repository.AccountRepository;
import MainProject.repository.PaymentRepository;
import MainProject.repository.RoleRepository;
import MainProject.security.jwt.JwtUtils;
import MainProject.security.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	 RoleRepository roleRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/register/signin")
	public ResponseEntity<?> authenticateAccount( @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		List<String> payments = userDetails.getPaymentMethod().stream()
				.map(item -> item.getName().toString())
				.collect(Collectors.toList());
		System.out.println("XXXX Balance"+ userDetails.getBalance());
		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(),
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 roles,
				payments,
				userDetails.getFirstName(),
				userDetails.getLastName()));
	}

	@PostMapping("/register/signup")
	public ResponseEntity<?> registerAccount( @RequestBody SignupRequest signUpRequest) {
		if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Account account = new Account(signUpRequest.getUsername(),
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getBalance());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(RoleEnum.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		/************/
		Set<String> strPayments = signUpRequest.getPaymentMethod();
		Set<Payment> paymentMethods = new HashSet<>();

		if (strPayments == null) {
			Payment userPayment = paymentRepository.findByName(PaymentEnum.BANK_ACCOUNT)
					.orElseThrow(() -> new RuntimeException("Error: Payment is not found."));
			paymentMethods.add(userPayment);
		} else {
			strPayments.forEach(payment -> {
				switch (payment) {
					case "paypal":
						Payment paypal = paymentRepository.findByName(PaymentEnum.PAYPAL)
								.orElseThrow(() -> new RuntimeException("Error: Payment Method is not found."));
						paymentMethods.add(paypal);

						break;
					case "creditcard":
						Payment creditcardPayment = paymentRepository.findByName(PaymentEnum.CREDIT_CARD)
								.orElseThrow(() -> new RuntimeException("Error: Payment Method is not found."));
						paymentMethods.add(creditcardPayment);

						break;
					default:
						Payment bankAccount = paymentRepository.findByName(PaymentEnum.BANK_ACCOUNT)
								.orElseThrow(() -> new RuntimeException("Error: Payment Method is not found."));
						paymentMethods.add(bankAccount);
				}
			});
		}

		/************/

		account.setPaymentMethod(paymentMethods);
		account.setRoles(roles);
		accountRepository.save(account);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
