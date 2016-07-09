/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.appworks.developer.lab;

import com.opentext.appworks.developer.lab.services.PushNotificationService;
import com.opentext.otag.sdk.client.v3.NotificationsClient;
import com.opentext.otag.sdk.client.v3.ServiceClient;
import com.opentext.otag.sdk.handlers.AWServiceContextHandler;
import com.opentext.otag.sdk.handlers.AWServiceStartupComplete;
import com.opentext.otag.sdk.types.v3.api.error.APIException;
import com.opentext.otag.sdk.types.v3.management.DeploymentResult;
import com.opentext.otag.service.context.components.AWComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class AppWorksService  implements AWServiceContextHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AppWorksService.class);

    // mark this method as the one that completes the deployment
    @AWServiceStartupComplete
    @Override
    public void onStart(String appName) {
        boostrapService(appName);
    }

    @Override
    public void onStop(String appName) {
        LOG.info("AppWorksService#onStop() called for \"" + appName + "\"");
    }

    private void boostrapService(String appName) {
        LOG.info("AppWorksService#onStart() - initializing service \"" + appName + "\"");
        ServiceClient serviceClient = new ServiceClient();

        try {
            // as soon as onStart is called in any implementation of AWServiceContextHandler
            // we know it is safe to instantiate our SDK clients
            initialiseService();
            LOG.info("Attempting to complete deployment of " + appName);
            // make sure we let the Gateway know we have completed our startup
            serviceClient.completeDeployment(new DeploymentResult(true));
            LOG.info("AppWorksService#onStart() completed");
        } catch (Exception e) {
            processBootstrapFailure(appName, serviceClient, e);
        }
    }

    private void initialiseService() {
        // create our services that use SDK clients, we use the convenient GatewayRegistry
        // as this class is granted access to the full suite as a
        // com.opentext.otag.sdk.client.v3.GatewayClientRegistry.RegistryUser
        LOG.info("Starting PushNotificationService");
        PushNotificationService pushNotificationService = new PushNotificationService(
                new NotificationsClient());

        // throw them into the AppWorks context for later use
        AWComponentContext.add(pushNotificationService);
    }

    private void processBootstrapFailure(String appName, ServiceClient serviceClient, Exception e) {
        LOG.error("We failed to report the deployment completed for app {}", appName);
        if (e instanceof APIException) {
            LOG.error("SDK call failed - {}", ((APIException) e).getCallInfo());
            throw new RuntimeException("Failed to report deployment outcome ", e);
        }
        try {
            // explicitly tell the Gateway we have failed
            serviceClient.completeDeployment(
                    new DeploymentResult(appName + " deployment failed," + e.getMessage()));
            LOG.info(String.format("%s deployment failed", appName), e);
        } catch (APIException e1) {
            // API was unreachable
            throw new RuntimeException("Failed to report deployment outcome", e1);
        }
    }

}
