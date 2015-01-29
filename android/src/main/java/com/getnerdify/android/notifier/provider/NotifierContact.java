package com.getnerdify.android.notifier.provider;

public class NotifierContact {

    public interface InstallationsColumns {
        String INSTALLATION_USER_EMAIL = "email";
        String INSTALLATION_USER_CELLPHONE = "celular";
    }

    public interface NotificationsColumns {
        String NOTIFICATION__CLASS = "Notification";
        String NOTIFICATION_ID = "objectId";
        String NOTIFICATION_TITLE = "title";
        String NOTIFICATION_DESCRIPTION = "description";
        String NOTIFICATION_IMAGE_URL = "imageUrl";
        String NOTIFICATION_CREATED_AT = "createdAt";
        String NOTIFICATION_UPDATED_AT = "updatedAt";
    }

}
