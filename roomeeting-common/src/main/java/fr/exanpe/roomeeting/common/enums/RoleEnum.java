package fr.exanpe.roomeeting.common.enums;

public enum RoleEnum
{
    ADMIN(1L), USER(3L);

    private long code;

    private RoleEnum(long code)
    {
        this.code = code;
    }

    public long getCode()
    {
        return code;
    }
}
