import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import org.apache.commons.lang3.text.WordUtils;
/*
 * Window Class
 * To construct a window that will display output and collect input from the user.
 */
public class Window extends JFrame {
	static Properties properties = new Properties();
	private static final long serialVersionUID = 1L;
	private JTextField input;
	public static JTextArea output;
	private JScrollPane outputScrollPane;
	private DefaultCaret caret;
	public String text = "";
	private TextFieldStreamer tFS;
	/*
	 * Window()
	 * Constructor for the window.
	 */
	public Window() throws IOException {
		//Window creation start
		super(WordUtils.capitalizeFully(properties.propertiesInit("resources/universe.txt", "universe")));
		Main.setUniverse(properties.propertiesInit("resources/universe.txt", "universe").toLowerCase());
		setSize(800, 600);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Window creation end
		//Font properties fetch start
		String outputBackground = properties.propertiesInit("resources/theme properties.txt", "output.background");
		outputBackground = outputBackground.toUpperCase();
		String[] oBA = outputBackground.split(",");
		String outputForeground = properties.propertiesInit("resources/theme properties.txt", "output.foreground");
		outputForeground = outputForeground.toUpperCase();
		String[] oFA = outputForeground.split(",");
		String inputBackground = properties.propertiesInit("resources/theme properties.txt", "input.background");
		inputBackground = inputBackground.toUpperCase();
		String[] iBA = inputBackground.split(",");
		String inputForeground = properties.propertiesInit("resources/theme properties.txt", "input.foreground");
		inputForeground = inputForeground.toUpperCase();
		String[] iFA = inputForeground.split(",");
		String caretColor = properties.propertiesInit("resources/theme properties.txt", "caret.color");
		caretColor = caretColor.toUpperCase();
		String[] cCA = caretColor.split(",");
		String fontName = properties.propertiesInit("resources/theme properties.txt", "font");
		//Font properties fetch end
		//Font customization start
		InputStream is = this.getClass().getResourceAsStream(
			"resources/fonts/" + fontName);
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
		} catch (FontFormatException FontCreationFFE) {
			// TODO Auto-generated catch block
			FontCreationFFE.printStackTrace();
		} catch (IOException FontFileIOE) {
			// TODO Auto-generated catch block
			FontFileIOE.printStackTrace();
		}
		//Font customization end
		//Output text area construction start
		output = new JTextArea();
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		//Output background set start
		try {
			Color oBColor = new Color(Integer.parseInt(oBA[0]),Integer.parseInt(oBA[1]),Integer.parseInt(oBA[2]));
			output.setBackground(oBColor);
		} catch (NumberFormatException oBColorNFE) {
			// TODO Auto-generated catch block
			oBColorNFE.printStackTrace();
		}
		//Output background set end
		//Output foreground set start
		try {
			Color oFColor = new Color(Integer.parseInt(oFA[0]),Integer.parseInt(oFA[1]),Integer.parseInt(oFA[2]));
			output.setForeground(oFColor);
		} catch (NumberFormatException oFColorNFE) {
			// TODO Auto-generated catch block
			oFColorNFE.printStackTrace();
		}
		//Output foreground set end
		output.setFont(f);
		output.setAutoscrolls(true);
		PrintStream ps = new PrintStream(new TextAreaOutputStream(output));
		System.setOut(ps);
		System.setErr(ps);
		outputScrollPane = new JScrollPane(output);
		outputScrollPane.setAutoscrolls(true);
		caret = (DefaultCaret) output.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//Output text area construction end
		//Input text area construction start
		input = new JTextField();
		input.setEditable(true);
		//input background set start
		try {
			Color iBColor = new Color(Integer.parseInt(iBA[0]),Integer.parseInt(iBA[1]),Integer.parseInt(iBA[2]));
			input.setBackground(iBColor);
		} catch (NumberFormatException iBColorNFE) {
			// TODO Auto-generated catch block
			iBColorNFE.printStackTrace();
		}
		//input background set end
		//input foreground set start
		try {
			Color iFColor = new Color(Integer.parseInt(iFA[0]),Integer.parseInt(iFA[1]),Integer.parseInt(iFA[2]));
			input.setForeground(iFColor);
		} catch (NumberFormatException iFColorNFE) {
			// TODO Auto-generated catch block
			iFColorNFE.printStackTrace();
		}
		//input foreground set end
		//caret color set start
		try {
			Color cCColor = new Color(Integer.parseInt(cCA[0]),Integer.parseInt(cCA[1]),Integer.parseInt(cCA[2]));
			input.setCaretColor(cCColor);
		} catch (NumberFormatException cCColorNFE) {
			// TODO Auto-generated catch block
			cCColorNFE.printStackTrace();
		}
		//caret color set end
		input.setFont(f);
		tFS = new TextFieldStreamer(input);
		input.addKeyListener(tFS);
		System.setIn(tFS);
		add(outputScrollPane, BorderLayout.CENTER);
		add(input, BorderLayout.SOUTH);
		//Input text area construction end
	}
	/*
	 * windowInit()
	 * Activates and opens the window on the users monitor.
	 */
	public void windowInit() throws IOException {
		new Window().setVisible(true);
	}
	/*
	 * scrollUpdate()
	 * Used to scroll the output text area of the window to the latest output for the user's
	 * convenience.
	 */
	public void scrollUpdate() {
		output.setCaretPosition(output.getDocument().getLength());
	}
}