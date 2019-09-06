using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using MovimentiTest.Helpers;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;

namespace MovimentiTest
{
	[TestClass]
	public class ReadInterfaceTest : MovimentoFrontofficeTestClass
	{
		[TestMethod]
		public void Quando_viene_creato_un_nuovo_movimento_i_dati_del_movimento_di_origine_devono_essere_nulli()
		{

			var idComune = "E256";
			var idMovimentoOrigine = 666;
			var idMovimentoDaFare = 10;

			var cmd = new CreaMovimento(idComune, idMovimentoDaFare, idMovimentoOrigine);

			this._bus.Send(cmd);

			var datiMovimentoCreato = this._dataContext.GetDataStore().MovimentoDaEffettuare;

			Assert.AreEqual<int>(idMovimentoOrigine, datiMovimentoCreato.IdMovimentoDiOrigine);
			/*
			var modello = base.GetClasseTestMovimenti();

			Assert.AreEqual<string>( modello.Amministrazione , datiMovimentoCreato.Amministrazione );
			Assert.AreEqual<string>(modello.Descrizione, datiMovimentoCreato.NomeAttivita);
			Assert.AreEqual<DateTime?>( modello.DataMovimento , datiMovimentoCreato.DataAttivita );
			Assert.AreEqual<int>( modello.CodiceIstanza , datiMovimentoCreato.DatiIstanza.CodiceIstanza );
			Assert.AreEqual<string>( modello.IdComune , datiMovimentoCreato.DatiIstanza.IdComune );
			Assert.AreEqual<string>( modello.NumeroIstanza , datiMovimentoCreato.DatiIstanza.NumeroIstanza );
			Assert.AreEqual<DateTime?>( modello.DataProtocolloIstanza , datiMovimentoCreato.DatiIstanza.Protocollo.Data);
			Assert.AreEqual<string>( modello.NumeroProtocolloIstanza , datiMovimentoCreato.DatiIstanza.Protocollo.Numero);
			Assert.AreEqual<string>(modello.Esito, datiMovimentoCreato.Esito);
			Assert.AreEqual<int>(modello.CodiceMovimento, datiMovimentoCreato.IdMovimento);
			Assert.AreEqual<string>(modello.Note, datiMovimentoCreato.Note);
			Assert.AreEqual<string>(modello.Parere, datiMovimentoCreato.Oggetto);
			Assert.AreEqual<string>(modello.DescInventario, datiMovimentoCreato.Procedimento);
			Assert.AreEqual<DateTime?>(modello.DataProtocollo, datiMovimentoCreato.Protocollo.Data);
			Assert.AreEqual<string>(modello.NumeroProtocollo, datiMovimentoCreato.Protocollo.Numero);
			Assert.AreEqual<int>(modello.Allegati.Length, datiMovimentoCreato.Allegati.Count);

			for (int i = 0; i < modello.Allegati.Length; i++)
			{
				var allegato = modello.Allegati[i];

				Assert.AreEqual<int>(Convert.ToInt32(allegato.CODICEOGGETTO), datiMovimentoCreato.Allegati[i].IdAllegato);
				Assert.AreEqual<string>(allegato.DESCRIZIONE, datiMovimentoCreato.Allegati[i].Descrizione);
				Assert.AreEqual<string>(allegato.NOTE, datiMovimentoCreato.Allegati[i].Note);
			}
			*/
		}

		[TestMethod]
		public void Quando_vengono_modificate_le_note_di_un_movimento_devono_essere_aggiornate_le_note_del_movimento_da_effettuare()
		{
			var idComune			= "E256";
			var idMovimentoOrigine	= 666;
			var idMovimentoDaFare	= 10;
			var note				= "Nuove note";

			var cmd1 = new CreaMovimento(idComune, idMovimentoDaFare, idMovimentoOrigine);

			this._bus.Send(cmd1);

			var cmd2 = new ModificaNoteMovimento(idComune, idMovimentoDaFare, note);

			this._bus.Send(cmd2);

			var datiMovimento = this._dataContext.GetDataStore().MovimentoDaEffettuare;

			Assert.IsInstanceOfType(datiMovimento, typeof(MovimentoDaEffettuare));

			Assert.AreEqual<string>(note, datiMovimento.Note);
		}

		[TestMethod]
		public void Aggiunta_e_rimozione_degli_allegati_di_un_movimento()
		{
			var idComune = "E256";
			var idMovimentoOrigine = 666;
			var idMovimentoDaFare = 10;

			var idAllegato1 = 1;
			var nomeAllegato1 = "allegato1.ext";
			var descrizione1 = "descrizione1";

			var idAllegato2 = 2;
			var nomeAllegato2 = "allegato2.ext";
			var descrizione2 = "descrizione2";

			this._bus.Send(new CreaMovimento(idComune, idMovimentoDaFare, idMovimentoOrigine));

			// Test aggiunta di un allegato
			this._bus.Send(new AggiungiAllegatoAlMovimento(idComune, idMovimentoDaFare, idAllegato1, nomeAllegato1, descrizione1));
			this._bus.Send(new AggiungiAllegatoAlMovimento(idComune, idMovimentoDaFare, idAllegato2, nomeAllegato2, descrizione2));

			var datiMovimento = this._dataContext.GetDataStore().MovimentoDaEffettuare;

			Assert.AreEqual<int>(2, datiMovimento.Allegati.Count);

			var allegato = datiMovimento.Allegati[0];

			Assert.AreEqual<int>(idAllegato1, allegato.IdAllegato);
			Assert.AreEqual<string>(nomeAllegato1, allegato.Note);
			Assert.AreEqual<string>(descrizione1, allegato.Descrizione);

			allegato = datiMovimento.Allegati[1];

			Assert.AreEqual<int>(idAllegato2, allegato.IdAllegato);
			Assert.AreEqual<string>(nomeAllegato2, allegato.Note);
			Assert.AreEqual<string>(descrizione2, allegato.Descrizione);


			// Test rimozione di un allegato
			this._bus.Send(new RimuoviAllegatoDalMovimento(idComune, idMovimentoDaFare, idAllegato1));

			datiMovimento = this._dataContext.GetDataStore().MovimentoDaEffettuare;

			Assert.AreEqual<int>(1, datiMovimento.Allegati.Count);

			allegato = datiMovimento.Allegati[0];

			Assert.AreEqual<int>(idAllegato2, allegato.IdAllegato);
			Assert.AreEqual<string>(nomeAllegato2, allegato.Note);
			Assert.AreEqual<string>(descrizione2, allegato.Descrizione);
		}

	}
}
