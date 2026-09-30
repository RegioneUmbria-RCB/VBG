using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;


namespace Init.SIGePro.Manager
{
    public partial class OneriTipiRateizzazioneMgr : BaseManager
    {
        public enum DataInizioRateizzazione
        {
            Sconosciuto = 0,
            DataValiditaIstanza = 1,
            DataOdierna,
            DataMovimento,
            DataIstanza,
            DataProtocollo
        }

        //Restituisce la data di validità dell'istanza utilizzando la logica presente nella dll VB6 sigeproClass_01.dll
        public string GetDataInizioRate(string idcomune, int codiceistanza, DataInizioRateizzazione tipoCalcoloInizioRata, OneriTipiRateizzazione tipoRateizzazione)
        {
            DateTime? data = this.GetDataInizioRateInternal(idcomune, codiceistanza, tipoCalcoloInizioRata, tipoRateizzazione);

            return data.GetValueOrDefault(DateTime.Now).ToString("dd/MM/yyyy");
        }

        private DateTime? GetDataInizioRateInternal(string idcomune, int codiceistanza, DataInizioRateizzazione tipoCalcoloInizioRata, OneriTipiRateizzazione tipoRateizzazione)
        {
            var istanzeMgr = new IstanzeMgr(this.db);

            switch (tipoCalcoloInizioRata)
            {
                case DataInizioRateizzazione.DataValiditaIstanza:
                    return istanzeMgr.GetDataValidita(idcomune, codiceistanza);

                case DataInizioRateizzazione.DataMovimento:

                    List<Movimenti> list = new MovimentiMgr(this.db).GetList(new Movimenti
                    {
                        CODICEISTANZA = codiceistanza.ToString(),
                        TIPOMOVIMENTO = tipoRateizzazione.FkTipomovDetermdatain,
                        IDCOMUNE = idcomune,
                        OthersWhereClause = new[] { " MOVIMENTI.DATA IS NOT NULL " }.ToList()
                    });

                    if (list != null && list.Count != 0)
                        return list.First().DATA;

                    break;

                case DataInizioRateizzazione.DataIstanza:
                    return istanzeMgr.GetById(idcomune, codiceistanza).DATA.Value;

                case DataInizioRateizzazione.DataProtocollo:
                    return istanzeMgr.GetById(idcomune, codiceistanza).DATAPROTOCOLLO;
            }

            return null; ;
        }

        public IEnumerable<KeyValuePair<int, string>> GetTipiCalcoloInizioRata()
        {
            yield return new KeyValuePair<int, string>((int)DataInizioRateizzazione.DataValiditaIstanza, "Data validità istanza");
            yield return new KeyValuePair<int, string>((int)DataInizioRateizzazione.DataOdierna, "Data odierna");
            yield return new KeyValuePair<int, string>((int)DataInizioRateizzazione.DataMovimento, "Data del movimento");
            yield return new KeyValuePair<int, string>((int)DataInizioRateizzazione.DataIstanza, "Data dell'istanza");
            yield return new KeyValuePair<int, string>((int)DataInizioRateizzazione.DataProtocollo, "Data del protocollo");
        }


        //Restituisce la collection dei tipi scadenza
        public List<TIPI_SCADENZA> GetTipiScadenze()
        {
            TIPI_SCADENZAMgr tipiScadMgr = new TIPI_SCADENZAMgr(this.db);
            TIPI_SCADENZA tipiScad = new TIPI_SCADENZA();
            List<TIPI_SCADENZA> list = tipiScadMgr.GetList(tipiScad);

            return list;
        }

        private void VerificaRecordCollegati(OneriTipiRateizzazione cls)
        {
            // Verifico se un campo è presente in un modello
            string cmdText = "SELECT FK_ONERITIPIRATEIZ FROM RANGE_RATEIZZAZIONI WHERE IDCOMUNE = '" + cls.Idcomune + "' AND FK_ONERITIPIRATEIZ = " + cls.Tiporateizzazione.ToString();

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    closeCnn = true;
                    this.db.Connection.Open();
                }
                using (IDbCommand cmd = this.db.CreateCommand(cmdText))
                {
                    object obj = cmd.ExecuteScalar();
                    if (obj != null && Convert.ToInt32(obj) > 0)
                    {
                        throw new ReferentialIntegrityException("RANGE_RATEIZZAZIONI");
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }
    }
}
