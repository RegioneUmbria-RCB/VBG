using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Manager
{
	public partial class ProtocolloMgr
	{
		internal class NomeFileAllegato
		{
			string _idComune;
			string _codicecomune;
			string _descrizione;
			string _estensione;
			string _nomeFile;
			
            int _codiceOggetto;

            int? _vertParamNomeFileMaxLength = (int?)null;

            internal NomeFileAllegato(string idComune, string codicecomune, Oggetti oggetto, string descrizione, string vertParamNomeFileMaxLength)
			{
                if (!String.IsNullOrEmpty(vertParamNomeFileMaxLength))
                    _vertParamNomeFileMaxLength = Convert.ToInt32(vertParamNomeFileMaxLength);
                
				this._idComune = idComune;
				this._codicecomune = codicecomune;
				this._codiceOggetto = Convert.ToInt32(oggetto.CODICEOGGETTO);
				this._descrizione = Path.GetFileNameWithoutExtension(RimuoviCaratteriNonValidi(descrizione));
				this._nomeFile = RimuoviCaratteriNonValidi(oggetto.NOMEFILE);
				this._estensione = Path.GetExtension(this._nomeFile);

                if (!this._vertParamNomeFileMaxLength.HasValue && this._descrizione.Length > 30)
					this._descrizione = this._descrizione.Substring(0, 30);

				string fileNameWithoutExtension = Path.GetFileNameWithoutExtension(this._nomeFile);

                if (fileNameWithoutExtension.IndexOf(".") != -1 && Path.GetExtension(this._nomeFile) == ProtocolloConstants.P7M)
					this._estensione = String.Concat(Path.GetExtension(fileNameWithoutExtension), this._estensione);
			}

			private string RimuoviCaratteriNonValidi(string nomeFile)
			{
				return new String(nomeFile.Where(c => !Path.GetInvalidFileNameChars().Contains(c)).ToArray());
			}

			internal string GetEstensione()
			{
				return this._estensione;
			}

			internal string GetNomeCompleto(string vertParamNomeFileOrigine)
			{
                string retVal = String.Concat(this._descrizione, this._estensione);

				switch (vertParamNomeFileOrigine)
				{
					case "1":
						retVal = this._nomeFile;
                        break;
					case "2":
						retVal = String.Concat(this._idComune, "-", this._codiceOggetto, this._estensione);
                        break;
					case "3":
						retVal = String.Concat(this._codicecomune, "-", this._codiceOggetto, "-", this._descrizione, this._estensione);
                        break;
				}

                if (_vertParamNomeFileMaxLength.HasValue && retVal.Length > _vertParamNomeFileMaxLength.Value)
                {
                    string fileNameWithoutExtension = Path.GetFileNameWithoutExtension(retVal);

                    if (fileNameWithoutExtension.IndexOf(".") != -1 && Path.GetExtension(retVal) == ProtocolloConstants.P7M)
                        fileNameWithoutExtension = Path.GetFileNameWithoutExtension(fileNameWithoutExtension);

                    if (this._estensione.Length >= _vertParamNomeFileMaxLength.Value)
                        throw new Exception(String.Format("SI E' VERIFICATO UN ERRORE DURANTE LA FORMATTAZIONE DEL NOME FILE DELL'ALLEGATO CODICE: {0} NOME: {1}, IL NUMERO DEI CARATTERI INDICATI E' INFERIORE AL CONSENTITO, NUMERO CARATTERI ESTENSIONE {2}: {3}, VALORE PARAMETRO NOMEFILE_MAXLENGTH: {4}", this._codiceOggetto, retVal, this._estensione, this._estensione.Length, _vertParamNomeFileMaxLength.Value));

                    retVal = String.Concat(fileNameWithoutExtension.Substring(0, _vertParamNomeFileMaxLength.Value - this._estensione.Length), this._estensione);
                }

				return retVal;
			}
		}
	}
}
