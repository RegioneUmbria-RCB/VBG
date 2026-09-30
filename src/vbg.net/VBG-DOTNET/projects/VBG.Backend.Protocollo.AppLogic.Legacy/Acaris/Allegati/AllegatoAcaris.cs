using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.VerificaFirmaDigitale;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Allegati
{
    public class AllegatoAcaris
    {
        private class Constants
        {
            public static string MetadatoFirmaDigitalePresente = "FIRMA_DIGITALE_PRESENTE";
        }

        public int CodiceOggetto { get; internal set; }
        public string NomeFile { get; internal set; }
        public string Id { get; internal set; }
        public byte[] Content { get; internal set; }
        public string MimeType { get; internal set; }
        public int ContentLenght { get; internal set; }
        public string Descrizione { get; internal set; }
        public IEnumerable<OggettiMetadati> Metadati => this._metadati;
        private readonly List<OggettiMetadati> _metadati = new List<OggettiMetadati>();
        public bool IsFirmato
        {
            get
            {
                if (this.Metadati == null)
                {
                    return false;
                }

                return this.Metadati.Any(x => x.Chiave == Constants.MetadatoFirmaDigitalePresente && x.Valore == "S");
            }
        }

        public AllegatoAcaris()
        {

        }

        public AllegatoAcaris(string id, string nomeFile, string mimeType)
        {
            this.Id = id;
            this.NomeFile = nomeFile;
            this.MimeType = mimeType;
        }

        public AllegatoAcaris(VerificaFirmaDigitaleService servizioVerificaFirma, ProtocolloAllegati allegato, IEnumerable<OggettiMetadati> metadati)
        {
            if (allegato == null)
            {
                return;
            }

            this.CodiceOggetto = Convert.ToInt32(allegato.CODICEOGGETTO);
            this.NomeFile = allegato.NOMEFILE;
            this.Content = allegato.OGGETTO;
            this.MimeType = this.DecodificaMime(allegato.MimeType);
            this.ContentLenght = (this.Content?.Length) ?? 0;
            this.Descrizione = allegato.Descrizione;
            this._metadati.AddRange(metadati);

            //verifica presenza metadato file firmato
            if (!this.Metadati.Any(x => x.Chiave.Equals(Constants.MetadatoFirmaDigitalePresente, StringComparison.InvariantCultureIgnoreCase)))
            {
                var fileFirmato = servizioVerificaFirma.VerificaFirmaDigitale(this.Content, this.NomeFile) ? "S" : "N";
                this._metadati.Add(new OggettiMetadati
                {
                    Chiave = Constants.MetadatoFirmaDigitalePresente,
                    Codiceoggetto = this.CodiceOggetto,
                    Idcomune = allegato.IDCOMUNE,
                    Valore = fileFirmato
                });
            }
        }

        private string DecodificaMime(string mimeType)
        {
            if (String.IsNullOrEmpty(mimeType))
            {
                return mimeType;
            }

            if (mimeType.ToUpper().StartsWith("X-"))
            {
                return mimeType.Substring(2);
            }

            return mimeType;
        }
    }
}
