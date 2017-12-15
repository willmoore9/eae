package com.eae.schedule.ui.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="BOOK1")
public class PublisherAlreadyBookedException extends Exception {

	private static final long serialVersionUID = 1L;

}
