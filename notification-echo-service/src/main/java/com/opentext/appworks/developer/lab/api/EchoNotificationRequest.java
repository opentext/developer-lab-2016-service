/**
 * Copyright © 2016 Open Text.  All Rights Reserved.
 */
package com.opentext.appworks.developer.lab.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EchoNotificationRequest {

    /**
     * The id by which the Gateway knows the connecting client.
     */
    private String clientId;

    /**
     * This is the "target" that will be used in the push notification to send directly to
     * a specific app on the container.
     */
    private String appId;

    /**
     * This should be a valid URL that will be embedded in the payload sent to the app.
     */
    private String link;

    @JsonCreator
    public EchoNotificationRequest(@JsonProperty("clientId")  String clientId,
                                   @JsonProperty("appId") String appId,
                                   @JsonProperty("link") String link) {
        this.clientId = clientId;
        this.appId = appId;
        this.link = link;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAppId() {
        return appId;
    }

    public String getLink() {
        return link;
    }

    public boolean isPopulated() {
        return clientId != null && !clientId.trim().isEmpty() &&
                appId != null && !appId.trim().isEmpty() &&
                link != null && !link.trim().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EchoNotificationRequest that = (EchoNotificationRequest) o;

        return clientId != null ? clientId.equals(that.clientId) :
                that.clientId == null && (appId != null ? appId.equals(that.appId) :
                        that.appId == null && (link != null ? link.equals(that.link) :
                                that.link == null));
    }

    @Override
    public int hashCode() {
        int result = clientId != null ? clientId.hashCode() : 0;
        result = 31 * result + (appId != null ? appId.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EchoNotificationRequest{" +
                "clientId='" + clientId + '\'' +
                ", appId='" + appId + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

}
