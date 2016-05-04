import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Frank on 1/05/2016.
 */
public class toUnicode extends Frame implements ActionListener, WindowListener {

    private ArrayList<String> paragraph;
    private ArrayList<String> convertedParagraph;
    private Label lblCount;    // Declare a Label component
    private TextField tfCount; // Declare a TextField component
    private Button btnCount;   // Declare a Button component
    private TextArea taDisplay;

    public toUnicode() throws Exception{
        paragraph = new ArrayList<String>();
        convertedParagraph = new ArrayList<String>();

        setLayout(new FlowLayout());

        lblCount = new Label("toUnicode - Fkbkarencald1995");
        add(lblCount);

        btnCount = new Button("Convert");   // construct the Button component
        add(btnCount);

        taDisplay = new TextArea(30, 100); // 5 rows, 40 columns
        add(taDisplay);


        btnCount.addActionListener(this);
        // btnCount is the source object that fires ActionEvent when clicked.
        // The source add "this" instance as an ActionEvent listener, which provides
        //  an ActionEvent handler called actionPerformed().
        // Clicking btnCount invokes actionPerformed().

        setTitle("AWT Counter");  // "super" Frame sets its title
        setSize(800, 600);        // "super" Frame sets its initial window size

        // For inspecting the components/container objects
        // System.out.println(this);
        // System.out.println(lblCount);
        // System.out.println(tfCount);
        // System.out.println(btnCount);

        setVisible(true);
        addWindowListener(this);
    }

    public void run(){
        Scanner kb = new Scanner(System.in);
        String para = taDisplay.getText();
       // System.out.print("please enter filename: ");
        //String fileName = kb.nextLine();
        try {

            taDisplay.setText("");

            File desktopDir = new File(System.getProperty("user.home"), "Desktop\\output.txt");
            getParagraph(para);
            convert();
            PrintWriter pw = new PrintWriter(desktopDir);
            convertedParagraph.forEach(pw::println);

            pw.close();

        }
        catch (Exception e) {
            System.out.println("in Run: " + e);
        }
    }

    public void getParagraph(String input){

        StringTokenizer st = new StringTokenizer(input, "\n");

        while (st.hasMoreTokens()){
            paragraph.add(st.nextToken());
        }
    }

    public void convert(){
        for(String str: paragraph){
            convertedParagraph.add(getSentences(str));
        }
    }

    public String getSentences(String str){
        String newParagraph = "";
        if(str.length()>0) {
            boolean containFullStop = false;

            if (str.charAt(str.length() - 1) == '.') {
                containFullStop = true;
            }

            StringTokenizer stringTokenizer = new StringTokenizer(str, ".");
            int tokens = stringTokenizer.countTokens();

            if (tokens > 0) {
                for (int i = 0; i < tokens; ++i) {

                    if (tokens == i + 1 && !containFullStop) {
                        return (newParagraph + convertSentence(stringTokenizer.nextToken()));
                    }
                    newParagraph += convertSentence(stringTokenizer.nextToken()) + '.';
                }
            }
        }
            return newParagraph;
        }


    public String convertSentence(String sentence) {
                char[] charArray = sentence.toCharArray();
                String convertedStr = "";
                karenNew kn = new karenNew();

                for (char c : charArray) {
                    convertedStr += kn.aChar(c);
                }
        return (fix_check(convertedStr));
    }

    public String fix_check(String s){
        String s_consonants= "က ခ ဂ ဃ င စ ဆ ၡ ည တ ထ ဒ န ပ ဖ ဘ မ ယ ရ လ ဝ သ ဟ အ ဧ";
        String s_double_consonants = "ှ ၠ ြ ျ ";
        String s_vowels = "ါ ့  ၢ ု ူ ့  ံ ိ ီ";
        String s_tones = "ၢ် ာ် း ၣ် ၤ";

        //change ra
        String newString = "";

        if (s.contains("ြ")){
            char[] charArray = s.toCharArray();

            for (int i = 0 ; i < charArray.length; ++i) {
                if(charArray[i] == 'ြ' && !s_consonants.contains("" + charArray[i-1])){
                    newString += charArray[i+1] + "" + charArray[i];
                    ++i;
                }
                else{
                    newString += charArray[i];
                }
            }
            return newString;
        }

        return s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        run();
    }

    /* WindowEvent handlers */
    // Called back upon clicking close-window button
    @Override
    public void windowClosing(WindowEvent evt) {
        System.exit(0);  // Terminate the program
    }

    // Not Used, but need to provide an empty body to compile.
    @Override public void windowOpened(WindowEvent evt) { }
    @Override public void windowClosed(WindowEvent evt) { }
    @Override public void windowIconified(WindowEvent evt) { }
    @Override public void windowDeiconified(WindowEvent evt) { }
    @Override public void windowActivated(WindowEvent evt) { }
    @Override public void windowDeactivated(WindowEvent evt) { }
}
