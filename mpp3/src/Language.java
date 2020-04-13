import java.util.ArrayList;
import java.util.List;

public class Language
{
    String name;
    List<String> trainingFiles = new ArrayList<>();
    int[] vector = new int[26];

    public void addTrainingFile(String trainingFile)
    {
        trainingFiles.add(trainingFile);
    }

    public Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTrainingFiles() {
        return trainingFiles;
    }

    public void setTrainingFiles(List<String> trainingFiles) {
        this.trainingFiles = trainingFiles;
    }

    public int[] getVector() {
        return vector;
    }

    public void setVector(int[] vector) {
        this.vector = vector;
    }
}
