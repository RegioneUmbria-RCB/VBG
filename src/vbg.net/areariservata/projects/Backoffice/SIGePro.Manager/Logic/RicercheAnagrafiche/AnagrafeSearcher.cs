using System;
using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
	/// <summary>
	/// Descrizione di riepilogo per AnagrafeSearcher.
	/// </summary>
	public class AnagrafeSearcher : AnagrafeSearcherBase
	{
		public AnagrafeSearcher(string className):base(className)
		{
		}

		public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
		{
			var filtro = new Anagrafe{
				IDCOMUNE = IdComune,
				CODICEFISCALE = codiceFiscale,
				FLAG_DISABILITATO = "0"
			};

			return (Anagrafe)SigeproDb.GetClass(filtro);
		}

		public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
		{
			var filtro = new Anagrafe{
				IDCOMUNE = IdComune,
				CODICEFISCALE = codiceFiscale,
				TIPOANAGRAFE = tipoPersona == TipoPersona.PersonaFisica ? "F" : "G",
				FLAG_DISABILITATO = "0"
			};

			return (Anagrafe)SigeproDb.GetClass(filtro);
		}

		public override Anagrafe ByPartitaIvaImp(string partitaIva)
		{
			var filtro = new Anagrafe{
				IDCOMUNE = IdComune,
				PARTITAIVA = partitaIva,
				FLAG_DISABILITATO = "0"
			};

			var anagrafeRet = (Anagrafe)SigeproDb.GetClass(filtro);

			// Non mi piace ma al momento non c'è alternativa
			var vertParix = new ConfigurazioneParix(x =>
			{
				x.IdComune = IdComune;
				x.IdComuneAlias = Alias;
				x.Database = SigeproDb;
				return x;
			});

            if (anagrafeRet == null  || (vertParix.IsVerticalizzazioneAttiva && vertParix.Get.CercaSoloCf))
                anagrafeRet = ByCodiceFiscaleImp(TipoPersona.PersonaGiuridica, partitaIva);
			
			return anagrafeRet;

		}

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            var filtro = new Anagrafe{
				IDCOMUNE = IdComune,
				NOME = nome,
				NOMINATIVO = cognome,
				FLAG_DISABILITATO = "0"
			};

			return SigeproDb.GetClassList(filtro).ToList<Anagrafe>();
        }
	}
}
