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
    int multiply=0;

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
    public int getValue(Language language)
    {
        if(this.getName().equals(language.getName()))
            return 1;
        else
            return 0;
    }

    public Language(String name)
    {
        this.name = name;
        weights=new Random().doubles(-5,5).limit(26).toArray();
    }

    public void   simple(Language l)
    {
        boolean stop;
        do
        {
            stop = false;
            for( int[] vector : trainingVectors)
            {
                double s;
                while (true)
                {
                    s=0;
                    for (int i=0; i<vector.length; i++)
                        s+=weights[i]*vector[i];

                    if((this.getValue(l)==1&&s<threshold)||(this.getValue(l)==0&&s>=threshold))
                    {
                        stop=true;
                        //threshold = threshold+alpha*(this.getValue(l)-(s>=threshold?1:0))*-1;
                        //threshold = threshold+alpha*(this.getValue(l)-(s>=threshold?1:0));
                        for (int i=0; i<weights.length; i++)
                            weights[i]=weights[i] + alpha*(this.getValue(l)-(s>=threshold?1:0))*(vector[i]);
                    }
                    else
                    {
                        break;
                    }

                }
            }
        }
        while (stop);
        multiply++;
    }

//    public boolean learn(Language l,boolean stop)
//    {
//        int vectorCounter=0,whileCounter=0;
//        for( int[] vector : trainingVectors)
//        {
//            System.out.println(multiply+" vector "+vectorCounter);
//            double s;
//            while (true)
//            {
//                System.out.println(multiply+" while "+whileCounter);
//                s=0;
//                for (int i=0; i<vector.length; i++)
//                    s+=weights[i]*vector[i];
//
//                if((this.getValue(l)==1&&s<threshold)||(this.getValue(l)==0&&s>=threshold))
//                {
//                    System.out.println("value=1 "+this.getName()+" "+l.getName()+" t="+threshold+" s="+s);
//                    stop=true;
//                    threshold = threshold+alpha*(this.getValue(l)-(s>=threshold?1:0));
//                    for (int i=0; i<weights.length; i++)
//                        weights[i]=weights[i] + alpha*(this.getValue(l)-(s>=threshold?1:0))*(vector[i]);
//                }
//                else
//                    {
//                        System.out.println("value=0 "+this.getName()+" "+l.getName()+" t="+threshold+" s="+s);
//                        break;
//                    }
//
//            }
//        }
//        multiply++;
//        return stop;
//    }

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

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
