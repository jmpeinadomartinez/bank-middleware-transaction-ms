package com.atoscodechallenge.middleware.web.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * New transaction object request
 */
@ApiModel(description = "New transaction object request")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-13T00:57:10.307+02:00")

public class NewTransactionRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("reference")
  private String reference = null;

  @JsonProperty("account_iban")
  private String accountIban = null;

  @JsonProperty("date")
  private String date = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("fee")
  private Double fee = null;

  @JsonProperty("description")
  private String description = null;

  public NewTransactionRequest reference(String reference) {
    this.reference = reference;
    return this;
  }

   /**
   * Get reference
   * @return reference
  **/
  @ApiModelProperty(value = "")


  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public NewTransactionRequest accountIban(String accountIban) {
    this.accountIban = accountIban;
    return this;
  }

   /**
   * Get accountIban
   * @return accountIban
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getAccountIban() {
    return accountIban;
  }

  public void setAccountIban(String accountIban) {
    this.accountIban = accountIban;
  }

  public NewTransactionRequest date(String date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(value = "")


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public NewTransactionRequest amount(Double amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public NewTransactionRequest fee(Double fee) {
    this.fee = fee;
    return this;
  }

   /**
   * Get fee
   * @return fee
  **/
  @ApiModelProperty(value = "")


  public Double getFee() {
    return fee;
  }

  public void setFee(Double fee) {
    this.fee = fee;
  }

  public NewTransactionRequest description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewTransactionRequest newTransactionRequest = (NewTransactionRequest) o;
    return Objects.equals(this.reference, newTransactionRequest.reference) &&
        Objects.equals(this.accountIban, newTransactionRequest.accountIban) &&
        Objects.equals(this.date, newTransactionRequest.date) &&
        Objects.equals(this.amount, newTransactionRequest.amount) &&
        Objects.equals(this.fee, newTransactionRequest.fee) &&
        Objects.equals(this.description, newTransactionRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference, accountIban, date, amount, fee, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewTransactionRequest {\n");
    
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
    sb.append("    accountIban: ").append(toIndentedString(accountIban)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    fee: ").append(toIndentedString(fee)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

