using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.Utils;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
using Init.SIGePro.DatiDinamici.WebControls;

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


		MovimentoDaEffettuare _movimentoDaEffettuare;
        IEnumerable<SchedaDinamicaMovimento> _schedeDelMovimentoDiOrigine;
		ICommandSender _bus;

		public IstanzeDyn2DatiManager(MovimentoDaEffettuare datiMovimentoDaEffettuare, IEnumerable<SchedaDinamicaMovimento> schedeDelMovimentoDiOrigine, ICommandSender bus)
		{
			this._movimentoDaEffettuare = datiMovimentoDaEffettuare;
            this._schedeDelMovimentoDiOrigine = schedeDelMovimentoDiOrigine;
			this._bus = bus;
		}

		#region IIstanzeDyn2DatiManager Members

		public SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
		{
            var valoriSchede = this._schedeDelMovimentoDiOrigine
                                                            .SelectMany(x => x.Valori)
                                                            .Where(x => !string.IsNullOrEmpty(x.Valore))
                                                            .Select(x => new ValoreSchedaDinamicaMovimento
                                                            {
                                                                Id = x.Id,
                                                                IndiceMolteplicita = x.IndiceMolteplicita,
                                                                Valore = x.Valore,
                                                                ValoreDecodificato = x.ValoreDecodificato
                                                            });

            // POTENZIALE BUG!
            // Se l'utente ha già compilato una scheda dinamica non vengono mostrati eventuali valori letti
            // dal backend (vd trello!)
            valoriSchede = this._movimentoDaEffettuare.GetValoriCampiDinamiciODefault(valoriSchede);

			return new SerializableDictionary<int,List<IIstanzeDyn2Dati>>(
                    valoriSchede.Select( x => new IstanzeDyn2Dati{
							Codiceistanza = this._movimentoDaEffettuare.CodiceIstanza,
							FkD2cId = x.Id,
                            Idcomune = this._movimentoDaEffettuare.AliasComune,
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
			var idComune = this._movimentoDaEffettuare.AliasComune;
			var idMovimento = this._movimentoDaEffettuare.Id;
            var valoriStorici = this.GetValoriCampoDaIdModello(modello.IdComune, modello.IdIstanza, modello.IdModello, -1);


            foreach(var campo in campiDaSalvare)
            {
                if (campo.TipoCampo != TipoControlloEnum.Upload)
                {
                    continue;
                }

                if (!valoriStorici.ContainsKey(campo.Id))
                {
                    continue;
                }
                 
                var vecchiValori = valoriStorici[campo.Id].Select(x => new
                {
                    valore = x.Valore,
                    indiceMolteplicita = x.IndiceMolteplicita.Value
                });
                var nuoviValori = campo.ListaValori.Select(x => x.Valore);

                var daEliminare = vecchiValori.Where(x => !nuoviValori.Any(y => y == x.valore));

                foreach(var el in daEliminare)
                {
                    var cmd = new RimuoviAllegatoDellaSchedaDinamica(idComune, idMovimento, campo.Id, el.indiceMolteplicita, el.valore);
                    this._bus.Send(cmd);
                }
                
            }

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
