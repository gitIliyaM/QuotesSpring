package models;
import jakarta.persistence.*;
@Entity
@Table(name = "\"Chat2\"")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "\"chatId\"", nullable = false)
    private Long chatId;
    @Column(name = "\"lastId\"", nullable = false)
    private int lastId;
    public Long getId(){ return id;}
    public void setId(Long id){ this.id = id;}
    public Long getChatId(){ return chatId;}
    public void setChatId(Long chatId){ this.chatId = chatId;}
    public int getLastId(){ return lastId;}
    public void setLastId(int lastId){ this.lastId = lastId;}
}
