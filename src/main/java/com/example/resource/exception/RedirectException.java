package com.example.resource.exception;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * The class RedirectException.
 */
public final class RedirectException extends RuntimeException {

    /**
     * The constant serialVersionUID.
     */
    private static final long         serialVersionUID = 1L;

    /**
     * Is the location absolute.
     */
    private boolean                   absolute;

    /**
     * The context path.
     */
    private String              contextPath;

    /** The data. */
    private Map<String, Object> data;

    /**
     * The redirect location.
     */
    private String              location;

    /**
     * Instantiates a new redirect exception.
     *
     * @param request the request
     * @param location the location
     */
    public RedirectException(final HttpServletRequest request, final String location) {
        super();
    }

    /**
     * Instantiates a new redirect exception.
     *
     * @param request the request
     * @param location the location
     * @param data the data
     */
    public RedirectException(final HttpServletRequest request, final String location, final Map<String, Object> data) {
        super();
    }

    /**
     * Instantiates a new redirect exception.
     *
     * @param request the request
     * @param location the location
     * @param data the data
     * @param parameters the parameters
     */
    public RedirectException(final HttpServletRequest request, final String location, final Map<String, Object> data, final String... parameters) {
        super();
    }

    /**
     * Instantiates a new redirect exception.
     *
     * @param request the request
     * @param location the location
     * @param data the data
     * @param parameters the parameters
     */
    public RedirectException(final HttpServletRequest request, final String location, final Map<String, Object> data, final UUID... parameters) {
        super();
    }

    /**
     * Instantiates a new redirect exception.
     *
     * @param request the request
     * @param location the location
     * @param parameters the parameters
     */
    public RedirectException(final HttpServletRequest request, final String location, final String... parameters) {
        super();
    }

    /**
     * Instantiates a new redirect exception.
     *
     * @param request the request
     * @param location the location
     * @param parameters the parameters
     */
    public RedirectException(final HttpServletRequest request, final String location, final UUID... parameters) {
        super();
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return absolute ? location : (contextPath + location); // NOPMD
    }

    /**
     * Checks if the path is absolute.
     *
     * @return true, if is absolute
     */
    public boolean isAbsolute() {
        return absolute;
    }

    /**
     * Sets the absolute flag.
     *
     * @param absolute the absolute
     * @return the redirect exception
     */
    public RedirectException setAbsolute(final boolean absolute) {
        this.absolute = absolute;
        return this;
    }

}
