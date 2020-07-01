package vn.rin.blog.domain.entity;

import javax.persistence.*;

/**
 * @author Rin
 */
@Entity
@Table(name = "options")
public class OptionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 5)
    private int type;

    @Column(name = "key_", unique = true, length = 32)
    private String key;

    @Column(length = 300)
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
