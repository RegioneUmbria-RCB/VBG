// -----------------------------------------------------------------------
// <copyright file="EventBusTestClass.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest.Helpers
{
    using AutoMapper;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Bootstrap;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDiOrigine;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
    using Init.Sigepro.FrontEnd.GestioneMovimenti.Scadenzario;
    using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Moq;
    using System;
    using System.Collections.Generic;
    using System.Linq;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	[TestClass]
	public class MovimentoFrontofficeTestClass
	{
		protected EventsBus _bus;
		protected TestDataContext _dataContext;
		protected int NumeroEventiGeneratiDuranteInizializzazione { get; private set; }
		DatiMovimentoDaEffettuareDto _classeTestWebServiceMovimenti;
		EventJsonSerializer _serializer;

		public virtual void OnTestInitialize()
		{
		}

		[TestInitialize]
		public void InizializzazioneTests()
		{
			this._bus = new EventsBus();
			this._dataContext = new TestDataContext();
			this._serializer = new EventJsonSerializer(  EventTypesRegistry.RegisterEvents()
																			.FromAssembly( typeof( MovimentoCreato ).Assembly )
																			.Now() );

			var configuration = new GestioneMovimentiBootstrapper.GestioneMovimentiBootstrapperSettings( 
				this._bus,
				CreaMockMovimentiService(),
				this._dataContext,
				CreaMockScadenzeService(),
				CreaMockTrasmissioneService(),
                CreaMockMovimentoDiOrigineRepository()
			);

			GestioneMovimentiBootstrapper.Bootstrap(configuration);
			
			OnTestInitialize();

			this.NumeroEventiGeneratiDuranteInizializzazione = this._dataContext.GetDataStore().EventsStream.Count;
		}

        private IMovimentiDaEffettuareRepository CreaMockmovimentoDaEffettuareRepository()
        {
            var mock = new Mock<IMovimentiDaEffettuareRepository>();

            mock.Setup(x => x.GetById(It.IsAny<int>()));
            //mock.Setup(x => x.GetByIdHackUsaSoloPerCreazioneMovime(It.IsAny<int>())).Returns(Mapper.Map<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>(GetClasseTestMovimenti()));

            return mock.Object;
        }

        private IMovimentiDiOrigineRepository CreaMockMovimentoDiOrigineRepository()
        {
            MovimentiAutomapperBootstrapper.Bootstrap();

            var mock = new Mock<IMovimentiDiOrigineRepository>();

            mock.Setup(x => x.GetById(It.IsAny<MovimentoDaEffettuare>())).Returns(Mapper.Map<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>(GetClasseTestMovimenti()));
            mock.Setup(x => x.GetByIdHackUsaSoloPerCreazioneMovimento(It.IsAny<int>())).Returns(Mapper.Map<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>(GetClasseTestMovimenti()));

            return mock.Object;
        }

		private ITrasmissioneMovimentoService CreaMockTrasmissioneService()
		{
			var mock = new Mock<ITrasmissioneMovimentoService>();

			mock.Setup(x => x.Trasmetti(It.IsAny<int>()));

			return mock.Object;
		}

		private IScadenzeService CreaMockScadenzeService()
		{
			var mock = new Mock<IScadenzeService>();

			mock.Setup(x => x.GetById(It.IsAny<int>())).Returns(new Scadenza(new ElementoListaScadenze()));

			return mock.Object;
		}
		
		private IMovimentiBackofficeService CreaMockMovimentiService()
		{
			MovimentiAutomapperBootstrapper.Bootstrap();
            
			var mock = new Mock<IMovimentiBackofficeService>();
            
			//mock.Setup(x => x.GetById(It.IsAny<int>())).Returns(Mapper.Map<DatiMovimentoDaEffettuareDto, MovimentoDiOrigine>(GetClasseTestMovimenti()));
            
			return mock.Object;

            throw new NotImplementedException();
		}

		protected IEnumerable<Event> GetEventiGeneratiNelTest()
		{
			return this._dataContext
					   .GetDataStore()
					   .EventsStream
					   .Skip(this.NumeroEventiGeneratiDuranteInizializzazione)
					   .Select( x => (Event)this._serializer.Deserialize( x.EventType , x.EventData));
		}
		
		protected DatiMovimentoDaEffettuareDto GetClasseTestMovimenti()
		{
			if (_classeTestWebServiceMovimenti != null)
				return _classeTestWebServiceMovimenti;

			var cls = new DatiMovimentoDaEffettuareDto
			{
				CodiceMovimento = 666,
				DescInventario = String.Empty,
				Amministrazione = "UFFICIO DI COMPETENZA",
				Esito = "Positivo",
				Allegati = new MovimentiAllegati[] 
				{
					new MovimentiAllegati
					{
						IDCOMUNE = "E256",
						Id = "3125",
						IDALLEGATO = "1",
						CODICEMOVIMENTO = "50097",
						DESCRIZIONE= "Pubblicità nuova istanza",
						NOTE = "File generato automaticamente",
						CODICEOGGETTO = "360006",
						DATAREGISTRAZIONE = new DateTime(2012,10, 24),
						FlagPubblica = 1
					}
				},
				Descrizione = "Presentazione domanda",
				DataMovimento = new DateTime(2012, 10, 24),
				CodiceIstanza = 4820,
				NumeroIstanza = "462",
				VisualizzaParere = false,
				VisualizzaEsito = true,
				Pubblica = true,
				DataProtocollo = null,
				IdComune = "E256",
				DataProtocolloIstanza = null
			};

			_classeTestWebServiceMovimenti = cls;

			return _classeTestWebServiceMovimenti;
			/*
			var xmlClasseDiTest = @"<?xml version=""1.0"" encoding=""UTF-8""?><DatiMovimento xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"">
									  <CodiceMovimento xmlns=""http://init.sigepro.it"">666</CodiceMovimento>
									  <DescInventario xmlns=""http://init.sigepro.it"" />
									  <Amministrazione xmlns=""http://init.sigepro.it"">UFFICIO DI COMPETENZA</Amministrazione>
									  <Esito xmlns=""http://init.sigepro.it"">Positivo</Esito>
									  <Allegati xmlns=""http://init.sigepro.it"">
										<IDCOMUNE xmlns=""http://init.sigepro.it"">E256</IDCOMUNE>
										<Id xmlns=""http://init.sigepro.it"">3125</Id>
										<IDALLEGATO xmlns=""http://init.sigepro.it"">1</IDALLEGATO>
										<CODICEMOVIMENTO xmlns=""http://init.sigepro.it"">50097</CODICEMOVIMENTO>
										<DESCRIZIONE xmlns=""http://init.sigepro.it"">Pubblicità nuova istanza</DESCRIZIONE>
										<NOTE xmlns=""http://init.sigepro.it"">File generato automaticamente</NOTE>
										<CODICEOGGETTO xmlns=""http://init.sigepro.it"">360006</CODICEOGGETTO>
										<DATAREGISTRAZIONE xmlns=""http://init.sigepro.it"">2012-10-24T00:00:00</DATAREGISTRAZIONE>
										<FlagPubblica xmlns=""http://init.sigepro.it"">1</FlagPubblica>
									  </Allegati>
									  <Descrizione xmlns=""http://init.sigepro.it"">Presentazione domanda</Descrizione>
									  <DataMovimento xmlns=""http://init.sigepro.it"">2012-10-01T00:00:00</DataMovimento>
									  <CodiceIstanza xmlns=""http://init.sigepro.it"">4820</CodiceIstanza>
									  <NumeroIstanza xmlns=""http://init.sigepro.it"">462</NumeroIstanza>
									  <VisualizzaParere xmlns=""http://init.sigepro.it"">false</VisualizzaParere>
									  <VisualizzaEsito xmlns=""http://init.sigepro.it"">true</VisualizzaEsito>
									  <Pubblica xmlns=""http://init.sigepro.it"">true</Pubblica>
									  <DataProtocollo xmlns=""http://init.sigepro.it"" xsi:nil=""true"" />
									  <IdComune xmlns=""http://init.sigepro.it"">E256</IdComune>
									  <DataProtocolloIstanza xsi:nil=""true"" xmlns=""http://init.sigepro.it"" />
									</DatiMovimento>";



			using (var reader = new StringReader(xmlClasseDiTest))
			{
				var xs = new XmlSerializer(typeof(DatiMovimentoDaEffettuareDto));
				_classeTestWebServiceMovimenti = (DatiMovimentoDaEffettuareDto)xs.Deserialize(reader);
			}

			return _classeTestWebServiceMovimenti;
			*/
		}
	}
}
