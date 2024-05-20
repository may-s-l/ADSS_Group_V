import java.until.*;
public class jobs {

    private List<String> jobs=new List("cashier", "storekeeper", "carrier");

    public void addJob(String j){
        int flag=0
        for String i:jobs{
            if i == j
                flag=1
                break;
        }
        if flag == 0
            jobs.add(j);
        else
        System.out.println("job alredy exist");
    }

    public boolean isJobExist(String j){
        int flag=0
        for String i:jobs{
            if i == j
            flag=1
            break;
        }
        if flag == 0
            return False;
        else
            return True;
    }










}