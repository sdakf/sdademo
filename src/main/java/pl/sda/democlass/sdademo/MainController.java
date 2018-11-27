package pl.sda.democlass.sdademo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.sda.democlass.sdademo.users.*;
import pl.sda.democlass.sdademo.weather.service.WeatherService;

import javax.validation.Valid;

@Controller
public class MainController {

    private WeatherService weatherService;
    private UserRegistrationService userRegistrationService;

    @Autowired
    public MainController(WeatherService weatherService, UserRegistrationService userRegistrationService) {
        this.weatherService = weatherService;
        this.userRegistrationService = userRegistrationService;
    }

    @RequestMapping("/")
    public String welcome() {
        return "index";
    }


    @GetMapping("/weather")
    @ResponseBody
    public ResponseEntity<String> weather() {
        try {
            return ResponseEntity.ok(weatherService.getWeather());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Błąd");
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm(Model model) {
        model.addAttribute("customerRegistrationDto", new CustomerRegistrationDto());
        model.addAttribute("countries", Countries.values());

        return "registerForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerEffect(@ModelAttribute @Valid CustomerRegistrationDto customerRegistrationDto, BindingResult bindingResult, Model model) {
        model.addAttribute("customerRegistrationDto", customerRegistrationDto);
        model.addAttribute("countries", Countries.values());
        //todo 3 - w przypadku błędów walidacji należy przekierować ponownie na formularz rejestracji

        try {
            userRegistrationService.registerUser(customerRegistrationDto);
        } catch (UserExistsException e) {
            model.addAttribute("userExistsException", "Użytkownik znajduje się już w bazie.");
            return "registerForm";
        }
        return "registerEffect";
    }
}
