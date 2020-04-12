package common.beans;

import common.CommonConstants;
import common.ConvertUtil;
import java.util.Arrays;
import java.util.List;

public class QueryRes extends AbstractChatRoomProtocol {

  private List<String> userList;

  public QueryRes(List<String> userList) {
    this.userList = userList;
  }

  public List<String> getUserList() {
    return userList;
  }

  @Override
  public byte[] toByteArray() {
    return this.toByteArray(CommonConstants.QUERY_USER_RESPONSE, userList.size(), userList);
  }

  @Override
  public void print() {

  }

  public static void main(String[] args) {
    QueryRes queryRes = new QueryRes(Arrays.asList("Pan", "Lyh"));
    System.out.println(Arrays.toString(queryRes.toByteArray()));
  }
}