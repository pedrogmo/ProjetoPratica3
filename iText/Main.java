import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Main
{
	public static void main(
		String[] args)
	{
		try
		{
			BufferedReader teclado = new BufferedReader(
				new InputStreamReader(System.in));
		}
		catch(Exception exc)
		{
			System.err.println(exc.getMessage());
		}
	}
}