using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    [DataTable("VW_ALBEROPROC")]
    [Serializable]
    public class VwAlberoProc : BaseDataClass
    {
        #region Key Fields

        int? sc_id = null;
        [useSequence]
        [KeyField("SC_ID", Type = DbType.Decimal)]
        public int? ScId
        {
            get { return sc_id; }
            set { sc_id = value; }
        }

        string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string Idcomune
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        #endregion


        string software = null;
        [DataField("SOFTWARE", Size = 2, Type = DbType.String)]
        public string Software
        {
            get { return software; }
            set { software = value; }
        }

        string sc_codice = null;
        [isRequired(MSG = "ALBEROPROC.SC_CODICE obbligatorio")]
        [DataField("SC_CODICE", Size = 10, Type = DbType.String)]
        public string ScCodice
        {
            get { return sc_codice; }
            set { sc_codice = value; }
        }

        int? sc_padre = null;
        [DataField("SC_PADRE", Type = DbType.Decimal)]
        public int? ScPadre
        {
            get { return sc_padre; }
            set { sc_padre = value; }
        }

        string sc_descrizione = null;
        [DataField("SC_DESCRIZIONE", Size = 412, Type = DbType.String, CaseSensitive = false)]
        public string ScDescrizione
        {
            get { return sc_descrizione; }
            set { sc_descrizione = value; }
        }

        string sc_descrizionepadre = null;
        [DataField("SC_DESCRIZIONEPADRE", Size = 329, Type = DbType.String, CaseSensitive = false)]
        public string ScDescrizionePadre
        {
            get { return sc_descrizionepadre; }
            set { sc_descrizionepadre = value; }
        }

        string sc_descrizionebreve = null;
        [DataField("SC_DESCRIZIONEBREVE", Size = 80, Type = DbType.String, CaseSensitive = false)]
        public string ScDescrizioneBreve
        {
            get { return sc_descrizionebreve; }
            set { sc_descrizionebreve = value; }
        }

        int? ordine0 = null;
        [DataField("ORDINE0", Type = DbType.Decimal)]
        public int? Ordine0
        {
            get { return ordine0; }
            set { ordine0 = value; }
        }

        int? ordine1 = null;
        [DataField("ORDINE1", Type = DbType.Decimal)]
        public int? Ordine1
        {
            get { return ordine1; }
            set { ordine1 = value; }
        }

        int? ordine2 = null;
        [DataField("ORDINE2", Type = DbType.Decimal)]
        public int? Ordine2
        {
            get { return ordine2; }
            set { ordine2 = value; }
        }

        int? ordine3 = null;
        [DataField("ORDINE3", Type = DbType.Decimal)]
        public int? Ordine3
        {
            get { return ordine3; }
            set { ordine3 = value; }
        }

        int? ordine4 = null;
        [DataField("ORDINE4", Type = DbType.Decimal)]
        public int? Ordine4
        {
            get { return ordine4; }
            set { ordine4 = value; }
        }

        int? fkcodicemercato = null;
        [DataField("FKCODICEMERCATO", Type = DbType.Decimal)]
        public int? FkCodiceMercato
        {
            get { return fkcodicemercato; }
            set { fkcodicemercato = value; }
        }

        int? fkidmercatiuso = null;
        [DataField("FKIDMERCATIUSO", Type = DbType.Decimal)]
        public int? FkIdMercatiUso
        {
            get { return fkidmercatiuso; }
            set { fkidmercatiuso = value; }
        }     

        public override string ToString()
        {
            return this.ScDescrizione;
        }

    }
}