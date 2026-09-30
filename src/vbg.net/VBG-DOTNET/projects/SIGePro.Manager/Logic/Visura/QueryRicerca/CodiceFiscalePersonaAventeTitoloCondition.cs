using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class CodiceFicalePersonaAventeTitoloFast : QueryConditionBase
    {
        public CodiceFicalePersonaAventeTitoloFast(DataBase database, string idComune, string software, FiltroPersonaAventeTitoloDiVisura personaAventeTitolo)
            : base(database, "Richiedente2")
        {

            if (String.IsNullOrEmpty(personaAventeTitolo?.CodiceFiscale))
            {
                this.Query = " INNER JOIN ISTANZE ";

                // if (database.ConnectionDetails.ProviderType == ProviderType.MySqlClient)
                // {
                //     this.Query += " FORCE INDEX (IDX_ISTANZE_013) ";
                // }

                this.Query += " ON ALBEROPROC.IDCOMUNE = ISTANZE.IDCOMUNE AND ALBEROPROC.SC_ID = ISTANZE.CODICEINTERVENTOPROC  ";

                return;
            }

            var sql = $@"INNER JOIN (
	    
		    SELECT 
				I1.*
			FROM 
				istanze I1 
					INNER JOIN ANAGRAFE ON ANAGRAFE.IDCOMUNE = I1.IDCOMUNE AND ANAGRAFE.CODICEANAGRAFE = I1.CODICERICHIEDENTE
					LEFT JOIN ANAGRAFE TECNICO ON TECNICO.IDCOMUNE = I1.IDCOMUNE AND TECNICO.CODICEANAGRAFE = I1.CODICEPROFESSIONISTA
					LEFT JOIN ANAGRAFE AZIENDA ON  AZIENDA.IDCOMUNE = I1.IDCOMUNE AND AZIENDA.CODICEANAGRAFE = I1.CODICETITOLARELEGALE
			WHERE 
				I1.idcomune = {this.QueryParameterName("idcom1")} AND
				I1.software = {this.QueryParameterName("soft1")} AND
				(
					ANAGRAFE.CODICEFISCALE = {this.QueryParameterName("cf1")} OR ANAGRAFE.partitaiva = {this.QueryParameterName("cf2")}  OR 
					TECNICO.codicefiscale = {this.QueryParameterName("cf3")} OR TECNICO.partitaiva = {this.QueryParameterName("cf4")} OR 
					AZIENDA.codicefiscale = {this.QueryParameterName("cf5")} OR AZIENDA.partitaiva = {this.QueryParameterName("cf6")}
				)
			UNION
				SELECT 
					I2.*
				FROM 
					istanze I2 
						INNER JOIN istanzerichiedenti ON 
							istanzerichiedenti.idcomune = I2.idcomune AND
							istanzerichiedenti.codiceistanza = I2.codiceistanza							
						INNER JOIN anagrafe soggetticollegati ON
							soggetticollegati.idcomune = istanzerichiedenti.idcomune AND
							soggetticollegati.codiceanagrafe = istanzerichiedenti.codicerichiedente 
						INNER JOIN tipisoggetto tsvisura ON
							tsvisura.idcomune = istanzerichiedenti.idcomune AND 
							tsvisura.codicetiposoggetto = istanzerichiedenti.codicetiposoggetto
					WHERE  
						I2.idcomune = {this.QueryParameterName("idcom2")} AND
						I2.software = {this.QueryParameterName("soft2")} AND
						( soggetticollegati.codicefiscale = {this.QueryParameterName("cf7")} OR soggetticollegati.partitaiva = {this.QueryParameterName("cf8")}) AND
						{database.Specifics.NvlFunction("tsvisura.flag_livelli_visura_pratica", 0)} > 0
	    
	    ) ISTANZE	    
	        ON ISTANZE.IDCOMUNE = ALBEROPROC.IDCOMUNE AND ISTANZE.CODICEINTERVENTOPROC = ALBEROPROC.SC_ID ";

            var codiceFiscale = personaAventeTitolo.CodiceFiscale;

            this.AddParameter("idcom1", idComune);
            this.AddParameter("soft1", software);

            this.AddParameter("cf1", codiceFiscale);
            this.AddParameter("cf2", codiceFiscale);
            this.AddParameter("cf3", codiceFiscale);
            this.AddParameter("cf4", codiceFiscale);
            this.AddParameter("cf5", codiceFiscale);
            this.AddParameter("cf6", codiceFiscale);

            this.AddParameter("idcom2", idComune);
            this.AddParameter("soft2", software);

            this.AddParameter("cf7", codiceFiscale);
            this.AddParameter("cf8", codiceFiscale);

            this.Query = sql;
        }
    }





    /*
    public class CodiceFiscalePersonaAventeTitoloCondition : QueryConditionBase
    {
        private readonly FiltroPersonaAventeTitoloDiVisura _personaAventeTitolo;

        public CodiceFiscalePersonaAventeTitoloCondition(DataBase database, FiltroPersonaAventeTitoloDiVisura personaAventeTitolo)
            : base(database, "FiltriRichiedente")
        {
            if (String.IsNullOrEmpty(personaAventeTitolo?.CodiceFiscale))
            {
                return;
            }

            var codiceFiscale = personaAventeTitolo.CodiceFiscale;
            //var cercaNeiSoggettiCollegati = personaAventeTitolo.CercaNeiSoggettiCollegati;

            var sb = new StringBuilder();
            sb.Append("(");
            sb.Append($"( ANAGRAFE.CODICEFISCALE = {this.QueryParameterName("cf1")} OR ANAGRAFE.partitaiva = {this.QueryParameterName("cf2")} )");
            sb.Append($" OR (TECNICO.codicefiscale = {this.QueryParameterName("cf3")} OR TECNICO.partitaiva = {this.QueryParameterName("cf4")})");
            sb.Append($" OR (AZIENDA.codicefiscale = {this.QueryParameterName("cf5")} OR AZIENDA.partitaiva = {this.QueryParameterName("cf6")})");

            this.AddParameter("cf1", codiceFiscale);
            this.AddParameter("cf2", codiceFiscale);
            this.AddParameter("cf3", codiceFiscale);
            this.AddParameter("cf4", codiceFiscale);
            this.AddParameter("cf5", codiceFiscale);
            this.AddParameter("cf6", codiceFiscale);

            //if (cercaNeiSoggettiCollegati)
            //{
            sb.Append($@"OR EXISTS(
			        SELECT 
				        istanzerichiedenti.codiceistanza 
			        FROM 
				        istanzerichiedenti
					        INNER JOIN anagrafe soggetticollegati ON
						        soggetticollegati.idcomune = istanzerichiedenti.idcomune AND
						        soggetticollegati.codiceanagrafe = istanzerichiedenti.codicerichiedente 
                            inner join tipisoggetto tsvisura on
                                tsvisura.idcomune = istanzerichiedenti.idcomune and 
                                tsvisura.codicetiposoggetto = istanzerichiedenti.codicetiposoggetto

			        WHERE 
				        istanzerichiedenti.idcomune = istanze.idcomune AND
				        istanzerichiedenti.codiceistanza = istanze.codiceistanza AND 
				        ( soggetticollegati.codicefiscale = {this.QueryParameterName("cf7")} OR soggetticollegati.partitaiva = {this.QueryParameterName("cf8")} ) and
                        {database.Specifics.NvlFunction("tsvisura.flag_livelli_visura_pratica", 0)} > 0
		        )");

            this.AddParameter("cf7", codiceFiscale);
            this.AddParameter("cf8", codiceFiscale);
            // }

            sb.Append(")");

            this.Query = sb.ToString();
            this._personaAventeTitolo = personaAventeTitolo;
        }
    }
    */
}
