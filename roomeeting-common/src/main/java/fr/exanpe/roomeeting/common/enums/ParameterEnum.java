package fr.exanpe.roomeeting.common.enums;

public enum ParameterEnum
{
    HOUR_DAY_START(1L), HOUR_DAY_END(2L);

    private long code;

    private ParameterEnum(long code)
    {
        this.code = code;
    }

    public long getCode()
    {
        return code;
    }
}
