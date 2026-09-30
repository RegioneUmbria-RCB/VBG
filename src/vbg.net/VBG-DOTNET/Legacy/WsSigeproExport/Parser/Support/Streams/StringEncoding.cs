using System.Text;

namespace Parser.Support.Streams
{
	public class StringEncoding
	{
		/// <summary>
		/// Converte una stringa in un array di bytes
		/// </summary>
		/// <param name="sourceString">stringa da convertire</param>
		/// <returns>Contenuto della stringa convertito</returns>
		public static byte[] StringToBytes(string sourceString)
		{
			Encoder encoder = new UTF8Encoding().GetEncoder();

			byte[] buffer = new byte[encoder.GetByteCount(sourceString.ToCharArray(), 0, sourceString.Length, true)];

			encoder.GetBytes(sourceString.ToCharArray(), 0, sourceString.Length, buffer, 0, true);

			return buffer;
		}


		/// <summary>
		/// Converte un array di bytes in una stringa
		/// </summary>
		/// <param name="byteBuffer">Buffer di bytes da convertire</param>
		/// <returns>stringa contenente il contenuto del buffer</returns>
		public static string BytesToString(byte[] byteBuffer)
		{
			Decoder decoder = new UTF8Encoding().GetDecoder();

			int charCount = decoder.GetCharCount(byteBuffer, 0, byteBuffer.Length);
			char[] charBuffer = new char[charCount];

			decoder.GetChars(byteBuffer, 0, byteBuffer.Length, charBuffer, 0);

			return new string(charBuffer);
		}
	}
}