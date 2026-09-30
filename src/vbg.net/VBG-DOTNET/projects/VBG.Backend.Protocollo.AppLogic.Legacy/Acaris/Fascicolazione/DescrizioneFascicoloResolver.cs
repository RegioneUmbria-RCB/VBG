using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Metadati;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using System.Collections.Generic;
using System;
using System.Linq;
using Init.SIGePro.Manager.Logic.GestioneContesti;
using VBG.Backend.Protocollo.AppLogic.Legacy.Services.MailTipo;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    /*
     * La descrizione del fascicolo può essere estrapolata
     *  1. Dai metadati
     *  2. Dai dati dinamici dello specifico contesto
     *  3. Dalla mail tipo configurata per la fascicolazione
     *  4. Dall'oggetto del protocollo
     *  5. Dalla descrizione lavori dell'istanza
     */
    public class DescrizioneFascicoloResolver : IDescrizioneFascicoloResolver
    {
        private readonly string _descrizione;

        public DescrizioneFascicoloResolver(ProtocolloExt protocollo, IEnumerable<MetadatoType> metadati, Dictionary<string, List<Dyn2Dato>> datiDinamici, ProtocolloLogs _logger, IProtocolloSerializer _serializer, IBindingFactory bindingFactory)
        {
            if (metadati?.Any(x => x.Chiave == MetadatiConstants.DescrizioneFascicolo && !string.IsNullOrEmpty(x.Valore)) == true)
            {
                this._descrizione = metadati.First(x => x.Chiave == MetadatiConstants.DescrizioneFascicolo && !string.IsNullOrEmpty(x.Valore)).Valore;
                return;
            }
            if (datiDinamici?.Any() == true && !string.IsNullOrEmpty(protocollo.Configurazione.DescrizioneFascicolo))
            {
                this._descrizione = new TemplateContestiResolver().SostituisciTemplate(protocollo.Configurazione.DescrizioneFascicolo, datiDinamici);
                return;
            }
            if (protocollo.CodiceIstanza.HasValue)
            {
                var codiceMailTipo = new IstanzeMgr(protocollo.Db).GetTestoTipoFascicoloFromIstanza(protocollo.Idcomune, protocollo.CodiceIstanza.Value);
                if (!String.IsNullOrEmpty(codiceMailTipo))
                {
                    var mailTipo = new MailTipoCustomService(_logger, _serializer, bindingFactory, protocollo.Token, Convert.ToInt32(codiceMailTipo), protocollo.CodiceIstanza.Value, protocollo.CodiceMovimento).GetMailTipo();
                    this._descrizione = mailTipo.Oggetto;
                    return;
                }
            }

            this._descrizione = String.IsNullOrEmpty(protocollo.DescrizioneLavori) ? protocollo.DatiProtocollo.Oggetto : protocollo.DescrizioneLavori;
        }

        public string Get()
        {
            return this._descrizione;
        }
    }
}
