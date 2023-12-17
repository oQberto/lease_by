package com.example.lease_by.api.handler.util;

public interface ErrorPage {
    String ERROR_PAGE_DIR = "error/4xx/";
    String ERROR_PAGE_SUFFIX = ".html";

    String ERROR_PAGE_400 = ERROR_PAGE_DIR + "400" + ERROR_PAGE_SUFFIX;
    String ERROR_PAGE_403 = ERROR_PAGE_DIR + "403" + ERROR_PAGE_SUFFIX;
    String ERROR_PAGE_404 = ERROR_PAGE_DIR + "404" + ERROR_PAGE_SUFFIX;
    String ERROR_PAGE_409 = ERROR_PAGE_DIR + "409" + ERROR_PAGE_SUFFIX;
}
