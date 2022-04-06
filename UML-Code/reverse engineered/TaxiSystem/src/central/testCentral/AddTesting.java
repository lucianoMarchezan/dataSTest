package testCentral;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
/**
 * @author Christian Kekeiss e0025971@stud3.tuwien.ac.at
 *  
 */
public class AddTesting {
	public static void main(String[] args) {
		//Files to change
		AddTesting at = new AddTesting(".\\central\\Central.java");
		at = new AddTesting(".\\central\\Operator.java");

	}

	public AddTesting(String source) {
		try {
			//Backup the original File
			File file = new File(source);
			String filename = file.getName();
			File out = new File(source.substring(0, source.indexOf(filename)) + filename + ".backup");
			BufferedReader br = new BufferedReader(new FileReader(file));
			BufferedWriter bw = new BufferedWriter(new FileWriter(out));
			String newLine = null;
			while ((newLine = br.readLine()) != null) {
				bw.write(newLine);
				bw.newLine();
			}
			br.close();
			bw.close();
			//change the original File
			out = new File(source);
			file = new File(source.substring(0, source.indexOf(filename)) + filename + ".backup");
			String classname = filename.substring(0, filename.indexOf(".java"));
			br = new BufferedReader(new FileReader(file));
			bw = new BufferedWriter(new FileWriter(out));
			String content = "";
			while ((newLine = br.readLine()) != null) {
				content += newLine + "\n";
			}
			int i = 0, j = 0;
			String str1 = "", str2 = "", methodName = "";
			while ((i = content.indexOf("public void", i)) != -1) {
				methodName = "";
				j = i + 12; //length of "public void " is 11
				while (content.charAt(j) != '(' && content.charAt(j) != ' ') {
					methodName += Character.toString(content.charAt(j));
					j++;
				}
				i = content.indexOf("{", i);
				str1 = content.substring(0, i + 1);
				str2 = content.substring(i + 1, content.length());
				content = str1 + "\n" + "testCentral.Output.print(\"" + classname + ":" + methodName + "\");" + str2;
			}
			//			Pattern p = null;
			//			p = Pattern.compile("(\\s*)public\\svoid(\\s+)([a-zA-Z]*)\\([\\w,]*\\)(\\s*){");
			//			Matcher m = p.matcher(newLine);
			//			if (m.find()) {
			//				bw.write(newLine);
			//				bw.newLine();
			//				bw.write("Output.print(\"" + classname + ":" + m.group(2) + "\");");
			//				bw.newLine();
			//			}
			//			else {
			//				bw.write(newLine);
			//				bw.newLine();
			//			}
			//while
			bw.write(content);
			br.close();
			bw.close();
			System.out.println("Adding print methods successful!");
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}