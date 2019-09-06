using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class AnagraficheBase
    {
        public AnagraficheBase()
        {

        }

        protected void AggiornaAnagrafica(IAnagraficaAmministrazione anagrafica, ProtocolloService srv, string nominativo, AnagraficaView responseLeggi, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, ProtocolloLogs logs)
        {
            logs.Info($"INIZIO AGGIORNAMENTO ANAGRAFICA CODICE (PROTOCOLLO): {responseLeggi.codAna}, DESCRIZIONE (PROTOCOLLO): {responseLeggi.descAna}");
            logs.Info($"CONFIGURAZIONE AGGIORNAMENTO: {tipoAggiornamento}");

            if (tipoAggiornamento == TipoGestioneAnagraficaEnum.TipoAggiornamento.NO_AGGIORNAMENTO)
            {
                return;
            }

            logs.Info($"PEC ANAGRAFICA DA AGGIORNARE: {anagrafica.Pec}");

            if (String.IsNullOrEmpty(anagrafica.Pec))
            {
                return;
            }

            var numeroEmailPresenti = responseLeggi.emailList.Length;

            logs.Info($"NUMERO PEC PRESENTI SU ANAGRAFICA PROTOCOLLO: {numeroEmailPresenti}");

            if (tipoAggiornamento == TipoGestioneAnagraficaEnum.TipoAggiornamento.AGGIORNA_SE_PEC_VUOTA)
            {
                if (numeroEmailPresenti > 0)
                {
                    return;
                }
            }

            //if (String.IsNullOrEmpty(responseLeggi.codAna) || String.IsNullOrEmpty(responseLeggi.codAna.Trim()))
            //{
            //    logs.Info($"L'ANAGRAFICA {responseLeggi.descAna} NON E' PROVVISTA DI CODICE NON E' POSSIBILE QUINDI FARE L'AGGIORNAMENTO");
            //    return;
            //}

            if (responseLeggi.emailList.Where(x => x.email == anagrafica.Pec && x.tipo == tipoEmailAnagrafica.pec && x.principale == true).Count() == 0)
            {
                var requestAggiorna = new AggiornamentoAnagraficaRequest
                {
                    idAnagrafica = new IdAnagrafica { descAna = responseLeggi.descAna },
                    datiAggiornati = new NuovaAnagrafica
                    {
                        emailList = new EmailAnagrafica[]
                        {
                            new EmailAnagrafica
                            {
                                email = anagrafica.Pec,
                                tipo = tipoEmailAnagrafica.pec
                            }
                        }
                    }
                };

                logs.Info($"AGGIORNAMENTO DELL'ANAGRAFICA {responseLeggi.descAna} CON PEC {anagrafica.Pec}");
                srv.AggiornaAnagrafica(requestAggiorna);
            }
        }

        protected void InserisciAnagraficaDefault(IAnagraficaAmministrazione anagrafica, ProtocolloService srv, string nominativo)
        {
            var nuovaAnagrafica = new NuovaAnagrafica
            {
                descAna = nominativo,
                nome = anagrafica.Nome.Replace("  ", " ").Trim(),
                cognome = anagrafica.Cognome.Replace("  ", " ").Trim(),
                codTipoAna = "EST",
                disattivato = false,
                disattivatoSpecified = true
            };

            if (!String.IsNullOrEmpty(anagrafica.PartitaIva) && anagrafica.PartitaIva.Length == 11)
            {
                nuovaAnagrafica.piva = anagrafica.PartitaIva;
            }

            if (!String.IsNullOrEmpty(anagrafica.CodiceFiscale) && anagrafica.CodiceFiscale.Length == 16)
            {
                nuovaAnagrafica.codfis = anagrafica.CodiceFiscale;
            }

            if (!String.IsNullOrEmpty(anagrafica.Indirizzo))
            {
                nuovaAnagrafica.indirizzo = anagrafica.Indirizzo;
            }

            if (!String.IsNullOrEmpty(anagrafica.Cap))
            {
                int result;
                bool isParsable = int.TryParse(anagrafica.Cap.Trim(), out result);

                if (!isParsable)
                {
                    throw new Exception($"IL VALORE DEL CAP {anagrafica.Cap.Trim()}, RELATIVO ALL'ANAGRAFICA {anagrafica.NomeCognome}, NON HA UN FORMATO CORRETTO");
                }

                if (anagrafica.Cap.Trim().Length > 5)
                {
                    throw new Exception($"IL VALORE {anagrafica.Cap.Trim()} RELATIVO AL CAP DI RESIDENZA DELL'ANAGRAFICA {anagrafica.NomeCognome} E' COMPOSTO DA PIU' DI 5 CARATTERI");
                }

                nuovaAnagrafica.cap = anagrafica.Cap.Trim();
            }

            if (!String.IsNullOrEmpty(anagrafica.Pec))
            {
                nuovaAnagrafica.emailList = new EmailAnagrafica[]
                {
                        new EmailAnagrafica
                        {
                            email = anagrafica.Pec,
                            tipo = tipoEmailAnagrafica.pec
                        }
                };
            }


            if (!String.IsNullOrEmpty(anagrafica.Sesso))
            {
                nuovaAnagrafica.sesso = anagrafica.Sesso == "F" ? NuovaAnagraficaSesso.F : NuovaAnagraficaSesso.M;
                nuovaAnagrafica.sessoSpecified = true;
            }

            var requestInsert = new NuovaAnagraficaRequest { anagrafica = nuovaAnagrafica, };

            srv.InserisciAnagrafica(requestInsert);
        }

    }
}
