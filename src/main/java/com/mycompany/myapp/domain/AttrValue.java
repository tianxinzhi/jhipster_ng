package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AttrValue.
 */
@Entity
@Table(name = "attr_value")
public class AttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attr_name")
    private String attrName;

    @Column(name = "attr_value")
    private String attrValue;

    @Column(name = "remark")
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public AttrValue attrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public AttrValue attrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getRemark() {
        return remark;
    }

    public AttrValue remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttrValue)) {
            return false;
        }
        return id != null && id.equals(((AttrValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AttrValue{" +
            "id=" + getId() +
            ", attrName='" + getAttrName() + "'" +
            ", attrValue='" + getAttrValue() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
