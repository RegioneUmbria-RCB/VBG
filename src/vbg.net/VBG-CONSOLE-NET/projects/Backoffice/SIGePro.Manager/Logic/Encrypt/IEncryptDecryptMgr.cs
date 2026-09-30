using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Encrypt
{
	/// <summary>
	/// Descrizione di riepilogo per IEncryptDecryptMgr.
	/// </summary>
	//[Guid("af039299-6a0b-4134-b869-aa4b7a6751a3")]
	public interface IEncryptDecryptMgr
	{
		#region Metodi dell'interfaccia
		//Metodo per criptare una password
		//[DispId(0)]
		string Encrypt(string sPassword, string sKey, string sTestVector);

		//Metodo per decriptare una password
		//[DispId(1)]
		string Decrypt(string sPassword, string sKey, string sTestVector);
		#endregion
	}
}
