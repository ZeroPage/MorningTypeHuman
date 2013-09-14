package org.zeropage.gdg.morningTypeHuman.model;

import java.io.Serializable;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class AppStats implements Serializable {
    public static String intentKey="appstats";
    public int totalTries;
    public int totalSucceed;
    public int totalFailed;
    public int totalLaunched;
}
