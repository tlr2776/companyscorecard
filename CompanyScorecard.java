/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package companyscorecard;

/**
 *
 * @author tara
 */
public class CompanyScorecard {

    private static String fn; //file name

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Parser p = new Parser();

        if (args.length == 0) {
            System.out.println("Error: no file found.");
        } else {
            //for now I am treating this as though there could be arguments other than just the file name in case I choose to add some later
            for (String arg : args) {
                if (arg.endsWith("xlsx")) {
                    fn = arg;
                }
            }
            if (fn == null) {
                System.out.println("Error: No valid excel file found.");
                return;
            } else {
                p.openFile(fn);
                CompileScorecard c = new CompileScorecard();
            }
        }
    }

}
