import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Language
{
    String name;
    List<String> trainingFiles = new ArrayList<>();
    List<int[]> trainingVectors = new ArrayList<>();
    double[] weights;
    static final double alpha = 0.5;
    static double threshold = 1;

    public void addTrainingFile(String trainingFile)
    {
        trainingFiles.add(trainingFile);
    }
    public void addTrainingVector(String trainingFile)
    {
        int[] tmp = new int[26];
        for(int i=0; i<trainingFile.length(); i++)
            if (trainingFile.charAt(i)>=65&&trainingFile.charAt(i)<=90)
                tmp[trainingFile.charAt(i)-65]++;
        trainingVectors.add(tmp);

    }
    public int[] textToArray(String text)
    {
        int[] tmp = new int[26];
        for(int i=0; i<text.length(); i++)
            if (text.charAt(i)>=65&&text.charAt(i)<=90)
                tmp[text.charAt(i)-65]++;
        return tmp;
    }

    public int getValue(Language language)
    {
        if(this.getName().equals(language.getName()))
            return 1;
        else
            return 0;
    }

    public List<int[]> getTrainingVectors() {
        return trainingVectors;
    }

    public Language(String name)
    {
        this.name = name;
        weights=new Random().doubles(-5,5).limit(26).toArray();
    }

    public void simple(List<Language> l)
    {
        boolean stop;
        do
        {
            stop = false;
            for(Language lang: l)
            {
                for( int[] vector : lang.getTrainingVectors())
                {
                    double s;
                    while (true)
                    {
                        s=0;
                        for (int i=0; i<vector.length; i++)
                            s+=weights[i]*vector[i];

                        if((this.getValue(lang)==1&&s<threshold)||(this.getValue(lang)==0&&s>=threshold))
                        {
                            stop=true;
                            threshold = threshold+alpha*(this.getValue(lang)-(s>=threshold?1:0))*-1;
                            //threshold = threshold+alpha*(this.getValue(l)-(s>=threshold?1:0));
                            for (int i=0; i<weights.length; i++)
                                weights[i]=weights[i] + alpha*(this.getValue(lang)-(s>=threshold?1:0))*(vector[i]);
                        }
                        else
                        {
                            break;
                        }

                    }
                }
            }

        }
        while (stop);
    }

    public String check(String text)
    {
        int[] v = textToArray(text);
        double s=0;
        for (int i=0; i<v.length; i++)
            s+=weights[i]*v[i];

        if(s<threshold)
            return "other";
        else
            return this.getName();
    }
    public String check(int[] vector)
    {
        double s=0;
        for (int i=0; i<vector.length; i++)
            s+=weights[i]*vector[i];
        if(s<threshold)
            return "other";
        else
            return this.getName();
    }

    public String getName() {
        return name;
    }

}
