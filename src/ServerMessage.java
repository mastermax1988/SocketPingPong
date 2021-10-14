import java.io.Serial;
import java.io.Serializable;

public class ServerMessage implements Serializable {

  @Serial
  private static final long serialVersionUID = -1036384894552495007L;
  private String content;
  private String from;

  /**
   * Message being sent from the server to the clients, including additional information.
   *
   * @param content the content of the message
   * @param from the source client's name
   */
  public ServerMessage(String content, String from) {
    this.content = content;
    this.from = from;
  }

  public String getContent() {
    return content;
  }

  public String getFrom() {
    return from;
  }
}
