package JSHOP2;

import JSHOP2.*;
import java.util.*;
import hr.org.foi.generated.aifiles.problem;

public class gui{
	public static void main(String[] args) {
                System.out.println("DEBUG Before getting plans!");
		problem.getPlans();
                System.out.println("DEBUG After getting plans!");
		new JSHOP2GUI();
	} 
}
