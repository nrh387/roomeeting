package fr.exanpe.roomeeting.common.enums;

public enum AuthorityEnum
{
    AUTH_MANAGE_USERS(1L), AUTH_MANAGE_SITES(2L), AUTH_MANAGE_SITE(3L), AUTH_MANAGE_PARAMETERS(4L), AUTH_MANAGE_ROOMFEATURES(5L), AUTH_BOOK(6L);

    private long code;

    private AuthorityEnum(long code)
    {
        this.code = code;
    }

    public long getCode()
    {
        return code;
    }
}
