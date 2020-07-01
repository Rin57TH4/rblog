package vn.rin.blog.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Rin
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = 2436696690653745208L;

    @Id
    private String id;
    private String operation;
    private String operations;
    private String userId;
    private int code;
    private String dataId;
    private Date created;
    private String ip;
    private String ua;
}
