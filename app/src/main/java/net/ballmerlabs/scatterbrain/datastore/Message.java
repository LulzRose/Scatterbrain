package net.ballmerlabs.scatterbrain.datastore;

/**
 * Basic data unit for a single message. 
 */
public class Message {



    public String subject;
    public String contents;
    public int ttl;
    public String replyto;
    public String luid;
    public String from;
    public String flags;
    public String sig;

    public Message(String subject, String contents, int ttl, String replyto, String luid,
                   String recipient, String from, String flags,  String sig) {
        this.subject = subject;
        this.contents = contents;
        this.ttl = ttl;
        this.replyto = replyto;
        this.luid = luid;
        this.from = from;
        this.flags = flags;
        this.sig = sig;
    }


}
