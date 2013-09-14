package org.zeropage.gdg.morningTypeHuman.model;

import java.io.Serializable;

/**
 * Created by Skywave on 13. 9. 14.
 */
public class DayOfWeek implements Serializable, Cloneable {
    public boolean mon;
    public boolean tue;
    public boolean wed;
    public boolean thu;
    public boolean fri;
    public boolean sat;
    public boolean sun;

    public DayOfWeek() {
        mon = false;
        tue = false;
        wed = false;
        thu = false;
        fri = false;
        sat = false;
        sun = false;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
