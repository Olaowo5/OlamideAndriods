package algonquin.cst2335.owol;

public class ChatMessage {

    String message;
    String timeSent;
    boolean isSentButton;

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
