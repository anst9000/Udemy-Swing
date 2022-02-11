package gui;

class ServerInfo {
  private String name;
  private int id;
  private boolean checked;

  public ServerInfo(String name, int id, boolean checked) {
    this.name = name;
    this.id = id;
    this.checked = checked;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  @Override
  public String toString() {
    return name;
  }
}