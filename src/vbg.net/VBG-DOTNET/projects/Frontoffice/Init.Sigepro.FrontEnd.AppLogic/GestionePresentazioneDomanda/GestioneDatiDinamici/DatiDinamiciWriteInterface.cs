using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneRiepiloghiSchedeDinamiche;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.VisibilitaCampi;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici
{
    public static class Dyn2DatiExtensions
    {
        public static PresentazioneIstanzaDbV2.Dyn2DatiRow FindByIdCampoIndiceSchedaIndiceMolteplicita(this PresentazioneIstanzaDbV2.Dyn2DatiDataTable table, int idCampo, int indiceScheda, int indiceMolteplicita)
        {
            return table.Where(x => x.IdCampo == idCampo && x.IndiceScheda == indiceScheda && x.IndiceMolteplicita == indiceMolteplicita).FirstOrDefault();
        }
    }

    public class DatiDinamiciWriteInterface : IDatiDinamiciWriteInterface
    {
        private static class Constants
        {
            public const string TipoModelloIntervento = "I";
            public const string TipoModelloEndoprocedimento = "E";
            public const string TipoModelloCittadinoExtracomunitario = "EX";

            public const string CampiNonVisibiliKeyFmtString = "campi:non:visibili:{0}";

            public const int IdModelloSenzaOrdine = 999;
        }

        private readonly PresentazioneIstanzaDbV2 _database;
        private readonly IRiepiloghiSchedeDinamicheWriteInterface _riepiloghiSchedeDinamicheWriteInterface;

        public DatiDinamiciWriteInterface(PresentazioneIstanzaDbV2 database, IRiepiloghiSchedeDinamicheWriteInterface riepiloghiSchedeDinamicheWriteInterface)
        {
            this._database = database;
            this._riepiloghiSchedeDinamicheWriteInterface = riepiloghiSchedeDinamicheWriteInterface;
        }

        #region IDatiDinamiciWriteInterface Members

        public void AggiornaOCrea(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato, string nomeCampo)
        {
            var row = this._database.Dyn2Dati.FindByIdCampoIndiceSchedaIndiceMolteplicita(idCampo, indiceScheda, indiceMolteplicita);

            if (row != null)
                this.EliminaValoreDaIdcampoIndiceMolteplicita(idCampo, indiceScheda, indiceMolteplicita);

            this.AggiungiDatoDinamico(idCampo, indiceScheda, indiceMolteplicita, valore, valoreDecodificato, nomeCampo);
        }

        public void EliminaValoreDaIdcampoIndiceMolteplicita(int idCampo, int indiceScheda, int indiceMolteplicita)
        {
            var row = this._database.Dyn2Dati.FindByIdCampoIndiceSchedaIndiceMolteplicita(idCampo, indiceScheda, indiceMolteplicita);

            if (row == null)
                return;
            /*throw new InvalidOperationException("Impossibile trovare il campo dinamico con idCampo=" + idCampo + " e indicemolteplicita=" + indiceMolteplicita);*/

            row.Delete();
            this._database.Dyn2Dati.AcceptChanges();
        }

        public void EliminaValoreDaIdcampo(int idCampo)
        {
            var rows = this._database.Dyn2Dati.Where(x => x.IdCampo == idCampo);

            if (!(rows?.Any() ?? false))
                return;

            /*throw new InvalidOperationException("Impossibile trovare il campo dinamico con idCampo=" + idCampo + " e indicemolteplicita=" + indiceMolteplicita);*/
            rows.ToList().ForEach(x => x.Delete());
            this._database.Dyn2Dati.AcceptChanges();
        }

        public void EliminaValoreDaIdcampo(int idCampo, int indiceScheda)
        {
            var rows = this._database.Dyn2Dati.Where(x => x.IdCampo == idCampo && x.IndiceScheda == indiceScheda).ToList();

            if (rows == null || rows.Count == 0)
                return;
            /*throw new InvalidOperationException("Impossibile trovare il campo dinamico con idCampo=" + idCampo + " e indicemolteplicita=" + indiceMolteplicita);*/
            rows.ForEach(x => x.Delete());
            this._database.Dyn2Dati.AcceptChanges();
        }

        public void AggiungiDatoDinamico(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato, string nomeCampo)
        {
            var row = this._database.Dyn2Dati.FindByIdCampoIndiceSchedaIndiceMolteplicita(idCampo, indiceScheda, indiceMolteplicita);

            if (row != null)
                throw new InvalidOperationException("Esiste già un record per idCampo=" + idCampo + " e indicemolteplicita=" + indiceMolteplicita);

            this._database.Dyn2Dati.AddDyn2DatiRow(idCampo, indiceScheda, indiceMolteplicita, valore, valoreDecodificato, nomeCampo);
        }

        public void ModificaValoreCampo(int idCampo, int indiceScheda, int indiceMolteplicita, string valore, string valoreDecodificato)
        {
            var row = this._database.Dyn2Dati.FindByIdCampoIndiceSchedaIndiceMolteplicita(idCampo, indiceScheda, indiceMolteplicita);

            row.Valore = valore;
            row.ValoreDecodificato = valoreDecodificato;
        }

        public void ModificaStatoCompilazioneModello(int idModello, int maxMolteplicita, bool compilato)
        {
            var row = this._database.Dyn2Modelli.FindByIdModello(idModello);

            if (row == null)
                throw new InvalidOperationException("Impossibile trovare il modello con id=" + idModello);

            row.MaxMolteplicita = maxMolteplicita;
            row.Compilato = compilato;
        }

        public void SincronizzaModelliDinamici(SincronizzaModelliDinamiciCommand command)
        {
            // Reimposto gli id dei modelli in uso
            var modelliInterventoDaSincronizzare = command.ModelliIntervento.Select(x => x.ToModelloDinamicoInterventoInUsoDto());

            var modelliEndoDaSincronizzare = command.ModelliEndoprocedimento.Select(x => x.ToModelloDinamicoEndoprocedimentoInUsoDto());

            ModelloDinamicoCittadinoExtracomunitarioInUsoDto? modelloCittadinoExtracomunitario = null;

            if (command.ModelloCittadiniExtracomunitari != null)
                modelloCittadinoExtracomunitario = new ModelloDinamicoCittadinoExtracomunitarioInUsoDto { IdModello = command.ModelloCittadiniExtracomunitari.Codice };

            var reimpostaCommand = new ReimpostaModelliDinamiciInUsoCommand(modelliInterventoDaSincronizzare, modelliEndoDaSincronizzare, modelloCittadinoExtracomunitario);

            this.ReimpostaModelliInUso(reimpostaCommand);

            // Sincronizzo le schede dinamiche
            this.Sincronizza(command);

            // Sincronizzo lo stato dei riepiloghi
            this._riepiloghiSchedeDinamicheWriteInterface.SincronizzaConModelli();
        }



        #endregion

        private void ReimpostaModelliInUso(ReimpostaModelliDinamiciInUsoCommand command)
        {
            this._database.ModelliInterventiEndo.Clear();

            foreach (var schedaIntervento in command.ModelliIntervento)
                this._database.ModelliInterventiEndo.AddModelliInterventiEndoRow(Constants.TipoModelloIntervento, schedaIntervento.IdIntervento, schedaIntervento.IdModello, schedaIntervento.Ordine);

            foreach (var schedaEndo in command.ModelliEndoprocedimento)
                this._database.ModelliInterventiEndo.AddModelliInterventiEndoRow(Constants.TipoModelloEndoprocedimento, schedaEndo.IdEndoprocedimento, schedaEndo.IdModello, schedaEndo.Ordine);

            if (command.ModelloCittadinoExtracomunitario != null)
                this._database.ModelliInterventiEndo.AddModelliInterventiEndoRow(Constants.TipoModelloCittadinoExtracomunitario, -1, command.ModelloCittadinoExtracomunitario.IdModello, Constants.IdModelloSenzaOrdine);
        }

        private void Sincronizza(SincronizzaModelliDinamiciCommand command)
        {
            var modelloCittadinoEc = command.ModelloCittadiniExtracomunitari;

            var listaNuoviId = command.ModelliIntervento
                                      .Select(x => x.Codice)
                                      .Union(command.ModelliEndoprocedimento
                                                     .Select(x => x.Codice)).ToList();

            if (modelloCittadinoEc != null)
                listaNuoviId.Add(modelloCittadinoEc.Codice);

            this.EliminaWhereIdModelloNotIn(listaNuoviId);

            foreach (var mod in command.ModelliIntervento)
                this.SalvaOAggiorna(mod.Codice, mod.Descrizione, mod.TipoFirma, mod.Facoltativa);

            foreach (var mod in command.ModelliEndoprocedimento)
                this.SalvaOAggiorna(mod.Codice, mod.Descrizione, mod.TipoFirma, mod.Facoltativa);

            if (modelloCittadinoEc != null)
                this.SalvaOAggiorna(modelloCittadinoEc.Codice, modelloCittadinoEc.Descrizione, modelloCittadinoEc.TipoFirma, modelloCittadinoEc.Facoltativa);
        }

        private void SalvaOAggiorna(int idModello, string descrizione, TipoFirmaEnum tipoFirmaEnum, bool facoltativo)
        {
            var row = this._database.Dyn2Modelli.FindByIdModello(idModello);

            if (row == null)
            {
                this._database.Dyn2Modelli.AddDyn2ModelliRow(idModello, descrizione, false, (int)tipoFirmaEnum, 0, facoltativo);
            }
            else
            {
                row.NomeScheda = descrizione;
                row.TipoFirma = (int)tipoFirmaEnum;
                row.Facoltativo = facoltativo;
            }
        }

        private void EliminaWhereIdModelloNotIn(IEnumerable<int> listaNuoviId)
        {
            var modellidaEliminare = this._database.Dyn2Modelli.Where(r => !listaNuoviId.Contains(r.IdModello)).ToArray();

            foreach (var modello in modellidaEliminare)
                this.Elimina(modello);
        }

        private void Elimina(PresentazioneIstanzaDbV2.Dyn2ModelliRow modello)
        {
            if (modello != null)
            {
                this._riepiloghiSchedeDinamicheWriteInterface.EliminaByIdModello(modello.IdModello);

                modello.Delete();
            }
        }


        public void SalvaCampiNonVisibili(int idModello, IEnumerable<IdValoreCampo> campiNonVisibili)
        {
            var key = String.Format(Constants.CampiNonVisibiliKeyFmtString, idModello);
            var item = this._database.DatiExtra.FindByChiave(key);

            if (item != null)
                item.Delete();

            var xml = StreamUtils.SerializeClass(campiNonVisibili.ToList());

            this._database.DatiExtra.AddDatiExtraRow(key, xml);
        }


        public void EliminaValoriCampo(int idCampo)
        {
            var rows = this._database.Dyn2Dati.Where(x => x.IdCampo == idCampo).ToList();

            if (rows == null)
                return;

            foreach (var r in rows)
            {
                r.Delete();
            }

            this._database.Dyn2Dati.AcceptChanges();
        }
    }
}
