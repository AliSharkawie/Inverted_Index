import java.io.*;
import java.util.*;

public class Inverted_Index {

    public static class Dictenter {
        int doc_freq = 0;
        int term_freq = 0;
        Posting pList = null;
    }

    public static class Posting {
        Posting next = null;
        int docId;
        int dtf = 1;
    }

    public static boolean stop_words(String s) {
        String[] commonWords = { "i", "we", "our", "you", "your", "it", "its", "itself", "they", "them", "their",
                "theirs", "themselves", "what", "which", "this", "that", "these", "those", "am", "is", "are", "was",
                "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an",
                "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with",
                "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to",
                "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once",
                "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most",
                "other", "some", "such", "no", "not", "only", "own", "same", "so", "than", "too", "very", "can", "will",
                "should", "now" };

        for (String word : commonWords) {
            if (s.equals(word)) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        HashMap<String, Dictenter> index = new HashMap<String, Dictenter>();

        for (int i = 1; i <= 10; i++) {
            String fileName = "file" + i + ".txt";
            int docId = i;
            try {
                File file = new File(fileName);
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String term = scanner.next().toLowerCase();
                    if (!index.containsKey(term)) {
                        index.put(term, new Dictenter());
                    }
                    Dictenter obj = index.get(term);
                    obj.term_freq++;
                    if (obj.pList == null) {
                        obj.pList = new Posting();
                        obj.pList.docId = docId;
                    } else {
                        Posting p = obj.pList;
                        while (p.next != null && p.docId != docId) {
                            p = p.next;
                        }
                        if (p.docId == docId) {
                            p.dtf++;
                        } else {
                            p.next = new Posting();
                            p.next.docId = docId;
                        }
                    }

                }

                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + fileName);
            }
        }
        Scanner scanner = new Scanner(System.in);
        boolean x = true;
        while (x) {
            System.out.println("-----------------------------------------");
            System.out.println("1-To Search for a word");
            System.out.println("2-Exit The programm");
            System.out.print("Enter Your option:");
            int v = scanner.nextInt();
            System.out.println("-----------------------------------------");
            if (v != 2) {
                System.out.println(" ");
                System.out.print("Enter a word: ");
                String word = scanner.next().toLowerCase();
                System.out.println("-----------------------------------------");
                if (stop_words(word) != true) {
                    if (index.containsKey(word)) {
                        Dictenter obj = index.get(word);
                        Posting p = obj.pList;
                        System.out.println("Files containing the word '" + word + "': ");
                        System.out.println(" ");
                        while (p != null) {
                            System.out.println("File "+p.docId + " ");
                            System.out.println("Term frequency in file " + p.docId + ": " + p.dtf);
                            System.out.println("-----------------------------------------");

                            obj.doc_freq++;
                            p = p.next;
                        }
                        System.out.println(" ");
                        System.out.println("Document frequency of the word '" + word + "': " + obj.doc_freq);
                        System.out.println("Term frequency of the word '" + word + "': " + obj.term_freq);
                        System.out.println("-----------------------------------------");
                        System.out.println(" ");
                        obj.doc_freq = 0;
                    } 
                     else {
                        System.out.println("The word '" + word + "' does not exist in any file.");
                    }
                } else {
                    System.out.println("This word is from very common words , please search for other word or Exit.");
                }

                x = true;
            }

            else {
                x = false;
                scanner.close();
            }
        }
    }
}
