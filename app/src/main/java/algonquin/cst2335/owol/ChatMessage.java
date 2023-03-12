package algonquin.cst2335.owol;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
@ColumnInfo(name="message")
  protected  String message;
    @ColumnInfo(name="TimeSent")
   protected String timeSent;
    @ColumnInfo(name="SendOrReceive")
   protected boolean isSentButton;

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id")
    public int Id;

    public ChatMessage()
    {

    }
   public ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }


    public  String Getmessage()
    {
        return message;
    }

    public String GetTime()
    {
        return timeSent;
    }

    public  boolean IsSent()
    {
        return isSentButton;
    }
}
