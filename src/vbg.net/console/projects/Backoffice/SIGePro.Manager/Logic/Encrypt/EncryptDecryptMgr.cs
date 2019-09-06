using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Encrypt
{
	/// <summary>
	/// Descrizione di riepilogo per EncryptDecryptMgr.
	/// </summary>
	/*[Guid("ce706e12-06ec-4931-92dc-30406d8144ed"),
	ClassInterface(ClassInterfaceType.None)]*/
	public class EncryptDecryptMgr : IEncryptDecryptMgr
	{
		#region IEncryptDecryptMgr
		public string Encrypt(string sPassword, string sKey, string sTestVector)
		{
			try
			{
				//TODO: Decidere come ricevere la key
				RijndaelAlgorithm pEncrypt = new RijndaelAlgorithm(sKey, sTestVector);
				return pEncrypt.Encrypt(sPassword);
			}
			catch (Exception ex)
			{
				throw new Exception("Problema durante il criptaggio della password.Messaggio: " + ex.Message);
			}
		}

		public string Decrypt(string sPassword, string sKey, string sTestVector)
		{
			try
			{
				//TODO: Decidere come ricevere la key
				RijndaelAlgorithm pDecrypt = new RijndaelAlgorithm(sKey, sTestVector);
				return pDecrypt.Decrypt(sPassword);
			}
			catch (Exception ex)
			{
				throw new Exception("Problema durante il decriptaggio della password.Messaggio: " + ex.Message);
			}
		}
		#endregion
	}
}
