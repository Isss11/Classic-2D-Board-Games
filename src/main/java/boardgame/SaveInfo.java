package boardgame;

/*I refactored one of the files from Professor McCuaig's workshop, but this is mostly her code*/
import java.nio.file.Files;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.io.IOException;

public class SaveInfo {
    /**
     * Saves a given object to a file.
     * @param toSave takes an object of type Saveable and uses it's classes to save that item.
     * @param filename name of the file to save to.
     * @param relativePath relative path of the file (ex. 'assets')
     * @author Judi McCuaig
     * @author Isaiah Sinclair
     */
    public static void save(Saveable toSave, String filename, String relativePath){

        Path path = FileSystems.getDefault().getPath(relativePath,filename);

        try{
            Files.writeString(path, toSave.getStringToSave());
        }catch(Exception e){
            throw(new RuntimeException(e.getMessage()));
        }
  

  //Checked exception--> exception class

// Unchecked exceptions ---> runtimeexception
    }

    /**
     * Takes in a string from an object and the saveable object to load the values into that object.
     * @param toLoad saveable object we will load items into.
     * @param filename name of the file to load stuff from.
     * @param relativePath relative path of the file (ex. assets)
     * @author Judi McCuaig
     * @author Isaiah Sinclair
     */
    public static void load(Saveable toLoad, String filename, String relativePath){
        Path path = FileSystems.getDefault().getPath(relativePath,filename);
        try{
            String fileLines = String.join("\n",Files.readAllLines(path));  //might get double lines here
            toLoad.loadSavedString(fileLines);
        }catch(IOException e){
            throw(new RuntimeException(e.getMessage()));
        }
    }

}