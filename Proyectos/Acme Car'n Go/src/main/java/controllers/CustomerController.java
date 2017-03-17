/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;
import forms.Register;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Services -----------------------------------------------------------------------
	@Autowired
	private CustomerService	customerService;


	// Constructor --------------------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Listing ------------------------------------------------------------------------	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false, defaultValue = "0") final int customerId) {

		ModelAndView result;
		Customer customer;

		if (customerId == 0)
			customer = this.customerService.findByPrincipal();
		else
			customer = this.customerService.findOne(customerId);
		result = new ModelAndView("customer/display");
		result.addObject("customer", customer);
		result.addObject("comments", customer.getComments());

		return result;
	}

	// Creation -----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Register customer;

		customer = new Register();
		customer.setAccept(false);

		result = this.createEditModelAndView(customer);

		return result;
	}

	// Edition ------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Register registerCustomer, final BindingResult binding) {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.reconstruct(registerCustomer, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(registerCustomer);
		else
			try {
				customer = this.customerService.register(customer);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(registerCustomer, "customer.commit.error");
			}
		return result;
	}

	// Ancillary methods --------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Register customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Register customer, final String message) {
		ModelAndView result;

		final String requestURI = "customer/edit.do";

		result = new ModelAndView("customer/register");
		result.addObject("register", customer);
		result.addObject("message", message);
		result.addObject("requestURI", requestURI);

		return result;
	}
}
