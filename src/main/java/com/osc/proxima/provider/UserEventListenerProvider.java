package com.osc.proxima.provider;

import com.osc.utils.HttpUtils;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

import java.util.Map;

/**
 * @author WuChengguo
 * @TODO
 * @date 2021/3/6 9:08 下午
 */
public class UserEventListenerProvider implements EventListenerProvider {

    private static final String RESOURCE_TYPE="USER";
    public UserEventListenerProvider() {
    }

    @Override
    public void onEvent(Event event) {
        // System.out.println("Event Occurred:"+toString(event));
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
         System.out.println("Admin Event Occurred:"+ toString(adminEvent));
        System.out.println("========================");
        if (RESOURCE_TYPE.equals(adminEvent.getResourceTypeAsString())){
            System.out.println(toJSON(adminEvent));
            HttpUtils.postData(toJSON(adminEvent));
        }
    }

    @Override
    public void close() {

    }

    private String toString(Event event) {

        StringBuilder sb = new StringBuilder();
        sb.append("type=");
        sb.append(event.getType());
        sb.append(", realmId=");
        sb.append(event.getRealmId());
        sb.append(", clientId=");
        sb.append(event.getClientId());
        sb.append(", userId=");
        sb.append(event.getUserId());
        sb.append(", ipAddress=");
        sb.append(event.getIpAddress());
        if (event.getError() != null) {
            sb.append(", error=");
            sb.append(event.getError());
        }

        if (event.getDetails() != null) {
            for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
                sb.append(", ");
                sb.append(e.getKey());
                if (e.getValue() == null || e.getValue().indexOf(' ') == -1) {
                    sb.append("=");
                    sb.append(e.getValue());
                } else {
                    sb.append("='");
                    sb.append(e.getValue());
                    sb.append("'");
                }
            }
        }

        return sb.toString();
    }

    private String toString(AdminEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("time=").append(event.getTime());
        sb.append(", realmId=").append(event.getRealmId());
        sb.append(", resourceType=").append(event.getResourceType());
        sb.append(", operationType=").append(event.getOperationType().toString());
        sb.append(", resourcePath=").append(event.getResourcePath());
        sb.append(", representation=").append(event.getRepresentation());
        sb.append(", authDetails=[");
        sb.append("realmId=").append(event.getAuthDetails().getRealmId());
        sb.append(", clientId=").append(event.getAuthDetails().getClientId());
        sb.append(", userId=").append(event.getAuthDetails().getUserId());
        sb.append("]");
        return sb.toString();
    }

    private String toJSON(AdminEvent event){
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"realmId\":\"").append(event.getRealmId()).append("\",");
        sb.append("\"resourceType\":\"").append(event.getResourceTypeAsString()).append("\",");
        sb.append("\"operationType\":\"").append(event.getOperationType()).append("\",");
        sb.append("\"userId\":\"").append(event.getResourcePath().replace("users/","")).append("\",");
        sb.append("\"representation\":").append(event.getRepresentation());
        sb.append("}");
        return sb.toString();
    }
}
