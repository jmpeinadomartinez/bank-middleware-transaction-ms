package com.atoscodechallenge.middleware.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A MdwTransaction.
 */
@Entity
@Table(name = "mdw_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MdwTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 6)
    @Column(name = "reference", length = 6)
    private String reference;

    @NotNull
    @Size(max = 100)
    @Column(name = "account_iban", length = 100, nullable = false)
    private String accountIban;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "description")
    private String description;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public String getReference() {
        return reference;
    }

    public MdwTransaction reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAccountIban() {
        return accountIban;
    }

    public MdwTransaction accountIban(String accountIban) {
        this.accountIban = accountIban;
        return this;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public MdwTransaction date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public MdwTransaction amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public MdwTransaction fee(Double fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return description;
    }

    public MdwTransaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MdwTransaction mdwTransaction = (MdwTransaction) o;
        if (mdwTransaction.getReference() == null || getReference() == null) {
            return false;
        }
        return Objects.equals(getReference(), mdwTransaction.getReference());
    }    

    @Override
    public int hashCode() {
        return Objects.hashCode(getReference());
    }    

    @Override
    public String toString() {
        return "MdwTransaction{" +
            "reference='" + getReference() + "'" +
            ", accountIban='" + getAccountIban() + "'" +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", fee=" + getFee() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
