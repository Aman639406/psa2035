package com.crm.exception;


import com.crm.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


// Catch block
@ControllerAdvice
public class HandleException {



    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleExceptionResourceNotFound(
            ResourceNotFound e,
            WebRequest request

    ){

        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                new Date(),
                request.getDescription(true)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @ExceptionHandler (Exception.class)
    public ResponseEntity<ErrorDetails> globalException(
            Exception e,
            WebRequest request
            //webRequest use here to get more detail to the client it return back the client information  gives uri detail in the post man
            //uri is different from every url menas only gives the uri of this method which gives exception
    ){
        ErrorDetails errorDetails = new ErrorDetails(
                e.getMessage(),
                new Date(),
                request.getDescription(false)

        );

        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
