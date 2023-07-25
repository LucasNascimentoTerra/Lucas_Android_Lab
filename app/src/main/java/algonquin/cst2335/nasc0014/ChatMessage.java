package algonquin.cst2335.nasc0014;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @ColumnInfo(name="messages")
    public String messageText;

    @ColumnInfo(name="timeSent")
    public String timeSent;

    @ColumnInfo(name="SendOrReceive")
    public boolean SendOrReceive;

@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "id")
    public int id;

public ChatMessage(){};
    public ChatMessage(String m, String t, boolean sR) {
        messageText = m;
        timeSent = t;
        SendOrReceive = sR;


    }

    public String getMessage() {return messageText;}
    public String getTimeSent() {return timeSent;}
    public boolean isSentButton(boolean b) {return SendOrReceive;}

}
