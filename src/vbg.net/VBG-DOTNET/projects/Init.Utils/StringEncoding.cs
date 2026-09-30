using System.Text;

namespace Init.Utils
{
	/// <summary>
	/// Funzioni per la codifica/decodifica di bytes in stringhe in formato UTF8
	/// </summary>
	public class StringEncoding
	{
		private StringEncoding()
		{
		}

		/// <summary>
		/// Converte una stringa in un array di bytes
		/// </summary>
		/// <param name="sourceString">stringa da convertire</param>
		/// <returns>Contenuto della stringa convertito</returns>
		public static byte[] StringToBytes(string sourceString)
		{
            return StringToBytes(sourceString,TipoCodifica.UTF8);
		}

        /// <summary>
        /// Converte una stringa in un array di bytes
        /// </summary>
        /// <param name="sourceString">stringa da convertire</param>
        /// <returns>Contenuto della stringa convertito</returns>
        public static byte[] StringToBytes(string sourceString, TipoCodifica tipoCodifica)
        {
			Encoding encoding = null;

			switch (tipoCodifica)
			{
				case TipoCodifica.UTF8:
					encoding = Encoding.UTF8;
					break;
				case TipoCodifica.ISO88591:
					encoding = Encoding.GetEncoding("iso-8859-1");
					break;
			}

			return encoding.GetBytes(sourceString);
        }


		/// <summary>
		/// Converte un array di bytes in una stringa
		/// </summary>
		/// <param name="byteBuffer">Buffer di bytes da convertire</param>
		/// <returns>stringa contenente il contenuto del buffer</returns>
		public static string BytesToString(byte[] byteBuffer)
		{
            return BytesToString(byteBuffer,TipoCodifica.UTF8);
		}


        /// <summary>
        /// Converte un array di bytes in una stringa
        /// </summary>
        /// <param name="byteBuffer">Buffer di bytes da convertire</param>
        /// <returns>stringa contenente il contenuto del buffer</returns>
        public static string BytesToString(byte[] byteBuffer,TipoCodifica tipoCodifica)
        {
			Encoding encoding = null;

            switch (tipoCodifica)
            {
                case TipoCodifica.UTF8:
					encoding = Encoding.UTF8;
                    break;
                case TipoCodifica.ISO88591:
					encoding = Encoding.GetEncoding("iso-8859-1");
                    break;
            }

			return encoding.GetString(byteBuffer);
        }
	}
}