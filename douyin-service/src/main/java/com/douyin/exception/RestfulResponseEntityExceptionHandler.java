package com.douyin.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@SuppressWarnings("deprecation")
@ControllerAdvice
public class RestfulResponseEntityExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(RestfulResponseEntityExceptionHandler.class);

    private final static String SUCCESS = "success";
    private final static String ERROR_CODE = "status_code";
    private final static String MSG_KEY = "status_msg";

    /**
     * Provides handling for standard Spring MVC exceptions.
     *
     * @param ex      the target exception
     * @param request the current request
     */
    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class, MissingServletRequestParameterException.class,
            ServletRequestBindingException.class, ConversionNotSupportedException.class, TypeMismatchException.class,
            HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class, MissingServletRequestPartException.class, BindException.class,
            NoHandlerFoundException.class, CommonException.class,
            Exception.class })
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
            return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, headers, status,
                    request);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, headers, status, request);
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, headers, status, request);
        } else if (ex instanceof MissingServletRequestParameterException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, headers, status,
                    request);
        } else if (ex instanceof ServletRequestBindingException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleServletRequestBindingException((ServletRequestBindingException) ex, headers, status, request);
        } else if (ex instanceof ConversionNotSupportedException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleConversionNotSupported((ConversionNotSupportedException) ex, headers, status, request);
        } else if (ex instanceof TypeMismatchException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleTypeMismatch((TypeMismatchException) ex, headers, status, request);
        } else if (ex instanceof HttpMessageNotReadableException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, headers, status, request);
        } else if (ex instanceof HttpMessageNotWritableException) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, headers, status, request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, headers, status, request);
        } else if (ex instanceof MissingServletRequestPartException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMissingServletRequestPart((MissingServletRequestPartException) ex, headers, status, request);
        } else if (ex instanceof BindException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleBindException((BindException) ex, headers, status, request);
        } else if (ex instanceof NoHandlerFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleNoHandlerFoundException((NoHandlerFoundException) ex, headers, status, request);
        } else if (ex instanceof CommonException) {
            HttpStatus status = HttpStatus.OK;
            return handleBusinessException((CommonException) ex, headers, status, request);
        }  else {
            logger.error("Unknown exception type: " + ex.getClass().getName(), ex);
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.SERVER_INNER_ERROR), headers,
                    status, request);
        }
    }

    /**
     * A single place to customize the response body of all Exception types.
     * This method returns {@code null} by default.
     *
     * @param ex      the exception
     * @param body    the body to use for the response
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     */
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<Object>(body, headers, status);
    }

    protected ResponseEntity<Object> handleBusinessException(CommonException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logLocalErrorMsg(ex, request, ex);
        return handleExceptionInternal(ex, getErrorBody(ex), headers, status, request);
    }

    /**
     * Customize the response for HttpRequestMethodNotSupportedException. This
     * method logs a warning, sets the "Allow" header, and delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.warn(ex.getMessage(), ex);

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!supportedMethods.isEmpty()) {
            headers.setAllow(supportedMethods);
        }

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_405), headers, status, request);
    }

    /**
     * Customize the response for HttpMediaTypeNotSupportedException. This
     * method sets the "Accept" header and delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
        }

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_415), headers, status, request);
    }

    /**
     * Customize the response for HttpMediaTypeNotAcceptableException. This
     * method delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_406), headers, status, request);
    }

    /**
     * Customize the response for MissingServletRequestParameterException. This
     * method delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for ServletRequestBindingException. This method
     * delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for ConversionNotSupportedException. This method
     * delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.SERVER_INNER_ERROR), headers, status,
                request);
    }

    /**
     * Customize the response for TypeMismatchException. This method delegates
     * to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for HttpMessageNotReadableException. This method
     * delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for HttpMessageNotWritableException. This method
     * delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.SERVER_INNER_ERROR), headers, status,
                request);
    }

    /**
     * Customize the response for MethodArgumentNotValidException. This method
     * delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for MissingServletRequestPartException. This
     * method delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for BindException. This method delegates to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_400), headers, status, request);
    }

    /**
     * Customize the response for NoHandlerFoundException. This method delegates
     * to
     * {@link #handleExceptionInternal(Exception, Object, HttpHeaders, HttpStatus, WebRequest)}
     * .
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     * @since 4.0
     */
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.warn(ex.getMessage(), ex);

        return handleExceptionInternal(ex, getErrorBody(CommonServerErrorConstants.HTTP_404), headers, status, request);
    }

    private void logLocalErrorMsg(LocalError error, WebRequest webRequest, Exception e) {
        StringBuilder errMsg = new StringBuilder();

        if (webRequest != null && webRequest instanceof ServletWebRequest) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
            HttpServletRequest servletRequest = servletWebRequest.getRequest();
            if (servletRequest != null) {
                errMsg.append("url:");
                errMsg.append(servletRequest.getRequestURL().toString());
                errMsg.append(",");
            }
        }

        errMsg.append("code:");
        errMsg.append(error.getCode());
        errMsg.append(",message:");
        errMsg.append(error.getMessage());

        if (logger.isWarnEnabled()) {
            if (e != null) {
                logger.error(errMsg.toString(), e);
            } else {
                logger.error(errMsg.toString());
            }
        }
    }

    private void logLocalErrorMsg(LocalError error, WebRequest webRequest) {
        logLocalErrorMsg(error, webRequest, null);
    }

    public static Map<String, Object> getErrorBody(LocalError localError) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR_CODE, localError.getCode());
        result.put(MSG_KEY, localError.getMessage());
        return result;
    }
}
