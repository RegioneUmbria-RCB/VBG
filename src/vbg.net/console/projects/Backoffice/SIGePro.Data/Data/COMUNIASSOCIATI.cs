using System;
using System.Data;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
    [DataTable("COMUNIASSOCIATI")]
    [Serializable]
    public class ComuniAssociati : BaseDataClass
    {
        string codicecomune = null;
        [DataField("CODICECOMUNE", Size = 5, Type = DbType.String, CaseSensitive = false)]
        public string CODICECOMUNE
        {
            get { return codicecomune; }
            set { codicecomune = value; }
        }

        string si_stemma = null;
        [DataField("SI_STEMMA", Type = DbType.Decimal)]
        public string SI_STEMMA
        {
            get { return si_stemma; }
            set { si_stemma = value; }
        }

        string si_intestazione1 = null;
        [DataField("SI_INTESTAZIONE1", Size = 100, Type = DbType.String, CaseSensitive = false)]
        public string SI_INTESTAZIONE1
        {
            get { return si_intestazione1; }
            set { si_intestazione1 = value; }
        }

        string si_intestazione2 = null;
        [DataField("SI_INTESTAZIONE2", Size = 100, Type = DbType.String, CaseSensitive = false)]
        public string SI_INTESTAZIONE2
        {
            get { return si_intestazione2; }
            set { si_intestazione2 = value; }
        }

        string si_intestazione3 = null;
        [DataField("SI_INTESTAZIONE3", Size = 100, Type = DbType.String, CaseSensitive = false)]
        public string SI_INTESTAZIONE3
        {
            get { return si_intestazione3; }
            set { si_intestazione3 = value; }
        }

        string si_pdp1 = null;
        [DataField("SI_PDP1", Size = 255, Type = DbType.String, CaseSensitive = false)]
        public string SI_PDP1
        {
            get { return si_pdp1; }
            set { si_pdp1 = value; }
        }

        string si_pdp2 = null;
        [DataField("SI_PDP2", Size = 255, Type = DbType.String, CaseSensitive = false)]
        public string SI_PDP2
        {
            get { return si_pdp2; }
            set { si_pdp2 = value; }
        }

        string idcomune = null;
        [DataField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        [DataField("CODICEAMMINISTRAZIONE_IPA", Size = 20, Type = DbType.String)]
        public string CodiceamministrazioneIpa { get; set; }

        [DataField("CODICEFISCALE", Size = 11, Type = DbType.String)]
        public string CodiceFiscale { get; set; }

        [DataField("CODICE_ENTE_PAYER", Size = 5, Type = DbType.String)]
        public string CodiceEntePayer { get; set; }
    }
}