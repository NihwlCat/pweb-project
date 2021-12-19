package iftm.pedro.aproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import iftm.pedro.aproject.services.OrderService;
import iftm.pedro.aproject.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class HomeController {

    private final ProductService service;
    private final OrderService oService;

    HomeController(ProductService service, OrderService oService) {
        this.service = service;
        this.oService = oService;
    }

    public static boolean isItemSelected(HttpServletRequest request, Long id) throws JsonProcessingException {
        Cookie cookie = WebUtils.getCookie(request,"cart");

        if(cookie != null) {
            ObjectMapper mapper = new ObjectMapper();
            String decoded = URLDecoder.decode(cookie.getValue(),StandardCharsets.UTF_8);
            Map<Long, Integer> map = mapper.readValue(decoded, new TypeReference<>(){});
            return map.containsKey(id);
        }
        return false;
    }


    @GetMapping(value = {"/", "index.html"})
    public ModelAndView loadProducts(Pageable pageable, HttpServletRequest request){
        var products = service.findAll(PageRequest.of(pageable.getPageNumber(), 4));
        return new ModelAndView("index","data", products);
    }

    @GetMapping(value = {"/login", "auth.html"})
    public String loadLoginView() {
        return "auth";
    }

    @PostMapping(value = "/create-order")
    public ResponseEntity<Void> createOrder(@RequestBody Map<Long, Integer> products){
        var body = oService.insert(products);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/",consumes = "application/json")
    public ResponseEntity<Void> insertItems(HttpServletResponse response, HttpServletRequest request, @RequestBody String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String[] splatted = body.split(",");
        var obj = request.getCookies();
        Optional<Cookie> cookie = Arrays.stream(obj).filter(c -> c.getName().equals("cart")).findFirst();
        if(cookie.isPresent()){
            var c = cookie.get();
            String decoded = URLDecoder.decode(c.getValue(),StandardCharsets.UTF_8);
            Map<Long, Integer> map = mapper.readValue(decoded, new TypeReference<>(){});
            map.put(Long.parseLong(splatted[0]),Integer.parseInt(splatted[1]));
            String cart = mapper.writeValueAsString(map);
            c.setValue(URLEncoder.encode(cart, StandardCharsets.UTF_8));
            response.addCookie(c);
        } else {
            Map<Long, Integer> map = new HashMap<>();
            map.put(Long.parseLong(splatted[0]),Integer.parseInt(splatted[1]));
            String cart = mapper.writeValueAsString(map);
            Cookie c = new Cookie("cart",URLEncoder.encode(cart, StandardCharsets.UTF_8));
            response.addCookie(c);
        }
        return ResponseEntity.accepted().build();
    }
}
