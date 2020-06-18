package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AttrAndValue.
 */
@Entity
@Table(name = "attr_and_value")
public class AttrAndValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attr_name")
    private String attrName;

    @Column(name = "attr_value")
    private String attrValue;

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

    public AttrAndValue attrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public AttrAndValue attrValue(String attrValue) {
        this.attrValue = attrValue;
        return this;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttrAndValue)) {
            return false;
        }
        return id != null && id.equals(((AttrAndValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AttrAndValue{" +
            "id=" + getId() +
            ", attrName='" + getAttrName() + "'" +
            ", attrValue='" + getAttrValue() + "'" +
            "}";
    }
}
