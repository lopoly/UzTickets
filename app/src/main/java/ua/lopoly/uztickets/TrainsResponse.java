
package ua.lopoly.uztickets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "error",
    "data"
})
public class TrainsResponse {

    @JsonProperty("value")
    private ArrayList<Value> value;
    @JsonProperty("error")
    private Boolean error;
    @JsonProperty("data")
    private Object data;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The value
     */
    @JsonProperty("value")
    public ArrayList<Value> getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    @JsonProperty("value")
    public void setValue(ArrayList<Value> value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The error
     */
    @JsonProperty("error")
    public Boolean getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    @JsonProperty("error")
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     * 
     * @return
     *     The data
     */
    @JsonProperty("data")
    public Object getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TrainsResponse{" +
                "value=" + value +
                ", error=" + error +
                ", data=" + data +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
