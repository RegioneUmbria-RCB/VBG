using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public partial class CCValiditaCoefficientiDettaglioMgr
    {
        private readonly DataBase _dataBase;
        [Serializable]
        public class BindingItemDataKey
        {
            public readonly int ListinoId;
            public readonly int InterventoId;
            public readonly int DestinazioneId;

            public BindingItemDataKey(int listinoId, int interventoId, int destinazioneId)
            {
                this.ListinoId = listinoId;
                this.InterventoId = interventoId;
                this.DestinazioneId = destinazioneId;
            }

            public BindingItemDataKey(string key)
            {
                var parts = key.Split('_');
                if (parts.Length != 3)
                    throw new ArgumentException($"Chiave non valida: {key}");

                this.ListinoId = Convert.ToInt32(parts[0]);
                this.InterventoId = Convert.ToInt32(parts[1]);
                this.DestinazioneId = Convert.ToInt32(parts[2]);
            }

            public override string ToString() => $"{this.ListinoId}_{this.InterventoId}_{this.DestinazioneId}";

        }

        public class BindingItem
        {
            public string Intervento { get; set; } = "";
            public string Destinazione { get; set; } = "";

            public decimal CostoMq { get; set; }

            public BindingItemDataKey? Id { get; internal set; }

        }

        public CCValiditaCoefficientiDettaglioMgr(DataBase dataBase)
        {
            this._dataBase = dataBase;
        }

        public IEnumerable<BindingItem> GetListByIdListino(string idComune, int idListino)
        {
            FormattableString sql = $@"SELECT 
	            cc_validitacoeff_dettaglio.fk_coefficente_id,
	            cc_validitacoeff_dettaglio.fk_intervento_id,
	            cc_validitacoeff_dettaglio.fk_destinazione_id,
	            cc_validitacoeff_dettaglio.costomq,
	            cc_tipointervento.intervento,
	            cc_destinazioni.destinazione
            FROM
	            cc_validitacoeff_dettaglio
	
	            INNER JOIN cc_tipointervento ON 
		            cc_tipointervento.idcomune = cc_validitacoeff_dettaglio.idcomune AND
		            cc_tipointervento.id = cc_validitacoeff_dettaglio.fk_intervento_id
		
	            INNER JOIN cc_destinazioni ON 
		            cc_destinazioni.idcomune = cc_validitacoeff_dettaglio.idcomune AND
		            cc_destinazioni.id = cc_validitacoeff_dettaglio.fk_destinazione_id

            WHERE 
	            cc_validitacoeff_dettaglio.idcomune = {idComune} AND
	            cc_validitacoeff_dettaglio.fk_coefficente_id = {idListino}
            order by 
                cc_tipointervento.intervento,
	            cc_destinazioni.destinazione";

            return this._dataBase.ExecuteReader(sql, dr =>
            {
                return new BindingItem
                {
                    Id = new BindingItemDataKey(
                        dr.GetInt("fk_coefficente_id")!.Value,
                        dr.GetInt("fk_intervento_id")!.Value,
                        dr.GetInt("fk_destinazione_id")!.Value
                    ),
                    CostoMq = dr.GetDecimal("costomq")!.Value,
                    Intervento = dr.GetString("intervento"),
                    Destinazione = dr.GetString("destinazione")
                };
            });
        }

        public void Insert(string idComune, int idListino, int idIntervento, int idDestinazione, decimal costoMq)
        {
            FormattableString sql = $@"INSERT INTO cc_validitacoeff_dettaglio (idcomune, fk_coefficente_id, fk_intervento_id, fk_destinazione_id, costomq)
                                        VALUES ({idComune}, {idListino}, {idIntervento}, {idDestinazione}, {costoMq})";

            this._dataBase.ExecuteNonQuery(sql);
        }

        public void Delete(string idComune, int idListino, int idIntervento, int idDestinazione)
        {
            FormattableString sql = $@"DELETE FROM cc_validitacoeff_dettaglio 
                                        WHERE idcomune = {idComune} AND fk_coefficente_id = {idListino} AND fk_intervento_id = {idIntervento} AND fk_destinazione_id = {idDestinazione}";
            this._dataBase.ExecuteNonQuery(sql);
        }


    }
}


