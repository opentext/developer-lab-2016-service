/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.appworks.developer.lab.api;

import com.opentext.appworks.developer.lab.services.PushNotificationService;
import com.opentext.otag.service.context.components.AWComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("echo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EchoResource {

    private static final String REQUEST_ERROR = "Invalid echo notification request received:";

    private static final Logger LOG = LoggerFactory.getLogger(EchoResource.class);

    private PushNotificationService pushNotificationService;

    @POST
    public Response createEchoRequest(EchoNotificationRequest request) {
        validateRequest(request);
        getNotificationService().sendPushNotification(request);
        return Response.ok().build();
    }

    /**
     * Get an instance of the {@link PushNotificationService} from the AppWorks component context.
     * @return notification service
     */
    private PushNotificationService getNotificationService() {
        if (pushNotificationService != null)
            return pushNotificationService;

        // try to get the AppWorks component out of our component context and
        // let API users know that this service is not available yet if we cannot
        // get the service instance
        PushNotificationService notificationService =
                AWComponentContext.getComponent(PushNotificationService.class);

        if (notificationService == null) {
            LOG.error("We failed to get the PushNotificationService has the service started yet???");
            throw new WebApplicationException("The Echo Service is yet to initialise", 503);
        }

        pushNotificationService = notificationService;
        return notificationService;
    }

    private void validateRequest(EchoNotificationRequest request) {
        String errorMessage = null;
        if (request == null) {
            errorMessage = REQUEST_ERROR + " Request cannot be null";
        } else if (!request.isPopulated()) {
            errorMessage = REQUEST_ERROR;
            if (isNullOrEmpty(request.getClientId()))
                errorMessage += " the client id was missing |";
            if (isNullOrEmpty(request.getAppId()))
                errorMessage += " the app id was missing |";
            if (isNullOrEmpty(request.getLink()))
                errorMessage += " the link was missing |";
        }

        if (!isNullOrEmpty(errorMessage))
            throw new WebApplicationException(errorMessage, 400);
    }

    private boolean isNullOrEmpty(String toCheck) {
        return toCheck == null || toCheck.trim().isEmpty();
    }
}
