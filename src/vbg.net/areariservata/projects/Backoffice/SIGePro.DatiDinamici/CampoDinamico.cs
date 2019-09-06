using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.Exceptions;
using System.Text.RegularExpressions;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;

namespace Init.SIGePro.DatiDinamici
{
	public partial class CampoDinamico : CampoDinamicoBase
	{
		public CampoDinamico(ModelloDinamicoBase modello, IDyn2Campo campoDinamico, int posizioneOrizzontale, int posizioneVerticale)
			: base(modello)
		{
			base.PosizioneOrizzontale = posizioneOrizzontale;
			base.PosizioneVericale = posizioneVerticale;

			Inizializza(campoDinamico);
		}

		private void Inizializza(IDyn2Campo campo)
		{
			this.Id = campo.Id.GetValueOrDefault(int.MinValue);
			this.NomeCampo = campo.Nomecampo;
			this.Etichetta = campo.Etichetta;
			this.Descrizione = campo.Descrizione;
			this.TipoCampo = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), campo.Tipodato);

			// Carica lo script del modello
			var mgr = ModelloCorrente.DataAccessProvider.GetScriptCampiManager();

			var idComune = campo.Idcomune;
			var idCampo = campo.Id.GetValueOrDefault(int.MinValue);

			var scripts = mgr.GetScriptsCampo(idComune, idCampo);

			// Script caricamento
			if(scripts.ContainsKey(TipoScriptEnum.Caricamento))
			{
				var script = scripts[TipoScriptEnum.Caricamento];

				if (!String.IsNullOrEmpty(script.GetTestoScript()))
					this.ScriptCaricamento = new ScriptCampoDinamico(ModelloCorrente, script.GetTestoScript());
			}

			// Script modifica
			if (scripts.ContainsKey(TipoScriptEnum.Modifica))
			{
				var script = scripts[TipoScriptEnum.Modifica];

				if (!String.IsNullOrEmpty(script.GetTestoScript()))
					this.ScriptModifica = new ScriptCampoDinamico(ModelloCorrente, script.GetTestoScript());
			}

			// Script Salvataggio
			if (scripts.ContainsKey(TipoScriptEnum.Salvataggio))
			{
				var script = scripts[TipoScriptEnum.Salvataggio];

				if (!String.IsNullOrEmpty(script.GetTestoScript()))
					this.ScriptSalvataggio = new ScriptCampoDinamico(ModelloCorrente, script.GetTestoScript());
			}

			//Proprieta del controllo web (se esiste)
			var mgrProprieta = ModelloCorrente.DataAccessProvider.GetProprietaCampiManager();

			var propList = mgrProprieta.GetProprietaCampo(idComune, idCampo);

			try
			{
				propList.ForEach( p => {

					string key = p.Proprieta;
					string val = p.Valore;

					base.ImpostaProprietaRiservate(key, val);

					ProprietaControlloWeb.Add(new KeyValuePair<string, string>(key, val));
				});
			}
			catch (Exception ex)
			{
				string fmtMsg = "Errore durante l'assegnazione delle proprietà del campo dinamico {0} ({1}): {2}";

				throw new Exception(String.Format(fmtMsg, campo.Id, campo.Nomecampo, ex.ToString()), ex);
			}
		}

		public override IEnumerable<ErroreValidazione> Valida()
		{
			var errori = new List<ErroreValidazione>();

			var validatori = new CampiDinamiciValidationService().GetValidatoriPer(this);

			foreach (var validatore in validatori)
			{
				errori.AddRange(validatore.GetErroriDiValidazione());
			}

			return errori;
		}
	}
}
