/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.appworks.developer.lab.services;

import com.opentext.appworks.developer.lab.api.EchoNotificationRequest;
import com.opentext.otag.sdk.client.v3.NotificationsClient;
import com.opentext.otag.sdk.types.v3.api.SDKResponse;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.notification.ClientPushNotificationRequest;
import com.opentext.otag.service.context.components.AWComponent;
import com.opentext.otag.service.context.components.AWComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import java.util.Date;

/**
 * Service to demonstrate the use of the Gateways push notification API. A custom
 * component that can be added to the shared registry.
 *
 * @see AWComponent
 * @see AWComponentContext
 */
public class PushNotificationService implements AWComponent {

    private static final Logger LOG = LoggerFactory.getLogger(PushNotificationService.class);

    private static final String SEND_ERR = "We failed to use the SDK to send a push notification";

    private NotificationsClient notificationsClient;

    public PushNotificationService(NotificationsClient notificationsClient) {
        this.notificationsClient = notificationsClient;
    }

    /**
     * Send a push notification with the content of our echo request.
     *
     * @param toSend to send
     */
    public void sendPushNotification(EchoNotificationRequest toSend) {
        try {
            LOG.info("Attempting to send request - {}", toSend.toString());

            // use the notification builder supplied by the SDK
            ClientPushNotificationRequest pushNotificationRequest =
                    new ClientPushNotificationRequest.Builder()
                            // send to a specific client
                            .addClient(toSend.getClientId())
                            // send to a specific AppWorks app
                            .addData("target", toSend.getAppId())
                            // note the created date
                            .addData("created", new Date().getTime())
                            // this is the JSON payload delivered to the app
                            .addData("message", "{\"link\": \"" + toSend.getLink() + "\"}")
                            .build();

            SDKResponse sdkResponse = notificationsClient.sendPushNotification(pushNotificationRequest);
            LOG.info("We received a response after attempting to send a push notification - {}", sdkResponse.toString());
        } catch (APIException e) {
            // catch the API exception so we can log the useful call info provided
            // about the request processing
            LOG.error(SEND_ERR + " " + e.getCallInfo());
            throw new WebApplicationException(SEND_ERR);
        } catch (Exception e) {
            LOG.error(SEND_ERR, e);
            throw new WebApplicationException(SEND_ERR);
        }
    }

}
