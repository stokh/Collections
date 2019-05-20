public class Main
{
  public static void main(String[] args)
  {
    MyCommonWords mcw = new MyCommonWords (5);
    mcw.put("сказал", 2225);
    mcw.put("больше",368);
    mcw.put("соня",300);
//    System.out.println("words_list="+MyСommonWords.words_limit);
//    System.out.println("size="+mcw.getSize());
//    //System.out.println(mcw.limit());
    System.out.println("freq 300 is the word: "+mcw.get(300));


    mcw.printString();
    //mcw.deleteDict();


  }
}
