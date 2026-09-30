// -----------------------------------------------------------------------
// <copyright file="DocumentiIstanzaExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.Linq;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService
{
    public partial class DocumentiIstanza : IDocumentoIstanzaOggettoDiVerifica
    {
        public StatoVerificaDocumentoEnum EsitoVerifica
        {
            get
            {
                switch (this.ControlloOk.GetValueOrDefault(-1))
                {
                    case 0:
                        return StatoVerificaDocumentoEnum.NonValido;

                    case 1:
                        return StatoVerificaDocumentoEnum.Valido;
                }

                return StatoVerificaDocumentoEnum.DaVerificare;
            }
        }

        public bool ContieneOggetto => !String.IsNullOrEmpty(this.CODICEOGGETTO);

        public bool ContieneDatiSensibili => this.Oggetto?.ContieneDatiSensibili ?? false;

        public string CodiceOggetto => this.CODICEOGGETTO;
    }


    public partial class DocumentiIstanzaOggetti
    {
        private static class Constants
        {
            public const string Md5Key = "MD5_SUM";
            public const string DatiSensibiliKey = "DATO_SENSIBILE";
        }

        [SoapIgnore]
        [XmlIgnore]
        public bool HasMd5
        {
            get
            {
                return !String.IsNullOrEmpty(this.Md5);
            }
        }

        [SoapIgnore]
        [XmlIgnore]
        public string Md5
        {
            get { return this.GetMetadatoMd5(); }
            set { this.AggiungiMetadatoMd5(value); }
        }

        public bool ContieneDatiSensibili => this.GetMetadato(Constants.DatiSensibiliKey, "0") == "1";

        private string GetMetadatoMd5()
        {
            return this.GetMetadato(Constants.Md5Key);
        }

        public void AggiungiMetadatoMd5(string md5)
        {
            var metadati = new List<OggettiMetadati>();

            if (this.Metadati != null)
            {
                metadati.AddRange(this.Metadati.Where(x => x.Chiave != Constants.Md5Key));
            }

            metadati.Add(new OggettiMetadati
            {
                Chiave = Constants.Md5Key,
                Valore = md5
            });

            this.Metadati = metadati.ToArray();
        }

        public string GetMetadato(string chiaveMetadato, string valoreDefault = "")
        {
            return this.Metadati?.FirstOrDefault(x => x.Chiave == chiaveMetadato)?.Valore ?? valoreDefault;
        }
    }
}
