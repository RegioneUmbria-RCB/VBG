
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella TIPIPROCEDURE il 01/09/2014 16.26.50
    ///
    ///												ATTENZIONE!!!
    ///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
    /// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
    ///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///
    [DataTable("TIPIPROCEDURE")]
    [Serializable]
    public partial class TipiProcedure : BaseDataClass
    {

        [KeyField("CODICEPROCEDURA", Type = DbType.Decimal)]
        [useSequence]
        public int? Codiceprocedura { get; set; }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }



        [DataField("PROCEDURA", Type = DbType.String, CaseSensitive = false, Size = 80)]
        public string Procedura { get; set; }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 255)]
        public string Note { get; set; }

        [DataField("GIORNI", Type = DbType.Decimal)]
        public int? Giorni { get; set; }

        [DataField("NUMGGCDS", Type = DbType.Decimal)]
        public int? Numggcds { get; set; }

        [DataField("CODICEOGGETTO", Type = DbType.Decimal)]
        public int? Codiceoggetto { get; set; }

        [DataField("CODICEOGGETTODIAGRAMMA", Type = DbType.Decimal)]
        public int? Codiceoggettodiagramma { get; set; }

        [DataField("FLAGATTOCHIUSURA", Type = DbType.Decimal)]
        public int? Flagattochiusura { get; set; }

        [DataField("FLAGPREVEDECDS", Type = DbType.Decimal)]
        public int? Flagprevedecds { get; set; }

        [DataField("FLAGAUTOCERTIFICABILE", Type = DbType.Decimal)]
        public int? Flagautocertificabile { get; set; }

        [DataField("NUMGGVALIDITAPROVV", Type = DbType.Decimal)]
        public int? Numggvaliditaprovv { get; set; }

        [DataField("CODICENATURA", Type = DbType.Decimal)]
        public int? Codicenatura { get; set; }

        [DataField("IDCHIUSURAISTANZA", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idchiusuraistanza { get; set; }

        [DataField("IDESITOPROVVAUTORIZZATIVO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idesitoprovvautorizzativo { get; set; }

        [DataField("IDTRASMNEGATIVA", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idtrasmnegativa { get; set; }

        [DataField("IDCOMUNCDS", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idcomuncds { get; set; }

        [DataField("IDCHIUSURACDS", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idchiusuracds { get; set; }

        [DataField("CODLETTAUORIZZATIVA", Type = DbType.Decimal)]
        public int? Codlettauorizzativa { get; set; }

        [DataField("NUMGGINVIO", Type = DbType.Decimal)]
        public int? Numgginvio { get; set; }

        [DataField("DETERMINAZIONEINIZIOISTANZA", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Determinazioneinizioistanza { get; set; }

        [DataField("NUMGGCDSPIUDATA", Type = DbType.Decimal)]
        public int? Numggcdspiudata { get; set; }

        [DataField("IDAUTORICHIESTADOC", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idautorichiestadoc { get; set; }

        [DataField("NAUTOGGRICHIESTADOC", Type = DbType.Decimal)]
        public int? Nautoggrichiestadoc { get; set; }

        [DataField("IDAUTOCHIUSURACONTR", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idautochiusuracontr { get; set; }

        [DataField("IDAUTOSOSPENSIONE", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idautosospensione { get; set; }

        [DataField("IDAUTOPUBBLICITA", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idautopubblicita { get; set; }

        [DataField("IDAUTOAUDIZTERZI", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idautoaudizterzi { get; set; }

        [DataField("IDAUTOAUDIZTERZIGG", Type = DbType.Decimal)]
        public int? Idautoaudizterzigg { get; set; }

        [DataField("CODICEOGGETTODOL", Type = DbType.Decimal)]
        public int? Codiceoggettodol { get; set; }

        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software { get; set; }

        [DataField("CODICEOGGETTOFACSDOMPREC", Type = DbType.Decimal)]
        public int? Codiceoggettofacsdomprec { get; set; }

        [DataField("CODICEEXPORT", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Codiceexport { get; set; }

        [DataField("DETERMINAZIONEEFFICACIA", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Determinazioneefficacia { get; set; }

        [DataField("DETERMINAZIONEIDMOVIMENTO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Determinazioneidmovimento { get; set; }

        [DataField("DETERMINAZIONEESITO", Type = DbType.Decimal)]
        public int? Determinazioneesito { get; set; }

        [DataField("FLAG_ELABCH_TERMINI", Type = DbType.Decimal)]
        public int? FlagElabchTermini { get; set; }

        [DataField("FLAG_ELABCH_ENDO", Type = DbType.Decimal)]
        public int? FlagElabchEndo { get; set; }

        [DataField("IDCONSMINISTRI", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Idconsministri { get; set; }

        [DataField("CODICEOGGETTO_CERTINVIO", Type = DbType.Decimal)]
        public int? CodiceoggettoCertinvio { get; set; }

        [DataField("FLAG_DISABILITATO", Type = DbType.Decimal)]
        public int? FlagDisabilitato { get; set; }

        [DataField("ATRIB_TIPORICHIESTA", Type = DbType.Decimal)]
        public int? AtribTiporichiesta { get; set; }

        [DataField("FK_RITP_CODICE", Type = DbType.String, CaseSensitive = false, Size = 20)]
        public string FkRitpCodice { get; set; }

        [DataField("CODOGGETTO_RICEVUTACART", Type = DbType.Decimal)]
        public int? CodoggettoRicevutacart { get; set; }

    }
}

/*using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using Init.SIGePro.Collection;

namespace Init.SIGePro.Data
{
    [DataTable("TIPIPROCEDURE")]
    [Serializable]
    public class TipiProcedure : BaseDataClass
    {

        #region Key Fields

        string codiceprocedura=null;
        [useSequence]
        [KeyField("CODICEPROCEDURA", Type=DbType.Decimal)]
        public string CODICEPROCEDURA
        {
            get { return codiceprocedura; }
            set { codiceprocedura = value; }
        }

        string idcomune=null;
        [KeyField("IDCOMUNE",Size=6, Type=DbType.String )]
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        #endregion

        string procedura=null;
        [DataField("PROCEDURA",Size=80, Type=DbType.String, CaseSensitive=false)]
        public string PROCEDURA
        {
            get { return procedura; }
            set { procedura = value; }
        }

        string note=null;
        [DataField("NOTE",Size=255, Type=DbType.String, CaseSensitive=false)]
        public string NOTE
        {
            get { return note; }
            set { note = value; }
        }

        string giorni=null;
        [DataField("GIORNI", Type=DbType.Decimal)]
        public string GIORNI
        {
            get { return giorni; }
            set { giorni = value; }
        }

        string numggcds=null;
        [DataField("NUMGGCDS", Type=DbType.Decimal)]
        public string NUMGGCDS
        {
            get { return numggcds; }
            set { numggcds = value; }
        }

        string codiceoggetto=null;
        [DataField("CODICEOGGETTO", Type=DbType.Decimal)]
        public string CODICEOGGETTO
        {
            get { return codiceoggetto; }
            set { codiceoggetto = value; }
        }

        string codiceoggettodiagramma=null;
        [DataField("CODICEOGGETTODIAGRAMMA", Type=DbType.Decimal)]
        public string CODICEOGGETTODIAGRAMMA
        {
            get { return codiceoggettodiagramma; }
            set { codiceoggettodiagramma = value; }
        }

        string flagattochiusura=null;
        [DataField("FLAGATTOCHIUSURA", Type=DbType.Decimal)]
        public string FLAGATTOCHIUSURA
        {
            get { return flagattochiusura; }
            set { flagattochiusura = value; }
        }

        string flagprevedecds=null;
        [DataField("FLAGPREVEDECDS", Type=DbType.Decimal)]
        public string FLAGPREVEDECDS
        {
            get { return flagprevedecds; }
            set { flagprevedecds = value; }
        }

        string flagautocertificabile=null;
        [DataField("FLAGAUTOCERTIFICABILE", Type=DbType.Decimal)]
        public string FLAGAUTOCERTIFICABILE
        {
            get { return flagautocertificabile; }
            set { flagautocertificabile = value; }
        }

        string numggvaliditaprovv=null;
        [DataField("NUMGGVALIDITAPROVV", Type=DbType.Decimal)]
        public string NUMGGVALIDITAPROVV
        {
            get { return numggvaliditaprovv; }
            set { numggvaliditaprovv = value; }
        }

        string codicenatura=null;
        [DataField("CODICENATURA", Type=DbType.Decimal)]
        public string CODICENATURA
        {
            get { return codicenatura; }
            set { codicenatura = value; }
        }

        string idchiusuraistanza=null;
        [DataField("IDCHIUSURAISTANZA",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDCHIUSURAISTANZA
        {
            get { return idchiusuraistanza; }
            set { idchiusuraistanza = value; }
        }

        string idesitoprovvautorizzativo=null;
        [DataField("IDESITOPROVVAUTORIZZATIVO",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDESITOPROVVAUTORIZZATIVO
        {
            get { return idesitoprovvautorizzativo; }
            set { idesitoprovvautorizzativo = value; }
        }

        string idtrasmnegativa=null;
        [DataField("IDTRASMNEGATIVA",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDTRASMNEGATIVA
        {
            get { return idtrasmnegativa; }
            set { idtrasmnegativa = value; }
        }

        string idcomuncds=null;
        [DataField("IDCOMUNCDS",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDCOMUNCDS
        {
            get { return idcomuncds; }
            set { idcomuncds = value; }
        }

        string idchiusuracds=null;
        [DataField("IDCHIUSURACDS",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDCHIUSURACDS
        {
            get { return idchiusuracds; }
            set { idchiusuracds = value; }
        }

        string codlettauorizzativa=null;
        [DataField("CODLETTAUORIZZATIVA", Type=DbType.Decimal)]
        public string CODLETTAUORIZZATIVA
        {
            get { return codlettauorizzativa; }
            set { codlettauorizzativa = value; }
        }

        string numgginvio=null;
        [DataField("NUMGGINVIO", Type=DbType.Decimal)]
        public string NUMGGINVIO
        {
            get { return numgginvio; }
            set { numgginvio = value; }
        }

        string determinazioneinizioistanza=null;
        [DataField("DETERMINAZIONEINIZIOISTANZA",Size=2, Type=DbType.String, CaseSensitive=false)]
        public string DETERMINAZIONEINIZIOISTANZA
        {
            get { return determinazioneinizioistanza; }
            set { determinazioneinizioistanza = value; }
        }

        string numggcdspiudata=null;
        [DataField("NUMGGCDSPIUDATA", Type=DbType.Decimal)]
        public string NUMGGCDSPIUDATA
        {
            get { return numggcdspiudata; }
            set { numggcdspiudata = value; }
        }

        string idautorichiestadoc=null;
        [DataField("IDAUTORICHIESTADOC",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDAUTORICHIESTADOC
        {
            get { return idautorichiestadoc; }
            set { idautorichiestadoc = value; }
        }

        string nautoggrichiestadoc=null;
        [DataField("NAUTOGGRICHIESTADOC", Type=DbType.Decimal)]
        public string NAUTOGGRICHIESTADOC
        {
            get { return nautoggrichiestadoc; }
            set { nautoggrichiestadoc = value; }
        }

        string idautochiusuracontr=null;
        [DataField("IDAUTOCHIUSURACONTR",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDAUTOCHIUSURACONTR
        {
            get { return idautochiusuracontr; }
            set { idautochiusuracontr = value; }
        }

        string idautosospensione=null;
        [DataField("IDAUTOSOSPENSIONE",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDAUTOSOSPENSIONE
        {
            get { return idautosospensione; }
            set { idautosospensione = value; }
        }

        string idautopubblicita=null;
        [DataField("IDAUTOPUBBLICITA",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDAUTOPUBBLICITA
        {
            get { return idautopubblicita; }
            set { idautopubblicita = value; }
        }

        string idautoaudizterzi=null;
        [DataField("IDAUTOAUDIZTERZI",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string IDAUTOAUDIZTERZI
        {
            get { return idautoaudizterzi; }
            set { idautoaudizterzi = value; }
        }

        string idautoaudizterzigg=null;
        [DataField("IDAUTOAUDIZTERZIGG", Type=DbType.Decimal)]
        public string IDAUTOAUDIZTERZIGG
        {
            get { return idautoaudizterzigg; }
            set { idautoaudizterzigg = value; }
        }

        string codiceoggettodol=null;
        [DataField("CODICEOGGETTODOL", Type=DbType.Decimal)]
        public string CODICEOGGETTODOL
        {
            get { return codiceoggettodol; }
            set { codiceoggettodol = value; }
        }

        string software=null;
        [DataField("SOFTWARE",Size=2, Type=DbType.String)]
        public string SOFTWARE
        {
            get { return software; }
            set { software = value; }
        }

        string codiceoggettofacsdomprec=null;
        [DataField("CODICEOGGETTOFACSDOMPREC", Type=DbType.Decimal)]
        public string CODICEOGGETTOFACSDOMPREC
        {
            get { return codiceoggettofacsdomprec; }
            set { codiceoggettofacsdomprec = value; }
        }

        string codiceexport=null;
        [DataField("CODICEEXPORT",Size=2, Type=DbType.String, CaseSensitive=false)]
        public string CODICEEXPORT
        {
            get { return codiceexport; }
            set { codiceexport = value; }
        }

        string determinazioneefficacia=null;
        [DataField("DETERMINAZIONEEFFICACIA",Size=2, Type=DbType.String, CaseSensitive=false)]
        public string DETERMINAZIONEEFFICACIA
        {
            get { return determinazioneefficacia; }
            set { determinazioneefficacia = value; }
        }

        string determinazioneidmovimento=null;
        [DataField("DETERMINAZIONEIDMOVIMENTO",Size=6, Type=DbType.String, CaseSensitive=false)]
        public string DETERMINAZIONEIDMOVIMENTO
        {
            get { return determinazioneidmovimento; }
            set { determinazioneidmovimento = value; }
        }

        string determinazioneesito=null;
        [DataField("DETERMINAZIONEESITO", Type=DbType.Decimal)]
        public string DETERMINAZIONEESITO
        {
            get { return determinazioneesito; }
            set { determinazioneesito = value; }
        }

        string flag_elabch_termini=null;
        [DataField("FLAG_ELABCH_TERMINI", Type=DbType.Decimal)]
        public string FLAG_ELABCH_TERMINI
        {
            get { return flag_elabch_termini; }
            set { flag_elabch_termini = value; }
        }

        string flag_elabch_endo=null;
        [DataField("FLAG_ELABCH_ENDO", Type=DbType.Decimal)]
        public string FLAG_ELABCH_ENDO
        {
            get { return flag_elabch_endo; }
            set { flag_elabch_endo = value; }
        }

        #region Arraylist per gli inserimenti nelle tabelle collegate

        TipiProcedureDocumentiCollection _Documenti = new TipiProcedureDocumentiCollection();
        public TipiProcedureDocumentiCollection Documenti
        {
            get { return _Documenti; }
            set { _Documenti = value; }
        }

        TipiProcedureAvvioCollection _MovimentiAvvio = new TipiProcedureAvvioCollection();
        public TipiProcedureAvvioCollection MovimentiAvvio
        {
            get { return _MovimentiAvvio; }
            set { _MovimentiAvvio = value; }
        }

        #endregion

        public override string ToString()
        {
            return PROCEDURA;
        }

    }
}*/