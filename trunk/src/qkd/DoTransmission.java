package qkd;

public class DoTransmission {
    
    int N = 0;
    int Eve = 0;
    String input = null;
    VisualDisplayTableModel visualDisplayTableModel = null;
    
    public DoTransmission(String input, int Eve, VisualDisplayTableModel visualDisplayTableModel) {
        N = input.length();
        this.input = input;
        this.Eve = Eve;
        this.visualDisplayTableModel = visualDisplayTableModel;
    }
    
    public void process() {
        System.out.println("\nN = The number of filters chosen = " + N);
        
        char[] evesFilters  = new char[N];
        char[] evesFilterSets 	= new char[N];
        
        
        // 	Arbitrarily choose the correspondence betwee filter and bit
        //	meaning.
        
        System.out.println("\nFilter SETs & bit meaning = ");
        System.out.println("\t{+} SET: (|) = 1, (-) = 0");
        System.out.println("\t{X} SET: (\\) = 1, (/) = 0");
        
        // 	Read in Alices filter choices i.e., arg[0].
        //	These are also the photons she sends.
        
        System.out.println("\n============== ALICE ==============");
        System.out.print  ("\nAlice's Filters == Alice's Photons.\n\n");
        char[] alicesFilters = SimulationMethods.getInputFiltersVector( input );
        
        
        // Convert Alices filter choices to filter SET choices
        // That she sends to Bob.
        
        System.out.print  ("\n\nAlice's Filter SETS sent to Bob.\n\n");
        char[] alicesFilterSets = SimulationMethods.getAliceFilterSets(alicesFilters);
        // Convert Alices filter choices to bits (she started with
        // bits for a message and converted to filters!!
        // Bob does not yet know these bits OR these filters.
        
        System.out.print  ("\nAlice's Bits.\n");
        int[]  alicesBits = SimulationMethods.getBitsVector(alicesFilters);
        
        
        // Randomly choose (generate) Bob's filters	from the set of 4 possible
        System.out.println("\n============== BOB ==============");
        System.out.print  ("\n\nBobs RANDOM Filter Choices.\n\n");
        char[] bobsFilters  = SimulationMethods.genBobsFilters(N);
        
        
        // Convert Bob's filter choices to filter SET choices
        
        System.out.print  ("\nBob's Filter SETS.\n\n");
        char[] bobsFilterSets = SimulationMethods.getBobsFilterSets(bobsFilters);
        
        
////////////////////////////////  Eve Before Call ///////////////////////180
        if (Eve == 2) {
            // Randomly choose (generate) Eve's filters
            // from the set of 4 possible.   Same process as for Bob.
            
            System.out.println("\n============== EVE ==============");
            System.out.print  ("\n\nEve's RANDOM Filter Choices.\n\n");
            evesFilters  = SimulationMethods.genBobsFilters(N);
            
            // Convert Eve's filter choices to filter SET choices
            
            System.out.print  ("\nEve's Filter SETS.\n\n");
            evesFilterSets = SimulationMethods.getEvesFilterSets(evesFilters);
            
            // Due to ignorance, Eve can only forward on to Bob a
            // partially random choice of polarized photons (filters)
            // as if they were coming from Alice.  In effect she is
            // randomly redefining the set sent by Alice.
            
            System.out.print  ("\nEve sends out Alice's (MODIFIED) Photons to BOB.\n");
            alicesFilters = SimulationMethods.evesFilterFixup(alicesFilters, evesFilters);
            
            
        }
        
/////////////////////// filterSetMatch ///////////////////////////
        // Alice calls Bob and they match FILTER SETS (not filters or bits)
        
        System.out.println("\n=========== BOB From ALICE/EVE ===========");
        System.out.print  ("\n\nAlice calls Bob With Filter SETS.");
        System.out.print  ("\nBob Says Which Filter SETS Match.");
        System.out.print  ("\nThis implies filter (& bit).\n\n");
        
        System.out.print  ("\nThese are Bob's filter sets matching Alice's.\n\n");
        
        char [] setMatches = SimulationMethods.filterSetMatch(alicesFilterSets, bobsFilterSets);
        
        // If Bob's filter SET matches then either he measured a photon or not.
        // If he measured a photon the FILTER matched. He knows the bit value.
        // If he measured NO photon, he knows his filter from the set is the
        //    opposite of the correct filter, so he still knows the correct
        //    filter and therefore the correct bit.
        
/////////////////////////// end filterSetMatch //////////////////////////
        
///////////////////////////// Bobs Final Bit Slots //////////////////////
        // Bob converts the matches into useable bits
        
        int[] finalBitsSlots = new int[N];
        
        // The original position of the finalBits matched sent by Alice
        
        System.out.print  ("\nFilter Matches Imply Bits That Match.\n");
        int[] finalBits = SimulationMethods.bitsAfterMatch(alicesFilters, bobsFilters, setMatches, finalBitsSlots);
        // This will printout the bits and also where they come from in
        // Alice's original input.
        
        System.out.print  ("\nThese bit matches are Bob/Alice.\n\n");
        
        char[] finalBitsEqual= SimulationMethods.bitsEqualTestAB(finalBitsSlots, setMatches, alicesBits, finalBits);
        
                /*
                 * After this Alice lets Bob know a subset of the actual bits sent.
                 * If ANY of them differ they have been compromised by EVE.
                 * All bits have to be dropped and they start over - or catch EVE!
                 */
        
/////////////////////////// end Bobs Final Bit Slots //////////////////////
        
        
////////////////////////////////  Eve After Call ///////////////////////
        if (Eve == 2) {
            System.out.println("\n=========== EVE From ALICE ===========");
            System.out.print("\n\nEve listens in about Filter SETS.");
            System.out.print("\nEve looks for filter SETS Match.");
            System.out.print("\nThis implies filter (& bit).\n");
            
            System.out.print("\nThese are Eve's filter sets matching Alice's.\n");
            System.out.print("\nEVE KNOWS THESE.\n\n");
            
            char [] evesSetMatches = SimulationMethods.filterSetMatch(alicesFilterSets, evesFilterSets);
            
            int[] evesFinalBitsSlots = new int[N];
            System.out.print("\nFilter Matches Give Eve's Maybe Matched Bits.\n\n");
            System.out.print("\nEVE DOES NOT KNOW ANY OF THIS.\n\n");
            
            int[] evesFinalBits = SimulationMethods.bitsAfterMatch(alicesFilters, evesFilters, evesSetMatches, evesFinalBitsSlots);
            
            System.out.print  ("\nThese bit matches are Eve/Alice.\n");
            System.out.print  ("\nThese are only a random guess.\n\n");
            char[] evesfinalBitsEqual=SimulationMethods.bitsEqualTestAE(evesFinalBitsSlots,  evesSetMatches,alicesBits, evesFinalBits);
        }
        visualDisplayTableModel.registerRow("alicesFilters", alicesFilters);
        visualDisplayTableModel.registerRow("alicesFilters", alicesFilterSets);
        visualDisplayTableModel.registerRow("alicesFilters", alicesBits);
        visualDisplayTableModel.registerRow("alicesFilters", bobsFilters);
        visualDisplayTableModel.registerRow("alicesFilters", bobsFilterSets);      
        visualDisplayTableModel.registerRow("alicesFilters", evesFilters);
        visualDisplayTableModel.registerRow("alicesFilters", evesFilterSets);        
        visualDisplayTableModel.registerRow("alicesFilters", alicesFilters);
        visualDisplayTableModel.registerRow("alicesFilters", setMatches);
        visualDisplayTableModel.registerRow("alicesFilters", finalBits);
        visualDisplayTableModel.registerRow("alicesFilters", finalBitsEqual);

    }
}