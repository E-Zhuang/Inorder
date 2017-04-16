import java.util.*;
import apcslib.*;

/**
 *  Implementation of lists, using singly linked elements.
 *
 * @author     G. Peck
 * @created    April 27, 2002
 */
public class BSTree
{
  private TreeNode root;  // root element
  private int count;

  /**
   *  Constructor for the BSTree object
   *  Generates an empty list.
   */
  public BSTree()
  {
    root = null;
    count = 0;
  }

  /**
   *  Returns true if this list contains no elements.
   *
   * @return    true iff the list is empty
   */
  public boolean isEmpty()
  {
    return root == null;
  }

  /**
   *  Returns the root's element in this list.
   *
   * @return  the root's element in the linked list.
   */
  public Object getRoot()
  {
    if (root == null)
    {
      throw new NoSuchElementException();
    }
    else
      return root.getValue();
  }

  /**
   *  Inserts the specified element at the position in this list
   *  according to the natural ordering of its elements. All elements
   *  in the list must implement the Comparable interface. Shifts
   *  the element currently at that position (if any) and any
   *  subsequent elements to the right.
   *
   * @param  element  element to be inserted
   */
  public void insert(Comparable element)
  {
      insertHelper(root, element);
  }
  
  private TreeNode insertHelper(TreeNode node, Comparable element)
  {
      //creates the root of the binary tree
      if (isEmpty())
      {
          root = new TreeNode(element, null, null);
          count++;
          return root;
      }
      else
      {
          //adds the leaf to the end of the tree
          if(node == null)
          {
              node = new TreeNode(element, null, null);
              count++;
              return node;
          }
          else
          {
              //recursivly travels down the left subtree
              if(element.compareTo(node.getValue()) < 0)
              {
                  node.setLeft(insertHelper(node.getLeft(), element));
                  return node;
              }
              //recursivly travels down the right subtree
              else
              {
                  node.setRight(insertHelper(node.getRight(), element));
                  return node;
              }
          }
      }
  }

  /**
   *  Returns the first occurrence of the specified element, or null
   *  if the List does not contain this element.
   *
   * @param  element  element to search for.
   * @return        first occurrence of the specified element, or null
   *                if the list doesn not contain the element.
   */
  public TreeNode find(Comparable valueToFind)
  {
      return findHelper(root, valueToFind);
      
      //nonrecursive method
      /*TreeNode node = root;
      
      while (node != null)
      {
          int result = valueToFind.compareTo(node.getValue());
          if (result == 0)
            return node;
          else if (result < 0)
            node = node.getLeft();
          else  //if (result > 0)
            node = node.getRight();
      }
      
      return null;*/
  }
  
  private TreeNode findHelper (TreeNode myNode, Comparable valueToFind)
  {
      if(myNode != null)
      {
          if (((Item)(myNode.getValue())).equals(valueToFind))
          {
              return myNode;
          }
          else
          {
              if (((Item)(myNode.getValue())).compareTo(valueToFind) < 0)
              {
                  myNode = findHelper(myNode.getRight(), valueToFind);
                  return myNode;
              }
              else
              {
                  myNode = findHelper(myNode.getLeft(), valueToFind);
                  return myNode;
              }
          }
      }
      
      return null;
  }

  /**
   *  Removes the first occurrence of the specified element in
   *  this list. If the list does not contain the element, it
   *  is unchanged.
   *
   * @param  element  element to be removed from this list, if present.
   * @return          removes first element with matching element, if any.
   */
    public TreeNode delete(Comparable target)
    // post: deletes a node with data equal to target, if present, 
    // preserving binary search tree property
    {
      if(target.compareTo(root.getValue()) == 0)
      {
          root = deleteHelper(root, target);
          return root;
      }
      
      return deleteHelper(root, target);
    }
    
    private TreeNode deleteHelper(TreeNode node, Comparable target) 
    // pre : node points to a non-empty binary search tree
    // post: deletes a node with data equal to target, if present,
    // preserving binary search tree property 
    {
        if (node == null)
            throw new NoSuchElementException();
        else if (target.compareTo(node.getValue()) == 0)
        {
            return deleteTargetNode(node);
        }
        else if (target.compareTo(node.getValue()) < 0)
        {
          node.setLeft(deleteHelper(node.getLeft(), target));
          return node; 
        }
        else //target.compareTo(root.getValue()) > 0
        {
          node.setRight(deleteHelper(node.getRight(), target));
          return node; 
        }
    }
    
    private TreeNode deleteTargetNode(TreeNode target)
    // pre : target points to node to be deleted
    // post: target node is deleted preserving binary search tree property 
    {
        //There is no right node but may be a left node
        if (target.getRight() == null)
        {
            count--;
            return target.getLeft();
        }
        //no left node but there is a right node
        else if (target.getLeft() == null)
        {
            count--;
            return target.getRight();
        }
        //the right child does not have a left child
        else if (target.getRight().getLeft() == null)
        {
            count--;
            target.setValue(target.getLeft().getValue()); 
            target.setLeft(target.getLeft().getLeft()); 
            return target;
        }
        else // right child has left child
        {
            count--;
            TreeNode marker = target.getRight();
            while (marker.getLeft().getLeft() != null)
                   marker = marker.getLeft();
            target.setValue(marker.getLeft().getValue());
            marker.setLeft(marker.getLeft().getRight());
            return target; 
        }
    }

  /**
   *  Returns the number of elements in this list.
   *
   * @return    number of elements in this list.
   */
  public int size()
  {
    return count;
  }

  /**
   *  Prints all the elements of the list
   */
  public void inOrder()
  {
      //inOrderHelper(root);
      
      ListStack stack = new ListStack();
      TreeNode temp = root;
      
      do
      {
          //while moving temp as far left as possible,
          //   push tree references onto the stack
          while(temp != null)
          {
              stack.push(temp);
              temp = temp.getLeft();
          }
          //if the stack is not empty
          //   reposition temp by popping the stack
          if(!stack.isEmpty())
          {
              temp = (TreeNode)stack.pop();
          }
          //print the contents of tempgetValue()
          System.out.println(temp.getValue());
          //move temp one node to the right
          temp = temp.getRight();
      }
      while (!stack.isEmpty() || temp != null);
  }
  
  /*private void inOrderHelper(TreeNode temp)
  {
    if (temp != null)
      {
          inOrderHelper(temp.getLeft());
          System.out.println(temp.getValue());
          inOrderHelper(temp.getRight());
      }
  }*/

  /**
   *  Returns a string representation of this list. The string
   *  representation consists of the list's elements in order,
   *  enclosed in square brackets ("[]"). Adjacent elements are
   *  separated by the characters ", " (comma and space).
   *
   * @return    Description of the Returned Value
   */
  /*
  public String toString()
  {// post: returns a string representing list

    String s = "[";

    TreeNode temp = first;  // start from the first node
    while (temp != null)
    {
      s += temp.getValue() + ", "; // append the data
      temp = temp.getNext();      // go to next node
    }
    s += "]";
    return s;
  }*/
}
