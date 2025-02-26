package com.practicesoftwaretesting.common;

import com.practicesoftwaretesting.ConfigReader;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogRequestFilter implements OrderedFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogRequestFilter.class);
    private ConfigReader configReader = new ConfigReader();

    public Response filter(
        FilterableRequestSpecification requestSpec,
        FilterableResponseSpecification responseSpec,
        FilterContext ctx
    ) {
        logger.info(requestSpec.getMethod().toUpperCase() + " " + requestSpec.getURI());

        Response response = ctx.next(requestSpec, responseSpec);

        logger.info(response.getStatusCode() + " " + requestSpec.getURI());

        if (needToLogResponse()) {
            logger.info(
                "Content-Type: " + response.getHeader("Content-Type") + "\n" +
                response.asPrettyString());
        }

        return response;
    }

    private boolean needToLogResponse() {
        return Boolean.parseBoolean(configReader.getProperty("log.response"));
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
