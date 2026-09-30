using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text.RegularExpressions;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
    public class DatiRepository : IDyn2DatiRepository
    {
        private readonly IDomandaOnlineReadInterface _readInterface;
        private readonly IDomandaOnlineWriteInterface _writeInterface;

        public class ValoreCampo : IValoreCampo
        {
            public int IdCampo { get; set; }
            public int? Indice { get; set; }
            public int? IndiceMolteplicita { get; set; }
            public string Valore { get; set; }
            public string Valoredecodificato { get; set; }
        }

        public DatiRepository(DomandaOnline domanda)
        {
            this._readInterface = domanda.ReadInterface;
            this._writeInterface = domanda.WriteInterface;
        }

        public void EliminaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            foreach (var campo in campiDaEliminare)
            {
                this._writeInterface.DatiDinamici.EliminaValoreDaIdcampo(campo.Id, idModello.IndiceModello);
            }
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var rVal = new SerializableDictionary<int, List<IValoreCampo>>();

            var listaValori = this._readInterface.DatiDinamici
                                            .DatiDinamici
                                            .Where(x => x.IdCampo >= 0 && x.IndiceScheda == indiceModello)
                                            .Select(x => new ValoreCampo
                                            {
                                                IdCampo = x.IdCampo,
                                                // Codiceistanza = -1,
                                                // Idcomune = string.Empty,
                                                Indice = x.IndiceScheda,
                                                IndiceMolteplicita = x.IndiceMolteplicita,
                                                Valore = x.Valore,
                                                Valoredecodificato = x.ValoreDecodificato
                                            })
                                            .GroupBy(x => x.IdCampo)
                                             .ToDictionary(group => group.Key, group => group.Cast<IValoreCampo>());

            return new SerializableDictionary<int, IEnumerable<IValoreCampo>>(listaValori);
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> campiNonVisibili)
        {
            this._writeInterface.DatiDinamici.SalvaCampiNonVisibili(idModello.IdModello, campiNonVisibili);
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            // Contiene l'indice massimo di molteplicità del modello.
            // Usato in futuro per la gestione della firma a blocchi
            var maxMolteplicita = 0;
            var indiceScheda = idModello.IndiceModello;
            var nomeModello = this._readInterface.DatiDinamici.Modelli.Where(x => x.IdModello == idModello.IdModello).FirstOrDefault()?.Descrizione;

            foreach (var campoDaSalvare in campiDaSalvare)
            {
                Debug.WriteLine("Salvataggio del campo " + campoDaSalvare.Id);

                var idCampo = campoDaSalvare.Id;
                var nomeCampo = campoDaSalvare.NomeCampo;
                var isCampoUpload = campoDaSalvare.IsCampoUpload;
                var isCampoData = campoDaSalvare.IsCampoData;

                // Conterrà la lista di righe da eliminare in quanto il campo ha come valore "" oppure
                // perchè la loro molteplicità eccede quella attuale del campo
                var righeDaEliminare = new List<PresentazioneIstanzaDataSet.Dyn2DatiRow>();

                for (var indiceMolteplicita = 0; indiceMolteplicita < campoDaSalvare.ListaValori.Count; indiceMolteplicita++)
                {
                    var valore = campoDaSalvare.ListaValori[indiceMolteplicita].Valore;
                    var valoreDecodificato = campoDaSalvare.ListaValori[indiceMolteplicita].ValoreDecodificato;
                    var valoreVuoto = string.IsNullOrEmpty(valore.Trim());

                    if (!valoreVuoto)
                        maxMolteplicita = Math.Max(maxMolteplicita, indiceMolteplicita);

                    if (string.IsNullOrEmpty(valoreDecodificato))
                        valoreDecodificato = valore;

                    var rigaDatiDinamici = this._readInterface.DatiDinamici.DatiDinamici
                                                         .Where(x => x.IdCampo == campoDaSalvare.Id && x.IndiceScheda == indiceScheda && x.IndiceMolteplicita == indiceMolteplicita)
                                                         .FirstOrDefault();

                    var isNuovaRiga = rigaDatiDinamici == null && !valoreVuoto;
                    var isValoreSvuotato = rigaDatiDinamici != null && valoreVuoto;
                    var isRigaModificata = rigaDatiDinamici != null && (rigaDatiDinamici.Valore != valore || rigaDatiDinamici.ValoreDecodificato != valoreDecodificato);

                    if (isValoreSvuotato || isRigaModificata && isCampoUpload)
                    {
                        this._writeInterface.DatiDinamici.EliminaValoreDaIdcampoIndiceMolteplicita(rigaDatiDinamici!.IdCampo, indiceScheda, rigaDatiDinamici.IndiceMolteplicita);
                    }

                    if ((isValoreSvuotato || isRigaModificata) && isCampoUpload)
                    {
                        // Elimino il vecchio allegato del campo
                        this._writeInterface.Documenti.ForzaEliminazioneDocumentoDaCodiceOggetto(Convert.ToInt32(rigaDatiDinamici!.Valore));
                    }

                    if (isCampoData && !valoreVuoto && Regex.IsMatch(valoreDecodificato, "^[0-9]{8}$")) // HACK: il valore del campo data è stato probabilmente prepopolato con una formula ma non è stato convertito nel formato dd/MM/yyyy
                    {
                        var data = DateTime.Now;

                        if (DateTime.TryParseExact(valoreDecodificato, "yyyyMMdd", null, System.Globalization.DateTimeStyles.None, out data))
                        {
                            valoreDecodificato = data.ToString("dd/MM/yyyy");
                        }
                    }

                    if (isNuovaRiga || (isRigaModificata && isCampoUpload && !isValoreSvuotato))
                    {
                        if (isCampoUpload)
                        {
                            var nomeAllegato = string.Format("Documento della scheda {0}: {1} {2}", nomeModello, campoDaSalvare.Etichetta, indiceMolteplicita + 1);
                            //_writeInterface.Documenti.AggiungiDocumentoInterventoLibero(nomeAllegato, Convert.ToInt32(valore), valoreDecodificato, -1, "Altri allegati", false);
                            this._writeInterface.Documenti.AggiungiDocumentoSchedaDinamica(nomeAllegato, Convert.ToInt32(valore), valoreDecodificato);
                        }

                        this._writeInterface.DatiDinamici.AggiungiDatoDinamico(idCampo, indiceScheda, indiceMolteplicita, valore, valoreDecodificato, nomeCampo);
                    }

                    if (!isValoreSvuotato && isRigaModificata && !isCampoUpload)
                    {
                        this._writeInterface.DatiDinamici.ModificaValoreCampo(rigaDatiDinamici.IdCampo, indiceScheda, rigaDatiDinamici.IndiceMolteplicita, valore, valoreDecodificato);
                    }
                }

                // Elimino le righe con molteplicità superiore al numero massimo di valori nel campo
                // Questa funzionalità è stata introdotto con la cancellazione dei blocchi multipli
                var molteplicitaRiga = campoDaSalvare.ListaValori.Count - 1;

                var righeInEccesso = this._readInterface
                                        .DatiDinamici
                                        .DatiDinamici
                                        .Where(x => x.IdCampo == campoDaSalvare.Id && x.IndiceMolteplicita > molteplicitaRiga)
                                        .ToList();

                foreach (var rigaDaEliminare in righeInEccesso)
                {
                    if (isCampoUpload)
                    {
                        var valore = rigaDaEliminare.Valore;

                        this._writeInterface.Documenti.EliminaAllegatoADocumentoDaCodiceOggetto(Convert.ToInt32(valore));
                    }

                    this._writeInterface.DatiDinamici.EliminaValoreDaIdcampoIndiceMolteplicita(rigaDaEliminare.IdCampo, indiceScheda, rigaDaEliminare.IndiceMolteplicita);
                }
            }

            // Se nel datase non è presente il modello a cui il campo è associato lo aggiungo
            // e lo marco come compilato
            this._writeInterface.DatiDinamici.ModificaStatoCompilazioneModello(idModello.IdModello, maxMolteplicita, true);
        }

    }
}
