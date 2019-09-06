using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
    class ParicelleCatastaliTable : QueryTable
    {
        public ParicelleCatastaliTable(string prefix) : base(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoNome, prefix)
        {

        }

        internal QueryField ObjectId
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoObjectid); }
        }

        internal QueryField Comune
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoComune); }
        }

        internal QueryField Sezione
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoSezione); }
        }

        internal QueryField Foglio
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoFoglio); }
        }

        internal QueryField Allegato
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoAllegato); }
        }

        internal QueryField Sviluppo
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoSviluppo); }
        }

        internal QueryField Origine
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoOrigine); }
        }

        internal QueryField Numero
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoNumero); }
        }

        internal QueryField Livello
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoLivello); }
        }

        internal QueryField Chiave
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoChiave); }
        }

        internal QueryField Sez
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoSez); }
        }

        internal QueryField Giskey
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaParicelleCatastali.CampoGiskey); }
        }
    }
}
