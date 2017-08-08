import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DeserializeJSON {

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    //this method is called by UpdateBoards to create furniture items from JSON files.
    public Furniture deserializeJSON() throws IOException{
        System.out.println("Path of Furniture JSON file:");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();

        String JSONString = readFile(path, Charset.defaultCharset());

        Gson gson = new Gson();
        Furniture item = gson.fromJson(JSONString, Furniture.class);

        return item;
    }

    //run this method to test if the item is deserializing properly.
    public static void main(String[] args) throws IOException {
        System.out.println("Path of Furniture JSON file:");
        Scanner s = new Scanner(System.in);
        String path = s.nextLine();

        String JSONString = readFile(path, Charset.defaultCharset());

        System.out.println(JSONString);

        Gson gson = new Gson();
        Furniture item = gson.fromJson(JSONString, Furniture.class);

        System.out.println(item);
    }

}
