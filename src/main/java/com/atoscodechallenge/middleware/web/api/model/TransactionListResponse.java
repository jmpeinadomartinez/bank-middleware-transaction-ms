package com.atoscodechallenge.middleware.web.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import javax.validation.Valid;

/**
 * Transaction list response
 */
@ApiModel(description = "Transaction list response")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-12T17:23:17.306+02:00")

@JsonInclude(Include.NON_NULL)
public class TransactionListResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("totalTransaction")
  private Integer totalTransaction = 0;

  @JsonProperty("transactions")
  private List<Transaction> transactions = null;

  public TransactionListResponse totalTransaction(Integer totalTransaction) {
    this.totalTransaction = totalTransaction;
    return this;
  }

   /**
   * Number of transactions
   * @return totalTransaction
  **/
  @ApiModelProperty(value = "Number of transactions")


  public Integer getTotalTransaction() {
    return totalTransaction;
  }

  public void setTotalTransaction(Integer totalTransaction) {
    this.totalTransaction = totalTransaction;
  }

  public TransactionListResponse transactions(List<Transaction> transactions) {
    this.transactions = transactions;
    return this;
  }

  public TransactionListResponse addTransactionsItem(Transaction transactionsItem) {
    if (this.transactions == null) {
      this.transactions = new ArrayList<Transaction>();
    }
    this.transactions.add(transactionsItem);
    return this;
  }

   /**
   * Transactions list related to a iban
   * @return transactions
  **/
  @ApiModelProperty(value = "Transactions list related to a iban")

  @Valid

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionListResponse transactionListResponse = (TransactionListResponse) o;
    return Objects.equals(this.totalTransaction, transactionListResponse.totalTransaction) &&
        Objects.equals(this.transactions, transactionListResponse.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalTransaction, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionListResponse {\n");
    
    sb.append("    totalTransaction: ").append(toIndentedString(totalTransaction)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
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

