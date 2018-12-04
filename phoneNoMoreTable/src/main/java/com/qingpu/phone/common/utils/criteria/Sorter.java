package com.qingpu.phone.common.utils.criteria;

import com.qingpu.phone.common.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qp on 2017-08-17.
 */
public class Sorter  implements Serializable {
    private static final long serialVersionUID = -6373394706784489139L;
    private List<Sort> sorts = new ArrayList();

    public Sorter() {
    }

    public List<Sorter.Sort> getSorts() {
        return this.sorts;
    }

    public Sorter asc(String property) {
        Iterator i$ = this.sorts.iterator();

        while(i$.hasNext()) {
            Sorter.Sort sort = (Sorter.Sort)i$.next();
            if(StringUtil.compare(property, sort.property)) {
                this.sorts.remove(sort);
                break;
            }
        }

        this.sorts.add(new Sorter.Sort(property, Sorter.Type.ASCENDING));
        return this;
    }

    public Sorter desc(String property) {
        Iterator i$ = this.sorts.iterator();

        while(i$.hasNext()) {
            Sorter.Sort sort = (Sorter.Sort)i$.next();
            if(StringUtil.compare(property, sort.property)) {
                this.sorts.remove(sort);
                break;
            }
        }

        this.sorts.add(new Sorter.Sort(property, Sorter.Type.DESCENDING));
        return this;
    }

    public static class Sort implements Serializable {
        private static final long serialVersionUID = -1599386645244047497L;
        public String property;
        public Sorter.Type type;

        private Sort(String property, Sorter.Type type) {
            this.property = property;
            this.type = type;
        }

        public boolean isAscending() {
            return this.type == Sorter.Type.ASCENDING;
        }

        public String toString() {
            return this.property + " " + (this.type == Sorter.Type.ASCENDING?"asc":"desc");
        }
    }

    public static enum Type {
        ASCENDING,
        DESCENDING;

        private Type() {
        }
    }
}
