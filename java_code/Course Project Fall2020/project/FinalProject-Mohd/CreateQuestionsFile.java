import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateQuestionsFile {
     public static void main(String[] args) {

        try {
            FileOutputStream file = new FileOutputStream("questions.out");
            ObjectOutputStream output = new ObjectOutputStream(file);
            List<Question> ds=new ArrayList();
           // ds.add(new Question("Your Question Here", new String[]{"1.Choice", "2.Choice", "3.Choice", "4.Choice"}, correct answer number, number of points allocated));
            ds.add(new Question("This is Question 1 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 1, 1));
            ds.add(new Question("This is Question 2 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 3 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 3, 1));
            ds.add(new Question("This is Question 4 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 4, 1));
            ds.add(new Question("This is Question 5 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 6 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 7 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 8 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 3, 1));
            ds.add(new Question("This is Question 9 ?", new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 4, 1));
            ds.add(new Question("This is Question 10 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 4, 1));
            ds.add(new Question("This is Question 11 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 12 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 3, 1));
            ds.add(new Question("This is Question 13 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 3, 1));
            ds.add(new Question("This is Question 14 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 3, 1));
            ds.add(new Question("This is Question 15 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 16 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 17 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 4, 1));
            ds.add(new Question("This is Question 18 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 3, 1));
            ds.add(new Question("This is Question 19 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 2, 1));
            ds.add(new Question("This is Question 20 ?",new String[]{"1.Choice One", "2.Choice Two", "3.Choice Three", "4.Choice Four"}, 1, 1));
            output.writeObject(ds);           

            FileInputStream fileStream = new FileInputStream("D:/FinalProject-Mohd/questions.out");
            // Creating an object input stream
            ObjectInputStream objStream = new ObjectInputStream(fileStream);
            System.out.println("File Created : at D:/Mohd-Project/questions.out");
            output.flush();
            output.close();
            objStream.close();
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }
}
