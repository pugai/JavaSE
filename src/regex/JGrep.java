package regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Tianlong
 * eg. Args: scores.txt "\bJ\w+"
 */
public class JGrep {

	/**
	 * 文件中匹配搜索
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("Usage: java JGrep filepath regex");
			System.exit(0);
		}
		
		List<String> content = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0]).getAbsoluteFile()));
			try {
				String s;
				while((s = in.readLine()) != null){
					content.add(s);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Pattern p = Pattern.compile(args[1]);
		Matcher m = p.matcher("");
		int index = 0;
		int lineNum = content.size();
		for(int i = 0; i < lineNum; i++){
			m.reset(content.get(i));
			while(m.find())
				System.out.println(++index + ": " + m.group() + ": [" + (i + 1) + "," + (m.start() + 1) + "]");
		}
	}

}
