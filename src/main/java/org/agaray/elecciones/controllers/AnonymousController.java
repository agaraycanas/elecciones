package org.agaray.elecciones.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.agaray.elecciones.entities.ComunidadAutonoma;
import org.agaray.elecciones.entities.Provincia;
import org.agaray.elecciones.entities.User;
import org.agaray.elecciones.exception.DangerException;
import org.agaray.elecciones.exception.InfoException;
import org.agaray.elecciones.helper.H;
import org.agaray.elecciones.helper.PRG;
import org.agaray.elecciones.repositories.CandidaturaRepository;
import org.agaray.elecciones.repositories.ComunidadAutonomaRepository;
import org.agaray.elecciones.repositories.EleccionRepository;
import org.agaray.elecciones.repositories.PartidoPoliticoRepository;
import org.agaray.elecciones.repositories.ProvinciaRepository;
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

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private ComunidadAutonomaRepository comunidadAutonomaRepository;
	
	@Autowired
	private CandidaturaRepository candidaturaRepository;
	
	@Autowired
	private EleccionRepository eleccionRepository;
	
	@Autowired
	private PartidoPoliticoRepository partidoPoliticoRepository;

	private void initProvinciasYCCAAs() {
		Map<String, List<String>> bd = new HashMap<String,List<String>>();
		
		bd.put("Andalucía", Arrays.asList("Almería","Cádiz","Córdoba","Granada","Huelva","Jaén","Málaga","Sevilla"));
		bd.put("Aragón", Arrays.asList("Huesca","Teruel","Zaragoza"));
		bd.put("Asturias (Principado de)", Arrays.asList("Asturias"));
		bd.put("Canarias",Arrays.asList("Palmas (Las)","Santa Cruz de Tenerife"));
		bd.put("Cantabria",Arrays.asList("Cantabria"));
		bd.put("Castilla y León",Arrays.asList("Ávila","Burgos","León","Palencia","Salamanca","Segovia","Soria","Valladolid","Zamora"));
		bd.put("Castilla-La Mancha",Arrays.asList("Albacete","Ciudad Real","Cuenca","Guadalajara","Toledo"));
		bd.put("Cataluña",Arrays.asList("Barcelona","Gerona","Lérida","Tarragona"));
		bd.put("Ceuta (Ciudad de)",Arrays.asList("Ceuta"));
		bd.put("Comunidad Valenciana",Arrays.asList("Alicante","Castellón","Valencia"));
		bd.put("Extremadura",Arrays.asList("Badajoz",
                "Cáceres"));
		bd.put("Galicia",Arrays.asList("Coruña (La)","Lugo","Orense","Pontevedra"));
		bd.put("Islas Baleares",Arrays.asList("Islas Baleares"));
		bd.put("Madrid (Comunidad de)",Arrays.asList("Madrid"));
		bd.put("Melilla (Ciudad de)",Arrays.asList("Melilla"));
		bd.put("Murcia (Región de)",Arrays.asList("Murcia"));
		bd.put("Navarra (Comunidad Foral de)",Arrays.asList("Navarra"));
		bd.put("País Vasco",Arrays.asList("Álava","Guipúzcoa","Vizcaya"));
		bd.put("Rioja (La)",Arrays.asList("Rioja (La)"));

		provinciaRepository.deleteAll();
		comunidadAutonomaRepository.deleteAll();

		for (String nombreCCAA: bd.keySet()) {
			ComunidadAutonoma nuevaCCAA = new ComunidadAutonoma(nombreCCAA);
			comunidadAutonomaRepository.save(nuevaCCAA);
			
			for (String nombreProvincia : bd.get(nombreCCAA)) {
				Provincia nuevaProvincia = new Provincia(nombreProvincia);
				nuevaProvincia.setComunidadAutonoma(nuevaCCAA);
				nuevaCCAA.getProvincias().add(nuevaProvincia);
				provinciaRepository.save(nuevaProvincia);
			}
		}

	}

	@GetMapping("/init")
	public void initGet(ModelMap m) throws DangerException, InfoException {
		 
		 userRepository.deleteAll();
		 provinciaRepository.deleteAll();
		 comunidadAutonomaRepository.deleteAll();
		 candidaturaRepository.deleteAll();
		 partidoPoliticoRepository.deleteAll();
		 eleccionRepository.deleteAll();
		 
		 
		 User admin = new User("admin",new BCryptPasswordEncoder().encode("admin"),true);
		 userRepository.save(admin);
		 
		 this.initProvinciasYCCAAs();
		 
		 PRG.info("Base de datos reinicializada");
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
	public String home(
			ModelMap m,
			HttpSession s
			) {
		User user = s.getAttribute("user")!=null ? (User)s.getAttribute("user") : null;
		String rol =  (user!=null ? (user.isAdmin() ? "admin" : "auth" ) : "anon" );
		String vistaADesplegar = "/"+rol+"/home";
		if (rol.equals("auth") && (s.getAttribute("provincia") == null || s.getAttribute("eleccion") == null ) )  {
			m.put("elecciones", eleccionRepository.findAll());
			m.put("provincias", provinciaRepository.findAll());
			vistaADesplegar = "/auth/seleccionarProvinciaYEleccion";
		}
		m.put("view", vistaADesplegar);
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

