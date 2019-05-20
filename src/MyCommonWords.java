import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MyCommonWords implements Comparable <MyCommonWords>, Serializable
{
  int count=0;
  String word;
  public static int words_limit;
  private static Set<MyCommonWords> wordsMCW=new TreeSet<>();
  private static final long serialVersionUID = 6529685098267757690L;

  public MyCommonWords() {}

  public MyCommonWords(int words_limit) {this.words_limit=words_limit;}

  public MyCommonWords(String word, int count)
  {
    this.count=count;
    this.word=word;
  }

  public int compareTo(MyCommonWords other){
if (count <other.count) return -1; else if (count > other.count) return 1; else return 0;
  }

  public void printString()
  {
    System.out.println("The common words dictionary contains the following data:");
    for (MyCommonWords w : wordsMCW)
    {
      System.out.println(w.count + " " + w.word);
    }

  }
  public void deleteDict(){

    File file = new File("dict.txt");
    if(file.delete()){
      System.out.println("File dict.txt deleted");
    }else System.out.println("File dict.txt doesn't exist");

  }


  public int getSize(){
    return wordsMCW.size();
  }


  void put(String word, int count)
  {
    MyCommonWords wordMCW = new MyCommonWords(word, count);
    if (wordsMCW.size() == 0) wordsMCW=ReadDict();
    if (wordsMCW.size() == 0) wordsMCW=new TreeSet<>();

    if (!wordsMCW.contains(wordMCW))
    {
      wordsMCW.add(wordMCW);
      SaveDict(wordsMCW);
      words_limit++;
    } else
      System.out.println("The common words dictionary already contains the data: "+count+" "+word);
  }

  int limit()
  {
    return words_limit;
  }

  String get(int i)
  {
    String word="not found";
    if (wordsMCW.size() ==0)  wordsMCW=ReadDict();
    if (wordsMCW == null) return "0";
    //if (i > words_limit-1) return "0";
    //if (i == words_limit) return wordsMCW.get(wordsMCW.size() - 1).word;
    for (MyCommonWords w : wordsMCW){
   //   System.out.println(w.word+" "+w.count);
      if (w.count == i) word= w.word;}
    return word;
  }

  Set<String> getAll(){

    if (wordsMCW == null) wordsMCW=ReadDict();
    return wordsMCW.stream().map(w->(w.word)).collect(Collectors.toSet());
  }



  //serialize!!!! = write to file!!

  private static void SaveDict(Set<MyCommonWords> words)
  {
    if (words.isEmpty())
    {
      return;
    }

    try (ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("dict.txt")))
    {
      oos.writeInt(words.size());

      words.stream().distinct().sorted().forEach(w-> {
        try
        {
          oos.writeObject(w);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      });

      System.out.println("Writing Done!");

    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

  }

  //deserialize!!!! = read from file!!

  protected static Set<MyCommonWords> ReadDict()
  {
    File f=new File("dict.txt");
    if (!f.exists())
    {
      return Collections.emptySet();
    }

    Set<MyCommonWords> words=null;
    try (ObjectInputStream ois=
        new ObjectInputStream(new FileInputStream("dict.txt")))
    {
      int size=ois.readInt();

      words=new TreeSet<MyCommonWords>();
      for (int i=0; i < size; i++)
        words.add((MyCommonWords) ois.readObject());

    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }

    return words;
  }

}
