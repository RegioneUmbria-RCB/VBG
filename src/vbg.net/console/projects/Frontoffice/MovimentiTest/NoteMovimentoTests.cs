using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using Init.Sigepro.FrontEnd.GestioneMovimenti;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using MovimentiTest.Helpers;

namespace MovimentiTest
{
	/// <summary>
	/// Summary description for NoteMovimento
	/// </summary>
	[TestClass]
	public class NoteMovimentoTests : MovimentoFrontofficeTestClass
	{
		const string IdComune = "E256";
		const int IdMovimento = 1;
		const string NuoveNote = "Note movimento";

		public override void OnTestInitialize()
		{
			var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);

			this._bus.Send(crea);
		}


		[TestMethod]
		public void Quando_una_nota_viene_modificata_viene_generato_un_evento_di_tipo_NoteMovimentoModificate()
		{
			var cmd = new ModificaNoteMovimento( IdComune, IdMovimento, NuoveNote );

			this._bus.Send(cmd);

			var eventi = this.GetEventiGeneratiNelTest( )
							 .ToList();

			Assert.AreEqual<int>(1, eventi.Count);

			Assert.IsInstanceOfType(eventi[0], typeof(NoteMovimentoModificate));
			Assert.AreEqual<string>((eventi[0] as NoteMovimentoModificate).IdComune, IdComune);
			Assert.AreEqual<int>((eventi[0] as NoteMovimentoModificate).IdMovimento, IdMovimento);
			Assert.AreEqual<string>((eventi[0] as NoteMovimentoModificate).TestoNote, NuoveNote);
		}

	}
}
