//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{

    public static void main(String args[]){
        JobOwner dataOne = new JobOwner(1,"testUser2","2 hours", "oct 4");
        //new LandingPage();
        new JobOwnerLogic(dataOne);
    }

  //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
  // to see how IntelliJ IDEA suggests fixing it.

}
