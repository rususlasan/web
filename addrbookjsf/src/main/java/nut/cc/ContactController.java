package nut.cc;

import javax.inject.Inject;
import javax.inject.Named;

import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by ruslansh on 12.02.17.
 */
@Named
@RequestScoped
public class ContactController {

    @Inject
    private ContactEJB contactEJB;
    private Contact contact = new Contact();

    public String doCreateContact() {
        contactEJB.createContact(contact);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Contact created",
                        "The contact" +
                                contact.getName() +
                                " has been created with id = "
                                + contact.getId()));
        return "newcontact.xhtml";
    }

    public void doFindContactById() {
        contact = contactEJB.findContactById(contact.getId());
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
