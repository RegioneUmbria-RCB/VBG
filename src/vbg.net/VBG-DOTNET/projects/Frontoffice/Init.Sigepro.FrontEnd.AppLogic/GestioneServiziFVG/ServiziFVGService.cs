using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.GenerazionePdfModulo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.TagSearchers;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.FileEncoding;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Standard.Scripts;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class ServiziFVGService
    {
        private static class Constants
        {
            public const string SegnapostoNomeModello = "$nomeModello";
            public const string SegnapostoDataUltimaModifica = "$dataUltimaModifica";

            public const string TemplateScriptNumeroPagina = @"
<style>
	#pageFooter {
		width: 100%;
		display: none;		
	}
</style>
<script type='text/javascript'>
		 var PhantomJSPrinting = {
			footer: {
				height: '1cm',
				contents: function(pageNum, numPages)
                {
                    var footer = document.getElementById('pageFooter'),
                        html = footer.innerHTML;

                    return html.replace('$pageNum', pageNum.toString()).replace('$numPages', numPages.toString());
                }
            }
         };
</script>";

            public const string TemplatePageFooter = @"
	<footer id='pageFooter' name='pageFooter'>
		<div style='width:100%;text-align:center;font-family: sans-serif; font-size: 8pt'>
			Pag. $pageNum/$numPages
		</div>
	</footer>";
        }


        public class SchedaDinamicaEndoprocedimento
        {
            public int Id { get; set; }
            public string Descrizione { get; set; }
            public bool Compilata { get; set; }
            public int Ordine { get; set; }
            public bool Pubblica { get; set; }
        }

        public class AllegatoEndoprocedimento
        {
            public int Id { get; internal set; }
            public int CodiceOggetto { get; internal set; }
        }

        public class EndoprocedimentoDaCompilare
        {
            public readonly int Id;
            public readonly string Descrizione;
            public readonly DateTime? DataUltimaModifica;
            internal IEnumerable<SchedaDinamicaEndoprocedimento> ListaSchede;
            public IEnumerable<SchedaDinamicaEndoprocedimento> ListaSchedePubblicate => this.ListaSchede.Where(x => x.Pubblica);
            public bool TutteLeSchedeSonoCompilate => !this.ListaSchedePubblicate.Where(x => !x.Compilata).Any();
            public IEnumerable<AllegatoEndoprocedimento> Allegati;

            public EndoprocedimentoDaCompilare(int id, string descrizione, DateTime? dataUltimaModifica)
            {
                this.Id = id;
                this.Descrizione = descrizione;
                this.DataUltimaModifica = dataUltimaModifica;

                this.ListaSchede = Enumerable.Empty<SchedaDinamicaEndoprocedimento>();
                this.Allegati = Enumerable.Empty<AllegatoEndoprocedimento>();
            }

            public int? GetIdSchedaSuccessiva(int idSchedaRiferimento)
            {
                var numeroSchede = this.ListaSchedePubblicate.Count();

                for (int i = 0; i < numeroSchede; i++)
                {
                    var scheda = this.ListaSchedePubblicate.ElementAt(i);

                    if (scheda.Id == idSchedaRiferimento)
                    {
                        // La scheda compilata è l'ultima
                        if (i < numeroSchede - 1)
                        {
                            return this.ListaSchedePubblicate.ElementAt(i + 1).Id;
                        }
                    }
                }

                return null;
            }
        }



        private readonly IAliasResolver _aliasResolver;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly IDatiDinamiciRepository _datiDinamiciRepository;
        private readonly IFvgDatabaseFactory _databaseFactory;
        private readonly IFvgManagedDataRepository _managedDataRepository;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly IEndoprocedimentiService _endoService;
        private readonly ISostituzioneSegnapostoRiepilogoService _sostituzioneSegnapostoRiepilogoService;
        private readonly IOggettiService _oggettiService;
        private readonly IModelliDinamiciFactory _modelliDinamiciFactory;
        private readonly IStrutturaModelloDinamicoRepository _strutturaModelloDinamicoRepository;
        private readonly ILog _log = LogManager.GetLogger(typeof(ServiziFVGService));

        public ServiziFVGService(IAliasResolver aliasResolver, ITokenApplicazioneService tokenApplicazioneService, IDatiDinamiciRepository datiDinamiciRepository,
            IFvgDatabaseFactory databaseFactory, IFvgManagedDataRepository managedDataRepository, IEndoprocedimentiService endoService,
            ISostituzioneSegnapostoRiepilogoService sostituzioneSegnapostoRiepilogoService, IHtmlToPdfFileConverter fileConverter, IOggettiService oggettiService,
            IModelliDinamiciFactory modelliDinamiciFactory, IStrutturaModelloDinamicoRepository strutturaModelloDinamicoRepository)
        {
            this._aliasResolver = aliasResolver;
            this._tokenApplicazioneService = tokenApplicazioneService;
            this._datiDinamiciRepository = datiDinamiciRepository;
            this._databaseFactory = databaseFactory;
            this._managedDataRepository = managedDataRepository;
            //this._webServiceProxy = webServiceProxyFactory.CreateService();
            this._endoService = endoService;
            this._sostituzioneSegnapostoRiepilogoService = sostituzioneSegnapostoRiepilogoService;
            this._fileConverter = fileConverter;
            this._oggettiService = oggettiService;
            this._modelliDinamiciFactory = modelliDinamiciFactory;
            this._strutturaModelloDinamicoRepository = strutturaModelloDinamicoRepository;
        }

        public void SalvaScheda(ModelloDinamicoBase modello)
        {
            modello.Salva();

            if (modello.ErroriScript.Count() > 0)
                throw new SalvataggioModelloDinamicoException(modello.ErroriScript);

            modello.SalvaCampiNonVisibili();
        }

        private class EmptyClassLoader : IClasseContestoLoader
        {
            public IClasseContestoModelloDinamico LoadClass()
            {
                return new Istanze();
            }
        }

        public ModelloDinamicoIstanza GetModelloDinamico(long codiceIstanza, string idModulo, int idModello, int indiceScheda)
        {
            var database = this._databaseFactory.Create(codiceIstanza, idModulo);
            var cache = this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(idModello);
            var classeContestoLoader = new EmptyClassLoader();
            var repository = new FvgDyn2DatiRepository(database);

            var builder = new ModelloDinamicoLoaderBuilder();

            var loader = builder.UsaStruttura(cache)
                           .UsaLoaderClasseContesto(classeContestoLoader)
                           // .UsaQueryLocalizzazioni(classeContestoLoader) // Usa la classe di default che non restituisce localizzazioni
                           .UsaRepository(repository)
                           .UsaToken(this._tokenApplicazioneService.GetToken())
                           .Build(this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);



            //var modello = this._datiDinamiciRepository.GetCacheModelloDinamico(idModello);
            //var factory = new FvgDyn2DataAccessFactory(modello, database, this._tokenApplicazioneService);
            //var loader = new ModelloDinamicoLoader(factory, this._aliasResolver.AliasComune, ContestoScriptEnum.Frontoffice);
            var scheda = this._modelliDinamiciFactory.CreaModelloIstanza(loader, idModello, indiceScheda, false);

            return scheda;
        }

        public EndoprocedimentoDaCompilare GetDatiModulo(long codiceIstanza, string idModulo, long? codiceIstanzaDaCopiare = null)
        {
            var database = this._databaseFactory.Create(codiceIstanza, idModulo);

            var datiModulo = this.GetDatiModulo(database, idModulo);

            var campiDinamici = this.CampiDinamiciDaIdSchede(datiModulo.ListaSchede.Select(x => x.Id));

            if (codiceIstanzaDaCopiare.HasValue)
            {
                try
                {

                    var altroDatabase = this._databaseFactory.Create(codiceIstanzaDaCopiare.Value, idModulo);

                    database.CopiaValoriDaAltraDomanda(altroDatabase);
                    database.ImpostaTutteLeSchedeComeNonCompilate();
                }
                catch (Exception e)
                {
                    this._log.Error($"Impossibile rileggere i dati della domanda da cui effettuare la cipia: {e}");
                }
            }

            // Salvo sempre i dati di tutti i campi recuperabili dal managed data tramite espressioni xpath
            var listaDati = this._managedDataRepository.ReadAllValues(codiceIstanza, campiDinamici);

            database.SalvaListaDati(listaDati);

            return datiModulo;
        }

        /// <summary>
        /// Restituisce una lista di strutture che descrivono tutti i campi dinamici che appartengono alla lista di schede passata
        /// </summary>
        /// <param name="idSchede">Lista di id di schede dinamiche di cui recuperare la definizione dei campi</param>
        /// <returns>Lista di strutture che descrivono tutti i campi dinamici che appartengono alla lista di schede passata</returns>
        private IEnumerable<IDyn2Campo> CampiDinamiciDaIdSchede(IEnumerable<int> idSchede)
        {
            return idSchede.SelectMany(x => this._strutturaModelloDinamicoRepository.GetStrutturaModelloDinamico(x).ListaCampiDinamici.Values);
        }

        /// <summary>
        /// Carica i dati del modulo attualmente in compilazione. I dati includono:
        /// - Informazioni sull'endo
        /// - Informazioni sugli allegati
        /// - Informazioni sui dati dinamici compilati in precedenza
        /// </summary>
        /// <param name="database"></param>
        /// <param name="idModulo"></param>
        /// <returns></returns>
        /// <exception cref="ArgumentException"></exception>
        private EndoprocedimentoDaCompilare GetDatiModulo(FvgDatabase database, string idModulo)
        {
            var endoprocedimento = this._endoService.GetByIdEndoMappato(idModulo) ?? throw new ArgumentException($"Il modulo {idModulo} non è stato configurato");
            var rVal = new EndoprocedimentoDaCompilare(endoprocedimento.Id, endoprocedimento.Descrizione, endoprocedimento.DataUltimaModifica);

            database.SincronizzaSchede(endoprocedimento.Schede);

            if (!database.ContieneValori)
            {
                var listaCampiDelModulo = this.CampiDinamiciDaIdSchede(endoprocedimento.Schede.Select(x => x.Id))
                                            .Select(x => x.Nomecampo); ;

                database.InizializzaValoriDaManagedData(listaCampiDelModulo, this._managedDataRepository);
            }

            rVal.ListaSchede = endoprocedimento.Schede.Select(x => new SchedaDinamicaEndoprocedimento
            {
                Id = x.Id,
                Descrizione = x.Descrizione,
                Ordine = x.Ordine.GetValueOrDefault(9999),
                Compilata = database.IsSchedaCompilata(x.Id),
                Pubblica = x.Pubblica
            }).OrderBy(x => x.Ordine);

            rVal.Allegati = endoprocedimento.Allegati == null ?
                                Enumerable.Empty<AllegatoEndoprocedimento>() :
                                endoprocedimento.Allegati
                                                .Select(x => new AllegatoEndoprocedimento
                                                {
                                                    Id = x.Codice,
                                                    CodiceOggetto = x.CodiceOggetto.Value
                                                });

            return rVal;
        }

        public BinaryFile GeneraPdfModulo(long codiceIstanza, string idModulo)
        {
            var database = this._databaseFactory.Create(codiceIstanza, idModulo);

            return this.GeneraPdfModulo(database, idModulo);
        }

        private BinaryFile GeneraPdfModulo(FvgDatabase database, string idModulo)
        {
            var endoInCompilazione = this.GetDatiModulo(database, idModulo) ?? throw new ArgumentException($"Il modulo {idModulo} non è configurato");
            var reader = new FvgDatiDinamiciRiepilogoReader(this._aliasResolver, this._datiDinamiciRepository, database, endoInCompilazione.ListaSchede, this._strutturaModelloDinamicoRepository);
            var template = @"<!doctype html>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html;charset=utf-8' />
	<title>Document</title>
</head>
<body>
    Nome modello: $nomeModello<br />
    Data ultima modifica: $dataUltimaModifica<br />
	<schedeDinamiche />
    <noteCompilazione />
</body>
</html>";

            if (endoInCompilazione.Allegati.Any())
            {
                var oggetto = this._oggettiService.GetById(endoInCompilazione.Allegati.First().CodiceOggetto);
                template = UnknownEncodingToString.Convert(oggetto.FileContent);
            }

            // Gestione dei numeri pagina
            template = new TagAperturaBody(template).InserisciHtml(Constants.TemplatePageFooter);
            template = new TagChiusuraHead(template).InserisciHtml(Constants.TemplateScriptNumeroPagina);

            // Da rimuovere in produzione
            // template = File.ReadAllText(@"c:\temp\B1_-_Commercio_in_sede_fissa_web.html");
            template = template.Replace(Constants.SegnapostoDataUltimaModifica, endoInCompilazione.DataUltimaModifica.HasValue ? endoInCompilazione.DataUltimaModifica.Value.ToString("dd/MM/yyyy") : "");
            template = template.Replace(Constants.SegnapostoNomeModello, endoInCompilazione.Descrizione);

            var html = this._sostituzioneSegnapostoRiepilogoService.ProcessaRiepilogo(reader, template);

            return this._fileConverter.Converti($"riepilogo-{idModulo}.pdf", html, new RenderingFlags
            {
                ConvertToPdfa = false
            });
        }

        public void AllegaPdfADomanda(long codiceIstanza, string idModulo)
        {
            var binaryFile = this.GeneraPdfModulo(codiceIstanza, idModulo);

            this._managedDataRepository.SalvaFilePdf(codiceIstanza, idModulo, binaryFile);
        }

        public BinaryFile GeneraAnteprimaModulo(string idModulo)
        {
            return this.GeneraPdfModulo(new FvgDatabase(), idModulo);
        }
    }
}
