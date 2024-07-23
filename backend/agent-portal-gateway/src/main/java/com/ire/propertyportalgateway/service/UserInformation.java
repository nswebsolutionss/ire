package com.ire.propertyportalgateway.service;

import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UserInformation {
    private static final Logger LOGGER = LogManager.getLogger();

    private final String userId;
    private final String email_address;
    private final String organizationId;
    private final String organizationName;
    private final String userRole;

    public UserInformation(String userId, String emailAddress, String organizationId, String organizationName, String userRole) {
        this.userId = userId;
        email_address = emailAddress;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.userRole = userRole;
    }

    public static UserInformation parseToken(String token) {

        // Split token by . to get body
        String[] tokenParts = token.split("\\.");

        // Base64 decode
        JsonObject jwtBody = new JsonObject((new String(Base64.getDecoder().decode(tokenParts[1]), StandardCharsets.UTF_8)));

        String userId = jwtBody.getString("user_id");
        String email_address = jwtBody.getString("email");

        String organizationId = null;
        String organizationName = null;
        String userRole = null;
        JsonObject organizationIdByOrgMemberInfoGroup = jwtBody.getJsonObject("org_id_to_org_member_info");
        if (organizationIdByOrgMemberInfoGroup.stream().findFirst().isPresent()) {
            organizationId = organizationIdByOrgMemberInfoGroup.stream().findFirst().get().getKey();
            JsonObject organizationGroup = organizationIdByOrgMemberInfoGroup.getJsonObject(organizationId);
            organizationName = organizationGroup.getString("org_name");
            userRole = organizationGroup.getString("user_role");
        } else {
            LOGGER.error("Unable to parse token into user object: \n" +
                    "organizationId: " + organizationId + "\n" +
                    "organizationName: " + organizationName + "\n" +
                    "userRole: " + userRole
            );
            return null;
        }

        return new UserInformation(
                userId,
                email_address,
                organizationId,
                organizationName,
                userRole
        );
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail_address() {
        return email_address;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getUserRole() {
        return userRole;
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "userId='" + userId + '\'' +
                ", email_address='" + email_address + '\'' +
                ", organizationId='" + organizationId + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
