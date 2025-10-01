import job.model.JobOwner;
import job.service.JobOwnerService;
import job.service.JobOwnerServiceImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{

    public static void main(String args[]){
        JobOwner dataTwo = new JobOwner(2,"testUser2","2 hours", "oct 4");
        //new common.ui.LandingPage();

        JobOwnerService jobOwner = new JobOwnerServiceImpl();
        jobOwner.addJobOwner(dataTwo);
    }

  //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
  // to see how IntelliJ IDEA suggests fixing it.

}
