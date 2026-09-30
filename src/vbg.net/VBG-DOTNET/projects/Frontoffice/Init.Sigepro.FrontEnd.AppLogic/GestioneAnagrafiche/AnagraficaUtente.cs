// -----------------------------------------------------------------------
// <copyright file="AnagraficaUtente.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    using Init.Sigepro.FrontEnd.AppLogic.Adapters;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
    using System;
    using VBG.Frontend.AppLogic.WsAnagraficheService;

    [Serializable]
    public partial class AnagraficaUtente
    {
        public int? Codiceanagrafe { get; set; }

        public string Idcomune { get; set; }


        public string Nominativo { get; set; }

        public string Referente { get; set; }

        public int? Formagiuridica { get; set; }

        public int? Tipologia { get; set; }

        public string Indirizzo { get; set; }

        public string Citta { get; set; }

        public string Cap { get; set; }

        public string Provincia { get; set; }

        public string Telefono { get; set; }

        public string Telefonocellulare { get; set; }

        public string Fax { get; set; }

        public string Partitaiva { get; set; }

        public string Legalerappresentante { get; set; }

        public string Codicefiscale { get; set; }

        public string Note { get; set; }

        public string Email { get; set; }

        public string Regditte { get; set; }

        public string Regtrib { get; set; }

        public string Codcomregditte { get; set; }

        public string Codcomregtrib { get; set; }

        public string Codcomnascita { get; set; }

        public DateTime? Datanascita { get; set; }

        public DateTime? Dataregditte { get; set; }

        public DateTime? Dataregtrib { get; set; }

        public int? Invioemail { get; set; }

        public string Sesso { get; set; }

        public string Nome { get; set; }

        public int? Titolo { get; set; }

        public string Tipoanagrafe { get; set; }

        public string Indirizzolr { get; set; }

        public string Cittalr { get; set; }

        public string Caplr { get; set; }

        public string Provincialr { get; set; }

        public string Telefonolr { get; set; }

        public string Telefonocellularelr { get; set; }

        public DateTime? Datanominativo { get; set; }

        public int? Invioemailtec { get; set; }

        public int? Codicecittadinanza { get; set; }

        public string Comuneresidenza { get; set; }

        public string Password { get; set; }

        public string Indirizzocorrispondenza { get; set; }

        public string Cittacorrispondenza { get; set; }

        public string Capcorrispondenza { get; set; }

        public string Provinciacorrispondenza { get; set; }

        public string Comunecorrispondenza { get; set; }

        public string Provinciarea { get; set; }

        public string Numiscrrea { get; set; }

        public DateTime? Dataiscrrea { get; set; }

        public string Codicefiscalelr { get; set; }

        public int? FlagNoprofit { get; set; }

        public int? FlagDisabilitato { get; set; }

        public DateTime? DataDisabilitato { get; set; }

        public int? Codiceelencopro { get; set; }

        public string Numeroelencopro { get; set; }

        public string Provinciaelencopro { get; set; }

        public bool UtenteTester { get; set; }


        public Anagrafe ToWsAnagrafe()
        {
            var rVal = new Anagrafe();

            rVal.CODICEANAGRAFE = this.Codiceanagrafe.HasValue ? this.Codiceanagrafe.ToString() : String.Empty;
            rVal.IDCOMUNE = this.Idcomune;
            rVal.NOMINATIVO = this.Nominativo;
            rVal.FORMAGIURIDICA = this.Formagiuridica.HasValue ? this.Formagiuridica.ToString() : String.Empty;
            rVal.TIPOLOGIA = this.Tipologia.HasValue ? this.Tipologia.ToString() : String.Empty;
            rVal.INDIRIZZO = this.Indirizzo;
            rVal.CITTA = this.Citta;
            rVal.CAP = this.Cap;
            rVal.PROVINCIA = this.Provincia;
            rVal.TELEFONO = this.Telefono;
            rVal.TELEFONOCELLULARE = this.Telefonocellulare;
            rVal.FAX = this.Fax;
            rVal.PARTITAIVA = this.Partitaiva;
            rVal.CODICEFISCALE = this.Codicefiscale;
            rVal.NOTE = this.Note;
            rVal.EMAIL = this.Email;
            rVal.REGDITTE = this.Regditte;
            rVal.REGTRIB = this.Regtrib;
            rVal.CODCOMREGDITTE = this.Codcomregditte;
            rVal.CODCOMREGTRIB = this.Codcomregtrib;
            rVal.CODCOMNASCITA = this.Codcomnascita;
            rVal.DATANASCITA = this.Datanascita;
            rVal.DATAREGDITTE = this.Dataregditte;
            rVal.DATAREGTRIB = this.Dataregtrib;
            rVal.INVIOEMAIL = this.Invioemail.GetValueOrDefault(0).ToString();
            rVal.SESSO = this.Sesso;
            rVal.NOME = this.Nome;
            rVal.TITOLO = this.Titolo.HasValue ? this.Titolo.ToString() : String.Empty;
            rVal.TIPOANAGRAFE = this.Tipoanagrafe;
            rVal.DATANOMINATIVO = this.Datanominativo;
            rVal.INVIOEMAILTEC = this.Invioemailtec.GetValueOrDefault(0).ToString();
            rVal.CODICECITTADINANZA = this.Codicecittadinanza.HasValue ? this.Codicecittadinanza.ToString() : String.Empty;
            rVal.COMUNERESIDENZA = this.Comuneresidenza;
            rVal.PASSWORD = this.Password;
            rVal.INDIRIZZOCORRISPONDENZA = this.Indirizzocorrispondenza;
            rVal.CITTACORRISPONDENZA = this.Cittacorrispondenza;
            rVal.CAPCORRISPONDENZA = this.Capcorrispondenza;
            rVal.PROVINCIACORRISPONDENZA = this.Provinciacorrispondenza;
            rVal.COMUNECORRISPONDENZA = this.Comunecorrispondenza;
            rVal.PROVINCIAREA = this.Provinciarea;
            rVal.NUMISCRREA = this.Numiscrrea;
            rVal.DATAISCRREA = this.Dataiscrrea;
            rVal.FLAG_NOPROFIT = this.FlagNoprofit.GetValueOrDefault(0).ToString();
            rVal.FLAG_DISABILITATO = this.FlagDisabilitato.GetValueOrDefault(0).ToString();
            rVal.DATA_DISABILITATO = this.DataDisabilitato;
            rVal.CODICEELENCOPRO = this.Codiceelencopro.HasValue ? this.Codiceelencopro.ToString() : String.Empty;
            rVal.NUMEROELENCOPRO = this.Numeroelencopro;
            rVal.PROVINCIAELENCOPRO = this.Provinciaelencopro;
            rVal.FoUtenteTester = this.UtenteTester ? 1 : 0;

            return rVal;
        }

        public override string ToString()
        {
            var n = this.Nominativo;

            if (!string.IsNullOrEmpty(this.Nome))
                n += " " + this.Nome;

            return n;
        }

        public AnagraficaDomanda ToAnagraficaDomanda(IComuniService comuniService)
        {
            return new AnagrafeAdapter(this.ToWsAnagrafe(), comuniService)!.ToAnagraficaDomanda()!;
        }
    }
}
