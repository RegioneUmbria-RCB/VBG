using Microsoft.AspNetCore.SignalR;
using PersonalLib2.Data;
using VBG.AppLogic.SSU.DataAccess;
using VBG.AppLogic.SSU.DataAccess.Dto;
using VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess.Factory;
using VBG.AppLogic.SSU.GeneratoreRicevute.Hubs;
using VBG.AppLogic.SSU.HubInterfaces;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess
{
    public class DomandeSsuDefaultAliasRepository : IDomandeSsuDefaultAliasRepository
    {
        private readonly IDbConnectionFactoryProvider _dbConnectionFactoryProvider;
        private readonly IHubContext<DomandeNotificationHub, IDomandeNotificationClient> _hubContext;

        public DomandeSsuDefaultAliasRepository(
            IDbConnectionFactoryProvider dbConnectionFactoryProvider,
            IHubContext<DomandeNotificationHub, IDomandeNotificationClient> hubContext)
        {
            this._dbConnectionFactoryProvider = dbConnectionFactoryProvider;
            this._hubContext = hubContext;
        }

        public List<(int FkIdDomanda, string Alias, string Software)> GetDomandeElaborabili()
        {
            using var db = this._dbConnectionFactoryProvider.Create().CreateDatabase();

            FormattableString sql = $@"
                SELECT 
	                fo_domande_ssu.alias, 
	                fo_domande_ssu.fk_iddomanda, 
	                fo_domande.software 
                FROM 
	                fo_domande_ssu 
		                INNER JOIN FO_DOMANDE ON
			                FO_DOMANDE.idcomune = fo_domande_ssu.idcomune AND
			                FO_DOMANDE.id = fo_domande_ssu.fk_iddomanda
                WHERE 
 	                fo_domande_ssu.stato >= {(int)StatiDomandaSsuEnum.Inviata} AND 
 	                fo_domande_ssu.stato <= {(int)StatiDomandaSsuEnum.RicevutaNotificataAStc} AND
                    fo_domande_ssu.non_elaborabile = 0
                ORDER BY 
	                fo_domande_ssu.alias, 
	                fo_domande_ssu.stato";

            return db.ExecuteReader(sql, dr => (
                dr.GetInt("fk_iddomanda")!.Value,
                dr.GetString("alias"),
                dr.GetString("software")
            )).ToList();
        }

        public async Task SetNonElaborabileAsync(string token, int idDomanda, bool isElaborabile, string idComune)
        {
            var elaborabileText = isElaborabile ? "0" : "1";

            FormattableString sql = $@"
                UPDATE  fo_domande_ssu 
                SET non_elaborabile = {elaborabileText} 
                WHERE idcomune = {idComune} 
                AND fk_iddomanda = {idDomanda}";

            using var db = this._dbConnectionFactoryProvider.Create(token).CreateDatabase();
            db.ExecuteNonQuery(sql);

            await this._hubContext.Clients.All.SendDomandaElaborabileAsync(idComune, idDomanda, isElaborabile);
        }

        public IEnumerable<FoDomandeSsu> GetNonElaborabili(string token, string? idComune = null)
        {
            FormattableString sql = $@"
                SELECT * 
                FROM fo_domande_ssu 
                WHERE non_elaborabile = 1";

            if (!string.IsNullOrEmpty(idComune))
            {
                sql = $@"
                SELECT * 
                FROM fo_domande_ssu 
                WHERE non_elaborabile = 1
                AND idcomune = {idComune}";
            }

            using var db = this._dbConnectionFactoryProvider.Create(token).CreateDatabase();

            return db.ExecuteReader(sql, dr => new FoDomandeSsu
            {
                IdComune = dr.GetString("IDCOMUNE"),
                FkIdDomanda = dr.GetInt("FK_IDDOMANDA")!.Value,
                IdDomandaSsu = dr.GetString("CUI"),
                NumeroDomandaSsu = dr.GetString("NUMERO_DOMANDA_SSU"),
                DataInvio = dr.GetDateTime("DATA_PRESENTAZIONE")!.Value,
                Stato = dr.GetInt("STATO").GetValueOrDefault(0),
                CodiceoggettoRicevuta = dr.GetInt("CODICEOGGETTO_RICEVUTA"),
                NonElaborabile = dr.GetInt("NON_ELABORABILE").GetValueOrDefault(0) == 1,
                Alias = dr.GetString("ALIAS"),
                IstatEnte = dr.GetString("ISTAT_ENTE")
            });
        }

        public IEnumerable<FoDomandaSsuMinimalDto> GetStatiDomande(string token)
        {
            FormattableString sql = $@"
                SELECT idcomune, fk_iddomanda, stato 
                FROM fo_domande_ssu
                WHERE non_elaborabile = 0
                AND stato >= {(int)StatiDomandaSsuEnum.Inviata}  
 	            AND stato <= {(int)StatiDomandaSsuEnum.RicevutaNotificataAStc}
                ORDER BY stato ASC";

            using var db = this._dbConnectionFactoryProvider.Create(token).CreateDatabase();

            return db.ExecuteReader(sql, dr => new FoDomandaSsuMinimalDto
            {
                IdComune = dr.GetString("IDCOMUNE"),
                FkIdDomanda = dr.GetInt("FK_IDDOMANDA")!.Value,
                Stato = dr.GetInt("STATO").GetValueOrDefault(0)
            });
        }

        public int GetTotErrori(string token)
        {
            FormattableString sql = $@"
                SELECT COUNT(*) AS TOT
                FROM fo_domande_ssu_audit";

            using var db = this._dbConnectionFactoryProvider.Create(token).CreateDatabase();
            return db.ExecuteScalar<int>(sql, 0);
        }

        public IEnumerable<FoDomandeSsuAudit> GetErrori(string token, string idComune, int? idDomanda = null)
        {
            FormattableString sql = $@"
                SELECT * 
                FROM fo_domande_ssu_audit
                WHERE idcomune = {idComune}";

            if (idDomanda is not null)
            {
                sql = $@"
                        SELECT * 
                        FROM fo_domande_ssu_audit
                        WHERE idcomune = {idComune}
                        AND fk_iddomanda = {idDomanda}";
            }

            using var db = this._dbConnectionFactoryProvider.Create(token).CreateDatabase();

            return db.ExecuteReader(sql, dr => new FoDomandeSsuAudit
            {
                IdComune = dr.GetString("IDCOMUNE"),
                FkIdDomanda = dr.GetInt("FK_IDDOMANDA")!.Value,
                Data = dr.GetDateTime("DATA")!.Value,
                Stato = dr.GetInt("STATO").GetValueOrDefault(0),
                Errore = dr.GetString("ERRORE")
            });
        }

        public void DeleteErrori(string token, string idComune, int idDomanda)
        {
            FormattableString sql = $@"
                DELETE FROM fo_domande_ssu_audit 
                WHERE idcomune = {idComune}
                AND fk_iddomanda = {idDomanda}";

            using var db = this._dbConnectionFactoryProvider.Create(token).CreateDatabase();
            db.ExecuteNonQuery(sql);
        }
    }
}
