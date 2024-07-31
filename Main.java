package Version2;



public class Main {

  public static void main(String[] args) {
    MyModel myModel = new MyModel();
    MyView myView = new MyView();

    MyController myController = new MyController(myModel, myView);
  }

}