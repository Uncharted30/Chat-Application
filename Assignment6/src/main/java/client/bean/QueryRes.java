package client.bean;

import java.util.List;

public class QueryRes {
  List<String> users;

  public QueryRes(List<String> users) {
    this.users = users;
  }

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
  }
}
