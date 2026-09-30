namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests
{
    //    [TestClass]
    //    public class NuovoVsVecchioTests
    //    {
    //        private DomandaOnline _domanda;


    //        [TestInitialize]
    //        public void Init()
    //        {
    //            PresentazioneIstanzaDbV2 db = new PresentazioneIstanzaDbV2();
    //            db.ReadXml(new MemoryStream(Encoding.UTF8.GetBytes(XML_DOMANDA)));

    //            this._domanda = new DomandaOnline(PresentazioneIstanzaDataKey.New("E256", "SS", "123", 123), db, false);

    //            FoKernelContainer.Kernel = new StandardKernel();
    //            FoKernelContainer.Kernel.Bind<IAliasToIdComuneTranslator>().To<AliasToIdComuneTranslatorFake>();
    //            FoKernelContainer.Kernel.Bind<IInterventiRepository>().To<InterventiRepositoryFake>();
    //            FoKernelContainer.Kernel.Bind<IComuniService>().To<ComuniServiceFake>();
    //            FoKernelContainer.Kernel.Bind<IConfigurazioneAreaRiservataRepository>().To<ConfigurazioneAreaRiservataRepositoryFake>();
    //            FoKernelContainer.Kernel.Bind<IStatiIstanzaRepository>().To<StatiIstanzaRepositoryFake>();
    //            FoKernelContainer.Kernel.Bind<ICittadinanzeService>().To<CittadinanzeServiceFake>();
    //            FoKernelContainer.Kernel.Bind<IFormeGiuridicheRepository>().To<FormeGiuridicheRepositoryFake>();
    //            FoKernelContainer.Kernel.Bind<IConfigurazioneVbgRepository>().To<ConfigurazioneVbgRepositoryFake>();
    //            FoKernelContainer.Kernel.Bind<IStradarioRepository>().To<StradarioRepositoryFake>();
    //            FoKernelContainer.Kernel.Bind<IConfigurazione<ParametriAllegati>>().To<ConfigurazioneParametriAllegatiFake>();
    //            FoKernelContainer.Kernel.Bind<IUserCredentialsStorage>().To<UserCredentialsStorageFake>();
    //            FoKernelContainer.Kernel.Bind<ILogicaRisoluzioneTecnico>().To<LogicaRisoluzioneTecnico>();
    //            FoKernelContainer.Kernel.Bind<IDateTimeServiceWrapper>().ToMethod((ctx) => new StaticDateTimeServiceWrapper(new DateTime(2021, 09, 11)));
    //            FoKernelContainer.Kernel.Bind<IstanzaSigeproAdapterServiceFactory>().ToSelf();
    //            FoKernelContainer.Kernel.Bind<IIstanzaSigeproAdapterService>().ToMethod(x => FoKernelContainer.GetService<IstanzaSigeproAdapterServiceFactory>().Create());
    //        }

    //        [TestCleanup]
    //        public void Cleanup()
    //        {
    //            FoKernelContainer.Kernel = null;
    //        }

    //        [TestMethod]
    //        public void Caricamento_domanda()
    //        {
    //            Assert.IsNotNull(this._domanda);
    //        }

    //        //[TestMethod]
    //        //public void I_due_metodi_di_conversione_danno_lo_stesso_risultato()
    //        //{
    //        //    var old = new IstanzaSigeproAdapter(this._domanda.ReadInterface);

    //        //    var istanzaBoOld = old.ToIstanzaBackoffice().ToXmlModelloRiepilogo();

    //        //    var nuovo = FoKernelContainer.GetService<IIstanzaSigeproAdapterService>();

    //        //    var istanzaBoNew = nuovo.ToIstanzaBackoffice(this._domanda.ReadInterface).ToXmlModelloRiepilogo();

    //        //    Assert.IsTrue(istanzaBoNew == istanzaBoOld);
    //        //}

    //        #region XML DOMANDA ONLINE

    //        private const string XML_DOMANDA = @"<PresentazioneIstanzaDbV2 xmlns=""http://tempuri.org/PresentazioneIstanzaDbV2.xsd"">
    //  <ANAGRAFE>
    //    <NOMINATIVO>gargagli</NOMINATIVO>
    //    <INDIRIZZO>via aaaaaaaa</INDIRIZZO>
    //    <CITTA>localita</CITTA>
    //    <CAP>06100</CAP>
    //    <PROVINCIA>PG</PROVINCIA>
    //    <TELEFONO>telefono</TELEFONO>
    //    <TELEFONOCELLULARE>cellulare</TELEFONOCELLULARE>
    //    <FAX>fax</FAX>
    //    <CODICEFISCALE>GRGNCL79C19G478O</CODICEFISCALE>
    //    <NOTE />
    //    <EMAIL>gianpaolo.todini@pa-group.com</EMAIL>
    //    <CODCOMNASCITA>G478</CODCOMNASCITA>
    //    <DATANASCITA>1979-03-19T00:00:00+01:00</DATANASCITA>
    //    <SESSO>M</SESSO>
    //    <NOME>nicola</NOME>
    //    <TITOLO>4</TITOLO>
    //    <TIPOANAGRAFE>F</TIPOANAGRAFE>
    //    <CODICECITTADINANZA>8574</CODICECITTADINANZA>
    //    <COMUNERESIDENZA>G478</COMUNERESIDENZA>
    //    <INDIRIZZOCORRISPONDENZA>via aaaaaaaa</INDIRIZZOCORRISPONDENZA>
    //    <CITTACORRISPONDENZA>localita</CITTACORRISPONDENZA>
    //    <CAPCORRISPONDENZA>06100</CAPCORRISPONDENZA>
    //    <PROVINCIACORRISPONDENZA>PG</PROVINCIACORRISPONDENZA>
    //    <COMUNECORRISPONDENZA>G478</COMUNECORRISPONDENZA>
    //    <TIPOSOGGETTO>66</TIPOSOGGETTO>
    //    <PROVINCIANASCITA>PG</PROVINCIANASCITA>
    //    <ANAGRAFE_PK>0</ANAGRAFE_PK>
    //    <DescrizioneTipoSoggetto />
    //    <DescrSoggetto>Richiedente</DescrSoggetto>
    //    <Pec />
    //    <FlagTipoSoggetto>R</FlagTipoSoggetto>
    //    <FlagRichiedeAnagraficaCollegata>false</FlagRichiedeAnagraficaCollegata>
    //    <PartitaIva />
    //    <IsCittadinoExtracomunitario>false</IsCittadinoExtracomunitario>
    //    <MatricolaInps />
    //    <CodSedeIscrizioneInps />
    //    <DesSedeIscrizioneInps />
    //    <MatricolaInail />
    //    <CodSedeIscrizioneInail />
    //    <DesSedeIscrizioneInail />
    //  </ANAGRAFE>
    //  <ANAGRAFE>
    //    <NOMINATIVO>gargagli</NOMINATIVO>
    //    <INDIRIZZO>via aaaaaaaa</INDIRIZZO>
    //    <CITTA>localita</CITTA>
    //    <CAP>06100</CAP>
    //    <PROVINCIA>PG</PROVINCIA>
    //    <TELEFONO>telefono</TELEFONO>
    //    <TELEFONOCELLULARE>cellulare</TELEFONOCELLULARE>
    //    <FAX>fax</FAX>
    //    <CODICEFISCALE>GRGNCL79C19G478O</CODICEFISCALE>
    //    <NOTE />
    //    <EMAIL>gianpaolo.todini@pa-group.com</EMAIL>
    //    <CODCOMNASCITA>G478</CODCOMNASCITA>
    //    <DATANASCITA>1979-03-19T00:00:00+01:00</DATANASCITA>
    //    <SESSO>M</SESSO>
    //    <NOME>nicola</NOME>
    //    <TITOLO>4</TITOLO>
    //    <TIPOANAGRAFE>F</TIPOANAGRAFE>
    //    <CODICECITTADINANZA>8574</CODICECITTADINANZA>
    //    <COMUNERESIDENZA>G478</COMUNERESIDENZA>
    //    <INDIRIZZOCORRISPONDENZA>via aaaaaaaa</INDIRIZZOCORRISPONDENZA>
    //    <CITTACORRISPONDENZA>localita</CITTACORRISPONDENZA>
    //    <CAPCORRISPONDENZA>06100</CAPCORRISPONDENZA>
    //    <PROVINCIACORRISPONDENZA>PG</PROVINCIACORRISPONDENZA>
    //    <COMUNECORRISPONDENZA>G478</COMUNECORRISPONDENZA>
    //    <TIPOSOGGETTO>6</TIPOSOGGETTO>
    //    <PROVINCIANASCITA>PG</PROVINCIANASCITA>
    //    <ANAGRAFE_PK>1</ANAGRAFE_PK>
    //    <IdAnagraficaCollegata>3</IdAnagraficaCollegata>
    //    <DescrizioneTipoSoggetto />
    //    <DescrSoggetto>Legale rappresentante della Societa'</DescrSoggetto>
    //    <Pec />
    //    <FlagTipoSoggetto>R</FlagTipoSoggetto>
    //    <FlagRichiedeAnagraficaCollegata>true</FlagRichiedeAnagraficaCollegata>
    //    <PartitaIva />
    //    <IsCittadinoExtracomunitario>false</IsCittadinoExtracomunitario>
    //    <MatricolaInps />
    //    <CodSedeIscrizioneInps />
    //    <DesSedeIscrizioneInps />
    //    <MatricolaInail />
    //    <CodSedeIscrizioneInail />
    //    <DesSedeIscrizioneInail />
    //  </ANAGRAFE>
    //  <ANAGRAFE>
    //    <NOMINATIVO>asdasd</NOMINATIVO>
    //    <FORMAGIURIDICA>15</FORMAGIURIDICA>
    //    <INDIRIZZO />
    //    <CITTA />
    //    <CAP />
    //    <PROVINCIA />
    //    <TELEFONO />
    //    <TELEFONOCELLULARE />
    //    <FAX />
    //    <CODICEFISCALE>12345678902</CODICEFISCALE>
    //    <NOTE />
    //    <EMAIL />
    //    <REGDITTE />
    //    <REGTRIB />
    //    <CODCOMREGDITTE />
    //    <CODCOMREGTRIB />
    //    <CODCOMNASCITA />
    //    <DATAREGDITTE>0001-01-01T00:00:00+01:00</DATAREGDITTE>
    //    <DATAREGTRIB>0001-01-01T00:00:00+01:00</DATAREGTRIB>
    //    <SESSO>M</SESSO>
    //    <NOME />
    //    <TIPOANAGRAFE>G</TIPOANAGRAFE>
    //    <DATANOMINATIVO>0001-01-01T00:00:00+01:00</DATANOMINATIVO>
    //    <COMUNERESIDENZA />
    //    <INDIRIZZOCORRISPONDENZA />
    //    <CITTACORRISPONDENZA />
    //    <CAPCORRISPONDENZA />
    //    <PROVINCIACORRISPONDENZA />
    //    <COMUNECORRISPONDENZA />
    //    <PROVINCIAREA />
    //    <NUMISCRREA />
    //    <DATAISCRREA>0001-01-01T00:00:00+01:00</DATAISCRREA>
    //    <TIPOSOGGETTO>62</TIPOSOGGETTO>
    //    <PROVINCIANASCITA />
    //    <ANAGRAFE_PK>3</ANAGRAFE_PK>
    //    <DescrizioneTipoSoggetto />
    //    <DescrSoggetto>Azienda</DescrSoggetto>
    //    <Pec />
    //    <FlagTipoSoggetto>A</FlagTipoSoggetto>
    //    <FlagRichiedeAnagraficaCollegata>false</FlagRichiedeAnagraficaCollegata>
    //    <PartitaIva />
    //    <IsCittadinoExtracomunitario>false</IsCittadinoExtracomunitario>
    //    <MatricolaInps />
    //    <CodSedeIscrizioneInps />
    //    <DesSedeIscrizioneInps />
    //    <MatricolaInail />
    //    <CodSedeIscrizioneInail />
    //    <DesSedeIscrizioneInail />
    //  </ANAGRAFE>
    //  <ISTANZE>
    //    <OGGETTO>Sportello unico
    //Attività e interventi
    //COMMERCIO ALL'INGROSSO E AL DETTAGLIO
    //COMMERCIO AL DETTAGLIO
    //COMMERCIO AL DETTAGLIO AMBULANTE
    //Commercio al dettaglio su aree pubbliche in forma itinerante
    //Avvio</OGGETTO>
    //    <NOTE />
    //    <CODICEINTERVENTO>72753</CODICEINTERVENTO>
    //    <DENOMINAZIONE_ATTIVITA />
    //    <CODICECOMUNE>E256</CODICECOMUNE>
    //    <FlgPrivacy>true</FlgPrivacy>
    //    <DescrizioneEstesaIntervento>Sportello unico
    //Attività e interventi
    //COMMERCIO ALL'INGROSSO E AL DETTAGLIO
    //COMMERCIO AL DETTAGLIO
    //COMMERCIO AL DETTAGLIO AMBULANTE
    //Commercio al dettaglio su aree pubbliche in forma itinerante
    //Avvio</DescrizioneEstesaIntervento>
    //    <IndirizzoDomicilioEletronico>aaa@aaa.com</IndirizzoDomicilioEletronico>
    //    <NaturaBase>ordinario</NaturaBase>
    //  </ISTANZE>
    //  <ISTANZESTRADARIO>
    //    <ID>2</ID>
    //    <CODICESTRADARIO>0</CODICESTRADARIO>
    //    <CIVICO />
    //    <COLORE />
    //    <NOTE />
    //    <STRADARIO>Piazza PIAZZA ALBERICO IL GRANDE</STRADARIO>
    //    <CODICECOMUNE>E256</CODICECOMUNE>
    //    <Esponente />
    //    <Interno />
    //    <Scala />
    //    <EsponenteInterno />
    //    <Piano />
    //    <Fabbricato />
    //    <Km />
    //    <Circoscrizione />
    //    <Cap />
    //    <Uuid>029b932d-f7c1-431a-a177-9ca97b58d8c2</Uuid>
    //    <CodViario />
    //    <CodCivico />
    //  </ISTANZESTRADARIO>
    //  <ISTANZESTRADARIO>
    //    <ID>3</ID>
    //    <CODICESTRADARIO>0</CODICESTRADARIO>
    //    <CIVICO />
    //    <COLORE />
    //    <NOTE />
    //    <STRADARIO>Piazza PIAZZA ALBERICO IL GRANDE</STRADARIO>
    //    <CODICECOMUNE>E256</CODICECOMUNE>
    //    <Esponente />
    //    <Interno />
    //    <Scala />
    //    <EsponenteInterno />
    //    <Piano />
    //    <Fabbricato />
    //    <Km />
    //    <Circoscrizione />
    //    <Cap />
    //    <Uuid>8ec8c440-f778-41dd-91eb-25885d3e61fc</Uuid>
    //    <CodViario />
    //    <CodCivico />
    //  </ISTANZESTRADARIO>
    //  <ISTANZESTRADARIO>
    //    <ID>4</ID>
    //    <CODICESTRADARIO>0</CODICESTRADARIO>
    //    <CIVICO />
    //    <COLORE />
    //    <NOTE />
    //    <STRADARIO>Piazza PIAZZA ALBERICO IL GRANDE</STRADARIO>
    //    <CODICECOMUNE>E256</CODICECOMUNE>
    //    <Esponente />
    //    <Interno />
    //    <Scala />
    //    <EsponenteInterno />
    //    <Piano />
    //    <Fabbricato />
    //    <Km />
    //    <Circoscrizione />
    //    <Cap />
    //    <Uuid>4790169a-dbcd-4242-bf7e-1958735b342a</Uuid>
    //    <CodViario />
    //    <CodCivico />
    //  </ISTANZESTRADARIO>
    //  <ISTANZESTRADARIO>
    //    <ID>5</ID>
    //    <CODICESTRADARIO>0</CODICESTRADARIO>
    //    <CIVICO />
    //    <COLORE />
    //    <NOTE />
    //    <STRADARIO>Piazza PIAZZA ALBERICO IL GRANDE</STRADARIO>
    //    <CODICECOMUNE>E256</CODICECOMUNE>
    //    <Esponente />
    //    <Interno />
    //    <Scala />
    //    <EsponenteInterno />
    //    <Piano />
    //    <Fabbricato />
    //    <Km />
    //    <Circoscrizione />
    //    <Cap />
    //    <Uuid>d1b12f55-62bf-48b8-9c3d-fa1a995bbee8</Uuid>
    //    <CodViario />
    //    <CodCivico />
    //  </ISTANZESTRADARIO>
    //  <ISTANZEPROCEDIMENTI>
    //    <CODICEINVENTARIO>20683</CODICEINVENTARIO>
    //    <DESCRIZIONE>IG-SAN Locali di pubblico spettacolo (discoteche, sale da ballo, cinema, teatri, sale di intrattenimento in genere)</DESCRIZIONE>
    //    <Principale>true</Principale>
    //    <CodiceNatura>2</CodiceNatura>
    //    <DescrizioneNatura>Comunicazione</DescrizioneNatura>
    //    <Presente>false</Presente>
    //    <BinarioDipendenze>2</BinarioDipendenze>
    //    <PermetteVerificaAcquisizione>false</PermetteVerificaAcquisizione>
    //    <EndoFacoltativo>false</EndoFacoltativo>
    //    <TipoTitoloObbligatorio>false</TipoTitoloObbligatorio>
    //    <Ordine>9999</Ordine>
    //    <OrdineFamiglia>0</OrdineFamiglia>
    //    <OrdineTipo>0</OrdineTipo>
    //  </ISTANZEPROCEDIMENTI>
    //  <ISTANZEPROCEDIMENTI>
    //    <CODICEINVENTARIO>20967</CODICEINVENTARIO>
    //    <DESCRIZIONE>Domanda Unica</DESCRIZIONE>
    //    <Principale>false</Principale>
    //    <CodiceNatura>1</CodiceNatura>
    //    <DescrizioneNatura>Ordinario</DescrizioneNatura>
    //    <Presente>false</Presente>
    //    <BinarioDipendenze>11</BinarioDipendenze>
    //    <PermetteVerificaAcquisizione>false</PermetteVerificaAcquisizione>
    //    <EndoFacoltativo>false</EndoFacoltativo>
    //    <TipoTitoloObbligatorio>false</TipoTitoloObbligatorio>
    //    <Ordine>0</Ordine>
    //    <OrdineFamiglia>0</OrdineFamiglia>
    //    <OrdineTipo>0</OrdineTipo>
    //  </ISTANZEPROCEDIMENTI>
    //  <ISTANZEPROCEDIMENTI>
    //    <CODICEINVENTARIO>20951</CODICEINVENTARIO>
    //    <DESCRIZIONE>ASL: semplificato</DESCRIZIONE>
    //    <Principale>false</Principale>
    //    <CodiceNatura>1</CodiceNatura>
    //    <DescrizioneNatura>Ordinario</DescrizioneNatura>
    //    <Presente>false</Presente>
    //    <BinarioDipendenze>11</BinarioDipendenze>
    //    <PermetteVerificaAcquisizione>false</PermetteVerificaAcquisizione>
    //    <EndoFacoltativo>false</EndoFacoltativo>
    //    <TipoTitoloObbligatorio>false</TipoTitoloObbligatorio>
    //    <Ordine>0</Ordine>
    //    <OrdineFamiglia>0</OrdineFamiglia>
    //    <OrdineTipo>0</OrdineTipo>
    //  </ISTANZEPROCEDIMENTI>
    //  <ISTANZEPROCEDIMENTI>
    //    <CODICEINVENTARIO>20591</CODICEINVENTARIO>
    //    <DESCRIZIONE>ASL 11.E - IG-SAN Notifica nuovi insediamenti produttivi</DESCRIZIONE>
    //    <Principale>false</Principale>
    //    <CodiceNatura>2</CodiceNatura>
    //    <DescrizioneNatura>Comunicazione</DescrizioneNatura>
    //    <Presente>false</Presente>
    //    <BinarioDipendenze>2</BinarioDipendenze>
    //    <PermetteVerificaAcquisizione>true</PermetteVerificaAcquisizione>
    //    <EndoFacoltativo>false</EndoFacoltativo>
    //    <TipoTitoloObbligatorio>false</TipoTitoloObbligatorio>
    //    <Ordine>11</Ordine>
    //    <OrdineFamiglia>0</OrdineFamiglia>
    //    <OrdineTipo>0</OrdineTipo>
    //  </ISTANZEPROCEDIMENTI>
    //  <ISTANZEPROCEDIMENTI>
    //    <CODICEINVENTARIO>20628</CODICEINVENTARIO>
    //    <DESCRIZIONE>ASL 45 - IG-SAN Acqua potabile. Acquedotti</DESCRIZIONE>
    //    <Principale>false</Principale>
    //    <CodiceNatura>2</CodiceNatura>
    //    <DescrizioneNatura>Comunicazione</DescrizioneNatura>
    //    <Presente>false</Presente>
    //    <BinarioDipendenze>2</BinarioDipendenze>
    //    <PermetteVerificaAcquisizione>false</PermetteVerificaAcquisizione>
    //    <EndoFacoltativo>false</EndoFacoltativo>
    //    <TipoTitoloObbligatorio>false</TipoTitoloObbligatorio>
    //    <Ordine>100</Ordine>
    //    <OrdineFamiglia>0</OrdineFamiglia>
    //    <OrdineTipo>0</OrdineTipo>
    //  </ISTANZEPROCEDIMENTI>
    //  <ISTANZEPROCEDIMENTI>
    //    <CODICEINVENTARIO>20676</CODICEINVENTARIO>
    //    <DESCRIZIONE>ASL 79 - IG-SAN Acconciatori</DESCRIZIONE>
    //    <Principale>false</Principale>
    //    <CodiceNatura>2</CodiceNatura>
    //    <DescrizioneNatura>Comunicazione</DescrizioneNatura>
    //    <Presente>false</Presente>
    //    <BinarioDipendenze>2</BinarioDipendenze>
    //    <PermetteVerificaAcquisizione>false</PermetteVerificaAcquisizione>
    //    <EndoFacoltativo>false</EndoFacoltativo>
    //    <TipoTitoloObbligatorio>false</TipoTitoloObbligatorio>
    //    <Ordine>96</Ordine>
    //    <OrdineFamiglia>0</OrdineFamiglia>
    //    <OrdineTipo>0</OrdineTipo>
    //  </ISTANZEPROCEDIMENTI>
    //  <OGGETTI>
    //    <ID>5</ID>
    //    <DESCRIZIONE>Allegato della procedura</DESCRIZIONE>
    //    <CODICEDOCUMENTO>-21</CODICEDOCUMENTO>
    //    <IDMODELLO>359722</IDMODELLO>
    //    <TIPODOCUMENTO>I</TIPODOCUMENTO>
    //    <CodiceEndoOIntervento>72753</CodiceEndoOIntervento>
    //    <RICHIESTO>true</RICHIESTO>
    //    <RIEPILOGODOMANDA>0</RIEPILOGODOMANDA>
    //    <ORDINE>0</ORDINE>
    //    <CodiceCategoria>-1</CodiceCategoria>
    //    <Categoria>Altri allegati</Categoria>
    //    <RichiedeFirma>true</RichiedeFirma>
    //    <TipoDownload>pdf,doc,odt,rtf,</TipoDownload>
    //    <LinkInformazioni />
    //    <NomeFileModello>allegatoProcedura.DOCX</NomeFileModello>
    //    <IdAllegato>27</IdAllegato>
    //    <Note>&lt;p&gt;&lt;strong&gt;Note dell'allegato della procedura&lt;/strong&gt;&lt;/p&gt;
    //&lt;ol&gt;
    //&lt;li&gt;uno&lt;/li&gt;
    //&lt;li&gt;due&lt;/li&gt;
    //&lt;li&gt;tre&lt;/li&gt;
    //&lt;/ol&gt;
    //&lt;p&gt;asdasdasd&lt;/p&gt;</Note>
    //    <FromDatiDinamici>0</FromDatiDinamici>
    //    <AllegatoLibero>false</AllegatoLibero>
    //    <DimensioneMassima>0</DimensioneMassima>
    //  </OGGETTI>
    //  <OGGETTI>
    //    <ID>6</ID>
    //    <DESCRIZIONE>Modulo obbligatorio</DESCRIZIONE>
    //    <CODICEDOCUMENTO>252</CODICEDOCUMENTO>
    //    <IDMODELLO>353215</IDMODELLO>
    //    <TIPODOCUMENTO>I</TIPODOCUMENTO>
    //    <CodiceEndoOIntervento>72753</CodiceEndoOIntervento>
    //    <RICHIESTO>true</RICHIESTO>
    //    <RIEPILOGODOMANDA>0</RIEPILOGODOMANDA>
    //    <ORDINE>1420</ORDINE>
    //    <CodiceCategoria>-1</CodiceCategoria>
    //    <Categoria>Altri allegati</Categoria>
    //    <RichiedeFirma>true</RichiedeFirma>
    //    <TipoDownload>pdf,doc,odt,rtf,</TipoDownload>
    //    <LinkInformazioni />
    //    <NomeFileModello>353215-Bollettino.rtf</NomeFileModello>
    //    <IdAllegato>28</IdAllegato>
    //    <Note>&lt;p&gt;&lt;strong&gt;Note dell'allegato obbligatorio&lt;/strong&gt;&lt;/p&gt;
    //&lt;p&gt;queste sono le note dell'allegato obbligatorio&lt;strong&gt;&lt;br /&gt;&lt;/strong&gt;&lt;/p&gt;</Note>
    //    <FromDatiDinamici>0</FromDatiDinamici>
    //    <AllegatoLibero>false</AllegatoLibero>
    //    <DimensioneMassima>10000</DimensioneMassima>
    //    <EstensioniAmmesse>pdf,p7m</EstensioniAmmesse>
    //  </OGGETTI>
    //  <OGGETTI>
    //    <ID>7</ID>
    //    <DESCRIZIONE>Modulo di domanda</DESCRIZIONE>
    //    <CODICEDOCUMENTO>292</CODICEDOCUMENTO>
    //    <IDMODELLO>356097</IDMODELLO>
    //    <TIPODOCUMENTO>I</TIPODOCUMENTO>
    //    <CodiceEndoOIntervento>72753</CodiceEndoOIntervento>
    //    <RICHIESTO>true</RICHIESTO>
    //    <RIEPILOGODOMANDA>1</RIEPILOGODOMANDA>
    //    <ORDINE>1690</ORDINE>
    //    <CodiceCategoria>-1</CodiceCategoria>
    //    <Categoria>Altri allegati</Categoria>
    //    <RichiedeFirma>true</RichiedeFirma>
    //    <LinkInformazioni />
    //    <NomeFileModello>riepilogodomandaUmbria.20120210.html</NomeFileModello>
    //    <FromDatiDinamici>0</FromDatiDinamici>
    //    <AllegatoLibero>false</AllegatoLibero>
    //    <DimensioneMassima>0</DimensioneMassima>
    //  </OGGETTI>
    //  <OGGETTI>
    //    <ID>8</ID>
    //    <DESCRIZIONE>delega</DESCRIZIONE>
    //    <CODICEDOCUMENTO>281</CODICEDOCUMENTO>
    //    <IDMODELLO>619</IDMODELLO>
    //    <TIPODOCUMENTO>I</TIPODOCUMENTO>
    //    <CodiceEndoOIntervento>72753</CodiceEndoOIntervento>
    //    <RICHIESTO>false</RICHIESTO>
    //    <RIEPILOGODOMANDA>0</RIEPILOGODOMANDA>
    //    <ORDINE>1620</ORDINE>
    //    <CodiceCategoria>-1</CodiceCategoria>
    //    <Categoria>Altri allegati</Categoria>
    //    <RichiedeFirma>false</RichiedeFirma>
    //    <LinkInformazioni />
    //    <NomeFileModello>619-___RiepilogoDomanda.pdf.p7m</NomeFileModello>
    //    <Note>&lt;html /&gt;</Note>
    //    <FromDatiDinamici>0</FromDatiDinamici>
    //    <AllegatoLibero>false</AllegatoLibero>
    //    <DimensioneMassima>0</DimensioneMassima>
    //  </OGGETTI>
    //  <OGGETTI>
    //    <ID>9</ID>
    //    <DESCRIZIONE>test</DESCRIZIONE>
    //    <CODICEDOCUMENTO>306</CODICEDOCUMENTO>
    //    <IDMODELLO>356829</IDMODELLO>
    //    <TIPODOCUMENTO>I</TIPODOCUMENTO>
    //    <CodiceEndoOIntervento>72753</CodiceEndoOIntervento>
    //    <RICHIESTO>false</RICHIESTO>
    //    <RIEPILOGODOMANDA>0</RIEPILOGODOMANDA>
    //    <ORDINE>1689</ORDINE>
    //    <CodiceCategoria>-1</CodiceCategoria>
    //    <Categoria>Altri allegati</Categoria>
    //    <RichiedeFirma>false</RichiedeFirma>
    //    <TipoDownload>pdfc,</TipoDownload>
    //    <LinkInformazioni />
    //    <NomeFileModello>CertificatoDiInvio.pdf</NomeFileModello>
    //    <Note>&lt;p&gt;&lt;strong&gt;Prova note html&lt;/strong&gt;&lt;/p&gt;
    //&lt;p&gt;Note pubbliche del documento&lt;strong&gt;&lt;br /&gt;&lt;/strong&gt;&lt;/p&gt;</Note>
    //    <FromDatiDinamici>0</FromDatiDinamici>
    //    <AllegatoLibero>false</AllegatoLibero>
    //    <DimensioneMassima>0</DimensioneMassima>
    //  </OGGETTI>
    //  <DATICATASTALI>
    //    <Id>1</Id>
    //    <CodiceTipoCatasto>T</CodiceTipoCatasto>
    //    <TipoCatasto>Terreni</TipoCatasto>
    //    <Foglio>245</Foglio>
    //    <Particella>198</Particella>
    //    <Sub />
    //    <IdLocalizzazione>2</IdLocalizzazione>
    //    <Sezione />
    //  </DATICATASTALI>
    //  <DATICATASTALI>
    //    <Id>2</Id>
    //    <CodiceTipoCatasto>T</CodiceTipoCatasto>
    //    <TipoCatasto>Terreni</TipoCatasto>
    //    <Foglio>245</Foglio>
    //    <Particella>206</Particella>
    //    <Sub />
    //    <IdLocalizzazione>3</IdLocalizzazione>
    //    <Sezione />
    //  </DATICATASTALI>
    //  <DATICATASTALI>
    //    <Id>3</Id>
    //    <CodiceTipoCatasto>T</CodiceTipoCatasto>
    //    <TipoCatasto>Terreni</TipoCatasto>
    //    <Foglio>245</Foglio>
    //    <Particella>208</Particella>
    //    <Sub />
    //    <IdLocalizzazione>4</IdLocalizzazione>
    //    <Sezione />
    //  </DATICATASTALI>
    //  <DATICATASTALI>
    //    <Id>4</Id>
    //    <CodiceTipoCatasto>T</CodiceTipoCatasto>
    //    <TipoCatasto>Terreni</TipoCatasto>
    //    <Foglio>245</Foglio>
    //    <Particella>210</Particella>
    //    <Sub />
    //    <IdLocalizzazione>5</IdLocalizzazione>
    //    <Sezione />
    //  </DATICATASTALI>
    //  <Dyn2Modelli>
    //    <IdModello>433</IdModello>
    //    <NomeScheda>Test oneri</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>0</TipoFirma>
    //    <MaxMolteplicita>0</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>408</IdModello>
    //    <NomeScheda>Test scheda per scce</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>0</TipoFirma>
    //    <MaxMolteplicita>0</MaxMolteplicita>
    //    <Facoltativo>true</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>400</IdModello>
    //    <NomeScheda>Test stradario</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>0</TipoFirma>
    //    <MaxMolteplicita>3</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>82</IdModello>
    //    <NomeScheda>Dichiarazioni statiche people</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>1</TipoFirma>
    //    <MaxMolteplicita>0</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>157</IdModello>
    //    <NomeScheda>MODELLO DOCUMENTO</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>0</TipoFirma>
    //    <MaxMolteplicita>0</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>121</IdModello>
    //    <NomeScheda>Domicilio Elettronico</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>0</TipoFirma>
    //    <MaxMolteplicita>0</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>141</IdModello>
    //    <NomeScheda>Dettaglio esercizio</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>0</TipoFirma>
    //    <MaxMolteplicita>0</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Modelli>
    //    <IdModello>91000529</IdModello>
    //    <NomeScheda>AUA - 5. ISTANZA</NomeScheda>
    //    <Compilato>true</Compilato>
    //    <TipoFirma>1</TipoFirma>
    //    <MaxMolteplicita>5</MaxMolteplicita>
    //    <Facoltativo>false</Facoltativo>
    //  </Dyn2Modelli>
    //  <Dyn2Dati>
    //    <IdCampo>3419</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>10</Valore>
    //    <ValoreDecodificato>10</ValoreDecodificato>
    //    <NomeCampo>TOTALE_ONERE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>520</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>Da Hand Made a MetanoDa innesto S.G.C. FI-PI-LI Porto a Terminal Darsena Toscana, lungo la via di Mogadiscio e vice versaDa varco Donegani fino alla Calata Sgarallino e vice versaDa varco Valessini a zone operative, lungo le vie Pisa e Michelangelo e vice versa o in uscita dal varco ZaraDa via L. Da Vinci - varco Bengasi a zone operative lungo le strade adiacenti le Calate Addis Abeba, Assab, Gondar, Tripoli, Lucca e vice versaDa via L. Da Vinci - via Galvani - varco Galvani a zone operative lungo le strade adiacenti le Calate Addis Abeba, Assab, Gondar, Tripoli, Lucca e vice versaDa via L. Da Vinci a Terminal Sintermar e vice versaDa Via Nino Bixio a Via ManzoniDa viabilità interno porto attraverso Terminal CILP fino a Terminal Livorno est per Darsena Toscana</Valore>
    //    <ValoreDecodificato>Da Hand Made a MetanoDa innesto S.G.C. FI-PI-LI Porto a Terminal Darsena Toscana, lungo la via di Mogadiscio e vice versaDa varco Donegani fino alla Calata Sgarallino e vice versaDa varco Valessini a zone operative, lungo le vie Pisa e Michelangelo e vice versa o in uscita dal varco ZaraDa via L. Da Vinci - varco Bengasi a zone operative lungo le strade adiacenti le Calate Addis Abeba, Assab, Gondar, Tripoli, Lucca e vice versaDa via L. Da Vinci - via Galvani - varco Galvani a zone operative lungo le strade adiacenti le Calate Addis Abeba, Assab, Gondar, Tripoli, Lucca e vice versaDa via L. Da Vinci a Terminal Sintermar e vice versaDa Via Nino Bixio a Via ManzoniDa viabilità interno porto attraverso Terminal CILP fino a Terminal Livorno est per Darsena Toscana</ValoreDecodificato>
    //    <NomeCampo>TXT_OUTPUT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3310</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>029b932d-f7c1-431a-a177-9ca97b58d8c2</Valore>
    //    <ValoreDecodificato>029b932d-f7c1-431a-a177-9ca97b58d8c2</ValoreDecodificato>
    //    <NomeCampo>ID_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3310</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>1</IndiceMolteplicita>
    //    <Valore>8ec8c440-f778-41dd-91eb-25885d3e61fc</Valore>
    //    <ValoreDecodificato>8ec8c440-f778-41dd-91eb-25885d3e61fc</ValoreDecodificato>
    //    <NomeCampo>ID_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3310</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>2</IndiceMolteplicita>
    //    <Valore>4790169a-dbcd-4242-bf7e-1958735b342a</Valore>
    //    <ValoreDecodificato>4790169a-dbcd-4242-bf7e-1958735b342a</ValoreDecodificato>
    //    <NomeCampo>ID_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3310</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>3</IndiceMolteplicita>
    //    <Valore>d1b12f55-62bf-48b8-9c3d-fa1a995bbee8</Valore>
    //    <ValoreDecodificato>d1b12f55-62bf-48b8-9c3d-fa1a995bbee8</ValoreDecodificato>
    //    <NomeCampo>ID_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3311</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:198)</Valore>
    //    <ValoreDecodificato>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:198)</ValoreDecodificato>
    //    <NomeCampo>DENOMINAZIONE_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3311</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>1</IndiceMolteplicita>
    //    <Valore>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:206)</Valore>
    //    <ValoreDecodificato>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:206)</ValoreDecodificato>
    //    <NomeCampo>DENOMINAZIONE_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3311</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>2</IndiceMolteplicita>
    //    <Valore>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:208)</Valore>
    //    <ValoreDecodificato>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:208)</ValoreDecodificato>
    //    <NomeCampo>DENOMINAZIONE_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>3311</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>3</IndiceMolteplicita>
    //    <Valore>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:210)</Valore>
    //    <ValoreDecodificato>Piazza Piazza PIAZZA ALBERICO IL GRANDE  (Catasto Terreni, F: 245, P:210)</ValoreDecodificato>
    //    <NomeCampo>DENOMINAZIONE_LOCALIZZAZIONE</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>645</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>aaa@aaa.com</Valore>
    //    <ValoreDecodificato>aaa@aaa.com</ValoreDecodificato>
    //    <NomeCampo>DOM-EL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>695</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>10</Valore>
    //    <ValoreDecodificato>10</ValoreDecodificato>
    //    <NomeCampo>MQ_ALIM</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>696</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>10</Valore>
    //    <ValoreDecodificato>10</ValoreDecodificato>
    //    <NomeCampo>MQ_NON_ALIM</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014321</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_RIL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014322</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>0</Valore>
    //    <ValoreDecodificato>0</ValoreDecodificato>
    //    <NomeCampo>AUA_5_MOD</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014325</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>0</Valore>
    //    <ValoreDecodificato>0</ValoreDecodificato>
    //    <NomeCampo>AUA_5_RIN</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014328</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>Domanda Unica</Valore>
    //    <ValoreDecodificato>Domanda Unica</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014328</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>1</IndiceMolteplicita>
    //    <Valore>ASL: semplificato</Valore>
    //    <ValoreDecodificato>ASL: semplificato</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014328</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>2</IndiceMolteplicita>
    //    <Valore>ASL 11.E - IG-SAN Notifica nuovi insediamenti produttivi</Valore>
    //    <ValoreDecodificato>ASL 11.E - IG-SAN Notifica nuovi insediamenti produttivi</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014328</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>3</IndiceMolteplicita>
    //    <Valore>ASL 79 - IG-SAN Acconciatori</Valore>
    //    <ValoreDecodificato>ASL 79 - IG-SAN Acconciatori</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014328</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>4</IndiceMolteplicita>
    //    <Valore>ASL 45 - IG-SAN Acqua potabile. Acquedotti</Valore>
    //    <ValoreDecodificato>ASL 45 - IG-SAN Acqua potabile. Acquedotti</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014328</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>5</IndiceMolteplicita>
    //    <Valore>IG-SAN Locali di pubblico spettacolo (discoteche, sale da ballo, cinema, teatri, sale di intrattenimento in genere)</Valore>
    //    <ValoreDecodificato>IG-SAN Locali di pubblico spettacolo (discoteche, sale da ballo, cinema, teatri, sale di intrattenimento in genere)</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIT</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014329</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>rinnovo</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIPOL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014329</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>1</IndiceMolteplicita>
    //    <Valore>2</Valore>
    //    <ValoreDecodificato>nuova</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIPOL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014329</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>2</IndiceMolteplicita>
    //    <Valore>4</Valore>
    //    <ValoreDecodificato>proseguimento senza modifiche</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIPOL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014329</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>3</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>rinnovo</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIPOL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014329</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>4</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>rinnovo</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIPOL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014329</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>5</IndiceMolteplicita>
    //    <Valore>2</Valore>
    //    <ValoreDecodificato>nuova</ValoreDecodificato>
    //    <NomeCampo>AUA_5_TIPOL</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014761</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_COMP</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014761</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>1</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_COMP</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014761</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>3</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_COMP</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014761</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>4</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_COMP</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014761</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>5</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_COMP</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014746</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>0</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_DICH</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014746</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>2</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_DICH</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014746</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>3</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_DICH</NomeCampo>
    //  </Dyn2Dati>
    //  <Dyn2Dati>
    //    <IdCampo>91014746</IdCampo>
    //    <IndiceScheda>0</IndiceScheda>
    //    <IndiceMolteplicita>4</IndiceMolteplicita>
    //    <Valore>1</Valore>
    //    <ValoreDecodificato>1</ValoreDecodificato>
    //    <NomeCampo>AUA_5_DICH</NomeCampo>
    //  </Dyn2Dati>
    //  <Procure>
    //    <CodiceAnagrafe>GRGNCL79C19G478O</CodiceAnagrafe>
    //    <CodiceProcuratore />
    //  </Procure>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>433</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>Test oneri </Descrizione>
    //    <IdAllegato>6</IdAllegato>
    //    <HashConfronto>166DBBD80FFD2587E82758792DB7F801</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>408</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>Test scheda per scce </Descrizione>
    //    <IdAllegato>7</IdAllegato>
    //    <HashConfronto>C70F9502318FC167EDE9A66A686C9E09</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>400</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>Test stradario </Descrizione>
    //    <IdAllegato>8</IdAllegato>
    //    <HashConfronto>B99733FB0E23169E26D3D70CFACCB04C</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>157</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>MODELLO DOCUMENTO </Descrizione>
    //    <IdAllegato>9</IdAllegato>
    //    <HashConfronto>FD1BE4CDA2C2F25CA62E39616ED1FCA4</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>121</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>Domicilio Elettronico </Descrizione>
    //    <IdAllegato>10</IdAllegato>
    //    <HashConfronto>B361082AE7A38FBBC1B1D8DF03F7AB36</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>141</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>Dettaglio esercizio </Descrizione>
    //    <IdAllegato>11</IdAllegato>
    //    <HashConfronto>CEC90B3D03626A63AEA9DA0538E88569</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>82</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>Dichiarazioni statiche people </Descrizione>
    //    <IdAllegato>23</IdAllegato>
    //    <HashConfronto>F52F6E650E8CEF5A235F5E152845B488</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <RiepilogoDatiDinamici>
    //    <IdModello>91000529</IdModello>
    //    <IndiceMolteplicita>-1</IndiceMolteplicita>
    //    <Descrizione>AUA - 5. ISTANZA </Descrizione>
    //    <IdAllegato>25</IdAllegato>
    //    <HashConfronto>F1B0F8561921A92BAA33EFD7C1805341</HashConfronto>
    //  </RiepilogoDatiDinamici>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>I</TipoRecord>
    //    <Codice>0</Codice>
    //    <CodiceModello>433</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>I</TipoRecord>
    //    <Codice>0</Codice>
    //    <CodiceModello>408</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>I</TipoRecord>
    //    <Codice>0</Codice>
    //    <CodiceModello>400</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>E</TipoRecord>
    //    <Codice>20591</Codice>
    //    <CodiceModello>82</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>E</TipoRecord>
    //    <Codice>20591</Codice>
    //    <CodiceModello>157</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>E</TipoRecord>
    //    <Codice>20591</Codice>
    //    <CodiceModello>121</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>E</TipoRecord>
    //    <Codice>20591</Codice>
    //    <CodiceModello>141</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <ModelliInterventiEndo>
    //    <TipoRecord>E</TipoRecord>
    //    <Codice>20676</Codice>
    //    <CodiceModello>91000529</CodiceModello>
    //    <Ordine>999</Ordine>
    //  </ModelliInterventiEndo>
    //  <OneriDomanda>
    //    <TipoOnere>E</TipoOnere>
    //    <CodiceCausale>1</CodiceCausale>
    //    <Causale>DA ENDOPROCEDIMENTO</Causale>
    //    <CodiceInterventoOEndoOrigine>20591</CodiceInterventoOEndoOrigine>
    //    <InterventoOEndoOrigine>ASL 11.E - IG-SAN Notifica nuovi insediamenti produttivi</InterventoOEndoOrigine>
    //    <Importo>0</Importo>
    //    <Note />
    //    <NonPagato>false</NonPagato>
    //    <CodiceTipoPagamento>3</CodiceTipoPagamento>
    //    <DescrizioneTipoPagamento>Bollettino postale</DescrizioneTipoPagamento>
    //    <DataPagmento>08/11/2021 00:00:00</DataPagmento>
    //    <NumeroPagamento>456789</NumeroPagamento>
    //    <ImportoPagato>0</ImportoPagato>
    //    <ModalitaPagamento>2</ModalitaPagamento>
    //    <IdPagamentoOnline />
    //    <StatoPagamentoOnline>5</StatoPagamentoOnline>
    //    <UniqueId />
    //    <IdPosizioneNodoPagamenti />
    //    <IUV />
    //    <StatoPagamentoNativo />
    //    <DataUltimaVerifica>04/11/2021 11:21:19</DataUltimaVerifica>
    //  </OneriDomanda>
    //  <OneriDomanda>
    //    <TipoOnere>E</TipoOnere>
    //    <CodiceCausale>1</CodiceCausale>
    //    <Causale>DA ENDOPROCEDIMENTO</Causale>
    //    <CodiceInterventoOEndoOrigine>20951</CodiceInterventoOEndoOrigine>
    //    <InterventoOEndoOrigine>ASL: semplificato</InterventoOEndoOrigine>
    //    <Importo>50</Importo>
    //    <Note />
    //    <NonPagato>false</NonPagato>
    //    <CodiceTipoPagamento />
    //    <DescrizioneTipoPagamento />
    //    <DataPagmento />
    //    <NumeroPagamento />
    //    <ImportoPagato>50</ImportoPagato>
    //    <ModalitaPagamento>0</ModalitaPagamento>
    //    <IdPagamentoOnline />
    //    <StatoPagamentoOnline>5</StatoPagamentoOnline>
    //    <UniqueId />
    //    <IdPosizioneNodoPagamenti />
    //    <IUV />
    //    <StatoPagamentoNativo />
    //    <DataUltimaVerifica>04/11/2021 11:21:19</DataUltimaVerifica>
    //  </OneriDomanda>
    //  <OneriDomanda>
    //    <TipoOnere>I</TipoOnere>
    //    <CodiceCausale>19</CodiceCausale>
    //    <Causale>Onere per Sistema Pagamenti</Causale>
    //    <CodiceInterventoOEndoOrigine>72753</CodiceInterventoOEndoOrigine>
    //    <InterventoOEndoOrigine>Avvio</InterventoOEndoOrigine>
    //    <Importo>10</Importo>
    //    <Note>note dell'onere bla bla bla</Note>
    //    <NonPagato>false</NonPagato>
    //    <CodiceTipoPagamento>3</CodiceTipoPagamento>
    //    <DescrizioneTipoPagamento>Bollettino postale</DescrizioneTipoPagamento>
    //    <DataPagmento>09/11/2021 00:00:00</DataPagmento>
    //    <NumeroPagamento>123456</NumeroPagamento>
    //    <ImportoPagato>10</ImportoPagato>
    //    <ModalitaPagamento>2</ModalitaPagamento>
    //    <IdPagamentoOnline />
    //    <StatoPagamentoOnline>5</StatoPagamentoOnline>
    //    <UniqueId />
    //    <IdPosizioneNodoPagamenti />
    //    <IUV />
    //    <StatoPagamentoNativo />
    //    <DataUltimaVerifica>04/11/2021 11:21:19</DataUltimaVerifica>
    //  </OneriDomanda>
    //  <OneriDomanda>
    //    <TipoOnere>I</TipoOnere>
    //    <CodiceCausale>79</CodiceCausale>
    //    <Causale>Sanzioni CIL per lavori gia' iniziati</Causale>
    //    <CodiceInterventoOEndoOrigine>72753</CodiceInterventoOEndoOrigine>
    //    <InterventoOEndoOrigine>Avvio</InterventoOEndoOrigine>
    //    <Importo>0</Importo>
    //    <Note />
    //    <NonPagato>false</NonPagato>
    //    <CodiceTipoPagamento />
    //    <DescrizioneTipoPagamento />
    //    <DataPagmento />
    //    <NumeroPagamento />
    //    <ImportoPagato>0</ImportoPagato>
    //    <ModalitaPagamento>0</ModalitaPagamento>
    //    <IdPagamentoOnline />
    //    <StatoPagamentoOnline>5</StatoPagamentoOnline>
    //    <UniqueId />
    //    <IdPosizioneNodoPagamenti />
    //    <IUV />
    //    <StatoPagamentoNativo />
    //    <DataUltimaVerifica>04/11/2021 11:21:19</DataUltimaVerifica>
    //  </OneriDomanda>
    //  <OneriDomanda>
    //    <TipoOnere>E</TipoOnere>
    //    <CodiceCausale>57</CodiceCausale>
    //    <Causale>Spese istruttoria amministrazione provinciale</Causale>
    //    <CodiceInterventoOEndoOrigine>20628</CodiceInterventoOEndoOrigine>
    //    <InterventoOEndoOrigine>ASL 45 - IG-SAN Acqua potabile. Acquedotti</InterventoOEndoOrigine>
    //    <Importo>10</Importo>
    //    <Note>note per il frontend</Note>
    //    <NonPagato>false</NonPagato>
    //    <CodiceTipoPagamento />
    //    <DescrizioneTipoPagamento />
    //    <DataPagmento />
    //    <NumeroPagamento />
    //    <ImportoPagato>10</ImportoPagato>
    //    <ModalitaPagamento>0</ModalitaPagamento>
    //    <IdPagamentoOnline />
    //    <StatoPagamentoOnline>5</StatoPagamentoOnline>
    //    <UniqueId />
    //    <IdPosizioneNodoPagamenti />
    //    <IUV />
    //    <StatoPagamentoNativo />
    //    <DataUltimaVerifica>04/11/2021 11:21:19</DataUltimaVerifica>
    //  </OneriDomanda>
    //  <OneriAttestazionePagamento>
    //    <Importo>7000</Importo>
    //    <DichiaraDiNonAvereOneriDaPagare>false</DichiaraDiNonAvereOneriDaPagare>
    //    <IdAllegato>26</IdAllegato>
    //  </OneriAttestazionePagamento>
    //  <Allegati>
    //    <Id>0</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>386356</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>1</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>386357</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>2</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>388759</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>6</Id>
    //    <NomeFile>Test oneri.pdf</NomeFile>
    //    <CodiceOggetto>390044</CodiceOggetto>
    //    <Md5>166DBBD80FFD2587E82758792DB7F801</Md5>
    //    <FirmatoDigitalmente>false</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>7</Id>
    //    <NomeFile>Test scheda per scce.pdf</NomeFile>
    //    <CodiceOggetto>390045</CodiceOggetto>
    //    <Md5>C70F9502318FC167EDE9A66A686C9E09</Md5>
    //    <FirmatoDigitalmente>false</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>8</Id>
    //    <NomeFile>Test stradario.pdf</NomeFile>
    //    <CodiceOggetto>390046</CodiceOggetto>
    //    <Md5>B99733FB0E23169E26D3D70CFACCB04C</Md5>
    //    <FirmatoDigitalmente>false</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>9</Id>
    //    <NomeFile>MODELLO DOCUMENTO.pdf</NomeFile>
    //    <CodiceOggetto>390047</CodiceOggetto>
    //    <Md5>FD1BE4CDA2C2F25CA62E39616ED1FCA4</Md5>
    //    <FirmatoDigitalmente>false</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>10</Id>
    //    <NomeFile>Domicilio Elettronico.pdf</NomeFile>
    //    <CodiceOggetto>390048</CodiceOggetto>
    //    <Md5>B361082AE7A38FBBC1B1D8DF03F7AB36</Md5>
    //    <FirmatoDigitalmente>false</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>11</Id>
    //    <NomeFile>Dettaglio esercizio.pdf</NomeFile>
    //    <CodiceOggetto>390049</CodiceOggetto>
    //    <Md5>CEC90B3D03626A63AEA9DA0538E88569</Md5>
    //    <FirmatoDigitalmente>false</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>23</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>390091</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>25</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>390098</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>26</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>390133</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>27</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>390138</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <Allegati>
    //    <Id>28</Id>
    //    <NomeFile>___prova.odt.p7m</NomeFile>
    //    <CodiceOggetto>390140</CodiceOggetto>
    //    <Md5 />
    //    <FirmatoDigitalmente>true</FirmatoDigitalmente>
    //    <Note />
    //  </Allegati>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:433</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:408</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:400</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:157</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:121</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:141</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:82</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" /&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>campi:non:visibili:91000529</Chiave>
    //    <Valore>&lt;?xml version=""1.0"" encoding=""utf-16""?&gt;&lt;ArrayOfIdValoreCampo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema""&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015913&lt;/Id&gt;&lt;IndiceMolteplicita&gt;1&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015913&lt;/Id&gt;&lt;IndiceMolteplicita&gt;2&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015913&lt;/Id&gt;&lt;IndiceMolteplicita&gt;5&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015958&lt;/Id&gt;&lt;IndiceMolteplicita&gt;0&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015958&lt;/Id&gt;&lt;IndiceMolteplicita&gt;2&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015959&lt;/Id&gt;&lt;IndiceMolteplicita&gt;0&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015959&lt;/Id&gt;&lt;IndiceMolteplicita&gt;1&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;-91015959&lt;/Id&gt;&lt;IndiceMolteplicita&gt;5&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;91014761&lt;/Id&gt;&lt;IndiceMolteplicita&gt;2&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;91014746&lt;/Id&gt;&lt;IndiceMolteplicita&gt;1&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;IdValoreCampo&gt;&lt;Id&gt;91014746&lt;/Id&gt;&lt;IndiceMolteplicita&gt;5&lt;/IndiceMolteplicita&gt;&lt;/IdValoreCampo&gt;&lt;/ArrayOfIdValoreCampo&gt;</Valore>
    //  </DatiExtra>
    //  <DatiExtra>
    //    <Chiave>EndoprocedimentiService.StrutturaSubEndo</Chiave>
    //    <Valore>&lt;?xml version=""1.0""?&gt;
    //&lt;StrutturaSubEndo xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema""&gt;
    //  &lt;Endo&gt;
    //    &lt;SubEndoSerializable&gt;
    //      &lt;Id&gt;20683&lt;/Id&gt;
    //      &lt;IdPadre xsi:nil=""true"" /&gt;
    //    &lt;/SubEndoSerializable&gt;
    //    &lt;SubEndoSerializable&gt;
    //      &lt;Id&gt;20967&lt;/Id&gt;
    //      &lt;IdPadre xsi:nil=""true"" /&gt;
    //    &lt;/SubEndoSerializable&gt;
    //    &lt;SubEndoSerializable&gt;
    //      &lt;Id&gt;20951&lt;/Id&gt;
    //      &lt;IdPadre xsi:nil=""true"" /&gt;
    //    &lt;/SubEndoSerializable&gt;
    //    &lt;SubEndoSerializable&gt;
    //      &lt;Id&gt;20591&lt;/Id&gt;
    //      &lt;IdPadre xsi:nil=""true"" /&gt;
    //    &lt;/SubEndoSerializable&gt;
    //    &lt;SubEndoSerializable&gt;
    //      &lt;Id&gt;20628&lt;/Id&gt;
    //      &lt;IdPadre xsi:nil=""true"" /&gt;
    //    &lt;/SubEndoSerializable&gt;
    //    &lt;SubEndoSerializable&gt;
    //      &lt;Id&gt;20676&lt;/Id&gt;
    //      &lt;IdPadre&gt;20628&lt;/IdPadre&gt;
    //    &lt;/SubEndoSerializable&gt;
    //  &lt;/Endo&gt;
    //&lt;/StrutturaSubEndo&gt;</Valore>
    //  </DatiExtra>
    //</PresentazioneIstanzaDbV2>";

    //        #endregion
    //    }
}
