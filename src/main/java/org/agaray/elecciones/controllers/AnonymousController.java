package org.agaray.elecciones.controllers;

import javax.servlet.http.HttpSession;

import org.agaray.elecciones.entities.User;
import org.agaray.elecciones.exception.DangerException;
import org.agaray.elecciones.exception.InfoException;
import org.agaray.elecciones.helper.H;
import org.agaray.elecciones.helper.PRG;
import org.agaray.elecciones.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnonymousController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/init")
	public String initGet(ModelMap m) throws DangerException {
		 User admin = new User("admin",new BCryptPasswordEncoder().encode("admin"),true);
		 userRepository.deleteAll();
		 userRepository.save(admin);
		return "redirect:/";
	}

	@GetMapping("/info")
	public String info(HttpSession s, ModelMap m) {

		String mensaje = s.getAttribute("_mensaje") != null ? (String) s.getAttribute("_mensaje")
				: "Pulsa para volver a home";
		String severity = s.getAttribute("_severity") != null ? (String) s.getAttribute("_severity") : "info";
		String link = s.getAttribute("_link") != null ? (String) s.getAttribute("_link") : "/";

		s.removeAttribute("_mensaje");
		s.removeAttribute("_severity");
		s.removeAttribute("_link");

		m.put("mensaje", mensaje);
		m.put("severity", severity);
		m.put("link", link);

		m.put("view", "/_t/info");
		return "/_t/frame";
	}

	@GetMapping("/")
	public String home(ModelMap m) {
		m.put("view", "/anon/home");
		return "/_t/frame";
	}

	@GetMapping("/registro")
	public String registro(ModelMap m) {
		m.put("view", "anon/registro");
		return "/_t/frame";
	}
	
	@PostMapping("/registro")
	public void registro(
			@RequestParam("loginname") String loginname,
			@RequestParam("password") String password,
			HttpSession s
			) throws DangerException, InfoException {
		H.isRolOK("anon", s);
		User usuarioActual = userRepository.getByLoginname(loginname);
		if (usuarioActual == null) {
			userRepository.save(new User(
					loginname,
					new BCryptPasswordEncoder().encode(password),
					false)
					);
			PRG.info("Usuario creado correctamente");
		}
		else {
			PRG.error("Usuario ya existente");
		}
	}
	

	@GetMapping("/login")
	public String loginGet(ModelMap m, HttpSession s) throws DangerException {
		H.isRolOK("anon", s);
		m.put("view", "/anon/login");
		return "/_t/frame";
	}

	
	

	@PostMapping("/login")
	public String loginPost(@RequestParam("loginname") String loginname, @RequestParam("password") String password,
			ModelMap m, HttpSession s) throws DangerException {
		H.isRolOK("anon", s);

		try {
			User user = userRepository.getByLoginname(loginname);
			if (!(new BCryptPasswordEncoder()).matches(password, user.getPassword())) {
				throw new Exception();
			}
			s.setAttribute("user", user);
		} catch (Exception e) {
			PRG.error("Usuario o contraseña incorrecta", "/login");
		}

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession s) throws DangerException {
		H.isRolOK("auth", s);

		s.invalidate();
		return "redirect:/";
	}
}

