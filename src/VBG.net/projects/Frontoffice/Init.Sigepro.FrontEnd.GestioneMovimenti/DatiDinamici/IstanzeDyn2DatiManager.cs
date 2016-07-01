using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class IstanzeDyn2DatiManager : IIstanzeDyn2DatiManager
	{
		public class IstanzeDyn2Dati : IIstanzeDyn2Dati
		{
			public string Idcomune { get; set; }

			public int? Codiceistanza { get; set; }

			public int? FkD2cId { get; set; }
			public int? Indice { get; set; }
			public int? IndiceMolteplicita { get; set; }
			public string Valore { get; set; }
			public string Valoredecodificato { get; set; }
		}


		DatiMovimentoDaEffettuare _movimentoDaEffettuare;
		ICommandSender _bus;

		public IstanzeDyn2DatiManager(DatiMovimentoDaEffettuare datiMovimentoDaEffettuare, ICommandSender bus)
		{
			// TODO: Complete member initialization
			this._movimentoDaEffettuare = datiMovimentoDaEffettuare;
			this._bus = bus;
		}

		#region IIstanzeDyn2DatiManager Members

		public SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
		{
			return new SerializableDictionary<int,List<IIstanzeDyn2Dati>>(
					this._movimentoDaEffettuare
						.ValoriSchedeDinamiche
						.Select( x => new IstanzeDyn2Dati{
							Codiceistanza = this._movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.CodiceIstanza,
							FkD2cId = x.Id,
							Idcomune = this._movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.IdComune,
							Indice = 0,
							IndiceMolteplicita = x.IndiceMolteplicita,
							Valore = x.Valore,
							Valoredecodificato = x.ValoreDecodificato
						} as IIstanzeDyn2Dati)
						.GroupBy(x => x.FkD2cId.Value)
						.ToDictionary(gdc => gdc.Key, gdc => gdc.ToList())
					);
		}

		
		public void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoIstanza modello, IEnumerable<SIGePro.DatiDinamici.CampoDinamico> campiDaSalvare)
		{
			var idComune = this._movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.IdComune;
			var idMovimento = this._movimentoDaEffettuare.Id;

			campiDaSalvare.Select(x => new EliminaValoriCampo(idComune, idMovimento, x.Id))
						  .ToList()
						  .ForEach(x => this._bus.Send(x));

			campiDaSalvare.ToList().ForEach(campo => {

				var indiceMolteplicita = 0;

				campo.ListaValori.ToList().ForEach(valore => {

					var cmd = new ModificaValoreDatoDinamicoDelMovimento(idComune, idMovimento, campo.Id, indiceMolteplicita++, valore.Valore, valore.ValoreDecodificato);

					this._bus.Send(cmd);					
				});
			});

			this._bus.Send( new CompletaCompilazioneSchedaDinamica( idComune , idMovimento , modello.IdModello ));
		}

		public void EliminaValoriCampi(ModelloDinamicoIstanza modello, IEnumerable<SIGePro.DatiDinamici.CampoDinamico> campiDaEliminare)
		{
			//throw new NotImplementedException();
		}

		#endregion
	}
}
