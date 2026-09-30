using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class QueryRicercaPratiche
    {
        private const string _queryBase = @"SELECT
	ISTANZE.CODICECOMUNE,
	ISTANZE.IDCOMUNE,
	ISTANZE.SOFTWARE AS SOFTWARE,
	SOFTWARE.DESCRIZIONELUNGA AS DESCSOFTWARE,
	ISTANZE.CODICEISTANZA AS IDPRATICA,
	ISTANZE.NUMEROISTANZA AS NUMEROPRATICA,
	ISTANZE.DATA AS DATAPRESENTAZIONE,
	ISTANZE.NUMEROPROTOCOLLO as NUMEROPROTOCOLLO,
	ISTANZE.DATAPROTOCOLLO AS DATAPROTOCOLLO,
	ISTANZE.CODICEINTERVENTOPROC AS CODICEINTERVENTO,
	ALBEROPROC.DESCRIZIONE_COMPLETA AS DESCRIZIONEINTERVENTO,
	TIPIPROCEDURE.CODICEPROCEDURA,
	TIPIPROCEDURE.PROCEDURA,
	ISTANZE.LAVORI AS OGGETTO,
	ISTANZE.CHIUSURA AS CODSTATOPRATICA,
	STATO AS STATOPRATICA,
	RESPONSABILI.RESPONSABILE as RESPONSABILE,
	RESPONSABILI.TELEFONOLAVORO AS RESPONSABILE_TELEFONO,
	STRADARIO.CODICESTRADARIO AS PR_CODVIARIO,
	STRADARIO.PREFISSO AS INDIRIZZO_PREFISSO,
	STRADARIO.DESCRIZIONE AS INDIRIZZO_DESCRIZIONE,
	ISTANZESTRADARIO.CODICECIVICO AS PR_CODCIVICO,
	ISTANZESTRADARIO.CIVICO AS PR_CIVICO,
	ISTANZE.CODICEAREA AS CODICEZONIZZAZIONE,
	AREE.DENOMINAZIONE AS ZONIZZAZIONE,
	ANAGRAFE.CODICEANAGRAFE AS CODICERICHIEDENTE,
	ANAGRAFE.CODICEFISCALE AS CODICEFISCALE,
	ANAGRAFE.PARTITAIVA AS PARTITAIVA,
	ANAGRAFE.NOMINATIVO AS RIC_NOMINATIVO,
	ANAGRAFE.NOME AS RIC_NOME,
	ANAGRAFE.INDIRIZZO,
	ANAGRAFE.CAP,
	ANAGRAFE.CITTA AS LOCALITA,
	COMUNERESIDENZA.COMUNE AS CITTA,
	COMUNERESIDENZA.PROVINCIA,
	TIPISOGGETTO.TIPOSOGGETTO AS TIPORAPPORTO,
	CATASTO.DESCRIZIONE AS TIPOCATASTO,
	ISTANZEMAPPALI.FOGLIO,
	ISTANZEMAPPALI.PARTICELLA,
	ISTANZEMAPPALI.SUB,
	TECNICO.CODICEANAGRAFE AS TEC_CODICE,
	TECNICO.NOMINATIVO AS TEC_NOMINATIVO,
	TECNICO.NOME AS TEC_NOME,
	TECNICO.CODICEFISCALE AS TEC_CODICEFISCALE,
	TECNICO.PARTITAIVA AS TEC_PARTITAIVA,
	AZIENDA.CODICEANAGRAFE AS AZ_CODICE,
	AZIENDA.NOMINATIVO AS AZ_NOMINATIVO,
	AZIENDA.NOME AS AZ_NOME,
	AZIENDA.CODICEFISCALE AS AZ_CODICEFISCALE,
	AZIENDA.PARTITAIVA AS AZ_PARTITAIVA,
	ISTRUTTORI.RESPONSABILE AS ISTRUTTORE,
	ISTRUTTORI.TELEFONOLAVORO AS ISTRUTTORE_TELEFONO,
	OPERATORI.RESPONSABILE AS OPERATORE,
	OPERATORI.TELEFONOLAVORO AS OPERATORE_TELEFONO,
	ISTANZE.DOMICILIO_ELETTRONICO AS DOMICILIO_ELETTRONICO,
	istanze.uuid AS UUID,
	ISTANZE.POSIZIONEARCHIVIO
FROM
    ALBEROPROC 
	    {0}
		INNER JOIN SOFTWARE ON SOFTWARE.CODICE = ISTANZE.SOFTWARE 
		INNER JOIN comuniassociati ON comuniassociati.idcomune = istanze.idcomune AND comuniassociati.codicecomune = istanze.codicecomune
		INNER JOIN ANAGRAFE ON ANAGRAFE.IDCOMUNE = ISTANZE.IDCOMUNE AND ANAGRAFE.CODICEANAGRAFE = ISTANZE.CODICERICHIEDENTE
		INNER JOIN STATIISTANZA ON STATIISTANZA.IDCOMUNE = ISTANZE.IDCOMUNE AND STATIISTANZA.SOFTWARE = ISTANZE.SOFTWARE AND STATIISTANZA.CODICESTATO = ISTANZE.CHIUSURA
		LEFT JOIN ANAGRAFE TECNICO ON TECNICO.IDCOMUNE = ISTANZE.IDCOMUNE AND TECNICO.CODICEANAGRAFE = ISTANZE.CODICEPROFESSIONISTA
		LEFT JOIN ANAGRAFE AZIENDA ON ISTANZE.IDCOMUNE = AZIENDA.IDCOMUNE AND ISTANZE.CODICETITOLARELEGALE = AZIENDA.CODICEANAGRAFE
		LEFT JOIN ISTANZESTRADARIO ON ISTANZE.IDCOMUNE = ISTANZESTRADARIO.IDCOMUNE AND ISTANZE.CODICEISTANZA = ISTANZESTRADARIO.CODICEISTANZA AND 1 = ISTANZESTRADARIO.PRIMARIO
		LEFT JOIN STRADARIO ON STRADARIO.IDCOMUNE = ISTANZESTRADARIO.IDCOMUNE AND STRADARIO.CODICESTRADARIO = ISTANZESTRADARIO.CODICESTRADARIO
		LEFT JOIN ISTANZEMAPPALI ON ISTANZE.IDCOMUNE = ISTANZEMAPPALI.IDCOMUNE AND ISTANZE.CODICEISTANZA = ISTANZEMAPPALI.FKCODICEISTANZA AND 1 = ISTANZEMAPPALI.PRIMARIO
		LEFT JOIN RESPONSABILI ON ISTANZE.IDCOMUNE = RESPONSABILI.IDCOMUNE AND ISTANZE.CODICERESPONSABILEPROC = RESPONSABILI.CODICERESPONSABILE
		LEFT JOIN COMUNI COMUNERESIDENZA ON ANAGRAFE.COMUNERESIDENZA = COMUNERESIDENZA.CODICECOMUNE
		LEFT JOIN TIPISOGGETTO ON ISTANZE.IDCOMUNE = TIPISOGGETTO.IDCOMUNE AND ISTANZE.FKCODICESOGGETTO = TIPISOGGETTO.CODICETIPOSOGGETTO
		LEFT JOIN AREE ON ISTANZE.IDCOMUNE = AREE.IDCOMUNE AND ISTANZE.CODICEAREA = AREE.CODICEAREA
		LEFT JOIN CATASTO ON ISTANZEMAPPALI.CODICECATASTO = CATASTO.CODICE
		LEFT JOIN TIPIPROCEDURE ON ISTANZE.IDCOMUNE = TIPIPROCEDURE.IDCOMUNE AND ISTANZE.CODICEPROCEDURA = TIPIPROCEDURE.CODICEPROCEDURA
		LEFT JOIN RESPONSABILI ISTRUTTORI ON ISTANZE.IDCOMUNE = ISTRUTTORI.IDCOMUNE AND ISTANZE.CODICEISTRUTTORE = ISTRUTTORI.CODICERESPONSABILE
		LEFT JOIN RESPONSABILI OPERATORI ON OPERATORI.IDCOMUNE = ISTANZE.IDCOMUNE AND OPERATORI.CODICERESPONSABILE = ISTANZE.CODICERESPONSABILE	
        LEFT JOIN COMUNIASSOCIATIESCLUSIONI ON 
            COMUNIASSOCIATIESCLUSIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND
            COMUNIASSOCIATIESCLUSIONI.CODICECOMUNE = ISTANZE.CODICECOMUNE AND
            COMUNIASSOCIATIESCLUSIONI.SOFTWARE = ISTANZE.SOFTWARE
WHERE
	-- Condizione sempre presente
	(COMUNIASSOCIATIESCLUSIONI.IDCOMUNE IS NULL and comuniassociati.disattivo_fo = 0) ";

        // private readonly StringBuilder _queryBuilder;
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly List<ParameterDefinition> _parameters = new List<ParameterDefinition>();

        private readonly List<IQueryCondition> _queryConditions = new List<IQueryCondition>();
        private CodiceFicalePersonaAventeTitoloFast _filtroPersonaAventeTitolo;


        public QueryRicercaPratiche(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;

            //var queryBase = _queryBase;
            //var forceIndex = "";

            // if (db.Specifics.DBMSName() == Provider.MYSQL)
            // {
            //     forceIndex = " FORCE INDEX (IDX_ISTANZE_013) ";
            // }
            // queryBase = String.Format(queryBase, forceIndex);
            // 
            // this._queryBuilder = new StringBuilder(queryBase);

            this._filtroPersonaAventeTitolo = new CodiceFicalePersonaAventeTitoloFast(db, idComune, null, null);
            // this._queryBuilder.Append($" AND ALBEROPROC.idcomune = {db.Specifics.QueryParameterName("idComune")}");

            // this._parameters.Add(new ParameterDefinition("idComune", idComune));
            //this._parameters.Add(db.CreateParameter("software", software));
        }

        public void SetFiltroAnagrafiche(string software, FiltroPersonaAventeTitoloDiVisura filtro)
        {
            this._filtroPersonaAventeTitolo = new CodiceFicalePersonaAventeTitoloFast(this._db, this._idComune, software, filtro);
        }

        public void AddCondition(IQueryCondition condition)
        {
            this._queryConditions.Add(condition);
            /*
            var query = condition.GetQuery();

            if (String.IsNullOrEmpty(query))
            {
                return;
            }

            this._queryBuilder.Append(" AND ");
            this._queryBuilder.Append(condition.GetQuery());
            this._queryBuilder.Append(" ");

            this._parameters.AddRange(condition.GetParameters());
            */
        }

        public string GetQuery()
        {
            var queryBase = String.Format(_queryBase, this._filtroPersonaAventeTitolo.GetQuery());

            this._parameters.AddRange(this._filtroPersonaAventeTitolo.GetParameters());

            var queryBuilder = new StringBuilder(queryBase);

            queryBuilder.Append($" AND ALBEROPROC.idcomune = {this._db.Specifics.QueryParameterName("idComune")}");

            this._parameters.Add(new ParameterDefinition("idComune", this._idComune));

            foreach (var condition in this._queryConditions)
            {
                var query = condition.GetQuery();

                if (String.IsNullOrEmpty(query))
                {
                    continue;
                }

                queryBuilder.Append(" AND (");
                queryBuilder.Append(condition.GetQuery());
                queryBuilder.Append(") ");

                this._parameters.AddRange(condition.GetParameters());
            }

            return queryBuilder.ToString();
        }

        public IEnumerable<ParameterDefinition> GetParameters()
        {
            return this._parameters;
        }

        //public void VerifyConditions()
        //{
        //    foreach (var condition in this._queryConditions)
        //    {
        //        condition.VerifyParameters();
        //    }
        //}
    }
}
