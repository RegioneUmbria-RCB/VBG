using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Init.SIGePro.Data;

namespace ParixStandard
{
	public class AnagrafeSearcher : AnagrafeSearcherParixBase
	{
		Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher _searcherSigepro;

		public AnagrafeSearcher():base("PARIXSTANDARD")
		{
			_searcherSigepro = new Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher("PARIXSTANDARD");
		}

		public override void Init()
		{
			_searcherSigepro.InitParams(this.IdComune, this.Alias, this.SigeproDb);
		}

		public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
		{
			return _searcherSigepro.ByCodiceFiscaleImp( TipoPersona.PersonaFisica, codiceFiscale);
			// return _searcherSigepro.ByCodiceFiscaleImp(codiceFiscale);
		}

		public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
		{
			if (tipoPersona == TipoPersona.PersonaFisica)
			{
				return ByCodiceFiscaleImp(codiceFiscale);
			}
			else
			{
				//Persona giuridica
				return base.ByPartitaIvaImp(codiceFiscale);
			}
		}

		public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
		{
			return _searcherSigepro.ByNomeCognomeImp(nome, cognome);
		}
	}
}
