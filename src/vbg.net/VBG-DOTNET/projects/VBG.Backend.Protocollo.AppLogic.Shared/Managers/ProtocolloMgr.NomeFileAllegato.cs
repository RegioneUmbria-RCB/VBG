using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public partial class ProtocolloMgr
    {
        public class NomeFileAllegato
        {
            private readonly string _idComune;
            private readonly string _codicecomune;
            private readonly string _descrizione;
            private readonly string _estensione;
            private readonly string _nomeFile;

            private readonly int _codiceOggetto;

            private readonly int? _vertParamNomeFileMaxLength = (int?)null;

            internal NomeFileAllegato(string idComune, string codicecomune, Oggetti oggetto, string descrizione, string vertParamNomeFileMaxLength)
            {
                if (!String.IsNullOrEmpty(vertParamNomeFileMaxLength))
                    this._vertParamNomeFileMaxLength = Convert.ToInt32(vertParamNomeFileMaxLength);

                this._idComune = idComune;
                this._codicecomune = codicecomune;
                this._codiceOggetto = Convert.ToInt32(oggetto.CODICEOGGETTO);
                this._descrizione = Path.GetFileNameWithoutExtension(this.RimuoviCaratteriNonValidi(descrizione));
                this._nomeFile = this.RimuoviCaratteriNonValidi(oggetto.NOMEFILE);
                this._estensione = Path.GetExtension(this._nomeFile);

                if (!this._vertParamNomeFileMaxLength.HasValue && this._descrizione.Length > 30)
                    this._descrizione = this._descrizione.Substring(0, 30);

                var fileNameWithoutExtension = Path.GetFileNameWithoutExtension(this._nomeFile);

                if (Path.HasExtension(fileNameWithoutExtension) && Path.GetExtension(this._nomeFile).ToLower() == ProtocolloConstants.P7M)
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

            internal string GetNomeCompleto(string vertParamNomeFileOrigine, IEnumerable<ProtocolloAllegati> protoAllegati, bool creaCopiaFile)
            {
                var retVal = String.Concat(this._descrizione, this._estensione);

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

                if (creaCopiaFile)
                    retVal = this.GetNomeFileCopia(retVal, protoAllegati, 0);

                if (this._vertParamNomeFileMaxLength.HasValue && retVal.Length > this._vertParamNomeFileMaxLength.Value)
                {
                    var fileNameWithoutExtension = Path.GetFileNameWithoutExtension(retVal);

                    if (Path.HasExtension(fileNameWithoutExtension) && Path.GetExtension(retVal).ToLower() == ProtocolloConstants.P7M)
                        fileNameWithoutExtension = Path.GetFileNameWithoutExtension(fileNameWithoutExtension);

                    if (this._estensione.Length >= this._vertParamNomeFileMaxLength.Value)
                        throw new Exception(String.Format("SI E' VERIFICATO UN ERRORE DURANTE LA FORMATTAZIONE DEL NOME FILE DELL'ALLEGATO CODICE: {0} NOME: {1}, IL NUMERO DEI CARATTERI INDICATI E' INFERIORE AL CONSENTITO, NUMERO CARATTERI ESTENSIONE {2}: {3}, VALORE PARAMETRO NOMEFILE_MAXLENGTH: {4}", this._codiceOggetto, retVal, this._estensione, this._estensione.Length, this._vertParamNomeFileMaxLength.Value));

                    retVal = String.Concat(fileNameWithoutExtension.Substring(0, this._vertParamNomeFileMaxLength.Value - this._estensione.Length), this._estensione);
                }

                return retVal;
            }

            public string GetDescrizioneFileCopia(string descrizione, IEnumerable<ProtocolloAllegati> protoAllegati, bool creaCopia, int idx, string vertParamDescrFileMaxLength)
            {
                if (!String.IsNullOrEmpty(vertParamDescrFileMaxLength))
                {
                    var maxlength = Convert.ToInt32(vertParamDescrFileMaxLength); ;

                    if (descrizione.Length > maxlength)
                    {
                        descrizione = descrizione.Substring(0, maxlength);
                    }
                }

                if (!creaCopia)
                    return descrizione;

                var retVal = descrizione;

                var exist = protoAllegati.Any(x => x.Descrizione == descrizione);

                if (exist)
                {
                    idx++;
                    retVal = this.GetDescrizioneFileCopia($"[{idx}] {descrizione}", protoAllegati, true, idx, vertParamDescrFileMaxLength);
                }

                return retVal;
            }

            private string GetNomeFileCopia(string nomeFile, IEnumerable<ProtocolloAllegati> protoAllegati, int idx)
            {
                var retVal = nomeFile;

                var exist = protoAllegati.Any(x => x.NOMEFILE == nomeFile);

                if (exist)
                {
                    idx++;
                    retVal = this.GetNomeFileCopia($"[{idx}] {nomeFile}", protoAllegati, idx);
                }

                return retVal;
            }
        }
    }
}
