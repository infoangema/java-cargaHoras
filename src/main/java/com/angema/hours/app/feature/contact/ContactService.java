package com.angema.hours.app.feature.contact;

import com.angema.hours.app.core.Messages;
import com.angema.hours.app.core.errors.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ErrorService errorService;

    public List<Contact> getAllContact() {
        try {
            return contactRepository.findAll();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Contact getIdContact(final Long id) {
        try {
            Optional<Contact> contact = contactRepository.findById(id);
            if (!contact.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            return contact.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Contact saveContact(Contact contact, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            return contactRepository.save(contact);
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Contact deleteContact(final Long id) {
        try {
            Optional<Contact> contact = contactRepository.findById(id);
            if (!contact.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            contactRepository.delete(contact.get());
            return contact.get();
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }

    public Contact updateContact(Contact data, final Long id, BindingResult bindingResult) {
        try {
            List<String> errors = errorService.collectErrorsBindings(bindingResult);
            if (!errors.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Arrays.deepToString(errors.toArray()));
            }
            Optional<Contact> contact = contactRepository.findById(id);
            if (!contact.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, Messages.ERROR_USER_NOT_FOUND);
            }
            contact.get().setName(data.getName());
            contact.get().setCompany(data.getCompany());
            contact.get().setMail(data.getMail());
            contact.get().setPhone(data.getPhone());
            contact.get().setDescription(data.getDescription());
            contact.get().setViewed(data.isViewed());
            return contactRepository.save(contact.get());
        } catch (InvalidDataAccessResourceUsageException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, Messages.ERROR_SERVER, e);
        }
    }
}
