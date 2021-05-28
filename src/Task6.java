import java.text.ParseException;

import java.util.Arrays;
import java.util.*;


public class Task6 {

    public static void main(String[] args) {
        System.out.println("Первый номер   : "+ hiddenAnagram("My world evolves in a beautiful space called Tesh.", "sworn love lived")); //+
        System.out.println("Второй номер   : "+ Arrays.deepToString(chops("intercontinentalisationalism", 6)));                        //+
        System.out.println("Третий номер   : "+ nicoCipher("mubashirhassan", "crazy"));                                            //+
        System.out.println("Четвертый номер: "+ Arrays.toString(twoProduct(new int[]{1, 2, 3, 9, 4, 5, 15, 3}, 45)));                        //+
        System.out.println("Пятый номер    : "+ Arrays.toString(isExact(720)));                                                              //+
        System.out.println("Шестой номер   : "+ fractions("0.19(2367)"));                                                                       //+
        System.out.println("Седьмой номер  : "+ pilishString("HOWINEEDADRINKALCOHOLICINNATUREAFTERTHEHEAVYLECTURESINVOLVINGQUANTUMMECHANI"));         //+                                                   //+
        System.out.println("Восьмой номер  : "+ generateNonconsecutive(5));                                                                     //+
        System.out.println("Девятый номер  : "+ isValid("abccd"));                                                                      //+
        System.out.println("Десятый номер  : "+ Arrays.deepToString(sumsUp(new int[]{7,6,5,4,2,5,3,7,26,6,8,3})));                                 //+
    }

    public static String hiddenAnagram(String str, String an){
    str = Polish(str); //очистка от ненужных символов
    an = Polish(an);

    boolean found = false;

    int length = an.length();                                           //длина анаграммы для выделения из строки кусков
        for (int i = 0; i <= str.length() - length; i++){
        String base = str.substring(i, i + length);                      //из исходной строки берем куски такой же длины как и анаграмма
        String alfavit = an;                                             //создание алфавита для анаграммы

        for (int j = 0; j < length - 1; j++){
            if (alfavit.contains(Character.toString(base.charAt(j)))){                                          // проверяем есть ли в куске символ их алфавита
                alfavit = alfavit.replaceFirst(Character.toString(base.charAt(j)), "");              // только 1 раз можно использовать
                found = true;
                
            }else{
                found = false;
                break;
            }
        }
        if (found){
            return base;                      // кусок строки оказался анаграммой
        }
    }
        return "notfound";
}
    
    public static String[] chops(String str, int n) {
        ArrayList<String> chop = new ArrayList<>();           // список для хранения срезов

        if (str.length() >= n) {                              // строка должна быть длиннее среза

            chop.add(str.substring(0, n));                    //добавляем в список первые n символов

            for (String elem : chops(str.substring(n), n)) {  //вызок рекурсии для исходной строки -n первых символов
                chop.add(elem);
            }
            Collections.sort(chop);                            //сортируем по алфавиту ???

            return chop.toArray(new String[0]);
        }
        else {
            return new String[0];
        }
    }

    public static String nicoCipher(String message, String key) {


        while (message.length() % key.length() != 0) {    //длина сообщения должна быть кратна ключу
            message = message + " ";
        }

        char[][] PillarsAndRows;
        PillarsAndRows = new char    [key.length()]   [message.length()/key.length()+1];        // количество строк=лина ключа-
                                                                                                // кол-во столбцов = message/key+1

        for (int i = 0; i < key.length(); i++) {
            PillarsAndRows[i][0] = key.charAt(i);                                               //заполнения 1 столбца ключом
        }
        for (int i = 0; i < message.length(); i++) {
            PillarsAndRows      [i%key.length()]      [i/key.length()+1] = message.charAt(i);   // заполнение остальных стобцов символами из строки
        }
        Arrays.sort(PillarsAndRows, Comparator.comparingInt(a -> a[0]));                        // сортируем элементы по ключу ( всю строку переставляем местами с дургой )

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            sb.append(   PillarsAndRows     [i%key.length()]     [i/key.length()+1]  );         //по каждому столбцу составляем шифр
        }

        return sb.toString();
    }


    public static int[] twoProduct(int[] arr, int prod){

        int[] res = new int[2];

        for (int i = 1; i < arr.length; i++){    //вложенный цикл для поиска множителей
            for (int j = 1; j < i; j++){

                if ((arr[i] * arr[j]) == prod){   //работает 1 раз

                    res[0] = arr[j];              // <---- j < i
                    res[1] = arr[i];


                    return res;  // полный
                }
            }
        }
        return new int[]{};      // пустой
    }

    public static int[] isExact(int num) {
        int count = isExactRec(num, 1); // метод с рекурсии для нахожд факториала

        if (count == -54) {
            return new int[0];

        }
        else{
            return new int[]{num, count};
        }

    }

    public static int isExactRec(int num, int count) {    // метод с рекурсией для проверки
        if (num <0 || num%count != 0) {                    //доожно быть -> num>count и num>0
            return -54;
        }
        if (num/count == 1) {                              //нашли факториал
            return count;
        }
        return isExactRec(num/count, count+1);   // продолжаем делить
    }

    public static String fractions(String num){

        int dotPosition = num.indexOf(".");                                     // позиция точки
        int bracketPosition = num.indexOf("(");                                 // позиция сокбки (
        int A = Integer.parseInt(num.substring(0, dotPosition));                // можно выделить целую часть

        String part1 = num.substring(dotPosition + 1, bracketPosition);         //десятичная часть без повторений (если есть )

        String part2 = num.substring(bracketPosition + 1, num.length() - 1);    //десятичная часть с повторением

        int part1Int =0;
        if (part1.length() > 0) part1Int = Integer.parseInt(part1);

        int numerator = Integer.parseInt(part1+part2) - part1Int;           // числитель который состоит из десятичной части - не повторяющаяся чать
        String denominatorStr = "";
        for (int i=0; i < part2.length(); i++){                                //таким образом заполняем знаменатель
            denominatorStr += "9";
        }
        for (int i=0; i < part1.length(); i++){
            denominatorStr += "0";
        }
        int denominator = Integer.parseInt(denominatorStr);                  // знаменатель в числ вид
        int i = 2;
        int delim = Math.max(numerator, denominator);

        while (i < delim/2) {                                                 //получили дробь-> сокращаем ее
            if (numerator % i == 0 && denominator % i == 0) {                 //до delim/2 тк максимальный делитель
                numerator /= i;                                              // числа это число деленое на 2
                denominator /= i;                                             // (((не учитывая само число )))
            }
            else{
                i++;
            }

        }
        return (A * denominator + numerator) + "/" + denominator;   //ответ состоит из A раз знаментелей+ числитель / знаменатель
    }

    public static String pilishString(String s){
        if (s==""){
            return "";
        }
        String pi = "314159265358979";
        int Len;

        String res = "";

        for(int i = 0; pi.length() != 0 && i < s.length(); i += Len){

            Len = Integer.parseInt(pi.substring(0, 1));     //получаем длину слова
                                                            //слева направо от числа pi

            pi = pi.substring(1);                            //Убираем это число из pi
            int f = s.length();

            if(i + Len >= s.length()){                      //пока сумма длин меньше длины строки

                res += s.substring(i);                     //добавляем участок


                char Last = res.charAt(res.length() - 1);      // по заданию нужно повторять последний символ

                for(int j = 0; j < i + Len - s.length(); j++){
                    res += Last;
                }

            }
            else {

                res += s.substring(i, i + Len) + " ";   // добавляем учатски из строки
            }

        }

        return res ;
    }

    public static String check(int d, int n){
        String res = "";

        for(int i = (int)Math.pow(2, n - 1); i > 0; i >>= 1){

            if((d & i) != 0){                         //  Конъюнкция
                res += "1";                           // строим двоичное число
            }
            else{
                res += "0";
            }
        }

        if(!res.contains("11")){                    //проверяем условие задания
            return res;
           }
        else{
            return "-54";
          }
    }
    public static String generateNonconsecutive(int n){
        int total = (int)Math.pow(2, n) -1;        //кол-во элементов (все)

        String res = "";
        String add = "";

        for(int i = 0; i <= total; i++){
            add = check(i, n) ;
            if(add != "-54"){                      //Если число подходит добавляем его
                res += add + " ";
            }
        }
        return res;
    }


    public static String isValid(String str){
        HashMap<Character, Integer> map = new HashMap<>();   //хэшмэп для хранения кол-ва появлений символов

        Integer k = 0;
        for(int i = 0; i < str.length(); i++){
            if((k = map.get(str.charAt(i))) != null){      //если уже есть такой символ +1
                map.put(str.charAt(i),  k+1);
            }
            else{                                          //иначе делаем заполняем ячейку 1
                map.put(str.charAt(i), 1);
            }
        }
        Integer min = str.length();                        //максимально возможное кол-во повторений
        Integer t = 0;
                int bolscheByOne = 0;
                int mnogo_bolshce = 0;
        for (HashMap.Entry e: map.entrySet()) {
            t = (Integer)(e.getValue());
            if(t < min){
                min = t;                                 //минимальное кол-во повторений
            }
        }

        for (HashMap.Entry e: map.entrySet()) {             //для каждоого элемента таблицы
            t = (Integer)(e.getValue());

            if(t > min){
                if(t - min == 1){              //если разница в 1
                    bolscheByOne++;
                }
                else if(t - min > 1){         // если разница >1
                    mnogo_bolshce++;
                }
            }
        }
        if(mnogo_bolshce > 0){           // хотя бы один 2+ символ
            return "NO";
        }
        if(bolscheByOne > 1){                  // больше одного (+1) символа
            return "NO";
        }
        if (t == min && min > 1) {      // проверка если одинаковое количество (или 1 ) символов которое больше 1
           return "NO";
        }
        return "YES";
    }

    public static int[][] sumsUp(int[] arr){
        int count = 0;
        for (int i = 1; i < arr.length; i++){    // считаем сколько всего таких пар
            for (int j = 0; j < i; j++){
                if (arr[i] + arr[j] == 8){
                    count++;
                }
            }
        }
        int[][] result = new int[count][2];
        count = 0;
        for (int i = 1; i < arr.length; i++){
            for (int j = 0; j < i; j++){
                if (arr[i] + arr[j] == 8){
                    result[count++] = new int[]{Math.min(arr[i], arr[j]), Math.max(arr[i], arr[j])};  // по заданию заполняем двумерный массив
                }
            }
        }
        return result;
    }


    public static String Polish(String str){                      //убирает все кроме букв
        str = str.toLowerCase(Locale.ROOT);
        for (int i = 0; i < str.length(); i++){
            if (!(str.charAt(i) > 97) ||(str.charAt(i) < 122)){
                char symb = str.charAt(i);
                str = str.replace(symb, ' ');
            }
        }
        str = str.replaceAll("\\s+", "");
        return str;
    }
}

