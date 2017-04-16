/**
 *  Lesson 31 Ordered List
 *
 * @author     G. Peck
 * @created    July 18, 2002
 */

//import apcslib.*;
import chn.util.*;
import java.util.*;

public class OrderedList
{
  void testFind(BSTree list)
  {
    int idToFind;
    TreeNode location;

    ConsoleIO console = new ConsoleIO();

    System.out.println("Testing search algorithm\n");
    System.out.print("Enter Id value to search for (-1 to quit) --> ");
    idToFind = console.readInt();

    while (idToFind >= 0)
    {
      location = (TreeNode)list.find(new Item(idToFind, 0));
      if (location == null)
        System.out.println("Id = " + idToFind + "  No such part in stock");
      else
        System.out.println(location.getValue());
        
      System.out.println();
      System.out.print("Enter Id value to search for (-1 to quit) --> ");
      idToFind = console.readInt();
    }
  }

  public void testDelete(BSTree list)
  {
    int idToDelete;
    boolean success;

    ConsoleIO console = new ConsoleIO();

    System.out.println("Testing delete algorithm\n");
    System.out.print("Enter Id value to delete (-1 to quit) --> ");
    idToDelete = console.readInt();

    while (idToDelete >= 0)
    {
      Item idInvItem = new Item(idToDelete, 0);
      
      try
      {
          list.delete(idInvItem);
          System.out.println("     Id #" + idToDelete + " was deleted");
      }
      catch (NoSuchElementException e)
      {
          System.out.println("Id# " + idToDelete + "  No such part in stock");
      }
      System.out.println();
      System.out.print("Enter Id value to delete (-1 to quit) --> ");

      idToDelete = console.readInt();
    }
  }

  public void readData(BSTree list)
  {
    FileInput inFile;

    String fileName = "file20.txt";
    int id, inv;

    inFile = new FileInput(fileName);

    int howMany = inFile.readInt();
    for (int k = 1; k <= howMany; k++)
    {
      id = inFile.readInt();
      inv = inFile.readInt();
      list.insert(new Item(id, inv));
    }
  }

  public void mainMenu (BSTree head)
  {
    String choice;
    ConsoleIO console = new ConsoleIO();

    do
    {
      System.out.println("Linked List algorithm menu\n");
      System.out.println("(1) Read data from disk");
      System.out.println("(2) Print the tree in order");
      System.out.println("(3) Search the tree");
      System.out.println("(4) Delete from the tree");
      System.out.println("(5) Count the nodes in the tree");
      System.out.println("(Q) Quit\n");
      System.out.print("Choice ---> ");
      choice = console.readLine() + " ";  // kludge to ensure choice.charAt(0) > 0

      System.out.println();

      if ('1' <= choice.charAt(0) && choice.charAt(0) <= '7')
      {
        switch (choice.charAt(0))
        {
          case '1' :
            readData(head);
            break;
          case '2' :
            System.out.println();
            System.out.println("The tree printed inorder\n");
            head.inOrder();
            System.out.println();
            break;
          case '3' :
            testFind(head);
            break;
          case '4' :
            testDelete(head);
            break;
          case '5' :
            System.out.println("Number of nodes = " + head.size ());
            System.out.println();
            break;
        }
      }
    }
    while (choice.charAt(0) != 'Q' && choice.charAt(0) != 'q');
  }

  public static void main(String[] args)
  {
    OrderedList test = new OrderedList();
    BSTree list = new BSTree();

    test.mainMenu (list);
  }
}