package fr.exanpe.roomeeting.domain.database;

import java.util.Comparator;

public class DatabaseVersionComparator implements Comparator<DatabaseVersion>
{
    @Override
    public int compare(DatabaseVersion dv1, DatabaseVersion dv2)
    {
        String[] v1 = dv1.getVersion().split("\\.");
        String[] v2 = dv2.getVersion().split("\\.");

        for (int i = 0; i < v1.length; i++)
        {
            // dv1 is after dv2
            if (i >= v2.length)
            {
                // so return greater than
                return 1;
            }
            if (Integer.parseInt(v1[i]) != Integer.parseInt(v2[i])) { return Integer.parseInt(v1[i]) - Integer.parseInt(v2[i]); }
        }

        // 1.0 vs 1.0.1, v1 is lesser
        if (v2.length > v1.length) { return -1; }

        return 0;
    }
}
