package vn.rin.blog.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Rin
 */
@Entity
@Data
@Table(name = "option")
public class Option implements Serializable {
    private static final long serialVersionUID = 2436696690653745208L;
    @Id
    private String id;
    private String option;
    private String options;
    private String value;
    private String category;


    public Integer getInValue() {
        if (this.value != null)
            return Integer.valueOf(this.value);
        return -1;
    }

    public long getLongValue() {
        if (this.value != null)
            return Long.valueOf(this.value);
        return -1l;
    }
}
