package  shared.transferObjects;

public enum RequestType
{
    LOGIN,
    SUCCESSFUL_LOGIN,
    NON_EXISTENT_USERNAME,
    UPDATE_ACTIVE_USERS,
    GET_ACTIVE_USERS,
    RECEIVE_PUBLIC,

    DISCONNECT,
    RECEIVE_PRIVATE
}
