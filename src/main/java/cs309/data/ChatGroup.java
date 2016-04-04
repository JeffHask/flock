package cs309.data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "chat_group")
public class ChatGroup {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatUser> chatUsers;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatMessage> chatMessages;

    @ManyToOne
    @JoinColumn(name = "chat_admin_id", referencedColumnName = "id")
    private User chatAdmin;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "chat_name")
    private String chatName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public User getChatAdmin() {
        return chatAdmin;
    }

    public void setChatAdmin(User chatAdmin) {
        this.chatAdmin = chatAdmin;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
