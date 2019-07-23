package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		
		Node fullAns = null;
		
		if (poly1 == null && poly2 == null){
			
			return fullAns;
		}
		
		Node dum1 = poly1;
		Node dum2 = poly2;
		
		if (poly1 == null && dum2 != null){
			
			while (dum2 != null){
				
				if (dum2.term.coeff != 0){
				
					Node partAns = new Node (dum2.term.coeff, dum2.term.degree, fullAns);
					fullAns = partAns;
				}
				
				dum2 = dum2.next;
			}
			
			return reverseOrder(fullAns);
		}
		
		if (dum1 != null && poly2 == null){
			
			while (dum1 != null){
				
				if (dum1.term.coeff != 0){
				
					Node partAns = new Node (dum1.term.coeff, dum1.term.degree, fullAns);
					fullAns = partAns;
				}
				
				dum1 = dum1.next;
			}
			
			return reverseOrder(fullAns);
		}
		
		while (dum1 != null){
			
			if (dum2 == null || (dum1.term.degree < dum2.term.degree)){
				
				if (dum1.term.coeff != 0){
					
					Node partAns = new Node (dum1.term.coeff, dum1.term.degree, fullAns);
					fullAns = partAns;
				}
				
				dum1 = dum1.next;
				continue;
			}
			
			while (dum2 != null){
				
				if (dum2.term.degree < dum1.term.degree){
					
					if (dum2.term.coeff != 0){
					
						Node partAns = new Node (dum2.term.coeff, dum2.term.degree, fullAns);
						fullAns = partAns;
					}
						
					dum2 = dum2.next;
					
					if (dum2 == null || dum1.term.degree < dum2.term.degree){
						
						break;
					}
					
					continue;
				}
				
				if (dum1.term.degree == dum2.term.degree){
					
					float coeff = dum1.term.coeff + dum2.term.coeff;
					
					if (coeff != 0){
						
						Node partAns = new Node (coeff, dum1.term.degree, fullAns);
						fullAns = partAns;
					}
					
					dum1 = dum1.next;
					dum2 = dum2.next;
					break;
				}
			}
		}
		
		while (dum2 != null){
			
			if (dum2.term.coeff != 0){
			
				Node partAns = new Node (dum2.term.coeff, dum2.term.degree, fullAns);
				fullAns = partAns;
			}
			
			dum2 = dum2.next;
		}
		
		return reverseOrder(fullAns);
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		
		Node fullAns = null;
		
		Node dum1 = poly1;
		
		while (dum1 != null){
			
			Node partAns = null;
			Node dum2 = poly2;
			
			while (dum2 != null){
				
				float coeff = dum2.term.coeff * dum1.term.coeff;
				int degree = dum2.term.degree + dum1.term.degree;
				partAns = new Node (coeff, degree, partAns);
				
				dum2 = dum2.next;
			}
			
			partAns = reverseOrder(partAns);
			fullAns = add(fullAns, partAns);
			dum1 = dum1.next;
		}
		
		return fullAns;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		
		if (poly == null){
			return 0;
		}
		
		float partAns = 0;
		float fullAns = 0;
		
		Node dummy = poly;
		
		while (dummy != null){
			
			float coeff = (float)(dummy.term.coeff);
			float xRaised = (float)(Math.pow(x, dummy.term.degree));
			partAns = (float)((coeff)*(xRaised));
			
			fullAns += partAns;
			
			dummy = dummy.next;
		}
		
		return fullAns;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
	
	private static Node reverseOrder(Node poly){
		
		Node reverseLL = null;
		Node dummy = poly;
		
		for (Node ptr = dummy; ptr != null; ptr = ptr.next){
			
			reverseLL = new Node(ptr.term.coeff, ptr.term.degree, reverseLL);
		}
		
		return reverseLL;
	}
}
