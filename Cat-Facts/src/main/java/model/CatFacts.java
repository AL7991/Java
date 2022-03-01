
package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CatFacts {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("used")
    @Expose
    private Boolean used;

    @Override
	public String toString() {
		return "CatFacts [status=" + status + ", id=" + id + ", user=" + user + ", text=" + text + ", v=" + v
				+ ", source=" + source + ", updatedAt=" + updatedAt + ", type=" + type + ", createdAt=" + createdAt
				+ ", deleted=" + deleted + ", used=" + used + "]";
	}

	public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

}
