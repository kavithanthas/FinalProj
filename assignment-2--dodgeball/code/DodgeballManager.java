/*Kavithan Ranjagunathas
  Assignment 2: Dodgeball
  Written: 11-05-2021
  Input: Thrower Name, Dodger Name, hit or dodge
  Output: Updated Scores and final winning statement
*/
import java.io.PrintStream;
import java.util.List;

public class DodgeballManager {
  DodgeballNode throwerFirstNode = null;
  DodgeballNode dodgerFirstNode = null;

  public DodgeballManager(List < String > initialThrowers, List < String > initialDodgers) {
    //throw exception if size is 0 
    if (initialThrowers.size() == 0 || initialDodgers.size() == 0) {
      throw new IllegalArgumentException();
    }
    //throw exception if element is null in either list
    if (initialThrowers == null || initialDodgers == null) {
      throw new IllegalArgumentException();
    }

    this.throwerFirstNode = new DodgeballNode(initialThrowers.get(0));
    DodgeballNode presentThrow = throwerFirstNode;
    //iterate through for loop to construct and initialize throwers
    for (int i = 1; i < initialThrowers.size(); i++) {
      presentThrow.next = new DodgeballNode(initialThrowers.get(i));
      //iterate
      presentThrow = presentThrow.next;
    }

    this.dodgerFirstNode = new DodgeballNode(initialDodgers.get(0));
    DodgeballNode presentDodge = dodgerFirstNode;
    //iterate through for loop to construct and initialize dodgers
    for (int i = 1; i < initialDodgers.size(); i++) {
      presentDodge.next = new DodgeballNode(initialDodgers.get(i));
      //iterate
      presentDodge = presentDodge.next;
    }

  }

  public void printThrowers(PrintStream stream) {
    //prints names and scores of throwers respectively
    DodgeballNode present = throwerFirstNode;
    while (present != null) {
      stream.print(present.name + " " + present.score);
      if (present.next != null) {
        //continues with a comma if next element is not empty
        stream.print(", ");
      }
      //iterate
      present = present.next;
    }

  }

  public void printDodgers(PrintStream stream) {
    //prints names and scores of dodgers respectively
    DodgeballNode present = dodgerFirstNode;
    while (present != null) {
      stream.print(present.name + " " + present.score);
      if (present.next != null) {
        //continues with a comma if next element is not empty
        stream.print(", ");
      }
      //iterate
      present = present.next;
    }
  }

  public void printWinner(PrintStream stream) {
    //introduce maxScore from other method
    int maxScore = this.getMaximumScore();

    DodgeballNode presentDodge = dodgerFirstNode;
    DodgeballNode presentThrow = throwerFirstNode;

    //begin with empty string output
    String output = "";
    //begin with # of winners as 0
    int winnerCount = 0;

    while (presentDodge != null) {
      //if dodger score in list is maxScore
      if (presentDodge.score == maxScore) {
        //1 winner
        winnerCount++;
        if (winnerCount > 1) {
          //cannot have more than 1 winner
          throw new IllegalArgumentException();
        } else {
          //new winning statement
          output = ("The winner is " + presentDodge.name + " with " + maxScore + " points");

        }
      }
      //iterate
      presentDodge = presentDodge.next;

    }
    while (presentThrow != null) {
      //if thrower score in list is maxScore
      if (presentThrow.score == maxScore) {
        //1 winner
        winnerCount++;
        if (winnerCount > 1) {
          //cannot have more than 1 winner
          throw new IllegalArgumentException();
        } else {
          //new winning statement
          output = ("The winner is " + presentThrow.name + " with " + maxScore + " points");
        }
      }
      //iterate
      presentThrow = presentThrow.next;

    }
    //print either blank output or winning statement
    stream.print(output);

  }

  public boolean throwersContains(String name) {
    DodgeballNode present = throwerFirstNode;
    while (present != null) {
      //if entered name equals node name, true
      if (name.equalsIgnoreCase(present.name)) {
        return true;
      }
      //iterate
      present = present.next;
    }
    return false;
  }

  public boolean dodgersContains(String name) {
    DodgeballNode present = dodgerFirstNode;
    while (present != null) {
      //if entered name equals list name, true
      if (name.equalsIgnoreCase(present.name)) {
        return true;
      }
      //iterate
      present = present.next;
    }
    return false;
  }

  public void hit(String throwerName, String dodgerName) {
    DodgeballNode presentThrow = throwerFirstNode;
    DodgeballNode presentDodge = dodgerFirstNode;

    if (dodgerName == null || throwerName == null || throwersContains(throwerName) == false || dodgersContains(dodgerName) == false) {
      //throws exception if names are null or thrower/dodgerName does not exist
      throw new IllegalArgumentException();
    }

    while (presentDodge != null) {
      //if we have the correct dodger node
      if (presentDodge.name.equalsIgnoreCase(dodgerName)) {
        break; //end the loop
      }
      else {
        //If this is not the correct dodger, iterate and check next
        presentDodge = presentDodge.next;
      }
    }

    while (presentThrow != null) {
      //if throwerName exist in thrower list , point for thrower
      if (presentThrow.name.equalsIgnoreCase(throwerName)) {
        presentThrow.score++;

        //introduce new update variables
        String newDodgeName = presentThrow.name;
        String newThrowName = presentDodge.name;
        int newDodgeScore = presentThrow.score;
        int newThrowScore = presentDodge.score;
        //swap throw names/score with dodge
        presentThrow.name = newThrowName;
        presentThrow.score = newThrowScore;
        presentDodge.score = newDodgeScore;
        presentDodge.name = newDodgeName;

      }
      //iterate
      presentThrow = presentThrow.next;
    }

  }

  public void dodge(String throwerName, String dodgerName) {
    DodgeballNode present = dodgerFirstNode;
    boolean exist = false;
    if (dodgerName == null || throwerName == null || throwersContains(throwerName) == false) {
      //throws exception if names are null or throwerName does not exist
      throw new IllegalArgumentException();
    }
    while (present != null) {
      //if dodgerName exist in dodger list , point for dodger
      if (present.name.equalsIgnoreCase(dodgerName)) {
        present.score++;
        //make boolean value true since dodgerName exists
        exist = true;
      }
      //iterate
      present = present.next;
    }
    if (exist == false) {
      //throws exception if dodger name doesn't exist
      throw new IllegalArgumentException();
    }
  }

  public int getMaximumScore() {
    DodgeballNode presentDodge = dodgerFirstNode;
    DodgeballNode presentThrow = throwerFirstNode;
    //set maxscore at 0
    int maxScore = 0;
    while (presentDodge != null) {
      //iterate through dodgers to find maxscore
      if (presentDodge.score > maxScore) {
        maxScore = presentDodge.score;
      }
      presentDodge = presentDodge.next;
    }
    while (presentThrow != null) {
      if (presentThrow.score > maxScore) {
        //iterate through throwers for a higher value
        maxScore = presentThrow.score;
      }
      //iterate
      presentThrow = presentThrow.next;
    }

    return maxScore;
  }

  public void printSortedScores(PrintStream stream) {
    //did not attempt bonus   
  }

}