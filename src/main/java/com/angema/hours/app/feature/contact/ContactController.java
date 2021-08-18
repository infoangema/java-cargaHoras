package com.angema.hours.app.feature.contact;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    private List<Contact> getAll() {
        return contactService.getAllContact();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    private Contact getId(@PathVariable("id") Long id) {
        return contactService.getIdContact(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    private Contact save(@Valid @RequestBody Contact data, BindingResult bindingResult) {
        return contactService.saveContact(data, bindingResult);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    private Contact delete(@PathVariable("id") Long id) {
        return contactService.deleteContact(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    private Contact update(@PathVariable("id") Long id, @Valid @RequestBody Contact data, BindingResult bindingResult) {
        return contactService.updateContact(data, id, bindingResult);
    }
}
