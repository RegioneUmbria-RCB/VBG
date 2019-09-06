using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel
{
    class DatiDinamiciExcelService : IDatiDinamiciExcelService
    {
        IAliasResolver _aliasResolver;
        ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        IRegoleRepository _regoleRepository;
        IOggettiService _oggettiService;
        IFirmaDigitaleMetadataService _firmaDigitaleService;

        public DatiDinamiciExcelService(IAliasResolver aliasResolver, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IRegoleRepository regoleRepository, IOggettiService oggettiService, IFirmaDigitaleMetadataService firmaDigitaleService)
        {
            this._aliasResolver = aliasResolver;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._regoleRepository = regoleRepository;
            this._oggettiService = oggettiService;
            this._firmaDigitaleService = firmaDigitaleService;
        }

        public void EstraiDatiDinamiciDaFilesExcel(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var regole = this._regoleRepository.All();
            var esitoEstrazione = new List<EsitoMappatura>();

            var documenti = domanda.ReadInterface.Documenti.Endo.Documenti.Union(domanda.ReadInterface.Documenti.Intervento.Documenti).Where( x => IsValidExcelFile(x.AllegatoDellUtente));

            foreach (var documento in documenti)
            {
                var doc = new FileExcel(documento.AllegatoDellUtente, this._oggettiService, this._firmaDigitaleService);
                var valoriMappati = regole.ApplicaA(doc);

                esitoEstrazione.AddRange(valoriMappati);
            }

            foreach(var valore in esitoEstrazione)
            {
                domanda.WriteInterface.DatiDinamici.AggiornaOCrea(valore.IdCampo, 0, 0, valore.Valore, valore.Valore, valore.NomeCampo);
            }

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        private static bool IsValidExcelFile(DocumentoDomanda.AllegatoUtente allegatoUtente)
        {
            if (allegatoUtente == null)
            {
                return false;
            }

            var ext = Path.GetExtension(allegatoUtente.NomeFile).ToUpperInvariant();

            return (ext == ".XLSX" || allegatoUtente.NomeFile.ToUpperInvariant().EndsWith(".XLSX.P7M"));
        }
    }
}
