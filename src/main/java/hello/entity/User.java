package hello.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class User {
    private Integer id;
    private String username;
    private String avatar;
    private Instant createdAt;
    private Instant updatedAt;
    @JsonIgnore
    private String encryptedPassword;

    public User(String username, String encryptedPassword) {
        this.username = username;
        this.avatar = "";
        this.encryptedPassword=encryptedPassword;
        this.createdAt=Instant.now();
        this.updatedAt=Instant.now();
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
