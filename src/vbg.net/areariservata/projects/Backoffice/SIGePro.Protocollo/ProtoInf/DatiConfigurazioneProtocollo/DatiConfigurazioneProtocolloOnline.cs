using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo
{
    public class DatiConfigurazioneProtocolloOnline : IDatiConfigurazioneInterventoProtocollo
    {
        DataBase _db;
        string _codiceIstanza;
        string _idComune;
        string _software;
        string _codiceComune;
        IDatiProtocollo _datiProtoDefault;
        VerticalizzazioniServiceWrapper _regole;
        ProtocolloLogs _logs;

        public DatiConfigurazioneProtocolloOnline(IDatiProtocollo datiProtoDefault, VerticalizzazioniServiceWrapper regole, DataBase db, string _codiceIstanza, string idComune, string software, string codiceComune, ProtocolloLogs logs)
        {
            this._db = db;
            this._codiceIstanza = _codiceIstanza;
            this._idComune = idComune;
            this._software = software;
            this._codiceComune = codiceComune;
            this._datiProtoDefault = datiProtoDefault;
            this._regole = regole;
            this._logs = logs;
        }

        public DatiConfigurazioneProtocolloInfo GetDati()
        {
            this._logs.Info("RECUPERO DEI DATI DA ONLINE");
            bool isDaSmistare = false;
            var retVal = new DatiConfigurazioneProtocolloInfo();

            var inventarioProcedimentiMgr = new InventarioProcedimentiMgr(this._db);
            this._logs.Info($"RICERCA DEGLI ENDOPROCEDIMENTI RELATIVI ALL'ISTANZA {this._codiceIstanza}");
            var inventarioProcedimenti = inventarioProcedimentiMgr.GetListByCodiceIstanza(this._idComune, this._codiceIstanza);

            this._logs.Info($"NUMERO ENDOPROCEDIMENTI TROVATI RELATIVI ALL'ISTANZA {this._codiceIstanza}: {inventarioProcedimenti.Count}");

            foreach (var el in inventarioProcedimenti)
            {
                this._logs.Info($"VERIFICA SE L'ENDOPROCEDIMENTO CON CODICE INVENTARIO {el.Codiceinventario.Value} E' PRESENTE TRA QUELLI CHE POSSONO ESSERE SMISTATI, PARAMETRO CODICI_ENDOPROC_SMISTABILI: {String.Join(",", this._regole.EndoProcedimentiSmistamento)}");
                var amm = el.GetAmministrazionePerSmistamento(this._regole.EndoProcedimentiSmistamento, this._db, this._idComune, this._software, this._codiceComune);

                if (amm != null)
                {
                    this._logs.Info($"ENDPROCEDIMENTO TROVATO, CODICE INVENTARIO: {el.Codiceinventario.Value}, AMMINISTRAZIONE OTTENUTA, CODICE: {amm.CODICEAMMINISTRAZIONE}, DESCRIZIONE: {amm.AMMINISTRAZIONE}, SOFTWARE: {amm.STC_IDSPORTELLO}");
                    this._software = amm.STC_IDSPORTELLO;
                    //this._regole = new VerticalizzazioniServiceWrapper(new VerticalizzazioneProtocolloProtInf(this._idComune, this._software, this._codiceComune));
                    isDaSmistare = true;
                    break;
                }

                this._logs.Info($"ENDPROCEDIMENTO {el.Codiceinventario.Value} NON TROVATO TRA QUELLI SMISTABILI, PARAMETRO CODICI_ENDOPROC_SMISTABILI: {String.Join(",", this._regole.EndoProcedimentiSmistamento)}");
            }

            if (!isDaSmistare)
            {
                this._logs.Info($"LA PRATICA NON DEVE ESSERE SMISTATA, SARANNO QUINDI RECUPERATI I DATI DI DEFAULT RELATIVI ALL'INTERVENTO DELLA PRATICA");
                var datiConfDefault = new DatiConfigurazioneProtocolloDefault(this._datiProtoDefault, this._regole, this._logs);
                return datiConfDefault.GetDati();
            }

            this._logs.Info($"RECUPERO DELL'INTERVENTO DEL SOFTWARE {this._software} A CUI LA PRATICA DEVE ESSERE SMISTATA");
            //var intervento = inventarioProcedimenti.Select(x => x.GetInterventoDaProcedimentoPrincipale(this._db, this._software, this._codiceComune)).ToArray()[0];

            AlberoProc intervento = null;

            foreach (var el in inventarioProcedimenti)
            {
                intervento = el.GetInterventoDaProcedimentoPrincipale(this._db, this._software, this._codiceComune);
                if (intervento != null)
                {
                    break;
                }
            }

            if (intervento == null)
            {
                throw new Exception($"INTERVENTO NON TROVATO PER IL SOFTWARE {this._software} DAL PROCEDIMENTO PRINCIPALE, VERIFICARE LA CONFIGURAZIONE DELL'ALBERO IN BASE AI DATI PRESENTI NELL'ISTANZA");
            }

            this._logs.Info($"INTERVENTO DEL SOFTWARE {this._software} RECUPERATO, ID: {intervento.Sc_id.Value}, CODICE: {intervento.SC_CODICE}, DESCRIZIONE: {intervento.SC_DESCRIZIONE} A CUI LA PRATICA DEVE ESSERE SMISTATA");

            retVal.Classifica = intervento.ClassificaProtocollazione;
            retVal.TipoDocumento = intervento.TipoDocumentoProtocollazione;

            if (!intervento.CodiceAmministrazione.HasValue)
            {
                throw new Exception($"L'INTERVENTO {intervento.SC_DESCRIZIONE}, CODICE: {intervento.Sc_id.Value} RELATIVO ALLO SPORTELLO {this._software} NON HA VALORIZZATO IL CODICE AMMINISTRAZIONE, NON E' POSSIBILE DEFINIRE L'ASSEGNATARIO");
            }

            //retVal.Uo = intervento.CodiceAmministrazione.Value.ToString();
            
            var ammMgr = new AmministrazioniMgr(this._db);
            var ammProtocollo = ammMgr.GetByIdProtocollo(this._idComune, intervento.CodiceAmministrazione.Value, this._software, this._codiceComune);

            if (ammProtocollo == null)
            {
                throw new Exception($"AMMINISTRAZIONE CODICE: {intervento.CodiceAmministrazione.Value} NON TROVATA");
            }

            this._logs.Info($"DATI PROTOCOLLO RECUPERATI, CODICE AMMINISTRAZIONE: {intervento.CodiceAmministrazione.Value}, AMMINISTRAZIONE: {ammProtocollo.AMMINISTRAZIONE}, CLASSIFICA: {retVal.Classifica}, TIPO DOCUMENTO: {retVal.TipoDocumento}, RUOLO: {ammProtocollo.PROT_RUOLO}, UO: {ammProtocollo.PROT_UO}");

            if (String.IsNullOrEmpty(ammProtocollo.PROT_UO))
            {
                throw new Exception($"L'AMMINISTRAZIONE {ammProtocollo.AMMINISTRAZIONE} NON HA VALORIZZATO IL CAMPO UO RELATIVO AL CODICE USER ID DELL'UTENTE PROTOCOLLATORE");
            }

            if (String.IsNullOrEmpty(ammProtocollo.PROT_RUOLO))
            {
                throw new Exception($"L'AMMINISTRAZIONE {ammProtocollo.AMMINISTRAZIONE} NON HA VALORIZZATO IL CAMPO RUOLO RELATIVO AL RUOLO / PROPRIETARIO");
            }

            retVal.Uo = ammProtocollo.PROT_UO;
            retVal.Ruolo = ammProtocollo.PROT_RUOLO;

            return retVal;
        }


    }
}
