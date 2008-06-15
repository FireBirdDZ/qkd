package qkd;
// (C) Ronald I. Frank 2004
// Quantum Key Distribution Algoithm Simulation As Java Code
// Compiled in SDK 1.4.2 08/14/04
// V9. Not refactored or fully "objectified"
//
// ********** Quantum Key Distribution Simulation **********
// *********************** main() **************************

// Version 8 (With an Eve & Using Detailed Measurement info)
//
// ********************************
/*
 * Futures:
 * 1) GUI contolled input
 *    A) Option for auto random generate Alice's filters (bits)
 *	 B) Option for supressing intermediate output (see 2-A)
 * 2) GUI controlled output
 *	 A) Consolidated output - all vectors together with only headers
 * 3) Separate functions for Bob ans Eve and better headings for results
 * 4) Meta routine that can run many runs and count number of equal bits
 */
// ********************************
// Input is a number (of filters) and a string of filter choices
// 		therefore bit choices represented as:
// 		| for 1, - for 0
// 		\ for 1, \ for 0
//
// Example: -\//|\|--/|\ has length 12
// 		standing for x8E3 == 010011100011
//
// Before  Alice's call to Bob
// String [] arg[0]			Input FILTER (PHOTON) designations
// 			 arg[1]			If non zero length - there is an Eve.
// int  N  					the number of input filters,
//							and therefore bits
//							and photons too.
// char[N] alicesFilters	same vector of N filters input
// char[N] alicesFilterSets vector of N corresponding filter SET disigantions
// int [N] alicesBits		Vector of N bits Alice intends (sends)
// char[N] bobsFilters		vector of N filters chosen by Bob
// char[N] bobsFilterSets	vector of N corresponding filter SET disigantions
// char[N] evesFilters		vector of N filters chosen by optional Eve
// char[N] evesFilterSets	vector of N corresponding filter SET disigantions
// char[N] alicesFilters	Again! This time reset by Eve
//
// After Alice's call to Bob about filters
// char[N] setMatches		Which K of N filters match? Y, N, ~
// int [K] finalBits		What are the K of N bits for the matches?
//							Vector is length K in main().
// int [N] finalBitsSlots	What are the K=J of N indexes for the matches?
//							Vector is length N.

// After Alice's call to Bob about a subset of actual bits sent
// char[N] finalBitsEqual 	What are the M equal subset bits ? Filled with -3.
//
// ********** Simulation  Driver **********

/////////////////////////////////// Class //////////////////////////////////
///////////////////////////////// & main() /////////////////////////////////

import java.util.*; // For Arrays.asList(args).size();
import javax.swing.JFrame;

////////////////////////////////// class ///////////////////////////////////


public class Simulation {
    
   /* args[0]	= 	The N filter choices;
                                a filter choice is one of '|', '-', '\', '/' ;
                                the bit interpretation  of the filter choices is:
                                '|' = 1, '-' = 0, '\' = 1, '/' = 0 ;
    args[0]  =   If there is a second argument in the command line,
                                then there is an Eve.  The argument is Eve.s name.
    
    N 		= 	args[0].length(); The number of filters being sent in.
    */
    static DoTransmission doTransmission = null;
    static VisualDisplayTableModel visualDisplayTableModel = new VisualDisplayTableModel();
    public static void main(String[] args) {
/////////////////////////////////////////////////////////////////////////
        final int Eve = Arrays.asList(args).size();	// 1 = filters only
        // 2 = filters + Eve's name
        
        // Check that the input array of strings has only 1 or 2 input Strings
        switch(Eve) {
            case 0 : // will never happen, Java catches no args case
                System.out.println("\nError.  No input arguments");
                System.out.println(" TERMINATING RUN");
                System.out.println(" in main()");
                System.out.println("===================================");
                break;
                
            case 1 :
                System.out.println("\nThere is NO EVE in this run.");
                System.out.println("==== filters only =====");
                break;
                
            case 2 :
                System.out.print  ("\nThere is EVE in this run named ");
                System.out.println("( " + args[1] + " ).");
                System.out.println("==== filters & Eves Name ====");
                break;
            default:
                System.out.print  (" INPUT WRONG");
                System.out.print  (" There are more than 2 inputs");
                System.out.println(" Input parms are a String of filters");
                System.out.print  (" '|', '-', '\', '/'.");
                System.out.println(" ,and optionally a String name of Eve");
                System.out.print  (" TERMINATING RUN in main.");
                System.out.println("===================================");
                System.exit(0);
        } // end check input array of strings has only 1 or 2 input Strings
        
/////////////////////////////////////////////////////////////////////////
        // 	Find N & print it out.  It is the length of the first input arg.
        // 	Set up Eve's arrays (Bob's & Alice's get set by methods that get
        // 	N passed implicitly to them as the length of the filter String).
        
        if ( args.length < 1 ) {
            usage();
        }
        int N = args[0].length();
        final String input = args[0];
        
        process(input, Eve);
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                createAndShowGUI(visualDisplayTableModel);
            }
        });
        
        
    } // end main
    public static void process(String input, int Eve ) {
        visualDisplayTableModel.setNumberFilters(input.length() );
        doTransmission = new DoTransmission(input,Eve,visualDisplayTableModel);
        doTransmission.process();
    }
    
    public  static void createAndShowGUI(VisualDisplayTableModel visualDisplayTableModel) {
        // Create and set up the window.
        JFrame frame = new JFrame("Quantum Key Distribution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VisualDisplay newContentPane = new VisualDisplay(visualDisplayTableModel);
        
        //  visualDisplayTableModel.fireTableDataChanged();
        //visualDisplayTableModel.fireTableStructureChanged();
        // Create and set up the content pane
        
        
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);
        
        // Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }
    public static void  usage() {
        System.out.println("Usage error:");
    }
} // end public class QuantumEncryptionProcessSimulation

/////////////////////////////// end class ///////////////////////////////=