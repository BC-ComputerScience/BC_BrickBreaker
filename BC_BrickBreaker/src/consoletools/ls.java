package consoletools;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ls implements ShellProgram{
	
	private static final int ALL=1;
	private static final int LONG=2;
	private static final int HUMAN=4;

	public int Execute(Shell s, String... args) {
		File dir=s.currentDirectory();
		File[] contents=dir.listFiles();
		
		int flags=parseArgs(args);
		
		if(contents!=null){
			for(File f:contents){
				if((flags&ALL)==ALL||!(f.isHidden())){
					s.out.println(printFile(f,flags));
				}
			}
			return 0;
		}
		return 1;
	}
	private String printFile(File f, int flags){
		StringBuilder toPrint=new StringBuilder();
		
		if((flags&LONG)==LONG){
			toPrint.append('-');
			if(f.canRead()){
				toPrint.append('r');
			}else{
				toPrint.append('-');
			}
			if(f.canWrite()){
				toPrint.append('w');
			}else{
				toPrint.append('-');
			}
			if(f.canExecute()){
				toPrint.append('x');
			}else{
				toPrint.append('-');
			}
			toPrint.append(' ');
			String files;
			String padding="     ";
			if(f.isDirectory()){
				files=Integer.toString(1);
			}else{
				File[] temp=f.listFiles();
				files=Integer.toString(temp!=null?temp.length:2);
			}
			files=padding.substring(files.length())+files;
			toPrint.append(files);
			
			long bytes=f.length();
			if((flags&HUMAN)==HUMAN){
				padding="      ";
				String suffix="B";
				if(bytes>1024){
					bytes=bytes>>>10;
					suffix="K";
				}
				if(bytes>1024){
					bytes=bytes>>>10;
					suffix="M";
				}
				if(bytes>1024){
					bytes=bytes>>>10;
					suffix="G";
				}
				toPrint.append(padding.substring(Long.toString(bytes).length())+bytes+suffix);
			}else{
				padding="             ";
				toPrint.append(padding.substring(Long.toString(bytes).length())+bytes);
			}
			String date=new SimpleDateFormat("dd/MM/yy").format(new Date(f.lastModified()));//(f.lastModified()).;
			toPrint.append("  "+date+"  ");
		}
		if(f.isDirectory()){
			toPrint.append(f.getName()+"/");
		}else{
			toPrint.append(f.getName());
		}
		return toPrint.toString();
	}
	
	
	private int parseArgs(String[] args){
		int ret=0;
		for(String s:args){
			if(s.charAt(0)=='-'){
				ret=ret|parseArg(s);
			}else{
				System.out.println(s);
			}
		}
		return ret;
		
	}
	private int parseArg(String s){
		int ret=0;
		for(int i=0;i<s.length();i++){
			switch(s.charAt(i)){
			case 'h':
				ret=ret|HUMAN;
			case 'l':
				ret=ret|LONG;
				break;
			case 'a':
				ret=ret|ALL;
				break;
			}
		}
		return ret;
		
	}

}
