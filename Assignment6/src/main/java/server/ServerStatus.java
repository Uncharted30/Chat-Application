package server;

public class ServerStatus {

  private Boolean isRunning;

  public ServerStatus(Boolean isRunning) {
    this.isRunning = isRunning;
  }

  public ServerStatus() {
    this.isRunning = true;
  }

  public Boolean getRunning() {
    return isRunning;
  }

  public void setRunning(Boolean running) {
    isRunning = running;
  }
}
