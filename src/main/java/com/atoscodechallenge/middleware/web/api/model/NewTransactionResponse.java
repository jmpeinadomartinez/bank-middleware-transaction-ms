package com.atoscodechallenge.middleware.web.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * New transaction object response
 */
@ApiModel(description = "New transaction object response")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-12T17:23:17.306+02:00")

@JsonInclude(Include.NON_NULL)
public class NewTransactionResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("reference")
  private String reference = null;

  public NewTransactionResponse reference(String reference) {
    this.reference = reference;
    return this;
  }

   /**
   * Get reference
   * @return reference
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewTransactionResponse newTransactionResponse = (NewTransactionResponse) o;
    return Objects.equals(this.reference, newTransactionResponse.reference);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewTransactionResponse {\n");
    
    sb.append("    reference: ").append(toIndentedString(reference)).append("\n");
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

