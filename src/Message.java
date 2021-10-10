import java.io.Serial;
import java.io.Serializable;

public class Message implements Serializable {

  @Serial
  private static final long serialVersionUID = -2723363051271966964L;
  public String content;
  public Message(String content){
    this.content = content;
  }
}
