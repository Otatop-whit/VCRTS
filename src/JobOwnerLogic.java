import java.io.BufferedWriter;
import java.io.FileWriter;

public class JobOwnerLogic {

    public JobOwnerLogic(){
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("JobOwnerData.txt"));
            writer.write("Job Owner Data:");
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
