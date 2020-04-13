package common;

import common.beans.BroadcastMsg;
import common.beans.DirectMsg;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * The type Message processor.
 */
public abstract class MessageProcessor {
  private DataInputStream dataInputStream;

  /**
   * Instantiates a new Message processor.
   *
   * @param dataInputStream the data input stream
   */
  public MessageProcessor(DataInputStream dataInputStream) {
    this.dataInputStream = dataInputStream;
  }

  /**
   * Read a string from input stream.
   *
   * @return the string
   * @throws IOException the io exception
   */
  protected String readString() throws IOException {
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] usernameBytes = new byte[len];
    this.dataInputStream.read(usernameBytes);
    return new String(usernameBytes, 0, len);
  }

  /**
   * Read a byte array from input stream.
   *
   * @return the byte array
   * @throws IOException the io exception
   */
  protected byte[] readByteArray() throws IOException {
    int len = this.dataInputStream.readInt();
    this.dataInputStream.read();
    byte[] result = new byte[len];
    this.dataInputStream.read(result, 0, len);
    return result;
  }

  /**
   * Process broadcast message, reads a broadcast message from input stream.
   *
   * @return the broadcast msg
   * @throws IOException the io exception
   */
  public BroadcastMsg processBroadcastMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    byte[] content = this.readByteArray();
    return new BroadcastMsg(sender, content);
  }

  /**
   * Process direct message, reads a direct message from input stream.
   *
   * @return the direct msg
   * @throws IOException the io exception
   */
  public DirectMsg processDirectMsg() throws IOException {
    String sender = this.readString();
    this.dataInputStream.read();
    String recipient = this.readString();
    this.dataInputStream.read();
    byte[] content = this.readByteArray();
    return new DirectMsg(sender, recipient, content);
  }
}
