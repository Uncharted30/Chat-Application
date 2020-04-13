package client;

import static org.junit.Assert.*;

import common.beans.BroadcastMsg;
import common.beans.DirectMsg;
import common.beans.DisconnectMsg;
import common.beans.InsultMsg;
import common.beans.UserQuery;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.junit.Before;
import org.junit.Test;

public class UserInterfaceTest {
  String username = "haolan";
  private UserInterface getUI() {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    return new UserInterface(username, bufferedReader);
  }

  private void setSystemIn(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
  }

  @Test
  public void TestWrongCMD() throws IOException {
    setSystemIn("sdfsf");
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertNull(userInterface.getMessage());
  }

  @Test
  public void TestHelpCMD() throws IOException {
    setSystemIn("?");
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertNull(userInterface.getMessage());
  }

  @Test
  public void TestLogoffCMD() throws IOException {
    DisconnectMsg disconnectMsg = new DisconnectMsg(username);
    setSystemIn("logoff");
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertEquals(disconnectMsg, userInterface.getMessage());
  }

  @Test
  public void TestShowUserCMD() throws IOException {
    UserQuery userQuery = new UserQuery(username);
    setSystemIn("who");
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertEquals(userQuery, userInterface.getMessage());
  }

  @Test
  public void TestSendDirectMsg() throws IOException {
    String msg = "sdf";
    String recipient = "pan";
    DirectMsg directMsg = new DirectMsg(username, recipient, msg.getBytes());
    setSystemIn("@user" + System.lineSeparator() + recipient + System.lineSeparator() + msg);
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertEquals(directMsg, userInterface.getMessage());
  }

  @Test
  public void TestSendBroadcastMsgCMD() throws IOException {
    String msg = "hahah";
    BroadcastMsg broadcastMsg = new BroadcastMsg(username, msg.getBytes());
    setSystemIn("@all" + System.lineSeparator() + msg);
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertEquals(broadcastMsg, userInterface.getMessage());
  }

  @Test
  public void TestSendInsultMsgCMD() throws IOException {
    String recipient = "pan";
    InsultMsg insultMsg = new InsultMsg(username, recipient);
    setSystemIn("!user" + System.lineSeparator() + recipient);
    UserInterface userInterface = getUI();
    userInterface.getMessage();
    assertEquals(insultMsg, userInterface.getMessage());
  }

}