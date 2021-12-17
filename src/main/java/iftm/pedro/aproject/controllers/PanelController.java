package iftm.pedro.aproject.controllers;

import iftm.pedro.aproject.dtos.AddressForm;
import iftm.pedro.aproject.dtos.ProductDTO;
import iftm.pedro.aproject.services.OrderService;
import iftm.pedro.aproject.services.PayloadService;
import iftm.pedro.aproject.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/panel")
public class PanelController {

    private final OrderService oService;
    private final PayloadService pService;
    private final ProductService prodService;

    public PanelController(OrderService oService, PayloadService pService, ProductService prodService) {
        this.oService = oService;
        this.pService = pService;
        this.prodService = prodService;
    }

    @GetMapping()
    public ModelAndView renderPanelOrAuth(){
        Map<String,Object> models = new HashMap<>();
        var orders = oService.findAllByUser();
        var payloads = pService.findAllByUser();
        models.put("orders",orders);
        models.put("payloads",payloads);
        return new ModelAndView("/pages/panel",models);
    }

    @GetMapping(value = "/sending")
    public ModelAndView renderSendingForm(@RequestParam Long id){
        Map<String,Object> models = new HashMap<>();
        var obj = oService.findById(id);
        AddressForm form = new AddressForm();
        models.put("order", obj);
        models.put("addressForm",form);
        return new ModelAndView("/pages/PayloadForm",models);
    }

    // The BindingResult must come right after the model object that is validated or else Spring will fail to validate the object and throw an exception
    @PostMapping (value = "/sending")
    public ModelAndView submitPayload(@Valid @ModelAttribute AddressForm addressForm, BindingResult result, @RequestParam Long orderId){

        try {
            pService.insert(addressForm, orderId);
        } catch (RestClientException | IOException ex){
            System.out.println("ENTROU AQUI");
            result.addError(new ObjectError("cep", "Oi teste"));
        }

        if(result.hasErrors()){
            var obj = oService.findById(orderId);
            return new ModelAndView("pages/PayloadForm","order",obj);
        }

        return new ModelAndView("redirect:/panel");
    }

    @DeleteMapping
    public String deleteOrder(@RequestParam Long orderId){
        oService.delete(orderId);
        return "redirect:/panel";
    }

    @GetMapping("/items")
    public ModelAndView renderItemPage(Pageable pageable, HttpServletRequest request){
        var products = prodService.findAll(PageRequest.of(pageable.getPageNumber(), 4,Sort.by(Sort.Direction.DESC, "id")));
        return new ModelAndView("/pages/Items","data", products);
    }


    @GetMapping("/items/{id}")
    public ModelAndView renderItemForm(@PathVariable String id){
        ProductDTO dto;

        if(id.equals("creating")){
            dto = new ProductDTO();
        } else {
            dto = prodService.findById(Long.parseLong(id));
        }

        return new ModelAndView("/pages/ItemForm","productDTO",dto);
    }

    @PostMapping("/items/creating")
    public ModelAndView createProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult result){
        if(result.hasErrors()){
            return new ModelAndView("pages/ItemForm","productDTO",productDTO);
        }

        prodService.insert(productDTO);

        return new ModelAndView("redirect:/panel/items");
    }

    // Nome do objeto tem que ser igual ao nome da classe com a primeira letra minuscula
    @PutMapping("/items/{id}")
    public ModelAndView updateProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult result, @PathVariable String id){
        if(result.hasErrors()){
            result.getFieldErrors().forEach(System.out::println);
            return new ModelAndView("pages/ItemForm","productDTO",productDTO);
        }

        prodService.update(productDTO,Long.parseLong(id));
        return new ModelAndView("redirect:/panel/items");
    }


    @GetMapping("/payloads")
    public ModelAndView renderPayloads(Pageable pageable){
        var data = pService.findAll(PageRequest.of(pageable.getPageNumber(), 4));
        return new ModelAndView("/pages/Payloads","data", data);
    }

    @DeleteMapping("/payloads")
    public String deliverPayload(@RequestParam String id, RedirectAttributes attributes){
        pService.deliverOrder(id);
        return "redirect:/panel/payloads";
    }

    @DeleteMapping("/items/{id}")
    public String deleteItem(@PathVariable Long id){
        prodService.delete(id);
        return "redirect:/panel/items";
    }
}
