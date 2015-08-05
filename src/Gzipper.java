import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author M. Reza Irvanda
 * @version	1.0
 */
/** Gzipper helps you to do Gzip Compression and Decompression*/
public class Gzipper {
	private FileOutputStream out;
	private GZIPOutputStream gout;
	boolean flagCompression;
	
	/**
	 * Constructor, only used if you want compress/decompress streaming content,
	 * for collected content, use static gzipFile of ungzipFile
	 * 
	 * @param pathOutput output file path, results of compression/decompression
	 * @param isCompression set true if you want to do compression, false if you want to do decompression
	 * @throws IOException if file can't be found
	 */
	public Gzipper(String pathOutput,boolean isCompression) throws IOException{
		flagCompression = isCompression;
		if(flagCompression){
			gout = new GZIPOutputStream(new FileOutputStream(pathOutput));
		}
		else{
			out = new FileOutputStream(pathOutput);
		}
	}
	 
	/**
	 * writeByte is used for streaming content. If input file has been collected, checkout gzipFile(String,String) or ungzipFile(String,String)
	 * @param b bytes of content
	 * @param first start of bytes
	 * @param length length of bytes taken
	 * @throws IOException if output file seems missing
	 */
	public void writeByte(byte[] b,int first, int length) throws IOException{
		if(flagCompression)
			gout.write(b,first,length);
		else
			out.write(b, first, length);
	}
	
	/**
	 * 
	 * @return Output File of Compression/Decompression, either as GZIPOutputStream or FileOutputStream
	 * @throws IOException if output file seems missing
	 */
	public OutputStream getOutput() throws IOException{
		if (flagCompression){
			gout.finish();
			return gout;
		}
		else{
			return out;
		}
	}
	
	public static void main(String[] args){
		String a,b;
		int i;
		
		Scanner scan = new Scanner(System.in);

		a = scan.nextLine();
		b = scan.nextLine();
		
		i = scan.nextInt();
		
		if(i==1){
			gzipFile(a, b);
			System.out.println("Kompresi Selesai");
		}
		else{
			ungzipFile(a, b);
			System.out.println("Decompresi Selesai");
		}
		
		scan.close();
		
	}
	
	/**
	 * Compress file with gzip compression
	 * @param pathInput where's file want to be compressed
	 * @param pathOutput where's compressed file want to be saved
	 */
	public static void gzipFile(String pathInput, String pathOutput){
		byte[] buffer = new byte[1024];
		try{
			GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(pathOutput));
			FileInputStream fin = new FileInputStream(pathInput);
			
			int len;
			
			while((len = fin.read(buffer)) > 0)
			{
				gzos.write(buffer,0,len);
			}
			
			fin.close();
			gzos.finish();
			gzos.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Decompress file with gzip decompression
	 * @param pathInput where's file want to be decompressed
	 * @param pathOutput where's decompressed file want to be saved
	 */
	
	public static void ungzipFile(String pathInput, String pathOutput){
		byte[] buffer = new byte[1024];
		try{
			FileOutputStream fout = new FileOutputStream(pathOutput);
			GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(pathInput));
			
			int len;
			
			while((len = gzis.read(buffer)) > 0){
				fout.write(buffer,0,len);
			}
			
			gzis.close();
			fout.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
