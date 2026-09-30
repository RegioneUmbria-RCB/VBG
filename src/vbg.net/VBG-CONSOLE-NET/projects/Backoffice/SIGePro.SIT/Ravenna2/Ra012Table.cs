using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Ravenna2
{
	class Ra012Table : QueryTable
	{
		public Ra012Table(string prefix)
			: base(Ravenna2DbClient.Constants.TabellaRA012.Nome, prefix)
		{
		}

		internal QueryField CodiceVia
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoCodiceVia); }
		}

		internal QueryField Civico
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoCivico); }
		}

		internal QueryField Esponente
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoEsponente); }
		}

		internal QueryField Sezione
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoSezione); }
		}

		internal QueryField Foglio
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoFoglio); }
		}

		internal QueryField Particella
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoParticella); }
		}

		internal QueryField Edificio
		{
			get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoEdificio); }
		}

        internal QueryField CodiceSezione
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoCodiceSezione); }
        }

        internal QueryField Toponimo
        {
            get { return GetField(Ravenna2DbClient.Constants.TabellaRA012.CampoToponimo); }
        }

        public override QueryField AllFields()
		{
			return new CompositeQueryField(this, new[]{
				Ravenna2DbClient.Constants.TabellaRA012.CampoCivico,
				Ravenna2DbClient.Constants.TabellaRA012.CampoCodiceVia,
				Ravenna2DbClient.Constants.TabellaRA012.CampoEdificio,
				Ravenna2DbClient.Constants.TabellaRA012.CampoEsponente,
				Ravenna2DbClient.Constants.TabellaRA012.CampoFoglio,
				Ravenna2DbClient.Constants.TabellaRA012.CampoParticella,
				Ravenna2DbClient.Constants.TabellaRA012.CampoSezione,
				Ravenna2DbClient.Constants.TabellaRA012.CodCivico,
                Ravenna2DbClient.Constants.TabellaRA012.CampoCodiceSezione,
                Ravenna2DbClient.Constants.TabellaRA012.CampoCAP                
			});
		}
	}
}
