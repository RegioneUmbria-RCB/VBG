using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
    internal class Ra147Table : QueryTable
    {
        public Ra147Table(string prefix)
			: base(Ravenna2DbClient.Constants.TabellaRA147.Nome, prefix)
		{
		}

        internal QueryField CodiceSezione
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaRA147.CampoCodiceSezione); }
        }

        internal QueryField CodiceCircoscrizione
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaRA147.CampoCodiceCircoscrizione); }
        }

        internal QueryField Frazione
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaRA147.CampoDescrizioneFrazione); }
        }

        public override QueryField AllFields()
        {
            return new CompositeQueryField(this, new[]{
				Ravenna2DbClient.Constants.TabellaRA147.CampoCodiceSezione,
				Ravenna2DbClient.Constants.TabellaRA147.CampoCodiceCircoscrizione,
				Ravenna2DbClient.Constants.TabellaRA147.CampoDescrizioneFrazione
			});
        }

        // public const string CampoNome = "RA147";
        // public const string CampoObjectId = "OBJECTID";
        // public const string CampoCodiceSezione = "COD_SEZ";
        // public const string CampoAnno = "ANNO";
        // public const string CampoCodFraz = "COD_FRAZ";
        // public const string CampoTipSez = "TIP_SEZ";
        // public const string CampoCodUe = "COD_UE";
        // public const string CampoCodiceCircoscrizione = "COD_CIRC";
        // public const string CampoDescrizioneFrazione = "DESCRIZION";
        // public const string CampoDescrizi_1 = "DESCRIZI_1";
    }
}
