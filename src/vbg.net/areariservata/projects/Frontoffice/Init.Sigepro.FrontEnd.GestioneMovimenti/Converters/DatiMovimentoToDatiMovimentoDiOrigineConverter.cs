// -----------------------------------------------------------------------
// <copyright file="MovimentoDaEffettuareConverter.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using AutoMapper;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
	using log4net;
	using Init.Utils;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneSchedeDinamiche;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento;

	/// <summary>
	/// Responsabile della conversione tra un oggetto di tipo <see cref="DatiMovimento"/> e un oggetto di tipo <see cref="MovimentoDiOrigine"/>
	/// </summary>
	public class DatiMovimentoToDatiMovimentoDiOrigineConverter : ITypeConverter<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>
	{
		ILog _log = LogManager.GetLogger(typeof(DatiMovimentoToDatiMovimentoDiOrigineConverter));

		#region ITypeConverter<DatiMovimento,DatiMovimentoDiOrigine> Members

		public MovimentoDiOrigine Convert(ResolutionContext context)
		{
			var origine = (DatiMovimentoDaEffettuareDto)context.SourceValue;

			_log.DebugFormat("DatiMovimentoToDatiMovimentoDiOrigineConverter.Convert from {0}", StreamUtils.SerializeClass(origine));

			
			var destinazione = new MovimentoDiOrigine();
            destinazione.Software = origine.Software;

            destinazione.Amministrazione = origine.Amministrazione;
			destinazione.DataAttivita = origine.DataMovimento;
			destinazione.DatiIstanza = new RiferimentiIstanza
			{
				CodiceIstanza = origine.CodiceIstanza,
				IdComune = origine.IdComune,
				NumeroIstanza = origine.NumeroIstanza,
				DataIstanza = origine.DataIstanza,
				Protocollo = new DatiProtocolloMovimento
				{
					Data = origine.DataProtocolloIstanza,
					Numero = origine.NumeroProtocolloIstanza
				}				
			};
			destinazione.Esito = origine.Esito;
			destinazione.IdMovimento = origine.CodiceMovimento;
			destinazione.NomeAttivita = origine.Descrizione;
			destinazione.Note = origine.Note;
			destinazione.Oggetto = origine.Parere;
			destinazione.CodiceProcedimento = origine.CodiceInventario;
			destinazione.Procedimento = origine.DescInventario;
			destinazione.Protocollo = new DatiProtocolloMovimento
			{
				Data = origine.DataProtocollo,
				Numero = origine.NumeroProtocollo
			};

			destinazione.Pubblica = origine.Pubblica;
			destinazione.PubblicaEsito = origine.VisualizzaEsito;
			destinazione.PubblicaOggetto = origine.VisualizzaParere;
            destinazione.PubblicaSchede = origine.PubblicaSchede;
			
			if (origine.Allegati != null)
			{
				destinazione.Allegati = origine
											.Allegati
											.Where( x => !String.IsNullOrEmpty(x.CODICEOGGETTO))
											.Select(x => new DatiAllegatoMovimento
											{
												IdAllegato =  int.Parse(x.CODICEOGGETTO),
												Descrizione = x.DESCRIZIONE,
												Note = x.NOTE
											}).ToList();
			}

			if (origine.SchedeDinamiche != null)
			{
				destinazione.SchedeDinamiche = origine.SchedeDinamiche.Select(scheda => new SchedaDinamicaMovimento
				{
					Compilata = false,
					IdScheda = scheda.Id,
					NomeScheda = scheda.Titolo,
					Valori = scheda.Valori == null ? new List<ValoreSchedaDinamicaMovimento>() :  scheda.Valori.Select(val => new ValoreSchedaDinamicaMovimento
					{
						Id = val.Id,
						IndiceMolteplicita = val.Indice,
						Valore = val.Valore,
						ValoreDecodificato = val.ValoreDecodificato
					}).ToList(),
					IdCampiDinamiciContenuti = scheda.IdCampiContenuti.ToList()
				}).ToList();
			}
            /*
            if (origine.DocumentiSostituibili != null)
            {
                origine.DocumentiSostituibili.ToList().ForEach(doc =>
                {
                    var codiceOggetto = doc.CodiceOggetto;
                    var descrizione = doc.Descrizione;
                    var idDocumento = doc.IdDocumento;
                    var nomeFile = doc.NomeFile;

                    if (doc.Origine == OrigineDocumentoEnum.Intervento)
                    {
                        destinazione.AggiungiDocumentoSostituibileIntervento(idDocumento, descrizione, codiceOggetto, nomeFile);
                    }
                    else
                    {
                        destinazione.AggiungiDocumentoSostituibileEndo(idDocumento, descrizione, codiceOggetto, nomeFile);
                    }
                    
                });


                
            }
            */
			return destinazione;
		}

		#endregion
	}
}
