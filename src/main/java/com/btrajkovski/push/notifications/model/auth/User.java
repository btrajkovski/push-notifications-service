package com.btrajkovski.push.notifications.model.auth;

import com.btrajkovski.push.notifications.model.Application;
import com.btrajkovski.push.notifications.model.Notification;
import com.btrajkovski.push.notifications.model.enums.Role;
import com.btrajkovski.push.notifications.model.enums.SocialMediaService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends SocialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 100,nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_sign_in_provider", length = 20)
    private SocialMediaService socialSignInProvider;

    @Column(name = "image_url", length = 200, nullable = false, unique = false)
    private String imageUrl;

    @Transient
    private String fullName;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private List<Application> applications;

    @JsonIgnore
    @Transient
    private String userId;

    @Column(unique = true)
    private String apiKey;

    public static int apiKeyLength = 30;

    @OneToMany
    @JsonIgnore
    private List<Notification> notifications;

    public User() {
        super("username", "password", new HashSet<>());
    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SocialMediaService getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public void setSocialSignInProvider(SocialMediaService socialSignInProvider) {
        this.socialSignInProvider = socialSignInProvider;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", socialSignInProvider=" + socialSignInProvider +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public static class Builder {

        private Long id;

        private String username;

        private String firstName;

        private String lastName;

        private String password;

        private Role role;

        private String email;

        private SocialMediaService socialSignInProvider;

        private Set<GrantedAuthority> authorities;

        private String imageUrl;

        public Builder() {
            this.authorities = new HashSet<>();
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            if (password == null) {
                password = "SocialUser";
            }

            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            this.authorities.add(authority);

            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public User build() {
            User user = new User(username, password, authorities);

            user.id = id;
            user.firstName = firstName;
            user.lastName = lastName;
            user.role = role;
            user.socialSignInProvider = socialSignInProvider;
            user.email = email;
            user.imageUrl = imageUrl;

            return user;
        }
    }
}
