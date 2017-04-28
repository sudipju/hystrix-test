package swish.hystrix.control;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PanelController {
	List<Bank> banks = Arrays.asList(
			new Bank(1, "BANK1"),
			new Bank(2, "BANK2"),
			new Bank(3, "BANK3"),
			new Bank(4, "BANK4"),
			new Bank(5, "BANK5"));
			
    @RequestMapping("/panel")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("banks", banks);
    	model.addAttribute("name", name);
        return "panel";
    }

}
