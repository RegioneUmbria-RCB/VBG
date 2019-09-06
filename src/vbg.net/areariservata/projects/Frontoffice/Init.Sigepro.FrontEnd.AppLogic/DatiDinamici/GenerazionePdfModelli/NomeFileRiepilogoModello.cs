// -----------------------------------------------------------------------
// <copyright file="NomeFileRiepilogoDatiDinamici.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.IO;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class NomeFileRiepilogoModello
	{
		string _nomeBase;
		int _indiceMolteplicita;
		string _extension;

		internal NomeFileRiepilogoModello(string nomeBase, int indiceMolteplicita = -1, string extension = ".pdf")
		{
			this._nomeBase = nomeBase;
			this._indiceMolteplicita = indiceMolteplicita;
			this._extension = extension;
		}

		public override string ToString()
		{
			var nomeRiepilogo = this._nomeBase;

			if (this._indiceMolteplicita >= 0)
				nomeRiepilogo += this._indiceMolteplicita.ToString();

			return GetNomeFile(nomeRiepilogo);
		}

		private string GetNomeFile(string nomeRiepilogo)
		{
			var resChars = Path.GetInvalidFileNameChars();
			var fileName = nomeRiepilogo;

			for (int i = 0; i < resChars.Length; i++)
			{
				fileName = fileName.Replace(resChars[i], '_');
			}

			fileName += this._extension;

			return fileName;
		}

	}
}
