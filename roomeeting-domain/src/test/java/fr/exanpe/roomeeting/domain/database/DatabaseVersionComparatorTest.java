package fr.exanpe.roomeeting.domain.database;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.unitils.UnitilsTestNG;

import fr.exanpe.roomeeting.domain.database.versions.OnlyDatabaseVersion;

public class DatabaseVersionComparatorTest extends UnitilsTestNG
{
    private static final DatabaseVersionComparator comparator = new DatabaseVersionComparator();

    @Test
    public void compareEqual()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("1.0.50");
        DatabaseVersion v2 = new OnlyDatabaseVersion("1.0.50");

        Assert.assertEquals(comparator.compare(v1, v2), 0);
    }

    @Test
    public void compare1stAfter()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("1.0.50");
        DatabaseVersion v2 = new OnlyDatabaseVersion("1.0.49");

        Assert.assertTrue(comparator.compare(v1, v2) > 0);
    }

    @Test
    public void compare1stAfter2()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("1.0.1");
        DatabaseVersion v2 = new OnlyDatabaseVersion("1.0");

        Assert.assertTrue(comparator.compare(v1, v2) > 0);
    }

    @Test
    public void compare1stAfter3()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("10.0.1");
        DatabaseVersion v2 = new OnlyDatabaseVersion("7.50.50");

        Assert.assertTrue(comparator.compare(v1, v2) > 0);
    }

    @Test
    public void compare1stBefore()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("0.10.50");
        DatabaseVersion v2 = new OnlyDatabaseVersion("1.0.49");

        Assert.assertTrue(comparator.compare(v1, v2) < 0);
    }

    @Test
    public void compare1stBefore2()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("1.0");
        DatabaseVersion v2 = new OnlyDatabaseVersion("1.0.8");

        Assert.assertTrue(comparator.compare(v1, v2) < 0);
    }

    @Test
    public void compare1stBefore3()
    {
        DatabaseVersion v1 = new OnlyDatabaseVersion("0.1");
        DatabaseVersion v2 = new OnlyDatabaseVersion("0.10");

        Assert.assertTrue(comparator.compare(v1, v2) < 0);
    }
}
