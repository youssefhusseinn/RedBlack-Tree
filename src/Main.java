import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        RBTree tree = new RBTree(null);
	    Scanner scanner = new Scanner(System.in).useDelimiter("\n");

	    int input;
	    boolean loop = true;
	    while(loop) {
            do {
                System.out.println("===========================\n  Size of dictionary: "+tree.treeSize(tree.root));
                System.out.println("  Height: "+tree.treeHeight(tree.root));
                System.out.println("===========================\n|| 1 - Load Dictionary   ||\n|| 2 - Insert Words      ||\n|| 3 - Look-up Words     ||\n|| 4 - Print Tree        ||\n|| 5 - Terminate Program ||\n===========================");
                try {
                    input = scanner.nextInt();
                    //input = 1;
                }
                catch (Exception e){
                    System.out.println("Please insert a correct number.");
                    input = -1;
                }
            } while (!(input == 1 || input == 2 || input == 3 || input == 4 || input == 5));

            switch (input){
                case 1:
                    loadDictionary(tree);
                    break;
                case 2:
                    System.out.println("Insert new word: ");
                    String insertWord = scanner.next();
                    if(!tree.search(tree.root,insertWord))
                        tree.insert(insertWord);
                    else
                        System.out.println(insertWord+" already exists!");
                    break;
                case 3:
                    System.out.println("Insert desired word: ");
                    String searchWord = "";
                    searchWord += scanner.next();
                    if(tree.search(tree.root,searchWord))
                        System.out.println(searchWord+" exists!");
                    else
                        System.out.println(searchWord+" doesn't exist!");
                    break;
                case 4:
                    tree.printTree(tree.root);
                    break;
                case 5:
                    loop = false;
                    scanner.close();
                    break;
            }
        }
        System.out.println("\nProgram terminated successfully.");
    }

    public static void loadDictionary(RBTree tree){
        try{
            //Scanner reader = new Scanner(new File("EN-US-randomized-unique updated.txt"));
            Scanner reader = new Scanner(new File("file.txt"));
            while (reader.hasNextLine())
                tree.insert(reader.nextLine());
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occured");
            e.printStackTrace();
        }
    }
}
