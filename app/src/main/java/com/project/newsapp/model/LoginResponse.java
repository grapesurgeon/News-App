package com.project.newsapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private String status;

    private String message;

    private Data data;

    private String token;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    class Data{
        private String id;

        private String email;

        @SerializedName("oauth_uid")
        private String oauthUid;

        @SerializedName("oauth_provider")
        private String oauthProvider;

        private String username;

        @SerializedName("full_name")
        private String fullName;

        private String avatar;

        private String banned;

        @SerializedName("last_login")
        private String lastLogin;

        @SerializedName("last_activity")
        private String lastActivity;

        @SerializedName("date_created")
        private String dateCreated;

        @SerializedName("forgot_exp")
        private String forgotExp;

        @SerializedName("remember_time")
        private String rememberTime;

        @SerializedName("remember_exp")
        private String rememberExp;

        @SerializedName("verification_code")
        private String verificationCode;

        @SerializedName("top_secret")
        private String topSecret;

        @SerializedName("ip_address")
        private String ipAddress;

        @SerializedName("company_id")
        private String companyId;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getOauthUid() {
            return oauthUid;
        }

        public String getOauthProvider() {
            return oauthProvider;
        }

        public String getUsername() {
            return username;
        }

        public String getFullName() {
            return fullName;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getBanned() {
            return banned;
        }

        public String getLastLogin() {
            return lastLogin;
        }

        public String getLastActivity() {
            return lastActivity;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public String getForgotExp() {
            return forgotExp;
        }

        public String getRememberTime() {
            return rememberTime;
        }

        public String getRememberExp() {
            return rememberExp;
        }

        public String getVerificationCode() {
            return verificationCode;
        }

        public String getTopSecret() {
            return topSecret;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public String getCompanyId() {
            return companyId;
        }
    }
}
