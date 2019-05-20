import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class CollectionsM
{

  public static void main(String[] args) throws IOException
  {
    String line;
    String word;

    Map<String, Integer> dict=new LinkedHashMap();
    try (BufferedReader bf=new BufferedReader(new FileReader("voyna_i_mir.txt")))
    //try (BufferedReader bf=new BufferedReader(new FileReader("test.txt")))

    {
      while ((line=bf.readLine()) != null)
      {

        String[] strarr=line.replaceAll("(?U)[\\W\\d_]", " ").trim().split("\\s+");

        for (int i=0; i < strarr.length; i++)
        {
          word=strarr[i].toLowerCase().trim();
          //if (word.matches("\\D+") && !word.matches("_+")) // && word == null || word.equals("") ));
           //if (word.matches("[^\\d_].{2,}")) // && word == null || word.equals("") ));
             if (word.matches(".{2,}")) // && word == null || word.equals("") ));
          //if (word.matches("[A-Za-z\\u0410-\\u044F]+"))
          //if (word.matches("[A-Za-z\\u0410-\\u044F]+"))

          {
            if (!dict.containsKey(word))
              dict.put(word, 1);
            else
              dict.replace(word, dict.get(word) + 1);
          }
        }
      }
    }

    long m = System.currentTimeMillis();

    try (BufferedWriter br1=new BufferedWriter(new FileWriter("enter_order_cnt.res")))
    {
      dict.forEach((k, v) -> {
        try
        {
          br1.write(k + " " + v + "\n");
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      });

    }

    System.out.println("1. Writing to file: enter_order_cnt.res: "+(double) (System.currentTimeMillis() - m)+" ms");


    m = System.currentTimeMillis();

    try (BufferedWriter br=new BufferedWriter(new FileWriter("alph_order_cnt.res")))
    {
      dict.entrySet()
          .stream()
          .sorted(Map.Entry.comparingByKey())
          .forEachOrdered(e ->
              {
                try
                {
                  br.write(e.getKey() + " " + e.getValue() + "\n");
                }
                catch (IOException ee)
                {
                  ee.printStackTrace();
                }
              }
          );

    }

    System.out.println("2. Writing to file: alph_order_cnt.res: "+(double) (System.currentTimeMillis() - m)+" ms");


    // Count letters!!!

    m = System.currentTimeMillis();

    Map<String, Integer> dict_es=new HashMap();
    //Pattern pattern=Pattern.compile("[a-zA-Z]+");
    Pattern pattern=Pattern.compile("[^\\u0410-\\u044F]+");
    //U+20D0?U+20FF
    //("[A-Za-z\\u0410-\\u044F]+"))
    //^([ \u00c0-\u01ffa-zA-Z'])+$

    dict.keySet()
        .stream()
        .filter(string -> pattern.matcher(string).matches())
        .forEach(e ->
            dict_es.put(e, e.length())
        );

    try (BufferedWriter br=new BufferedWriter(new FileWriter("eng_symb_rev_order.res")))
    {
      br.write("Length order\n");
      dict_es.entrySet()
          .stream()
          .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
          .forEach(e ->
              {
                try
                {
                  br.write(e.getKey() + " " + e.getValue() + "\n");
                }
                catch (IOException ee)
                {
                  ee.printStackTrace();
                }
              }
          );
    }

    try (BufferedWriter br=new BufferedWriter(new FileWriter("eng_symb_rev_order.res", true)))
    {
      br.write("\nReversed alphabetic order\n");
      dict_es.entrySet()
          .stream()
          .sorted(Map.Entry.<String, Integer>comparingByKey().reversed())
          .forEach(e ->
              {
                try
                {
                  br.write(e.getKey() + " " + e.getValue() + "\n");
                }
                catch (IOException ee)
                {
                  ee.printStackTrace();
                }
              }
          );
    }

    System.out.println("3. Writing to file: eng_symb_rev_order.res: "+(double) (System.currentTimeMillis() - m)+" ms");



    //Reverse count->word dictionary!!!!!!!

    m = System.currentTimeMillis();

    Multimap<Integer, String> mmap=HashMultimap.create();

    for (Map.Entry<String, Integer> entry : dict.entrySet())
    {
      mmap.put(entry.getValue(), entry.getKey());
    }

    System.out.println("4. Guava Multimap created as reversed map: "+(double) (System.currentTimeMillis() - m)+" ms");


    m = System.currentTimeMillis();

    Map<Integer, String> dict_rev=new HashMap<>();

    int temp=0;
    for (Integer count : mmap.keySet())
    {
      if (count.equals(temp))
        continue;
      Collection words=mmap.get(count);
      dict_rev.put(count, String.join(" ", words));
    }

    System.out.println("4. New map with joined words created from Multimap: "+(double) (System.currentTimeMillis() - m)+" ms");

    m = System.currentTimeMillis();

    try (BufferedWriter br=new BufferedWriter(new FileWriter("count_rev_order.res")))
    {
      dict_rev.entrySet()
          .stream()
          .sorted(Map.Entry.<Integer, String>comparingByKey().reversed())
          .forEachOrdered(e ->
              {
                try
                {
                  br.write(e.getKey() + " " + e.getValue() + "\n");
                }
                catch (IOException ee)
                {
                  ee.printStackTrace();
                }
              }
          );

    }
    System.out.println("4. Writing to file: count_rev_order.res: "+(double) (System.currentTimeMillis() - m)+" ms");



  }

}


